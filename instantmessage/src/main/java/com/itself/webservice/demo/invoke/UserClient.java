package com.itself.webservice.demo.invoke;

import com.itself.domain.User;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 * jdk8的环境下运行，jdk8以上报错，暂时没法解决
 * 动态调用，根据发布地址动态生成客户端然后调用方法
 * 必要条件：
 * 1.webservice服务端发布地址
 * 2.调用方法使用的参数和返回值
 * 3.如果参数或返回值是实体类，需要根据指定的namespace创建实体类
 */
public class UserClient {
	// 创建动态客户端
	private static Client client = null;
	static {
		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
		//此处枚举类文件和返回值实体类不能和UserService放在同一个包下，否则会报错
		client = factory.createClient("http://127.0.0.1:1212/ws/user?wsdl");
		// 需要密码的情况需要加上用户名和密码
		// client.getOutInterceptors().add(new
		// ClientLoginInterceptor(USER_NAME,PASS_WORD));
		HTTPConduit conduit = (HTTPConduit) client.getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(2000); // 连接超时
		httpClientPolicy.setAllowChunking(false); // 取消块编码
		httpClientPolicy.setReceiveTimeout(120000); // 响应超时
		conduit.setClient(httpClientPolicy);
	}

	public static void main(String[] args) {
		// client.getOutInterceptors().addAll(interceptors);//设置拦截器
		try {
			Object[] objects = null;
			// invoke("方法名",参数1,参数2,参数3....);
			objects = client.invoke("getUserByName", "李四");
			User user = (User) objects[0];//此处强转会报错，提示返回值不是同一个类
			System.out.println(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(client);
	}
}
