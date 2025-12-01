// Package
package dk.project.controller.Calculator;

// Imports
import dk.project.DTO.MaterialUsage;
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

    }

    // _________________________________________________________________________

    private void startCalculate(Context ctx) {

        try {

            // Initial
            String page = ctx.formParam("targetPage");
            String lengthStr = ctx.formParam("length");
            String widthStr  = ctx.formParam("width");
            String heightStr = ctx.formParam("height");
            String roofType  = ctx.formParam("roofType");
            String shedStr   = ctx.formParam("hasToolShed");
            String orderId   = ctx.formParam("orderId");

            // Validation + Default for targetPage
            if (page == null || page.isEmpty()) {
                page = "tilbud";
            }

            // Validation
            if (lengthStr == null || widthStr == null || heightStr == null) {
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

            ctx.html(ThymeleafSetup.render(page + ".html", Map.of(
                    "total", total,
                    "materials", materials,
                    "length", length,
                    "width", width,
                    "height", height,
                    "roofType", roofType,
                    "orderId", orderId
            )));

        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }

    }

}