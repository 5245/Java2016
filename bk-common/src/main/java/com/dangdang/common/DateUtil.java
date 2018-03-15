package com.dangdang.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

/**
 * 
 * @description date工具类
 * @author sxk
 * @date 2016年5月13日
 *
 */
public class DateUtil extends DateUtils {

    public static final String DEFAULT_DATE_PATTERN         = "yyyy-MM-dd HH:mm:ss";

    /**
     *  会员卡的日期入参格式要求
     */
    public static final String VIPCARD_DATE_PATTERN         = "yyyyMMdd";

    /**
     * 折扣卡过期时间类型
     */
    public static final String VIPCARD_EXPIRCE_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * 一天的秒数
     */
    public static final int    DAY_SECOND                   = 86400;

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
        SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);
        return sdf.format(date);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式的字符串时间转换为unix时间戳
     * @param dateStr
     * @return
     *
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
        parsePattern = parsePattern == null ? DEFAULT_DATE_PATTERN : parsePattern;
        SimpleDateFormat sdf = new SimpleDateFormat(parsePattern);
        try {
            Date date = sdf.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * getCurrentTimestamp: <br/>
     * 返回当前时间(精确到秒)<br/>
     * @return
     */
    public static Integer getCurrentTimestamp() {
        return Integer.valueOf(System.currentTimeMillis() / 1000L + "");
    }

    /**
     * getBeforeDayTimestamp: <br/>
     * 返回基准时间之前多少天的时间戳(精确到秒) <br/>
     * @param refTime 时间戳(精确到秒)
     * @param days (天数)
     * @return
     */
    public static Integer getBeforeDayTimestamp(Integer refTime, Integer days) {
        return Integer.valueOf((refTime - days * DAY_SECOND) + "");
    }

    /**
     * getDatebyStr: <br/>
     * 字符转日期
     * @author liujie
     * @param dateStr
     * @param matchString
     * @return
     */
    public static Date getDatebyStr(String dateStr, String formatStr) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            //严格校验
            format.setLenient(false);
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * isValidDate: <br/>
     * 判断是否是对应的日期格式 <br/>
     * @author liujie
     * @param dateStr
     * @param formatStr
     * @return
     */
    public static boolean isValidDate(String dateStr, String formatStr) {
        boolean convertSuccess = true;
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            format.setLenient(false);
            format.parse(dateStr);
        } catch (ParseException e) {
            convertSuccess = false;
        } catch (IllegalArgumentException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }
}
