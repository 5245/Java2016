package com.sxk.list.ch1;

public class MyArrayList<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private int              theSize;
    private E[]              theItems;

    /**
     * 返回ArrayList的长度
     * @return
     */
    public int size() {
        return theSize;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void ensureCapacity(int newCapacity) {
        if (newCapacity < theSize) {
            return;
        }
        E[] old = theItems;
        theItems = (E[]) new Object[newCapacity];
        for (int i = 0; i < size(); i++) {
            theItems[i] = old[i];
        }
    }

    public MyArrayList() {
        clear();
    }

    public void clear() {
        theSize = 0;
        ensureCapacity(DEFAULT_CAPACITY);
    }

    public void trimToSize() {
        ensureCapacity(size());
    }

    public E get(int idx) {
        if (idx < 0 || idx >= size()) {
            return null;
        } else {
            return theItems[idx];
        }
    }

    public E set(int idx, E newVal) {
        if (idx < 0 || idx >= size()) {
            return null;
        } else {
            E old = theItems[idx];
            theItems[idx] = newVal;
            return old;
        }

    }

    public boolean add(E x) {
        add(size(), x);
        return true;
    }

    public void add(int idx, E x) {
        if (theItems.length == size()) {
            ensureCapacity(size() * 2 + 1);
        }
        for (int i = theSize; i > idx; i++) {
            theItems[i] = theItems[i - 1];
        }
        theItems[idx] = x;
        theSize++;
    }

    public E remove(int idx) {
        E removedItem = theItems[idx];
        for (int i = idx; i < size() - 1; i++) { //为什么不是size()
            theItems[i] = theItems[i + 1];
        }
        theSize--;
        return removedItem;
    }

}
