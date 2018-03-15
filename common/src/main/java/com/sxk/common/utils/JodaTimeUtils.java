package com.sxk.common.utils;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;

/**
 * @description date工具类
 * @author sxk
 * @date 2016年5月13日
 */
public class JodaTimeUtils {

    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将Date格式为yyyy-MM-dd HH:mm:ss格式的字符串
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, DEFAULT_DATE_PATTERN);
    }

    /**
     * Date格式为字符串
     * @param date
     * @param formatPattern
     * @return
     *
     */
    public static String format(Date date, String formatPattern) {
        formatPattern = formatPattern == null ? DEFAULT_DATE_PATTERN : formatPattern;
        return new DateTime(date).toString(formatPattern);

    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式的字符串时间转换为unix时间戳
     * @param dateStr
     * @return
     */
    public static long parseUnixTime(String dateStr) {
        return parseUnixTime(dateStr, DEFAULT_DATE_PATTERN);
    }

    /**
     * 将字符串时间转换为unix时间戳
     * @param dateStr
     * @param parsePattern
     * @return
     */
    public static long parseUnixTime(String dateStr, String parsePattern) {
        Date date = parseDate(dateStr, parsePattern);
        return date == null ? 0 : date.getTime();
    }

    /**
     * @param dateStr
     * @param parsePattern
     * @return
     */
    public static Date parseDate(String dateStr, String parsePattern) {
        parsePattern = parsePattern == null ? DEFAULT_DATE_PATTERN : parsePattern;
        try {
            return DateTime.parse(dateStr, DateTimeFormat.forPattern(parsePattern)).toDate();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 相差几天
     * @param d1
     * @param d2
     * @return
     */
    public static int getBetweenDay(Date d1, Date d2) {
        DateTime dt1 = new DateTime(d1);
        DateTime dt2 = new DateTime(d2);
        return Days.daysBetween(dt1, dt2).getDays();
    }

    /**
     * 得到当天时间的结束时间，23:59:59
     * @param date
     * @return
     */
    public static Date getDateEnd(Date date) {
        DateTime dt1 = new DateTime(date);
        return dt1.millisOfDay().withMaximumValue().toDate();
    }

    public static boolean isSameDay(Date d1, Date d2) {
        DateTime dt1 = new DateTime(2016, 10, 14, 10, 55, 55, 0);
        DateTime dt2 = new DateTime(2016, 10, 14, 12, 0, 0, 0);
        System.out.println(dt1.isAfterNow());
        return false;

    }
}
