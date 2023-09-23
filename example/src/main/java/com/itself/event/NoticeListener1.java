package com.itself.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 监听器的注册和注销可能造成内存泄漏：如果不及时将不需要的监听器注销掉，可能会导致内存泄漏问题
 * @Author xxw
 * @Date 2023/06/06
 */
@Component
@Slf4j
public class NoticeListener1 implements ApplicationListener<NoticeEvent> {
    @Override
    public void onApplicationEvent(NoticeEvent event) {
      log.info("listener receive the event ! sleep two second... ");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("event's message is:{}",event.getMessage());
    }
}
