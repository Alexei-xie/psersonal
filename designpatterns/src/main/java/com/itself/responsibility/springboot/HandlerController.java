package com.itself.responsibility.springboot;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/** 责任链测试接口
 * @Author duJi
 * @Date 2023/12/06
 */
@Slf4j
@RestController
@Api(tags = "责任链测试接口")
@RequestMapping("/responsibility")
public class HandlerController {

    @Resource
    private Handler handler;

    @ApiOperation("测试接口")
    @GetMapping("/test")
    public void handleRequest(){
        HandlerRequest request = new HandlerRequest(1);
        handler.handleRequest(request);
        log.info("success , count : "+request.getCount());
    }
}
