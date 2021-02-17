<<<<<<< HEAD
package WebServices;

import musicdata.Playlist;
import spotify.SpotifyConnector;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllRequestsServlet extends HttpServlet {
    private WebServer webServer;
    private Playlist playlist;


    public AllRequestsServlet(Playlist playlist) {
        this.playlist = playlist;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try{
            String code = request.getParameter("code");

            response.getWriter().println(code);
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            System.out.println(code);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getParameter("url");
        System.out.println(request.getParameter("url"));

        if (url == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        else {
            playlist.setUrlVk(url);
        }

        response.setContentType("text/html;charset=utf-8");
        System.out.println(url);
    }
=======
package WebServices;

import musicdata.Playlist;
import spotify.SpotifyConnector;
import vk.VkСonnector;

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
        VkСonnector.setTrackVkPlaylist(playlist, playlist.getUrl());

        spotifyConnector.getCodeUrl();

        response.setContentType("text/html;charset=utf-8");
    }
>>>>>>> fix
}