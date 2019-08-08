package mrg;

import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.QoSFilter;
import org.eclipse.jetty.util.resource.Resource;
import org.jetbrains.annotations.NotNull;

import javax.servlet.DispatcherType;
import java.net.URL;
import java.util.EnumSet;

public final class Launcher {

    public static void main(@NotNull String[] args) throws Exception {
        final Server server = new Server();
        final HttpConfiguration httpConfig = new HttpConfiguration();
        final HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConfig);
        final ServerConnector serverConnector = new ServerConnector(server, httpConnectionFactory);
        serverConnector.setHost("localhost");
        serverConnector.setPort(3466);
        server.setConnectors(new Connector[]{serverConnector});

        final ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();

        ServletContextHandler defaultContext = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        defaultContext.setContextPath("/file");
        final URL resource = LoginService.class.getResource("/static");
        defaultContext.setBaseResource(Resource.newResource(resource.toExternalForm()));
        defaultContext.setWelcomeFiles(new String[]{"/static/example"});

        defaultContext.addServlet(new ServletHolder("default", DefaultServlet.class), "/");

        final QoSFilter filter = new QoSFilter();
        final FilterHolder filterHolder = new FilterHolder(filter);
        filterHolder.setInitParameter("maxRequests", "1");
        defaultContext.addFilter(filterHolder, "/*", EnumSet.of(DispatcherType.REQUEST));

        contextHandlerCollection.addHandler(defaultContext);


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        context.addEventListener(new MyGuiceListener());
        context.addFilter(GuiceFilter.class, "/time", EnumSet.of(DispatcherType.REQUEST));
        contextHandlerCollection.addHandler(context);


        final String hash_config = Launcher.class.getResource("/hash_config").toExternalForm();
        final HashLoginService hashLoginService = new HashLoginService("login", hash_config);
        final ConstraintSecurityHandler securityHandler = new SecurityHandlerBuilder().build(server, hashLoginService);
        securityHandler.setHandler(contextHandlerCollection);
        server.setHandler(securityHandler);

        server.start();
    }
}
