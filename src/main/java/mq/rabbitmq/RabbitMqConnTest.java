package mq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author ZJY
 * @ClassName: RabbitMqConnTest
 * @Description: RabbitMqConnTest
 * @date 2018/10/9 20:38
 */
public class RabbitMqConnTest {

	private static final String QUEUE_NAME = "Test";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = RabbitConnectionUtil.getConnection();
		Channel channel ;

//		channel = connection.createChannel();
//		RabbitProducer producer = new RabbitProducer();
//		producer.produce(channel, QUEUE_NAME, "Hello");
//
//		channel.close();
//		connection.close();

		channel = connection.createChannel();
		RabbitConsumer consumer = new RabbitConsumer();
		consumer.consume(channel, QUEUE_NAME);

		channel.close();
		connection.close();
	}

}
