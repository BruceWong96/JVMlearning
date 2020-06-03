package com.notsafe;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *  ArrayList(Java 8)  一个Object类型,初始值是10的数组
 *
 *  1.new ArrayList<>()  底层实际上是一个Object类型的数组
 *  2.size初始值 10   (HashMap初始值是16)
 *  3.每次扩容到原值的一半 (copyOf方法来搬家) 10 15 22 33   (hashmap的扩容是原值的一倍)
 *  4.线程不安全
 *  5.线程不安全的错误代码
 *  6.  Collections 和 Collection 区别
 *      Collection是集合类的顶级接口
 *      Collections是集合的一个工具类
 *  7.HashMap, HashSet, ArrayList 都是线程不安全的
 *
 *  1-故障现象:
 *  java.util.ConcurrentModificationException
 *  2-导致原因:
 *
 *  3-解决方法
 *      3.1 换成 new Vector(线程安全)   需要效率则选择ArrayList
 *      3.2 Collections.synchronizedList(new ArrayList<>())
 *      3.3 new CopyOnWriteArrayList<>()
 *          读写分离的一种体现
 *
 *  4-优化建议(同样的错误不出现第二次)
 *
 */
public class NotSafeDemo03 {

    public static void main(String[] args) {

    }

    public static void mapNoSafe() {
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }

    public static void setNoSafe() {
        Set<String> set = new CopyOnWriteArraySet<>();

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }

    public static void listNoSafe() {

        List<String> list = new CopyOnWriteArrayList<>(); //写时赋值
        //Collections.synchronizedList(new ArrayList<>());
        //new Vector<>();
        //new ArrayList<>();

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
