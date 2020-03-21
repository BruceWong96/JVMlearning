package com.jvm.classloader;

import java.io.*;

public class MyTest16 extends ClassLoader {

    private String classLoaderName;

    private String path;

    private final String fileExtension =".class";

    public MyTest16(String classLoaderName){
        super();  //将系统类加载器当作该类加载器的父加载器
        this.classLoaderName = classLoaderName;
    }

    public MyTest16(ClassLoader parent, String classLoaderName){
        super(parent); //显式指定该类加载器的父加载器
        this.classLoaderName = classLoaderName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "MyTest16{" +
                "classLoaderName='" + classLoaderName + '\'' +
                '}';
    }

    @Override
    protected Class<?> findClass(String calssName) throws ClassNotFoundException {
        System.out.println("findClass invoked: " + calssName);
        System.out.println("class loader name: " + this.classLoaderName);

        byte[] data = this.loadClassData(calssName);
        return this.defineClass(calssName, data, 0, data.length);
    }

    private byte[] loadClassData(String className) {

        InputStream is = null;
        byte[] data = null;
        ByteArrayOutputStream baos = null;

        className = className.replace(".","/");

        try{
            this.classLoaderName = this.classLoaderName.replace(".", "/");

            is = new FileInputStream(new File(this.path + className + this.fileExtension));
            baos = new ByteArrayOutputStream();

            int ch = 0;

            while (-1 != (ch = is.read())){
                baos.write(ch);
            }

            data = baos.toByteArray();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                is.close();
                baos.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return data;
    }



    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        MyTest16 loader1 = new MyTest16("loader1");

//        loader1.setPath("H://IDEA_workspace/JVMlearning/out/production/JVMlearning/");
//C://Users/Ferdinand Wang/Desktop
        loader1.setPath("C://Users/Ferdinand Wang/Desktop/");
        Class<?> clazz = loader1.loadClass("com.jvm.classloader.MyTest1");

        System.out.println("class: " + clazz.hashCode());

        Object object = clazz.newInstance();

        System.out.println(object);

        System.out.println("------------------------------");

        MyTest16 loader2 = new MyTest16("loader2");
        loader2.setPath("C://Users/Ferdinand Wang/Desktop/");
        Class<?> clazz2 = loader2.loadClass("com.jvm.classloader.MyTest1");

        System.out.println("class: " + clazz2.hashCode());
        Object object2 = clazz2.newInstance();
        System.out.println(object2);

        System.out.println();
    }


}
