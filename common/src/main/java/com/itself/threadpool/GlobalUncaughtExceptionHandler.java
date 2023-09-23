package com.itself.threadpool;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程池异常日志处理handler
 * @author duJi
 */
@Slf4j
public class GlobalUncaughtExceptionHandler  implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("Exception in thread {} ", t.getName(), e);
    }

}