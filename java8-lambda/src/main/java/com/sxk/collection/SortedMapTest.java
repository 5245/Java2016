package com.sxk.collection;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SortedMapTest {
    public static void main(String[] args) {
        TreeMap<String, Integer> analyticsMap = new TreeMap<>();
        analyticsMap.put("1", 1);
        analyticsMap.put("2", 2);
        analyticsMap.put("3", 3);
        analyticsMap.put("4", 4);
        analyticsMap.put("5", 5);
        System.out.println(analyticsMap);
        Optional<Map.Entry<String, Integer>> lastDateAnalytics = analyticsMap.entrySet().stream()
                .filter(x -> x.getValue() > 4).findFirst();
        System.out.println(lastDateAnalytics.get());
        mapSorted();
    }

    private static void mapSorted() {

        Map<String, Crowd> crowdMap = Stream.of(Crowd.builder().date("20180101").crowdScale(1).build(),
                Crowd.builder().date("20180201").crowdScale(1).build(),
                Crowd.builder().date("20181001").crowdScale(1).build()).collect(Collectors.toMap(Crowd::getDate, Function.identity()));
        System.out.println(crowdMap);
    }
}

@Builder
@Data
class Crowd {
    private String date;
    private Integer crowdScale;
}