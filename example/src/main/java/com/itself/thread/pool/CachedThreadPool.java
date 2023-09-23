package com.itself.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  可缓存的线程池
 * @Author xxw
 * @Date 2022/09/21
 */
public class CachedThreadPool {
    public static void main(String[] args) {

    }
}

/**
 * 可缓存的线程池 newCachedThreadPool
 */
class CachedThreadPool1{
    /**
     * 静态的好处是为了防止程序启动中，线程池的不断创建导致内存溢出
     */
    static final ExecutorService threadPool = Executors.newCachedThreadPool(); // 创建一个静态可缓存的线程池

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int index = i;
            threadPool.submit(()-> System.out.println("i:" + index + " | 线程名称：" + Thread.currentThread().getName()));
        }
    }
}