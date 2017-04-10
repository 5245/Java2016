package com.sxk.common.thread;

public class ThreadLocalTest2 {

    private static final ThreadLocal<String> logLocal = new ThreadLocal<String>();

    public static void main(String[] args) {
        ThreadLocalTest2 t = new ThreadLocalTest2();
        logLocal.set("aa");
        String log = logLocal.get();
        
        System.out.println(log);
    }

}
