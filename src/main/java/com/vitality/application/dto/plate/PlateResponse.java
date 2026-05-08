package com.vitality.application.dto.plate;

import java.time.LocalDateTime;

public record PlateResponse(
    Long id,
    FoodInfo protein,
    FoodInfo carb,
    String veggieNames,
    String notes,
    LocalDateTime createdAt
) {
  public record FoodInfo(Long id, String name, String imageUrl) {
  }
}
