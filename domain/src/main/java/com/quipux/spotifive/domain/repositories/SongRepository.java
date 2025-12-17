package com.quipux.spotifive.domain.repositories;

import com.quipux.spotifive.domain.model.SongRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository <SongRecord, Long> {
}
