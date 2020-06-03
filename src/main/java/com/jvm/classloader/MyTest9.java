package com.jvm.classloader;

class Parent9{
    static int a = 3;

    static {
        System.out.println("Parent9 static block");
    }
}

class Child9 extends Parent9{
    static int b = 4;

    static {
        System.out.println("Child9 static block");
    }
}

public class MyTest9 {

    static {
        System.out.println("MyTest9 static block");
    }

    public static void main(String[] args) {
        System.out.println(Child9.b);
        /**
         * out:
         * MyTest9 static block
         * Parent9 static block
         * Child9 static block
         * 4
         */
    }
}
