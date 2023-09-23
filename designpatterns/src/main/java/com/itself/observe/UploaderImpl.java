package com.itself.observe;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体up主实现类，实现up主接口，并维护粉丝列表
 */
public class UploaderImpl implements Uploader {
    private List<Observer> fans = new ArrayList<>();//粉丝列表
    private String message;//消息内容

    public String getMessage() {
        return message;
    }

    /**
     * 发送通知给粉丝
     * @param message
     */
    public void sendMessage(String message) {
        this.message = message;
        notifyObservers();
    }

    @Override
    public void attach(Observer fan) {
        fans.add(fan);
    }

    @Override
    public void detach(Observer fan) {
        fans.remove(fan);
    }

    @Override
    public void notifyObservers() {
        for (Observer fan : fans) {
            fan.receive(this);
        }
    }
}