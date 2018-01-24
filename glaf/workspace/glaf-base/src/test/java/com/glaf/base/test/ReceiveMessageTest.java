package com.glaf.base.test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.domain.ServerEntity;
import com.glaf.core.mq.rabbitmq.MQConnectionFactory;
import com.glaf.core.util.ContextUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

/**
 * 通过其他系统通过MQ登录到本系统的消息检测Job 设置服务器端登录配置:/mx/sys/loginModule?type=server
 */
public class ReceiveMessageTest {
	protected static final Log logger = LogFactory.getLog(ReceiveMessageTest.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected static AtomicBoolean running = new AtomicBoolean(false);

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	public static void main(String[] args) throws Exception {
		ServerEntity serverEntity = new ServerEntity();
		serverEntity.setHost(TestConstants.HOST);
		serverEntity.setPort(5672);
		serverEntity.setUser(TestConstants.USER);
		serverEntity.setPassword(TestConstants.PWD);
		serverEntity.setDbname(TestConstants.QNAME);
		String loginQueueName = serverEntity.getDbname();
		if (!ContextUtils.contains(loginQueueName)) {
			Channel channel = MQConnectionFactory.getInstance().getChannel(serverEntity);
			try {
				channel.exchangeDeclare(serverEntity.getDbname(), "fanout");
			} catch (Throwable ex) {
				channel.exchangeDeclare(serverEntity.getDbname(), "fanout", true);
			}
			String queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, serverEntity.getDbname(), "");

			logger.debug(" [*] Waiting for login messages...");

			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume(queueName, true, consumer);

			ContextUtils.put(queueName, queueName);
			//LoginMessageConsumer messageConsumer = new LoginMessageConsumer();

			while (true) {
				try {
					QueueingConsumer.Delivery delivery = consumer.nextDelivery();
					logger.debug("envelope:" + delivery.getEnvelope());
					String message = new String(delivery.getBody(), "UTF-8");
					logger.debug("Receive Msg:" + message);
					//messageConsumer.processMessage(message, serverEntity);
				} catch (Exception ex) {
					logger.error(ex);
				}
			}
		}
	}

}
