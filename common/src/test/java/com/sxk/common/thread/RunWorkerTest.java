package com.sxk.common.thread;

import org.apache.commons.lang.math.RandomUtils;

public class RunWorkerTest implements Runnable {

    @Override
    public void run() {
        WebLogAspect a = new WebLogAspect();
        int i = RandomUtils.nextInt(100);
        if (i % 2 == 0) {
            a.doBefore();
            a.doAfter();
        } else {
          //  a.doBefore();
            a.doAfter();
        }

    }

}
