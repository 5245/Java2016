package com.sxk;

import java.io.UnsupportedEncodingException;

public class ByteTest {
    /* 
     * 
         【强制】所有的相同类型的包装类对象之间值的比较，全部使用 equals 方法比较。
         说明：对于 Integer var =?在-128 至 127 之间的赋值， Integer 对象是在
         IntegerCache . cache 产生， 会复用已有对象， 这个区间内的 Integer 值可以直接使用==进行
         判断， 但是这个区间之外的所有数据， 都会在堆上产生， 并不会复用已有对象， 这是一个大坑，
         推荐使用 equals 方法进行判断
    *
    */

    public static void main(String[] args) throws Exception {
        ByteTest bt = new ByteTest();
        bt.test1();
        Integer var = 220;
        Integer var2 = 220;
        System.out.println(var.equals(220));
        System.out.println(var == var2);
        System.out.println(var == 220);

        String str = "a,b,c,,";
        String[] ary = str.split(",");
        //预期大于 3，结果是 3
        System.out.println(ary.length);

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
