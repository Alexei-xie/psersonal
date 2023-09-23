package com.itself.proxy.cjlibproxy;

import java.util.Collections;
import java.util.List;

/**
 * 需要被代理的类
 */
public class UserServiceImpl {
    // 查询功能
    List<String> findUserList() {
        return Collections.singletonList("小A");
    }
}