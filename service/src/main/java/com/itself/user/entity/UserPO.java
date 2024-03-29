package com.itself.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itself.utils.CustomerBigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.experimental.Accessors;

/**
 * @Author xxw
 * @Date 2022/07/09
 */
@Data
@TableName("user")
@Accessors(chain = true)
public class UserPO implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    private Integer age;
    private String sex;
    @JsonSerialize(using = CustomerBigDecimalSerialize.class)
    private BigDecimal price;
}
