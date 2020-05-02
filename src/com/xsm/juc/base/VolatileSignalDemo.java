package com.xsm.juc.base;

/**
 * volitile 关键字能够保证内存的可见性, 如果用去哦立体了关键字声明了一个变量,
 * 在一个线程里面改变了这个变量的值, 那其他线程是立马课件更改后的值的
 * @author xsm
 * @Date 2020/5/1 15:20
 */
public class VolatileSignalDemo {

    /**
     * 以下代码, 线程A输出0, 然后线程B输出1, 再然后线程A输出2, 以此类推
     */
    private static volatile int signal = 0;

    static class ThreadA implements Runnable {

        @Override
        public void run() {
            while (signal < 5){
                if (signal % 2 == 0) {
                    System.out.println("threadA: " + signal);
                    synchronized (this) { // 加锁, 防止并发修改 signal, 因为 signal++ 不是原子操作
                        signal ++;
                    }
                }
            }
        }
    }

    static class ThreadB implements Runnable {

        @Override
        public void run() {
            while (signal < 5){
                if (signal % 2 == 1) {
                    System.out.println("threadB: " + signal);
                    synchronized (this) {
                        signal ++;
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
