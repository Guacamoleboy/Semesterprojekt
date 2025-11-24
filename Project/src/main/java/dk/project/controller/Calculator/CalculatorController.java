package dk.project.controller.Calculator;

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
            String lengthStr = ctx.formParam("length");
            String widthStr  = ctx.formParam("width");
            String heightStr = ctx.formParam("height");
            String roofType  = ctx.formParam("roofType");
            String shedStr   = ctx.formParam("hasToolShed");

            // Validation
            if (lengthStr == null || widthStr == null || heightStr == null) {
                //TODO: Notification!
                // Where is this being triggered? /Status step 3? Surely.
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

            ctx.html(ThymeleafSetup.render("result.html", Map.of(
                    "materials", materials,
                    "length", length,
                    "width", width,
                    "height", height,
                    "roofType", roofType,
                    "total", total
            )));

        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }

    }

}