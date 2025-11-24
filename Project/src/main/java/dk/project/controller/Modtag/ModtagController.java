// Package
package dk.project.controller.Modtag;

// Imports
import dk.project.server.ThymeleafSetup;
import io.javalin.Javalin;

public class ModtagController {

    // Attributes

    // __________________________________________

    public static void registerRoutes(Javalin app) {

        app.get("/beregn/modtag", ctx -> ctx.html(ThymeleafSetup.render("modtag.html", null)));

    }

} // ModtagController end