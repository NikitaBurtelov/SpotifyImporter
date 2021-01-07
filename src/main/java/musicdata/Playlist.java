package musicdata;

import java.util.ArrayList;

public class Playlist {
    private ArrayList<Track> arrTracks;
    private String title;
    private String id;

    public Playlist(){
        arrTracks = new ArrayList<>();
    }

    public ArrayList<Track> getArrTrack() {
        return arrTracks;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public void addTrack(Track track) {
        arrTracks.add(track);
    }

    public void removeTrack(Track track) {arrTracks.remove(track);}
}
