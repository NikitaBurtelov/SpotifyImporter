package WebServices;

import musicdata.Playlist;
import spotify.JsoupRequest;
import spotify.SpotifyConnector;
import vk.VkConnector;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllRequestsServlet extends HttpServlet {
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
            response.sendRedirect("http://localhost:8888");
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        playlist.setUrl(request.getParameter("url"));
        JsoupRequest.joinUrl();
        VkConnector.setTrackVkPlaylist(playlist, playlist.getUrl());
        response.sendRedirect("http://localhost:8888");
        response.getWriter().println(spotifyConnector.getCodeUrl());
        response.setContentType("text/html;charset=utf-8");
    }
}