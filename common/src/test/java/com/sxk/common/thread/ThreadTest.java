package com.sxk.common.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.math.RandomUtils;

public class ThreadTest {

    private static ExecutorService pool;

    static {
        // 起1000线程容量的线程池，用以处理Redis插入操作
        pool = Executors.newFixedThreadPool(5000);
    }

    public static void main(String[] args) {
        for (int j = 0; j < 10000; j++) {
            RunWorkerTest w = new RunWorkerTest();
            //w.run();
            
            pool.submit(w);
            
            
            
//            WebLogAspect a = new WebLogAspect();
//            int i = RandomUtils.nextInt(100);
//            if (i % 2 == 0) {
//                a.doAfter();
//            } else {
//                a.doBefore();
//                a.doAfter();
//            }
//            
            
            
        }
        pool.shutdown();

    }

}
