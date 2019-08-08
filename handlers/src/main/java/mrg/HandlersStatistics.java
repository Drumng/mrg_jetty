package mrg;

import io.prometheus.client.jetty.JettyStatisticsCollector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("Duplicates")
public final class HandlersStatistics {

    public static void main(@NotNull String[] args) throws Exception {
        final Server server = new DefaultServer().build(3466);

        final ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath("/*");
        StatisticsHandler stats = new StatisticsHandler();
        contextHandler.setHandler(stats);
        new JettyStatisticsCollector(stats).register();
        server.setHandler(contextHandler);

        server.start();
    }
}
