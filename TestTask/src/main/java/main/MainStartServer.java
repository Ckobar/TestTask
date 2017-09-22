package main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlet.ServletForEntry;
import servlet.ServletForRegistration;

import javax.servlet.Servlet;

/**
 * Created by Алексей on 22.09.2017.
 * @author Alexey V.
 */

/**
 * Create and run jetty-server
 * on port 8080
 */
public class MainStartServer {
    private static int PORT = 8080;

    private static UserProfile ADMIN_USER = new UserProfile("admin","admin","anminSOBAKAadmin.ad");
    private static UserProfile TEST_USER = new UserProfile("test","test","test@test.test");

    public static void main(String[] args) throws Exception {

        AccountService accountService = new AccountService();
        accountService.addUser("admin",ADMIN_USER);
        accountService.addUser("test",TEST_USER);

        Servlet servletForEntry = new ServletForEntry(accountService);
        Servlet servletForRegistration = new ServletForRegistration(accountService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(servletForRegistration), "/registration");
        context.addServlet(new ServletHolder(servletForEntry), "/entry");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{"index.html"});

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(PORT);
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
