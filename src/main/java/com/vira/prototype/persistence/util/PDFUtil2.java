package com.vira.prototype.persistence.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.vira.prototype.persistence.model.Car;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PDFUtil2 {

    private final List<String> fieldNames;

    private final String fileName;

    private byte[] bytes;

    private Document document;

    private final Font headerFont;

    private final String rowIndexTitle;

    private final Font bodyFont;

    private PdfPTable table;

    private final List data;

    private final Rectangle pageSize;

    private final int textDirection;

    private ByteArrayOutputStream outputStream;

    public byte[] getBytes() {
        return bytes;
    }

    private PDFUtil2(Builder builder) {
        fieldNames = builder.fieldNames;
        fileName = builder.fileName;
        headerFont = builder.headerFont;
        bodyFont = builder.bodyFont;
        data = builder.data;
        pageSize = builder.pageSize;
        textDirection = builder.textDirection;
        rowIndexTitle = builder.rowIndexTitle;
    }

    public static class Builder {

        private List<String> fieldNames = new ArrayList<>();

        private String fileName = "";

        private Font headerFont;

        private int textDirection;

        private Rectangle pageSize = PageSize.A4;

        private String rowIndexTitle = "ردیف";

        private Font bodyFont;

        private List data = new ArrayList();

        public Builder addFieldName(String fieldName) {
            this.fieldNames.add(fieldName);
            return this;
        }

        public Builder setTextDirection(int textDirection) {
            this.textDirection = textDirection;
            return this;
        }

        public Builder setRowIndexTitle(String rowIndexTitle) {
            this.rowIndexTitle = rowIndexTitle;
            return this;
        }

        public Builder addFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder setPageSize(Rectangle pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder setHeaderFont(Font headerFont) {
            this.headerFont = headerFont;
            return this;
        }

        public Builder setBodyFont(Font bodyFont) {
            this.bodyFont = bodyFont;
            return this;
        }

        public Builder(List data) {
            this.data = data;
        }

        public PDFUtil2 build() {
            PDFUtil2 pdfUtil2 = new PDFUtil2(this);
            pdfUtil2.createFile();
            return pdfUtil2;
        }
    }

    private void createFile() {
        try {
            if (fieldNames.size() == data.toArray().length) {
                prepareFile();
                writeHeader();
                writeData();
                writeFile(outputStream);
            }else{
                throw new DocumentException("filed and data size are not same");
            }
        } catch (DocumentException e) {
            System.out.println(" error creating pdf " + e.getMessage());
        }
    }

    private void prepareFile() throws DocumentException {
        outputStream = new ByteArrayOutputStream();
        document = new Document(PageSize.A4);
//        try {
            PdfWriter.getInstance(document, outputStream);
//            PdfWriter.getInstance(document, outputStream);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        document.setMargins(20, 20, 20, 20);
        document.addTitle(fileName);
        document.open();
    }

    private void writeData() throws DocumentException {
        long rowNum = 1L;
        for (Object o : data.toArray()) {
            table.addCell(createCell(String.valueOf(rowNum++)));
//            for (Object field : ((Object[]) o)) {
//                table.addCell(createCell(((String) field)));
//            }
            table.addCell(createCell(String.valueOf(((Car) o).getYear())));
            table.addCell(createCell(((Car) o).getName()));
            table.addCell(createCell(((Car) o).getColor()));
        }

        document.add(table);
    }

    private void writeHeader() throws DocumentException {
        table = new PdfPTable(fieldNames.size() + 1);
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        table.setRunDirection(textDirection);
        table.addCell(createCell(rowIndexTitle));
        for (String header : fieldNames) {
            table.addCell(createCell(header));
        }
    }

    private void writeFile(ByteArrayOutputStream outputStream) {
        document.close();
        this.bytes = outputStream.toByteArray();
    }

    private PdfPCell createCell(String phraseText) {
        PdfPCell cell = new PdfPCell(new Phrase(phraseText,headerFont));
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }
}
