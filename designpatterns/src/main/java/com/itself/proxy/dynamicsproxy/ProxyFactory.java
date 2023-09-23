package com.itself.proxy.dynamicsproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理
 */
public class ProxyFactory {
    // 目标方法
    public Object target;
    public ProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(
                // 目标对象的类加载器
                target.getClass().getClassLoader(),
                // 目标对象的接口类型
                target.getClass().getInterfaces(),
                // 事件处理器
                new InvocationHandler() {
                    /**
                     * @param proxy  代理对象
                     * @param method 代理对象调用的方法
                     * @param args   代理对象调用方法时实际的参数
                     * @return null
                     * @throws Throwable
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("我是前置增强");
                        Object result = method.invoke(target, args);
                        System.out.println("我是后置增强");
                        return result;
                    }
                }
        );
    }
}