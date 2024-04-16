package com.itself.user.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itself.utils.CustomerBigDecimalSerialize;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件导入不能使用 @Accessors(chain = true)注解
 * 且需要添加@NoArgsConstructor 和 @AllArgsConstructor注解
 * @Author xxw
 * @Date 2022/07/09
 */
@Data
@Builder
@TableName("user")
@NoArgsConstructor
@AllArgsConstructor
public class UserPO implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_ID)
    @ExcelIgnore
    private String id;
    @ExcelProperty(value = "姓名")
    private String name;
    @ExcelProperty(value = "年纪")
    private Integer age;
    @ExcelProperty(value = "性别")
    private String sex;
    @ExcelProperty(value = "价格")
    @JsonSerialize(using = CustomerBigDecimalSerialize.class)
    private BigDecimal price;
}
