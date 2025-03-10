package com.recipes.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
	private static final String SECRET_KEY = "YourSuperSecretKeyForJWTAuthentication123456789"; // Must be at least 256
																								// bits
	private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

	private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}

	public String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String token, String username) {
		return extractUsername(token).equals(username) && !isTokenExpired(token);
	}

	public boolean isTokenExpired(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration()
				.before(new Date());
	}
}
