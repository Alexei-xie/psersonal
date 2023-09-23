package com.itself.rabbitmq.demo01;

import com.itself.utils.ConnectUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

import static com.itself.constant.MQConstants.SIMPLE_QUEUE;

/**
 * 消息监听时  业务无异常
 * @Author xxw
 * @Date 2022/08/26
 */
public class ReceivedCorrect {
    public static void main(String[] argv) throws Exception {
        // 获取到连接
        Connection connection = ConnectUtil.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(SIMPLE_QUEUE, false, false, false, null);
        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                // body 即消息体
                String msg = new String(body);
                System.out.println(" [x] received : " + msg + "!");
            }
        };
        // 监听队列，第二个参数：是否自动进行消息确认。
        channel.basicConsume(SIMPLE_QUEUE, true, consumer);
    }
}
