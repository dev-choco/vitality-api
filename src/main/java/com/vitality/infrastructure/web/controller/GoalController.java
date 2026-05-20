package com.vitality.infrastructure.web.controller;

import com.vitality.application.dto.goal.GoalCreateRequest;
import com.vitality.application.dto.goal.GoalResponse;
import com.vitality.application.dto.recipe.RecipeSummaryResponse;
import com.vitality.application.service.GoalService;
import com.vitality.application.service.RecipeService;
import jakarta.validation.Valid;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goals")
@RequiredArgsConstructor
public class GoalController {

  private final GoalService goalService;
  private final RecipeService recipeService;

  @GetMapping
  public ResponseEntity<List<GoalResponse>> getAllGoals() {
    return ResponseEntity.ok(goalService.getAllGoals());
  }

  @GetMapping("/{id}")
  public ResponseEntity<GoalResponse> getGoalById(@PathVariable Long id) {
    return ResponseEntity.ok(goalService.getGoalById(id));
  }

  @GetMapping("/{slug}/recipes")
  public ResponseEntity<Page<RecipeSummaryResponse>> getRecipesByGoal(
      @PathVariable String slug,
      @PageableDefault(size = 20) Pageable pageable) {
    return ResponseEntity.ok(recipeService.getAllRecipes(slug, null, null, pageable));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<GoalResponse> createGoal(
      @Valid @RequestBody GoalCreateRequest request) {
    return ResponseEntity.ok(goalService.createGoal(request));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<GoalResponse> updateGoal(
      @PathVariable Long id,
      @Valid @RequestBody GoalCreateRequest request) {
    return ResponseEntity.ok(goalService.updateGoal(id, request));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
    goalService.deleteGoal(id);
    return ResponseEntity.noContent().build();
  }
}
