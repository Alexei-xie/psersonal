package com.itself.thread.thread;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author xxw
 * @Date 2022/11/30
 */
public class ThreadDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new SingleThread().start();  // thread方式启动

        SingleRunnable runnable = new SingleRunnable();
        Thread thread2 = new Thread(runnable);
        thread2.start();// runnable方式启动

        SingleCallable callable = new SingleCallable();
        FutureTask<String> task = new FutureTask<>(callable);// 该返回值是线程返回值，通过 xx.get()方法获取
        new Thread(task).start();
        System.out.println(task.get());
    }
}

/**
 * 方式一：继承Thread类
 */
class SingleThread extends Thread {
    @Override
    public void run() {
        List.of("11", "22", "33").forEach(System.out::println);
        super.run();
    }
}

/**
 * 实现Runnable接口
 */
class SingleRunnable implements Runnable {
    @Override
    public void run() {
        List.of("aa", "bb", "cc").forEach(System.out::println);
    }

    public static void main(String[] args) {
        // lambda写法
        Runnable task = () -> System.out.println("hello");
        Thread thread = new Thread(task);
        thread.start();
    }
}

/**
 * 实现Callable接口
 */
class SingleCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("callable");
        return "ok";
    }

    /**
     * 另外两种写法
     */
    public static void main(String[] args) {
        // 匿名内部类写法
        FutureTask<String> task1 = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "hello";
            }
        });
        // lambda写法
        FutureTask<String> task = new FutureTask<>(() -> "hello");
        Thread thread = new Thread(task);
        Thread thread1 = new Thread(task1);
        thread.start();
        thread1.start();
    }
}
