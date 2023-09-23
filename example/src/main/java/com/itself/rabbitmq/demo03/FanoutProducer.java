package com.itself.rabbitmq.demo03;

import com.itself.utils.ConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import static com.itself.constant.MQConstants.*;

/**
 * - Fanout：广播，将消息交给所有绑定到交换机的队列
 * 消息生产者，消息发送到交换机，交换机绑定多个队列  不需要声明 routingKey
 * @Author xxw
 * @Date 2022/08/28
 */
public class FanoutProducer {



    public static void main(String[] args) throws Exception {
        // 1. 建立和mq的连接
        Connection connection = ConnectUtil.getConnection();
        // 2. 从连接中创建通道，channel   使用通道才能完成消息相关的操作
        Channel channel = connection.createChannel();
        //3.声明交换器和队列            TYPE:扇形交换机类型-fanout
        channel.exchangeDeclare(EXCHANGE_FANOUT,"fanout",true);
        //队列1
        channel.queueDeclare(FANOUT_QUEUE1, true, false, false,null);
        //队列2
        channel.queueDeclare(FANOUT_QUEUE2, true, false, false,null);
        //4.同一个交换机与2个队列绑定   参数3： 扇出形式 不需要路由规则   两个队列都会接受到生产投递的消息
        channel.queueBind(FANOUT_QUEUE1,EXCHANGE_FANOUT,"");//绑定第一个队列
        channel.queueBind(FANOUT_QUEUE2,EXCHANGE_FANOUT,"");//绑定第二个队列
        //5.生产消息
        for(int i=0;i<10;i++){
            channel.basicPublish(EXCHANGE_FANOUT,"", true,null,"hello fanout!".getBytes());
        }
        //6.关闭channel和连接
        channel.close();
        //关闭连接
        connection.close();
    }
}
