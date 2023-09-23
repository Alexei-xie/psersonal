package com.itself.rabbitmq.demo02;

import com.itself.utils.ConnectUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

import static com.itself.rabbitmq.demo02.Send.WORK_QUEUE;


/**
 * 正常消费速度
 * @Author xxw
 * @Date 2022/08/28
 */
public class Received2 {
    public static void main(String[] args) throws Exception{
        // 获取到连接
        Connection connection = ConnectUtil.getConnection();
        // 获取通道
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(WORK_QUEUE, false, false, false, null);

        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                try {
                    // body 即消息体
                    String msg = new String(body);
                    System.out.println(" [消费者2] received : " + msg + "!");
                    // 手动ACK
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (Exception e) {
                    channel.basicNack(envelope.getDeliveryTag(),false,true);
                }

            }
        };
        // 监听队列。
        channel.basicConsume(WORK_QUEUE, false, consumer);
    }
}
