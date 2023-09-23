package com.itself.webservice.demo;


import com.itself.domain.User;

import javax.jws.WebService;

@WebService(targetNamespace = "http://demo.webservice.itself.com"/*wsdl命名空间*/,
        name = "UserService"/*portType名称 客户端生成代码时 为接口名称*/,
        serviceName = "userService"/*服务name名称*/,
        portName = "userPortName"/*port名称*/,
        endpointInterface = "com.itself.webservice.demo.UserService"/*指定发布webservcie的接口类，此类也需要接入@WebService注解*/)
public class UserServiceImpl implements UserService {

    @Override
    public User getUserByName(String userName) {
        return new User(userName, "23", "11");
    }
}
