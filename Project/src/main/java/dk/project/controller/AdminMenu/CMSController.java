// Package
package dk.project.controller.AdminMenu;

// Imports
import io.javalin.Javalin;
import io.javalin.http.Context;

public class CMSController {

    // Attributes
    private static final CMSController controller = new CMSController();

    // _______________________________________________

    public static void registerRoutes(Javalin app) {

        app.post("/getCMSFees", controller::getCMSFees);
        app.post("/updateCMSFees", controller::updateCMSFees);

    }

    // _______________________________________________

    private void getCMSFees(Context ctx) {

        // TODO: Last week | Add or remove from Project

    }

    // _______________________________________________

    private void updateCMSFees(Context ctx) {

        // TODO: Last week | Add or remove from Project

    }

}