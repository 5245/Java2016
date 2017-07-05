package com.sxk.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceTest {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        int corePoolSize = 10;
        int maximumPoolSize = 100;
        int keepAliveTime = 1000;

        ExecutorService pool = Executors.newFixedThreadPool(100);

        //        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
        //                new ArrayBlockingQueue<Runnable>(corePoolSize), new ThreadPoolExecutor.CallerRunsPolicy());

        int splitCount = 1024;
        int tableCount = 128;
        int hash = 0;
        int dbIndex = 0;
        int tblIndex = 0;

        for (int i = 0; i < 1000000; i++) {
            Job job = new Job(i);
            pool.submit(job);
        }
        pool.shutdown();
        System.out.println((System.currentTimeMillis() - start) / 1000L);

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
            //Thread.sleep(100L);
            System.out.println("shopId:" + shopId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
