package com.itself.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 系统启动日志信息打印
 * 启动类也可实现ApplicationListener接口来进行数据初始化等操作
 * 先后执行顺序:
 *  @PostConstruct注解 > @Bean注解 > 实现ApplicationListener接口
 * @Author xxw
 * @Date 2022/08/10
 */
@Configuration
@Slf4j
public class StartLogConfig implements ApplicationListener<WebServerInitializedEvent> {

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
            int serverPort = event.getWebServer().getPort();
            System.out.println("#*#*#*#*#*#*#*#*#*\n" +
                                "      #*        #*\n" +
                                "      #*        #*\n" +
                                "      #*        #*\n" +
                                "      #*        #*\n" +
                                "      #*    #*  #*\n" +
                                "      #*     #* #*\n" +
                                "      #*          \n" +
                                "      #*          \n" +
                                "#*#*#*#*#*#*#*#*#*");
            log.info("===>>>Application start successful , IP Address:http://"+ inetAddress.getHostAddress()+":"+serverPort);
            log.info("项目启动启动成功！接口文档地址: http://"+inetAddress.getHostAddress()+":"+serverPort+"/swagger-ui/index.html");
        } catch (UnknownHostException e) {
            log.error("start log exception:{}", e.getMessage());
        }

    }

}