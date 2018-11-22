package mq.rabbitmq.exchange;

import com.rabbitmq.client.*;
import mq.rabbitmq.RabbitConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author ZJY
 * @ClassName: RoutingExchange
 * @Description: 这种模式添加了一个路由键，生产者发布消息的时候添加路由键，消费者绑定队列到交换机时添加键值，
 * 这样就可以接收到需要接收的消息
 * @date 2018/11/19 20:00
 */
public class RoutingExchange {

    private static final String EXCHANGE_NAME = "direct";

    public static void main(String[] args) {
        RoutingExchange.Provider provider = new RoutingExchange.Provider();
        RoutingExchange.Consumer1 consumer1 = new RoutingExchange.Consumer1();
        RoutingExchange.Consumer2 consumer2 = new RoutingExchange.Consumer2();

        new Thread(() -> {
            try {
                provider.provide();
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {
            try {
                consumer1.consume();
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                consumer2.consume();
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }).start();
    }

    static class Provider {
        void provide() throws IOException, TimeoutException {
            // 路由关键字
            String[] routingKeys = new String[]{"info", "warning", "error"};
            Connection connection = RabbitConnectionUtil.getConnection();
            Channel channel = connection.createChannel();
            // 声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            // 这里暂停2秒的原因是等待消费者的队列绑定到exchange上面去，否则生产者发布了消息到exchange，
            // 但是没有消费者，后面消费者注册了队列到exchange，也不会得到消息的推送了
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (String routingKey : routingKeys) {
                String msg = "RoutingSendDirect send the message level: " + routingKey;
                channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
                System.out.println("RoutingSendDirect Send" + routingKey + "':'" + msg);
            }
            channel.close();
            connection.close();
        }
    }

    static class Consumer1 {
        void consume() throws IOException, TimeoutException {
            // 路由关键字
            String[] routingKeys = new String[]{"info", "warning"};
            Connection connection = RabbitConnectionUtil.getConnection();
            Channel channel = connection.createChannel();
            // 声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            // 获取匿名队列名称
            String queueName = channel.queueDeclare().getQueue();
            System.out.println("queueName is " + queueName);

            // 根据路由关键字进行绑定
            for (String routingKey : routingKeys) {
                channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
                System.out.println("Consumer1 exchange: " + EXCHANGE_NAME + "," +
                        " queue: " + queueName + ",BindRoutingKey: " + routingKey);
            }
            System.out.println("Consumer1 is waiting for message");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body, "UTF-8");
                    System.out.println("Consumer1 Received '" + envelope.getRoutingKey() + "':'" + msg + "'");
                }
            };

            channel.basicConsume(queueName, true, consumer);
        }
    }

    static class Consumer2 {
        void consume() throws IOException, TimeoutException {
            // 路由关键字
            String[] routingKeys = new String[]{"error"};
            Connection connection = RabbitConnectionUtil.getConnection();
            Channel channel = connection.createChannel();
            // 声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            // 获取匿名队列名称
            String queueName = channel.queueDeclare().getQueue();
            System.out.println("queueName is " + queueName);

            // 根据路由关键字进行绑定
            for (String routingKey : routingKeys) {
                channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
                System.out.println("Consumer2 exchange: " + EXCHANGE_NAME + "," +
                        " queue: " + queueName + ",BindRoutingKey: " + routingKey);
            }
            System.out.println("Consumer2 is waiting for message");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body, "UTF-8");
                    System.out.println("Consumer2 Received '" + envelope.getRoutingKey() + "':'" + msg + "'");
                }
            };

            channel.basicConsume(queueName, true, consumer);
        }
    }
}
