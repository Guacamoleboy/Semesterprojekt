// Package
package dk.project.controller.Status;

// Imports
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import dk.project.exception.DatabaseException;
import dk.project.mapper.OrderMapper;
import dk.project.server.PdfGenerator;
import dk.project.server.ThymeleafSetup;
import io.javalin.Javalin;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class StatusController {

    // Attributes

    // __________________________________________________________________

    public static void registerRoutes(Javalin app) {

        app.get("/status", ctx -> ctx.html(ThymeleafSetup.render("status-search.html", null)));

        // _________________________________________________________

        app.get("/status/{id}", ctx -> {
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

        app.get("/status/{id}/status", ctx -> {
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

        // _____________________________________________________________

        app.post("/status/pdfgenerator", ctx -> {

            try {

                // TODO getId instead of hardcoded - {id}
                int orderNumber = 1;

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
                PdfWriter.getInstance(document, new FileOutputStream(outputPath.toFile())); // Overwrites pr automatic. No need for validation.

                // Generate
                document.open();
                PdfGenerator.addFullPageImage(document, page1);
                Font page2Font = FontFactory.getFont(FontFactory.HELVETICA, 16);
                PdfGenerator.addPageWithBackgroundAndText(document, page2, textPage2, page2Font);
                PdfGenerator.addFullPageImage(document, page3);
                PdfGenerator.addFullPageImage(document, page4);
                document.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

} // StatusController end