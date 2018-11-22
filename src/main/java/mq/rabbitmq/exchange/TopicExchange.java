package mq.rabbitmq.exchange;

import com.rabbitmq.client.*;
import mq.rabbitmq.RabbitConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author ZJY
 * @ClassName: TopicExchange
 * @Description: 基本思想和路由模式是一样的，只不过路由键支持模糊匹配，符号"#"匹配一个或多个词，符号"*"匹配不多不少一个词
 * @date 2018/11/19 20:19
 */
public class TopicExchange {

    private static final String EXCHANGE_NAME = "topic";

    public static void main(String[] args) {
        TopicExchange.Provider provider = new TopicExchange.Provider();
        TopicExchange.Consumer1 consumer1 = new TopicExchange.Consumer1();
        TopicExchange.Consumer2 consumer2 = new TopicExchange.Consumer2();

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
            //待发送的消息
            String[] routingKeys = new String[]{
                    "quick.orange.rabbit",
                    "lazy.orange.elephant",
                    "quick.orange.fox",
                    "lazy.brown.fox",
                    "quick.brown.fox",
                    "quick.orange.male.rabbit",
                    "lazy.orange.male.rabbit"
            };
            Connection connection = RabbitConnectionUtil.getConnection();
            Channel channel = connection.createChannel();
            // 声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            // 这里暂停2秒的原因是等待消费者的队列绑定到exchange上面去，否则生产者发布了消息到exchange，
            // 但是没有消费者，后面消费者注册了队列到exchange，也不会得到消息的推送了
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (String routingKey : routingKeys) {
                String msg = "productor send topic: " + routingKey;
                channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
                System.out.println("TopicSend Send" + routingKey + "':'" + msg);
            }
            channel.close();
            connection.close();
        }
    }


    static class Consumer1 {
        void consume() throws IOException, TimeoutException {
            // 路由关键字
            String[] routingKeys = new String[]{"*.orange.*"};
            Connection connection = RabbitConnectionUtil.getConnection();
            Channel channel = connection.createChannel();
            // 声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
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
            String[] routingKeys = new String[]{"*.*.rabbit", "lazy.#"};
            Connection connection = RabbitConnectionUtil.getConnection();
            Channel channel = connection.createChannel();
            // 声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
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
