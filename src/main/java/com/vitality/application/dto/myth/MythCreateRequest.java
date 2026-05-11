package com.vitality.application.dto.myth;

import jakarta.validation.constraints.NotBlank;

public record MythCreateRequest(
    @NotBlank String mythText,
    @NotBlank String realityText,
    String mythExplanation,
    String realityExplanation,
    String category,
    String imageUrl,
    String scientificSource
) {}
