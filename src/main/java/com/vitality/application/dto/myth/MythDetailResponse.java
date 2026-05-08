package com.vitality.application.dto.myth;

import java.time.LocalDateTime;

public record MythDetailResponse(
    Long id,
    String mythText,
    String realityText,
    String mythExplanation,
    String realityExplanation,
    String category,
    String imageUrl,
    String scientificSource,
    LocalDateTime createdAt
) {
}
