package com.dangdang.model;

public class Square {
    long width;

    public Square(long l) {
        width = l;
    }

    public static void main(String arg[]) {
        Integer i01 = 59;
        int i02 = 59;
        Integer i03 = Integer.valueOf(59);
        Integer i04 = new Integer(59);
        System.out.println(i04 == i03);
    }
}
