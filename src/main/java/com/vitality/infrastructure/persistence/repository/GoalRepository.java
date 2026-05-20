package com.vitality.infrastructure.persistence.repository;

import com.vitality.domain.model.Goal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

  List<Goal> findByActiveTrue();

  Optional<Goal> findBySlug(String slug);
}
