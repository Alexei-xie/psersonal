package com.itself.observe;

/**
 * 具体观察者类，实现观察者接口
 */
public class ObserverImpl implements Observer {

    @Override
    public void receive(Uploader subject) {
        // 获取up更新的信息
        String message = ((UploaderImpl) subject).getMessage();
        System.out.println("粉丝收到通知：" + message);
    }
}