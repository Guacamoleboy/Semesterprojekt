// Package
package dk.project.controller.Modtag;

// Imports
import dk.project.DTO.MaterialUsage;
import dk.project.entity.CarportCategory;
import dk.project.entity.CarportOrder;
import dk.project.entity.Customer;
import dk.project.entity.Order;
import dk.project.mapper.CarportCategoryMapper;
import dk.project.mapper.CarportOrderMapper;
import dk.project.mapper.CustomerMapper;
import dk.project.mapper.OrderMapper;
import dk.project.server.ThymeleafSetup;
import dk.project.service.CarportCalculationService;
import io.javalin.Javalin;
import java.util.List;
import java.util.Map;

public class ModtagController {

    // Attributes

    // __________________________________________

    public static void registerRoutes(Javalin app) {

        app.get("/beregn/modtag", ctx -> {

            // Initial load
            double length = Double.parseDouble(ctx.queryParam("length"));
            double width = Double.parseDouble(ctx.queryParam("width"));
            double height = Double.parseDouble(ctx.queryParam("height"));
            boolean hasToolShed = "true".equals(ctx.queryParam("hasToolShed"));
            Double toolShedLength = null;
            Double toolShedWidth = null;
            String roofSlope = ctx.queryParam("roof");
            String roofType = ctx.queryParam("material");

            // Validation
            if (hasToolShed) {
                toolShedLength = Double.parseDouble(ctx.queryParam("toolShedLength"));
                toolShedWidth = Double.parseDouble(ctx.queryParam("toolShedWidth"));
            }

            // Calculate materials
            List<MaterialUsage> materials = new CarportCalculationService()
                    .calculate(length, width, height, hasToolShed, roofType);

            // Gets total price for MaterialUsage without comma
            long total = Math.round(materials.stream()
                    .mapToDouble(MaterialUsage::getTotalPrice)
                    .sum());

            // Thymeleaf render | Map.ofEntries fixed a bug | DO NOT REMOVE
            ctx.html(ThymeleafSetup.render("modtag.html", Map.ofEntries(
                    Map.entry("total", total),
                    Map.entry("materials", materials),
                    Map.entry("length", formatDimension(length)),
                    Map.entry("width", formatDimension(width)),
                    Map.entry("height", formatDimension(height)),
                    Map.entry("roofSlope", roofSlope),
                    Map.entry("roofType", roofType),
                    Map.entry("hasToolShed", hasToolShed),
                    Map.entry("toolShedLength", hasToolShed ? formatDimension(toolShedLength) : "N/A"),
                    Map.entry("toolShedWidth", hasToolShed ? formatDimension(toolShedWidth) : "N/A")
            )));

        });

        // _____________________________________________________________________

        app.post("/generate-offer", ctx -> {

            try {

                // Initial
                var json = ctx.bodyAsClass(Map.class);
                CustomerMapper customerMapper = new CustomerMapper();
                String email = (String) json.get("email");
                Customer customer = customerMapper.getCustomerByEmail(email);

                // Customer validation or create
                if (customer == null) {
                    customer = new Customer();
                    customer.setFirstName((String) json.get("firstname"));
                    customer.setLastName((String) json.get("lastname"));
                    customer.setEmail(email);
                    customer.setPhone((String) json.get("phone"));
                    customerMapper.newCustomer(customer);
                }

                // Type Cast
                CarportCategoryMapper categoryMapper = new CarportCategoryMapper();
                CarportCategory category = categoryMapper.getFirstCategory();
                CarportOrder carportOrder = new CarportOrder(
                        0, customer, category,
                        ((Number) json.get("width")).doubleValue(),
                        ((Number) json.get("length")).doubleValue(),
                        ((Number) json.get("height")).doubleValue(),
                        ((Number) json.get("angle")).doubleValue(),
                        (String) json.get("roof"),
                        (Boolean) json.get("hasToolShed"),
                        ((Number) json.get("toolShedWidth")).doubleValue(),
                        ((Number) json.get("toolShedLength")).doubleValue(),
                        (Boolean) json.get("hasTrapez"),
                        java.time.LocalDateTime.now()
                );

                // Generates a new CarportOrder
                CarportOrderMapper carportOrderMapper = new CarportOrderMapper();
                carportOrderMapper.newOrder(carportOrder);

                // Generates a new Order
                Order order = new Order(
                        0, customer, carportOrder, 0.0, "pending",
                        java.time.LocalDateTime.now(), null
                );

                // Generates a new order with correct ID
                OrderMapper orderMapper = new OrderMapper();
                int orderId = orderMapper.newOrder(order);

                ctx.json(Map.of("success", true, "orderId", orderId));
            } catch (Exception e) {
                e.printStackTrace();
                ctx.json(Map.of("success", false, "error", e.getMessage()));
            }

        });

    }

    // ____________________________________________________________________________
    // %.0f
    // % = Start of format
    // .0 -> Precision. So .0 means no comma
    // f -> float (double) | The value we want

    private static String formatDimension(Double value) {
        return value != null ? String.format("%.0f", value) : "N/A";
    }

} // ModtagController end