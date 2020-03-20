package com.jvm.classloader;

import java.util.Random;

/**
 * 当一个接口在初始化时，并不要求其父接口都完成了初始化
 * 只有在真正使用父接口的时候（如引用接口中所定义的常量时），才会导致父接口初始化
 */
public class MyTest5 {
    public static void main(String[] args) {
        System.out.println(MyChild5.b);
    }
}

interface MyParent5{
//接口当中的public、static和final是默认的，可以不写
    public static final int a = 5;
}

interface MyChild5 extends MyParent5{
    public static final int b = 6;
//    public static final int b = new Random().nextInt(2);

}
