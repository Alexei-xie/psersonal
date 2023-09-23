package com.itself.threadpool;

import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadFactory;

@AllArgsConstructor
public class MyThreadFactory implements ThreadFactory {

  private final ThreadFactory factory;

    @Override
    public Thread newThread(Runnable r) {
        Thread thread =factory.newThread(r);
        //设置异常日志打印
        thread.setUncaughtExceptionHandler(new GlobalUncaughtExceptionHandler());
        return thread;
    }
}