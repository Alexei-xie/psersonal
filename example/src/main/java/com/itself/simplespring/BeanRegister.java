package com.itself.simplespring;

import java.util.HashMap;
import java.util.Map;

/**
 * 对象注册器
 * ⽤于单例 bean 的缓存，我们⼤幅简化，默认所有 bean 都是单例
 * @Author duJi
 * @Date 2023/10/25
 */
public class BeanRegister {

    /**
     * 单例Bean缓存
     */
    private Map<String,Object> singletonMap = new HashMap<String,Object>();

    /**
     * 获取单例bean
     * @param beanName
     * @return
     */
    public Object getSingletonBean(String beanName) {
        return singletonMap.get(beanName);
    }

    /**
     * 注册单例bean
     * @param beanName
     * @param bean
     */
    public void registerSingletonBean(String beanName, Object bean) {
        if (singletonMap.containsKey(beanName)) {
            return;
        }
        singletonMap.put(beanName, bean);
    }
}
