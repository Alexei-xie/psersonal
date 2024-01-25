package com.itself.thread.queue;

import java.util.concurrent.ThreadFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author duJi
 */
public class CustomThreadFactory implements ThreadFactory{

    private final String threadNamePrefix;

    public CustomThreadFactory(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    @Override
    public Thread newThread( Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(threadNamePrefix + thread.getId());
        //异常日志打印
        thread.setUncaughtExceptionHandler(new GlobalUncaughtExceptionHandler());
        return thread;
    }
    @Slf4j
    static class GlobalUncaughtExceptionHandler  implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            log.error("Exception in thread {} ", t.getName(), e);
        }

    }
}
