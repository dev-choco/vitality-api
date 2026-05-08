package com.vitality.infrastructure.persistence.repository;

import com.vitality.domain.model.Myth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MythRepository extends JpaRepository<Myth, Long> {

  Page<Myth> findByActiveTrue(Pageable pageable);

  Page<Myth> findByCategoryIgnoreCaseAndActiveTrue(String category, Pageable pageable);
}
