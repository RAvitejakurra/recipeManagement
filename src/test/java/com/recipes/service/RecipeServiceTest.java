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
    void getAllRecipes_shouldReturnAllRecipes() {
        List<Recipe> recipes = Arrays.asList(sampleRecipe);
        when(recipeRepository.findAll()).thenReturn(recipes);

        List<Recipe> result = recipeService.getAllRecipes();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getRecipeById_shouldReturnRecipe() {
        when(recipeRepository.findById("1")).thenReturn(Optional.of(sampleRecipe));

        Optional<Recipe> result = recipeService.getRecipeById("1");

        assertTrue(result.isPresent());
        assertEquals("Pasta", result.get().getName());
        verify(recipeRepository, times(1)).findById("1");
    }

    @Test
    void deleteRecipeById_shouldDeleteRecipe() {
        doNothing().when(recipeRepository).deleteById("1");

        recipeService.deleteRecipeById("1");

        verify(recipeRepository, times(1)).deleteById("1");
    }

    @Test
    void getVegetarianRecipes_shouldReturnVegetarianRecipes() {
        List<Recipe> vegetarianRecipes = Arrays.asList(sampleRecipe);
        when(recipeRepository.findByVegetarian(true)).thenReturn(vegetarianRecipes);

        List<Recipe> result = recipeService.getVegetarianRecipes();

        assertFalse(result.isEmpty());
        assertTrue(result.get(0).isVegetarian());
        verify(recipeRepository, times(1)).findByVegetarian(true);
    }

    @Test
    void getRecipesByServings_shouldReturnRecipesByServings() {
        List<Recipe> recipes = Arrays.asList(sampleRecipe);
        when(recipeRepository.findByServings(2)).thenReturn(recipes);

        List<Recipe> result = recipeService.getRecipesByServings(2);

        assertFalse(result.isEmpty());
        assertEquals(2, result.get(0).getServings());
        verify(recipeRepository, times(1)).findByServings(2);
    }
}
