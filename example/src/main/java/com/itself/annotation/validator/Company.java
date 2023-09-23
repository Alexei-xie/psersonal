package com.itself.annotation.validator;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author xxw
 * @Date 2022/12/09
 */
@Data
@Accessors(chain = true)
public class Company {

    @Validator(type = CheckType.MOBILE,message = "手机号格式有误")
    private String requestNo;

    @Validator(type = CheckType.SHOT_DATE,message = "时间戳格式不正确，格式为：yyyyMMdd")
    @NotBlank(message = "时间戳不能为空")
    private String timestamp;

}
