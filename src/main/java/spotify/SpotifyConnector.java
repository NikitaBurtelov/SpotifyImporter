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

    private void getCodeUrl() {
        String url = new URIBuilder().setScheme("https")
                .setHost("accounts.spotify.com")
                .setPath("/authorize")
                .addParameter("client_id", "c700db30083545a6a05352d0304a0597")
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

    public void getToken() {
        try {
            Map<String, String> dataCURL = new HashMap<>();
            dataCURL.put("grant_type", "authorization_code");
            dataCURL.put("code", code);
            dataCURL.put("redirect_uri", redirect_uri);
            dataCURL.put("client_id", client_id);
            dataCURL.put("client_secret", client_secret);

            Document doc = Jsoup.connect("https://accounts.spotify.com/api/token")
                    .header("Content-Type","application/x-www-form-urlencoded")
                    .data(dataCURL)
                    .referrer("http://www.google.com")
                    .post();

            System.out.println(doc);

            /*
            System.out.println(convertWithIteration(dataCURL));

            String command = "curl -H \"Content-Type: application/x-www-form-urlencoded\" -d ";
            System.out.println(command + convertWithIteration(dataCURL) + " -X POST https://accounts.spotify.com/api/token");
            //command + convertWithIteration(dataCURL) + " -X POST https://accounts.spotify.com/api/token"
            Process process = Runtime.getRuntime().exec("curl www.google.com");
            System.out.println(process);
            System.out.println("\n");
            process.getInputStream();
            process.getOutputStream();
            process.destroy();*/
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public String convertWithIteration(Map<String, String> map) {
        StringBuilder mapAsString = new StringBuilder("\"");
        for (String key : map.keySet()) {
            mapAsString.append(key + "=" + map.get(key) + "&");
        }
        mapAsString.delete(mapAsString.length()-2, mapAsString.length()).append("\"");
        return mapAsString.toString();
    }

    public void runSpotifyImporter() {
        try {
            startStream();
            getCodeUrl();
            buffRead();
            //getCode();
            getToken();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}


//http://localhost:8888/callback
//https://accounts.spotify.com/authorize
//https://accounts.spotify.com/api/token
/*
* {
   "access_token": "NgCXRK...MzYjw",
   "token_type": "Bearer",
   "scope": "user-read-private user-read-email",
   "expires_in": 3600,
   "refresh_token": "NgAagA...Um_SHo"
}
* */