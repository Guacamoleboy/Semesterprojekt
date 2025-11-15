package dk.project.controller.AdminMenu;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class CMSController {

    private static final CMSController controller = new CMSController();

    // _______________________________________________

    public static void registerRoutes(Javalin app) {

        app.post("/getCMSFees", controller::getCMSFees);
        app.post("/updateCMSFees", controller::updateCMSFees);

    }

    // _______________________________________________

    private void getCMSFees(Context ctx) {

        //TODO: Laves gebyre? (Opsætnings, leverings og administrations gebyre?)

    }

    // _______________________________________________

    private void updateCMSFees(Context ctx) {

        //TODO: Laves gebyre? (Opsætnings, leverings og administrations gebyre?)


    }
}

