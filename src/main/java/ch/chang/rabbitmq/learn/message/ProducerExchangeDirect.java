package ch.chang.rabbitmq.learn.message;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class ProducerExchangeDirect {
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
        String hello = "message.direct";

        String routingKey = "message.direct.key";

        String exchangeName = "message.direct";

        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties();
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("name", "张三");
        basicProperties.setHeaders(stringStringHashMap);
        for (int i = 0; i < 5; i++) {
            /*
            exchange
            routingKey  队列名字
            * */
            //发送消息
            channel.basicPublish(exchangeName, routingKey, basicProperties, hello.getBytes());
        }
        channel.close();
        connection.close();
    }
}
