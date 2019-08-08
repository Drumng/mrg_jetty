package mrg;

import org.eclipse.jetty.server.AsyncRequestLogWriter;
import org.eclipse.jetty.server.CustomRequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.eclipse.jetty.util.resource.Resource;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Logging {

    public static void main(@NotNull String[] args) throws Exception {
        final Server server = new DefaultServer().build(3466);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        final URL resource = Logging.class.getResource("/static");
        context.setBaseResource(Resource.newResource(resource.toExternalForm()));
        context.setWelcomeFiles(new String[]{"/static/example"});
        context.addServlet(new ServletHolder("default", DefaultServlet.class), "/*");
        server.setHandler(context);

        final Path logsPath = Paths.get(Logging.class.getResource("/").toURI());
        final Path jettyPath = logsPath.resolve("jetty");
        if (!Files.exists(jettyPath)) {
            Files.createDirectories(jettyPath);
        }

        final AsyncRequestLogWriter writer = new AsyncRequestLogWriter(
                jettyPath.toString() + '/' + "app.log",
                new BlockingArrayQueue<>(100)
        );
        writer.setAppend(true);
        writer.setRetainDays(7);
        final CustomRequestLog customRequestLog = new CustomRequestLog(writer, CustomRequestLog.NCSA_FORMAT);
        server.setRequestLog(customRequestLog);

        server.start();
    }
}
