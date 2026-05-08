package com.vitality.application.dto.recipe;

import java.util.List;

public record RecipeSummaryResponse(
    Long id,
    String title,
    String slug,
    String imageUrl,
    Integer prepTimeMin,
    String budgetTag,
    Integer calories,
    String goalTag,
    List<String> ingredientNames
) {
}
