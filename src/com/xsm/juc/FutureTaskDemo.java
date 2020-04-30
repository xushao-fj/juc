package com.xsm.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author: xsm
 * @create: 2020-04-30
 * @description:
 */
public class FutureTaskDemo implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Thread.sleep(1000L);
        return 2;
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        FutureTask<Integer> futureTask = new FutureTask<>(new FutureTaskDemo());
        executor.submit(futureTask);
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}
