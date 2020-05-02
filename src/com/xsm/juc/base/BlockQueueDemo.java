package com.xsm.juc.base;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 阻塞队列 demo
 * @author xsm
 * @Date 2020/5/2 9:14
 */
public class BlockQueueDemo {

    private int queueSize = 10;

    private ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(queueSize);

    /**
     * 这个例子中的输出结果看起来可能有问题，比如有几行在插入一个元素之后，队列的剩余空间不变。
     * 这是由于System.out.println语句没有锁。
     * 考虑到这样的情况：线程1在执行完put/take操作后立即失去CPU时间片，然后切换到线程2执行put/take操作，
     * 执行完毕后回到线程1的System.out.println语句并输出，发现这个时候阻塞队列的size已经被线程2改变了，
     * 所以这个时候输出的size并不是当时线程1执行完put/take操作之后阻塞队列的size，但可以确保的是size不会超过10个。
     * 实际上使用阻塞队列是没有问题的
     */
    public static void main(String[] args) {
        BlockQueueDemo test = new BlockQueueDemo();
        Producer producer = test.new Producer();
        Consumer consumer = test.new Consumer();
        producer.start();
        consumer.start();
    }

    class Consumer extends Thread {
        @Override
        public void run() {
            consume();
        }

        private void consume() {
            while (true) {
                try {
                    queue.take();
                    System.out.println("从队列取走一个元素, 队列剩余" + queue.size() + "个元素");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Producer extends Thread {
        @Override
        public void run() {
            produce();
        }

        private void produce() {
            while (true) {
                try {
                    queue.put(1);
                    System.out.println("向队列中插入一个元素, 队列剩余空间: " + (queueSize - queue.size()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
