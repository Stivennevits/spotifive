package com.quipux.spotifive.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PLAY_LIST_SONG")
public class PlayListSongRecord {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLAY_LIST_SONG_SEQ")
    @SequenceGenerator(
            name = "PLAY_LIST_SONG_SEQ",
            sequenceName = "PLAY_LIST_SONG_SEQ",
            initialValue = 1,
            allocationSize = 1
    )
    @Id
    private Long id;
    private Long playListId;
    private Long songId;
}
