package dk.project.controller.AdminMenu;

import dk.project.entity.User;
import dk.project.exception.DatabaseException;
import dk.project.mapper.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class UserController {


    private static final UserController controller = new UserController();
    private final UserMapper userMapper = new UserMapper();

    // _______________________________________________

    public static void registerRoutes(Javalin app) {

        app.post("/searchUsers", controller::searchUsers);
        app.post("/createUser", controller::createUser);
        app.post("/updateUser", controller::updateUser);
        app.post("/deleteUser", controller::deleteUser);

    }

    // _______________________________________________

    private void searchUsers(Context ctx) throws SQLException, DatabaseException {

        String idParam = ctx.formParam("id");
        String username = ctx.formParam("username");
        String roleParam = ctx.formParam("role");

        int id = 0;
        if (idParam != null && !idParam.isEmpty()) {

            try { id = Integer.parseInt(idParam); } catch (NumberFormatException ignore) {}

        }

        int roleID = 0;
        if (roleParam != null && !roleParam.isEmpty()) {

            try { roleID = Integer.parseInt(roleParam); } catch (NumberFormatException ignore) {}

        }

        List<User> result;

        if (id > 0) {

            User user = userMapper.getById(id);
            result = user != null ? List.of(user) : List.of();

        } else if (username != null && !username.isBlank()) {

            User user = userMapper.getByUserName(username);
            result = user != null ? List.of(user) : List.of();

        } else if (roleID > 0) {

            result = userMapper.getByRole(roleID);

        } else {

            result = userMapper.getAll();

        }

        ctx.json(result);
    }


    // _______________________________________________

    private void createUser(Context ctx) {

        //TODO: Skal lige lave rigtige notifications!
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        String roleID = ctx.formParam("role");

        try {

            if (username == null || password == null || roleID == null) {

                ctx.redirect("/menu?error=missingFields");
                return;

            }

            int id = Integer.parseInt(roleID);

            User user = new User();
            user.setUsername(username);
            user.setPassword_hash(BCrypt.hashpw(password, BCrypt.gensalt())); // sikker hashing
            user.setRoleID(id);
            user.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            userMapper.newUser(user);
            ctx.status(200).result("Bruger oprettet!");

        } catch (NumberFormatException e) {

            ctx.status(400).result("Skal være et tal!");

        } catch (Exception e) {

            ctx.status(500).result("Fejl ved oprettelse af bruger");

        }
    }

    // _______________________________________________

    private void updateUser(Context ctx) {

        String username = ctx.formParam("username");
        String roleID = ctx.formParam("role");

        try {

            if (username == null || roleID == null) {

                ctx.redirect("/menu?error=missingFields");
                return;

            }

            int id = Integer.parseInt(roleID);

            System.out.println(id + " " + username);
            //TODO: Skal lige koble backend fra userMapper på!!!

        } catch (NumberFormatException e) {

            ctx.status(400).result("Skal være et tal!");

        } catch (Exception e) {

            ctx.status(500).result("Fejl opdatering af bruger");

        }
    }

    // _______________________________________________

    private void deleteUser(Context ctx) {

        //TODO: Skal lige tilføje rigtige notifications!
        String userID = ctx.formParam("id");

        try {

            if (userID == null) {

                return;

            }

            int id = Integer.parseInt(userID);

            System.out.println(id);
            //TODO: Skal lige koble backend fra userMapper på!!!

        } catch (NumberFormatException e) {

            ctx.status(400).result("Skal være et tal!");

        } catch (Exception e) {

            ctx.status(500).result("Fejl ved sletning af bruger!");

        }
    }

}
