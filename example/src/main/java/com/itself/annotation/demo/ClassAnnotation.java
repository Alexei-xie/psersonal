package com.itself.annotation.demo;

import java.lang.annotation.*;

/**
 *  类注解
 * @Author xxw
 * @Date 2022/10/17
 */
@Documented//定义可以被文档工具文档化
@Target(ElementType.TYPE)//注解修饰范围为类、接口、枚举
@Retention(RetentionPolicy.RUNTIME)//声明周期为runtime，运行时可以通过反射拿到
public @interface ClassAnnotation {
    String name() default "defaultService";
    String version() default "1.1.0";
}
