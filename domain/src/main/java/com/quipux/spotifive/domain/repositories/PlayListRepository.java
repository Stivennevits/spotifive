package com.quipux.spotifive.domain.repositories;

import com.quipux.spotifive.domain.model.PlayListRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayListRepository extends JpaRepository<PlayListRecord, Long> {
}
