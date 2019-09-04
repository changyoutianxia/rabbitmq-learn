package ch.chang.rabbitmq.learn.dlx;

import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.ExchangeTypes;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * 固定key
 */
public class ConsumerDlx {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.220");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();


        String routingKey = "test_dlx.#";
        String exchangeName = "test_dlx_exchange";
        String queueName = "test_dlx_queue";


        /*声明一个exchange*/

        channel.exchangeDeclare(exchangeName, ExchangeTypes.TOPIC, true, false, null);

        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("x-dead-letter-exchange", "dlx.exchange");
        objectObjectHashMap.put("x-message-ttl",6000);
        /*声明一个队列*/
        channel.queueDeclare(queueName, true, false, false, objectObjectHashMap);
        /*绑定*/
        channel.queueBind(queueName, exchangeName, routingKey);



        /*死信队列*/
        channel.exchangeDeclare("dlx.exchange", ExchangeTypes.TOPIC, true, false, null);
        channel.queueDeclare("dlx.queue", true, false, false, null);
        channel.queueBind("dlx.queue", "dlx.exchange", "#");



        /*消息大小　每次接受几个　绑定到consumer*/
        channel.basicQos(0, 1, false);
        channel.basicConsume(queueName, false, new MyConsumer(channel));

    }
}
