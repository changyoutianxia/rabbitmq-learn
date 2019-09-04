package ch.chang.rabbitmq.learn.exchange.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerExchangeFanout {
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

        String exchangeName = "fanout_exchange";
        //匹配多个单词
        //和这个key已经无关
        String routingKey1 = "fanout.user";
        String routingKey2 = "fanout.admin";
        String routingKey3 = "fanout.user.zhangsan";
        String routingKey4 = "fanout.user.tl.yy";

        String hello = "helloWorld";

        channel.basicPublish(exchangeName, routingKey1, null, hello.getBytes());
        channel.basicPublish(exchangeName, routingKey2, null, hello.getBytes());
        channel.basicPublish(exchangeName, routingKey3, null, hello.getBytes());
        channel.basicPublish(exchangeName, routingKey4, null, hello.getBytes());

        channel.close();
        connection.close();
    }
}
