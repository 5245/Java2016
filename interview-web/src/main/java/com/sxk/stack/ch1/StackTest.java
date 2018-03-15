package com.sxk.stack.ch1;

import java.util.Stack;

public class StackTest {
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        stack.push("aaaa");
        stack.push("bbbb");
        stack.push("cccc");
        stack.push("dddd");
        stack.push("eeee");
        String s = null;
        while ((s = stack.pop()) != null) {
            System.out.println(s);
            if (stack.isEmpty()) {
                break;
            }
        }
    }
}
