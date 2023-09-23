package com.itself.filter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Filter的使用：当有多个自定义的注解的时候，filter的 “初始化” 顺序是按照类名进行加载并不是按照@Order注解来执行，
 * 所以多个自定义Filter名称最好是相同名字+数字来进行区别，保证Filter的加载顺序，程序启动时日志输出：my filter initialization
 *                                                                                  other filter  initialization
 * 如果一个是类名是MyFilter一个是OtherFilter的话，程序启动时日志输出：  other filter  initialization
 *                                                             my filter initialization
 * @Author xxw
 * @Date 2023/01/04
 */
@RestController
@RequestMapping("/filter")
@Api(tags = "filter过滤器demo")
public class TestFilterController {


    @GetMapping("/test")
    @ApiOperation(value = "filter-test")
    public String testFilter(){
        System.out.println("filter执行成功");
        return "filter";
    }

}
