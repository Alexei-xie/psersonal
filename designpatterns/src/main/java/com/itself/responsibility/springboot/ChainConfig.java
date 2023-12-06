package com.itself.responsibility.springboot;

import com.itself.responsibility.springboot.handler.FirstHandler;
import com.itself.responsibility.springboot.handler.SecondHandler;
import com.itself.responsibility.springboot.handler.ThirdHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**责任链配置管理中心
 * @Author duJi
 * @Date 2023/12/06
 */
@Configuration
public class ChainConfig {

    @Bean
    public Handler chain() {
        FirstHandler firstHandler = new FirstHandler();
        SecondHandler secondHandler = new SecondHandler();
        ThirdHandler thirdHandler = new ThirdHandler();
        //这里填写顺序没有强制要求
        firstHandler.setNextHandler(secondHandler);
        secondHandler.setNextHandler(thirdHandler);

        return firstHandler;
    }
}
