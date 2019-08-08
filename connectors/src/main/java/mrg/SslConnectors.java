package mrg;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.jetbrains.annotations.NotNull;

public final class SslConnectors {

    public static void main(@NotNull String[] args) throws Exception {
        final Server server = new Server();

        final HttpConfiguration httpConfig = new HttpConfiguration();

        final SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
        sslContextFactory.setKeyStoreResource(Resource.newClassPathResource("keystore"));
        sslContextFactory.setKeyStorePassword("qwerty");

        final SslConnectionFactory httpConnectionFactory = new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString());

        final ServerConnector serverConnector = new ServerConnector(server, httpConnectionFactory, new HttpConnectionFactory(httpConfig));

        serverConnector.setHost("localhost");
        serverConnector.setPort(3466);

        server.setConnectors(new Connector[]{serverConnector});

        server.start();
    }
}
