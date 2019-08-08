package mrg;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DebugHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.RolloverFileOutputStream;
import org.eclipse.jetty.util.resource.Resource;
import org.jetbrains.annotations.NotNull;

import java.net.URL;

@SuppressWarnings("Duplicates")
public final class HandlersDebug {

    public static void main(@NotNull String[] args) throws Exception {
        final Server server = new Server();
        final HttpConfiguration httpConfig = new HttpConfiguration();
        final HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConfig);
        final ServerConnector serverConnector = new ServerConnector(server, httpConnectionFactory);
        serverConnector.setHost("localhost");
        serverConnector.setPort(3466);
        server.setConnectors(new Connector[]{serverConnector});

        final ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath("/*");
        final RolloverFileOutputStream outputStream = new RolloverFileOutputStream("log/yyyy_mm_dd.request.log", true,10);
        final DebugListener listener = new DebugListener(outputStream, false, true, true);
        contextHandler.addEventListener(listener);

        final ResourceHandler resourceHandler = new ResourceHandler();
        final URL resource = HandlersResource.class.getResource("/static");
        resourceHandler.setBaseResource(Resource.newResource(resource.toExternalForm()));
        resourceHandler.setDirectoriesListed(false);
        contextHandler.setHandler(resourceHandler);
        server.setHandler(contextHandler);

        server.start();
    }
}
