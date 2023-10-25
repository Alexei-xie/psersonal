package com.itself.bean;

import lombok.Data;

/**
 * @Author duJi
 * @Date 2023/10/25
 */
@Data
public class Stu {
    private String name;
    private int age;
    public void println(){
        System.out.println("duJi !!!");
    }
}
