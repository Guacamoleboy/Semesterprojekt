// Package
package dk.project.controller.Status;

// Imports
import dk.project.exception.DatabaseException;
import dk.project.mapper.OrderMapper;
import dk.project.server.ThymeleafSetup;
import io.javalin.Javalin;

import java.util.Map;

public class StatusController {

    // Attributes

    // __________________________________________________________________

    public static void registerRoutes(Javalin app) {

        app.get("/status", ctx -> ctx.html(ThymeleafSetup.render("status-search.html", null)));

        // _________________________________________________________

        app.get("/status/id/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                OrderMapper orderMapper = new OrderMapper();
                String status = orderMapper.getStatusById(id);
                if (status != null) {
                    ctx.html(ThymeleafSetup.render("status.html", Map.of("status", status)));
                }
            } catch (NumberFormatException e) {
                ctx.redirect("/status/?error=invalidId");
            } catch (DatabaseException e) {
                ctx.redirect("/status/?error=dbError");
            }
        });

        // _____________________________________________________________

        app.get("/status/id/{id}/status", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                OrderMapper orderMapper = new OrderMapper();
                String status = orderMapper.getStatusById(id);
                if (status != null) {
                    ctx.json(Map.of("status", status));
                }
            }  catch (NumberFormatException e) {
                ctx.redirect("/status/?error=invalidId");
            } catch (DatabaseException e) {
                ctx.redirect("/status/?error=dbError");
            }
        });

    }

} // StatusController end