package com.itself.event;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <a href="http://localhost:1212/event/test/hello">测试请求地址</a>
 * @Author xxw
 * @Date 2023/06/06
 */
@RestController
@Slf4j
@RequestMapping("/event")
@Api(tags = "spring-event事件demo")
public class EventController {

    private ApplicationEventPublisher publisher;

    @Autowired
    public void setPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping("/test/{message}")
    @ApiOperation(value = "spring-event-test")
    public void eventTest(@PathVariable("message")String message){
        log.info("start————————>");
        publisher.publishEvent(new NoticeEvent(message));
        log.info("end——————————>");
    }

}
