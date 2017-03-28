package com.sxk.thread;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * @description date工具类封装性能和安全测试
 * @author sxk
 * @email sxk5245@126.com
 * @date 2017年3月24日
 */
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

    //线程不安全，入参不一样，返回的可能一样
    public static String format5(Date date) {
        return date == null ? null : dateFormat2.format(date);
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

    /**
     * @description  测试线程安全问题，线程不安全，入参不一样，返回的确一样
     * @author sxk
     * @email sxk5245@126.com
     * @date 2017年3月28日
     */
    private static class TestClient2 extends Thread {
        private Date date;

        public TestClient2(Date date) {
            this.date = date;
        }

        public void run() {
            System.out.println(Thread.currentThread().getName() + " : " + DateTest.format5(date));
        }
    }

    /**
     * 
     * @description 线程不安全 直接报错：java.lang.NumberFormatException: multiple points
     * @author sxk
     * @email sxk5245@126.com
     * @date 2017年3月28日
     */
    private static class TestClient extends Thread {
        private String dateStr;

        public TestClient(String dateStr) {
            this.dateStr = dateStr;
        }

        public void run() {
            System.out.println(Thread.currentThread().getName() + " : " + DateTest.parseDate2(dateStr));
        }
    }

    public static void main(String[] args) {
        DateTest dt = new DateTest();
        long start1 = System.nanoTime();
        long start2 = System.currentTimeMillis();
        dt.testMulitiThread();
        //dt.testThreadLocalEfficiency();

        //dt.testDTEfficiency();
        System.out.println("nanoTime    " + (System.nanoTime() - start1));
        System.out.println("currentTime " + (System.currentTimeMillis() - start2));
    }

    /**
     * 测试多线程下 局部变量SimpleDateFormat的安全性
     *
     */
    private void testMulitiThread2() {
        DateTest dt = new DateTest();
        // ③ 3个线程共享sn，各自产生序列号  
        TestClient2 t1 = new TestClient2(DateTest.parseDate("2016-01-01 10:59:11"));
        TestClient2 t2 = new TestClient2(DateTest.parseDate("2016-01-01 10:59:22"));
        TestClient2 t3 = new TestClient2(DateTest.parseDate("2016-01-01 10:59:33"));
        t1.start();
        t2.start();
        t3.start();
    }

    private void testMulitiThread() {
        DateTest dt = new DateTest();
        // ③ 3个线程共享sn，各自产生序列号  
        TestClient t1 = new TestClient("2016-01-01 10:59:11");
        TestClient t2 = new TestClient("2016-01-01 10:59:22");
        TestClient t3 = new TestClient("2016-01-01 10:59:33");
        t1.start();
        t2.start();
        t3.start();
    }

    /**
     * 测试ThreadLocal<DateFormat>和new SimpleDateFormat()实例的性能
     * 
     */
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

    /**
     * 测试ThreadLocal<DateFormat>、DateFormatUtils和new SimpleDateFormat()实例的性能
     *
     */
    private void testDTEfficiency() {
        long start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            DateTest.format(Calendar.getInstance().getTime());
            //DateTest.format2(Calendar.getInstance().getTime());
            //DateTest.format3(Calendar.getInstance().getTime());
        }
        System.out.println("DateFormatUtils耗时：               " + (System.nanoTime() - start));
        //System.out.println("ThreadLocal<DateFormat>耗时：" + (System.nanoTime() - start));
        //System.out.println("SimpleDateFormat耗时：              " + (System.nanoTime() - start));
    }

    private void testDTEfficiency2() {
        long start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            DateTest.format2(Calendar.getInstance().getTime());
        }
        System.out.println("ThreadLocal<DateFormat>耗时：" + (System.nanoTime() - start));
    }

    private void testDTEfficiency3() {
        long start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            DateTest.format3(Calendar.getInstance().getTime());
        }
        System.out.println("SimpleDateFormat耗时：              " + (System.nanoTime() - start));
    }

    /**
     * 测试性能，在testThreadLocalEfficiency()方法中同时测试parseDate和parseDate3结果不准，
     * 可能和jvm缓存有关系，应该先跑parseDate，在跑parseDate3
     * 
     * 经过测试性能从高到底：ThreadLocal<DateFormat>、DateFormatUtils、new SimpleDateFormat()
     * 
     * 
     * 
     */
}
