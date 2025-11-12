// Package
package dk.project.controller;

// Import
import io.javalin.http.Context;

public class ErrorController {

    // Attributes

    // ______________________________________________________________

    public static void handle400(Context ctx) {
        renderError(ctx, 400, "Forkert forespørgsel", "Din anmodning kunne ikke forstås. Prøv venligst igen.");
    }

    // ______________________________________________________________

    public static void handle401(Context ctx) {
        renderError(ctx, 401, "Ikke autoriseret", "Du har ikke adgang til denne side. Log ind for at fortsætte.");
    }

    // ______________________________________________________________

    public static void handle403(Context ctx) {
        renderError(ctx, 403, "Adgang nægtet", "Du har ikke de nødvendige rettigheder til at se denne side.");
    }

    // ______________________________________________________________

    public static void handle404(Context ctx) {
        renderError(ctx, 404, "Siden findes ikke", "Ups! Siden du leder efter, findes desværre ikke.");
    }

    // ______________________________________________________________

    public static void handle429(Context ctx) {
        renderError(ctx, 429, "For mange forespørgsler", "Du har sendt for mange anmodninger på kort tid. Prøv igen senere.");
    }

    // ______________________________________________________________

    public static void handle500(Context ctx) {
        renderError(ctx, 500, "Intern serverfejl", "Noget gik galt på serveren. Prøv igen senere.");
    }

    // ______________________________________________________________

    public static void handle503(Context ctx) {
        renderError(ctx, 503, "Service utilgængelig", "Tjenesten er midlertidigt utilgængelig. Prøv igen om lidt.");
    }

    // ______________________________________________________________

    private static void renderError(Context ctx, int status, String title, String message) {
        ctx.status(status);
        ctx.attribute("errorTitle", title);
        ctx.attribute("errorMessage", message);
        ctx.render("error.html");
    }

} // ErrorController end