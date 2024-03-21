package com.itself.thread.sync;

import com.itself.ExampleService;
import java.util.concurrent.Executor;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = ExampleService.class)
public class TestAsync {

    @Resource
    AsyncTest asyncTest;

    @Resource
    Executor executor;

    @Test
    public void test() throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        System.out.println(executor.getClass().getName());
        for (int i = 0; i < 10; i++) {
            asyncTest.sout();
        }
        Thread.sleep(20000L);
    }


}
