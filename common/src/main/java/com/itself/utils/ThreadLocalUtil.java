package com.itself.utils;


import com.itself.domain.User;

/**
 * ThreadUtil工具类，用于拦截器存放用户信息等
 */
public class ThreadLocalUtil {

    private final static ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 存入线程中
     */
    public static void setUser(User user){
        USER_THREAD_LOCAL.set(user);
    }

    /**
     * 从线程中获取
     */
    public static User getUser(){
        return USER_THREAD_LOCAL.get();
    }

    /**
     * 清楚缓存
     */
    public static void clear(){
        USER_THREAD_LOCAL.remove();
    }

}
