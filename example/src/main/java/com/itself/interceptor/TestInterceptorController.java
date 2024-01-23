package com.itself.interceptor;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 具体拦截器已经挪移到common模块中
 * @Author duJi
 * @Date 2023/12/06
 */
@RestController
@RequestMapping("interceptor")
@Api(tags = "拦截器测试接口")
public class TestInterceptorController {

    @GetMapping("/test")
    public String testInterceptor(){
        return "success";
    }
}
