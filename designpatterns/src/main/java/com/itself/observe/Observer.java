package com.itself.observe;

/**
 * 粉丝接口
 */
public interface Observer {
    void receive(Uploader uploader);    // 接收到通知时进行更新
}