package com.itself.simplespring;

import java.io.InputStream;
import java.util.*;

/**
 * 资源加载器，⽤来完成配置⽂件中配置的加载
 * @Author duJi
 * @Date 2023/10/25
 */
public class ResourceLoader {

    public static Map<String,BeanDefintion> getResources(){
        Map<String,BeanDefintion> beanDefintionMap = new HashMap<>();
        Properties properties = new Properties();
        InputStream inputStream = ResourceLoader.class.getResourceAsStream("/beans.properties");
        assert inputStream != null : "未加载到资源文件！";
        try {
            properties.load(inputStream);
            Iterator<String> iterator = properties.stringPropertyNames().iterator();
            while (iterator.hasNext()){
                String key = iterator.next();
                String className = properties.getProperty(key);

                BeanDefintion beanDefintion = new BeanDefintion();
                beanDefintion.setBeanName(key);
                Class<?> aClass = Class.forName(className);
                beanDefintion.setBeanClass(aClass);
                beanDefintionMap.put(key, beanDefintion);
            }

            inputStream.close();
        } catch (Exception e ) {
            throw new RuntimeException(e);
        }
        return beanDefintionMap;
    }
}
