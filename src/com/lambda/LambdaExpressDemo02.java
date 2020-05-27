package com.lambda;

/**
 * i - Lambda表达式
 *  1解决了匿名内部类 代码冗余的问题
 *  2有且仅有一个方法
 *
 * ii - @FunctionalInterface  只能声明一个方法
 * iii - default    接口中可以实现多个方法
 * iv - static  接口中可以实现多个静态方法
 *
 * 拷贝小括号, 写死右箭头, 落地大括号
 *
 */
public class LambdaExpressDemo02 {
    public static void main(String[] args) {
//        Foo foo = new Foo() {
//            @Override
//            public void sayHello() {
//                System.out.println("hello 001");
//            }
//
//            @Override
//            public int add(int x, int y) {
//                return 0;
//            }
//        };

        Foo foo = (int x, int y) -> {
            System.out.println("Come in add method");
            return x + y;
        };
        System.out.println(foo.add(2, 3));
        System.out.println(foo.mul(2, 3));
        System.out.println(Foo.div(2, 3));

    }
}

@FunctionalInterface
interface Foo {
//    public void sayHello();
    //方法声明
    public int add(int x, int y);

    //方法实现
    public default int mul(int x, int y) {
        return x * y;
    }

    public static int div(int x, int y) {
        return x / y;
    }

}