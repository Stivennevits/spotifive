package com.quipux.spotifive.domain.repositories;

import com.quipux.spotifive.domain.model.PlayListSongRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayListSongRepository extends JpaRepository<PlayListSongRecord, Long> {
}
