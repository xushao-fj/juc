package com.xsm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author xsm
 * @date 2020/07/16
 * @description
 * 优雅关闭线程池测试
 */
public class GracefulShutDownThreadPoolDemo {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private final static Object lock = new Object();

    public static void main(String[] args) {
        executorService.execute(() -> doService());
        //shutDown1();
        Runtime.getRuntime().addShutdownHook(new Thread(GracefulShutDownThreadPoolDemo::shutDonw2));
         //shutDonw2();
    }

    public static void doService() {
        synchronized (lock) {
            System.out.println("开始执行任务");
            try {
                System.out.println("阻塞当前任务");
                for (;;){

                }
            } finally {

            }

        }
    }

    // 关闭不了线程池
    public static void shutDown1(){
        // 关闭线程池
        executorService.shutdown();
        System.out.println("关闭线程池");
    }

    // 优雅关闭
    public static void shutDonw2(){
        shutDown1();
        try {
            // 等待 10 s
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                // 调用 shutdownNow 取消正在执行的任务
                executorService.shutdownNow();
                // 再次等待 10 s，如果还未结束，可以再次尝试，或者直接放弃
                if (!executorService.awaitTermination(10, TimeUnit.SECONDS)){
                    System.err.println("线程池任务未正常执行结束");
                }
            }
        } catch (InterruptedException ie) {
            // 重新调用 shutdownNow
            executorService.shutdownNow();
        }
    }

}
