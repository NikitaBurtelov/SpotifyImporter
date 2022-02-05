package org.spotify.importer.dto.users;

import org.burtelov.spotifyimporter.model.musicdata.Playlist;
import org.burtelov.spotifyimporter.model.spotify.SpotifyConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

/**
 * @author Nikita Burtelov
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class User {
    @NotEmpty(message = "Id must not be empty")
    private String id;
    private Playlist playlist;
    private SpotifyConnector spotifyConnector;

    public User(){

    }

    public String getId() {
        return id;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public SpotifyConnector getSpotifyConnector() {
        return spotifyConnector;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Autowired
    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    @Autowired
    public void setSpotifyConnector(SpotifyConnector spotifyConnector) {
        this.spotifyConnector = spotifyConnector;
    }
}
