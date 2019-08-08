package mrg;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.jetbrains.annotations.NotNull;

public final class Client {

    public static void main(@NotNull String[] args) throws Exception {
        HttpClient httpClient = new HttpClient();

        httpClient.setFollowRedirects(false);

        httpClient.start();

        final ContentResponse get = httpClient.GET("http://localhost:3466/example");
        System.out.println( new String(get.getContent()));
    }
}
