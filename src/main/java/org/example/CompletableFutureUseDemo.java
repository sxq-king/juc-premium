package org.example;

import java.util.concurrent.*;

/**
 * @author song
 * @version 1.0
 * @date 2023/1/12 13:48
 * @description: TODO
 */

public class CompletableFutureUseDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        try {
            CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(()->{
                System.out.println(Thread.currentThread().getName() + "\t coming in");
                int result = ThreadLocalRandom.current().nextInt(10);
                //暂停几秒钟线程
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("------1秒钟后出结果" + result);
                if(result > 5){
                    int i = 10/0;
                }
                return result;
            },threadPool).whenComplete((v,e)->{
                if(e == null){
                    System.out.println("计算完成，更新updateValue：" + v);
                }
            }).exceptionally(e->{
                e.printStackTrace();
                System.out.println("异常情况"+e.getCause()+ "\t"+e.getMessage());
                return null;
            });
            System.out.println(Thread.currentThread().getName() + "主线程先去忙其他任务");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }

        //主线程暂停几秒钟线程 主线程不要立刻结束，否则completableFuture默认使用的线程池会立刻关闭
//        try {
//            TimeUnit.SECONDS.sleep(3);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private static void future1() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName() + "\t coming in");
            int result = ThreadLocalRandom.current().nextInt(10);
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("------1秒钟后出结果" + result);
            return result;
        });

        System.out.println(Thread.currentThread().getName() + "主线程先去忙其他任务");
        System.out.println(completableFuture.get());
    }
}
