package com.sxk.common.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebLogAspect {

    private static final Logger                 logger   = LoggerFactory.getLogger(WebLogAspect.class);

    //    private int                 randomNum;
    //
    //    public WebLogAspect() {
    //    }
    //
    //    public WebLogAspect(int randomNum) {
    //        this.randomNum = randomNum;
    //    }

    //线程同步
    private static final ThreadLocal<LogEntity> logLocal = new ThreadLocal<>();

    // private static final ThreadLocal<Long> logLocal = new ThreadLocal<>();

    public void doBefore() {
        LogEntity logMsg = new LogEntity(System.currentTimeMillis(), "a");
        logLocal.set(logMsg);
        // logLocal.set(System.currentTimeMillis());
    }

    public void doAfter() {
        LogEntity logMsg = logLocal.get();
        if (null != logMsg) {
            Long run = System.currentTimeMillis() - logMsg.getStarttime();
            if (run > 5) {
                logMsg.setStarttime(run);
                System.out.println(logMsg.toString());
            }
        }

        //        Long logMsg = logLocal.get();
        //        if (null != logMsg) {
        //            Long run = System.currentTimeMillis() - logMsg;
        //            if (run > 5) {
        //                System.out.println(run);
        //            }
        //        }

    }

}
