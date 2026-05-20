package com.vitality.infrastructure.web.controller;

import com.vitality.application.dto.recipe.RecipeCreateRequest;
import com.vitality.application.dto.recipe.RecipeDetailResponse;
import com.vitality.application.dto.recipe.RecipeSummaryResponse;
import com.vitality.application.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

  private final RecipeService recipeService;

  @GetMapping
  public ResponseEntity<Page<RecipeSummaryResponse>> getAllRecipes(
      @RequestParam(required = false) String goal,
      @RequestParam(required = false) String budget,
      @RequestParam(required = false) String mealType,
      @PageableDefault(size = 20) Pageable pageable) {
    return ResponseEntity.ok(recipeService.getAllRecipes(goal, budget, mealType, pageable));
  }

  @GetMapping("/by-ingredients")
  public ResponseEntity<Page<RecipeSummaryResponse>> getByIngredients(
      @RequestParam String ingredients,
      @PageableDefault(size = 20) Pageable pageable) {
    return ResponseEntity.ok(recipeService.getRecipesByIngredients(ingredients, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<RecipeDetailResponse> getRecipeById(@PathVariable Long id) {
    return ResponseEntity.ok(recipeService.getRecipeById(id));
  }

  @GetMapping("/slug/{slug}")
  public ResponseEntity<RecipeDetailResponse> getRecipeBySlug(@PathVariable String slug) {
    return ResponseEntity.ok(recipeService.getRecipeBySlug(slug));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<RecipeDetailResponse> createRecipe(
      @Valid @RequestBody RecipeCreateRequest request) {
    return ResponseEntity.ok(recipeService.createRecipe(request));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<RecipeDetailResponse> updateRecipe(
      @PathVariable Long id,
      @Valid @RequestBody RecipeCreateRequest request) {
    return ResponseEntity.ok(recipeService.updateRecipe(id, request));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
    recipeService.deleteRecipe(id);
    return ResponseEntity.noContent().build();
  }
}
