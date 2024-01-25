package com.itself.satoken;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试登录成功和状态接口
 */
@RestController
@RequestMapping("/sa-token/")
@Api(tags = "sa-token")
public class SaTokenController {

    // 测试登录，浏览器访问： http://localhost:8082/sa-token/doLogin?username=zhang&password=123456
    @ApiOperation(value = "登录",notes = "doLogin")
    @GetMapping("doLogin")
    public String doLogin(String username, String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对 
        if("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);//校验通过并生成token
            return "登录成功";
        }
        return "登录失败";
    }

    @ApiOperation(value = "判断是否登录",notes = "isLogin")
    @GetMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }
    @ApiOperation(value = "获取token信息",notes = "tokenInfo")
    @GetMapping("tokenInfo")
    public SaResult tokenInfo() {
        return SaResult.data(StpUtil.getTokenInfo());
    }
    @ApiOperation(value = "注销当前会话",notes = "logout")
    @GetMapping("logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }

    @ApiOperation(value = "当前会话踢下线",notes = "kickout")
    @GetMapping("kickout")
    public SaResult kickout() {
        StpUtil.logout();
        return SaResult.ok();
    }
}
