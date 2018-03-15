package com.sxk.sort;

import java.util.Arrays;

/**
 * @description 数组
 * @author sunxiongkun
 * @email xiongkun.sun@tendcloud.com
 */
public class ArrayUtil {

    /**
     *（1）使用你熟悉的语言，实现插入排序算法
            （2）使用你熟悉的语言，实现二分查找，并证明：你的二分查找为什么是正确的？
            （3）编写程序实现：求数组中出现次数超过一半的元素。（注意：使用你熟悉的语言，必须使用分治的思想）
            （4）使用你熟悉的语言，求：一个实数数组中和最大的子段
     */

    /**
     * 插入排序
     * @param array
     *
     */
    public static void insertSort(int[] array) {
        int in, out;
        for (out = 0; out < array.length; out++) {
            int current = array[out];
            in = out;
            while (in > 0 && array[in - 1] >= current) {
                array[in] = array[in - 1];
                in--;
            }
            array[in] = current;
        }
    }

    /**
     * 二分查找
     * @param array
     * @param target
     * @return
     */
    public static int binarySearch(int[] array, int target) {
        int start = 0;
        int end = array.length - 1;
        int postion = -1;
        while (start <= end) {
            postion = (start + end) / 2;
            if (array[postion] < target) {
                start = postion + 1;
            } else if (array[postion] > target) {
                end = postion - 1;
            } else {
                return postion;
            }
        }
        return postion;
    }

    /**
     * 求数组中出现次数超过一半的元素
     * @param array
     * @return
     */
    public static int moreHalf(int[] array) {
        int result = 0;
        int count = 1;
        if (array.length == 0)
            return -1;
        result = array[0];
        for (int i = 1; i < array.length; i++) {
            if (count == 0) {
                result = array[i];
                count = 1;
                continue;
            }
            if (result == array[i]) {
                count++;
            } else {
                count--;
            }
        }
        return result;
    }

    /**
     * 求最大子数组
     * @param array
     * @return
     */
    public static int[] maxSubArray(int[] array) {
        if (array.length == 0) {
            return null;
        }
        int currentSum = 0;
        int max = 0;
        int beginIndex = 0;
        int tmpBeginIndex = 0;
        int endIndex = array.length;
        for (int i = 0; i < array.length; i++) {
            if (currentSum <= 0) {
                currentSum = array[i];
                tmpBeginIndex = i;
            } else {
                currentSum += array[i];
            }
            if (currentSum > max) {
                beginIndex = tmpBeginIndex;
                endIndex = i;
                max = currentSum;
            }
        }
        int[] result = new int[endIndex - beginIndex + 1];
        System.arraycopy(array, beginIndex, result, 0, result.length);
        return result;
    }

    public static String arrayToString(int[] array) {
        return Arrays.toString(array);
    }

    public static void main(String[] args) {
        int[] array = new int[] { 22, 33, 44, -6, 55, 99, 24, -2, 25, 77 };
        System.out.println(String.format("originalArray:\n%s", ArrayUtil.arrayToString(array)));
        ArrayUtil.insertSort(array);
        System.out.println(String.format("insertSort:\n%s", ArrayUtil.arrayToString(array)));
        System.out.println(String.format("binarySearch:%s", ArrayUtil.binarySearch(array, 30)));

        int[] array4Half = new int[] { -1, 2, -1, 6, -3, -2, 7, -15, 1, 2, 2 };
        System.out.println(String.format("moreHalf:\n%s", ArrayUtil.moreHalf(array4Half)));

        int[] array4Sub = new int[] { -2, 3, -4, 6, -3, -2, 7, -15, 2, 3, 2 };
        int[] subArray = ArrayUtil.maxSubArray(array4Sub);
        System.out.println(String.format("maxSubArray:\n%s", ArrayUtil.arrayToString(subArray)));
    }
}
