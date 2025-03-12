package com.recipes.service;

import com.recipes.model.Recipe;
import com.recipes.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {

	private final RecipeRepository recipeRepository;

	public Recipe saveRecipe(Recipe recipe) {
		return recipeRepository.save(recipe);
	}

	public List<Recipe> getAllRecipes() {
		return recipeRepository.findAll();
	}

	public Optional<Recipe> getRecipeById(String id) {
		return recipeRepository.findById(id);
	}

	public void deleteRecipeById(String id) {
		recipeRepository.deleteById(id);
	}

	public List<Recipe> getVegetarianRecipes() {
		return recipeRepository.findByVegetarian(true);
	}

	public List<Recipe> getRecipesByServings(int servings) {
		return recipeRepository.findByServings(servings);
	}

	public List<Recipe> getRecipesIncludingIngredient(String ingredient) {
		return recipeRepository.findByIngredientsContaining(ingredient);
	}

	public List<Recipe> getRecipesWithInstructionsContaining(String keyword) {
		return recipeRepository.findByInstructionsContainingIgnoreCase(keyword);
	}

	// New method for filtering recipes that do NOT contain a specific ingredient
	public List<Recipe> getRecipesExcludingIngredient(String ingredient) {
		return recipeRepository.findByIngredientsNotContaining(ingredient);
	}
}
