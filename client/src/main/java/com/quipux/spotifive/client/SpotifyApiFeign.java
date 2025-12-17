package com.quipux.spotifive.client;

import com.quipux.spotifive.common.wrappers.SpotifyCategoriesWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "spotify-api", url = "https://api.spotify.com/v1/")
public interface SpotifyApiFeign {

    @GetMapping("browse/categories")
    SpotifyCategoriesWrapper getCategories(
            @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "limit", defaultValue = "50") int limit);

}
