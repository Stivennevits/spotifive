package com.quipux.spotifive.core.service;

import com.quipux.spotifive.client.services.SpotifyAuthService;
import com.quipux.spotifive.common.response.SpotifyTokenResponse;
import com.quipux.spotifive.core.components.I18NComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PlayListService {
    private final I18NComponent i18NComponent;
    private final SpotifyTokenCacheService spotifyTokenCacheService;

    private static final Logger log = LoggerFactory.getLogger(PlayListService.class);

    public PlayListService(I18NComponent i18NComponent, SpotifyAuthService spotifyAuthService, SpotifyTokenCacheService spotifyTokenCacheService) {
        this.i18NComponent = i18NComponent;
        this.spotifyTokenCacheService = spotifyTokenCacheService;
    }

    public SpotifyTokenResponse getToken() {
        log.info("PlayListService::getToken");
        return spotifyTokenCacheService.getToken();
    }
}
