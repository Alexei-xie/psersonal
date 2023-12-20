package com.itself.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class MessageQueueService {


    private RedisTemplate<String,Object> template;

    public MessageQueueService(RedisTemplate<String, Object> template) {
        this.template = template;
    }

    public void enqueueMessage(String message) {
        String messageId = UUID.randomUUID().toString();
        String messageKey = "message:" + messageId;
        Boolean added = template.opsForValue().setIfAbsent(messageKey, message);
        if (added != null && added) {
            template.opsForList().leftPush("myQueue", messageId);
        }
    }

    public String dequeueMessage() {
        Object messageId = template.opsForList().rightPop("myQueue").toString();
        if (messageId != null) {
            String messageKey = "message:" + messageId;
            Object string = template.opsForValue().get(messageKey);
            System.out.println(string);
            template.delete(messageKey);
            return string.toString();
        }
        return null;
    }
}