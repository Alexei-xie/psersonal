package com.itself.rabbitmq.demo02;

import com.itself.utils.ConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 *  两个消费者同时监听一个队列，无论消费者消费效率如何，二者都是平均数消费
 *  如果想要消费效率快的线程进行多消费：让消费者同一时间只接收一条消息，这样处理完成之前，就不会接收更多消息，就可以让处理快的人，接收更多消息
 * @Author xxw
 * @Date 2022/08/28
 */
public class Send {
    
    public static final String WORK_QUEUE = "work-queue";
    
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectUtil.getConnection(); //获取到连接
        Channel channel = connection.createChannel(); //获取通道
        //声明队列
        channel.queueDeclare(WORK_QUEUE,false,false,false,null);
        for (int i = 0; i < 50; i++) {//通过循环方式来发布任务
            String msg = "task..."+ i;//消息内容
            //向指定的队列中发送消息
            channel.basicPublish("",WORK_QUEUE,null,msg.getBytes());
            System.out.println("[X] send '"+ msg + "'");
        }
        channel.close();//关闭通道
        connection.close();//关闭连接
    }
}
