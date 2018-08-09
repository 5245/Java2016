package com.sxk.stack.ch1;

public class StackOverFlowDemo {
    int count = 0;

    public void recursiveMethod() {
        if (count == 1000000) {//递归方法执行1000000次时，结束
            return;
        }
        count++;
        System.out.println("执行了:" + count + "次");
        recursiveMethod();//递归调用
    }

    public static void main(String[] args) {
        StackOverFlowDemo stackOverFlowDemo = new StackOverFlowDemo();
        stackOverFlowDemo.recursiveMethod();
        System.out.println("执行其他代码...");
    }
}
