package com.vitality.application.dto.auth;

public record LoginResponse(
    String accessToken,
    String refreshToken,
    String email,
    String name
) {
}
