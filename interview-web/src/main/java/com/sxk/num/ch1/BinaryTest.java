package com.sxk.num.ch1;

public class BinaryTest {
    public static void main(String[] args) {
        int number = 10;
        //原始数二进制
        printInfo(number);
        number = number << 2;
        //左移一位
        printInfo(number);
        number = number >> 1;
        //右移一位
        printInfo(number);
    }

    /**
     * 输出一个int的二进制数
     * @param num
     */
    private static void printInfo(int num) {
        System.out.println(num + ":" + Integer.toBinaryString(num));
    }
}
