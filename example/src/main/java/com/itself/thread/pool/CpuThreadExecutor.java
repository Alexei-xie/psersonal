package com.itself.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  根据当前CPU生成线程池
 * @Author xxw
 * @Date 2022/09/21
 */
public class CpuThreadExecutor {
    public static void main(String[] args) {

    }
}

/**
 * 根据当前CPU生成线程池
 */
class CpuThreadExecutor1{
    /**
     * 静态的好处是为了防止程序启动中，线程池的不断创建导致内存溢出
     */
    static final ExecutorService service = Executors.newWorkStealingPool();
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            service.submit(()->{
                System.out.println("线程名称：" + Thread.currentThread().getName());
            });
            while (!service.isTerminated()){
            }
        }
    }
}
