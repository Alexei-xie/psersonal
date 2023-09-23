package com.itself.singleton;

/**懒汉式
 * @Author xxw
 * @Date 2022/06/09
 */
public class SingletonDemo2 {

    private static  SingletonDemo2 instance;

    private SingletonDemo2(){}

    private static synchronized SingletonDemo2 getInstance(){
        if (instance == null){
            instance = new SingletonDemo2();
        }
        return instance;
    }
}
