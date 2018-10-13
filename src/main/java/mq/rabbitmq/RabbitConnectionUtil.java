package mq.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author ZJY
 * @ClassName: RabbitConnectionUtil
 * @Description: RabbitConnectionUtil
 * @date 2018/10/12 15:49
 */
public class RabbitConnectionUtil {
	public static Connection getConnection() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();

		factory.setHost("39.105.8.99");
		// 需要在rabbitmq的配置中增加该用户并赋予权限，默认的用户guest不允许远程访问
		factory.setUsername("admin");
		factory.setPassword("admin");
		factory.setPort(5672);
		factory.setVirtualHost("/");

		return factory.newConnection();
	}
}
