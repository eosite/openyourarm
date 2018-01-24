package com.glaf.conver.excel2pdf.model;

import com.itextpdf.text.pdf.BaseFont;

public class Resource {
    /**
     * 中文字体支持
     */
    public static BaseFont BASE_FONT_CHINESE;
    static {
        try {
            BASE_FONT_CHINESE = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

