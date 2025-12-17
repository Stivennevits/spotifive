package com.quipux.spotifive.client;

import com.quipux.spotifive.common.response.SpotifyTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "spotify-auth", url = "https://accounts.spotify.com")
public interface SpotiFyAuthFeign {

    @PostMapping(value = "/api/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    SpotifyTokenResponse getToken(@RequestHeader("Authorization") String authorization,
                                  @RequestBody String body);

}
