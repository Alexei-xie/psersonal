package com.itself.publishsubscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定义一个中介者类
 */
public class Mediator {
    // 定义一个Map，用于存储订阅者列表
    private final Map<String, List<Subscriber>> subscribers = new HashMap<>();

    // 添加订阅者
    public void subscribe(String event, Subscriber subscriber) {
        List<Subscriber> subscriberList = subscribers.get(event);
        if (subscriberList == null) {
            subscriberList = new ArrayList<>();
            //添加订阅
            subscriberList.add(subscriber);
            subscribers.put(event, subscriberList);
        }
    }

    // 取消订阅
    public void unsubscribe(String event, Subscriber subscriber) {
        List<Subscriber> subscriberList = subscribers.get(event);
        if (subscriberList != null) {
            subscriberList.remove(subscriber);
            if (subscriberList.isEmpty()) {
                subscribers.remove(event);
            }
        }
    }

    // 发布消息
    public void publish(String event, Object data) {
        List<Subscriber> subscriberList = subscribers.get(event);
        if (subscriberList != null) {
            for (Subscriber subscriber : subscriberList) {
                subscriber.receive(event, data);
            }
        }
    }
}