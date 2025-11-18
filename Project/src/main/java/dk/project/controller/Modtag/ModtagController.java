// Package
package dk.project.controller.Modtag;

// Imports
import dk.project.server.PdfGenerator;
import dk.project.server.ThymeleafSetup;
import io.javalin.Javalin;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModtagController {

    // Attributes

    // __________________________________________

    public static void registerRoutes(Javalin app) {

        app.get("/beregn/modtag", ctx -> {

            // Apparently has to have full path instead of /static/ pathing from Server.java
            Path outDir = Path.of("src/main/resources/static/pdf/modtag");

            // If /modtag isnt found -> Generate it
            if (!Files.exists(outDir)) {
                Files.createDirectories(outDir);
            }

            // Name of our file. Will be changed with ID later.
            String outputPath = outDir.resolve("1.pdf").toString();

            // Generate .pdf with content as follows
            try {
                PdfGenerator.generateBrochure(
                        outputPath,
                        "src/main/resources/static/pdf/content/forside.png",
                        "Tilbud på carport\nPris: 12.999 kr\nMateriale: Trykimprægneret træ...",
                        "src/main/resources/static/pdf/content/2.png",
                        "src/main/resources/static/pdf/content/3.png"
                );
            } catch (Exception e) {
                ctx.redirect("/beregn/?error=pdfError");
            }

            // Load modtag.html as /beregn/modtag
            ctx.html(ThymeleafSetup.render("modtag.html", null));

        });

    }

} // ModtagController end