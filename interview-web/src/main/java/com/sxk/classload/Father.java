package com.sxk.classload;

public class Father {
    static {
        System.out.println("*******father init");
    }
    public static int a = 1;
}
