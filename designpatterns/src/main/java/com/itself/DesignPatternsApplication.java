package com.itself;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;

import java.net.Inet4Address;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})// 取消数据库配置
public class DesignPatternsApplication implements ApplicationListener<WebServerInitializedEvent> {
    public static void main(String[] args) {
        SpringApplication.run(DesignPatternsApplication.class,args);

    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        try {
            log.info("=====>>>DesignPatternsApplication start successful , IP Address:http:"+ Inet4Address.getLocalHost().getHostAddress()+":"+event.getWebServer().getPort());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}