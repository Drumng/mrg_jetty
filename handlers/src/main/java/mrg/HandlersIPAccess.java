package mrg;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.InetAccessHandler;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("Duplicates")
public class HandlersIPAccess {

    public static void main(@NotNull String[] args) throws Exception {
        final Server server = new DefaultServer().build(3466);

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
