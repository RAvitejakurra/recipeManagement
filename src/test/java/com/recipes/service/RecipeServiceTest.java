package com.recipes.service;

import com.recipes.model.Recipe;
import com.recipes.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    private Recipe recipe1;
    private Recipe recipe2;

    @BeforeEach
    void setUp() {
        recipe1 = new Recipe();
        recipe1.setName("Vegetable Curry");
        recipe1.setIngredients(List.of("Carrot", "Potato"));

        recipe2 = new Recipe();
        recipe2.setName("Grilled Salmon");
        recipe2.setIngredients(List.of("Salmon", "Lemon"));
    }

    @Test
    void getRecipesExcludingIngredient_shouldReturnRecipesWithoutGivenIngredient() {
        // Mock repository call
        when(recipeRepository.findByIngredientsNotContaining("Salmon"))
            .thenReturn(List.of(recipe1)); // Should return only recipe1

        // Call service method
        List<Recipe> result = recipeService.getRecipesExcludingIngredient("Salmon");

        // Assertions
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size()); // Should only return 1 recipe
        assertFalse(result.get(0).getIngredients().contains("Salmon"));

        // Verify that repository was called exactly once
        verify(recipeRepository, times(1)).findByIngredientsNotContaining("Salmon");
    }
}
