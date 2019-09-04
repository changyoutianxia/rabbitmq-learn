package ch.chang.rabbitmq.learn.qos;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerQos {
    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.220");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        //创建一个链接
        Connection connection = connectionFactory.newConnection();
        //创建一个信道
        Channel channel = connection.createChannel();
        //发送的消息

        String hello = "helloWorld qos ";

        String routingKey = "qos.routing";

        String exchangeName = "qos_exchange";

        channel.basicPublish(exchangeName, routingKey, true, null, hello.getBytes());
        channel.basicPublish(exchangeName, routingKey, true, null, hello.getBytes());
        channel.basicPublish(exchangeName, routingKey, true, null, hello.getBytes());

    }
}
