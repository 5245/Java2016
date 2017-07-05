package com.sxk.common.utils.excel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelBaseUtils {

    //默认字体  
    public static String          DEFAULT_FONT           = "微软雅黑";
    //excel默认宽度；  
    public static int             width                  = 256 * 14;
    public static final int       DEFAULT_CELL_HEIGHT    = 18;
    public static final int       DEFAULT_CELL_WIDTH     = 9;

    public static final int       FOOTER_ROW_SIZE        = 3;
    public static final int       HEAD_ROW_SIZE          = 4;
    public static final int       COL_SIZE               = 9;

    protected static final String valuePlaceholderString = "--";
    protected CellStyle           titleCellStyle;
    protected CellStyle           subTitleCellStyle;
    protected CellStyle           normalCellStyle;
    protected CellStyle           numberCellStyle;

    /**  
     * 对文件流输出下载的中文文件名进行编码 屏蔽各种浏览器版本的差异性  
     * @throws UnsupportedEncodingException   
     */
    protected static String encodeChineseDownloadFileName(HttpServletRequest request, String pFileName) throws UnsupportedEncodingException {
        String filename = null;
        String agent = request.getHeader("USER-AGENT");
        if (null != agent) {
            if (-1 != agent.indexOf("Firefox")) {//Firefox      
                filename = "=?UTF-8?B?" + (new String(Base64.encodeBase64(pFileName.getBytes("UTF-8")))) + "?=";
            } else if (-1 != agent.indexOf("Chrome")) {//Chrome      
                filename = new String(pFileName.getBytes(), "ISO8859-1");
            } else {//IE7+      
                filename = URLEncoder.encode(pFileName, "UTF-8");
                filename = filename.replace("+", "%20");
            }
        } else {
            filename = pFileName;
        }
        return filename;
    }

    protected void initStyle(Sheet excelSheet, Workbook workbook) {
        titleCellStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleCellStyle.setFont(titleFont);
        titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        setBorder(titleCellStyle);

        subTitleCellStyle = workbook.createCellStyle();
        Font subtitleFont = workbook.createFont();
        subtitleFont.setFontHeightInPoints((short) 18);
        subtitleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        subTitleCellStyle.setFont(titleFont);
        subTitleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        subTitleCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        setBorder(subTitleCellStyle);

        normalCellStyle = workbook.createCellStyle();
        normalCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        normalCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        normalCellStyle.setWrapText(true);
        setBorder(normalCellStyle);

        numberCellStyle = workbook.createCellStyle();
        numberCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        numberCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        numberCellStyle.setWrapText(true);
        DataFormat format = workbook.createDataFormat();
        numberCellStyle.setDataFormat(format.getFormat("0.00"));
        setBorder(numberCellStyle);

        excelSheet.setDefaultRowHeightInPoints(DEFAULT_CELL_HEIGHT);
        excelSheet.setDefaultColumnWidth(DEFAULT_CELL_WIDTH);
    }

    protected static void setBorder(CellStyle cellStyle) {

        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
    }
}
