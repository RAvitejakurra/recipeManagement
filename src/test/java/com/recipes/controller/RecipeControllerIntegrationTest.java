package com.recipes.controller;

import com.recipes.model.Recipe;
import com.recipes.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecipeControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private RecipeRepository recipeRepository;

	private String baseUrl;

	@BeforeEach
	void setUp() {
		baseUrl = "http://localhost:" + port + "/recipes";
		recipeRepository.deleteAll();
	}

	@Test
	void excludeIngredientEndpoint_shouldReturnRecipesWithoutSpecifiedIngredient() {
		Recipe recipe1 = new Recipe();
		recipe1.setName("Vegetable Curry");
		recipe1.setVegetarian(true);
		recipe1.setServings(4);
		recipe1.setIngredients(List.of("Carrot", "Potato", "Onion"));

		Recipe recipe2 = new Recipe();
		recipe2.setName("Grilled Salmon");
		recipe2.setVegetarian(false);
		recipe2.setServings(2);
		recipe2.setIngredients(List.of("Salmon", "Lemon", "Garlic"));

		recipeRepository.save(recipe1);
		recipeRepository.save(recipe2);

		ResponseEntity<Recipe[]> response = restTemplate.exchange(baseUrl + "/exclude-ingredient?ingredient=Salmon",
				HttpMethod.GET, null, Recipe[].class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(1, response.getBody().length);
		assertEquals("Vegetable Curry", response.getBody()[0].getName());
	}
}
