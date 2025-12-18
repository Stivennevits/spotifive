package com.quipux.spotifive.domain.repositories;

import com.quipux.spotifive.domain.model.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserRecord, Long> {
    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

    Optional<UserRecord> findByUsernameIgnoreCase(String username);
}
