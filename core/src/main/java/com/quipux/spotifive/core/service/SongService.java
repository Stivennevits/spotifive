package com.quipux.spotifive.core.service;

import com.quipux.spotifive.common.constants.ErrorMessages;
import com.quipux.spotifive.common.ex.SpotiFiveException;
import com.quipux.spotifive.common.request.SongRequest;
import com.quipux.spotifive.core.components.I18NComponent;
import com.quipux.spotifive.domain.model.SongRecord;
import com.quipux.spotifive.common.mapper.SongMapper;
import com.quipux.spotifive.domain.repositories.SongRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SongService {
    private final SongRepository repository;
    private final I18NComponent i18NComponent;
    private final GenreCacheService genreCacheService;

    public SongService(SongRepository repository, I18NComponent i18NComponent, GenreCacheService genreCacheService) {
        this.repository = repository;
        this.i18NComponent = i18NComponent;
        this.genreCacheService = genreCacheService;
    }

    public SongRecord getByTitleAndArtist(String title, String artist) {
        log.info("SongService::getByTitleAndArtist({}, {})", title, artist);
        return repository.findByTitleAndArtist(title,artist)
                .orElseThrow(() -> new SpotiFiveException(i18NComponent.getMessage(ErrorMessages.SONG_NOT_FOUND_BY_TITLE)));
    }

    public List<SongRecord> findAllSongs() {
        log.info("SongService::findAllSongs");
        return repository.findAll();
    }

    public Page<SongRecord> paging(String search, int page, int size) {
        log.info("SongService::paging search {} page {} size {}", search, page, size);
        return repository.findAllByFilter(search, PageRequest.of(page, size));
    }

    public SongRecord create(SongRequest request) {
        log.info("SongService::create request {}", request);
        existsByTitleAndArtist(request.getTitle(), request.getArtist());
        genreCacheService.validateGenreByName(request.getGenre());
        return repository.save(SongMapper.mapToCreate(request));
    }

    private void existsByTitleAndArtist(String title, String artist) {
        log.info("SongService::existsByTitleAndArtist({}, {})", title, artist);
        if (repository.existsByTitleAndArtist(title, artist)) {
            throw new SpotiFiveException(i18NComponent.getMessage(ErrorMessages.SONG_ALREADY_EXISTS_BY_TITLE_AND_ARTIST, title, artist));
        }
    }
}
