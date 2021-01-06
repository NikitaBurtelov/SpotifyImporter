package main;

import musicdata.Playlist;
import musicdata.Track;
import spotify.SpotifyConnector;
import vk.VkСonnector;

import java.net.URI;

public class Main {
    public static void main(String[] args) {
        try {
            new SpotifyConnector().runSpotifyImporter();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
