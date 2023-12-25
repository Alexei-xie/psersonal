package com.itself.thread.queue;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itself.domain.User;
import com.itself.redis.queue.RedisRouteService;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExportRequest {
    private String requestId;
    // 其他导出请求相关属性和方法

    private QueryWrapper<ExportRequest> queryWrapper = new QueryWrapper<ExportRequest>();

    private RedisRouteService service;

    private User user;

    // 构造函数、getter和setter方法等
}