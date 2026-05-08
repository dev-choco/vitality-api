package com.vitality.infrastructure.web.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

  private final com.vitality.infrastructure.persistence.repository.UserRepository userRepository;

  @GetMapping
  public ResponseEntity<Map<String, String>> getProfile(Authentication auth) {
    Long userId = (Long) auth.getCredentials();
    var user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    return ResponseEntity.ok(Map.of(
        "email", user.getEmail(),
        "name", user.getName(),
        "avatarUrl", user.getAvatarUrl() != null ? user.getAvatarUrl() : ""
    ));
  }
}
