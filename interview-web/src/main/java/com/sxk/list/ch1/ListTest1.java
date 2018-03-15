package com.sxk.list.ch1;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.Builder;
import lombok.Data;

public class ListTest1 {
    @Data
    @Builder
    static class Stu {
        private Integer age;
        private String  name;
    }

    public static void main(String[] args) {
        ArrayList<Stu> list = new ArrayList<>();
        Stu stu1 = Stu.builder().age(1).name("haha1").build();
        Stu stu2 = Stu.builder().age(2).name("haha2").build();
        Stu stu3 = Stu.builder().age(3).name("haha3").build();
        list.add(stu3);
        list.add(stu1);
        list.add(stu2);

        printCapacity(list);
        System.out.println(list.toString() + " :" + list.size());
        //trimSize
        list.trimToSize();
        printCapacity(list);
        System.out.println(list.toString() + " :" + list.size());
        list.sort(new Comparator<Stu>() {
            @Override
            public int compare(Stu o1, Stu o2) {
                return o1.age.compareTo(o2.age);
            }
        });
        System.out.println(list.toString());

        Integer[] arr1 = new Integer[] { 1, 8, 20, 3, 4, 5 };
        Integer[] arr2 = Arrays.copyOf(arr1, 4);
        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr2));
        Integer[] arr3 = new Integer[3];
        System.arraycopy(arr1, 0, arr3, 0, Math.min(arr1.length, 3));
        System.out.println(Arrays.toString(arr3));

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
