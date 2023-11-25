package com.itself.strategy.spring;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 策略上下文
 * @Author duJi
 * @Date 2023/10/08
 */
@Component
public class CalculationContext {
    /**
     *  把策略角色（类型）key,和参数value放到Map中
     *  key就是beanName(具体策略实现类中@Component的名字)，value就是接口（具体的实现类）
     *  Maps是guava下的封装类型，实则是静态的创建了一个HashMap的对象，Maps可以根据key去获取value对象
     */
    public final Map<String, CalculationStrategy> calculationStrategyMap = Maps.newHashMapWithExpectedSize(4);

    /**
     * 利用构造函数在项目启动的时候将策略实现类注册到 map里
     * @param strategyMap
     */
    public CalculationContext(Map<String, CalculationStrategy> strategyMap) {
        this.calculationStrategyMap.clear();
        this.calculationStrategyMap.putAll(strategyMap);
    }


    //可以使用@Getter注解代替，这样写方便读者理解在Service层调用Context执行策略
    public Map<String, CalculationStrategy> getCalculationStrategyMap() {
        return calculationStrategyMap;
    }
}
