/*

    What is "f"?

    Example:
    0.0f -> x.xf -> f = float

    Without f it would be double. Since we want and use float we add "f" after.

    - Guac

*/

// Package
package dk.project.server;

// Import
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class PdfGenerator {

    // Attributes

    // _________________________________________________________________
    // Used on all pages

    public static void addFullPageImage(Document doc, String path) throws Exception {
        doc.newPage();
        Image img = Image.getInstance(path);
        img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        img.setAbsolutePosition((PageSize.A4.getWidth() - img.getScaledWidth()) / 2, (PageSize.A4.getHeight() - img.getScaledHeight()) / 2);
        doc.add(img);
    }

    // _________________________________________________________________
    // Not used, but good to have

    public static void addTextPage(Document doc, String text) throws Exception {
        doc.newPage();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 16);
        Paragraph p = new Paragraph(text, font);
        doc.add(p);
    }

    // _________________________________________________________________
    // Used on Page 2

    public static void addPageWithMaterialsByCategory(Document doc, PdfWriter writer, String backgroundImagePath, List<Map<String,Object>> materials) throws Exception {
        doc.newPage();

        // Background
        Image bg = Image.getInstance(backgroundImagePath);
        bg.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        bg.setAbsolutePosition(
                (PageSize.A4.getWidth() - bg.getScaledWidth()) / 2,
                (PageSize.A4.getHeight() - bg.getScaledHeight()) / 2
        );
        doc.add(bg);

        float pageWidth = PageSize.A4.getWidth();
        float leftMargin = doc.leftMargin();
        float rightMargin = doc.rightMargin();
        float usableWidth = pageWidth - leftMargin - rightMargin;

        // Fonts
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font rowFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        // Initial
        PdfContentByte canvas = writer.getDirectContent();
        float startY = doc.top() - 80;
        float offsetY = 20;

        // For loop per categoryId
        for (int categoryId = 1; categoryId <= 2; categoryId++) {

            // Title
            String title = categoryId == 1 ? "Træ & Tagplader" : "Beslag & Skruer";
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(title, titleFont), leftMargin, startY - offsetY, 0);

            // Line under title
            float lineY = startY - 20 - offsetY;
            canvas.setLineWidth(2f);
            canvas.setColorStroke(new Color(0, 61, 118)); // rgb values of :roof --fog-blue
            canvas.moveTo(leftMargin, lineY);
            canvas.lineTo(leftMargin + usableWidth * 0.3f, lineY);
            canvas.stroke();

            // Header
            String[] headers = {"Beskrivelse", "Mål (cm)", "Antal", "Enhed"};
            int columns = headers.length;
            float columnWidth = usableWidth / columns;
            float rowY = lineY - 30;

            // For loop over placement
            for (int i = 0; i < columns; i++) {

                // Placement
                float colCenterX = leftMargin + i * columnWidth + columnWidth / 2;
                int alignment;
                float posX;

                // Placement
                switch (i) {
                    case 0:
                        alignment = Element.ALIGN_LEFT;
                        posX = leftMargin + 2;
                        break;
                    case 3:
                        alignment = Element.ALIGN_RIGHT;
                        posX = leftMargin + usableWidth - 2;
                        break;
                    default:
                        alignment = Element.ALIGN_CENTER;
                        posX = colCenterX;
                        break;
                }

                ColumnText.showTextAligned(canvas, alignment, new Phrase(headers[i], headerFont), posX, rowY - offsetY, 0);

            }

            // Initial
            float headerLineY = rowY - 10 - offsetY;
            canvas.setLineWidth(1f);
            canvas.moveTo(leftMargin, headerLineY);
            canvas.lineTo(leftMargin + usableWidth, headerLineY);
            canvas.stroke();

            // Rows
            rowY = headerLineY; // Distance from header & line -> content. For gap add "- 10" for example.

            // For-each loop over our Map object
            for (Map<String,Object> m : materials) {

                if (!m.get("categoryId").equals(categoryId)) continue;

                String desc = (String) m.get("name");
                String dimensions = "";

                // Only get dimensions for categoryId -> 1
                if (categoryId == 1) {

                    // Initial with type casting
                    Integer length = (Integer)m.get("length");
                    Integer width = (Integer)m.get("width");
                    Integer height = (Integer)m.get("height");
                    boolean first = true;

                    // Checks
                    if (length != null) {
                        dimensions += length; first = false;
                    }
                    if (width != null) {
                        if(!first) dimensions += " x "; dimensions += width; first = false;
                    }
                    if (height != null) {
                        if(!first) dimensions += " x "; dimensions += height;
                    }

                }

                // Initial
                String amount = String.valueOf(m.get("amount"));
                String unit = (String)m.get("unit");
                String[] row = {desc, dimensions, amount, unit};

                // For loop over columns
                for (int i = 0; i < columns; i++) {
                    float colCenterX = leftMargin + i * columnWidth + columnWidth / 2;
                    int alignment;
                    float posX;

                    // Switch case over i for columns
                    switch (i) {
                        case 0:
                            alignment = Element.ALIGN_LEFT;
                            posX = leftMargin + 2;
                            break;
                        case 3:
                            alignment = Element.ALIGN_RIGHT;
                            posX = leftMargin + usableWidth - 2;
                            break;
                        default:
                            alignment = Element.ALIGN_CENTER;
                            posX = colCenterX;
                            break;
                    }

                    ColumnText.showTextAligned(canvas, alignment, new Phrase(row[i], rowFont), posX, rowY - offsetY, 0);

                }

                rowY -= 20;

            }

            startY = rowY - 40;

        }

    }

    // _________________________________________________________________
    // Used on Page 3

    public static void addImageOverBackground(Document doc, String backgroundPath, String overlayImagePath) throws Exception {
        doc.newPage();

        Image bg = Image.getInstance(backgroundPath);
        bg.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        bg.setAbsolutePosition(
                (PageSize.A4.getWidth() - bg.getScaledWidth()) / 2,
                (PageSize.A4.getHeight() - bg.getScaledHeight()) / 2
        );
        doc.add(bg);

        Image overlay = Image.getInstance(overlayImagePath);

        float targetWidth = PageSize.A4.getWidth() * 0.7f;
        float scalePercent = targetWidth / overlay.getWidth() * 100;
        overlay.scalePercent(scalePercent);

        overlay.setAbsolutePosition(
                (PageSize.A4.getWidth() - overlay.getScaledWidth()) / 2,
                (PageSize.A4.getHeight() - overlay.getScaledHeight()) / 2
        );

        doc.add(overlay);
    }

} // PdfGenerator end