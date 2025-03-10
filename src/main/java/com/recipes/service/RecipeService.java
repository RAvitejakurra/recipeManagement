package com.recipes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipes.model.Recipe;
import com.recipes.repository.RecipeRepository;

@Service
public class RecipeService {
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	  // Save a new recipe
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);  // Uses built-in MongoRepository method
    }

    // Get all recipes
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();  // Uses built-in method
    }

    // Get a recipe by ID
    public Optional<Recipe> getRecipeById(String id) {
        return recipeRepository.findById(id);
    }

    // Delete a recipe by ID
    public void deleteRecipeById(String id) {
        recipeRepository.deleteById(id);  // Uses built-in method
    }

    // Get all vegetarian recipes
    public List<Recipe> getVegetarianRecipes() {
        return recipeRepository.findByVegetarian(true);
    }

    // Get recipes by number of servings
    public List<Recipe> getRecipesByServings(int servings) {
        return recipeRepository.findByServings(servings);
    }

    // Get recipes containing a specific ingredient
    public List<Recipe> getRecipesWithIngredient(String ingredient) {
        return recipeRepository.findByIngredientsContaining(ingredient);
    }

    // Get recipes excluding a specific ingredient
    public List<Recipe> getRecipesWithoutIngredient(String ingredient) {
        return recipeRepository.findByIngredientsNotContaining(ingredient);
    }

    // Search recipes by instructions (text search)
    public List<Recipe> searchRecipesByInstruction(String keyword) {
        return recipeRepository.findByInstructionsContainingIgnoreCase(keyword);
    }

}
