package com.itself.thread.completableFuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


/**
 *  CompletableFuture使用
 *   简书地址： https://www.jianshu.com/p/bf77dc48d4d2
 *   csdn地址：https://blog.csdn.net/sermonlizhi/article/details/123356877
 *
 * @Author xw
 * @Date 2022/7/6
 */
public class CompletableFutureDemo {
    public static void main(String[] args) {

    }
}

/**
 *  1-创建CompletableFuture对象
 *  方法名  功能描述
 *  1.completedFuture(U value)    返回一个已经计算好的CompletableFuture
 *  2.runAsync(Runnable runnable) 使用ForkJoinPool.commonPool()作为线程池执行任务，没有返回值
 *  3.runAsync(Runnable runnable, Executor executor)  使用指定的线程池执行任务，没有返回值
 *  4.supplyAsync(Supplier<U> supplier)   使用ForkJoinPool.commonPool()作为线程池执行任务，有返回值
 *  5.supplyAsync(Supplier<U> supplier, Executor executor)    使用指定的线程池执行任务，有返回值
 */
class CompletableFutureDemo01{
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1.completedFuture(U value)    返回一个已经计算好的CompletableFuture：参数 Integer value
        CompletableFuture<Integer> intFuture = CompletableFuture.completedFuture(100);
        //输出100
        System.out.println("1-1 "+intFuture.get());

        //2.runAsync(Runnable runnable) 使用ForkJoinPool.commonPool()作为线程池执行任务，没有返回值
        //输出hello
        CompletableFuture<Void> voidFuture = CompletableFuture.runAsync(() -> System.out.println("2-1 hello"));
        //输出null
        System.out.println("2-2 "+voidFuture.get());

        //4.supplyAsync(Supplier<U> supplier)   使用ForkJoinPool.commonPool()作为线程池执行任务，有返回值
        CompletableFuture<String> stringFuture = CompletableFuture.supplyAsync(()->"hello");
        //输出hello
        System.out.println("4-1 "+stringFuture.get());
    }
}
/**
 *  2-计算结果完成时.可以执行的方法
 *  whenComplete(BiConsumer<? super T,? super Throwable> action)
 *  whenCompleteAsync(BiConsumer<? super T,? super Throwable> action)
 *  whenCompleteAsync(BiConsumer<? super T,? super Throwable> action, Executor executor)
 */
class CompletableFutureDemo2 {
    public static void main(String[] args) throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return "hello";
        }).whenComplete((v,e) -> {
            // hello
            System.out.println(v);
        });
        // hello
        System.out.println(future.get());
    }
}
/**
 *  3-转换，消费，执行
 *  方法名      功能描述
 *  thenApply    获取上一个任务的返回，并返回当前任务的值
 *  thenAccept   获取上一个任务的返回，单纯消费，没有返回值
 *  thenRun      上一个任务执行完成后，开始执行thenRun中的任务
 */
class CompletableFutureDemo3 {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> {
            return "hello ";
        }).thenAccept(str -> {
            // hello world
            System.out.println(str + "world");
        }).thenRun(() -> {
            // task finish
            System.out.println("task finish");
        });
    }
}

/**
 * 等待所有方法结束后一并返回
 */
class CompletableFutureDemo4{

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Random rand = new Random();
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000 + rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("future1 done...");
            }
            return "abc";
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000 + rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("future2 done...");
            }
            return 111;
        });
        CompletableFuture<Void> future = CompletableFuture.allOf(future1, future2);
        future.join(); //会等待所方法执行完成  使用future.get();也可以
        System.out.println(future1.get()+future2.get());
        System.out.println("end done");
    }
}
/**
 * 等待所有方法结束后一并返回（包含异常处理）
 */
class CompletableFutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 111)
                .exceptionally(ex -> {
                    // 处理异常情况
                    System.err.println("Exception occurred in future1: " + ex.getMessage());
                    return 0; // 返回默认值或处理异常的结果
                });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 1/0)
                .exceptionally(ex -> {
                    // 处理异常情况
                    System.err.println("Exception occurred in future2: " + ex.getMessage());
                    return 0; // 返回默认值或处理异常的结果
                });

        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> 333)
                .exceptionally(ex -> {
                    // 处理异常情况
                    System.err.println("Exception occurred in future3: " + ex.getMessage());
                    return 0; // 返回默认值或处理异常的结果
                });

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(future1, future2, future3);

        allFutures.join(); // 等待所有的CompletableFuture完成
        int result = future1.join() + future2.join() + future3.join();
        System.out.println("Sum: " + result);
    }
}