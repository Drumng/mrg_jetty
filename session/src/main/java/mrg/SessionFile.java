package mrg;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.session.DefaultSessionCache;
import org.eclipse.jetty.server.session.FileSessionDataStore;
import org.eclipse.jetty.server.session.SessionCache;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("Duplicates")
public final class SessionFile {

    public static void main(@NotNull String[] args) throws Exception {
        final Server server = new Server();
        final HttpConfiguration httpConfig = new HttpConfiguration();
        final HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConfig);
        final ServerConnector serverConnector = new ServerConnector(server, httpConnectionFactory);
        serverConnector.setHost("localhost");
        serverConnector.setPort(3466);
        server.setConnectors(new Connector[]{serverConnector});

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        final URL resource = SessionFile.class.getResource("/static");
        context.setBaseResource(Resource.newResource(resource.toExternalForm()));
        context.setWelcomeFiles(new String[]{"/static/example"});
        context.addServlet(new ServletHolder("default", DefaultServlet.class), "/*");

        final SessionHandler sessionHandler = fileSessionHandler();
        sessionHandler.setHandler(context);
        server.setHandler(sessionHandler);

        server.start();
    }

    private static  @NotNull SessionHandler fileSessionHandler() throws IOException, URISyntaxException {
        SessionHandler sessionHandler = new SessionHandler();
        SessionCache sessionCache = new DefaultSessionCache(sessionHandler);
        sessionCache.setSessionDataStore(fileSessionDataStore());
        sessionHandler.setSessionCache(sessionCache);
        sessionHandler.setHttpOnly(true);
        return sessionHandler;
    }

    private static  @NotNull FileSessionDataStore fileSessionDataStore() throws URISyntaxException, IOException {
        final Path basePath = Paths.get(SessionFile.class.getResource("/").toURI());
        final Path jettyPath = basePath.resolve("jetty");
        if (!Files.exists(jettyPath)) {
            Files.createDirectories(jettyPath);
        }

        FileSessionDataStore fileSessionDataStore = new FileSessionDataStore();
        fileSessionDataStore.setStoreDir(jettyPath.toFile());
        return fileSessionDataStore;
    }
}
