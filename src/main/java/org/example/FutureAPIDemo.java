package org.example;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author song
 * @version 1.0
 * @date 2023/1/12 10:33
 * @description: TODO
 */

public class FutureAPIDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + "\t coming in");
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task over";
        });
        new Thread(futureTask).start();
        //get会阻塞
        System.out.println(Thread.currentThread().getName() + "\t ------忙其他任务了");
//        System.out.println(futureTask.get());
//        System.out.println(futureTask.get(3,TimeUnit.SECONDS));
        while (true){
            if(futureTask.isDone()){
                System.out.println(futureTask.get());
                break;
            }else {
                try { TimeUnit.MILLISECONDS.sleep(  500); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("正在处理中不要再催了，越催越慢");
            }
        }

        /**
         * 1.get容易导致阻塞，因为一但调用就会等到结果才会结束 。一般建议放在程序最后
         * 2.不愿等待很长时间，过时不候，可以自动离开。
         *
         * Future对于获取结果不是很友好，只能通过轮询和isDone来
         */
    }
}
