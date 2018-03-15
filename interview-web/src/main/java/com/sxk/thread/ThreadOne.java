package com.sxk.thread;

public class ThreadOne {
    static Object object = new Object();

    public static class ObjThread1 extends Thread {
        public ObjThread1(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (object) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static class ObjThread2 implements Runnable {
        public ObjThread2(String name) {
            Thread.currentThread().setName(name);
        }

        @Override
        public void run() {
            synchronized (object) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        ObjThread1 t1 = new ObjThread1("t1");
        ObjThread2 t2 = new ObjThread2("t2");
        t1.start();
        t2.run();

    }
}
