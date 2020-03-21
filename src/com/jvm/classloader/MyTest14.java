package com.jvm.classloader;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class MyTest14 {
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        System.out.println(classLoader); //sun.misc.Launcher$AppClassLoader@18b4aac2

        String resourceName = "com/jvm/classloader/MyTest13.class";

        Enumeration<URL> urls = classLoader.getResources(resourceName);

        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            System.out.println(url);
        }
/**
 * out:
 * file:/H:/IDEA_workspace/JVMlearning/out/production/JVMlearning/com/jvm/classloader/MyTest13.class
 */

    }
}
