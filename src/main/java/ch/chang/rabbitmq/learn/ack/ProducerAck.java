package ch.chang.rabbitmq.learn.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class ProducerAck {
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


        String routingKey = "ack.routing";

        String exchangeName = "ack_exchange";
        for (int i = 0; i < 5; i++) {
            String hello = "helloWorld ack " + i;
            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties();
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("num", i);
            basicProperties.setHeaders(stringObjectHashMap);
            channel.basicPublish(exchangeName, routingKey, true, basicProperties, hello.getBytes());
        }

    }
}
