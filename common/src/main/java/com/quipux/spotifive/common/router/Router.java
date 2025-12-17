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

        public static final String ROOT = API + "/spotify-api";
        public static final String GENRES = "/genres";

    }

    public static class SongsAPI {
        private SongsAPI() {
            throw new IllegalStateException("SongsAPI");
        }

        public static final String ROOT = API + "/songs";
        public static final String PAGING = "/paging";

    }

    public static class PlayListAPI {
        private PlayListAPI() {
            throw new IllegalStateException("PlayListAPI");
        }

        public static final String ROOT = API + "/play-list";

    }


}
