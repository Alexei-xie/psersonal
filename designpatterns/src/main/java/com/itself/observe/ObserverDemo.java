package com.itself.observe;

/**
 * 观察者模式
 */
public class ObserverDemo {
    public static void main(String[] args) {
        UploaderImpl uploader = new UploaderImpl();
        ObserverImpl observer1 = new ObserverImpl();
        ObserverImpl observer2 = new ObserverImpl();
        uploader.attach(observer1);  // 新增粉丝
        uploader.attach(observer2);  // 新增粉丝

        uploader.sendMessage("发布新视频");  // up主发布新视频并通知给所有粉丝
        uploader.detach(observer2);  // 粉丝取关
        uploader.sendMessage("发布观察者模式视频");  // up主发布新视频并通知给所有粉丝
    }
}