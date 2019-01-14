package fx.booking.helpers;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import javafx.stage.FileChooser;

public class DocumentGenerator {
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    public void generateDocument(Map<String, Object> documentInfo) {
        try{
            Document document = new Document();
            String fileToSavePath = new FileChooser().showSaveDialog(null).toString() + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileToSavePath));
            document.open();
            addMetaData(document);
            addTitlePage(document, documentInfo);
            document.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void addMetaData(Document document) {
        document.addTitle("Faktura");
        document.addAuthor("BookingFX");
        document.addCreator("BookingFX");
    }

    private static void addTitlePage(Document document, Map<String, Object> documentInfo)
            throws DocumentException {
        Paragraph preface = new Paragraph();

        addEmptyLine(preface, 1);

        String documentDate = documentInfo.get("DATA_WYSTAWIENIA").toString().split("\\s+")[0];

        Paragraph date = new Paragraph(documentDate, redFont);
        date.setAlignment(Element.ALIGN_RIGHT);
        preface.add(date);

        addEmptyLine(preface, 4);

        Paragraph title = new Paragraph("Faktura nr " + documentInfo.get("NR_FAKTURA"), smallBold);
        title.setAlignment(Element.ALIGN_CENTER);
        preface.add(title);

        addEmptyLine(preface, 4);

        String buyerName = documentInfo.get("IMIE").toString() + " " + documentInfo.get("NAZWISKO").toString();

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.addCell(getCell("Sprzedawca:", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("Nabywca:", PdfPCell.ALIGN_RIGHT));
        table.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
        table.addCell(getCell("BookingFX", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell(buyerName, PdfPCell.ALIGN_RIGHT));
        table.addCell(getCell("admin@bookingfx.cba.pl", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell(documentInfo.get("EMAIL").toString(), PdfPCell.ALIGN_RIGHT));
        table.addCell(getCell("111222333", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell(documentInfo.get("NR_TEL").toString(), PdfPCell.ALIGN_RIGHT));
        preface.add(table);

        addEmptyLine(preface, 4);

        PdfPTable table2 = new PdfPTable(7);
        table2.setWidthPercentage(100);
        table2.setWidths(new float[] {1, 5, 3, 2, 3, 3, 3});

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

        DecimalFormat df = new DecimalFormat("###.00");

        table2.addCell(getCell2("1"));
        table2.addCell(getCell2("Rezerwacja nr " + documentInfo.get("ID_REZERWACJA").toString()));
        table2.addCell(getCell2(df.format((((BigDecimal) documentInfo.get("KWOTA_FAKTURY")).multiply(new BigDecimal("0.77"))))));
        table2.addCell(getCell2("23%"));
        table2.addCell(getCell2(df.format((((BigDecimal) documentInfo.get("KWOTA_FAKTURY")).multiply(new BigDecimal("0.77"))))));
        table2.addCell(getCell2(df.format((((BigDecimal) documentInfo.get("KWOTA_FAKTURY")).multiply(new BigDecimal("0.23"))))));
        table2.addCell(getCell2(df.format(documentInfo.get("KWOTA_FAKTURY"))));

        preface.add(table2);

        addEmptyLine(preface, 4);

        preface.add(getParagraph("Razem: " + df.format(documentInfo.get("KWOTA_FAKTURY")) + " " + documentInfo.get("WALUTA")));

        document.add(preface);

        document.newPage();
    }

    private static Paragraph getParagraph(String text){
        Paragraph paragraph = new Paragraph(text);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        return paragraph;
    }

    private static PdfPCell getCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    private static PdfPCell getCell2(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPaddingTop(2);
        cell.setPaddingBottom(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
