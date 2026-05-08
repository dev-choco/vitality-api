package com.vitality.infrastructure.persistence.repository;

import com.vitality.domain.model.Food;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

  Optional<Food> findBySlug(String slug);

  List<Food> findByCategoryIdAndActiveTrue(Long categoryId);

  List<Food> findByActiveTrue();

  List<Food> findByNameContainingIgnoreCaseAndActiveTrue(String name);

  @Query("SELECT f FROM Food f WHERE f.active = true AND LOWER(f.name) LIKE LOWER(CONCAT('%', :query, '%'))")
  List<Food> searchByName(@Param("query") String query);

  boolean existsByNameIgnoreCase(String name);
}
