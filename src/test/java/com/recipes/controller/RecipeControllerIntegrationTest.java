package com.recipes.controller;

import com.recipes.model.Recipe;
import com.recipes.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecipeControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RecipeRepository recipeRepository;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/recipes";
        recipeRepository.deleteAll();  // Clean DB before each test
    }

    @Test
    void testAddAndRetrieveRecipe() {
        // Create a sample recipe
        Recipe recipe = new Recipe();
        recipe.setName("Paneer Butter Masala");
        recipe.setVegetarian(true);
        recipe.setServings(4);
        recipe.setIngredients(Collections.singletonList("Paneer"));
        recipe.setInstructions("Cook paneer with spices.");

        // Send POST request
        ResponseEntity<Recipe> postResponse = restTemplate.postForEntity(baseUrl, recipe, Recipe.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());
        assertNotNull(postResponse.getBody().getId());

        // Fetch all recipes
        ResponseEntity<Recipe[]> getResponse = restTemplate.getForEntity(baseUrl, Recipe[].class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals(1, getResponse.getBody().length);
    }

    @Test
    void testFilterVegetarianRecipes() {
        // Add a vegetarian recipe
        Recipe recipe = new Recipe();
        recipe.setName("Vegetable Biryani");
        recipe.setVegetarian(true);
        recipe.setServings(2);
        recipe.setIngredients(Collections.singletonList("Rice"));
        recipe.setInstructions("Cook rice with vegetables.");

        recipeRepository.save(recipe);

        // Fetch vegetarian recipes
        ResponseEntity<Recipe[]> response = restTemplate.getForEntity(baseUrl + "?vegetarian=true", Recipe[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertTrue(response.getBody()[0].isVegetarian());
    }

    @Test
    void testDeleteRecipe() {
        // Add a recipe
        Recipe recipe = new Recipe();
        recipe.setName("Chocolate Cake");
        recipe.setVegetarian(true);
        recipe.setServings(6);
        recipe.setIngredients(Collections.singletonList("Chocolate"));
        recipe.setInstructions("Bake chocolate cake.");

        Recipe savedRecipe = recipeRepository.save(recipe);

        // Delete the recipe
        restTemplate.delete(baseUrl + "/" + savedRecipe.getId());

        // Verify deletion
        ResponseEntity<Recipe> response = restTemplate.getForEntity(baseUrl + "/" + savedRecipe.getId(), Recipe.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
