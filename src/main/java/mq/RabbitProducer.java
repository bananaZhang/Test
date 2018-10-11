package mq;

import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * @author ZJY
 * @ClassName: RabbitProducer
 * @Description: RabbitProducer
 * @date 2018/10/11 14:01
 */
public class RabbitProducer {

	public void produce(Channel channel, String queueName, String msg) throws IOException {
		if (!channel.isOpen()) {
			return;
		}
		// 声明一个队列
		channel.queueDeclare(queueName, false, false, false, null);
		// 发布消息到队列中
		channel.basicPublish("", queueName, null, msg.getBytes("UTF-8"));
		System.out.println("Productor send msg:" + msg);
	}
}
