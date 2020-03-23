# 一、类加载器

- 在Java代码中，类型的加载、连接和初始化过程都是在程序运行期间完成的。

- Java虚拟机与程序的生命周期

在如下几种情况下，Java虚拟机将结束生命周期

① 执行了System.exit()方法

② 程序正常执行结束

③ 程序在执行过程中遇到了异常或错误而终止

④ 由于操作系统出现错误而导致Java虚拟机进程终止

## I、类的加载、连接、初始化

加载：查找并加载类的二进制数据

连接：
- 验证：确保被加载的类的正确性
- 准备：为类的**静态变量**分配内存，并将其初始化为**默认值** （比如int 类型的变量会被初始化为0）
- 解析：把类中的符号引用转化为直接引用

初始化：为类的静态变量赋予正确的初始值

![1584601423603](C:\Users\Ferdinand Wang\AppData\Roaming\Typora\typora-user-images\1584601423603.png)



#### 1、Java程序对类的使用方式（两种）

- 主动使用
- 被动使用

所有的Java虚拟机实现必须在每个类或接口被Java程序“首次主动使用”时才初始化它们

**主动使用**

- 创建类的实例（new）

- 访问某个类或接口的静态变量，或者对该静态变量赋值

- 调用类的静态方法

- 反射（Class.forName("com.test.Test")）

- 初始化一个类的子类（class Child extends Parent   初始化Child时也会初始化Parent）

- Java虚拟机启动时被标明启动类的类（Java Test）

- Java 1.7开始提供的动态语言支持：

  java.lang.invoke.MethodHandle实例的解析结果REF_getStatic，REF_putStatic，REF_invokeStatic句柄对应的类没有初始化则初始化

除了以上七种情况，其他使用Java类的方式都被看做是对类的被动使用，都不会导致类的初始化

#### 2、类的加载

类的加载指的是将类.class文件中的二进制数据读入到内存中，将其放在运行时数据区的方法区内，然后再内存中创建一个java.lang.Class对象(规范并未说明Class对象位于哪里，HotSpot虚拟机将其放在了方法区中)用来封装类再方法区内的数据结构

**加载.class文件的方式**

- 从本地系统中直接加载
- 通过网络下载.class文件
- 从zip,jar等归档文件中加载.class文件
- 从专有数据库中提取class文件
- 将Java源文件动态编译为.class文件

主动使用类的示例代码1如下

```java
package com.jvm.classloader;

/**
 * 对于静态字段来说，只有直接定义了该字段的类才会被初始化
 *
 * out1:
 * MyParent1 static block
 * hello parent
 *
 * 当一个类在初始化时，要求其父类全部都已经初始化完毕
 * out2:
 * MyParent1 static block
 * MyChild1 static block
 * hello child
 *
 * -XX:+TraceClassLoading,用于追踪类的加载信息并打印出来
 * -XX:+<option>, 表示开启option选项
 * -XX:-<option>, 表示关闭option选项
 * -XX:<option>=<value>, 表示将option选项设置为value
 */
public class MyTest1 {
    public static void main(String[] args) {
        System.out.println("out1:");
        System.out.println(MyChild1.str);

//        System.out.println("out2:");
//        System.out.println(MyChild1.str2);
    }
}

class MyParent1{
    public static String str = "hello parent";

    static {
        System.out.println("MyParent1 static block");
    }
}

class MyChild1 extends MyParent1{

    public static String str2 = "hello child";

    static {
        System.out.println("MyChild1 static block");
    }

}
```

主动使用类的示例代码2如下

```java
package com.jvm.classloader;

/**
 * out1：
 * MyParent2 static block
 * hello parent
 *
 * out2：
 * hello parent
 * 常量在编译阶段会存入到调用这个常量的方法所在的类的常量池当中
 * 本质上，调用类并没有直接引用到定义常量的类，因此并不会触发
 * 定义常量的类的初始化
 * 注意：这里指的是将常量存放到了MyTest2的常量池中，之后MyTest2与MyParent2就没有任何关系了
 * 甚至我们可以将MyParent2的class文件删除
 *
 * 反编译命令（在class文件目录下执行）
 * javap -c MyTest2.class
 *
 * 助记符：
 * ldc表示将int，float或是String类型的常量值从常量池中推送至栈顶
 * bipush表示将单字节（-128~127）的常量值推送至栈顶
 * sipush表示将一个短整型常量值（-32768~32767）推送至栈顶
 * iconst_1表示将int类型的数字1推送至栈顶 (iconst_m1~iconst_5 jvm认为-1-5比较常用)
 */
public class MyTest2 {

    public static void main(String[] args) {
        System.out.println(MyParent2.num);
    }

}

class MyParent2{

//  1.
//    public static String str1 = "hello parent";
//  2.
//    public static final String str1 = "hello parent";

    public static final int num = 6;

    static {
        System.out.println("MyParent2 static block");
    }
}
```



#### 3、类加载器的工作流程图

![1584684589065](C:\Users\Ferdinand Wang\AppData\Roaming\Typora\typora-user-images\1584684589065.png)

**详细过程（帮助理解）**

![1584685457938](C:\Users\Ferdinand Wang\AppData\Roaming\Typora\typora-user-images\1584685457938.png)



类的加载的最终产品是位于内存中的Class对象

Class对象封装了类在方法区内的数据结构，并且向java程序员提供了方法区内的数据结构的接口

#### 4、类加载器类型

- java虚拟机自带的加载器
  - 根类加载器（Bootstrap）
  - 扩展类加载器（Extension）
  - 系统（应用）类加载器（System）
- 用户自定义的类加载器
  - java.lang.ClassLoader的子类
  - 用户可以定制类的加载方式

**JVM规范允许类加载器在预料某个类将要被使用时就预先加载它，如果在预先加载的过程中遇到了.class文件确实或者存在错误，类加载器必须在程序首次主动使用该类时才报告错误（LinkageError）**

**如果这个类一致没有被程序主动使用，那么类加载器就不会报告错误**

#### 5、类加载器的双亲委托机制

在双亲委托机制中，各个加载器按照父子关系形成了**树形结构**，除了根类加载器之外，其余的类加载器都有且只有一个父加载器

注意：这里的树形结构时逻辑关系，不是物理意义上的继承

```java
//获取当前类的ClassLoader
clazz.getClassLoader();
//获取当前线程上下文的ClassLoader
Thread.currentThread().getContextClassLoader();
//获得系统的ClassLoader
ClassLoader.getSystemClassLoader();
//获得调用者的ClassLoader
DriverMananger.getCallerClassLoader();
```

#### 6、命名空间

- 每个类加载器都有自己的命名空间，命名空间由该加载器及所有父加载器所加载的类组成
- 在同一个命名空间中，不会出现类的完整名字（包括类的包名）相同的两个类
- 在不同的命名空间中，有可能会出现类的完整名字（包括类的包名）相同的两个类

#### 7、类的卸载

当MySample类被加载、连接和初始化后，它的声明周期就开始了，当代表MySample类的Class对象不再被引用，即不可触及时，Class对象就会结束生命周期，MySample类在方法区内的数据也会被卸载，从而结束MySample类的生命周期。

一个类何时结束生命周期，取决于代表它的Class对象何时结束生命周期





