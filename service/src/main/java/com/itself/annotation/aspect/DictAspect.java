package com.itself.annotation.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据字典注解切面
 * @Author duJi
 * @Date 2024-06-29
 */
@Slf4j
@Aspect
@Component
public class DictAspect {
    /**
     * 模拟数据字典查询接口
     */
    public static final Map<String,Map<String,String>> DICT_MAP = new HashMap<>();
    public static final Map<String,Map<String,String>> ITEM_MAP = new HashMap<>();

    static {
        DICT_MAP.put("SEX", Map.of("man", "男", "woman", "女"));
        DICT_MAP.put("NAME", Map.of("duJi","镀己","xw","小卫"));
        ITEM_MAP.put("SEX", Map.of("男", "man", "女", "woman"));
        ITEM_MAP.put("NAME", Map.of("镀己","duJi","小卫","xw"));
    }

    /**
     * 标注注解切点
     */
    @Pointcut("execution(* com.itself.user..*(..))")
    // @Pointcut("@annotation(com.itself.annotation.Dict)") @annotation 切点表达式主要用于方法上的注解,此处如果使用这种方式则切面不生效
    public void pointCut() {}

    /**
     * 前置通知,初始化数据字典 MAP
     */
    @Before("pointCut()")
    public void doBeforeAdvice(JoinPoint joinPoint) throws IllegalAccessException{
        log.info("前置通知,初始化数据字典 MAP");
    }

    /**
     * 环绕通知
     * @param joinPoint
     * @return Object
     * @throws Throwable
     */
    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //返回执行结果
        return joinPoint.proceed();
    }
}