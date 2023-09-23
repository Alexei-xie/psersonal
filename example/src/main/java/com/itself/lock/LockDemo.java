package com.itself.lock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  Lock锁使用
 * @Author xxw
 * @Date 2022/09/23
 */
public class LockDemo {
    public static void main(String[] args) {

    }
}


/**
 * 锁使用
 */
class LockDemo01 {

    private static final Map<Object, Lock> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        Lock lock = getSerialLock("123");
        boolean result = false;
        try {
            result = lock.tryLock(3, TimeUnit.SECONDS); // 加锁
            if (result) {
                // 业务
            }
        } catch (InterruptedException e) {
            // 异常处理
        } finally {
            if (result) {
                lock.unlock();
            }
        }
    }
    /**
     * 获取锁
     */
    private static Lock getSerialLock(Object o) {
        if (!map.containsKey(o)) {
            map.put(o, new ReentrantLock(true));
        }
        return map.get(o);
    }
}


