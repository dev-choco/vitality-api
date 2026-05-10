package com.vitality.infrastructure.web.controller;

import com.vitality.application.dto.goal.GoalResponse;
import com.vitality.application.dto.recipe.RecipeSummaryResponse;
import com.vitality.application.service.RecipeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goals")
@RequiredArgsConstructor
public class GoalController {

  private final RecipeService recipeService;

  private static final List<GoalResponse> GOALS = List.of(
      new GoalResponse("bajar-peso", "Bajar de peso", "fitness_center",
          "Recetas y alimentos para alcanzar tu peso ideal", "primary"),
      new GoalResponse("subir-peso", "Subir de peso", "trending_up",
          "Gana masa muscular de forma saludable", "tertiary"),
      new GoalResponse("comer-economico", "Comer económico", "payments",
          "Nutrición completa sin gastar de más", "secondary"),
      new GoalResponse("energia-diaria", "Energía diaria", "bolt",
          "Alimentos que te mantienen activo todo el día", "primary")
  );

  @GetMapping
  public ResponseEntity<List<GoalResponse>> getAllGoals() {
    return ResponseEntity.ok(GOALS);
  }

  @GetMapping("/{slug}/recipes")
  public ResponseEntity<Page<RecipeSummaryResponse>> getRecipesByGoal(
      @PathVariable String slug,
      @PageableDefault(size = 20) Pageable pageable) {
    return ResponseEntity.ok(recipeService.getAllRecipes(slug, null, null, pageable));
  }
}
