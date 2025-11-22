// Package
package dk.project.controller.Modtag;

// Imports
import dk.project.entity.CarportOrder;
import dk.project.entity.CarportCategory;
import dk.project.entity.Order;
import dk.project.mapper.CarportOrderMapper;
import dk.project.mapper.CarportCategoryMapper;
import dk.project.mapper.OrderMapper;
import dk.project.server.PdfGenerator;
import dk.project.server.ThymeleafSetup;
import dk.project.exception.DatabaseException;
import io.javalin.Javalin;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ModtagController {

    // Attributes

    // __________________________________________

    public static void registerRoutes(Javalin app) {

        app.get("/beregn/modtag", ctx -> ctx.html(ThymeleafSetup.render("modtag.html", null)));

        /*
        app.get("/beregn/modtag", ctx -> {

            try {
                CarportCategoryMapper categoryMapper = new CarportCategoryMapper();
                CarportCategory category = categoryMapper.getFirstCategory();
                
                if (category == null) {
                    ctx.redirect("/beregn/?error=categoryError");
                    return;
                }

                double width = 320.0;
                double length = 600.0;
                double height = 240.0;
                String roof = "Fladt tag";
                boolean hasToolShed = false;
                Double toolShedWidth = null;
                Double toolShedLength = null;
                
                String widthParam = ctx.queryParam("width");
                String lengthParam = ctx.queryParam("length");
                String heightParam = ctx.queryParam("height");
                String roofParam = ctx.queryParam("roof");
                String hasToolShedParam = ctx.queryParam("hasToolShed");
                String toolShedWidthParam = ctx.queryParam("toolShedWidth");
                String toolShedLengthParam = ctx.queryParam("toolShedLength");
                
                if (widthParam != null && !widthParam.isEmpty()) {
                    width = Double.parseDouble(widthParam);
                }
                if (lengthParam != null && !lengthParam.isEmpty()) {
                    length = Double.parseDouble(lengthParam);
                }
                if (heightParam != null && !heightParam.isEmpty()) {
                    height = Double.parseDouble(heightParam);
                }
                if (roofParam != null && !roofParam.isEmpty()) {
                    roof = roofParam;
                }
                if (hasToolShedParam != null && hasToolShedParam.equals("true")) {
                    hasToolShed = true;
                }
                if (toolShedWidthParam != null && !toolShedWidthParam.isEmpty()) {
                    toolShedWidth = Double.parseDouble(toolShedWidthParam);
                }
                if (toolShedLengthParam != null && !toolShedLengthParam.isEmpty()) {
                    toolShedLength = Double.parseDouble(toolShedLengthParam);
                }

                CarportOrder carportOrder = new CarportOrder(
                        0, null, category, width, length, height, 
                        null, roof, hasToolShed, toolShedWidth, toolShedLength, false, LocalDateTime.now()
                );

                CarportOrderMapper carportOrderMapper = new CarportOrderMapper();
                carportOrderMapper.newOrder(carportOrder);

                double totalPrice = 20000.0;
                
                Order order = new Order(0, null, carportOrder, totalPrice, "pending", LocalDateTime.now(), null);
                OrderMapper orderMapper = new OrderMapper();
                orderMapper.newOrder(order);

                int orderId = order.getId();

                Path targetDir = Path.of("target/classes/static/pdf/modtag");
                
                if (!Files.exists(targetDir)) {
                    Files.createDirectories(targetDir);
                }

                String outputPath = targetDir.resolve(orderId + ".pdf").toString();

                PdfGenerator.generateOfferPdf(
                        outputPath,
                        "src/main/resources/static/pdf/content/forside.png",
                        String.valueOf(orderId),
                        carportOrder.getWidth(),
                        carportOrder.getLength(),
                        carportOrder.getHeight(),
                        carportOrder.getRoof() != null ? carportOrder.getRoof() : "Fladt tag",
                        totalPrice,
                        carportOrder.isHasToolShed(),
                        carportOrder.getToolShedWidth(),
                        carportOrder.getToolShedLength(),
                        "src/main/resources/static/pdf/content/2.png",
                        "src/main/resources/static/pdf/content/3.png"
                );

                Map<String, Object> variables = new HashMap<>();
                variables.put("orderId", orderId);

                ctx.html(ThymeleafSetup.render("modtag.html", variables));

            } catch (DatabaseException e) {
                ctx.redirect("/beregn/?error=dbError");
            } catch (Exception e) {
                ctx.redirect("/beregn/?error=pdfError");
            }

        });
        */

    }

} // ModtagController end