package com.itself.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;

/** https://blog.csdn.net/JAVA_MHH/article/details/123988496
 * ServletContextListener 接口用于监听 ServletContext 对象的创建和销毁事件
 * ServletContextAttributeListener接口用于监听ServletContext 新增 删除 替换属性
 * @Author xxw
 * @Date 2023/01/11
 */
@Slf4j
@WebListener
// @Configuration
public class MyServletContextListener implements ServletContextListener, ServletContextAttributeListener {
    /**
     * 当servletContext对象被创建时，调用该方法
     * @param sce Information about the ServletContext that was initialized
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // ServletContextListener.super.contextInitialized(sce);
        log.info("监听ServletContext对象被创建了======");
        //获取事件源
        ServletContext servletContext = sce.getServletContext();
        //向servletContext中保存数据
        servletContext.setAttribute("age",12);
    }

    /**
     * 当servletContext对象被销毁时，调用该方法
     * @param sce Information about the ServletContext that was destroyed
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // ServletContextListener.super.contextDestroyed(sce);
        log.info("ServletContext对象被销毁了======");
    }

    /**
     * 当向监听器对象中添加一个属性时，web容器就调用事件监听器的attributeAdded方法进行响应
     * 这个方法接受一个事件类型的参数，监听器可以通过这个参数来获得正在增加属性的域对象和被保存到域中的属性对象
     * @param scae Information about the new attribute
     */
    @Override
    public void attributeAdded(ServletContextAttributeEvent scae) {
        // ServletContextAttributeListener.super.attributeAdded(scae);
        log.info("ServletContext新增属性--->" +scae.getName() + ": "+scae.getValue());
    }

    /**
     * 当监听器的域对象中的某个属性被替换时，web容器调用事件监听器的该方法
     * @param scae Information about the replaced attribute
     */
    @Override
    public void attributeReplaced(ServletContextAttributeEvent scae) {
        // ServletContextAttributeListener.super.attributeReplaced(scae);
        log.info("ServletContext替换属性--->" +scae.getName() + ": "+scae.getValue());
    }

    /**
     * 当删除被监听对象中的一个属性时，web容器调用事件监听器的该方法
     * @param scae Information about the removed attribute
     */
    @Override
    public void attributeRemoved(ServletContextAttributeEvent scae) {
        // ServletContextAttributeListener.super.attributeRemoved(scae);
        log.info("ServletContext删除属性--->" +scae.getName() + ": "+scae.getValue());
    }


}
