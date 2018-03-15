package com.sxk.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SortTest3 {
    public static void main(String[] args) {
        List<StuTest> list = new ArrayList<>();
        StuTest s1 = new StuTest(1, "aa");
        StuTest s2 = new StuTest(2, "bb");
        StuTest s3 = new StuTest(3, "cc");
        StuTest s4 = new StuTest(4, "cc");
        StuTest s5 = new StuTest(1, "ee");
        list.add(s3);
        list.add(s5);
        list.add(s2);
        list.add(s1);
        list.add(s4);
        Collections.sort(list, new Comparator<StuTest>() {
            @Override
            public int compare(StuTest o1, StuTest o2) {
                //return o1.getId() > o2.getId() ? 1 : -1;
                return o1.getId()- o2.getId();
            }
        });
        for (StuTest s : list) {
            System.out.println(s.toString());
        }
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("aa", "aa");
        map.put("bb", "bb");
        //map.put("bb", null);

        map.forEach((K, V) -> System.out.println(K + ":" + V));
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            //System.out.println(r.nextInt(10));
            //System.out.println(Math.random());
        }

    }
}

class StuTest {
    private int    id;
    private String name;

    public StuTest(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
