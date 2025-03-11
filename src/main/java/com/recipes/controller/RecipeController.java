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
        return recipeService.getRecipeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable String id) {
        recipeService.deleteRecipeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipes(
            @RequestParam(required = false) Boolean vegetarian,
            @RequestParam(required = false) Integer servings,
            @RequestParam(required = false) String includeIngredient,
            @RequestParam(required = false) String excludeIngredient,
            @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(recipeService.getAllRecipes().stream()
                .filter(recipe -> vegetarian == null || recipe.isVegetarian() == vegetarian)
                .filter(recipe -> servings == null || recipe.getServings() == servings)
                .filter(recipe -> includeIngredient == null || recipe.getIngredients().contains(includeIngredient))
                .filter(recipe -> excludeIngredient == null || !recipe.getIngredients().contains(excludeIngredient))
                .filter(recipe -> keyword == null || recipe.getInstructions().toLowerCase().contains(keyword.toLowerCase()))
                .toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable String id, @RequestBody Recipe updatedRecipe) {
        return recipeService.updateRecipe(id, updatedRecipe)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
