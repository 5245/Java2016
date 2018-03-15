package com.sxk.thread.ch1;

public class ThreadTest {

    public static class Thread1 extends Thread {

        public Thread1(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("interrupt");
                    break;
                }
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    System.out.println("interrupt when sleep");
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                System.out.println("run");
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread1 t1 = new Thread1("t1");
        t1.start();
        t1.sleep(1000L);
        t1.interrupt();
    }
}
