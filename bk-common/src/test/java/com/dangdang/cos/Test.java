package com.dangdang.cos;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dangdang.common.DateUtil;

public class Test {
    private static final String DATE2PATH_PATTERN  = "/yyyy/MM/dd/HH/";
    private static final String DATE2PATH_PATTERN2 = "yyyyMMddHH";

    public static void main(String[] args) {
        System.out.println(DateUtil.getDatebyStr("2013-04-26 15:14", "yyyy-MM-dd HH:mm"));
        String startTime = "2015-03-16 ";
        Map<Integer, Date> dateMap = new HashMap<>();
        String value = null;
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                value = startTime + "0" + i + ":00:00";
            } else {
                value = startTime + i + ":00:00";
            }
            Date d = DateUtil.getDatebyStr(value, DateUtil.DEFAULT_DATE_PATTERN);
            dateMap.put(i, d);
        }
        for (int i = 0; i < 24; i++) {
            Date d = dateMap.get(i);
//            System.out.println(DateUtil.format(d, DATE2PATH_PATTERN));
//            System.out.println(DateUtil.format(d, DATE2PATH_PATTERN2));
        }
        /*for (Entry<Integer, Date> entry : dateMap.entrySet()) {
            Date d = DateUtil.getDatebyStr(entry.getValue(), DateUtil.DEFAULT_DATE_PATTERN);
            System.out.println(DateUtil.format(d, DATE2PATH_PATTERN));
            System.out.println(DateUtil.format(d, DATE2PATH_PATTERN2));
            //System.out.println(entry.getKey());

        }*/
        for (int i = 1; i < 25; i++) {
            System.out.println(i % 24);
        }
    }
}
