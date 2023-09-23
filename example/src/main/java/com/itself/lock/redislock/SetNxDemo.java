package com.itself.lock.redislock;

import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author xxw
 * @Date 2023/08/21
 */
public class SetNxDemo {

}

/**
 * 利用setNx命令实现分布式锁
 */
class RedisSetNx{
    @Resource
    private StringRedisTemplate redisTemplate;
    public String redisLock() {
        String key = "pid";// 商品id作为key
        String value = "user-id";// 用户id作为value(这里是为了后续释放锁的时候辨别是谁加的锁)
        boolean result = tryLock(key, value, 10);// 根据业务耗时设置默认过期时间
        if (!result) {
            // 加锁失败
            return "友好提示！";
        }
        try {
            int stock = Integer.parseInt(getValue("stock:pid"));// 获取该商品库存
            if (stock > 0) {
                stock = stock - 1;
                redisTemplate.opsForValue().set("stock:pid", Integer.toString(stock));
                System.out.println("扣减库存成功，剩余库存：" + stock);
            } else {
                System.out.println("扣减失败，库存不足！");
            }
        } finally {
            //判断当前该锁对应的userId是不是最开始设置的userId，是，则可以删除锁
            //此时可以解决小部分的低并发的分布式超卖问题，但是由于下面两个原子操作组合在一起的时候并不是原子操作，所以高并发下还是不行
            if (value.equals(getValue("pid"))) {
                redisTemplate.delete(key);// 删除锁
            }
        }
        return "finish";
    }
    /**
     * setNx命令加锁
     * @param key key
     * @param value  值
     * @param second 秒
     * @return true or false
     */
    private boolean tryLock(String key, String value, long second) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value, second, TimeUnit.SECONDS);
        return null != result && result;
    }
    private String getValue(String key){
        return redisTemplate.opsForValue().get(key);
    }
}
