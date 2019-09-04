package ch.chang.rabbitmq.learn.listener.returnListener;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerListener {
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

        String hello = "helloWorld return Listener";

        String routingKey = "return.routing";
        String routingKeyNot = "return_not.routing";

        String exchangeName = "return_exchange";

        channel.basicPublish(exchangeName, routingKey, true, null, hello.getBytes());

/*
        channel.basicPublish(exchangeName, routingKeyNot, true, null, hello.getBytes());
*/

        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(replyCode);
                System.out.println(replyText);
                System.out.println(exchange);
                System.out.println(routingKey);
                System.out.println(properties);
                System.out.println(new String(body));
            }
        });

    }
}
