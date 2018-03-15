package com.sxk.test;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JodaTimeUtilsTest {

    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static void main(String[] args) {

        DateTime dateTime = DateTime.now();
        System.out.println(dateTime.toString(PATTERN));

        DateTimeFormatter formatter = DateTimeFormat.forPattern(PATTERN);
        DateTime d = DateTime.parse("2016-10-12 12:20:30", formatter);

        DateTime d2 = d.minusDays(2).minusMonths(1);
        System.out.println(d.toString(PATTERN));
        System.out.println(d2.toString(PATTERN));
        System.out.println(d.compareTo(d));
        System.out.println(d.compareTo(d2));

        LocalDate lc = new LocalDate();
        System.out.println(lc.toString());
        LocalTime lt = new LocalTime();
        System.out.println(lt.toString());

        DateTime dt1 = new DateTime(2016, 10, 14, 10, 55, 55, 12);
        DateTime dt2 = new DateTime(2016, 10, 14, 12, 59, 59, 0);
        System.out.println(dt1.toString(PATTERN) + ":" + dt1.isBeforeNow());
        System.out.println(dt2.toString(PATTERN) + ":" + dt2.isAfterNow());
        DateTime dend = dt1.millisOfDay().withMaximumValue();
        System.out.println(dend.toString(PATTERN));
        System.out.println(dt1.getSecondOfMinute());
        System.out.println(dt1.getMillisOfSecond());

    }
}
