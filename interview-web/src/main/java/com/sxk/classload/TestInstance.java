package com.sxk.classload;

public class TestInstance {

    /**
     *（1）在加载阶段，加载类的信息
            （2）在链接的准备阶段给instance、a、b做默认初始化并分配空间，此时a和b的值都为0
            （3）在初始化阶段，给静态变量做显式初始化，此时b的值仍为0
            （4）在初始化阶段，执行构造方法，此时a和b的值都为1
     * 
     */

    public static TestInstance instance = new TestInstance();
    public static int          a;
    public static int          b        = 0;

    private TestInstance() {
        a++;
        b++;
    }

    public static void main(String[] args) {
        System.out.println(TestInstance.a);
        System.out.println(TestInstance.b);
    }
}
