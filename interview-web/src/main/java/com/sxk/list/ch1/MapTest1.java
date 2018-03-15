package com.sxk.list.ch1;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import lombok.AllArgsConstructor;
import lombok.Data;

public class MapTest1 {
    public static void main(String[] args) {
        String str = "eertyuioperfgbnmqwertyuiopasdfghjkzxcvbnmwertyuiopsdfghjkzxcvbnm";
        char[] arr = str.toCharArray();
        Map<String, Integer> map = new HashMap<>();
        for (char c : arr) {
            if (map.containsKey(c + "")) {
                map.put(c + "", map.get(c + "") + 1);
            } else {
                map.put(c + "", 1);
            }
        }
        System.out.println(map);
        TreeSet<StrCount> ts = new TreeSet<>();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            ts.add(new StrCount(entry.getKey(), entry.getValue()));
        }
        System.out.println(ts);
    }
}

@Data
@AllArgsConstructor
class StrCount implements Comparable<StrCount> {
    private String str;
    private int    count;

    @Override
    public int compareTo(StrCount o) {
        if (this.count != o.count) {
            return this.count - o.count;
        }
        return this.str.compareTo(o.str);
    }

}
