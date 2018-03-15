package com.sxk.list.ch1;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ArrList {
    public static final int N = 50000;
    public static List      values;
    static {
        Integer vals[] = new Integer[N];
        Random r = new Random();
        for (int i = 0, currval = 0; i < N; i++) {
            vals[i] = new Integer(currval);
            currval += r.nextInt(100) + 1;
        }
        values = Arrays.asList(vals);
    }

    static long timeList(List lst) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            int index = Collections.binarySearch(lst, values.get(i));
            if (index != i) {
                System.out.println("***错误***");
            }
        }
        return System.currentTimeMillis() - start;
    }

    public static void main(String args[]) {
        ArrayList aList = new ArrayList(values);
        LinkedList lList = new LinkedList(values);
        System.out.println("ArrayList消耗时间：" + timeList(aList));
        //System.out.println("LinkedList消耗时间：" + timeList(lList));
        printCapacity(aList);
    }

    public static void printCapacity(List list) {
        Class c = list.getClass();
        Field f;
        try {
            f = c.getDeclaredField("elementData");
            f.setAccessible(true);
            Object[] o = (Object[]) f.get(list);
            System.out.println("Capacity" + ":" + o.length);
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
