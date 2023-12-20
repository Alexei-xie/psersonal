package com.itself.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageConsumer {

    @Autowired
    private MessageQueueService messageQueueService;


    private  Map<String,RedisRouteService> hashMap  = new HashMap<>();

    public MessageConsumer(MessageQueueService messageQueueService,Map<String,RedisRouteService> map ) {
        this.messageQueueService = messageQueueService;
        this.hashMap.putAll(map);
    }

    //@Scheduled(cron = "*/10 * * * * *") // adjust the delay as needed
    public void consumeMessage() {
        String message = messageQueueService.dequeueMessage();
        if (message != null) {
            // Process the message
            System.out.println("Received message: " + message);
            if (hashMap.get(message) != null) {
                hashMap.get(message).route();
            }
        }
    }
}