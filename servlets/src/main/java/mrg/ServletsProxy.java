package mrg;

import org.eclipse.jetty.proxy.ConnectHandler;
import org.eclipse.jetty.proxy.ProxyServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("Duplicates")
public final class ServletsProxy {

    public static void main(@NotNull String[] args) throws Exception {
        final Server server = new DefaultServer().build(3467);
        ConnectHandler proxy = new ConnectHandler();
        server.setHandler(proxy);

        ServletContextHandler context = new ServletContextHandler(proxy, "/*", ServletContextHandler.NO_SESSIONS);
        ServletHolder proxyServlet = new ServletHolder(ProxyServlet.Transparent.class);
        proxyServlet.setInitParameter("proxyTo", "http://localhost:3466/");
        proxyServlet.setInitParameter("prefix", "/");
        context.addServlet(proxyServlet, "/*");

        server.start();
    }
}
