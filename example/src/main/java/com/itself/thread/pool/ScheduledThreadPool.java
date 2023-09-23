package com.itself.thread.pool;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *  定时任务类型的线程池
 * @Author xxw
 * @Date 2022/09/21
 */
public class ScheduledThreadPool {
    public static void main(String[] args) {

    }
}

/**
 * 定时任务类型的线程池
 * newScheduledThreadPool
 */
class ScheduledThreadPool1{
    static final ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(5); //创建一个线程数量为5的定时线程池
    public static void main(String[] args) {
        System.out.println("添加任务的时间："+ LocalDateTime.now());
        /*执行定时任务，延时3s执行一次*/
        threadPool.schedule(() -> System.out.println("执行子任务时间：" + LocalDateTime.now()),3, TimeUnit.SECONDS);
    }
}
/**
 * 创建单线程的定时任务线程池
 * newSingleThreadScheduledExecutor
 */
class SingletonScheduledThreadPool5{
    static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(); // 创建单线程的定时任务线程池
    public static void main(String[] args) {
        System.out.println("添加任务的时间："+ LocalDateTime.now());
        executor.schedule(()->{
            System.out.println("执行子任务时间：" + LocalDateTime.now());
        },2,TimeUnit.SECONDS);
    }
}
/**
 * 周期性的执行线程任务 scheduleAtFixedRate()方法
 */
class ScheduledThreadPool2{
    static final ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(5);
    public static void main(String[] args) {
        System.out.println("添加任务的时间："+ LocalDateTime.now());
        /*周期性执行定时任务，3s后开始执行，4s执行一次，此时每4s是从上个任务的执行开始时间计算*/
        threadPool.scheduleAtFixedRate(()-> System.out.println("执行子任务时间" + LocalDateTime.now()),3,4,TimeUnit.SECONDS);
    }
}
/**
 * 周期性执行线程任务
 * 线程睡眠时间 > 定时执行时间大小   ==》此时按照睡眠时间来进行周期性执行线程任务
 */
class ScheduledThreadPool3{
    static final ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(5);
    public static void main(String[] args) {
        System.out.println("添加任务的时间："+ LocalDateTime.now());
        threadPool.scheduleAtFixedRate(()->{
            System.out.println("执行子任务时间" + LocalDateTime.now());
            try {
                Thread.sleep(5 * 1000); // 睡眠5s
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },2,3,TimeUnit.SECONDS);
    }
}
/**
 * 周期性执行线程任务 scheduleWithFixedDelay()方法
 */
class ScheduledThreadPool4{
    public static void main(String[] args) {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(5);
        System.out.println("添加任务的时间："+ LocalDateTime.now());
        threadPool.scheduleWithFixedDelay(() -> {
            System.out.println("执行子任务时间：" + LocalDateTime.now());
            try {
                Thread.sleep(1000);
                System.out.println("执行子任务结束：" + LocalDateTime.now());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*任务开始2s后开始执行，每4s执行一次，此时每4s是从上个任务的执行结束时间开始计算*/
        }, 2, 4, TimeUnit.SECONDS);
    }
}

