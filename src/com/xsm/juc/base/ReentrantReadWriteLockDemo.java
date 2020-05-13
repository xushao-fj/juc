package com.xsm.juc.base;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: xsm
 * @create: 2020-05-13
 * @description:
 */
public class ReentrantReadWriteLockDemo {

    // TODO 研究下ReentrantReadWriteLock的实现, 原理和使用场景

    public static void main(String[] args) throws Exception{
        // 并发获取读锁
        // concurrencyRead();

        // 一个死循环读锁, 一个竞争写锁, 一个竞争读锁
        concurrencyReadAndWriteLock();
    }

    private static void concurrencyReadAndWriteLock() throws Exception{
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Thread threadPark = new Thread(() -> {
            Lock readLock = lock.readLock();
            readLock.lock();
            try {
                while (true){
                    System.out.println("I have the read lock!! But I wan not to give it you");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }finally {
                readLock.unlock();
            }
        });
        Thread threadWrite = new Thread(() -> {
            Lock writeLock = lock.writeLock();
            writeLock.lock();
            try {
                System.out.println("I have the write lock!!!!");
            }finally {
                writeLock.unlock();
            }
        });
        Thread threadRead = new Thread(() -> {
            Lock readLock = lock.readLock();
            readLock.lock();
            try {
                System.out.println("I have the read lock!!!!");
            }finally {
                readLock.unlock();
            }
        });
        threadPark.start();
        Thread.sleep(2000);
        threadWrite.start();
        Thread.sleep(2000);
        threadRead.start();
    }

    private static void concurrencyRead() throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Thread threadPark = new Thread(() -> {

            Lock readLock = lock.readLock();
            readLock.lock();
            try {
                while (true){
                    System.out.println("I have the read lock!! But I wan not to give it you");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }finally {
                readLock.unlock();
            }
        });
        Thread threadRead = new Thread(() -> {
            Lock readLock = lock.readLock();
            readLock.lock();
            try {
                System.out.println("I have the read lock!!!!");
            }finally {
                readLock.unlock();
            }
        });
        threadPark.start();
        Thread.sleep(2000);
        threadRead.start();
    }

}
