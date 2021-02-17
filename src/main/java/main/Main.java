package main;

import WebServices.WebServer;
import musicdata.Playlist;
import spotify.SpotifyConnector;

public class Main {
    private static void startStream(Playlist playlist, SpotifyConnector spotifyConnector) {
        new Thread(new WebServer(playlist, spotifyConnector)).start();
    }

    public static void main(String[] args) {
        try {
            Playlist playlist = new Playlist();
            SpotifyConnector spotifyConnector = new SpotifyConnector();

            startStream(playlist, spotifyConnector);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}