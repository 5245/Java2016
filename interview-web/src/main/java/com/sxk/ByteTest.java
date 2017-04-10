package com.sxk;

import java.io.UnsupportedEncodingException;

public class ByteTest {
    public static void main(String[] args) throws Exception {
        ByteTest bt = new ByteTest();
        bt.test1();
    }

    public void test1() throws UnsupportedEncodingException {
        String a = "名";
        System.out.println("默认编码长度:" + a.getBytes().length);
        System.out.println("Unicode编码长度:" + a.getBytes("Unicode").length);
        System.out.println("UTF-8编码长度:" + a.getBytes("UTF-8").length);
        System.out.println("GBK编码长度:" + a.getBytes("GBK").length);
        System.out.println("GB2312编码长度:" + a.getBytes("GB2312").length);
        System.out.println("==========================================");

        String c = "0x20001";
        System.out.println("UTF-8编码长度:" + c.getBytes("UTF-8").length);
        System.out.println("GBK编码长度:" + c.getBytes("GBK").length);
        System.out.println("GB2312编码长度:" + c.getBytes("GB2312").length);
        System.out.println("==========================================");

        char[] arr = Character.toChars(0x20001);
        String s = new String(arr);
        System.out.println("char array length:" + arr.length);
        System.out.println("content:|  " + s + " |");
        System.out.println("String length:" + s.length());
        System.out.println("UTF-8编码长度:" + s.getBytes("UTF-8").length);
        System.out.println("GBK编码长度:" + s.getBytes("GBK").length);
        System.out.println("GB2312编码长度:" + s.getBytes("GB2312").length);
        System.out.println("==========================================");
    }
}
