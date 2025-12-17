package com.quipux.spotifive.common.constants;


public class ErrorMessages {

    private ErrorMessages() {
        throw new IllegalStateException("ErrorMessages");
    }

    public static final String HANDLER_UNKNOWN_ERROR = "error.unknown";
    public static final String HANDLER_UNAUTHORIZED_ERROR = "error.unauthorized";
    public static final String ERROR_CALLING_SPOTIFY_TOKEN = "error.calling.spotify.token";
}