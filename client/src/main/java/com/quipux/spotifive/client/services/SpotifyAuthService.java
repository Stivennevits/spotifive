package com.quipux.spotifive.client.services;

import com.quipux.spotifive.client.SpotiFyAuthFeign;
import com.quipux.spotifive.common.response.SpotifyTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Slf4j
@Service
public class SpotifyAuthService {

    private final SpotiFyAuthFeign spotiFyAuthFeign;

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    public SpotifyAuthService(SpotiFyAuthFeign spotiFyAuthFeign) {
        this.spotiFyAuthFeign = spotiFyAuthFeign;
    }

    public SpotifyTokenResponse getClientCredentialsToken() {
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        String authorization = "Basic " + encodedCredentials;
        String body = "grant_type=client_credentials";

        return spotiFyAuthFeign.getToken(authorization, body);
    }


}
