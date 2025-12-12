// Package
package dk.project.server;

// Imports
import dk.project.server.routing.Routing;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class Server {

    // Attributes
    private Javalin app;

    // _______________________________________________

    public void start(int port) {

        // Static Files
        app = Javalin.create(config -> {

            // Static files
            config.staticFiles.add(staticFiles -> {
                staticFiles.location = Location.CLASSPATH;
                staticFiles.directory = "/static";
            });

            // Dynamic server rendered files -> deployment -> fog -> content
            config.staticFiles.add(staticFiles -> {
                staticFiles.directory = "content";
                staticFiles.hostedPath = "/content";
                staticFiles.location = Location.EXTERNAL;
            });
        }).start(port);

        // Routing
        Routing.registerRoutes(app);

    }

    // _______________________________________________

    public void stop() {
        if (app != null) app.stop();
    }

} // Server end