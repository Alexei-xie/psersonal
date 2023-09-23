package com.itself.rabbitmq.demo05;

import com.itself.utils.ConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 先启动消费端  按照指定路由监听：  再启动服务端，再投递消息
 * 使用topic类型的Exchange，发送消息的routing key
 * @Author xxw
 * @Date 2022/08/28
 */
public class TopicProducer {

    public static final String EXCHANGE_TOPIC = "exchange-topic";

    public static void main(String[] argv) throws Exception {
        // 获取到连接
        Connection connection = ConnectUtil.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();
        // 消息内容
        String message = "新增用户 : id = 1001";
        //并且指定routing key 分别使用  user.update， user.delete， product.update 三次测试
        channel.basicPublish(EXCHANGE_TOPIC, "user.update", null, message.getBytes());
        System.out.println(" [生产者：] Sent '" + message + "'");
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
