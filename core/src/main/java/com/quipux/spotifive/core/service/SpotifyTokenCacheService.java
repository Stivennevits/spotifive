package com.quipux.spotifive.core.service;

import com.quipux.spotifive.client.services.SpotifyAuthService;
import com.quipux.spotifive.common.constants.ErrorMessages;
import com.quipux.spotifive.common.ex.ApiException;
import com.quipux.spotifive.common.ex.SpotiFiveException;
import com.quipux.spotifive.common.response.SpotifyTokenResponse;
import com.quipux.spotifive.core.components.I18NComponent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SpotifyTokenCacheService {

    private final SpotifyAuthService spotifyAuthService;
    private final I18NComponent i18NComponent;
    private String cachedToken;
    private long tokenExpiryTime;
    private final Object tokenLock = new Object();

    private static final long SAFETY_MARGIN_SECONDS = 300;

    public SpotifyTokenCacheService(SpotifyAuthService spotifyAuthService, I18NComponent i18NComponent) {
        this.spotifyAuthService = spotifyAuthService;
        this.i18NComponent = i18NComponent;
    }

    public SpotifyTokenResponse getToken() {
        log.info("SpotifyTokenCacheService::getToken");

        synchronized (tokenLock) {
            if (isTokenValid()) {
                log.info("Returning token from cache");
                return buildCachedTokenResponse();
            }
            log.info("Expired token, refreshing...");
            return refreshToken();
        }
    }

    private boolean isTokenValid() {
        return cachedToken != null &&
                System.currentTimeMillis() < tokenExpiryTime;
    }

    private SpotifyTokenResponse buildCachedTokenResponse() {
        long remainingSeconds = (tokenExpiryTime - System.currentTimeMillis()) / 1000;

        return SpotifyTokenResponse.builder()
                .accessToken(cachedToken)
                .tokenType("Bearer")
                .expiresIn((int) remainingSeconds)
                .build();
    }

    private SpotifyTokenResponse refreshToken() {
        try {
            System.out.println("paso por aqui 0");
            SpotifyTokenResponse tokenResponse = spotifyAuthService.getClientCredentialsToken();
            System.out.println("paso por aqui 0.01");
            this.cachedToken = tokenResponse.getAccessToken();
            System.out.println("paso por aqui");
            this.tokenExpiryTime = System.currentTimeMillis() +
                    ((tokenResponse.getExpiresIn() - SAFETY_MARGIN_SECONDS) * 1000);
            System.out.println("paso por aqui 2");

            log.info("Token updated. Expire in: {} sec", tokenResponse.getExpiresIn());

            return tokenResponse;

        } catch (Exception e) {
            log.error("Error calling token from spotify : {}", e.getMessage(), e);
            throw new SpotiFiveException(i18NComponent.getMessage(ErrorMessages.ERROR_CALLING_SPOTIFY_TOKEN));
        }
    }


    public void invalidateCache() {
        log.info("Invalidating Spotify token cache");
        synchronized (tokenLock) {
            this.cachedToken = null;
            this.tokenExpiryTime = 0;
        }
    }

    public boolean isCacheValid() {
        return isTokenValid();
    }
}
