package com.sxk.common.utils;

/**
算数运算符、关系运算符、逻辑运算符、位运算符。
算数运算符(9)：+  -  *  /  %  ++  --
关系运算符(6)：==  !=  >  >=  <  <=
逻辑运算符(6)：&&  ||  !  ^  &  |
位运算符(7)：&  |  ~  ^  >>  <<  >>>
-------------------------------------------------------------------------
Java基本数据类型：
数值类型：
整型：byte、short、int、long
非整型：double、float
非数值类型：char[字符]、boolean[布尔]
-------------------------------------------------------------------------
一：算数运算符：
注：算数运算符操作数必须是数值类型。
分为一元运算符和二元运算符；
一元运算符，只有一个操作数；
二元运算符有两个操作数，运算符在两个操作数之间。
一元运算符：正'+'，负'-'，自加'++'，自减'--'这四个。
①"++"和"--"运算符，只允许用于数值类型的变量，不允许用于表达式中；
    "++"和"--"可以用于数值变量之前或者之后；
    两处使用差别：
       "++"和"--"用于数值变量之前，在赋值操作中，先对被"++"或"--"操作变量值先加1或者先减1，然后在进行其他的操作；
       "++"和"--"用于数值变量之后，在赋值操作中，先用被"++"或"--"的操作变量值进行其他的操作，然后在对其值加1或者减1。
       
        boolean： 类型变量的取值有：ture、false,1字节（8位）  
        char：数据类型有：unicode字符,16位  
        byte：一个字节（8位）（-128~127）（-2的7次方到2的7次方-1）  
        short：两个字节（16位）（-32768~32767）（-2的15次方到2的15次方-1）  
        int：四个字节（32位）（一个字长）（-2147483648~2147483647）（-2的31次方到2的31次方-1）  
        long：八个字节（64位）（-9223372036854774808~9223372036854774807）（-2的63次方到2的63次方-1）  
        float：四个字节（32位）（3.402823e+38 ~ 1.401298e-45）（e+38是乘以10的38次方，e-45是乘以10的负45次方）  
        double：八个字节（64位）（1.797693e+308~ 4.9000000e-324）
       
       
       
**/
public class OperatorUtils {

    public static void main(String[] args) {
        OperatorUtils outils = new OperatorUtils();
        outils.test1();
        System.out.println("===================");
        outils.test2();
        System.out.println("===================");
        outils.test3();
        System.out.println("===================");
        outils.test4();
        System.out.println("===================");
        outils.test5();
    }

    private void test1() {
        int a = 5;
        int b, c, d, f, g, h;
        b = +a; //正值  
        System.out.println("b=" + b + ",a=" + a);
        c = -a; //负值  
        System.out.println("c=" + c + ",a=" + a);
        int l = 2;
        d = ++l; //先l=l+1;再d=l  
        System.out.println("d=" + d + ",l=" + l);
        int m = 3;
        f = m++;//先f=m;再m=m+1  
        System.out.println("f=" + f + ",m=" + m);
        int n = 4;
        g = --n;//先n=n-1;再g=n  
        System.out.println("g=" + g + ",n=" + n);
        int o = 6;
        h = o--;//先h=o;再o=o-1  
        System.out.println("h=" + h + ",o=" + o);
    }

    private void test2() {
        int a = 3;
        double b = 3.53;//或者3.53d  
        float c = 1.7f;
        int d = 1;
        System.out.println("int /int :a/d=" + a / d);
        System.out.println("double/int:b/a=" + b / a);
        System.out.println("float/int:c/a=" + c / a);
    }

    private void test3() {
        int a = 7;
        double b = 5.33;
        b = a;
        a = (int) b;
    }

    private void test4() {
        System.out.println("9.5<8 :" + (9.5 < 8));
        System.out.println("8.5<=8.5:" + (8.5 <= 8.5));
        System.out.println("a~z：" + ((int) 'a') + "~" + ((int) 'z'));
        System.out.println("A~Z：" + ((int) 'A') + "~" + ((int) 'Z'));
        System.out.println("'A' < 'a':" + ('A' < 'a'));//字符'A'的Unicode编码值小于字符'a'  
    }

    private void test5() {
        System.out.println(1 << 3);
        System.out.println(8 >> 3);
        int a = 15; //x等于二进制数的00001111
        int b = 6; //y等于二进制数的00000110
        int c = a & b; //z等于二进制数的00000110
        System.out.println(c);
    }
}
