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

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();

		factory.setHost("39.105.8.99");
		factory.setUsername("admin");
		factory.setPassword("admin");
		factory.setPort(15672);
		factory.setVirtualHost("/");
		// 得到连接
		Connection connection = factory.newConnection();
		//创建 channel实例
		Channel channel = connection.createChannel();
	}
}
