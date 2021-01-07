package main;

import musicdata.Playlist;
import spotify.SpotifyConnector;
import vk.VkСonnector;

public class Main {
    private  static String urlPlaylistTest = "https://vk.com/music/playlist/154428962_150_acc85b2332c2da30ff";

    public static void main(String[] args) {
        try {
            Playlist playlist = new Playlist();
            VkСonnector.setTrackVkPlaylist(playlist, urlPlaylistTest);
            new SpotifyConnector().runSpotifyImporter(playlist);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}