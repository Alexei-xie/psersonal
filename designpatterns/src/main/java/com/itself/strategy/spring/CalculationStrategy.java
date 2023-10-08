package com.itself.strategy.spring;

import org.springframework.stereotype.Component;

/**
 * @Author duJi
 * @Date 2023/10/08
 */
public interface CalculationStrategy {
    /**
     * 策略接口
     */
    int operate(int num1, int num2);
}
@Component("add")
class AddCalculationStrategyImpl implements CalculationStrategy {
    @Override
    public int operate(int num1, int num2) {
        return num1 + num2;
    }
}
@Component("Division")
class DivisionStrategyImpl implements CalculationStrategy {
    @Override
    public int operate(int num1, int num2) {
        return num1 / num2;
    }
}
@Component("multiple")
class MultiplicationStrategyImpl implements CalculationStrategy {
    @Override
    public int operate(int num1, int num2) {
        return num1 * num2;
    }
}
@Component("subtract")
class SubtractionStrategyImpl implements CalculationStrategy {

    @Override
    public int operate(int num1, int num2) {
        return num1 - num2;
    }
}

/**
 * 如果Component注解中不写标识会默认加载驼峰类名：testStrategyImpl
 */
@Component
class TestStrategyImpl implements CalculationStrategy {

    @Override
    public int operate(int num1, int num2) {
        return num1 - num2;
    }
}

