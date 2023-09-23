package com.itself.annotation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author xxw
 * @Date 2022/12/09
 */
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckValidator.class)//指出自定义的校验方法
public @interface Validator {

    String message() default "";//自定义异常返回信息

    CheckType type();//自定义校验字段

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
