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
public class BeanPo {

    private String name;

    private String age;

    private String newTime;

    private List<String> list1;

    private Set<String> set1;
}
