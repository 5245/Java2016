package com.sxk.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {
    private ExecutorService          pool;
    private ScheduledExecutorService scheduledPool;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ExecutorServiceTest t = new ExecutorServiceTest();
        t.t1();

        System.out.println((System.currentTimeMillis() - start) / 1000L);
    }

    public void t1() {
        int corePoolSize = 10;
        int maximumPoolSize = 100;
        int keepAliveTime = 1000;

        pool = Executors.newFixedThreadPool(corePoolSize);

        ThreadPoolExecutor pool2;
        pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(corePoolSize),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 1000000; i++) {
            Job job = new Job(i);
            pool.submit(job);
        }
        if (!pool.isShutdown()) {
            pool.shutdown();
        }
    }

    public void t2() {
        pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            try {
                Thread.sleep(index * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(index);
                }
            });
        }
        if (!pool.isShutdown()) {
            pool.shutdown();
        }
    }

    public void t3() {
        scheduledPool = Executors.newScheduledThreadPool(5);

        for (int i = 0; i < 1000; i++) {
            scheduledPool.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("delay 3 seconds");
                }
            }, 3, TimeUnit.SECONDS);
        }

        /**
         *
         * 表示延迟1秒后每3秒执行一次。
         * ScheduledExecutorService比Timer更安全，功能更强大，后面会有一篇单独进行对比。
         */

        scheduledPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("delay 1 seconds, and excute every 3 seconds");
            }
        }, 1, 3, TimeUnit.SECONDS);

    }

}

class Job implements Runnable {
    private int shopId;

    public Job(int shopId) {
        super();
        this.shopId = shopId;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100L);
            System.out.println("shopId:" + shopId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
