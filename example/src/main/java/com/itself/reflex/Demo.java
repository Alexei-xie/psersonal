package com.itself.reflex;


import com.itself.domain.User;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射
 * @Author xxw
 * @Date 2022/09/20
 */
public class Demo {
    public static void main(String[] args) {

    }
}

/**
 * 通过反射创建类对象的三种方式：
 *    1.Obj.getClass()
 *    2.Obj.class
 *    3.Class.forName("")
 */
class Demo01{
    public static void main(String[] args) throws ClassNotFoundException {
        User user = new User();
        user.setName("lisi");
        Class<? extends User> aClass = user.getClass();
        Class<User> bClass = User.class;
        System.out.println(aClass == bClass); // 判断两种方式获取的class对象是否是同一个
        System.out.println("----");
        Class<?> cClasss = Class.forName("com.itself.demo.bean.User");
        System.out.println(aClass==cClasss);
    }
}

/**
 * 通过反射去操作对象的属性
 */
class Demo02 {
    public static void main(String[] args) throws IllegalAccessException {
        User user = new User();
        user.setName("张三").setAge("20");
        Class<? extends User> clazz = user.getClass(); // 返回该对象的运行时类
        Field[] fields = clazz.getDeclaredFields(); // 获取该类下的所有属性
        List<String> list = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true); // 暴力反射，以便于操作该字段的属性
            String name = field.getName(); // 获取该字段的属性名称
            if ("name".equals(name)){   // 根据某个已知的属性名称去做判断，然后给该属性赋值
                field.set(user,"李四");
            }
            list.add(name);
            System.out.println(field.getType());    // 获取该属性的字段类型
            System.out.println(field.get(user));    // field.get(obj) 获取该对象该字段的value值
            list.add(String.valueOf(field.get(user)));
        }
        System.out.println(list);
    }
}
