package com.itself.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author duJi
 */
@RestController
@RequestMapping("/redis")
public class TestRedisController {

    @Autowired
    private MessageQueueService service;

    @GetMapping("/add")
    public void testController(){
        for (int i = 0; i < 20; i++) {
            if (5 < i  && i <8 ){
                Map<String, Object> map = new HashMap<>();
                map.put("aaa",new AserviceImpl());
                service.enqueueMessage(JSON.toJSONString(map));
            }else if (10< i  && i <14 ){
                service.enqueueMessage("bbb");
            }else {
                service.enqueueMessage(String.valueOf(i));
            }
        }
    }
    @GetMapping("/pop")
    public void pop() throws InstantiationException, IllegalAccessException {
        String message = service.dequeueMessage();
        if (message != null) {
            // Process the message
            System.out.println("Received message: " + message);
        }
    }
}
