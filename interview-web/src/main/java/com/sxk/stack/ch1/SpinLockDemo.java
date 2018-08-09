package com.sxk.stack.ch1;

public class SpinLockDemo {
    int count = 0;

    public void incr() {
        count++;
        System.out.println("执行了:" + count + "次");

    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        while (spinLockDemo.count != 1000000) {//这段代码其实就是一个自旋锁
            spinLockDemo.incr();
        }
        System.out.println("执行其他代码...");
    }
}
