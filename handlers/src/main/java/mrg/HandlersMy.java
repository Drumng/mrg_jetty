package mrg;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("Duplicates")
public final class HandlersMy {

    public static void main(@NotNull String[] args) throws Exception {
        final Server server = new DefaultServer().build(3466);

        final ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath("/*");
        contextHandler.setHandler(new MyHandler());
        server.setHandler(contextHandler);

        server.start();
    }
}
