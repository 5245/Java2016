package com.sxk.classload;

import java.util.Random;

public class Son extends Father {
    static {
        System.out.println("*******son init");
    }
    public static int       b = 2;
    public static final int c = 2;
    //public static final int c = new Random().nextInt(3);
}
