// Package
package dk.project.controller.AdminMenu;

// Imports
import dk.project.entity.User;
import dk.project.exception.DatabaseException;
import dk.project.mapper.UserMapper;
import dk.project.server.ThymeleafSetup;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.Map;

public class AdminController {

    // Attributes
    UserMapper userMapper = new UserMapper();

    // _______________________________________________

    public static void registerRoutes(Javalin app) {
        AdminController controller = new AdminController();
        app.get("/menu", controller::displayMenu);
    }

    // _______________________________________________

    private void displayMenu(Context ctx) throws DatabaseException {

        User user = ctx.sessionAttribute("user");

        if (user == null) {
            ctx.redirect("/login?error=notLoggedIn");
            return;
        }

        String roleName = userMapper.getRoleNameByID(user.getRoleID());

        ctx.html(ThymeleafSetup.render("adminMenu.html", Map.of("user", user, "role", roleName)));
    }

}