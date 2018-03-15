package com.sxk.sort;

import java.util.List;
import java.util.StringTokenizer;

public class Test {
    public static void main(String[] args) {
        int[] array = { 1, 3, 5, 7, 9, 29, 38, 44, 66, 88, 99, 101 };
        int target = 44;
        int start = 0;
        int end = array.length - 1;
        int split;
        System.out.println("start");
        while (start <= end) {
            split = (end + start) / 2;
            if (array[split] < target) {
                start = split + 1;
            } else if (array[split] > target) {
                end = split - 1;
            } else {
                System.out.println(array[split]);
                break;
            }
        }
        System.out.println("end");
        System.out.println("==============");
        System.out.println("path:" + System.getenv("PATH"));
        System.out.println("java_home:" + System.getenv("JAVA_HOME"));

        StringTokenizer st = new StringTokenizer("Hello,World|of|Java,&s&d", ",|&");
        while (st.hasMoreElements()) {
            System.out.println(st.nextToken());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Hello");
        sb.append("World");
        sb.toString();
        StringBuffer s = new StringBuffer(); // 反转字符串
        s.append("I ");
        s.append(" am");
        s.append(" a");
        s.append(" student");
        System.out.println(s.toString());
        System.out.println(s.reverse().toString());

    }

    private static <T> int indexedBinarySearch(List<? extends Comparable<? super T>> list, T key) {
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            Comparable<? super T> midVal = list.get(mid);
            int cmp = midVal.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1); // key not found
    }

    //求子数组最大
    public void getMax() {
        //sum为子数组的和  
        int sum = 0;
        //max为子数组的最大和  
        int max = 0;
        //最大子数组的起始位置  
        int startPos = 0;
        //最大子数组的结束位置  
        int endPos = 0;
        int[] array = { -1, 2, -3, 12, -5, -1, 9, -2 };

        for (int i = 0; i < array.length; i++) {
            sum += array[i];//求和  
            if (sum < 0) {//如果当前求得总和为负数的话，就将其清零，并且开始位置从下一个位置开始  
                sum = 0;
                startPos = i + 1;
            }
            if (sum > max) {//如果求得总和大于之前的最大值的话，就将sum赋值给max，同时记录最后的位置  
                max = sum;
                endPos = i + 1;
            }
        }

        System.out.println("Max:" + max);
        System.out.println("startPos:" + startPos + ",endPos:" + (endPos - 1));
    }
}
