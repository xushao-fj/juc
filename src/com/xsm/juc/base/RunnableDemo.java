package com.xsm.juc.base;

/**
 * @author: xsm
 * @create: 2020-04-30
 * @description: Runnable接口Demo
 */
public class RunnableDemo {

    /**
     * Thread 类与 Runnable接口的比较
     * 1. 由于Java 单集成, 多实现的特性, Runnable接口使用起来比Thread更灵活
     * 2. Runnable接口出现更符合面向对象, 将线程单独进行对象的封装
     * 3. Runnable接口出现, 降低了线程对象和线程任务的耦合性
     * 4. 如果使用线程是不需要使用Thread类的诸多方法, 显然使用Runnable接口更为轻量
     */

    public static void main(String[] args) {
        new Thread(new MyThread(), "A").start();

        // 函数式编程方式
        new Thread(() -> {
            System.out.println("java 8 匿名内部类");
        }).start();
    }

    static class MyThread implements Runnable {
        @Override
        public void run() {
            System.out.println("myThread");
        }
    }
}
