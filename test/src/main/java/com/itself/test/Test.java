package com.itself.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author duJi
 * @Date 2023/11/05
 */
public class Test {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 4, 5, 6);
        Collections.swap(list,2,4);
        System.out.println(list);
    }
}
