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

    public List<Recipe> getRecipesWithIngredient(String ingredient) {
        return recipeRepository.findByIngredientsContaining(ingredient);
    }

    public List<Recipe> getRecipesWithoutIngredient(String ingredient) {
        return recipeRepository.findAll()
                .stream()
                .filter(recipe -> !recipe.getIngredients().contains(ingredient))
                .toList();
    }

    public List<Recipe> searchRecipesByInstruction(String keyword) {
        return recipeRepository.findByInstructionsContainingIgnoreCase(keyword);
    }

    public Optional<Recipe> updateRecipe(String id, Recipe updatedRecipe) {
        return recipeRepository.findById(id).map(recipe -> {
            recipe.setName(updatedRecipe.getName());
            recipe.setVegetarian(updatedRecipe.isVegetarian());
            recipe.setServings(updatedRecipe.getServings());
            recipe.setIngredients(updatedRecipe.getIngredients());
            recipe.setInstructions(updatedRecipe.getInstructions());
            return recipeRepository.save(recipe);
        });
    }
}
