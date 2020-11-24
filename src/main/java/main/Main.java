package main;

import musicdata.Playlist;
import musicdata.Track;
import vk.VkСonnector;

public class Main {
    public static void main(String[] args) {
        Playlist playlist = new Playlist();
        VkСonnector.getTrackVk(playlist, "https://vk.com/music/playlist/154428962_168_42d3cf8c5e9144a844");

        for(Track track : playlist.getArrTrack()) {
            System.out.printf("%s - %s\n", track.title, track.artist);
        }
    }
}
