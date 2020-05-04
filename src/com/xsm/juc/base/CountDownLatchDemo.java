package com.xsm.juc.base;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * CountDown代表计数递减，Latch是“门闩”的意思。也有人把它称为“屏障”。
 * 而CountDownLatch这个类的作用也很贴合这个名字的意义，假设某个线程在执行任务之前，需要等待其它线程完成一些前置任务，必须等所有的前置任务都完成，才能开始执行本线程的任务。
 * {@link CountDownLatch}
 * @author: xsm
 * @create: 2020-05-04
 * @description: CountDownLatch Demo
 */
public class CountDownLatchDemo {

    /**
     * 模拟玩游戏钱, 在游戏真正开始之前, 一般会等待一些前置任务完成, 比如
     * 1. 加载地图数据, 2. 加载任务模型, 3. 加载背景音乐 等
     */

    // 定义前置任务线程
    static class PreTaskThread implements Runnable {

        private final String task;

        private final CountDownLatch countDownLatch;

        public PreTaskThread(String task, CountDownLatch countDownLatch) {
            this.task = task;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                Random random = new Random();
                Thread.sleep(random.nextInt(1000));
                System.out.println(task + " - 任务完成");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                countDownLatch.countDown();
            }
        }
    }

    public static void main(String[] args) {
        // 假设有三个模块需要加载
        CountDownLatch countDownLatch = new CountDownLatch(3);

        // 定义主任务
        new Thread(() -> {
            try {
                System.out.println("等待数据加载...");
                System.out.printf("还有%d个前置任务", countDownLatch.getCount());
                countDownLatch.await();
                System.out.println("数据加载完成, 正式开始游戏!");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(new PreTaskThread("加载地图数据", countDownLatch)).start();
        new Thread(new PreTaskThread("加载任务模型", countDownLatch)).start();
        new Thread(new PreTaskThread("记载背景音乐", countDownLatch)).start();
    }
}
