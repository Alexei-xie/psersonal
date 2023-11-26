package com.itself.test;

import java.util.*;

/**
 * @Author duJi
 * @Date 2023/11/05
 */
public class Test {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 4, 5, 6);
        System.out.println(list.subList(1,2));//list集合切分收集
        Collections.swap(list,2,4);//list元素位置调换
        System.out.println(list);
    }
}
