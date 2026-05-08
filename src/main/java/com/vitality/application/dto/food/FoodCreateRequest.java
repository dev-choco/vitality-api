package com.vitality.application.dto.food;

import jakarta.validation.constraints.NotBlank;

public record FoodCreateRequest(
    @NotBlank String name,
    @NotBlank Long categoryId,
    String description,
    String imageUrl,
    Double caloriesPer100g,
    Double proteinG,
    Double carbsG,
    Double fatG,
    Double fiberG,
    String benefits,
    String consumptionTips
) {
}
