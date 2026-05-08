package com.vitality.infrastructure.persistence.repository;

import com.vitality.domain.model.SavedPlate;
import com.vitality.domain.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedPlateRepository extends JpaRepository<SavedPlate, Long> {

  List<SavedPlate> findByUserIdOrderByCreatedAtDesc(Long userId);

  void deleteByUser(User user);
}
