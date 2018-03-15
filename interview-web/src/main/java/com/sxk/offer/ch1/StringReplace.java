package com.sxk.offer.ch1;

/**
 * 
 * @description 替换空格 
 * @author sxk
 * @email sxk5245@126.com
 * @date 2017年9月13日
 */
public class StringReplace {

    public static void main(String args[]) {
        String s = "We are happy.";
        char c_old[] = s.toCharArray();
        // 为简单起见，我们假设给它一个新的空间，空间的大小组以存下替换后的字符  
//        char c_new[] = new char[100];
//        for (int i = 0; i < c_old.length; i++) {
//            c_new[i] = c_old[i];
//        }

        // 输出新的数组  
        System.out.println(replaceBlank(c_old, c_old.length));
    }

    /** 
     * 计算新的字符串 
     * @param c带空格的字符数组 
     * @param length是指第一个字符到最后一个字符的长度，不是字符数组的长度 
     * @return 
     */
    public static String replaceBlank(char c[], int length) {
        // 查找空格的数目  
        int blank = 0;
        for (int i = 0; i < length; i++) {
            if (c[i] == ' ') {
                blank++;
            }
        }

        // 重新计算新的数组的大小  
        int length_new = length + blank * 2;

        // 从尾到头查找  
        int j = length - 1;
        int k = length_new - 1;
        while (j >= 0 && k >= 0) {
            if (c[j] != ' ') {
                c[k--] = c[j];
            } else {
                c[k--] = '0';
                c[k--] = '2';
                c[k--] = '%';
            }
            j--;
        }
        return new String(c);
    }

}
