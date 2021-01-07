package WebServices;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import spotify.SpotifyConnector;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

//Тестовый вариант
public class WebServer implements Runnable {
    static final int port = 8888;

    private void launch() {
        try {
            AllRequestsServlet allRequestsServlet = new AllRequestsServlet();
            ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
            contextHandler.addServlet(new ServletHolder(allRequestsServlet), "/callback");
            org.eclipse.jetty.server.Server server = new Server(port);
            server.setHandler(contextHandler);

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
