package com.itself.mapstruct.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

/**
 * @Author: duJi
 * @Date: 2024-01-19
 **/
@Data
@Accessors(chain = true)
public class BeanDto {

    private String name;

    private Integer age;

    private String time;

    private List<Integer> list;

    private Set<String> set;
}
