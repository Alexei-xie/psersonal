package com.itself.simplespring;

import java.util.HashMap;
import java.util.Map;

/**
 * 最核⼼的⼀个类，对象工厂
 *
 * @Author duJi
 * @Date 2023/10/25
 */
public class BeanFactory {

    private Map<String, BeanDefintion> beanDefintionMap = new HashMap<>();
    private BeanRegister beanRegister;

    public BeanFactory() {
        // 创建bean注册器
        beanRegister = new BeanRegister();
        // 加载资源
        this.beanDefintionMap = ResourceLoader.getResources();
    }

    /**
     * 获取bean
     *
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) {
        // 从bean缓存中获取
        Object bean = beanRegister.getSingletonBean(beanName);
        if (bean != null) {
            return bean;
        }
        // 根据bean定义，创建bean
        return createBean(beanDefintionMap.get(beanName));
    }

    /**
     * 创建bean
     * @param beanDefintion
     * @return
     */
    private Object createBean(BeanDefintion beanDefintion) {
        try {
            Object bean = beanDefintion.getBeanClass().newInstance();
            // 缓存bean
            beanRegister.registerSingletonBean(beanDefintion.getBeanName(), bean);
            return bean;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
