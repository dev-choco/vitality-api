package com.vitality.infrastructure.persistence.repository;

import com.vitality.domain.model.Recipe;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

  Optional<Recipe> findBySlug(String slug);

  Page<Recipe> findByActiveTrue(Pageable pageable);

  @Query("""
      SELECT r FROM Recipe r WHERE r.active = true
      AND (:goalPattern IS NULL OR LOWER(r.goalTags) LIKE :goalPattern)
      AND (:budgetPattern IS NULL OR LOWER(r.budgetTag) LIKE :budgetPattern)
      AND (:mealType IS NULL OR LOWER(r.mealType) = :mealType)
      ORDER BY r.id ASC
      """)
  Page<Recipe> findByFilters(@Param("goalPattern") String goalPattern,
                             @Param("budgetPattern") String budgetPattern,
                             @Param("mealType") String mealType,
                             Pageable pageable);

  @Query("""
      SELECT DISTINCT r FROM Recipe r
      JOIN r.ingredients i
      JOIN i.food f
      WHERE r.active = true
      AND LOWER(f.name) IN :ingredientNames
      GROUP BY r
      ORDER BY COUNT(DISTINCT f.id) DESC
      """)
  Page<Recipe> findByIngredientNames(@Param("ingredientNames") List<String> ingredientNames,
                                     Pageable pageable);

  @Query("SELECT r FROM Recipe r WHERE r.active = true AND r.prepTimeMin <= :maxTime ORDER BY r.prepTimeMin ASC")
  Page<Recipe> findQuickRecipes(@Param("maxTime") int maxTime, Pageable pageable);
}
