package com.itself.utils;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 在启动的服务中，获取Bean实例，要获取的这个实例是要被spring所管理，即加了Component等注解
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {
    private static final Log logger = LogFactory.getLog(ApplicationContextProvider.class);

    private static ApplicationContext context;

    private ApplicationContextProvider(){}

    public static ApplicationContext getApplicationContext(){
        return context;
    }

    public static <T> T getBean(String name,Class<T> tClass){
        if(context != null){
            return context.getBean(name,tClass);
        }
        return null;
    }

    public static <T> T getBean(Class<T> tClass){
        return getApplicationContext().getBean(tClass);
    }

    // 通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        logger.debug("========ApplicationContext config success ,在普通类可以通过调用ApplicationContextProvider.getApplicationContext()获取applicationContext对象,ApplicationContext="+ApplicationContextProvider.getApplicationContext()+"========");
    }

    /**
     * 获取HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes != null){
            return servletRequestAttributes.getRequest();
        }
        return null;
    }

    public static String getDomain(){
        HttpServletRequest request = getHttpServletRequest();
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    public static String getOrigin(){
        HttpServletRequest request = getHttpServletRequest();
        return request.getHeader("Origin");
    }
}