package mrg;

import org.eclipse.jetty.server.*;
import org.jetbrains.annotations.NotNull;

public final class DefaultServer {
    private final Server server = new Server();

    public @NotNull Server build(int port) {
        final HttpConfiguration httpConfig = new HttpConfiguration();
        final HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConfig);
        final ServerConnector serverConnector = new ServerConnector(server, httpConnectionFactory);
        serverConnector.setHost("localhost");
        serverConnector.setPort(port);
        server.setConnectors(new Connector[]{serverConnector});
        return server;
    }
}
