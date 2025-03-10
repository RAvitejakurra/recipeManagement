package com.recipes.controller;

import com.recipes.model.Recipe;
import com.recipes.service.RecipeService;
import com.recipes.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private JwtUtil jwtUtil;  // ✅ Inject JWT Utility for token validation

    //Helper method to validate JWT token
    private boolean isTokenValid(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false; // Invalid token format
        }
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        try {
            String username = jwtUtil.extractUsername(token);
            return username != null && !jwtUtil.isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    // 1. Add a new recipe (Requires Authentication)
    @PostMapping
    public ResponseEntity<Recipe> addRecipe(@RequestHeader("Authorization") String authHeader, 
                                            @RequestBody Recipe recipe) {
        if (!isTokenValid(authHeader)) {
            return ResponseEntity.status(401).body(null);
        }
        Recipe savedRecipe = recipeService.saveRecipe(recipe);
        return ResponseEntity.ok(savedRecipe);
    }

    //2. Get all recipes (Requires Authentication)
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes(@RequestHeader("Authorization") String authHeader) {
        if (!isTokenValid(authHeader)) {
            return ResponseEntity.status(401).body(null);
        }
        List<Recipe> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    //3. Get a recipe by ID (Requires Authentication)
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@RequestHeader("Authorization") String authHeader, 
                                                @PathVariable String id) {
        if (!isTokenValid(authHeader)) {
            return ResponseEntity.status(401).body(null);
        }
        Optional<Recipe> recipe = recipeService.getRecipeById(id);
        return recipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //4. Delete a recipe by ID (Requires Authentication)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipeById(@RequestHeader("Authorization") String authHeader, 
                                                 @PathVariable String id) {
        if (!isTokenValid(authHeader)) {
            return ResponseEntity.status(401).build();
        }
        recipeService.deleteRecipeById(id);
        return ResponseEntity.noContent().build();
    }

    //5. Get vegetarian recipes (Requires Authentication) --we need to add boolean check in above method
    @GetMapping("/vegetarian")
    public ResponseEntity<List<Recipe>> getVegetarianRecipes(@RequestHeader("Authorization") String authHeader) {
        if (!isTokenValid(authHeader)) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(recipeService.getVegetarianRecipes());
    }

    //6. Get recipes by number of servings (Requires Authentication)
    @GetMapping("/servings/{servings}")
    public ResponseEntity<List<Recipe>> getRecipesByServings(@RequestHeader("Authorization") String authHeader, 
                                                             @PathVariable int servings) {
        if (!isTokenValid(authHeader)) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(recipeService.getRecipesByServings(servings));
    }

    //7. Get recipes that include a specific ingredient (Requires Authentication)
    @GetMapping("/include-ingredient/{ingredient}")
    public ResponseEntity<List<Recipe>> getRecipesWithIngredient(@RequestHeader("Authorization") String authHeader, 
                                                                 @PathVariable String ingredient) {
        if (!isTokenValid(authHeader)) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(recipeService.getRecipesWithIngredient(ingredient));
    }

    //8. Get recipes that exclude a specific ingredient (Requires Authentication)
    @GetMapping("/exclude-ingredient/{ingredient}")
    public ResponseEntity<List<Recipe>> getRecipesWithoutIngredient(@RequestHeader("Authorization") String authHeader, 
                                                                    @PathVariable String ingredient) {
        if (!isTokenValid(authHeader)) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(recipeService.getRecipesWithoutIngredient(ingredient));
    }

    //9. Search recipes by instructions (Requires Authentication)
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Recipe>> searchRecipesByInstruction(@RequestHeader("Authorization") String authHeader, 
                                                                   @PathVariable String keyword) {
        if (!isTokenValid(authHeader)) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(recipeService.searchRecipesByInstruction(keyword));
    }

    //10. Update a recipe by ID (Requires Authentication)
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@RequestHeader("Authorization") String authHeader, 
                                               @PathVariable String id, 
                                               @RequestBody Recipe updatedRecipe) {
        if (!isTokenValid(authHeader)) {
            return ResponseEntity.status(401).body(null);
        }

        Optional<Recipe> existingRecipe = recipeService.getRecipeById(id);
        if (existingRecipe.isPresent()) {
            Recipe recipe = existingRecipe.get();
            recipe.setName(updatedRecipe.getName());
            recipe.setVegetarian(updatedRecipe.isVegetarian());
            recipe.setServings(updatedRecipe.getServings());
            recipe.setIngredients(updatedRecipe.getIngredients());
            recipe.setInstructions(updatedRecipe.getInstructions());

            Recipe savedRecipe = recipeService.saveRecipe(recipe);
            return ResponseEntity.ok(savedRecipe);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
