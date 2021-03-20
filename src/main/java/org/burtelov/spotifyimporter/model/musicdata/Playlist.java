package org.burtelov.spotifyimporter.model.musicdata;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component()
public class Playlist {
    private ArrayList<Track> arrTracks;
    private String title;
    private String id;
    private String url;

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

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void addTrack(Track track) {
        arrTracks.add(track);
    }
}
