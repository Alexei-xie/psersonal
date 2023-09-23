package com.itself.lock.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author xxw
 * @Date 2023/07/13
 */

public class RedissonDemo {


}


/**
 * http://localhost:1212/close/call
 * 闭锁应用demo： 只能进行减法，减到0结束
 */
@Slf4j
@RestController
@RequestMapping("/close")
class CloseLockDemo{
    @Resource
    private RedissonClient redissonClient;//使用redisson的时候需要注册redis连接地址，即configuration配置

    @GetMapping("/call")
    public String callDragon(HttpServletRequest request) throws InterruptedException {
        log.info("请求地址：{}",request.getRemoteAddr());
        RCountDownLatch count = redissonClient.getCountDownLatch("longzhu");
        count.trySetCount(7L);//设置等待计数次数减为0，必须通过相同名称的countDownLatch实例的countDown()方法
        count.await();
        return "召唤神龙";
    }

    /**
     *  http://localhost:1212/redisson/collect  请求七次为收集七颗龙珠
     */
    @GetMapping("/collect")
    public String collectBall(){
        RCountDownLatch count = redissonClient.getCountDownLatch("longzhu");
        count.countDown();
        return count.getCount()+"颗到手";
    }
}

/**
 * 信号量应用demo：能增能减
 */
@Slf4j
@RestController
@RequestMapping("/semaphore")
class SemaphoreLockDemo{
    @Resource
    private RedissonClient redissonClient;

    /**
     * 初始化5停车场
     */
    @GetMapping("/init")
    public String semaphore(){
        RSemaphore semaphore = redissonClient.getSemaphore("park");
        semaphore.trySetPermits(5);//设置5个信号量
        return "init complete";
    }

    /**
     * 停进来一辆车
     */
    @GetMapping("/park")
    public String park() throws InterruptedException {
        RSemaphore semaphore = redissonClient.getSemaphore("park");
        semaphore.acquire(1);//要一个信号量
        return "park complete";
    }

    /**
     * 开走了一辆车
     */
    @GetMapping("/move")
    public String move() throws InterruptedException {
        RSemaphore semaphore = redissonClient.getSemaphore("park");
        semaphore.release(1);//释放一个信号量
        return "move complete";
    }

}
