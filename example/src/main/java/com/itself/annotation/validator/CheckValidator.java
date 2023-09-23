package com.itself.annotation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *  校验实现：                                 <Valid,Object>
 *  实现ConstrainValidator接口，这是一个泛型接口，泛型中第一个参数是自定义的注解，第二个参数是注解的使用类型
 *
 * @Author xxw
 * @Date 2022/12/09
 */
public class CheckValidator implements ConstraintValidator<Validator,Object> {
    private CheckType type;

    @Override
    public void initialize(Validator constraintAnnotation) {
        this.type = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return CheckType.validate(type,o);
    }
}
