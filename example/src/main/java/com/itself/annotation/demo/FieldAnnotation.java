package com.itself.annotation.demo;

import java.lang.annotation.*;

/**
 *  字段注解
 * @Author xxw
 * @Date 2022/10/17
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldAnnotation {
    String name() default "defaultName";
    String value() default "defaultValue";
}
