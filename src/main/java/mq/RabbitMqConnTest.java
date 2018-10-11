package mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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
		ConnectionFactory factory = getFactory();
		Connection connection;
		Channel channel ;

//		connection = factory.newConnection();
//		channel = connection.createChannel();
//		RabbitProducer producer = new RabbitProducer();
//		producer.produce(channel, QUEUE_NAME, "Hello");
//
//		channel.close();
//		connection.close();

		connection = factory.newConnection();
		channel = connection.createChannel();
		RabbitConsumer consumer = new RabbitConsumer();
		consumer.consume(channel, QUEUE_NAME);

		channel.close();
		connection.close();
	}

	private static ConnectionFactory getFactory() {
		ConnectionFactory factory = new ConnectionFactory();

		factory.setHost("39.105.8.99");
		// 需要在rabbitmq的配置中增加该用户并赋予权限，默认的用户guest不允许远程访问
		factory.setUsername("admin");
		factory.setPassword("admin");
		factory.setPort(5672);
		factory.setVirtualHost("/");

		return factory;
	}
}
