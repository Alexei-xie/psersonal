package com.itself.webservice.demo;


import com.itself.domain.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * wsdl文档地址： http://localhost:1212/ws/user?wsdl
 * 创建webservice服务接口
 */
@WebService(targetNamespace = "http://demo.webservice.itself.com"/*一般把包名倒着写*/,
        name = "userService")
public interface UserService {

    /**
     * 通过用户名获取用户
     *
     * @param userName
     * @return
     */
    @WebMethod(operationName = "getUserByName")
    User getUserByName(@WebParam(name = "userName") String userName);

}

