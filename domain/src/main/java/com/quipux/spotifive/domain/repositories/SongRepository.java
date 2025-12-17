package com.quipux.spotifive.domain.repositories;

import com.quipux.spotifive.domain.model.SongRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SongRepository extends JpaRepository <SongRecord, Long> {
    @Query(name = "SongRepository.findAllByFilter", nativeQuery = true)
    Page<SongRecord> findAllByFilter(@Param("search") String search, PageRequest pageRequest);

    Optional<SongRecord> findByTitle(String title);

    Optional<SongRecord> findByTitleAndArtist(String title, String artist);

    boolean existsByTitleAndArtist(String title, String artist);
}
