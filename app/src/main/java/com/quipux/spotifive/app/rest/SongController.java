package com.quipux.spotifive.app.rest;

import com.quipux.spotifive.common.request.SongRequest;
import com.quipux.spotifive.core.service.SongService;
import com.quipux.spotifive.domain.model.SongRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.quipux.spotifive.common.router.Router.SongsAPI.*;

@Slf4j
@RestController
@RequestMapping(ROOT)
public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<SongRecord> findAllSongs() {
        log.info("SongController::findAllSongs");
        return songService.findAllSongs();
    }

    @GetMapping(PAGING)
    @ResponseStatus(HttpStatus.OK)
    public Page<SongRecord> paging(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size) {
        log.info("SongController::paging search {} page {} size {}", search, page, size);
        return songService.paging(search, page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SongRecord createSong(@RequestBody SongRequest request) {
        log.info("SongController::createSong request {}", request);
        return songService.create(request);
    }

}
