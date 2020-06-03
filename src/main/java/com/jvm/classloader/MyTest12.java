package com.jvm.classloader;

class CL{
    static {
        System.out.println("Class CL");
    }
}

public class MyTest12 {
    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        Class<?> clazz = loader.loadClass("com.jvm.classloader.CL"); //不会导致CL被初始化

        System.out.println(clazz);

        System.out.println("------------------");

        clazz = Class.forName("com.jvm.classloader.CL");  //反射机制触发类的初始化

        System.out.println(clazz);

        /**
         * out:
         * class com.jvm.classloader.CL
         * ------------------
         * Class CL
         * class com.jvm.classloader.CL
         */
    }
}
