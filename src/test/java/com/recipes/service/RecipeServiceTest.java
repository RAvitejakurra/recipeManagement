package com.recipes.service;

import com.recipes.model.Recipe;
import com.recipes.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

	@Mock
	private RecipeRepository recipeRepository;

	@InjectMocks
	private RecipeService recipeService;

	private Recipe sampleRecipe;

	@BeforeEach
	void setUp() {
		sampleRecipe = new Recipe();
		sampleRecipe.setId("1");
		sampleRecipe.setName("Pasta");
		sampleRecipe.setVegetarian(true);
		sampleRecipe.setServings(2);
		sampleRecipe.setIngredients(Arrays.asList("Tomato", "Garlic", "Cheese"));
	}

	@Test
	void saveRecipe_shouldSaveRecipe() {
		when(recipeRepository.save(sampleRecipe)).thenReturn(sampleRecipe);

		Recipe savedRecipe = recipeService.saveRecipe(sampleRecipe);

		assertNotNull(savedRecipe);
		assertEquals("Pasta", savedRecipe.getName());
		verify(recipeRepository, times(1)).save(sampleRecipe);
	}

	@Test
	void getRecipesExcludingIngredient_shouldReturnRecipesWithoutGivenIngredient() {
		List<Recipe> recipesWithoutCheese = List.of(sampleRecipe);
		when(recipeRepository.findByIngredientsNotContaining("Cheese")).thenReturn(recipesWithoutCheese);

		List<Recipe> result = recipeService.getRecipesExcludingIngredient("Cheese");

		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		assertFalse(result.get(0).getIngredients().contains("Cheese"));
		verify(recipeRepository, times(1)).findByIngredientsNotContaining("Cheese");
	}
}
