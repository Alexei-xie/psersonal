package com.itself.singleton;

/**
 * 饿汉式
 * @Author xxw
 * @Date 2022/06/09
 */
public class SingletonDemo1 {

    private static SingletonDemo1 instance = new SingletonDemo1();

    /**
     * 构造函数私有化
     */
    private SingletonDemo1() {}

    public static SingletonDemo1 getInstance() {
        return instance;
    }
}
