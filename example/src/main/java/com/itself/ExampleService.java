package com.itself;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author duJi
 * @Date 2023/09/23
 */
@Slf4j
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})//取消数据库配置
@EnableAsync //使Async异步方法注解生效
@ServletComponentScan("com.itself.filter")//描包的注解，开启包扫描,配合MyFilter使用
public class ExampleService implements ApplicationListener<WebServerInitializedEvent> {
    public static void main(String[] args) {
        SpringApplication.run(ExampleService.class,args);
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        try {
            log.info("=====>>>ExampleService start successful , IP Address:http:"+ Inet4Address.getLocalHost().getHostAddress()+":"+event.getWebServer().getPort());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
