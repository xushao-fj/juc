package com.xsm.juc.base;

/**
 * join() 方法是Thread类的一个实例方法. 他的作用是让当前线程陷入等待状态, 等join的这个线程执行完成后, 再继续执行当前线程.
 * 有时候, 主线程创建并启动了子线程, 如果子线程中需要进行大量的耗时运算, 主线程往往将早于子线程结束之前结束.
 * 如果主线程想等待子线程执行完毕后, 获得子线程中的处理完的某个数据, 就要用到join方法了
 *
 * 在程序中:
 * 主线程调用子线程的join方法, 则相当于主线程中调用了子线程的wait方法, 当子线程执行完(或者到达等待时间), 子
 * 线程会自动调用自身的notifyAll方法唤醒主线程, 从而达到同步的目的.
 * @author xsm
 * @Date 2020/5/1 15:40
 */
public class JoinDemo {

    static class ThreadA implements Runnable {

        @Override
        public void run() {
            try {
                System.out.println("我是子线程, 我先睡1秒");
                Thread.sleep(1000L);
                System.out.println("我是子线程, 我睡完了1秒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ThreadA());
        thread.start();
        thread.join(); // join() -> 底层使用了wait()方法
        // Thread.currentThread().join(1000L); // 如果放掉这段代码, 那么程序一直会阻塞着,
                                                  // 需要设置一个等待时间, 不然会导致主线程一直阻塞
        System.out.println("如果不加join方法, 我会先被打出来, 加了就不一样");
    }
}
