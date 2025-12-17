package com.quipux.spotifive.core.service;

import com.quipux.spotifive.client.services.SpotifyCategoriesService;
import com.quipux.spotifive.common.constants.ErrorMessages;
import com.quipux.spotifive.common.ex.SpotiFiveException;
import com.quipux.spotifive.common.response.CategoriesResponse;
import com.quipux.spotifive.common.wrappers.SpotifyCategoriesWrapper;
import com.quipux.spotifive.core.components.I18NComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class GenreCacheService {

    private final SpotifyTokenCacheService spotifyTokenCacheService;
    private final SpotifyCategoriesService spotifyCategoriesService;
    private final I18NComponent i18NComponent;

    public GenreCacheService(SpotifyTokenCacheService spotifyTokenCacheService, SpotifyCategoriesService spotifyCategoriesService, I18NComponent i18NComponent) {
        this.spotifyTokenCacheService = spotifyTokenCacheService;
        this.spotifyCategoriesService = spotifyCategoriesService;
        this.i18NComponent = i18NComponent;
    }

    @Cacheable(value = "genres", unless = "#result == null || #result.isEmpty()")
    public List<CategoriesResponse> getGenres() {
        log.info("GenreCacheService::getGenres");
        String token = spotifyTokenCacheService.getToken().getAccessToken();
        SpotifyCategoriesWrapper wrapper = spotifyCategoriesService.getCategories(token, 50);
        List<CategoriesResponse> genres = wrapper.getCategories() != null ?
                wrapper.getCategories().getItems() : Collections.emptyList();
        log.info("GenreCacheService::getGenres - Cached {} genres", genres.size());
        return genres;
    }

    public void validateGenreByName(String genreName) {
        log.info("GenreCacheService::validateGenreByName - Validating genre with name: {}", genreName);

        if (genreName == null || genreName.trim().isEmpty()) {
            log.warn("GenreCacheService::validateGenreByName - Genre name is null or empty");
            throw new SpotiFiveException(i18NComponent.getMessage(ErrorMessages.GENRE_NOT_FOUND, genreName));
        }

        List<CategoriesResponse> genres = getGenres();
        boolean exists = genres.stream()
                .anyMatch(genre -> genreName.trim().equalsIgnoreCase(genre.getName()));

        if (!exists) {
            log.error("GenreCacheService::validateGenreByName - Genre with name '{}' not found", genreName);
            throw new SpotiFiveException(i18NComponent.getMessage(ErrorMessages.GENRE_NOT_FOUND, genreName));
        }

        log.info("GenreCacheService::validateGenreByName - Genre '{}' is valid", genreName);
    }

}
