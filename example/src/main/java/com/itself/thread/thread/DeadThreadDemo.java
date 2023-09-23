package com.itself.thread.thread;

import com.itself.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 经典死锁demo
 *
 * @Author xxw
 * @Date 2023/08/13
 */
public class DeadThreadDemo {

    public static void main(String[] args) {

        Logger log = LoggerFactory.getLogger(DeadThreadDemo.class);


        Object obj = new Object();
        Object object = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (obj) {
                log.debug("lock A");
                TimeUtils.sleep(1);
                synchronized (object) {
                    log.debug("lock B");
                }
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            synchronized (object) {
                log.debug("lock B");
                TimeUtils.sleep(1);
                synchronized (obj) {
                    log.debug("lock A");
                }
            }

        }, "t2");

        t1.start();
        t2.start();

    }
}
