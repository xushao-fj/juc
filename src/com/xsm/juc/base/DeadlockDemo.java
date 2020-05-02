package com.xsm.juc.base;

/**
 * 死锁demo
 * @author xsm
 * @Date 2020/5/1 22:35
 */
public class DeadlockDemo {

    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    static class ThreadA implements Runnable {

        @Override
        public void run() {
            synchronized (LOCK1) {
                System.out.println("ThreadA获得LOCK1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK2) {
                    System.out.println("ThreadA获得LOCK2");
                }
            }
        }
    }

    static class ThreadB implements Runnable {

        @Override
        public void run() {
            synchronized (LOCK2) {
                System.out.println("ThreadA获得LOCK2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK1) {
                    System.out.println("ThreadA获得LOCK1");
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new ThreadA()).start();
        new Thread(new ThreadB()).start();
    }
}
