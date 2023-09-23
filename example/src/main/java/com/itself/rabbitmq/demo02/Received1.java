package com.itself.rabbitmq.demo02;

import com.itself.utils.ConnectUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 消费者1  每次会线程睡眠一秒后再进行消费
 * @Author xxw
 * @Date 2022/08/28
 */
public class Received1 {
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectUtil.getConnection();//获取连接
        Channel channel = connection.createChannel();//通过连接创建通道
        //声明队列
        channel.queueDeclare(Send.WORK_QUEUE,false,false,false,null);
        //设置消费者同时只能处理一条消息
        channel.basicQos(1);
        DefaultConsumer consumer = new DefaultConsumer(channel){//定义队列的消费者
            //获取消息并处理，这个方法类似事件监听，如果有消息的时候会自动进行调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                try {
                    String msg = new String(body);//获取消息体
                    Thread.sleep(1000);//线程睡眠一秒
                    System.out.println("[消费者1] Received1 :"+ msg + "!");
                    channel.basicAck(envelope.getDeliveryTag(),false);//手动ACK
                } catch (InterruptedException e) {
                    //  第三个参数  false  直接删除消息   true  表示 ：把消息重回队列
                    channel.basicNack(envelope.getDeliveryTag(), false,true);
                }
            }
        };
        //继续监听队列
        channel.basicConsume(Send.WORK_QUEUE,false,consumer);
    }
}
