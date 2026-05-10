package com.vitality.application.dto.food;

public record FoodSummaryResponse(
    Long id,
    String name,
    String slug,
    String categoryName,
    String categoryIcon,
    String imageUrl,
    String primaryBenefit,
    String consumptionSuggestion
) {
}
