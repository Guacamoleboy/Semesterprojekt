// Package
package dk.project.controller.Calculator;

// Imports
import dk.project.DTO.MaterialUsage;
import dk.project.exception.DatabaseException;
import dk.project.mapper.OrderMapper;
import dk.project.server.ThymeleafSetup;
import dk.project.service.CarportCalculationService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;
import java.util.Map;

public class CalculatorController {

    // Attributes
    private static final CalculatorController controller = new CalculatorController();
    private final CarportCalculationService service = new CarportCalculationService();

    // _________________________________________________________________________

    public static void registerRoutes(Javalin app) {

        app.post("/calculate", controller::startCalculate);

        // _____________________________________________________________________

        app.post("/sendOffer", ctx -> {

            String orderIdStr = ctx.formParam("orderId");

            if (orderIdStr == null) {
                ctx.status(400).result("Order ID mangler");
                return;
            }

            int orderId = Integer.parseInt(orderIdStr);

            try {

                OrderMapper orderMapper = new OrderMapper();
                orderMapper.updateStatus(orderId, "offer");

            } catch (DatabaseException e) {
                ctx.status(500).result("Kunne ikke opdatere ordre: " + e.getMessage());
                e.printStackTrace();
            }

        });

    }

    // _________________________________________________________________________

    private void startCalculate(Context ctx) {

        try {

            // Initial
            String lengthStr = ctx.formParam("length");
            String widthStr  = ctx.formParam("width");
            String heightStr = ctx.formParam("height");
            String roofType  = ctx.formParam("roofType");
            String shedStr   = ctx.formParam("hasToolShed");
            String orderId   = ctx.formParam("orderId");

            // Validation
            if (lengthStr == null || widthStr == null || heightStr == null) {
                // TODO | Andreas comment N/A
                // TODO | Notification!

                // TODO | Jonas comment 25-11
                // TODO | Where is this being triggered? /Status step 3? Surely.
                ctx.status(400);
                return;
            }

            double length = Double.parseDouble(lengthStr);
            double width  = Double.parseDouble(widthStr);
            double height = Double.parseDouble(heightStr);
            boolean hasToolShed = "on".equalsIgnoreCase(shedStr);
            List<MaterialUsage> materials = service.calculate(length, width, height, hasToolShed, roofType);

            double total = materials.stream()
            .mapToDouble(MaterialUsage::getTotalPrice)
            .sum();

            ctx.html(ThymeleafSetup.render("tilbud.html", Map.of(
                    "materials", materials,
                    "length", length,
                    "width", width,
                    "height", height,
                    "roofType", roofType,
                    "total", total,
                    "orderId", orderId
            )));

        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }

    }

}