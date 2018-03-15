package com.sxk.common.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReadTest {

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        String path = "F:\\lfc\\201801.xlsx";
        ExcelReadTest.readExcel(path);
    }

    public static List<Binglidan> readExcel(String path) throws Exception {
        List<Binglidan> list = new ArrayList<>();
        //2.得到Excel工作簿对象  
        Workbook wb = null;
        try {
            wb = new XSSFWorkbook(path);
        } catch (Exception ex) {
            wb = new HSSFWorkbook(new FileInputStream(path));
        }
        //3.得到Excel工作表对象  
        Sheet sheet = wb.getSheetAt(0);

        //总行数  
        int trLength = sheet.getLastRowNum();
        //4.得到Excel工作表的行  
        Row row = sheet.getRow(0);
        //总列数  
        int tdLength = row.getLastCellNum();

        for (int i = 0; i < trLength + 1; i++) {
            //得到Excel工作表的行  
            Row row1 = sheet.getRow(i);
            if (null == row1) {
                continue;
            }
            System.out.print(i + "===  ");
            for (int j = 0; j < tdLength; j++) {
                if (null != row1.getCell(j)) {
                    row1.getCell(j).setCellType(CellType.STRING);
                    String cellStr = row1.getCell(j).getStringCellValue().trim();
                    if (cellStr.startsWith("20180")) {
                        File f = new File("F:\\lfc\\1月份\\" + cellStr.substring(0, 8));
                        if (!f.exists()) {
                            f.mkdir();
                        }
                        cellStr = String.valueOf((int) row1.getCell(3).getNumericCellValue()) + " " + row1.getCell(4).getStringCellValue().trim();
                        File f2 = new File(f.getPath() + "\\" + cellStr);
                        if (!f2.exists()) {
                            f2.mkdir();
                        }
                    }
                    System.out.print(j + "=" + row1.getCell(j) + "  ");
                }
            }
            System.out.println();
            if (null != row1) {
                String blh = row1.getCell(1).getStringCellValue().trim();
                String name = row1.getCell(1).getStringCellValue().trim();
                Binglidan bld = Binglidan.builder().blh(blh).name(name).build();
                list.add(bld);
            }
        }
        return list;
    }
}

@Data
@Builder
class Binglidan {
    private String blh;
    private String name;
}
