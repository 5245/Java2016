package com.sxk.dao;

public class Test {
    public static void main(String[] args) {
        Test t = new Test();
        Long start = System.currentTimeMillis();
        int total = t.fun(9);
        System.out.println(total);
        System.out.println(System.currentTimeMillis() - start);
    }

    public static int fun(int n) {
        if (n == 0 || n == 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fun(n - 1) + fun(n - 2);
    }
}
