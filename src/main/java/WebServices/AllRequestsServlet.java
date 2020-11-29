package WebServices;

import spotify.SpotifyConnector;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllRequestsServlet extends HttpServlet {
    private WebServer webServer;

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
        String message = request.getParameter("message");
        response.setContentType("text/html;charset=utf-8");
        System.out.println(message);
    }
}