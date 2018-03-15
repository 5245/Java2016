/*package com.sxk.common.utils.pdf;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.PDFOperator;

@Slf4j
public class PdfEditTest {
    public static void main(String[] args) throws Exception {
        String file = "D:\\a_back\\10.pdf";
        String savePath = "D:\\a_back\\result1.txt";

        Map<String, String> maps = new HashMap<>();
        PDDocument document = PDDocument.load(new File(file));
        PDPageTree pages = document.getDocumentCatalog().getPages();
        boolean begin = false;
        // int postion = -1;
        LinkedList<Integer> postions = new LinkedList<>(); // 用于存放需要clear 的 坐标
        LinkedList<COSString> cosStrings = new LinkedList<>();

        StringBuilder test = new StringBuilder();
        for (PDPage pdPage : pages) {

            Iterator<PDStream> ite = pdPage.getContentStreams();

            PDStream pdStream = ite.next();

            PDFStreamParser parser = new PDFStreamParser(pdStream);

            parser.parse();
            List tokens = parser.getTokens();
            StringBuilder sb = new StringBuilder();
            for (int i = 0, len = tokens.size(); i < len; i++) {
                Object object = tokens.get(i);

                if (object instanceof Operator) {
                    Operator op = (Operator) object;
                    if (op.getName().equals("Tj")) {

                        COSString previous = (COSString) tokens.get(i - 1);
                        if (previous.getString().indexOf("(") != -1) {
                            postions.addFirst(i - 1);
                            sb.append(previous.getString().replaceFirst("\\{", ""));
                            continue;
                        } else if (previous.getString().indexOf(")") != -1) {
                            sb.append(previous.getString().replaceFirst("\\}", ""));

                            // 刷新缓冲区
                            for (int position : postions) {
                                COSString optation = (COSString) tokens.get(position);
                                optation.reset();
                            }
                            COSString optation = (COSString) tokens.get(i - 1);
                            optation.reset();
                            COSString replace = (COSString) tokens.get(postions.getFirst());
                            String value = maps.get(sb.toString());
                            if (value != null) {
                                System.out.println(value);
                                replace.append(value.getBytes("UTF-8"));
                            }

                            postions.clear();
                            sb = new StringBuilder();
                            continue;
                        } else if (!postions.isEmpty()) {
                            postions.addLast(i - 1);
                        }
                    }

                    else if ("TJ".equals(op.getName())) {
                        COSArray previous = (COSArray) tokens.get(i - 1);

                        for (int j = 0, lenj = previous.size(); j < lenj; j++) {
                            Object arrayElement = previous.get(j);
                            if (arrayElement instanceof COSString) {
                                COSString cosString = (COSString) arrayElement;
                                String cosValue = cosString.getString();
                                if (cosValue.indexOf("{") != -1) {
                                    System.out.println(i + cosString.getString());
                                    cosStrings.addFirst(cosString);
                                }
                                if (cosValue.indexOf("}") != -1) {
                                    System.out.println(i + cosString.getString());
                                    cosStrings.addLast(cosString);
                                    replace(maps, cosStrings);

                                    continue;

                                } else if (!cosStrings.isEmpty()) {
                                    cosStrings.add(cosString);
                                }
                            }
                        }
                    }

                }
            }
            // 修改流
            PDStream updatePdStream = new PDStream(document);
            // 输出流
            OutputStream outputStream = updatePdStream.createOutputStream();
            // 主题写流
            ContentStreamWriter tokenWriter = new ContentStreamWriter(outputStream);
            tokenWriter.writeTokens(tokens);
            // 设置修改流到当前 页
            pdPage.setContents(updatePdStream);

        }
        document.save(savePath);
    }

    private static void replace(Map<String, String> maps, LinkedList<COSString> cosStrings) throws IOException, UnsupportedEncodingException {
        //解析key
        //String key= getKey(cosStrings);
        String key = "";
        System.out.println(key);
        //获取值
        String value = maps.get(key);
        //填充值
        if (value != null) {
            //字符编码都以测试过无效 包括value.getBytes("UTF-8") 之类的替换 
            cosStrings.get(i).append(value.getBytes());
        } else {
            cosStrings.getFirst().append(value.getBytes("UTF-8"));
        }
        //清空集合
        cosStrings.clear();
    }

}
*/