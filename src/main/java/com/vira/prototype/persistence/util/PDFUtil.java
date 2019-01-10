package com.vira.prototype.persistence.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class PDFUtil<T> {

    private BaseFont bNazaninFont;
    private String logo;
    private Map<String,String> headers;
    private Map<String,Boolean> displayInEnglish;
    float[] columnWidths;
    List<String> exportCondition;
    Font bodyFont;
    Font headerFont;

    public PDFUtil(String logo, Map<String,String> headers, Map<String,Boolean> displayInEnglish, float[] columnWidths, List<String> exportCondition) throws IOException, DocumentException {
        this.logo = logo;
        this.headers = headers;
        this.displayInEnglish = displayInEnglish;
        this.columnWidths = columnWidths;
        this.bNazaninFont = BaseFont.createFont("resources/", BaseFont.IDENTITY_H,true);
        this.bodyFont = new Font(bNazaninFont,7);
        this.headerFont = new Font(bNazaninFont,10,Font.BOLD);
    }

    public void export(List<T> data,String fileName) throws DocumentException, NoSuchFieldException, IllegalAccessException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        document.setMargins(20,20,20,20);
        PdfWriter.getInstance(document,outputStream);
        document.addTitle("xxx");
        document.open();

        addPdfHeader(document);

        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

        document.add(exportPDFTable(data,table));

        document.close();
        writePDFToResponse(outputStream);
    }

    private PdfPTable exportPDFTable(List<T> data,PdfPTable table) throws NoSuchFieldException, IllegalAccessException {
        addColumnHeaders(table);
        exportCells(data,table);
        return table;
    }

    public void addPdfHeader(Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(new float[]{20F,80F});
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

        PdfPCell cell = new PdfPCell();
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        document.add(table);

        PdfPTable pdfPTable = new PdfPTable(1);
        for (String conditionValue : exportCondition) {
            PdfPCell conditionCell = new PdfPCell(new Phrase(conditionValue,headerFont));
            conditionCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            conditionCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(conditionCell);
        }

        document.add(pdfPTable);
    }

    public void addColumnHeaders(PdfPTable table){
        PdfPCell indexCell = new PdfPCell(new Phrase("ردیف",headerFont));
        indexCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        indexCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(indexCell);
        for (String header : headers.keySet()) {
            PdfPCell headerCell = new PdfPCell(new Phrase(headers.get(header),headerFont));
            headerCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(headerCell);
        }
    }

    private void exportCells(List<T> data,PdfPTable table) throws NoSuchFieldException, IllegalAccessException {
        Long index = 1L;
        for (T aData : data) {
            addColumnValue(table,index.toString(),bodyFont);
            index++;
            for (String header : headers.keySet()) {
                Field field = aData.getClass().getDeclaredField(header);
                field.setAccessible(true);
                if (displayInEnglish.get(header)) {
                    addColumnValue(table, String.valueOf(field.get(aData)), bodyFont);
                }else{
                    //
                }
            }
        }
    }

    private void addColumnValue(PdfPTable table,String value,Font font){
        PdfPCell cell = new PdfPCell(new Phrase(value,bodyFont));
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
    }

    private void writePDFToResponse(ByteArrayOutputStream outputStream){

    }
}
