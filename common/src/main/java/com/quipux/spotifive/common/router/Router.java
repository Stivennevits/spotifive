package com.quipux.spotifive.common.router;

public class Router {
    private Router() {
        throw new IllegalStateException("Router");
    }

    private static final String API = "/quipix/spotifive";

    public static class SpotifyAPI {
        private SpotifyAPI() {
            throw new IllegalStateException("SpotifyAPI");
        }

        public static final String ROOT = API + "/play-list";
        public static final String TOKEN = "/test-token";

    }


}
