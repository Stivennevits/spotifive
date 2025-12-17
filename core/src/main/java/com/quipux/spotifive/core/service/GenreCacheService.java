package com.quipux.spotifive.core.service;

import com.quipux.spotifive.client.services.SpotifyCategoriesService;
import com.quipux.spotifive.common.response.CategoriesResponse;
import com.quipux.spotifive.common.wrappers.SpotifyCategoriesWrapper;
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

    public GenreCacheService(SpotifyTokenCacheService spotifyTokenCacheService, SpotifyCategoriesService spotifyCategoriesService) {
        this.spotifyTokenCacheService = spotifyTokenCacheService;
        this.spotifyCategoriesService = spotifyCategoriesService;
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
}
