package com.xsm.juc;

/**
 * @author: xsm
 * @create: 2020-04-30
 * @description: 线程demo
 */
public class ThreadDemo extends Thread{

    public static void main(String[] args) {
        ThreadDemo thread = new ThreadDemo();
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("新建运行线程");
    }
}
