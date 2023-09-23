package com.itself.aop.controller;

import com.itself.aop.service.AopUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author xxw
 * @Date 2021/08/17
 */
@RestController
@RequestMapping("aop")
@Api(tags = "aop-log日志demo")
public class AopController {
    @Resource
    private AopUserService aopUserService;

    /**
     * http://localhost:1212/aop/xw/findUserNameByTel?tel=1234567
     */
    // @OperationLogDetail()
    @GetMapping("/{user}/findUserNameByTel")
    @ApiOperation("aop-log-test方法")
    public String findUserNameByTel(@PathVariable String user, @RequestParam("tel") String tel) {
        return aopUserService.findUserName(user,tel);
    }
}
