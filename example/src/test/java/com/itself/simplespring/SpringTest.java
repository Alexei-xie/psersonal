package com.itself.simplespring;

import com.itself.bean.Stu;
import org.junit.jupiter.api.Test;

/**
 * @Author duJi
 * @Date 2023/10/25
 */
class SpringTest {

    @Test
    public void  test_BeanFactory(){
        BeanFactory beanFactory = new BeanFactory();
        //第一次获取bean是通过反射创建bean对象，然后缓存bean
        Stu stu = (Stu)beanFactory.getBean("stu");
        stu.println();

        //第二次获取bean是从缓存中获取bean对象
        Stu stu2= (Stu)beanFactory.getBean("stu");
        stu2.println();
    }


    /**
     * assert关键字 用法demo
     * assert关键字通常用于调试和测试阶段，在程序正式运行时通常会被禁用，以避免性能损失。
     */
    @Test
    public void  test(){
        Stu stu = new Stu();
        assert stu != null : "对象为空"; //false走后面错误信息
        System.out.println(111);
    }
}