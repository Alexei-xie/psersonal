package com.itself.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *  控制台输出 Swagger 接口文档地址
 * @Author xxw
 * @Date 2022/08/10
 */
@Component
@Slf4j
public class SwaggerOutPutConfig implements ApplicationListener<WebServerInitializedEvent> {

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        try {
            InetAddress inetAddress = Inet4Address.getLocalHost();
            int serverPort = event.getWebServer().getPort();
            log.info("项目启动启动成功！接口文档地址: http://"+inetAddress.getHostAddress()+":"+serverPort+"/swagger-ui/index.html");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}