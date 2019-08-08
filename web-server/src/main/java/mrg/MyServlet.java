package mrg;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class MyServlet extends HttpServlet {
    private final @NotNull ContentGenerator contentGenerator;
    private @Nullable ServletConfig servletConfig;

    @Inject
    public MyServlet(@NotNull ContentGenerator contentGenerator) {
        this.contentGenerator = contentGenerator;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.servletConfig = config;
    }

    @Override
    public @Nullable ServletConfig getServletConfig() {
        return servletConfig;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        try (ServletOutputStream outputStream = res.getOutputStream()) {
            outputStream.write(contentGenerator.content().getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }
    }

    @Override
    public String getServletInfo() {
        return "my Servlet";
    }

    @Override
    public void destroy() {
    }
}
