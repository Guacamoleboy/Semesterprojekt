// Package
package dk.project.controller.AdminMenu;

// Imports
import dk.project.entity.User;
import dk.project.exception.DatabaseException;
import dk.project.mapper.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import jakarta.servlet.MultipartConfigElement;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class UserController {

    // Attributes
    private static final UserController controller = new UserController();
    private final UserMapper userMapper = new UserMapper();

    // _______________________________________________

    public static void registerRoutes(Javalin app) {

        app.post("/searchUsers", controller::searchUsers);
        app.post("/createUser", controller::createUser);
        app.post("/updateUser", controller::updateUser);
        app.post("/deleteUser", controller::deleteUser);

        // ____________________________________________________

        app.get("/logout", ctx -> {
            ctx.sessionAttribute("user", null);
            ctx.redirect("/login?success=loggedOut");
        });

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

        // Important | DO NOT REMOVE
        ctx.req().setAttribute("jakarta.servlet.multipartConfig",
        new MultipartConfigElement("C:/temp", 10_000_000, 10_000_000, 1024));

        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        String roleID = ctx.formParam("role");

        /* System.out.println("Request content type: " + ctx.req().getContentType()); */ // Debug

        UploadedFile uploadedFile = ctx.uploadedFile("picture");

        /* System.out.println("uploadedFile = " + uploadedFile); */ // Debug

        try {

            if (username == null || password == null || roleID == null || uploadedFile == null) {
                ctx.redirect("/menu?error=missingFields");
                return;
            }

            // Save file in correct path
            String filename = uploadedFile.filename();
            Path uploadPath = Paths.get("src/main/resources/public/images/staff/", filename);

            // Saves file to uploadPath
            try (OutputStream os = java.nio.file.Files.newOutputStream(uploadPath)) {
                uploadedFile.content().transferTo(os);
            }

            int id = Integer.parseInt(roleID);

            User user = new User();
            user.setUsername(username);
            user.setPassword_hash(BCrypt.hashpw(password, BCrypt.gensalt()));
            user.setRoleID(id);
            user.setPicture("/images/staff/" + filename);
            user.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            userMapper.newUser(user);

            // Clears C:/temp file after to prevent stacking
            File tempDir = new File("C:/temp");
            for (File f : tempDir.listFiles()) {
                f.delete();
            }

            ctx.redirect("/menu?success=userCreated");

        } catch (NumberFormatException e) {

            ctx.status(400).result("Skal være et tal!");

        } catch (Exception e) {
            e.printStackTrace(); // Debug
            ctx.status(500).result("Fejl ved oprettelse af bruger");

        }
    }

    // _______________________________________________

    private void updateUser(Context ctx) {

        String stringuserid = ctx.formParam("id");
        String newusername = ctx.formParam("username");
        String stringroleID = ctx.formParam("role");

        try {

            if (newusername == null || stringroleID == null || stringuserid == null) {

                ctx.redirect("/menu?error=missingFields");
                return;

            }

            int roleID = Integer.parseInt(stringroleID);
            int userID = Integer.parseInt(stringuserid);


            User user = userMapper.getById(userID);
            user.setUsername(newusername);
            user.setRoleID(roleID);

            userMapper.updateUser(user);

        } catch (NumberFormatException e) {

            ctx.status(400).result("Skal være et tal!");

        } catch (Exception e) {

            ctx.status(500).result("Fejl opdatering af bruger");

        }
    }

    // _______________________________________________

    private void deleteUser(Context ctx) {

        String userID = ctx.formParam("id");

        try {

            if (userID == null) {

                return;

            }

            int id = Integer.parseInt(userID);

            userMapper.deleteUser(id);

        } catch (NumberFormatException e) {

            ctx.status(400).result("Skal være et tal!");

        } catch (Exception e) {

            ctx.status(500).result("Fejl ved sletning af bruger!");

        }

    }

}