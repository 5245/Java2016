package com.sxk.collection;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author: sxk
 * Date: 2018/6/27
 * Description:
 */
public class ListTest {
    public static void main(String[] args) {
        List<String> languages = Arrays.asList("java", "scala", "python");
        languages.forEach(x -> System.out.println(x));
        languages.forEach(System.out::println);
        filterTest(languages, x -> x.startsWith("j"));
        filterTest(languages, x -> x.endsWith("a"));
        /**
         * map的作用是将一个对象变换为另外一个
         */
        List<Double> cost = Arrays.asList(10.0, 20.0, 30.0, 40.0);
        cost.stream().map(x -> x + x * 0.05).forEach(y -> System.out.println(y));
        /**
         * 而reduce实现的则是将所有值合并为一个
         */
        double allCost = cost.stream().map(x -> x + x * 0.05).reduce((x, y) -> x + y).get();
        System.out.println("reduce total:" + allCost);

        List<Double> filteredCost = cost.stream().filter(x -> x > 25.0).collect(Collectors.toList());
        filteredCost.forEach(x -> System.out.println(x));
    }

    public static void filterTest(List<String> languages, Predicate<String> condition) {
        languages.stream().filter(x -> condition.test(x)).forEach(x -> System.out.println(x + " "));
    }

}
