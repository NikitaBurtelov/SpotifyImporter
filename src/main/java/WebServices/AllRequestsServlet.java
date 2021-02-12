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
}