package com.itself.user.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itself.annotation.Dict;
import com.itself.converter.DictConverter;
import com.itself.utils.CustomerBigDecimalSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

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
@ExcelIgnoreUnannotated // 自动忽略不需要导入导出的字段，可替代@ExcelIgnore注解
public class UserPO implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    @Dict(code = "NAME")
    @ExcelProperty(value = "姓名",converter = DictConverter.class)
    private String name;
    @ExcelProperty(value = "年纪")
    private Integer age;
    @Dict(code = "SEX")
    @ExcelProperty(value = "性别",converter = DictConverter.class)
    private String sex;
    @ExcelProperty(value = "价格")
    @JsonSerialize(using = CustomerBigDecimalSerialize.class)
    private BigDecimal price;
}
