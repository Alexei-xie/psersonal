package com.itself;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author duJi
 * @Date 2023/09/23
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})//取消数据库配置
public class CommonService {
    public static void main(String[] args) {
        SpringApplication.run(CommonService.class,args);
    }
}
