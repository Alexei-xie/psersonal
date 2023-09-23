package com.itself.annotation.redis;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xxw
 * @Date 2022/10/17
 */

@RestController
@Api(tags = "redis自定义简易频控：aop-demo测试")
public class TestController {

    @NoRepeatSubmit(lockTime = 10)
    @GetMapping("/test")
    @ApiOperation("test方法")
    public void test(){
        System.out.println("1111");
    }

}
