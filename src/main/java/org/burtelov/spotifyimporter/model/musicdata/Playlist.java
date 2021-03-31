package org.burtelov.spotifyimporter.model.musicdata;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

@Component()
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Playlist {
    private ArrayList<Track> arrTracks;
    private String title;
    private String id;

    @NotEmpty(message = "Url must not be empty")
    @URL
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

    public void clearArr() {
        arrTracks.clear();
    }
}
