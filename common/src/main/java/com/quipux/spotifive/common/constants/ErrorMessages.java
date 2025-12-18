package com.quipux.spotifive.common.constants;


public class ErrorMessages {

    private ErrorMessages() {
        throw new IllegalStateException("ErrorMessages");
    }
    public static final String HANDLER_UNKNOWN_ERROR = "error.unknown";
    public static final String HANDLER_UNAUTHORIZED_ERROR = "error.unauthorized";
    public static final String ERROR_CALLING_SPOTIFY_TOKEN = "error.calling.spotify.token";
    public static final String GENRE_NOT_FOUND = "error.genre.not.found";
    public static final String SONG_NOT_FOUND_BY_TITLE = "error.song.not.found.by.title";
    public static final String SONG_ALREADY_EXISTS_BY_TITLE_AND_ARTIST = "error.song.already.exists.by.title.and.artist";
    public static final String USER_INVALID_PASSWORD = "error.user.invalid.password";
    public static final String USER_ALREADY_EXISTS_BY_EMAIL = "error.user.already.exists.by.email" ;
    public static final String USER_ALREADY_EXISTS_BY_USERNAME = "error.user.already.exists.by.username" ;
    public static final String INCORRECT_USERNAME_OR_PASSWORD = "error.incorrect.username.or.password";
    public static final String INVALID_TOKEN = "error.invalid.token";
    public static final String TOKEN_UNAUTHORIZED = "error.token.unauthorized";


}