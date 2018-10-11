package mq;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author ZJY
 * @ClassName: RabbitConsumer
 * @Description: RabbitConsumer
 * @date 2018/10/11 14:01
 */
public class RabbitConsumer {

	public void consume(Channel channel, String queueName) throws IOException {
		if (!channel.isOpen()) {
			return;
		}
		// 声明要关注的队列
		channel.queueDeclare(queueName, false, false, false, null);
		System.out.println("Consumer waiting for message");
		// DefaultConsumer类实现了Consumer接口，通过传入一个频道，
		// 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String msg = new String(body, "UTF-8");
				System.out.println("Consumer receive message:" + msg);
			}
		};
		// 自动回复队列应答 -- RabbitMQ中的消息确认机制
		channel.basicConsume(queueName, true, consumer);
	}
}
