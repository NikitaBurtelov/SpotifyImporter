package spotify;

import com.wrapper.spotify.SpotifyApi;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.net.URI;

public class SpotifyConnector {
    public void getToken() {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId("")
                .setClientSecret("")
                .setRedirectUri(URI.create("http://localhost:8888/callback"))
                .build();
    }
}

//http://localhost:8888/callback