package com.itself.thread.completableFuture;

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