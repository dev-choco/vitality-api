package com.vitality.application.dto.recipe;

import java.time.LocalDateTime;
import java.util.List;

public record RecipeDetailResponse(
    Long id,
    String title,
    String slug,
    String description,
    String imageUrl,
    Integer prepTimeMin,
    String difficulty,
    String budgetTag,
    Integer calories,
    Double proteinG,
    Double carbsG,
    Double fatG,
    String goalTags,
    String instructions,
    List<IngredientItem> ingredients,
    LocalDateTime createdAt
) {
  public record IngredientItem(Long foodId, String foodName, String foodSlug, String quantity,
                               String unit) {
  }
}
