package mrg;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.jetbrains.annotations.NotNull;

import java.net.URL;

@SuppressWarnings("Duplicates")
public final class ServletsDefault {

    public static void main(@NotNull String[] args) throws Exception {
        final Server server = new DefaultServer().build(3466);

        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.NO_SESSIONS
        );
        context.setContextPath("/file");
        final URL resource = ServletsDefault.class.getResource("/static");
        context.setBaseResource(Resource.newResource(resource.toExternalForm()));
        context.setWelcomeFiles(new String[]{"/static/example"});
        context.addServlet(new ServletHolder("default",
                DefaultServlet.class), "/example");
        server.setHandler(context);

        server.start();
    }
}
