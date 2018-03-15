package com.sxk.test;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("start");
        String str = "start";
        for (int i = 0; i < 100; i++) {
            str = str + "hello";
            sb.append("hello");
        }
        System.out.println(str);
        System.out.println(sb.toString());
        System.out.println("success");

        Map<String, String> map = new HashMap<>();
        map.put("dd", "44");
        map.put("bb", "22");
        map.put("aa", "11");
        map.put("cc", "33");

        map.forEach((k, v) -> System.out.println(k + ":" + v));
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

    }
}
