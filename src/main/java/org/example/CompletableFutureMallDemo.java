package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.lang.annotation.Documented;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author song
 * @version 1.0
 * @date 2023/1/12 14:14
 * @description: CompletableFuture案例精讲
 */

public class CompletableFutureMallDemo {
    static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("dangdang"),
            new NetMall("taobao"),
            new NetMall("pdd"),
            new NetMall("tmall")
    );

    /**
     * 一家家搜
     *
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPrice(List<NetMall> list, String productName) {
        //mysql in jd price is 109.91
        return list
                .stream()
                .map(netMall ->
                        String.format(productName + " in %s price is %.2f",
                                netMall.getNetMallName(),
                                netMall.calcPrice(productName)))
                .collect(Collectors.toList());
    }

    public static List<String> getPriceByCompletableFuture(List<NetMall> list, String productName) {
        return list.stream().map(netMall -> CompletableFuture.supplyAsync(() -> String.format(productName + " in %s price is %.2f",
                        netMall.getNetMallName(),
                        netMall.calcPrice(productName))))
                .collect(Collectors.toList())
                .stream()
                .map(s -> s.join())
                .collect(Collectors.toList());
    }

    ;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        List<String> list1 = getPrice(CompletableFutureMallDemo.list, "mysql");
        for (String s : list1) {
            System.out.println(s);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("------costTime:" + (endTime - startTime) + "毫秒");

        long startTime2 = System.currentTimeMillis();
        List<String> list2 = getPriceByCompletableFuture(list, "mysql");
        for (String s : list2) {
            System.out.println(s);
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("------costTime:" + (endTime2 - startTime2) + "毫秒");
        /*
        mysql in jd price is 109.71
        mysql in dangdang price is 110.80
        mysql in taobao price is 110.58
        mysql in pdd price is 109.37
        mysql in tmall price is 110.26
        ------costTime:5209毫秒
        mysql in jd price is 110.91
        mysql in dangdang price is 110.83
        mysql in taobao price is 110.14
        mysql in pdd price is 109.97
        mysql in tmall price is 109.77
        ------costTime:1026毫秒
         */
    }
}

class NetMall {
    @Getter
    private String netMallName;

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }

    public double calcPrice(String productName) {
        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }
}
