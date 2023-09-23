package com.itself.utils;


import com.itself.domain.User;

import java.util.Objects;

/**
 * @Author xxw
 * @Date 2022/03/27
 */
public class CheckParamUtil {

    /**
     * 泛型可变对象的定义
     * @param <T>泛型的标识，由调用方法的时候来指定
     */
    public static <T> T checkParam(T t){
        if(Objects.isNull(t)){
            return null;
        }
        return t;
    }

    /**
     * 泛型可变参数的定义
     */
    public static <E> E[] checkParams(E...e){
            return e;
    }


    public static void main(String[] args) {
        User user = new User();
        user = null;
        User result = checkParam(user);
        System.out.println("result = " + result);

        Integer[] params = checkParams(1, 2, 3);
        System.out.println(params[2]);
    }
}
