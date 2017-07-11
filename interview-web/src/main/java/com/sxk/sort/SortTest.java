package com.sxk.sort;

public class SortTest {
    /**
     * 冒泡排序
     * @param a
     */
    public static void bubbleSort(Comparable[] a) {
        int j, flag;
        Comparable temp;
        for (int i = 0; i < a.length; i++) {
            flag = 0;
            for (j = 1; j < a.length - i; j++) {
                if (a[j].compareTo(a[j - 1]) < 0) {
                    temp = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = temp;
                    flag = 1;
                }
            }
            // 如果没有交换，代表已经排序完毕，直接返回
            if (flag == 0) {
                return;
            }
        }
    }

    /********************************************************/

    /**
     * 插入排序
     * @param a
     *
     */
    public static void insertionSort(Comparable[] a) {
        int length = a.length;
        Comparable temp;
        for (int i = 1; i < length; i++) {
            for (int j = i; j > 0 && a[j].compareTo(a[j - 1]) < 0; j--) {
                temp = a[j];
                a[j] = a[j - 1];
                a[j - 1] = temp;
            }
        }
    }

    // 对实现Comparable的类型进行排序，先将大的元素都向右移动，减少一半交换次数 
    public static void insertionSort2(Comparable[] a) {
        int length = a.length;
        Comparable temp;
        int j;
        for (int i = 1; i < length; i++) {
            temp = a[i];
            for (j = i; j > 0 && temp.compareTo(a[j - 1]) < 0; j--) {
                a[j] = a[j - 1];
            }
            a[j] = temp;
        }
    }

    /********************************************************/

    /**
     * 选择排序
     * @param a
     */
    public static void selectionSort1(Comparable[] a) {
        int length = a.length;
        int min;
        Comparable temp;
        for (int i = 0; i < length; i++) {
            min = i;
            for (int j = i + 1; j < length; j++) {
                if (a[j].compareTo(a[min]) < 0) {
                    min = j;
                }
            }
            temp = a[min];
            a[min] = a[i];
            a[i] = temp;
        }
    }

    /********************************************************/

    /**
     * 希尔排序
     * @param a
     *
     */
    public static void shellSort(Comparable[] a) {
        int length = a.length;
        int h = 1;
        Comparable temp;
        while (h < length / 3) {
            h = 3 * h + 1;
        }
        while (h >= 1) {
            for (int i = h; i < length; i++) {
                for (int j = i; j >= h && a[j].compareTo(a[j - h]) < 0; j -= h) {
                    temp = a[j];
                    a[j] = a[j - h];
                    a[j - h] = temp;
                }
            }
            h /= 3;
        }
    }

    /********************************************************/
    /**
     * 堆排序
     * @param a
     *
     */
    public static void heapSort(Comparable[] a) {
        int length = a.length;
        Comparable temp;
        for (int k = length / 2; k >= 1; k--) {
            sink(a, k, length);
        }
        while (length > 0) {
            temp = a[0];
            a[0] = a[length - 1];
            a[length - 1] = temp;
            length--;
            sink(a, 1, length);
        }
    }

    private static void sink(Comparable[] a, int k, int n) {
        Comparable temp;
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && a[j - 1].compareTo(a[j]) < 0) {
                j++;
            }
            if (a[k - 1].compareTo(a[j - 1]) >= 0) {
                break;
            }
            temp = a[k - 1];
            a[k - 1] = a[j - 1];
            a[j - 1] = temp;
            k = j;
        }
    }

    /********************************************************/

    //自顶向下的归并排序
    private static Comparable[] aux; // 自顶向下 

    public static void mergeSort(Comparable[] a) {
        aux = new Comparable[a.length];
        mergeSort(a, 0, a.length - 1);
    }

    public static void mergeSort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int mid = (lo + hi) >>> 1;
        mergeSort(a, lo, mid);
        mergeSort(a, mid + 1, hi);
        merge(a, lo, mid, hi);
    }

    public static void merge(Comparable[] a, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (aux[j].compareTo(aux[i]) < 0) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    //自底向上的归并排序
    private static Comparable[] aux2; // 自底向上

    public static void mergeSort2(Comparable[] a) {
        int length = a.length;
        aux2 = new Comparable[length];
        for (int sz = 1; sz < length; sz = sz + sz) {
            for (int lo = 0; lo < length - sz; lo += sz + sz) {
                merge2(a, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, length - 1));
            }
        }
    }

    public static void merge2(Comparable[] a, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            aux2[k] = a[k];
        }
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux2[j++];
            } else if (j > hi) {
                a[k] = aux2[i++];
            } else if (aux2[j].compareTo(aux2[i]) < 0) {
                a[k] = aux2[j++];
            } else {
                a[k] = aux2[i++];
            }
        }
    }

    /********************************************************/

    /**
     * 快速排序
     * @param a
     *
     */
    public static void quickSort(Comparable[] a) {
        quickSort(a, 0, a.length - 1);
    }

    public static void quickSort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int j = partition(a, lo, hi);
        quickSort(a, lo, j - 1);
        quickSort(a, j + 1, hi);
    }

    public static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable temp;
        Comparable v = a[lo];
        while (true) {
            while (a[++i].compareTo(v) < 0) {
                if (i == hi) {
                    break;
                }
            }
            while (v.compareTo(a[--j]) < 0) {
                if (j == lo) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        temp = a[lo];
        a[lo] = a[j];
        a[j] = temp;
        return j;
    }

}
