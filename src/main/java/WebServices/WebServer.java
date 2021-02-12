package WebServices;

import musicdata.Playlist;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import spotify.SpotifyConnector;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

//Тестовый вариант
public class WebServer implements Runnable {
    static final int port = 8888;
    private Playlist playlist;
    public  WebServer(Playlist playlist) {
        this.playlist = playlist;
    }

    private void launch() {
        try {
            AllRequestsServlet allRequestsServlet = new AllRequestsServlet(playlist);
            ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
            contextHandler.addServlet(new ServletHolder(allRequestsServlet), "/callback");

            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setResourceBase("templates");

            HandlerList handlerList = new HandlerList();
            handlerList.setHandlers(new Handler[]{resourceHandler, contextHandler});

            org.eclipse.jetty.server.Server server = new Server(port);
            server.setHandler(handlerList);

            server.start();
            Logger.getGlobal().info("Server started");
            server.join();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void run() {
        launch();
        System.out.println("Launch");
    }
}
