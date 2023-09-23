package com.itself.webservice.demo.invoke;


import com.itself.domain.User;
import com.itself.webservice.demo.UserService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 使用jdk原生代码实现调用
 * 必要条件：
 * 1.webservice服务端发布地址
 * 2.webservice服务发布的接口类
 * 3.参数和返回值实体类
 */
public class UserClient1 {

	public static void main(String[] args) throws MalformedURLException {
		URL url = new URL("http://127.0.0.1:1212/ws/user?wsdl");
		// 指定命名空间和服务名称
		QName qName = new QName("http://demo1.webService.itself.com", "userService");
		Service service = Service.create(url, qName);
		// 通过getPort方法返回指定接口
		UserService myServer = service.getPort(
				new QName("http://demo1.webService.itself.com", "userPortName"), // 绑定端口名称
				UserService.class);
		// 调用方法 获取返回值
		User user = myServer.getUserByName("张三");
		System.out.println(user);
	}
}
