package com.itself;

import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
/**
 * @Author duJi
 * @Date 2024/01/23
 */
@Slf4j
public class ApplicationInfoPrinter {
    /**
    * 启动成功之后，打印项目信息
    */
    public static void print(ConfigurableApplicationContext context) throws UnknownHostException {
        ConfigurableEnvironment environment = context.getEnvironment();
        log.info("=====>>>CommonService start successful , IP Address:http://"+ InetAddress.getLocalHost().getHostAddress() + ":" + environment.getProperty("server.port"));
    }
}
