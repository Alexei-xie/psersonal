package com.itself.strategy.demo;

/**
 * 策略模式
 * @Author xxw
 * @Date 2022/06/08
 */
public class DemoPrint {
    public static void main(String[] args) {
        //创建策略代理类
        Strategy strategy = new Strategy();

        Person zs = new ZhangSan();
        strategy.setPerson(zs);
        strategy.execute();

        Person ls = new LiSi();
        strategy.setPerson(ls);
        strategy.execute();
    }
}
