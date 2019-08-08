package mrg;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.InetAccessHandler;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("Duplicates")
public class HandlersIPAccess {

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
        InetAccessHandler inetAccessHandler = new InetAccessHandler();
        inetAccessHandler.exclude("localhost");
        inetAccessHandler.setHandler(new DefaultHandler());
        contextHandler.setHandler(inetAccessHandler);
        server.setHandler(contextHandler);

        server.start();
    }
}
