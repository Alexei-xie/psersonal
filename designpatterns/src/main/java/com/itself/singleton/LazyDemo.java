package com.itself.singleton;

/**
 * 单例模式
 * @Author xxw
 * @Date 2022/05/17
 */
public class LazyDemo {
    private  String value;
    private LazyDemo(){}
    private  String getInstance(){
        if (value == null){
            value = "new LazyDemo()";
        }
        return value;
    }
}
