package com.sxk.list.ch1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LinkList {
    static final int N    = 50000;
    static byte[]    lock = new byte[1];

    static long timeList(List list) {
        long start = System.currentTimeMillis();
        Object o = new Object();
        for (int i = 0; i < N; i++) {
            list.add(0, o);
        }
        System.out.println(list.size());
        return System.currentTimeMillis() - start;
    }

    public static void main(String[] args) {
        System.out.println("ArrayList耗时：" + timeList(new ArrayList()));
        System.out.println("LinkedList耗时：" + timeList(new LinkedList()));
    }
}
