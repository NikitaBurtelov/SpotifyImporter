package spotify;

import WebServices.WebServer;
import musicdata.Playlist;
import musicdata.Track;
import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.util.Iterator;

public class SpotifyConnector {
    private static String code;
    private final String client_id = getJsonObject("client_id");
    private final String user_id = getJsonObject("user_id");
    private final String client_secret = getJsonObject("client_secret");
    private final String redirect_uri = getJsonObject("redirect_uri");
    private String accessToken;

    public static void refreshToken(String token, String id) {
        JsoupRequest.updateToken(token, id);
    }

    private static String getJsonObject(String key) {
        try {
            return ((JSONObject) (new JSONParser()).parse(new FileReader("src/main/resources/data/spotify_token.json"))).get(key).toString();

        } catch (IOException | ParseException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            return null;
        }
    }

    private void getIdTrackSpotify(Playlist playlist) {
        Iterator<Track> trackIterator = playlist.getArrTrack().iterator();
        String idTrack;

        while (trackIterator.hasNext()) {
            Track track = trackIterator.next();

            idTrack = JsoupRequest.requestId("track:"+track.getTitle()+" artist:"+track.getArtist(), accessToken);

            if (idTrack != null)
                track.setIdSpotify(idTrack);
            else
                trackIterator.remove();
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

    private void startStream(Playlist playlist) {
        new Thread(new WebServer(playlist)).start();
    }

    private static void buffRead() {
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            code = reader.readLine();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    //Spotify url
    private void getCodeUrl() {
        String url = new URIBuilder().setScheme("https")
                .setHost("accounts.spotify.com")
                .setPath("/authorize")
                .addParameter("client_id", client_id)
                .addParameter("response_type","code")
                .addParameter("scope","playlist-modify-public playlist-modify-private")
                .addParameter("redirect_uri","http://localhost:8888/callback").toString();

        System.out.println(url);
    }

    private void getCode() throws IOException {
        Document doc = Jsoup.connect("http://localhost:8888/callback")
                .referrer("http://www.google.com")
                .get();

        System.out.println(doc.select("*"));
    }

    public void runSpotifyImporter(Playlist playlist) {
        try {
            startStream(playlist); //run server. localhost. port 8888
            getCodeUrl(); //get Spotify url + authorization
            buffRead();  //input code
            accessToken = JsoupRequest.getToken(code, client_id, client_secret);
            getIdTrackSpotify(playlist);
            createPlaylist(playlist);
            addItems(playlist);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}