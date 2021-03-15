package webServices;

import musicdata.Playlist;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spotify.SpotifyConnector;

import java.util.logging.Logger;

@Component
public class WebServer implements Runnable {
    static final int port = 8888;
    private final AllRequestsServlet allRequestsServlet;

    @Autowired
    public WebServer(AllRequestsServlet allRequestsServlet) {
        this.allRequestsServlet = allRequestsServlet;
    }

    private void launch() {
        try {
            ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
            contextHandler.addServlet(new ServletHolder(allRequestsServlet), "/callback");

            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setResourceBase("templates");

            HandlerList handlerList = new HandlerList();
            handlerList.setHandlers(new Handler[]{resourceHandler, contextHandler});

            org.eclipse.jetty.server.Server server = new Server(port);
            server.setHandler(handlerList);

            server.start();
            Logger.getGlobal().info("Server started " + port);
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
