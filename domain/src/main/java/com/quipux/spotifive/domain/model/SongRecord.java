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
@Table(name = "SONG")
public class SongRecord {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SONG_SEQ")
    @SequenceGenerator(
            name = "SONG_SEQ",
            sequenceName = "SONG_SEQ",
            initialValue = 1,
            allocationSize = 1
    )
    @Id
    private Long id;
    private String title;
    private String artist;
    private String album;
    private Integer annio;
    private String genre;
}
