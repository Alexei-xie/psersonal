package com.itself.thread.pool;

import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.*;

/**
 *  固定线程数量的线程池
 * @Author xxw
 * @Date 2022/09/21
 */
public class FixedThreadPool {
    public static void main(String[] args) {

    }
}

/**
 * 固定线程数量的线程池   newFixedThreadPool
 */
class FixedThreadPool1{
    static final ExecutorService threadPool = Executors.newFixedThreadPool(2);  // 创建只有2个线程的固定线程池
    public static void main(String[] args) {
        // 添加任务1
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());   // 返回当前正在执行的线程名称
            }
        });
        // 添加任务2   lambda表达式写法
        threadPool.submit(() -> System.out.println(Thread.currentThread().getName()));
    }
}
/**
 * 有返回值
 */
class FixedThreadPool2 {
    static final ExecutorService threadPool = Executors.newFixedThreadPool(2);
    public static void main(String[] args) throws Exception {
        // 添加任务1
        Future<Integer> submit = threadPool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int random = new Random().nextInt(); // 随机数
                System.out.println(random);
                return random;
            }
        });
        System.out.println("-----" + submit.get()); // submit.get()：获取返回值结果
        // 添加任务2的 lambda写法
        Future<String> result = threadPool.submit(() -> {
            String str = "测试任务2";
            System.out.println(str);
            return str;
        });
        System.out.println("-----" + result.get());
    }
}
/**
 *  自定义线程池中的线程名称和优先级
 */
class FixedThreadPool3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建线程工厂
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(@NotNull Runnable r) {
                /*此处一定要把任务Runnable设置给新创建爱的线程*/
                Thread thread = new Thread(r);
                thread.setName("我的线程" + r.hashCode()); // 设置线程的命名规则
                thread.setPriority(Thread.MAX_PRIORITY); // 设置线程的优先级
                return thread;
            }
        };
        ExecutorService threadPool = Executors.newFixedThreadPool(2, threadFactory); // 创建一个线程池 指定线程数量和线程工厂
        Future<Integer> result = threadPool.submit(() -> {
            int random = new Random().nextInt();
            System.out.println("线程优先级：" + Thread.currentThread().getPriority() + ",随机数:" + random);
            System.out.println("线程名称：" + Thread.currentThread().getName());
            return random;
        });
        System.out.println("返回结果：" + result.get());
    }
}
