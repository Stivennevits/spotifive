package com.quipux.spotifive.domain.repositories;

import com.quipux.spotifive.domain.model.ModuleRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<ModuleRecord, Long> {
}
