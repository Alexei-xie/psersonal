package com.itself.mapstruct.bean;

import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: duJi
 * @Date: 2024-01-19
 **/
@Data
@Accessors(chain = true)
public class BeanPo {

    private String name;

    private String age;

    private List<String> list;

    private Set<String> set;
}
