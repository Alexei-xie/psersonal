package com.itself.genericity;

import com.itself.domain.User;
import com.itself.utils.CheckParamUtil;
import java.util.List;

/**
 * @Author xxw
 * @Date 2022/03/27
 */
public class GenericDemo {
    public static void main(String[] args) {
        //指定类型
        Generic<String> generic = new Generic<>();
        //创建对应类型的集合
        List<String> arrayList = List.of("苹果手机", "华为手机", "小米手机");
        //将集合填入类里面的集合
        for (String s : arrayList) {
            generic.addObject(s);
        }
        //获取对应的随机数据
        System.out.println(generic.getObject());
        System.out.println("------------------------------------");
        User user = new User();
        User result = CheckParamUtil.checkParam(user);
        System.out.println(result);


    }
}
