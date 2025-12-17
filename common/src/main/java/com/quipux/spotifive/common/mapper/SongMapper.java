package com.quipux.spotifive.common.mapper;

import com.quipux.spotifive.common.request.SongRequest;
import com.quipux.spotifive.domain.model.SongRecord;

public class SongMapper {
    private SongMapper() {
        throw new IllegalStateException("SongMapper");
    }

    public static SongRecord mapToCreate(SongRequest request) {
        SongRecord songRecord = new SongRecord();
        songRecord.setTitle(request.getTitle());
        songRecord.setArtist(request.getArtist());
        songRecord.setAlbum(request.getAlbum());
        songRecord.setAnnio(request.getAnnio());
        songRecord.setGenre(request.getGenre());
        return songRecord;
    }
}
