package com.itself.strategy.demo;

/** 抽象策略
 * @Author xxw
 * @Date 2022/06/08
 */
public interface Person {
    /**
     * 执行
     */
    void action();
}

/**
 * 具体策略实现1
 */
class LiSi implements Person{
    @Override
    public void action() {
        System.out.println("我是李四");
    }
}
/**
 * 具体策略实现2
 */
class ZhangSan implements Person{
    @Override
    public void action() {
        System.out.println("我是张三");
    }
}

