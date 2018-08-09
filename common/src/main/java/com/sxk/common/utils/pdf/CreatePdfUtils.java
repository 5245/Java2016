//package com.sxk.common.utils.pdf;
//
//import java.io.ByteArrayOutputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Iterator;
//
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.pdf.AcroFields;
//import com.itextpdf.text.pdf.PdfCopy;
//import com.itextpdf.text.pdf.PdfImportedPage;
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.PdfStamper;
//
//public class CreatePdfUtils {
//    // 利用模板生成pdf
//    public static void fillTemplate() {
//        // 模板路径
//        String templatePath = "D:\\a_back\\10.pdf";
//        // 生成的新文件路径
//        String newPDFPath = "D:\\a_back\\10_01.pdf";
//        PdfReader reader;
//        FileOutputStream out;
//        ByteArrayOutputStream bos;
//        PdfStamper stamper;
//        try {
//            out = new FileOutputStream(newPDFPath);// 输出流
//            reader = new PdfReader(templatePath);// 读取pdf模板
//            bos = new ByteArrayOutputStream();
//            stamper = new PdfStamper(reader, bos);
//            AcroFields form = stamper.getAcroFields();
//
//            String[] str = { "123456789", "TOP__ONE", "男", "1991-01-01", "130222111133338888", "河北省保定市" };
//            int i = 0;
//            Iterator<String> it = form.getFields().keySet().iterator();
//            while (it.hasNext()) {
//                String name = it.next().toString();
//                System.out.println(name);
//                form.setField(name, str[i++]);
//            }
//            stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true
//            stamper.close();
//
//            Document doc = new Document();
//            PdfCopy copy = new PdfCopy(doc, out);
//            doc.open();
//            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
//            copy.addPage(importPage);
//            doc.close();
//            System.out.println("success");
//        } catch (IOException e) {
//            System.out.println(1);
//        } catch (DocumentException e) {
//            System.out.println(2);
//        }
//
//    }
//
//    public static void main(String[] args) {
//        fillTemplate();
//    }
//}
