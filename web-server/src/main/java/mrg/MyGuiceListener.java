package mrg;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

public final class MyGuiceListener extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule(){
            @Override
            protected void configureServlets() {
                bind(MyServlet.class).asEagerSingleton();

                serve("/time").with(MyServlet.class);
            }
        });
    }
}
