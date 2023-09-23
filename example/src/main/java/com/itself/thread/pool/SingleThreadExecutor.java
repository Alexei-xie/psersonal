package com.itself.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  单线程的线程池
 *      意义：1.复用线程
 *           2.单线程的线程池提供了任务队列和拒绝策略(任务队列满了(Integer.MAX_VALUE)之后就会拒绝后面新来的线程任务)
 * @Author xxw
 * @Date 2022/09/21
 */
public class SingleThreadExecutor {
    public static void main(String[] args) {

    }
}

/**
 * 单线程的线程池
 */
class SingleThreadExecutor1{
    static final ExecutorService executor = Executors.newSingleThreadExecutor();
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            executor.submit(()-> System.out.println("线程名称：" + Thread.currentThread().getName()));
        }
    }
}
