package com.sxk.stack.ch1;


/**
 * 你在mian方法中实例化StackOverErrorTest时，程序会去实际化成员变量，
 * 这里就是指instance，实际化instance的时候又会去实际化StackOverErrorTest，
 * 然后又去实际化instance，如此循环，就会无限递归
 */

public class StackOverErrorTest {


    private int count = 0;
    private StackOverErrorTest instance = new StackOverErrorTest();

    public void StackOverErrorTest() {
        System.out.println("count:" + count);
    }

    public static void main(String[] args) {
        try {
            StackOverErrorTest t = new StackOverErrorTest();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
