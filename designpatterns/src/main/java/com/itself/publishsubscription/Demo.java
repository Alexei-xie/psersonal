package com.itself.publishsubscription;

/**
 * 发布/订阅模式
 */
public class Demo {
    public static void main(String[] args) {
        // 创建一个中介者对象
        Mediator mediator = new Mediator();

        // 创建两个订阅者
        Subscriber subscriber1 = new MessageSubscriber("Subscriber1");
        Subscriber subscriber2 = new MessageSubscriber("Subscriber2");

        // 订阅者订阅消息
        mediator.subscribe("message", subscriber1);
        mediator.subscribe("message", subscriber2);

        // 创建一个发布者
        Publisher publisher = new Publisher(mediator);

        // 发布者发布消息
        publisher.publishMessage("Hello, world!");

        // 订阅者取消订阅
        mediator.unsubscribe("message", subscriber1);

        // 发布者再次发布消息
        publisher.publishMessage("Hello again!");
    }
}