package com.itself.thread.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class ExportManager implements Runnable{
    private ThreadPoolExecutor executor;

    @Autowired
    private StringRedisTemplate redisTemplate;
    public ExportManager() {
        executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

    public void submitExportRequest(ExportRequest request) {
        redisTemplate.opsForList().leftPush("Queue",request.getRequestId());
        executor.submit(this);
    }


    @Override
    public void run() {
        while (true){
            //阻塞式获取数据
            String pop = redisTemplate.opsForList().rightPop("Queue",0,TimeUnit.SECONDS);
            System.out.println("-----");
            try {
                TimeUnit.SECONDS.sleep(6);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(pop);
        }
    }
}