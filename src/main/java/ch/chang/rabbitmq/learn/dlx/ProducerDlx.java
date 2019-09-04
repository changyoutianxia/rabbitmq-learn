package ch.chang.rabbitmq.learn.dlx;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerDlx {
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

        String hello = "helloWorld dlx ";

        String routingKey = "test_dlx.routing";

        String exchangeName = "test_dlx_exchange";
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties();
        basicProperties.setExpiration("50000");
        channel.basicPublish(exchangeName, routingKey, true, basicProperties, hello.getBytes());

    }
}
