// Package
package dk.project.controller;

// Imports
import dk.project.server.ThymeleafSetup;
import io.javalin.Javalin;

public class PageController {

    // Attributes

    // _______________________________________________

    public static void registerRoutes(Javalin app) {

        app.get("/", ctx -> ctx.html(ThymeleafSetup.render("index.html", null)));
        app.get("/menu", ctx -> ctx.html(ThymeleafSetup.render("adminMenu.html", null)));
        app.get("/beregn", ctx -> ctx.html(ThymeleafSetup.render("beregn-intro.html", null)));
        app.get("/beregn/app", ctx -> ctx.html(ThymeleafSetup.render("beregn.html", null)));
        app.get("/beregn/modtag", ctx -> ctx.html(ThymeleafSetup.render("modtag.html", null)));
        app.get("/error", ctx -> ctx.html(ThymeleafSetup.render("error.html", null)));

    }

} // PageController end