package com.sxk.single;

public class PropUtils {

    private static class LazyHolder {
        private static final PropUtils INSTANCE = new PropUtils();
    }

    private PropUtils() {
    }

    public static final PropUtils getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static void main(String[] args) {
        PropUtils singleton1 = PropUtils.getInstance();
        PropUtils singleton2 = PropUtils.getInstance();
        if (singleton1 == singleton2)
            System.out.println("same");
    }
}

