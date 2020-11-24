package musicdata;

import java.util.ArrayList;

public class Playlist {
    public ArrayList<Track> arrTracks;
    public String title;

    public ArrayList<Track> getArrTrack() {
        return arrTracks;
    }

    public void addTrack(Track track) {
        arrTracks.add(track);
    }
}
