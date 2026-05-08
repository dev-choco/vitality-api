package com.vitality.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "myths")
public class Myth {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "myth_text", nullable = false)
  private String mythText;

  @Column(name = "reality_text", nullable = false)
  private String realityText;

  @Column(name = "myth_explanation", columnDefinition = "TEXT")
  private String mythExplanation;

  @Column(name = "reality_explanation", columnDefinition = "TEXT")
  private String realityExplanation;

  @Column(length = 100)
  private String category;

  @Column(name = "image_url", length = 1000)
  private String imageUrl;

  @Column(name = "scientific_source", length = 500)
  private String scientificSource;

  @Column(nullable = false)
  private boolean active = true;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
