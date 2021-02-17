package WebServices;

import musicdata.Playlist;
import spotify.SpotifyConnector;
import vk.VkConnector;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllRequestsServlet extends HttpServlet {
    private WebServer webServer;
    private Playlist playlist;
    private SpotifyConnector spotifyConnector;

    public void setSpotifyConnector(SpotifyConnector spotifyConnector) {
        this.spotifyConnector = spotifyConnector;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try{
            String code = request.getParameter("code");

            spotifyConnector.setCode(code);
            spotifyConnector.runSpotifyImporter(playlist);

            response.getWriter().println(code);
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String message = request.getParameter("url");

        playlist.setUrl(request.getParameter("url"));

        System.out.println("RUN POST VKConn");
        VkConnector.setTrackVkPlaylist(playlist, playlist.getUrl());

        spotifyConnector.getCodeUrl();

        response.setContentType("text/html;charset=utf-8");
    }
}