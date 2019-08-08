package mrg;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.jetbrains.annotations.NotNull;

import javax.servlet.DispatcherType;
import java.net.URL;
import java.util.EnumSet;

@SuppressWarnings("Duplicates")
public final class FiltersMy {

    public static void main(@NotNull String[] args) throws Exception {
        final Server server = new DefaultServer().build(3466);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        final URL resource = FiltersQoS.class.getResource("/static");
        context.setBaseResource(Resource.newResource(resource.toExternalForm()));
        context.setWelcomeFiles(new String[]{"/static/example"});
        context.addServlet(new ServletHolder("default", DefaultServlet.class), "/*");

        final MyFilter filter = new MyFilter();
        final FilterHolder filterHolder = new FilterHolder(filter);
        context.addFilter(filterHolder, "/*", EnumSet.of(DispatcherType.REQUEST));
        server.setHandler(context);

        server.start();
    }
}
