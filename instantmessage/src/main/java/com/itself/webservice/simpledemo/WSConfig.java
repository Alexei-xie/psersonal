package com.itself.webservice.simpledemo;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import javax.xml.ws.Endpoint;

/**
 * https://blog.csdn.net/qiaodaima0/article/details/104812483
 * 配置并发布webService
 */
// @Configuration
public class WSConfig {

    @Resource
 	private Bus bus;

    /**
     * webService接口
     */
    @Resource
 	 WSService  demoService;

    /**   *用Endpoint发布服务   * @return   */
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, demoService);
        endpoint.publish("/api");
        return endpoint;
    }
}