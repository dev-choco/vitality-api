package com.vitality.application.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.vitality.application.dto.auth.*;
import com.vitality.domain.model.RefreshToken;
import com.vitality.domain.model.User;
import com.vitality.infrastructure.persistence.repository.RefreshTokenRepository;
import com.vitality.infrastructure.persistence.repository.RoleRepository;
import com.vitality.infrastructure.persistence.repository.UserRepository;
import com.vitality.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.google.client-id}")
    private String googleClientId;

  @Transactional
  public LoginResponse register(RegisterRequest request) {
    if (userRepository.existsByEmailIgnoreCase(request.email())) {
      throw new IllegalArgumentException("Email already registered");
    }

    User user = new User();
    user.setEmail(request.email().toLowerCase());
    user.setPasswordHash(passwordEncoder.encode(request.password()));
    user.setName(request.name());
    user.setRole(roleRepository.findByName("ROLE_USER").orElse(null));
    user = userRepository.save(user);

    return generateTokens(user);
  }

  @Transactional
  public LoginResponse login(LoginRequest request) {
    User user = userRepository.findByEmailIgnoreCase(request.email())
        .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

    if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
      throw new IllegalArgumentException("Invalid credentials");
    }

    return generateTokens(user);
  }

  @Transactional
  public LoginResponse googleLogin(GoogleAuthRequest request) {
    GoogleIdToken.Payload payload = verifyGoogleIdToken(request.idToken());
    String email = payload.getEmail();

    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("Google token does not contain an email");
    }

    String lowerEmail = email.toLowerCase();

    User user = userRepository.findByEmailIgnoreCase(lowerEmail)
        .orElseGet(() -> {
          User newUser = new User();
          newUser.setEmail(lowerEmail);
          newUser.setName(extractNameFromPayload(payload));
          newUser.setGoogleId(lowerEmail);
          newUser.setRole(roleRepository.findByName("ROLE_USER").orElse(null));
          return userRepository.save(newUser);
        });

    user.setGoogleId(lowerEmail);
    return generateTokens(user);
  }

  @Transactional
  public LoginResponse refreshToken(RefreshRequest request) {
    RefreshToken storedToken = refreshTokenRepository.findByToken(request.refreshToken())
        .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

    if (storedToken.isRevoked() || storedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("Refresh token expired or revoked");
    }

    User user = storedToken.getUser();
    refreshTokenRepository.delete(storedToken);

    return generateTokens(user);
  }

  @Transactional
  public void logout(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
    refreshTokenRepository.revokeAllByUser(user);
  }

  private LoginResponse generateTokens(User user) {
    String accessToken = jwtService.generateAccessToken(user.getId(), user.getEmail());
    String refreshToken = jwtService.generateRefreshToken(user.getId(), user.getEmail());

    RefreshToken storedToken = new RefreshToken();
    storedToken.setUser(user);
    storedToken.setToken(refreshToken);
    storedToken.setExpiresAt(LocalDateTime.now().plusDays(7));
    refreshTokenRepository.save(storedToken);

    return new LoginResponse(accessToken, refreshToken, user.getEmail(), user.getName());
  }

  private GoogleIdToken.Payload verifyGoogleIdToken(String idToken) {
    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
        new NetHttpTransport(), new GsonFactory())
        .setAudience(Collections.singletonList(googleClientId))
        .build();

    try {
      GoogleIdToken token = verifier.verify(idToken);
      if (token == null) {
        throw new IllegalArgumentException("Invalid Google ID token: verification failed");
      }
      return token.getPayload();
    } catch (GeneralSecurityException | IOException e) {
      throw new IllegalArgumentException("Failed to verify Google ID token", e);
    }
  }

  private String extractNameFromPayload(GoogleIdToken.Payload payload) {
    String name = (String) payload.get("name");
    if (name != null && !name.isBlank()) {
      return name;
    }
    String givenName = (String) payload.get("given_name");
    if (givenName != null && !givenName.isBlank()) {
      return givenName;
    }
    return "User";
  }
}
