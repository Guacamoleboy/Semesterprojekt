// Package
package dk.project.controller.Tilbud;

// Imports
import dk.project.entity.Order;
import dk.project.exception.DatabaseException;
import dk.project.mapper.OrderMapper;
import dk.project.server.MailSetup;
import io.javalin.Javalin;

public class TilbudController {

    // Attributes

    // _________________________________________________________________________

    public static void registerRoutes(Javalin app) {

        app.post("/sendOffer", ctx -> {

            // Get values
            String orderIdStr = ctx.formParam("orderId");
            String totalPriceStr = ctx.formParam("totalPrice");

            // Translate to correct values
            int orderId = Integer.parseInt(orderIdStr);
            double totalPrice = Double.parseDouble(totalPriceStr);

            try {

                // Initial
                OrderMapper orderMapper = new OrderMapper();

                // Update
                orderMapper.updateTotalPrice(orderId, totalPrice);
                orderMapper.updateStatus(orderId, "offer");

                // Get order
                Order order = orderMapper.getById(orderId);

                String customerEmail = order.getCustomer().getEmail();
                String subject = "Dit tilbud fra Fog";
                String body = String.format("""
                Hej %s,

                Vi har lavet et tilbud til dig på din carport. Du kan se dit tilbud og følge status her:
                %s/status/%d?status=offer

                Venlig hilsen
                Fog
                """, order.getCustomer().getFirstName(), "https://fog.guacamoleboy.dk", orderId);

                /* Debug */
                System.out.println("Forsøger at sende til: " + customerEmail);

                /* If all worked out -> send mail */
                MailSetup.sendMail(customerEmail, subject, body);

            } catch (DatabaseException e) {
                ctx.status(500).result("Kunne ikke opdatere ordre: " + e.getMessage());
                e.printStackTrace();
            }

        });

    }

}