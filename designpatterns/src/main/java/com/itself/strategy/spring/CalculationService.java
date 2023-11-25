package com.itself.strategy.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculationService {

    @Autowired
    private CalculationContext calculationContext;

    public int operateByStrategy(String strategy, int num1, int num2) {
        // 获取入参，根据不同的参数类型去执行不同的策略，Context的get方法是在这个地方用到的，operate方法就是一开始定义的策略接口
        //calculationContext.getCalculationStrategyMap().get(strategy)这里可能会出现空，所以要做一个容错处理
        return calculationContext.getCalculationStrategyMap().get(strategy).operate(num1, num2);
    }
}