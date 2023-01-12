package org.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author song
 * @version 1.0
 * @date 2023/1/12 15:03
 * @description: TODO
 */

public class CompletableFutureAPIDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });
//        System.out.println(completableFuture.get());
//        System.out.println(completableFuture.get(2,TimeUnit.SECONDS));
//        System.out.println(completableFuture.join());
        //getNow时如果计算完成就返回return的内容，否则返回valueIfAbsent
        System.out.println(completableFuture.getNow("xxx"));
        System.out.println(completableFuture.complete("completeValue")+ "\t "+completableFuture.join());
    }
}
