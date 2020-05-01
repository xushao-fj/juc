package com.xsm.juc.base;

/**
 * 等待 / 通知机制
 * Java 多线程的等待 / 通知机制是基于Object类的wait()方法和notify(), notifyAll()方法来实现的
 * 场景:
 * notify()方法会随机侥幸一个正在等待的线程, 而notifyAll()会唤醒所有正在等待的线程
 * 一个锁同一时刻只能被一个线程持有, 而加入线程A现在持有了一个锁LOCK并开始执行, 他可以使用LOCK.wait()让
 * 自己进入等待状态. 这个时候, LOCK这个锁是被释放掉的.
 * 这时, 线程B获得了lock这个锁并开始执行, 他可以在某一时刻, 使用LOCK.notify(), 通知之前持有LOCK锁并进入等待状态的线程A,
 * 说线程A你可以不用等了, 可以往下执行了.
 * 注意:
 * 1. 这个时候线程B并没有释放锁LOCK, 除非线程B这个时候使用LOCK.wait()释放锁, 或者线程B执行结束自行释放锁, 线程A才能得到LOCK锁.
 * 2. 等待 / 通知机制使用的是同一个对象锁, 如果你两个线程使用的是不同的对象锁, 那他们之间不能用等待 / 通知机制通信的
 * @author xsm
 * @Date 2020/5/1 15:02
 */
public class WaitAndNotifyDemo {

    private final static Object LOCK = new Object();

    static class ThreadA implements Runnable {

        @Override
        public void run() {
            synchronized (LOCK) {
                for (int i = 0; i < 5; i++) {
                    try {
                        System.out.println("ThreadA: " + i);
                        LOCK.notify();
                        LOCK.wait();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    static class ThreadB implements Runnable {

        @Override
        public void run() {
            synchronized (LOCK) {
                for (int i = 0; i < 5; i++) {
                    try {
                        System.out.println("ThreadB: " + i);
                        LOCK.notify();
                        LOCK.wait();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new ThreadA()).start();
        Thread.sleep(1000L);
        new Thread(new ThreadB()).start();
    }

}
