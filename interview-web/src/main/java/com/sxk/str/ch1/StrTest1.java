package com.sxk.str.ch1;
/**
 * 
 * @description 字符串翻转 
 * @author sxk
 * @email sxk5245@126.com
 * @date 2017年9月7日
 */
public class StrTest1 {
    public static void main(String[] args) {
        String str1 = "abcdefghijklmnopqrstuvwxyz" + "";
        StringBuilder sb = new StringBuilder();
        for (int i = str1.length() - 1; i >= 0; i--) {
            sb.append(str1.charAt(i));
        }
        System.out.println(sb.toString());
        System.out.println(new StringBuffer(str1).reverse());
        System.out.println(new StringBuilder(str1).reverse());
        System.out.println(sb.capacity());
        String str2 = "I am a student";
        String[] arr1 = str2.split(" ");
        StringBuilder sb1 = new StringBuilder();
        for (int i = arr1.length - 1; i >= 0; i--) {
            sb1.append(" ");
            sb1.append(arr1[i]);
        }
        System.out.println(sb1.toString().substring(1));

    }
}
