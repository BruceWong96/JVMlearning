package com.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目:三个售票员  卖出  30张票
 *
 * 笔记  如何编写企业级的多线程代码
 *      固定的编程套路+模板
 *      1.高内聚低耦合的前提下,  线程 操作 资源类
 *          1.1 先创建一个资源类
 */
public class SaleTicketDemo01 {
    public static void main(String[] args) {  //主线程,一切程序的入口

        final Ticket ticket = new Ticket();

//        Runnable接口是函数式接口,可以使用Lambda接口
//        @FunctionalInterface
//        public interface Runnable {
//            public abstract void run();
//        }

        //Lambdas
        new Thread(() -> { for (int i = 0; i < 40; i++) ticket.sale();} , "A").start();
        new Thread(() -> { for (int i = 0; i < 40; i++) ticket.sale();} , "B").start();
        new Thread(() -> { for (int i = 0; i < 40; i++) ticket.sale();} , "C").start();

        //Thread(Runnable target, String name)  接口可以New
        //匿名内部类
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 40; i++) {
                    ticket.sale();
                }
            }
        }, "A").start();  //start是让线程就绪,等待被调用

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 40; i++) {
                    ticket.sale();
                }
            }
        }, "B").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 40; i++) {
                    ticket.sale();
                }
            }
        }, "C").start();*/

    }
}

//票
class Ticket  //资源类 = 实例变量 + 实例方法
{
    private int number = 30;
    //List list = new ArrayList();

    //可重入锁   这个锁谁都能用(不认人)
    Lock lock = new ReentrantLock();

    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(
                        Thread.currentThread().getName()
                        + "\t 卖出第:"
                        + (number--)
                        + "\t 还剩下: "
                        + number
                );
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}












