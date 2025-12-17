package com.quipux.spotifive.app.rest;

import com.quipux.spotifive.common.response.CategoriesResponse;
import com.quipux.spotifive.common.wrappers.SpotifyCategoriesWrapper;
import com.quipux.spotifive.core.service.GenreCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.quipux.spotifive.common.router.Router.SpotifyAPI.GENRES;
import static com.quipux.spotifive.common.router.Router.SpotifyAPI.ROOT;

@Slf4j
@RestController
@RequestMapping(ROOT)
public class GenreController {
    private final GenreCacheService genreCacheService;

    public GenreController(GenreCacheService genreCacheService) {
        this.genreCacheService = genreCacheService;
    }

    @GetMapping(GENRES)
    @ResponseStatus(HttpStatus.OK)
    public List<CategoriesResponse> getGenres() {
        log.info("GenreController::getGenres");
        return genreCacheService.getGenres();
    }
}
