package com.sxk.queue;

import java.util.concurrent.LinkedBlockingQueue;

public class QueueTest {

    private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>(2);

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        QueueTest t = new QueueTest();
        t.t1();
        t.t2();
        //t.t3();
    }

    /* 
     * 
       LinkedBlockingQueue构造的时候若没有指定大小，则默认大小为Integer.MAX_VALUE，
             当然也可以在构造函数的参数中指定大小。LinkedBlockingQueue不接受null。
       add方法在添加元素的时候，若超出了度列的长度会直接抛出异常。IllegalStateException: Queue full
      
             对于put方法，若向队尾添加元素的时候发现队列已经满了会发生阻塞一直等待空间，以加入元素。
     
       offer方法在添加元素时，如果发现队列已满无法添加的话，会直接返回false。
    */

    /* 
     *   
        remove:若队列为空，抛出NoSuchElementException异常。
        take:若队列为空，发生阻塞，等待有元素。
        poll: 若队列为空，返回null。
        
        add-remove
        put-take 阻塞
        offer-poll
        
      */

    public void t1() {
        try {
            queue.add("hello");
            boolean isAdd = queue.add("world");
            System.out.println("add:" + isAdd);
            isAdd = queue.add("yes");
            System.out.println("add:" + isAdd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void t2() {
        try {
            queue.put("hello");
            queue.put("world");
            queue.put("yes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void t3() {
        try {
            queue.offer("hello");
            boolean isAdd = queue.offer("world");
            System.out.println("offer:" + isAdd);
            isAdd = queue.offer("yes");
            System.out.println("offer:" + isAdd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
