package com.itself.supplier.opt;


import com.itself.supplier.Function;

import java.util.HashMap;
import java.util.Map;

/**
 * 通过map+function函数来解决if...else问题
 * @Author xxw
 * @Date 2023/07/08
 */

public class BizServiceFactory {

    /**
     * key：不同的业务类型代码
     * Function<String, String>  key:传参，
     */
    private final static  Map<String, Function<String,String>> map = new HashMap<>();

    private static String get(String value){
        return value;
    }


    public static void initData() {
        map.put("key1",BizServiceFactory::get);
        map.put("key2",BizServiceFactory::get);
        map.put("key3",BizServiceFactory::get);
        map.put("key4",BizServiceFactory::get);
    }


    public static void execBizService(String key, String apply){
        initData();
        Function<String, String> function = map.getOrDefault(key,value->get("0"));
        // Function<String, String> function = map.getOrDefault(key,BizServiceFactory::get);
        //此时apply调用的是initData方法中的value的Function函数即里面的get方法，并获取到返回值
        System.out.println(function.apply(apply));
    }

    public static void main(String[] args) {
        execBizService("key1","key2");
        execBizService("1","key2");
    }

}
