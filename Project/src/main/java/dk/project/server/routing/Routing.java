package dk.project.server.routing;

// Imports
import dk.project.controller.AdminMenu.*;
import dk.project.controller.Calculator.CalculatorController;
import dk.project.controller.ErrorController;
import dk.project.controller.Login.LoginController;
import dk.project.controller.Modtag.MailController;
import dk.project.controller.Modtag.ModtagController;
import dk.project.controller.PageController;
import dk.project.controller.Status.StatusController;
import io.javalin.Javalin;

public class Routing {

    // Attributes

    // _________________________________________________

    public static void registerRoutes(Javalin app) {

        PageController.registerRoutes(app);
        LoginController.registerRoutes(app);
        AdminController.registerRoutes(app);
        MaterialController.registerRoutes(app);
        MaterialCategoryController.registerRoutes(app);
        CMSController.registerRoutes(app);
        UserController.registerRoutes(app);
        MailController.registerRoutes(app);
        StatusController.registerRoutes(app);
        ModtagController.registerRoutes(app);
        CalculatorController.registerRoutes(app);
        RequestController.registerRoutes(app);

        // Last
        //registerErrorRoutes(app);

    }

    // _________________________________________________

    private static void registerErrorRoutes(Javalin app) {

        app.error(400, ErrorController::handle400);
        app.error(401, ErrorController::handle401);
        app.error(403, ErrorController::handle403);
        app.error(404, ErrorController::handle404);
        app.error(429, ErrorController::handle429);
        app.error(500, ErrorController::handle500);
        app.error(503, ErrorController::handle503);

    }

} // Routing end