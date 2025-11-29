// Package
package dk.project.controller.Status;

// Imports
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import dk.project.DTO.MaterialUsage;
import dk.project.controller.Calculator.CalculatorController;
import dk.project.entity.CarportOrder;
import dk.project.entity.Order;
import dk.project.exception.DatabaseException;
import dk.project.mapper.OrderMapper;
import dk.project.server.PdfGenerator;
import dk.project.server.ThymeleafSetup;
import dk.project.service.CarportCalculationService;
import io.javalin.Javalin;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class StatusController {

    // Attributes
    private static final OrderMapper orderMapper = new OrderMapper();
    private static final CarportCalculationService carportCalculationService = new CarportCalculationService();

    // __________________________________________________________________

    public static void registerRoutes(Javalin app) {

        app.get("/status", ctx -> ctx.html(ThymeleafSetup.render("status-search.html", null)));

        // _________________________________________________________

        app.get("/status/{id}", ctx -> {

            // Can't access /status/{id} without using the search tool
            if (ctx.sessionAttribute("canViewStatus") == null) {
                ctx.redirect("/status?error=noAccess");
                return;
            }

            ctx.html(ThymeleafSetup.render("status.html", null));
        });

        // _________________________________________________________

        app.post("/status/{id}/authorize", ctx -> { /* ALLOW */
            ctx.sessionAttribute("canViewStatus", true);
            ctx.json(Map.of("success", true));
        });

        // _____________________________________________________________

        app.get("/status/{id}/status", ctx -> { /* API */
            try {

                // Initial
                int id = Integer.parseInt(ctx.pathParam("id"));
                OrderMapper orderMapper = new OrderMapper();

                // Assert
                Object value = orderMapper.getOrderFieldById("status", id);

                // Validation for null
                if (value == null) {
                    ctx.json(Collections.singletonMap("status", null));
                    return;
                }

                // Collect
                String status = (String) value;
                ctx.json(Map.of("status", status));

            } catch (NumberFormatException e) {
                ctx.json(Collections.singletonMap("status", null));
            } catch (DatabaseException e) {
                ctx.json(Collections.singletonMap("status", null));
            }
        });

        // _____________________________________________________________

        app.post("/status/{id}/update", ctx -> {
            try {

                // Assert
                int id = Integer.parseInt(ctx.pathParam("id"));
                String status = ctx.formParam("status");

                // Act
                OrderMapper orderMapper = new OrderMapper();
                orderMapper.updateStatus(id, status);

                // Handle
                ctx.json(Map.of("success", true));
            } catch (Exception e) {
                ctx.json(Map.of("success", false, "error", e.getMessage()));
            }
        });

        // _____________________________________________________________

        app.post("/status/{id}/pdfgenerator", ctx -> {

            try {

                // Gets our ID
                int orderNumber = Integer.parseInt(ctx.pathParam("id"));

                // Debug
                /*System.out.println(orderNumber);*/

                /* Stykliste | Step 1 */
                Order order = orderMapper.getById(orderNumber);
                CarportOrder c = order.getCarportOrder();
                List<MaterialUsage> materials = carportCalculationService.calculate(
                        c.getLength(),
                        c.getWidth(),
                        c.getHeight(),
                        c.isHasToolShed(),
                        c.getRoof()
                );

                // Setup
                Path modtagDir = Path.of("src/main/resources/static/pdf/modtag");
                Path backupDir = Path.of("src/main/resources/static/pdf/backup");

                // Naming
                Path outputPath = modtagDir.resolve(orderNumber + ".pdf");
                Path backupPath = backupDir.resolve(orderNumber + ".pdf");

                // Moves the existing file -> /backup before generating a new
                // If a file already exists in /backup name it _1 | +1 | per attempt. All offers are saved.
                if (Files.exists(outputPath)) {

                    int counter = 1;

                    while (Files.exists(backupPath)) {
                        String newName = orderNumber + "_" + counter + ".pdf";
                        backupPath = backupDir.resolve(newName);
                        counter++;
                    }

                    Files.move(outputPath, backupPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                }
                
                // Content
                String page1 = "src/main/resources/static/pdf/content/frontpage.png";
                String page2 = "src/main/resources/static/pdf/content/stykliste.png";
                String page3 = "src/main/resources/static/pdf/content/tegning.png";
                String page4 = "src/main/resources/static/pdf/content/vejledning.png";

                // Stykliste content
                String textPage2 = "Stykliste her";

                // Initial .pdf setup
                Document document = new Document(PageSize.A4);
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath.toFile())); // Overwrites pr automatic. No need for validation.

                // Generate
                document.open();
                PdfGenerator.addFullPageImage(document, page1);
                Font page2Font = FontFactory.getFont(FontFactory.HELVETICA, 16);

                /* Stykliste | Step 2 */
                String[][] rowsPage2 = new String[materials.size()][4];
                for (int i = 0; i < materials.size(); i++) {
                    MaterialUsage m = materials.get(i);

                    rowsPage2[i][0] = m.getMaterial().getName();
                    Integer length = m.getMaterial().getLength();
                    Integer width = m.getMaterial().getWidth();
                    Integer height = m.getMaterial().getHeight();
                    StringBuilder dimensions = new StringBuilder();
                    boolean first = true;

                    if (length != null) {
                        dimensions.append(length);
                        first = false;
                    }
                    if (width != null) {
                        if (!first) dimensions.append(" x ");
                        dimensions.append(width);
                        first = false;
                    }
                    if (height != null) {
                        if (!first) dimensions.append(" x ");
                        dimensions.append(height);
                    }

                    rowsPage2[i][1] = dimensions.toString();
                    rowsPage2[i][2] = String.valueOf(m.getAmount());
                    rowsPage2[i][3] = m.getMaterial().getUnit() != null ? m.getMaterial().getUnit() : "stk";

                }

                PdfGenerator.addPageWithBackgroundAndRows(document, writer, page2, "Træ & Tagplader", rowsPage2);
                PdfGenerator.addFullPageImage(document, page3);
                PdfGenerator.addFullPageImage(document, page4);
                document.close();

                // JSON
                ctx.json(Map.of("success", true));

            } catch (Exception e) {
                e.printStackTrace();
                ctx.json(Map.of("success", false, "error", e.getMessage()));
            }

        });

    }

} // StatusController end