package com.itself.threadpool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Description: 线程池配置

 */
@Configuration
@EnableAsync
public class ThreadPoolConfig implements AsyncConfigurer {
    /**
     * 项目共用线程池
     */
    public static final String EXECUTOR = "executor";
    /**
     * 处理异步方法调用时要使用的Executor实例
     */
    @Override
    public Executor getAsyncExecutor() {
        return Executor();
    }


    /**
     * Spring框架提供的一个实现了Executor和AsyncTaskExecutor接口的线程池实现类,用于执行异步任务
     *      1.可配置性： ThreadPoolTaskExecutor提供了丰富的配置选项，可以通过设置属性来调整线程池的行为，如核心线程数、最大线程数、队列容量、线程空闲时间等。
     *      2.支持异步任务： ThreadPoolTaskExecutor实现了AsyncTaskExecutor接口，可以用于执行异步任务。它可以接受Runnable或Callable类型的任务，并在后台线程池中执行。
     *      3.线程池管理： ThreadPoolTaskExecutor提供了管理线程池的方法，如启动、停止、销毁等。它也支持在Spring应用上下文关闭时自动销毁线程池。
     *      4.任务拒绝策略： 当线程池已满且无法接受新任务时，ThreadPoolTaskExecutor提供了可配置的任务拒绝策略，如抛出异常、丢弃任务等。
     */
    @Bean(EXECUTOR)
    public ThreadPoolTaskExecutor Executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("thread-executor-");//设置线程名称
        // CallerRunsPolicy: 不在新线程中执行任务，而是由调用者所在的线程来执行；
        // AbortPolicy: 拒绝执行新任务，并抛出RejectedExecutionException异常；
        // DiscardPolicy：丢弃当前将要加入队列的任务；
        // DiscardOldestPolicy：丢弃任务队列中最旧的任务；
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadFactory(new MyThreadFactory(executor));
        executor.setWaitForTasksToCompleteOnShutdown(true);//开启优雅停机
        executor.initialize();
        return executor;
    }

    /**
     * 使用demo
     */
    @Async(EXECUTOR)
    public void execTask(){

    }

}
