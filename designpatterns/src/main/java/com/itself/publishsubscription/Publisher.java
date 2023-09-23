package com.itself.publishsubscription;

/**
 * 定义一个发布者类
 */
class Publisher {
    private final Mediator mediator;

    public Publisher(Mediator mediator) {
        this.mediator = mediator;
    }

    // 发布消息
    public void publishMessage(String message) {
        mediator.publish("message", message);
    }
}