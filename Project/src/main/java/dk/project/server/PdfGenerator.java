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

    public static void addPageWithBackgroundAndRows(Document doc, PdfWriter writer, String backgroundImagePath, String title, String[][] rows) throws Exception {
        doc.newPage();

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

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph titlePara = new Paragraph(title, titleFont);
        titlePara.setSpacingBefore(100);
        doc.add(titlePara);

        float titleHeightEstimate = 20;

        PdfContentByte canvas = writer.getDirectContent();

        float lineY = doc.top() - 100 - titleHeightEstimate - 20;
        canvas.setColorStroke(new Color(0, 61, 118));
        canvas.setLineWidth(2f);
        float lineWidth = usableWidth * 0.3f;
        canvas.moveTo(leftMargin, lineY);
        canvas.lineTo(leftMargin + lineWidth, lineY);
        canvas.stroke();

        String[] headers = {"Beskrivelse", "Mål (cm)", "Antal", "Enhed"};
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        int columns = headers.length;
        float columnWidth = usableWidth / columns;

        float rowY = lineY - 30;

        for (int i = 0; i < columns; i++) {
            float colCenterX = leftMargin + i * columnWidth + columnWidth / 2;
            ColumnText.showTextAligned(
                    canvas,
                    Element.ALIGN_CENTER,
                    new com.lowagie.text.Phrase(headers[i], headerFont),
                    colCenterX,
                    rowY,
                    0
            );
        }

        float headerLineY = rowY - 10;
        canvas.setLineWidth(1f);
        canvas.moveTo(leftMargin, headerLineY);
        canvas.lineTo(leftMargin + usableWidth, headerLineY);
        canvas.stroke();

        Font rowFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        rowY = headerLineY - 20;
        for (String[] row : rows) {
            for (int i = 0; i < columns; i++) {
                float colCenterX = leftMargin + i * columnWidth + columnWidth / 2;
                ColumnText.showTextAligned(
                        canvas,
                        Element.ALIGN_CENTER,
                        new com.lowagie.text.Phrase(row[i], rowFont),
                        colCenterX,
                        rowY,
                        0
                );
            }
            rowY -= 20;
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