package com.sxk.func;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author: sxk
 * Date: 2018/6/27
 * Description:
 */
public class FirstFunction {

    public static void main(String[] args) {
        donation(5000, money -> System.out.println("好心的麦乐迪为Blade捐赠了" + money + "元"));

        List<Integer> list = supply(10, () -> (int) (Math.random() * 100));
        list.forEach(System.out::println);

        Integer value = convert("28", x -> Integer.parseInt(x));

        List<String> fruit = Arrays.asList("香蕉", "哈密瓜", "榴莲", "火龙果", "水蜜桃");
        List<String> newFruit = filter(fruit, str -> str.length() == 2);
        System.out.println(newFruit);
    }

    public static void donation(Integer money, Consumer<Integer> consumer) {
        consumer.accept(money);
    }

    public static List<Integer> supply(Integer num, Supplier<Integer> supplier) {
        List<Integer> resultList = new ArrayList<Integer>();
        for (int x = 0; x < num; x++) {
            resultList.add(supplier.get());
        }
        return resultList;
    }

    public static Integer convert(String str, Function<String, Integer> function) {
        return function.apply(str);
    }

    public static List<String> filter(List<String> fruit, Predicate<String> predicate) {
        List<String> f = new ArrayList<>();
        for (String s : fruit) {
            if (predicate.test(s)) {
                f.add(s);
            }
        }
        return f;
    }


}
