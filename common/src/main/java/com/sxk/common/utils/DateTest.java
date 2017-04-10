package com.sxk.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DateTest extends DateUtils {

    private static final String                  DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final SimpleDateFormat        dateFormat2          = new SimpleDateFormat(DEFAULT_DATE_PATTERN);

    private static final ThreadLocal<DateFormat> dateFormat           = new ThreadLocal<DateFormat>() {
                                                                          @Override
                                                                          protected DateFormat initialValue() {
                                                                              return new SimpleDateFormat(DEFAULT_DATE_PATTERN);
                                                                          }
                                                                      };

    public static String format(Date date) {
        return date == null ? null : DateFormatUtils.format(date, DEFAULT_DATE_PATTERN);
    }

    public static String format2(Date date) {
        return date == null ? null : dateFormat.get().format(date);
    }

    public static String format3(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        return date == null ? null : sdf.format(date);
    }

    public static String format(Date date, String pattern) {
        return date == null ? null : DateFormatUtils.format(date, pattern);
    }

    /**
     * 用threadLocal来保证局部变量的线程安全
     * @param str
     * @return
     *
     */
    public static Date parseDate(String str) {
        DateFormat sdf = dateFormat.get();
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 存在线程安全
     * @param str
     * @return
     *
     */
    public static Date parseDate2(String str) {
        try {
            return dateFormat2.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 每次直接new实例保证线程安全
     * @param str
     * @return
     *
     */
    public static Date parseDate3(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class TestClient extends Thread {
        private DateTest dt;
        private String   dateStr;

        public TestClient(DateTest dt, String dateStr) {
            this.dt = dt;
            this.dateStr = dateStr;
        }

        public void run() {
            System.out.println(Thread.currentThread().getName() + " : " + dt.parseDate2(dateStr));
        }
    }

    public static void main(String[] args) {
        DateTest dt = new DateTest();

        //dt.testMulitiThread();
        long start1 = System.nanoTime();
        long start2 = System.currentTimeMillis();
        dt.testThreadLocalEfficiency();

        //dt.testDTEfficiency();
        System.out.println("nanoTime    " + (System.nanoTime() - start1));
        System.out.println("currentTime " + (System.currentTimeMillis() - start2));
    }

    private void testMulitiThread() {
        DateTest dt = new DateTest();
        // ③ 3个线程共享sn，各自产生序列号  
        TestClient t1 = new TestClient(dt, "2016-01-01 10:59:11");
        TestClient t2 = new TestClient(dt, "2016-01-01 10:59:22");
        TestClient t3 = new TestClient(dt, "2016-01-01 10:59:33");
        t1.start();
        t2.start();
        t3.start();
    }

    private void testThreadLocalEfficiency() {
        String date = "2016-01-01 10:59:1";
        long start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            DateTest.parseDate(date + RandomUtils.nextInt(0, 10));
            //DateTest.parseDate3(date + RandomUtils.nextInt(0, 10));
        }
        System.out.println("parse threadLocal 耗时：" + (System.nanoTime() - start));
        long start1 = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            DateTest.parseDate3(date + RandomUtils.nextInt(0, 10));
        }
        System.out.println("parse simpleDatef 耗时：" + (System.nanoTime() - start1));
    }

    private void testDTEfficiency() {
        long start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            DateTest.format(Calendar.getInstance().getTime());
        }
        System.out.println("format-0耗时：" + (System.nanoTime() - start));

        long start1 = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            DateTest.format2(Calendar.getInstance().getTime());
        }
        System.out.println("format-2耗时：" + (System.nanoTime() - start1));

        long start2 = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            DateTest.format3(Calendar.getInstance().getTime());
        }
        System.out.println("format-3耗时：" + (System.nanoTime() - start2));
    }
}
