package com.recipes.controller;

import com.recipes.model.Recipe;
import com.recipes.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

	private final RecipeService recipeService;

	@PostMapping
	public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
		return ResponseEntity.ok(recipeService.saveRecipe(recipe));
	}

	@GetMapping
	public ResponseEntity<List<Recipe>> getAllRecipes() {
		return ResponseEntity.ok(recipeService.getAllRecipes());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Recipe> getRecipeById(@PathVariable String id) {
		return recipeService.getRecipeById(id).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRecipe(@PathVariable String id) {
		recipeService.deleteRecipeById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/vegetarian")
	public ResponseEntity<List<Recipe>> getVegetarianRecipes() {
		return ResponseEntity.ok(recipeService.getVegetarianRecipes());
	}

	@GetMapping("/servings")
	public ResponseEntity<List<Recipe>> getRecipesByServings(@RequestParam int servings) {
		return ResponseEntity.ok(recipeService.getRecipesByServings(servings));
	}

	@GetMapping("/include-ingredient")
	public ResponseEntity<List<Recipe>> getRecipesIncludingIngredient(@RequestParam String ingredient) {
		return ResponseEntity.ok(recipeService.getRecipesIncludingIngredient(ingredient));
	}

	@GetMapping("/instructions")
	public ResponseEntity<List<Recipe>> getRecipesWithInstructions(@RequestParam String keyword) {
		return ResponseEntity.ok(recipeService.getRecipesWithInstructionsContaining(keyword));
	}

	@GetMapping("/exclude-ingredient")
	public ResponseEntity<List<Recipe>> getRecipesExcludingIngredient(@RequestParam String ingredient) {
		return ResponseEntity.ok(recipeService.getRecipesExcludingIngredient(ingredient));
	}
}
