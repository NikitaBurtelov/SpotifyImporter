package spotify;

import WebServices.WebServer;
import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SpotifyConnector {
    private static String code;
    public boolean flag = false;
    private final String client_id = getJsonObject("client_id");
    private final String client_secret = getJsonObject("client_secret");
    private final String redirect_uri = getJsonObject("redirect_uri");

    private static String getJsonObject(String key) {
        try {
            return ((JSONObject) (new JSONParser()).parse(new FileReader("src/main/resources/data/spotify_token.json"))).get(key).toString();

        } catch (IOException | ParseException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            return null;
        }
    }

    private void startStream() {
        new Thread(new WebServer()).start();
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
                .addParameter("redirect_uri","http://localhost:8888/callback").toString();

        System.out.println(url);
    }

    private void getCode() throws IOException {
        Document doc = Jsoup.connect("http://localhost:8888/callback")
                .referrer("http://www.google.com")
                .get();

        System.out.println(doc.select("*"));
    }

    public void runSpotifyImporter() {
        try {
            startStream(); //run server. localhost. port 8888
            getCodeUrl(); //get Spotify url + authorization
            buffRead(); //input code

            System.out.println(JsoupRequest.getToken(code, client_id, client_secret)); //output token
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}