package com.vitality.infrastructure.web.controller;

import com.vitality.application.dto.auth.GoogleAuthRequest;
import com.vitality.application.dto.auth.LoginRequest;
import com.vitality.application.dto.auth.LoginResponse;
import com.vitality.application.dto.auth.RefreshRequest;
import com.vitality.application.dto.auth.RegisterRequest;
import com.vitality.application.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterRequest request) {
    return ResponseEntity.ok(authService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }

  @PostMapping("/google")
  public ResponseEntity<LoginResponse> googleLogin(@Valid @RequestBody GoogleAuthRequest request) {
    return ResponseEntity.ok(authService.googleLogin(request));
  }

  @PostMapping("/refresh")
  public ResponseEntity<LoginResponse> refresh(@Valid @RequestBody RefreshRequest request) {
    return ResponseEntity.ok(authService.refreshToken(request));
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(Authentication auth) {
    Long userId = (Long) auth.getCredentials();
    authService.logout(userId);
    return ResponseEntity.noContent().build();
  }
}
