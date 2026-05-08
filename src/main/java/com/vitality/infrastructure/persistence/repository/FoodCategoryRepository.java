package com.vitality.infrastructure.persistence.repository;

import com.vitality.domain.model.FoodCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {

  Optional<FoodCategory> findBySlug(String slug);
}
