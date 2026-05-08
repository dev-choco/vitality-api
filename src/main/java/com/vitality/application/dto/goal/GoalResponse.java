package com.vitality.application.dto.goal;

public record GoalResponse(
    String slug,
    String name,
    String icon,
    String description,
    String colorClass
) {
}
