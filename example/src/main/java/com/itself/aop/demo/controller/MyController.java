package com.itself.aop.demo.controller;

import com.itself.aop.demo.annotation.MyAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/myAnnotation")
@Api(tags = "aop-demo")
public class MyController {

    @MyAnnotation
    @GetMapping("/{param1}/{param2}")
    @ApiOperation("aop-test方法")
    public void myMethod(@PathVariable String param1,@PathVariable int param2){
        System.out.println(111111);
    }
}