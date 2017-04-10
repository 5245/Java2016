package com.sxk.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileReadTest {

    public static void main(String[] args) {
        FileReadTest t = new FileReadTest();
        String rootUrl = System.getProperty("user.dir") + "/" + args[0];
        System.out.println("rootUrl:" + rootUrl);
        File root = new File(rootUrl);
        t.readWriteFile(root);
    }

    private void readWriteFile(File parent) {
        if (parent.isDirectory()) {
            File[] files = parent.listFiles();
            for (File file : files) {
                readWriteFile(file);
            }
        } else {
            BufferedReader bre = null;
            BufferedWriter bw = null;//定义一个流
            try {
                String code = getCodeFromFile(parent);
                System.out.println("code:" + code);
                InputStreamReader isr = new InputStreamReader(new FileInputStream(parent), code);
                StringBuffer sbread = new StringBuffer();
                while (isr.ready()) {
                    sbread.append((char) isr.read());
                }
                isr.close();
                String str = sbread.toString();
                // 从构造器中生成字符串，并替换搜索文本  
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(parent), "UTF-8");
                out.write(str.toCharArray());
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件编码格式
     * @param file
     * @return
     * @throws Exception
     *
     */
    public static String getCodeFromFile(File file) throws Exception {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
        int p = (bin.read() << 8) + bin.read();
        String code = null;

        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }
        return code;
    }

}
