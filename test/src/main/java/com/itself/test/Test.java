package com.itself.test;

import java.util.ArrayList;
import org.assertj.core.util.Lists;

/**
 * @Author duJi
 * @Date 2023/11/05
 */
public class Test {
    public static void main(String[] args) {
        ArrayList<String> list = Lists.newArrayList("1", "2", "3");
        for (String s : list) {
            System.out.println(s);
            if (s.equals("2")){
                break;
            }
        }
    }
}
