package ch.chang.rabbitmq.learn.exchange.direct;

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
public class ConsumerExchangeDirect {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.220");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String queueName = "hello";

        String exchangeName = "test";
        String routingKey = "test_routing";
        /*声明一个exchange*/
        channel.exchangeDeclare(exchangeName, ExchangeTypes.DIRECT, true, true, null);
        /*声明一个队列*/
        channel.queueDeclare(queueName, true, false, false, null);

        /*绑定*/
        channel.queueBind(queueName, exchangeName, routingKey, null);

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, queueingConsumer);
        //获取数据
        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            System.out.println(new String(delivery.getBody()));
        }
    }
}
