package com.sxk.list.ch1;

public class MyLinkedList<E> {
    private static class Node<E> {
        public E       data;
        public Node<E> prev;
        public Node<E> next;

        public Node(E d, Node<E> p, Node<E> n) {
            data = d;
            prev = p;
            next = n;
        }
    }

    private int     theSize;
    private int     modCount;
    private Node<E> beginMarker;
    private Node<E> endMarker;

    public MyLinkedList() {
        clear();
    }

    public void clear() {
        beginMarker = new Node<E>(null, null, null);
        endMarker = new Node<E>(null, beginMarker, null);
        beginMarker.next = endMarker;
        theSize = 0;
    }

    public int size() {
        return theSize;
    }

    public boolean add(E x) {
        add(size(), x);
        return true;
    }

    public void add(int idx, E x) {
        addBefore(getNode(idx), x);
    }

    public E get(int idx) {
        return getNode(idx).data;
    }

    private void addBefore(Node<E> p, E x) {
        Node<E> newNode = new Node<E>(x, p.prev, p);
        newNode.prev.next = newNode;
        p.prev = newNode;
        theSize++;
        modCount++;
    }

    private Node<E> getNode(int idx) {
        Node<E> p;

        if (idx < 0 || idx > size()) {
            System.out.println("IndexOutOfBoundsException");
        }

        if (idx <= size() / 2) {
            System.out.println(idx);
            p = beginMarker.next;
            for (int i = 0; i < idx; i++) {
                p = p.next;
            }
        } else {
            p = endMarker;
            for (int i = size(); i > idx; i--) {
                p = p.prev;
            }
        }

        return p;

    }

    public boolean find(E x) {
        Node<E> p = beginMarker.next;
        for (int i = 0; i < size(); i++) {
            if (p.data == x) {
                return true;
            }
            p = p.next;
        }
        return false;
    }

    /**这个方法还有点问题，打印前面多了个null，昨天实在太困了，还没来得及完善，待续......**/
    public String toString() {
        String s = null;
        Node<E> p = beginMarker.next;
        for (int i = 0; i < size(); i++) {
            s += p.data + ",";
            p = p.next;
        }
        return s;
    }

}
