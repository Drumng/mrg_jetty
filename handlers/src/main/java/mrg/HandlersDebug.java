package mrg;

import org.eclipse.jetty.server.DebugListener;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.RolloverFileOutputStream;
import org.eclipse.jetty.util.resource.Resource;
import org.jetbrains.annotations.NotNull;

import java.net.URL;

@SuppressWarnings("Duplicates")
public final class HandlersDebug {

    public static void main(@NotNull String[] args) throws Exception {
        final Server server = new DefaultServer().build(3466);

        final ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath("/*");
        final RolloverFileOutputStream outputStream = new RolloverFileOutputStream("log/yyyy_mm_dd.request.log", true, 10);
        final DebugListener listener = new DebugListener(outputStream, false, true, true);
        contextHandler.addEventListener(listener);

        final ResourceHandler resourceHandler = new ResourceHandler();
        final URL resource = HandlersResource.class.getResource("/static");
        resourceHandler.setBaseResource(Resource.newResource(resource.toExternalForm()));
        resourceHandler.setDirectoriesListed(false);
        contextHandler.setHandler(resourceHandler);
        server.setHandler(contextHandler);

        server.start();
    }
}
