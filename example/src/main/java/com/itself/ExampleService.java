package com.itself;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author duJi
 * @Date 2023/09/23
 */
@SpringBootApplication
@EnableAsync //使Async异步方法注解生效
@ServletComponentScan("com.itself.example.filter")//描包的注解，开启包扫描,配合MyFilter使用
public class ExampleService {
    public static void main(String[] args) {
        SpringApplication.run(ExampleService.class,args);
    }
}
