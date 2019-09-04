package ch.chang.rabbitmq.learn.helloword;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.220");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        //创建一个链接
        Connection connection = connectionFactory.newConnection();
        //创建一个信道
        Channel channel = connection.createChannel();
        String hello = "helloWorld";
        String queueName = "hello";
        for (int i = 0; i < 10; i++) {
            /*

            exchange
            routingKey  队列名字
            * */
            //发送消息
            channel.basicPublish("", queueName, null, hello.getBytes());
        }
        channel.close();
        connection.close();
    }
}
