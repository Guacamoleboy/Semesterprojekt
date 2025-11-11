// Package
package dk.project.controller;

// Imports
import dk.project.server.ThymeleafSetup;
import io.javalin.Javalin;
import java.util.Map;

public class PageController {

    // Attributes

    // _______________________________________________

    public static void registerRoutes(Javalin app) {

        app.get("/", ctx -> ctx.html(ThymeleafSetup.render("index.html", null)));
        app.get("/beregn", ctx -> ctx.html(ThymeleafSetup.render("beregn-intro.html", null)));
        app.get("/beregn/app", ctx -> ctx.html(ThymeleafSetup.render("beregn.html", null)));

    }

} // PageController end