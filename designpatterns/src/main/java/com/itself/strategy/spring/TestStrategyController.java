package com.itself.strategy.spring;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(tags = "策略模式接口")
@RestController
@RequestMapping("/strategy")
public class TestStrategyController {
    @Autowired
    private CalculationService calculationService;

    @ApiOperation(value = "测试策略接口")
    @GetMapping("/test/{operation}/{num1}/{num2}")
    public int testCalculation(@PathVariable String operation,@PathVariable  int num1, @PathVariable int num2) {
        // 省略参数判空
        return calculationService.operateByStrategy(operation, num1, num2);
    }
}
