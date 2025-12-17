package com.quipux.spotifive.app.rest;

import com.quipux.spotifive.common.response.SpotifyTokenResponse;
import com.quipux.spotifive.core.service.PlayListService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.quipux.spotifive.common.router.Router.SpotifyAPI.ROOT;
import static com.quipux.spotifive.common.router.Router.SpotifyAPI.TOKEN;

@RestController
@RequestMapping(ROOT)
public class PlayListController {
    private final PlayListService playListService;
    private static final Logger log = LoggerFactory.getLogger(PlayListController.class);

    public PlayListController(PlayListService playListService) {
        this.playListService = playListService;
    }

    @GetMapping(TOKEN)
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    public SpotifyTokenResponse getToken() {
        log.info("PlayListController::getToken");
        return playListService.getToken();
    }

}
