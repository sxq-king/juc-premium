package org.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author song
 * @version 1.0
 * @date 2023/1/12 15:17
 * @description: TODO
 */

public class CompletableFutureAPI2Demo {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture.supplyAsync(()->{
            //暂停几秒钟
            try{TimeUnit.SECONDS.sleep(1);}catch (InterruptedException e){e.printStackTrace();}
            System.out.println("111");
            return 1;
        },threadPool).handle((f,e)->{
            int i = 10/0;
            System.out.println("222");
            return f+2;
        }).handle((f,e)->{
            System.out.println("333");
            return f+3;
        }).whenComplete((v,e)->{
            if(e == null){
                System.out.println("------计算结果："+ v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            System.out.println("异常"+ e.getCause() + " 信息"+ e.getMessage());
            return null;
        });
        System.out.println(Thread.currentThread().getName() + " 主线程先去忙其他任务");
        threadPool.shutdown();

    }
}
