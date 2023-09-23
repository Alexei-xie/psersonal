package com.itself.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 * @Author xxw
 * @Date 2023/06/06
 */
@Slf4j
public class NoticeEvent extends ApplicationEvent {
    /**
     * 接收的消息
     */
    private String message;

    public NoticeEvent(String message) {
        super(message);
        this.message = message;
        log.info("send event success ! message:{}",message);
    }

    public String getMessage() {
        return message;
    }
}
