package com.jvm.classloader;

class Parent10{
    static int a = 3;

    static {
        System.out.println("Parent10 static block");
    }
}

class Child10 extends Parent10{
    static int b = 4;

    static {
        System.out.println("Child10 static block");
    }
}

public class MyTest10 {
    static {
        System.out.println("MyTest10 static block");
    }

    public static void main(String[] args) {
        Parent10 parent10;
        System.out.println("--------------");
        parent10 = new Parent10();

        System.out.println("--------------");
        System.out.println(parent10.a);

        System.out.println("--------------");
        System.out.println(Child10.b);

    }
}
