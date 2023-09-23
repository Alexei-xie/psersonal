package com.itself.rabbitmq.demo04;

import com.itself.utils.ConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * Direct：定向，把消息交给符合指定routing key 的队列
 * 发送消息的RoutingKey分别是：insert、update、delete
 * @Author xxw
 * @Date 2022/08/28
 */
public class DirectProducer {

    public static final String EXCHANGE_DIRECT = "exchange-direct";
    //创建多个队列
    public static final String DIRECT_QUEUE1 = "direct_queue1";
    public static final String DIRECT_QUEUE2 = "direct_queue2";

    public static void main(String[] args) throws Exception {
        // 1. 建立和mq的连接
        Connection connection = ConnectUtil.getConnection();
        // 2. 从连接中创建通道，channel   使用通道才能完成消息相关的操作
        Channel channel = connection.createChannel();
        //3.声明交换器和队列             TYPE:交换机类型-direct
        channel.exchangeDeclare(EXCHANGE_DIRECT,"direct",false);
        //队列1
        channel.queueDeclare(DIRECT_QUEUE1, false, false, false,null);
        //队列2
        channel.queueDeclare(DIRECT_QUEUE2, false, false, false,null);

        //4.同一个交换机与2个队列绑定   参数3： 直连direct形式  定义路由规则   两个队列按照路由 接受对应的消息
        channel.queueBind(DIRECT_QUEUE1,EXCHANGE_DIRECT,"insert");//绑定第一个队列
        channel.queueBind(DIRECT_QUEUE2,EXCHANGE_DIRECT,"delete");//绑定第二个队列
        channel.queueBind(DIRECT_QUEUE2,EXCHANGE_DIRECT,"update");//绑定第二个队列
        //5.生产消息  向指定的队列投递消息
        for(int i=0;i<10;i++){
            channel.basicPublish(EXCHANGE_DIRECT,"insert", true,null,"hello direct insert!".getBytes());
            channel.basicPublish(EXCHANGE_DIRECT,"delete", true,null,"hello direct delete!".getBytes());
            channel.basicPublish(EXCHANGE_DIRECT,"update", true,null,"hello direct update!".getBytes());
        }
        //6.关闭channel和连接
        channel.close();
        //关闭连接
        connection.close();
    }
}
