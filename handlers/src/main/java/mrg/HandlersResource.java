package mrg;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.jetbrains.annotations.NotNull;

import java.net.URL;

@SuppressWarnings("Duplicates")
public final class HandlersResource {

    public static void main(@NotNull String[] args) throws Exception {
        final Server server = new DefaultServer().build(3466);

        final ResourceHandler resourceHandler = new ResourceHandler();

        final URL resource = HandlersResource.class.getResource("/static");
        resourceHandler.setBaseResource(Resource.newResource(resource.toExternalForm()));
        resourceHandler.setDirectoriesListed(false);

        final ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath("/*");
        contextHandler.setHandler(resourceHandler);
        server.setHandler(contextHandler);

        server.start();
    }
}
