package org.burtelov.spotifyimporter.model.spotify;

import org.apache.http.client.utils.URIBuilder;
import org.burtelov.spotifyimporter.model.musicdata.Playlist;
import org.burtelov.spotifyimporter.model.musicdata.Track;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Component
public class SpotifyConnector {
    private String code;

    @Value("${client_id}")
    private String client_id;
    @Value("${user_id}")
    private String user_id;
    @Value("${client_secret}")
    private String client_secret;
    @Value("${redirect_uri}")
    private String redirect_uri;
    private static String accessToken;

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    private void getIdTrackSpotify(Playlist playlist) {
        List<Track> trackList = playlist.getArrTrack();

        for (Track track : trackList) {
<<<<<<< HEAD
            track.setIdSpotify(JsoupRequest.requestId(track.getTitle() +
                    " " + track.getArtist(), accessToken));
=======
            track.setIdSpotify(JsoupRequest.requestId(track.getTitle()
                    + " " + track.getArtist(), accessToken));
>>>>>>> 9da7c6f48c0e1dccdeea5c2fbd275585596c0bfe
        }
    }

    private void createPlaylist(Playlist playlist) {
        JsoupRequest.requestCreatePlaylist(playlist, accessToken, user_id);
    }

    private void addItems(Playlist playlist) {
        StringBuilder uris = new StringBuilder();
        for (Track track : playlist.getArrTrack())
            uris.append("\"spotify:track:").append(track.getIdSpotify()).append("\",");

        if (uris.length() > 0)
            uris.deleteCharAt(uris.length() - 1);

        JsoupRequest.requestAddItems(playlist.getId(), uris.toString(), accessToken);
    }

    //private void startStream() {
    //  new Thread(new WebServer()).start();
    //}

    /*private static void buffRead() {
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            code = reader.readLine();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }*/
    //Spotify url
    public String getCodeUrl() {
        String url = new URIBuilder().setScheme("https")
                .setHost("accounts.spotify.com")
                .setPath("/authorize")
                .addParameter("client_id", client_id)
                .addParameter("response_type","code")
                .addParameter("scope","playlist-modify-public playlist-modify-private ugc-image-upload")
                .addParameter("redirect_uri","http://localhost:8888/callback").toString();

        System.out.println(url);

        return url;
    }

    public void runSpotifyImporter(Playlist playlist) {
        try {
            //startStream(); //run server. localhost. port 8888
            //getCodeUrl(); //get Spotify url + authorization
            //buffRead();  //input code
            accessToken = JsoupRequest.getToken(code, client_id, client_secret);
            getIdTrackSpotify(playlist);
            createPlaylist(playlist);
            addItems(playlist);
<<<<<<< HEAD
            JsoupRequest.requestUploadImage(playlist.getId(), accessToken);
=======
            playlist.clearArr();
>>>>>>> 9da7c6f48c0e1dccdeea5c2fbd275585596c0bfe
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}