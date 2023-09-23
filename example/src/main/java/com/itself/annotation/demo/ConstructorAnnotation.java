package com.itself.annotation.demo;

import java.lang.annotation.*;

/**
 *  构造方法注解
 * @Author xxw
 * @Date 2022/10/17
 */
@Documented
@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConstructorAnnotation {
    String constructionName() default "";
    String remark() default "构造器";
}
