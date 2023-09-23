package com.itself.annotation.demo;

import java.lang.annotation.*;

/**
 *  方法注解
 * @Author xxw
 * @Date 2022/10/17
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodAnnotation {
    String name() default "defaultName";
    MethodTypeEnum type() default MethodTypeEnum.TYPE1; //注意：此处的引用枚举类必须要是public类型，不然无法通过反射获取该注解属性
}
