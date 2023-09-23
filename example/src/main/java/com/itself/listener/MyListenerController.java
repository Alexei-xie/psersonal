package com.itself.listener;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * 使用 ApiFox工具进行请求
 * @Author xxw
 * @Date 2023/01/11
 */
@RestController
@RequestMapping("listener")
@Api(tags = "listener监听器demo")
public class MyListenerController {

    @GetMapping("test")
    @ApiOperation("listener-test")
    public String test(HttpServletRequest request){
        //获取ServletContext
        ServletContext servletContext = request.getServletContext();
        //获取监听器赋的值
        System.out.println("获取在监听器初始化赋的value-->"+servletContext.getAttribute("age"));
        //赋值新的属性
        servletContext.setAttribute("name","xxw");
        //替换属性
        servletContext.setAttribute("age",26);
        //删除属性
        servletContext.removeAttribute("name");
        return "Listener is OK";
    }
}
