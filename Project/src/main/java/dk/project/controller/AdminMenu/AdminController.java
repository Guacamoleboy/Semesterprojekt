package dk.project.controller.AdminMenu;

import dk.project.entity.User;
import dk.project.server.ThymeleafSetup;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.Map;

public class AdminController {

    public static void registerRoutes(Javalin app) {

        AdminController controller = new AdminController();

        app.get("/menu", controller::displayMenu);
    }

    // _______________________________________________

    private void displayMenu(Context ctx) {
        User user = ctx.sessionAttribute("user");
        if (user == null) {
            ctx.redirect("/login?error=notLoggedIn");
            return;
        }

        ctx.html(ThymeleafSetup.render("adminMenu.html", Map.of("user", user)));

    }
}
