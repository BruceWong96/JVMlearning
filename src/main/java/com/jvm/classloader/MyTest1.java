package com.jvm.classloader;

/**
 * 对于静态字段来说，只有直接定义了该字段的类才会被初始化
 *
 * out1:
 * MyParent1 static block
 * hello parent
 *
 * 当一个类在初始化时，要求其父类全部都已经初始化完毕
 * out2:
 * MyParent1 static block
 * MyChild1 static block
 * hello child
 *
 * -XX:+TraceClassLoading,用于追踪类的加载信息并打印出来
 * -XX:+<option>, 表示开启option选项
 * -XX:-<option>, 表示关闭option选项
 * -XX:<option>=<value>, 表示将option选项设置为value
 */
public class MyTest1 {
    public static void main(String[] args) {
        System.out.println("out1:");
        System.out.println(MyChild1.str);

//        System.out.println("out2:");
//        System.out.println(MyChild1.str2);
    }
}

class MyParent1{
    public static String str = "hello parent";

    static {
        System.out.println("MyParent1 static block");
    }
}

class MyChild1 extends MyParent1{

    public static String str2 = "hello child";

    static {
        System.out.println("MyChild1 static block");
    }

}