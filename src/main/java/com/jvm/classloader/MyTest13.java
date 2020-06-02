package com.jvm.classloader;

public class MyTest13 {
    public static void main(String[] args) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        System.out.println(classLoader);

        while (null != classLoader){
            classLoader = classLoader.getParent();
            System.out.println(classLoader);
        }
        /**
         * out:
         * sun.misc.Launcher$AppClassLoader@18b4aac2
         * sun.misc.Launcher$ExtClassLoader@1b6d3586
         * null         //Bootstrap
         */

        System.out.println("---------------------------");

        Class<?> clazz = String.class;
        System.out.println(clazz.getClassLoader());

    }
}
