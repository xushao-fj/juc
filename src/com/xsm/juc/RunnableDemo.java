package com.xsm.juc;

/**
 * @author: xsm
 * @create: 2020-04-30
 * @description: Runnable接口Demo
 */
public class RunnableDemo {
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
