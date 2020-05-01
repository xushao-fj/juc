package com.xsm.juc.base;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author: xsm
 * @create: 2020-04-30
 * @description: Callable Demo
 */
public class TaskDemo implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Thread.sleep(1000L);
        return 2;
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        TaskDemo task = new TaskDemo();
        Future<Integer> result = executor.submit(task);
        // 注意调用get方法会阻塞当前线程, 直到得到结果
        // 所以注意实际编码中建议使用是可以设置超时时间的重载get方法
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("任务执行结束");
        executor.shutdown();
    }
}
