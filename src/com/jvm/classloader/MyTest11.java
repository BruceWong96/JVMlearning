package com.jvm.classloader;

class Parent11{
    static int a = 3;

    static {
        System.out.println("Parent11 static block");
    }

    static void doSomething(){
        System.out.println("Do something");
    }
}

class Child11 extends Parent11{
    static {
        System.out.println("Child11 static block");
    }
}

public class MyTest11 {
    public static void main(String[] args) {
        System.out.println(Child11.a);
        Child11.doSomething();
        /**
         * out:
         * Parent11 static block
         * 3
         * Do something
         */
    }
}
