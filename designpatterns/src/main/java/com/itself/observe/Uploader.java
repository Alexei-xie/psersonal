package com.itself.observe;

/**
 * up主接口
 */
public interface Uploader {
    void attach(Observer observer);  // 粉丝关注
    void detach(Observer observer);  // 粉丝取关
    void notifyObservers();          // 发送通知
}