package com.itself.rabbitmq.demo01;

import com.itself.utils.ConnectUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

import static com.itself.constant.MQConstants.SIMPLE_QUEUE;


/**
 * 消息监听时  业务有异常
 * @Author xxw
 * @Date 2022/08/28
 */
public class ReceivedIncorrect {
    public static void main(String[] argv) throws Exception {
        Connection connection = ConnectUtil.getConnection();   // 获取到连接
        final Channel channel = connection.createChannel();// 创建通道
        // 声明队列
        channel.queueDeclare(SIMPLE_QUEUE, false, false, false, null);
        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    // body 即消息体
                    String msg = new String(body);
                    System.out.println(1/0); // 模拟异常  表示消息未正常处理
                    System.out.println(" [x] received : " + msg + "!");
                    channel.basicAck(envelope.getDeliveryTag(),false);//  代码没有异常 手动通知队列 删除消息即可
                } catch (Exception e){
                    e.printStackTrace();
                    //  第三个参数  false  直接删除消息   true  表示 ：把消息重回队列
                    channel.basicNack(envelope.getDeliveryTag(),false,true);
                }
            }
        };
        // 监听队列，第二个参数：是否自动进行消息确认。 false 告诉队列不要删除消息
        channel.basicConsume(SIMPLE_QUEUE, false, consumer);
    }
}
