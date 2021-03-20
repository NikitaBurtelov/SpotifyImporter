package org.burtelov.spotifyimporter.model.vk;

import org.burtelov.spotifyimporter.model.musicdata.Playlist;
import org.burtelov.spotifyimporter.model.musicdata.Track;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class VkConnector {
    private static String getJsonObject(String filePath) {
        try {
            return ((JSONObject) (new JSONParser()).parse(new FileReader(filePath))).get("token").toString();

        } catch (IOException | ParseException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            return null;
        }
    }

    public static void setTrackVkPlaylist(Playlist playlist, String urlPlaylist) {
        try {
            ArrayList<String> arrTitle = new ArrayList<>();
            ArrayList<String> arrAuthor = new ArrayList<>();
            Elements JsoupListTitle;
            Elements JsoupListAuthor;

            Document doc = Jsoup.connect(urlPlaylist)
                .referrer("http://www.google.com")
                .get();

            JsoupListTitle = doc.select("span.audio_row__title_inner._audio_row__title_inner");

            for (Element element : JsoupListTitle.select("*")) {
                arrTitle.add(element.text());
            }

            JsoupListAuthor = doc.select("div.audio_row__performers a:nth-child(1)");

            for (Element element : JsoupListAuthor.select("a")) {
                arrAuthor.add(element.text());
            }
            //add title, author
            for (int i = 0; i < arrTitle.size(); i++) {
                playlist.addTrack(new Track(arrTitle.get(i), arrAuthor.get(i)));
            }

            playlist.setTitle(doc.select("h1.AudioPlaylistSnippet__title--main").first().text());
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}