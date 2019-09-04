package ch.chang.rabbitmq.learn.qos;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.springframework.amqp.core.ExchangeTypes;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 固定key
 */
public class ConsumerQos {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.220");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String routingKey = "qos.*";
        String exchangeName = "qos_exchange";
        String queueName = "qos_queue";
        /*声明一个exchange*/
        channel.exchangeDeclare(exchangeName, ExchangeTypes.TOPIC, true, true, null);
        /*声明一个队列*/
        channel.queueDeclare(queueName, true, false, false, null);

        /*绑定*/
        channel.queueBind(queueName, exchangeName, routingKey, null);
        /*消息大小　每次接受几个　绑定到consumer*/
        channel.basicQos(0, 1, false);
        channel.basicConsume(queueName, false, new MyConsumer(channel));

    }
}
