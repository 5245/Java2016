package com.sxk.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil extends DateUtils {

    private static final String                  DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final ThreadLocal<DateFormat> dateFormat           = new ThreadLocal<DateFormat>() {
                                                                          /**
                                                                           * 每个线程都会调用初始化
                                                                           */
                                                                          @Override
                                                                          protected DateFormat initialValue() {
                                                                              return new SimpleDateFormat(DEFAULT_DATE_PATTERN);
                                                                          }
                                                                      };

    public static Date parseDate(String str) {
        DateFormat format = dateFormat.get();
        try {
            return format.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.parseDate("2015-01-02 10:59:59").getTime());
    }

}
