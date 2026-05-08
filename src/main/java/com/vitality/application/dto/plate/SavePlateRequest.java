package com.vitality.application.dto.plate;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SavePlateRequest(
    Long proteinFoodId,
    Long carbFoodId,
    @NotNull List<Long> veggieFoodIds,
    String notes
) {
}
