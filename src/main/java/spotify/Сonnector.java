package spotify;

import musicdata.Playlist;
import musicdata.Track;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.text.html.parser.Parser;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Сonnector {
    private final String token = getJsonObject("spotify_token");

    private static String getJsonObject(String filePath) {
        try {
            return ((JSONObject) (new JSONParser()).parse(new FileReader(filePath))).get("token").toString();

        } catch (IOException | ParseException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            return null;
        }
    }

    public void parser(Playlist playlist) throws IOException {
        Document doc = (Document) Jsoup.connect("https://vk.com/music/playlist/154428962_168_42d3cf8c5e9144a844")
                .referrer("http://www.google.com")
                .get();
        ArrayList<String> arrTitle = new ArrayList<>();
        ArrayList<String> arrAuthor = new ArrayList<>();

        Elements listNews = doc.select("span.audio_row__title_inner._audio_row__title_inner");

        for (Element element : listNews.select("*")) {
            System.out.println(element.text());
            arrTitle.add(element.text());
        }

        listNews = doc.select("div.audio_row__performers");

        for (Element element : listNews.select("*")) {
            System.out.println(element.text());
            arrAuthor.add(element.text());
        }

        for (int i = 0; i < arrTitle.size(); i++) {
            playlist.addTrack(new Track() {});
        }

    }
}
