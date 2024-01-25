package com.itself;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
/**
 * @Author duJi
 * @Date 2024/01/23
 */
@Slf4j
public class ApplicationInfoPrinter {
    /**
    * 启动成功之后，打印项目信息
    */
    public static void print(ConfigurableApplicationContext context) {
        ConfigurableEnvironment environment = context.getEnvironment();
        log.info("=====>>>CommonService start successful , IP Address:http://"+ InetAddress.getLoopbackAddress().getHostAddress() + ":" + environment.getProperty("server.port"));
    }
}
