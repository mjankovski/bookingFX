package fx.booking;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class DocumentGenerator {
    private static String FILE = "Testowy.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    public void generateDocument() {
        try {
            Map<String, String> documentInfo = new HashMap<>();
            documentInfo.put("date","08.12.2018r.");
            documentInfo.put("number","10");
            documentInfo.put("buyerName","Adam Abacki");
            documentInfo.put("buyerEmail","adam@abacki.pl");
            documentInfo.put("buyerPhone","555666777");
            documentInfo.put("resNumber","17");
            documentInfo.put("resBrutto", "1200");
            double netto = Double.valueOf(documentInfo.get("resBrutto"))*0.77;
            double vat = Double.valueOf(documentInfo.get("resBrutto"))*0.23;
            documentInfo.put("resNetto", Double.toString(netto));
            documentInfo.put("resVat", Double.toString(vat));
            documentInfo.put("currency", "PLN");
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
            addTitlePage(document, documentInfo);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addMetaData(Document document) {
        document.addTitle("Faktura");
        document.addAuthor("BookingFX");
        document.addCreator("BookingFX");
    }

    private static void addTitlePage(Document document, Map<String, String> documentInfo)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        Paragraph date = new Paragraph(documentInfo.get("date"), redFont);
        date.setAlignment(Element.ALIGN_RIGHT);
        preface.add(date);

        addEmptyLine(preface, 4);

        Paragraph title = new Paragraph("Faktura nr " + documentInfo.get("number"), smallBold);
        title.setAlignment(Element.ALIGN_CENTER);
        preface.add(title);

        addEmptyLine(preface, 4);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.addCell(getCell("Sprzedawca:", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("Nabywca:", PdfPCell.ALIGN_RIGHT));
        table.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
        table.addCell(getCell("BookingFX", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell(documentInfo.get("buyerName"), PdfPCell.ALIGN_RIGHT));
        table.addCell(getCell("admin@bookingfx.cba.pl", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell(documentInfo.get("buyerEmail"), PdfPCell.ALIGN_RIGHT));
        table.addCell(getCell("111222333", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell(documentInfo.get("buyerPhone"), PdfPCell.ALIGN_RIGHT));
        preface.add(table);

        addEmptyLine(preface, 4);

        PdfPTable table2 = new PdfPTable(7);
        table2.setWidthPercentage(100);
        table2.setWidths(new float[] {1, 5, 3, 2, 3, 3, 3});

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Lp"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c1);

        c1 = new PdfPCell(new Phrase("Nazwa uslugi"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c1);

        c1 = new PdfPCell(new Phrase("Cena netto"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c1);

        c1 = new PdfPCell(new Phrase("Stawka VAT"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c1);

        c1 = new PdfPCell(new Phrase("Wartosc netto"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c1);

        c1 = new PdfPCell(new Phrase("Wartosc VAT"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c1);

        c1 = new PdfPCell(new Phrase("Wartosc brutto"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c1);

        table2.setHeaderRows(1);

        table2.addCell(getCell2("1", PdfPCell.ALIGN_CENTER));
        table2.addCell(getCell2("Rezerwacja nr " + documentInfo.get("resNumber"), PdfPCell.ALIGN_CENTER));
        table2.addCell(getCell2(documentInfo.get("resNetto"), PdfPCell.ALIGN_CENTER));
        table2.addCell(getCell2("23%", PdfPCell.ALIGN_CENTER));
        table2.addCell(getCell2(documentInfo.get("resNetto"), PdfPCell.ALIGN_CENTER));
        table2.addCell(getCell2(documentInfo.get("resVat"), PdfPCell.ALIGN_CENTER));
        table2.addCell(getCell2(documentInfo.get("resBrutto"), PdfPCell.ALIGN_CENTER));

        preface.add(table2);

        addEmptyLine(preface, 4);

        preface.add(getParagraph("Razem: " + documentInfo.get("resBrutto") + " " + documentInfo.get("currency") , Element.ALIGN_RIGHT));

        document.add(preface);
        // Start a new page
        document.newPage();
    }

    private static Paragraph getParagraph(String text, int align){
        Paragraph paragraph = new Paragraph(text);
        paragraph.setAlignment(align);
        return paragraph;
    }

    private static PdfPCell getCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    private static PdfPCell getCell2(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPaddingTop(2);
        cell.setPaddingBottom(5);
        cell.setHorizontalAlignment(alignment);
        return cell;
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
