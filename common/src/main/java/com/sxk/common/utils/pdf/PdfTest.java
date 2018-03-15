/*package com.sxk.common.utils.pdf;

import java.io.File;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.PDFOperator;

public class PdfTest {

    private static String toRealCNString(String cn, String encoding) throws UnsupportedEncodingException {
        byte[] bytes = cn.getBytes("ISO-8859-1");
        return new String(bytes, encoding);
    }

    private static String toCNString(String cn, String encoding) throws UnsupportedEncodingException {
        byte[] copy = getCNBytes(cn, encoding);
        return new String(copy, "ISO-8859-1");
    }

    private static byte[] getCNBytes(String replaceKey, String encoding) throws UnsupportedEncodingException {
        byte[] bytes = replaceKey.getBytes(encoding);
        byte[] copy = new byte[bytes.length - 2];
        for (int i = 0; i < copy.length; ++i) {
            copy[i] = bytes[i + 2];
        }
        return copy;
    }

    public static void doIt(String sourceFile, String outputFile, String strToFind, String message) throws Exception {
        String flag = "$";
        //char[] chars = message.toCharArray();
        Map<String, String> chars = new HashMap<>();

        PDDocument pdDocument = PDDocument.load(new File(sourceFile));
        for (Object obj : pdDocument.getDocumentCatalog().getAllPages()) {
            PDPage page = (PDPage) obj;
            PDFStreamParser parser = new PDFStreamParser(page.getContents().getStream());
            parser.parse();
            List<Object> tokens = parser.getTokens();
            int status = 0;
            String replaceKey = null;
            String s0 = toCNString("％", "utf-16");//判断中文替换标志
            String s1 = toCNString("｝", "utf-16");//判断中文结尾标志

            for (int j = 0; j < tokens.size(); j++) {
                Object next = tokens.get(j);
                if (next instanceof PDFOperator) {
                    PDFOperator op = (PDFOperator) next;
                    *//************** new **************//*
                    try {
                        Object pre = tokens.get(j - 1);
                        if (pre instanceof COSString) {
                            COSString previousString = (COSString) pre;
                            String string = previousString.getString();
                            if (string.contains(flag)) {
                                for (String key : chars.keySet()) {
                                    if (string.indexOf(key) < 0) {
                                        continue;
                                    }
                                    string = string.replace(key, chars.get(key));
                                }
                                previousString.reset();
                                previousString.append(string.getBytes("UTF-8"));
                            }
                            if (string.contains(s0)) {
                                replaceKey = string;
                                status = 3;
                            }
                            if (status == 3) {
                                if (!string.contains(s0)) {
                                    replaceKey += string;
                                }
                                previousString.reset();
                            }
                            if (replaceKey != null && replaceKey.contains(s1)) {
                                replaceKey = toRealCNString(replaceKey, "utf-16");
                                System.out.println(replaceKey);
                                for (String key : chars.keySet()) {
                                    if (replaceKey.indexOf(key) < 0) {
                                        continue;
                                    }
                                    replaceKey = replaceKey.replace(key, chars.get(key));
                                }
                                byte[] copy = getCNBytes(replaceKey, "utf-16");
                                previousString.append(copy);
                                status = 0;
                                replaceKey = null;
                            }
                        } else if (pre instanceof COSArray) {
                            COSArray previousArray = (COSArray) pre;
                            for (int k = 0; k < previousArray.size(); k++) {
                                Object arrElement = previousArray.getObject(k);
                                if (arrElement instanceof COSString) {
                                    COSString cosString = (COSString) arrElement;
                                    String string = cosString.getString();

                                    if (string.contains(flag)) {
                                        replaceKey = string;
                                        status = 1;
                                    }
                                    if (status == 1) {
                                        if (!string.contains(flag)) {
                                            replaceKey += string;
                                        }
                                        cosString.reset();
                                    }
                                    if (replaceKey != null && replaceKey.contains("}")) {
                                        System.out.println(replaceKey);
                                        for (String key : chars.keySet()) {
                                            if (replaceKey.indexOf(key) < 0) {
                                                continue;
                                            }
                                            replaceKey = replaceKey.replace(key, chars.get(key));
                                        }

                                        cosString.append(replaceKey.getBytes("ascii"));
                                        status = 0;
                                        replaceKey = null;
                                    }

                                    if (string.contains(s0)) {
                                        replaceKey = string;
                                        status = 2;
                                    }
                                    if (status == 2) {
                                        if (!string.contains(s0)) {
                                            replaceKey += string;
                                        }
                                        cosString.reset();
                                    }
                                    if (replaceKey != null && replaceKey.contains(s1)) {
                                        replaceKey = toRealCNString(replaceKey, "utf-16");
                                        System.out.println(replaceKey);
                                        for (String key : chars.keySet()) {
                                            if (replaceKey.indexOf(key) < 0) {
                                                continue;
                                            }
                                            replaceKey = replaceKey.replace(key, chars.get(key));
                                        }
                                        byte[] copy = getCNBytes(replaceKey, "utf-16");
                                        cosString.append(copy);
                                        status = 0;
                                        replaceKey = null;
                                    }
                                }
                            }

                        }
                    } catch (Exception e2) {
                        System.out.println(op.getOperation());
                        continue;
                    }
                }
            }
            PDStream updatedStream = new PDStream(pdDocument);
            OutputStream out = updatedStream.createOutputStream();
            ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
            tokenWriter.writeTokens(tokens);
            page.setContents(updatedStream);
            pdDocument.save(outputFile);
        }
        pdDocument.close();
    }
}
*/