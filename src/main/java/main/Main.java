package main;

import webServices.WebServer;
import musicdata.Playlist;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spotify.SpotifyConnector;

@Configuration
@ComponentScan({"musicdata", "spotify", "webServices"})
public class Main {
    private static AnnotationConfigApplicationContext context;

    private static void startStream(Playlist playlist, SpotifyConnector spotifyConnector) {
        new Thread(context.getBean("webServer", WebServer.class)).start();
    }

    public static void main(String[] args) {
        try {
            context = new AnnotationConfigApplicationContext(Main.class);

            Playlist playlist = context.getBean("playlist", Playlist.class);
            SpotifyConnector spotifyConnector = context.getBean("spotifyConnector", SpotifyConnector.class);

            startStream(playlist, spotifyConnector);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}