package com.itself.proxy.cjlibproxy;

/**
 * @Author xxw
 * @Date 2023/04/21
 */
public class Demo {
    public static void main(String[] args) {
        //需要被代理的目标对象
        UserServiceImpl userService = new UserServiceImpl();
        //生成代理对象
        UserServiceImpl proxy = (UserServiceImpl)new UserProxy().getLogProxy(userService);
        //执行方法
        System.out.println(proxy.findUserList());
        System.out.println(proxy.getClass());
    }
}
