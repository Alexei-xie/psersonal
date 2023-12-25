package com.itself.thread.queue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itself.domain.User;
import com.itself.redis.queue.RedisRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ExportManager implements Runnable {
    private ThreadPoolExecutor executor;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    private  CountDownLatch countDownLatch;

    public ExportManager() {
        executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), new CustomThreadFactory("export-thread-"),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public void submitExportRequest(ExportRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", request.getUser());
        map.put("service", request.getService().getClass().getName());
        map.put("map", request.getQueryWrapper());
        System.out.println("service-----" + request.getService().getClass().getName());
        String jsonString = JSON.toJSONString(map);
        redisTemplate.opsForList().leftPush("Queue", jsonString);
        executor.execute(this);
    }

    @Override
    public void run() {
        //取数据
        Object pop = redisTemplate.opsForList().rightPop("Queue");
        countDownLatch.countDown();
        try {
            execBizService(pop);
        } catch (Exception e) {
            log.error("execBizService error");
        }
    }


    /**
     * 模拟执行业务代码
     * @param obj 取出数据
     */
    private void execBizService(Object obj){
        if (Objects.isNull(obj)){
            return;
        }
        //模拟业务异常
        log.info("countDownLatch value:{}",countDownLatch.getCount());
        if (countDownLatch.getCount() == 1) {
            int i = 1/0;
        }

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = JSON.parseObject(obj.toString());
        try {
            //根据全类路径名称反射获取实例调用路由方法走对应的实现类
            RedisRouteService bean = (RedisRouteService) applicationContext.getBean(Class.forName(jsonObject.get("service").toString()));
            bean.route();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("jsonobject-------" + jsonObject);
        User user = JSONObject.parseObject(jsonObject.get("user").toString(), User.class);
        Map<String, Object> map = JSONObject.parseObject(jsonObject.get("map").toString(), Map.class);
        System.out.println("-----" + Thread.currentThread().getName());

        System.out.println("user--------" + user);
        System.out.println("map---------" + map);

        System.out.println("pop---------" + obj);
    }

    /**
     * 每次请求初始化一下countDownLatch()
     */
    public void initCountDownLatch() {
        countDownLatch = new CountDownLatch(5);
    }
}