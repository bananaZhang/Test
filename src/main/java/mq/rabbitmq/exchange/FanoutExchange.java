package mq.rabbitmq.exchange;

import com.rabbitmq.client.*;
import mq.rabbitmq.RabbitConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author ZJY
 * @ClassName: FanoutExchange
 * @Description: 订阅模式(Fanout Exchange)：一个生产者，多个消费者，每一个消费者都有自己的一个队列，
 * 生产者没有将消息直接发送到队列，而是发送到了交换机，每个队列绑定交换机，生产者发送的消息经过交换机，
 * 到达队列，实现一个消息被多个消费者获取的目的。需要注意的是，如果将消息发送到一个没有队列绑定的exchange上面，
 * 那么该消息将会丢失，这是因为在rabbitMQ中exchange不具备存储消息的能力，只有队列具备存储消息的能力。
 * @date 2018/10/12 15:46
 */
public class FanoutExchange {

	private static final String EXCHANGE_NAME = "exchange_fanout";

	public static void main(String[] args) {
		FanoutExchange.Provider provider = new FanoutExchange.Provider();
		FanoutExchange.Consumer consumer1 = new FanoutExchange.Consumer();
		FanoutExchange.Consumer consumer2 = new FanoutExchange.Consumer();

		new Thread(() -> {
			try {
				provider.provide("Fanout message");
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
		void provide(String msg) throws IOException, TimeoutException {
			Connection connection = RabbitConnectionUtil.getConnection();
			Channel channel = connection.createChannel();
			// 声明exchange，并采用订阅模式
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
			// 这里暂停2秒的原因是等待消费者的队列绑定到exchange上面去，否则生产者发布了消息到exchange，
			// 但是没有消费者，后面消费者注册了队列到exchange，也不会得到消息的推送了
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes("UTF-8"));

			System.out.println("Provider produce msg:" + msg);

			channel.close();
			connection.close();
		}
	}

	static class Consumer {
		void consume() throws IOException, TimeoutException {
			Connection connection = RabbitConnectionUtil.getConnection();
			Channel channel = connection.createChannel();
			//产生一个随机的队列名称
			String queueName = channel.queueDeclare().getQueue();
			System.out.println("queueName is " + queueName);
			// 声明队列
//			channel.queueDeclare(queueName, false, false, false, null);
			// 绑定队列到交换机
			channel.queueBind(queueName, EXCHANGE_NAME, "");

			com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
					String msg = new String(body, "UTF-8");
					System.out.println("Consumer receive message:" + msg);
				}
			};

			channel.basicConsume(queueName, true, consumer);
		}
	}

}
