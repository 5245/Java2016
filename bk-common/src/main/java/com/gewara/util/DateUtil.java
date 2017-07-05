package com.gewara.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

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

    public static String format(Date date) {
        return format(date, DEFAULT_DATE_PATTERN);
    }

    public static String formatDate(Date date) {
        return format(date);
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
     * 获取数据库timestamp类型的当前时间
     * @return
     *
     */
    public static Timestamp getCurFullTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp getMillTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

}
