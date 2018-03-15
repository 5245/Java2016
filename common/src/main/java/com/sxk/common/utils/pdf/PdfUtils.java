package com.sxk.common.utils.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.text.PDFTextStripper;

@Slf4j
public class PdfUtils {

    /**
     * 提取文本并保存
     * @param file PDF文档路径
     * @param savePath 文本保存路径
     */
    public static void extractTXT(String file, String savePath) {
        try {
            //打开pdf文件流
            //FileInputStream fis = new FileInputStream(file);
            RandomAccessFile fis = new RandomAccessFile(new File(file), "rw");
            //实例化一个PDF解析器
            PDFParser parser = new PDFParser(fis);
            //解析pdf文档
            parser.parse();
            //获取PDDocument文档对象
            PDDocument document = parser.getPDDocument();
            //获取一个PDFTextStripper文本剥离对象           
            PDFTextStripper stripper = new PDFTextStripper();
            //创建一个输出流
            Writer writer = new OutputStreamWriter(new FileOutputStream(savePath));
            //保存文本内容

            stripper.writeText(document, writer);
            //关闭输出流
            writer.close();
            //关闭输入流
            document.close();
            fis.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }

    /**
     * 提取部分页面文本并保存
     * @param file PDF文档路径
     * @param startPage 开始页数
     * @param endPage 结束页数
     * @param savePath 文本保存路径
     */
    public static void extractTXT(String file, int startPage, int endPage, String savePath) {
        try {
            //打开pdf文件流
            //FileInputStream fis = new FileInputStream(file);
            RandomAccessFile fis = new RandomAccessFile(new File(file), "rw");
            //实例化一个PDF解析器
            PDFParser parser = new PDFParser(fis);
            //解析pdf文档
            parser.parse();
            //获取PDDocument文档对象
            PDDocument document = parser.getPDDocument();
            //获取一个PDFTextStripper文本剥离对象           
            PDFTextStripper stripper = new PDFTextStripper();
            //创建一个输出流
            Writer writer = new OutputStreamWriter(new FileOutputStream(savePath));
            // 设置起始页
            stripper.setStartPage(startPage);
            // 设置结束页
            stripper.setEndPage(endPage);
            //保存文本内容
            stripper.writeText(document, writer);
            //关闭输出流
            writer.close();
            //关闭输入流
            document.close();
            fis.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }

    public static void editPDF(String inputFile, String outputFile, String strToFind, String message) {

        try {
            // pdfwithText  
            PDDocument doc = PDDocument.load(new File(inputFile));
            System.out.println("pageNum:" + doc.getNumberOfPages());
            // Creating a PDF Document
            PDPage firstPage = doc.getPage(0);

            PDFStreamParser parser = new PDFStreamParser(firstPage);

            parser.parse();

            List<Object> tokens = parser.getTokens();
            boolean isReplace = false;
            int j = 0;
            for (; j < tokens.size(); j++) {
                Object next = tokens.get(j);
                if (next instanceof Operator) {
                    Operator op = (Operator) next;
                    if (op.getName().equals("Tj")) {
                        // Tj takes one operator and that is the string    
                        // to display so lets update that operator 
                        COSString previous = (COSString) tokens.get(j - 1);

                        String string = previous.getString();

                        /* if (string.equals("备注")) {
                             isReplace = true;
                         }
                         if (!isReplace) {
                             continue;
                         }*/
                        //System.out.println("before:" + new String(string.getBytes(), "UTF-8"));
                        System.out.println("before:" + decodeUnicode(unicode(string)));
                        string = string.replaceFirst(strToFind, message);
                        System.out.println("after:" + previous.toHexString());
                        //System.out.println("after:" + new String(string.getBytes(), "UTF-8"));

                        //Word you want to change. Currently this code changes word "Solr" to "Solr123"    

                        previous.setValue(string.getBytes());
                        //previous.reset();
                        //previous.append(string.getBytes("GBK"));
                        //previous.append(string.getBytes("UTF-8"));
                        //previous.append(string.getBytes("ISO-8859-1"));
                    } else if (op.getName().equals("TJ")) {
                        COSArray previous = (COSArray) tokens.get(j - 1);
                        for (int k = 0; k < previous.size(); k++) {
                            Object arrElement = previous.getObject(k);
                            if (arrElement instanceof COSString) {
                                COSString cosString = (COSString) arrElement;
                                String string = cosString.getString();
                                /*if (string.equals("备注")) {
                                    isReplace = true;
                                }
                                if (!isReplace) {
                                    continue;
                                }*/
                                //System.out.println("before:" + new String(string.getBytes(), "UTF-8"));
                                string = string.replaceFirst(strToFind, message);
                                //System.out.println("after:" + new String(string.getBytes(), "UTF-8"));

                                cosString.setValue(string.getBytes());
                                // Currently this code changes word "Solr" to "Solr123"  
                                //cosString.reset();
                                //cosString.append(string.getBytes("GBK"));
                                //cosString.append(string.getBytes("UTF-8"));
                                //cosString.append(string.getBytes("ISO-8859-1"));
                            }
                        }
                    }
                }
            }

            System.out.println("j:" + j);
            // now that the tokens are updated we will replace the page content stream.    
            PDStream updatedStream = new PDStream(doc);
            OutputStream out = updatedStream.createOutputStream();
            ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
            tokenWriter.writeTokens(tokens);
            firstPage.setContents(updatedStream);

            out.close();

            doc.save(outputFile); //Output file name    
            doc.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Ascii转换为字符串
     * @param value
     * @return
     */
    public static String asciiToString(String value) {
        try {
            StringBuffer sbu = new StringBuffer();
            String[] chars = value.split(",");
            for (int i = 0; i < chars.length; i++) {
                sbu.append((char) Integer.parseInt(chars[i]));
            }
            return sbu.toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "a";
        }
    }

    public static String unicode(String source) {
        StringBuffer sb = new StringBuffer();
        char[] source_char = source.toCharArray();
        String unicode = null;
        for (int i = 0; i < source_char.length; i++) {
            unicode = Integer.toHexString(source_char[i]);
            if (unicode.length() <= 2) {
                unicode = "00" + unicode;
            }
            sb.append("\\u" + unicode);
        }
        System.out.println(sb);
        return sb.toString();
    }

    public static String decodeUnicode(String unicode) {
        try {
            StringBuffer sb = new StringBuffer();

            String[] hex = unicode.split("\\\\u");

            for (int i = 1; i < hex.length; i++) {
                int data = Integer.parseInt(hex[i], 16);
                sb.append((char) data);
            }
            return sb.toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "a";
        }
    }

    public static void pdf2Word(String inputFile, String outputFile) {

    }

    public static void main(String args[]) throws Exception {
        String file = "D:\\a_back\\10.pdf";
        String savePath = "D:\\a_back\\result1.txt";
        long startTime = System.currentTimeMillis();
        extractTXT(file, savePath);
        long endTime = System.currentTimeMillis();
        editPDF("D:\\a_back\\10.pdf", "D:\\a_back\\10_01.pdf", "快车", "出租车");
        //doIt("D:\\a_back\\10.pdf", "D:\\a_back\\10_02.pdf", "滴滴出行", "出行");
        System.out.println("读写所用时间为：" + (endTime - startTime) + "ms");
    }
}
