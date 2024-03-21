package com.itself.thread.sync;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@EnableAsync
@Component
public class AsyncTest {

    @Async
    public void sout() throws InterruptedException {
        Thread.sleep(10000L);
        System.out.println(Thread.currentThread().getName());
    }

}
