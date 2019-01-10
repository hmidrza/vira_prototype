package com.vira.prototype.persistence.util;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import org.apache.poi.util.IOUtils;

import java.io.IOException;

public enum PersianBaseFont {

    INSTANCE;

    private static final String FONT_PATH = "/fonts/";

    public static BaseFont B_NAZANIN;
    public static BaseFont B_MITRA;

    static {
        try {

            byte[] bytes = IOUtils.toByteArray(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(FONT_PATH + "Bnazanin.ttf"));
            B_NAZANIN = BaseFont.createFont("Bnazanin.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, bytes, null);
            B_MITRA = BaseFont.createFont();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

}
