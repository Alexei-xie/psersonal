package com.itself;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Author duJi
 * @Date 2023/09/23
 */
@Slf4j
@SpringBootApplication
@EnableAspectJAutoProxy// 开启AOP
@ComponentScan({"com.itself.user", "com.itself.easyexcel", "com.itself.config","com.itself.annotation"})
// 因为引入了common包中的东西，所以要指定扫描当前模块下的包路径，不然无法启动
// "com.itself.config"：将配置文件日志输出copy过来，因为指定了扫描路径，所以common包中的日志输出无法被扫描到，无法输出启动日志
public class ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    // @Override
    // public void onApplicationEvent(WebServerInitializedEvent event) {
    //     try {
    //         log.info("=====>>>ServiceApplication start successful , IP Address:http://"+ Inet4Address.getLocalHost().getHostAddress()+":"+event.getWebServer().getPort());
    //     } catch (UnknownHostException e) {
    //         throw new RuntimeException(e);
    //     }
    // }
}
