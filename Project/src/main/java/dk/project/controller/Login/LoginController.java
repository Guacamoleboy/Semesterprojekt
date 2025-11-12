package dk.project.controller.Login;

import dk.project.entity.User;
import dk.project.mapper.UserMapper;
import dk.project.server.ThymeleafSetup;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.mindrot.jbcrypt.BCrypt;

public class LoginController {
    UserMapper usermapper = new UserMapper();

    public static void registerRoutes(Javalin app) {

        LoginController controller = new LoginController();

        app.get("/loginPage", ctx -> ctx.html(ThymeleafSetup.render("login.html", null)));
        app.post("/login", controller::login);
    }

    private void login(Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        if (username == null || password == null || username.isBlank() || password.isBlank()) {

            ctx.redirect("/loginPage?error=missingFields");
            return;
        }

        try {
            User user = usermapper.getByUserName(username);

            if (user == null) {

                ctx.redirect("/loginPage?error=wrongInfo");
                return;
            }

            if (!BCrypt.checkpw(password, user.getPassword_hash())) {
                ctx.redirect("/loginPage?error=wrongInfo");
                return;
            }

            ctx.sessionAttribute("user", user);
            ctx.redirect("/menu");

        } catch (Exception e) {
            ctx.redirect("/loginPage?error=500");
        }
    }
}
