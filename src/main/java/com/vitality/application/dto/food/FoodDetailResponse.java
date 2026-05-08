package com.vitality.application.dto.food;

import java.util.List;

public record FoodDetailResponse(
    Long id,
    String name,
    String slug,
    FoodCategoryResponse category,
    String description,
    String imageUrl,
    Double caloriesPer100g,
    Double proteinG,
    Double carbsG,
    Double fatG,
    Double fiberG,
    List<BenefitItem> benefits,
    String consumptionTips
) {
  public record BenefitItem(String icon, String text) {
  }
}
