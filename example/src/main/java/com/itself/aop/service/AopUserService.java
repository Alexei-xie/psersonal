package com.itself.aop.service;

/**
 * @Author xxw
 * @Date 2021/08/17
 */
public interface AopUserService {
    /**
     * 获取用户信息
     * @param tel
     * @return
     */
    String findUserName(String user,String tel);
}
