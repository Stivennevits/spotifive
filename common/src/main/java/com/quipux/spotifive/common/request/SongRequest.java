package com.quipux.spotifive.common.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongRequest {
    private String title;
    private String artist;
    private String album;
    private String genre;
    private Integer annio;
}
