package mq.rabbitmq;

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
		// https://blog.csdn.net/a491857321/article/details/50583753
		// 默认情况下，RabbitMQ将逐个发送消息到在序列中的下一个消费者(而不考虑每个任务的时长等等，且是提前一次性分配，并非一个一个分配)。
		// 平均每个消费者获得相同数量的消息。这种方式分发消息机制称为Round-Robin（轮询）
		// 每次从队列获取的数量，这句代码表示服务器同一时刻只会发送一条消息给消费者，但并不指定是哪个消费者，当收到反馈后，才会进行第二次发送
		// 处理能力高的人会接收到更多的消息，注释掉这段代码的话会导致两个处理能力不一样的消费者消费的数量是相等的
		channel.basicQos(1);
		// DefaultConsumer类实现了Consumer接口，通过传入一个频道，
		// 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String msg = new String(body, "UTF-8");
				System.out.println("Consumer receive message:" + msg);
			}
		};
		// RabbitMQ中的消息确认机制：autoAck是否自动回复，如果为true的话，每次生产者只要发送信息就会从内存中删除，
		// 那么如果消费者程序异常退出，那么就无法获取数据，我们当然是不希望出现这样的情况，所以才去手动回复，
		// 每当消费者收到并处理信息然后在通知生成者。最后从队列中删除这条信息。如果消费者异常退出，如果还有其他消费者，
		// 那么就会把队列中的消息发送给其他消费者，如果没有，等消费者启动时候再次发送
		channel.basicConsume(queueName, true, consumer);
	}
}
