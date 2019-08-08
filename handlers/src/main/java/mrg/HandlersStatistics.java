package mrg;

import io.prometheus.client.jetty.JettyStatisticsCollector;
import org.eclipse.jetty.io.ConnectionStatistics;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("Duplicates")
public final class HandlersStatistics {

    public static void main(@NotNull String[] args) throws Exception {
        final Server server = new Server();
        final HttpConfiguration httpConfig = new HttpConfiguration();
        final HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConfig);
        final ServerConnector serverConnector = new ServerConnector(server, httpConnectionFactory);
        serverConnector.setHost("localhost");
        serverConnector.setPort(3466);
        serverConnector.addBean(new ConnectionStatistics());
        server.setConnectors(new Connector[]{serverConnector});

        final ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath("/*");
        StatisticsHandler stats = new StatisticsHandler();
        contextHandler.setHandler(stats);
        new JettyStatisticsCollector(stats).register();
        server.setHandler(contextHandler);

        server.start();
    }
}
