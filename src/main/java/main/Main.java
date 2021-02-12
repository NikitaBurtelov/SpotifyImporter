package main;

import musicdata.Playlist;
import spotify.SpotifyConnector;
import vk.VkСonnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private  static String urlPlaylistTest = "https://vk.com/music/playlist/154428962_177_49fc960dd08869a9af";

    public static void main(String[] args) {  
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Playlist playlist = new Playlist();
            VkСonnector.setTrackVkPlaylist(playlist, urlPlaylistTest); //заглушка
            //VkСonnector.setTrackVkPlaylist(playlist, reader.readLine()); //version input url
            //JsoupRequest.requestUploadImage("");
            new SpotifyConnector().runSpotifyImporter(playlist);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}