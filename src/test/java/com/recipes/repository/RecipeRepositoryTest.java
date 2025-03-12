package com.recipes.repository;

import com.recipes.model.Recipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    private Recipe testRecipe;

    @BeforeEach
    void setUp() {
        testRecipe = new Recipe();
        testRecipe.setName("Vegetable Curry");
        testRecipe.setServings(4);
        testRecipe.setVegetarian(true);
        testRecipe.setIngredients(List.of("Carrot", "Potato"));
        testRecipe.setInstructions("Cook in a pan for 20 minutes.");

        recipeRepository.save(testRecipe);
    }

    @AfterEach
    void tearDown() {
        recipeRepository.deleteAll();
    }

    @Test
    void testFindById() {
        Optional<Recipe> foundRecipe = recipeRepository.findById(testRecipe.getId());
        assertTrue(foundRecipe.isPresent());
        assertEquals("Vegetable Curry", foundRecipe.get().getName());
    }

    @Test
    void testFindByIngredientsContaining() {
        List<Recipe> recipes = recipeRepository.findByIngredientsContaining("Carrot");
        assertFalse(recipes.isEmpty());
        assertEquals(1, recipes.size());
        assertEquals("Vegetable Curry", recipes.get(0).getName());
    }

    @Test
    void testDeleteRecipe() {
        recipeRepository.deleteById(testRecipe.getId());
        Optional<Recipe> deletedRecipe = recipeRepository.findById(testRecipe.getId());
        assertFalse(deletedRecipe.isPresent());
    }
}
