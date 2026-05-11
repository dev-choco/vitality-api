package com.vitality.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final SecretKey signingKey;
  private final long accessTokenExpiration;
  private final long refreshTokenExpiration;

  public JwtService(
      @Value("${app.jwt.secret}") String secret,
      @Value("${app.jwt.access-token-expiration}") long accessTokenExpiration,
      @Value("${app.jwt.refresh-token-expiration}") long refreshTokenExpiration) {
    this.signingKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    this.accessTokenExpiration = accessTokenExpiration;
    this.refreshTokenExpiration = refreshTokenExpiration;
  }

  public String generateAccessToken(Long userId, String email) {
    return buildToken(new HashMap<>(), userId, email, accessTokenExpiration);
  }

  public String generateRefreshToken(Long userId, String email) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("jti", UUID.randomUUID().toString());
    return buildToken(claims, userId, email, refreshTokenExpiration);
  }

  public String extractEmail(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Long extractUserId(String token) {
    return extractClaim(token, claims -> claims.get("userId", Long.class));
  }

  public boolean isTokenValid(String token) {
    try {
      return !isTokenExpired(token);
    } catch (Exception e) {
      return false;
    }
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    Claims claims = Jwts.parser()
        .verifyWith(signingKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();
    return claimsResolver.apply(claims);
  }

  private String buildToken(Map<String, Object> extraClaims, Long userId, String email,
                            long expiration) {
    return Jwts.builder()
        .claims(extraClaims)
        .subject(email)
        .claim("userId", userId)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(signingKey)
        .compact();
  }
}
