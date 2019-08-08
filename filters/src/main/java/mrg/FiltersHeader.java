package mrg;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.HeaderFilter;
import org.eclipse.jetty.util.resource.Resource;

import javax.servlet.DispatcherType;
import java.net.URL;
import java.util.EnumSet;

@SuppressWarnings("Duplicates")
public final class FiltersHeader {

    public static void main(String[] args) throws Exception {
        final Server server = new DefaultServer().build(3466);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        final URL resource = FiltersHeader.class.getResource("/static");
        context.setBaseResource(Resource.newResource(resource.toExternalForm()));
        context.setWelcomeFiles(new String[]{"/static/example"});
        context.addServlet(new ServletHolder("default", DefaultServlet.class), "/*");

        final HeaderFilter filter = new HeaderFilter();
        final FilterHolder filterHolder = new FilterHolder(filter);
        filterHolder.setInitParameter("headerConfig",
                "set X-Frame-Options: DENY,\n" +
                "      \"add Cache-Control: no-cache, no-store, must-revalidate\",\n" +
                "      setDate Expires: 31540000000,\n" +
                "      addDate Date: 0"
        );
        context.addFilter(filterHolder, "/*", EnumSet.of(DispatcherType.REQUEST));

        server.setHandler(context);

        server.start();
    }
}
