package com.quipux.spotifive.client.services;

import com.quipux.spotifive.client.SpotifyApiFeign;
import com.quipux.spotifive.common.wrappers.SpotifyCategoriesWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpotifyCategoriesService {
    private final SpotifyApiFeign spotifyApiFeign;

    public SpotifyCategoriesService(SpotifyApiFeign spotifyApiFeign) {
        this.spotifyApiFeign = spotifyApiFeign;
    }


    public SpotifyCategoriesWrapper getCategories(String accessToken, int limit) {
        try {
            log.info("Fetching categories from Spotify API with limit: {}", limit);
            String authorization = "Bearer " + accessToken;
            SpotifyCategoriesWrapper response = spotifyApiFeign.getCategories(authorization, limit);
            log.info("Successfully retrieved {} categories",
                    response.getCategories() != null && response.getCategories().getItems() != null
                            ? response.getCategories().getItems().size()
                            : 0);
            return response;
        } catch (Exception e) {
            log.error("Error fetching categories from Spotify API: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch categories from Spotify", e);
        }
    }

}
