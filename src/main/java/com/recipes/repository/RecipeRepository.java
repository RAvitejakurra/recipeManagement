package com.recipes.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.recipes.model.Recipe;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String>{
	
	 // Find all vegetarian recipes
    List<Recipe> findByVegetarian(boolean vegetarian);

    // Find recipes by number of servings
    List<Recipe> findByServings(int servings);

    // Find recipes that contain a specific ingredient
    List<Recipe> findByIngredientsContaining(String ingredient);

    // Find recipes that do NOT contain a specific ingredient
    List<Recipe> findByIngredientsNotContaining(String ingredient);

    // Find recipes where instructions contain a keyword
    List<Recipe> findByInstructionsContainingIgnoreCase(String keyword);

}
