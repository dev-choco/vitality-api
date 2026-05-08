package com.vitality.application.dto.myth;

public record MythSummaryResponse(
    Long id,
    String mythText,
    String realityText,
    String category
) {
}
