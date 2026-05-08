package com.vitality.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 300)
  private String title;

  @Column(nullable = false, unique = true, length = 350)
  private String slug;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "image_url", length = 1000)
  private String imageUrl;

  @Column(name = "prep_time_min")
  private Integer prepTimeMin;

  @Column(length = 50)
  private String difficulty;

  @Column(name = "budget_tag", length = 30)
  private String budgetTag;

  @Column
  private Integer calories;

  @Column(name = "protein_g")
  private Double proteinG;

  @Column(name = "carbs_g")
  private Double carbsG;

  @Column(name = "fat_g")
  private Double fatG;

  @Column(name = "goal_tags", length = 500)
  private String goalTags;

  @Column(columnDefinition = "TEXT")
  private String instructions;

  @Column(nullable = false)
  private boolean active = true;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<RecipeIngredient> ingredients = new HashSet<>();

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
