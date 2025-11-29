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

    public static void generateOfferPdf(
            String outputPath,
            String frontImagePath,
            String orderId,
            double width,
            double length,
            double height,
            String roof,
            double totalPrice,
            boolean hasToolShed,
            Double toolShedWidth,
            Double toolShedLength,
            String page2ImagePath,
            String page3ImagePath) throws Exception {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));

        document.open();
        addFullPageImage(document, frontImagePath);
        generateOfferPage(document, orderId, width, length, height, roof, totalPrice, hasToolShed, toolShedWidth, toolShedLength);
        addFullPageImage(document, page2ImagePath);
        addFullPageImage(document, page3ImagePath);
        document.close();
    }

    // _________________________________________________________________

    public static void addFullPageImage(Document doc, String path) throws Exception {
        doc.newPage();
        Image img = Image.getInstance(path);
        img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        img.setAbsolutePosition((PageSize.A4.getWidth() - img.getScaledWidth()) / 2, (PageSize.A4.getHeight() - img.getScaledHeight()) / 2);
        doc.add(img);
    }

    // _________________________________________________________________

    public static void addTextPage(Document doc, String text) throws Exception {
        doc.newPage();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 16);
        Paragraph p = new Paragraph(text, font);
        doc.add(p);
    }

    // _________________________________________________________________

    public static void generateOfferPage(Document doc, String orderId, double width, double length, double height, String roof, double totalPrice, boolean hasToolShed, Double toolShedWidth, Double toolShedLength) throws Exception {
        doc.newPage();
        
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
        Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        Font priceFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        
        Paragraph title = new Paragraph("Tilbud på carport", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(30);
        doc.add(title);
        
        Paragraph orderInfo = new Paragraph("Ordre nr: " + orderId, normalFont);
        orderInfo.setAlignment(Element.ALIGN_CENTER);
        orderInfo.setSpacingAfter(20);
        doc.add(orderInfo);
        
        Paragraph specsTitle = new Paragraph("Specifikationer", headingFont);
        specsTitle.setSpacingBefore(20);
        specsTitle.setSpacingAfter(15);
        doc.add(specsTitle);
        
        com.lowagie.text.Table table = new com.lowagie.text.Table(2);
        table.setPadding(5);
        table.setSpacing(0);
        table.setBorderWidth(1);
        table.setWidth(100);
        table.setWidths(new float[]{40, 60});
        
        table.addCell(new com.lowagie.text.Cell(new Phrase("Bredde:", normalFont)));
        table.addCell(new com.lowagie.text.Cell(new Phrase(String.format("%.0f cm", width), normalFont)));
        
        table.addCell(new com.lowagie.text.Cell(new Phrase("Længde:", normalFont)));
        table.addCell(new com.lowagie.text.Cell(new Phrase(String.format("%.0f cm", length), normalFont)));
        
        table.addCell(new com.lowagie.text.Cell(new Phrase("Højde:", normalFont)));
        table.addCell(new com.lowagie.text.Cell(new Phrase(String.format("%.0f cm", height), normalFont)));
        
        table.addCell(new com.lowagie.text.Cell(new Phrase("Tag:", normalFont)));
        table.addCell(new com.lowagie.text.Cell(new Phrase(roof, normalFont)));
        
        if (hasToolShed && toolShedWidth != null && toolShedLength != null) {
            table.addCell(new com.lowagie.text.Cell(new Phrase("Redskabsskur:", normalFont)));
            table.addCell(new com.lowagie.text.Cell(new Phrase(String.format("%.0f x %.0f cm", toolShedWidth, toolShedLength), normalFont)));
        }
        
        doc.add(table);
        
        Paragraph priceTitle = new Paragraph("Total pris", headingFont);
        priceTitle.setSpacingBefore(30);
        priceTitle.setSpacingAfter(10);
        doc.add(priceTitle);
        
        Paragraph price = new Paragraph(String.format("%.2f kr", totalPrice), priceFont);
        price.setSpacingAfter(20);
        doc.add(price);
        
        Paragraph note = new Paragraph("Dette tilbud er gyldigt i 30 dage.", normalFont);
        note.setSpacingBefore(20);
        doc.add(note);
    }

    // _________________________________________________________________

    public static void addPageWithBackgroundAndText(Document doc, String backgroundImagePath, String text, Font textFont) throws Exception {
        doc.newPage();

        Image bg = Image.getInstance(backgroundImagePath);
        bg.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        bg.setAbsolutePosition(
                (PageSize.A4.getWidth() - bg.getScaledWidth()) / 2,
                (PageSize.A4.getHeight() - bg.getScaledHeight()) / 2
        );
        doc.add(bg);

        Paragraph p = new Paragraph(text, textFont);
        p.setAlignment(Element.ALIGN_LEFT);
        p.setSpacingBefore(100);
        doc.add(p);

    }

    // _________________________________________________________________

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

} // PdfGenerator end