package com.itself;

import java.net.UnknownHostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author duJi
 * @Date 2023/09/23
 */
@Slf4j
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})//取消数据库配置
public class CommonService {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext context = SpringApplication.run(CommonService.class, args);
        // 日志打印
        ApplicationInfoPrinter.print(context);
    }
}
