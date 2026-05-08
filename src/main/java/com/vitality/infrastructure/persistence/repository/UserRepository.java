package com.vitality.infrastructure.persistence.repository;

import com.vitality.domain.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmailIgnoreCase(String email);

  Optional<User> findByGoogleId(String googleId);

  boolean existsByEmailIgnoreCase(String email);
}
