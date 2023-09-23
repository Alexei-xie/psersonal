package com.itself.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**启动时自动打印想要输出的信息
 * @Author xxw
 * @Date 2023/03/31
 */
@Configuration
@Slf4j
public class ConfigBeanOutput {
    @Bean
    public void printServerInfo() throws UnknownHostException {
      log.info("Current Server's IP Address : "+ InetAddress.getLocalHost());
      log.info("Current Server's IP Address : "+ InetAddress.getLoopbackAddress());
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
    }
}

