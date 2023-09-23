package com.itself.webservice.demo.invoke;

import com.itself.domain.User;
import com.itself.webservice.demo.UserService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import java.net.MalformedURLException;

/**
 * 代理工厂的方式
 * 必要条件：
 * 1.webservice服务端发布地址
 * 2.webservice服务发布的接口类
 * 3.参数和返回值实体类
 */
public class UserClientAgent {
    public static void main(String[] args) throws MalformedURLException {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(UserService.class);
        factory.setAddress("http://127.0.0.1:1212/ws/user?wsdl");
        UserService service = (UserService) factory.create();
        // 通过代理对象获取本地客户端
        Client client = ClientProxy.getClient(service);
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new
        // ClientLoginInterceptor(USER_NAME,PASS_WORD));
        HTTPConduit conduit = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(2000); // 连接超时
        httpClientPolicy.setAllowChunking(false); // 取消块编码
        httpClientPolicy.setReceiveTimeout(120000); // 响应超时
        conduit.setClient(httpClientPolicy);
        // client.getOutInterceptors().addAll(interceptors);//设置拦截器
        try {
            User user = service.getUserByName("王五");
            System.out.println("返回数据:" + user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
