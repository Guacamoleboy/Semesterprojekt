// Package
package dk.project.server;

// Import
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;

public class PdfGenerator {

    // Attributes

    // _________________________________________________________________

    public static void generateBrochure(
            String outputPath,
            String frontImagePath,
            String textContent,
            String page2ImagePath,
            String page3ImagePath) throws Exception {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));

        document.open();
        addFullPageImage(document, frontImagePath);
        addTextPage(document, textContent);
        addFullPageImage(document, page2ImagePath);
        addFullPageImage(document, page3ImagePath);
        document.close();
    }

    // _________________________________________________________________

    private static void addFullPageImage(Document doc, String path) throws Exception {
        doc.newPage();
        Image img = Image.getInstance(path);
        img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        img.setAbsolutePosition(
                    (PageSize.A4.getWidth() - img.getScaledWidth()) / 2,
                    (PageSize.A4.getHeight() - img.getScaledHeight()) / 2
        );
        doc.add(img);
    }

    // _________________________________________________________________

    private static void addTextPage(Document doc, String text) throws Exception {
        doc.newPage();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 16);
        Paragraph p = new Paragraph(text, font);
        doc.add(p);
    }

} // PdfGenerator end