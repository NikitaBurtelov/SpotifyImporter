package spotify;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import musicdata.Playlist;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.lang.NonNull;

import java.io.IOException;

public class JsoupRequest {

    public static void joinUrl() {
        try {
            Document doc = Jsoup.connect("https://accounts.spotify.com/authorize")
                    .header("Accept-Language", "en")
                    .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                    .data("grant_type", "code")
                    .data("client_id", "c700db30083545a6a05352d0304a0597")
                    .data("scope","playlist-modify-public+playlist-modify-private")
                    .data("redirect_uri", "redirect_uri=http%3A%2F%2Flocalhost%3A8888%2Fcallback")
                    .ignoreHttpErrors(true)
                    .ignoreContentType(true)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9"
                            + ",*/*;q=0.8")
                    .get();
            System.out.println(doc.text());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static String getToken(String code, String clientId, String clientSecret) {
        try {
            Document doc = Jsoup.connect("https://accounts.spotify.com/api/token")
                    .header("Accept-Language", "en")
                    .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                    .data("grant_type", "authorization_code")
                    .data("client_id", clientId)
                    .data("client_secret", clientSecret)
                    .data("code", code)
                    .data("redirect_uri", "http://localhost:8888/callback")
                    .ignoreHttpErrors(true)
                    .ignoreContentType(true)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9"
                            + ",*/*;q=0.8")
                    .post();

            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(doc.text());

            JsonObject root = jsonElement.getAsJsonObject();
            String token = root.get("access_token").toString();

            return token.substring(1, token.length() - 1);

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return new Exception().getMessage();
    }

    public static void requestUploadImage(String playlistId, String imageBase64, String accessToken) {
        try {
            Jsoup.connect("https://api.spotify.com/v1/playlists/"
                    + playlistId + "/images")
                    .method(Connection.Method.PUT)
                    .header("Accept-Language", "en")
                    .header("Content-Type", "application/json")
                    .ignoreHttpErrors(true)
                    .ignoreContentType(true)
                    .requestBody(imageBase64)
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + accessToken)
                    .execute()
                    .body();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static String updateToken(String refreshToken, String client_id) {
        try {
            Document doc = Jsoup.connect("https://accounts.spotify.com/api/token")
                    .header("Accept-Language", "en")
                    .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                    .data("grant_type", "refresh_token")
                    .data("refresh_token", refreshToken)
                    .data("client_id", client_id)
                    .ignoreHttpErrors(true)
                    .ignoreContentType(true)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9"
                            + ",*/*;q=0.8")
                    .post();
            return doc.text();
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static void requestAddItems(String playlistId, String uris, String accessToken) {
        try {
            Document doc = Jsoup.connect("https://api.spotify.com/v1/playlists/"
                    + playlistId + "/tracks")
                    .header("Accept-Language", "en")
                    .header("Content-Type", "application/json")
                    .method(Connection.Method.POST)
                    .ignoreHttpErrors(true)
                    .ignoreContentType(true)
                    .requestBody("{\"uris\": [" + uris + "]}")
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + accessToken)
                    .post();

            System.out.println("Playlist moved");

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void requestCreatePlaylist(Playlist playlist, String accessToken, String userId) {
        try {
            String doc = Jsoup.connect("https://api.spotify.com/v1/users/"+userId+"/playlists")
                    .header("Accept-Language", "en")
                    .header("Content-Type", "application/json")
                    .method(Connection.Method.POST)
                    .ignoreHttpErrors(true)
                    .ignoreContentType(true)
                    .requestBody("{" + "\"name\":" + "\"" +
                            playlist.getTitle() +
                            "\"," + "\"description\": \"\","
                            + "\"public\": true"+"}")
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + accessToken)
                    .execute()
                    .body();

            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(doc);

            JsonObject root = jsonElement.getAsJsonObject();
            String id = root.get("id").toString();

            playlist.setId(id.substring(1, id.length() - 1));

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static String requestId(String q, String accessToken) {
        try {
            Document doc = Jsoup.connect("https://api.spotify.com/v1/search")
                    .header("Accept-Language", "en")
                    .header("Content-Type", "application/json")
                    .data("q", q)
                    .data("type", "track")
                    .data("limit", "1")
                    .data("offset", "0")
                    .ignoreHttpErrors(true)
                    .ignoreContentType(true)
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + accessToken)
                    .get();

            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(doc.text());

            JsonObject root = jsonElement.getAsJsonObject();

            if (root.get("tracks").getAsJsonObject()
                    .get("items").getAsJsonArray().size() != 0) {

                String id = root.get("tracks").getAsJsonObject()
                        .get("items").getAsJsonArray().
                                get(0).getAsJsonObject().
                                get("id").toString();

                return id.substring(1, id.length() - 1);
            }
            else return null;

        } catch (IOException exception) {
            exception.printStackTrace();
            return exception.getMessage();
        }
    }
}
