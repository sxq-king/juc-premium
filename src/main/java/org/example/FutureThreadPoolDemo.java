package org.example;

import java.util.concurrent.*;

/**
 * @author song
 * @version 1.0
 * @date 2023/1/12 10:06
 * @description: TODO
 */

public class FutureThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        m1();
        m2();
    }

    private static void m2() throws InterruptedException, ExecutionException {
        //3个任务 ，开启多个异步任务线程来处理，耗时多少
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        long start = System.currentTimeMillis();
        FutureTask<String> futureTask1 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task1 over";
        });
        threadPool.submit(futureTask1);
        FutureTask<String> futureTask2 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task2 over";
        });
        threadPool.submit(futureTask2);
        System.out.println(futureTask1.get());
        System.out.println(futureTask2.get());
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("------costTime " + (end - start) + " 毫秒");
        threadPool.shutdown();
    }

    private static void m1() {
        //3个任务 ，只有一个main线程来处理，耗时多少
        long start = System.currentTimeMillis();
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("------costTime " + (end - start) + " 毫秒");

        System.out.println(Thread.currentThread().getName() + "\t ------end");
    }
}
