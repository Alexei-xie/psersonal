package com.itself.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 测试对象的clone方法 类要实现Cloneable接口
 * @Author xxw
 * @Date 2021/09/29
 */
@Data
@Accessors(chain = true)
public class User implements Serializable,Cloneable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String name = "xxw";

    private String age  = "11";


    public static void main(String[] args) {
        User user = new User().setName("22").setAge("11");
        //测试clone方法
        try {
            User clone = (User)user.clone();
            System.out.println(clone);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 重写clone方法，主要目的是为了将修饰方法的 protected 权限修改为 public
     */
    @Override
    public Object clone()throws CloneNotSupportedException{
        return super.clone();
    }
}
