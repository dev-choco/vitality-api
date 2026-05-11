package com.vitality.application.dto.goal;

import jakarta.validation.constraints.NotBlank;

public record GoalCreateRequest(
    @NotBlank String name,
    @NotBlank String slug,
    String icon,
    String description,
    String colorClass
) {}
