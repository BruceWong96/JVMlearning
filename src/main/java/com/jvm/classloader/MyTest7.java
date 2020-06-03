package com.jvm.classloader;

public class MyTest7 {

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz = Class.forName("java.lang.String");
        System.out.println(clazz.getClassLoader()); //null

        Class<?> clazz2 = Class.forName("com.jvm.classloader.C");
        System.out.println(clazz2.getClassLoader()); //sun.misc.Launcher$AppClassLoader@18b4aac2
    }
}

class C{

}
