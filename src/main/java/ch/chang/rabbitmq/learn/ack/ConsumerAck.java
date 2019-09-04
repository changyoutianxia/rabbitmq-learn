package ch.chang.rabbitmq.learn.ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.ExchangeTypes;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 固定key
 */
public class ConsumerAck {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.220");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String routingKey = "ack.*";
        String exchangeName = "ack_exchange";
        String queueName = "ack_queue";
        /*声明一个exchange*/
        channel.exchangeDeclare(exchangeName, ExchangeTypes.TOPIC, true, true, null);
        /*声明一个队列*/
        channel.queueDeclare(queueName, true, false, false, null);

        /*绑定*/
        channel.queueBind(queueName, exchangeName, routingKey, null);
        channel.basicConsume(queueName, false, new MyConsumer(channel));

    }
}
