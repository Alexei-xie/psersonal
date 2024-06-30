package com.itself.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author duJi
 * @Date 2024-06-29
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {
    /**
     * 数据字典Code
     */
    String code() default "";
    /**
     * 数据字典值
     */
    String value() default "";
}
