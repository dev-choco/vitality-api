package com.vitality.application.dto.recipe;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record RecipeCreateRequest(
    @NotBlank String title,
    @NotBlank String description,
    String imageUrl,
    Integer prepTimeMin,
    String difficulty,
    String budgetTag,
    Integer calories,
    Double proteinG,
    Double carbsG,
    Double fatG,
    String goalTags,
    String mealType,
    String instructions,
    List<IngredientRequest> ingredients
) {
  public record IngredientRequest(Long foodId, String quantity, String unit) {
  }
}
