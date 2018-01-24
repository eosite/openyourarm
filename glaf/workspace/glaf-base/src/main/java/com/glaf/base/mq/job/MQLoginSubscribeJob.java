package com.glaf.base.mq.job;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ServerEntity;
import com.glaf.core.job.BaseJob;
import com.glaf.core.mq.rabbitmq.MQConnectionFactory;
import com.glaf.core.service.IServerEntityService;
import com.glaf.core.util.ContextUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.mq.LoginMessageConsumer;

public class MQLoginSubscribeJob extends BaseJob {

	protected static Configuration conf = BaseConfiguration.create();

	protected static AtomicBoolean running = new AtomicBoolean(false);

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < DateUtils.MINUTE * 1) {
			return;
		}
		if (!running.get()) {
			try {
				running.set(true);
				String loginQueueName = SystemConfig.getString("login_queue_name");
				if (StringUtils.isNotEmpty(loginQueueName)) {
					if (!ContextUtils.contains(loginQueueName)) {
						IServerEntityService serverEntityService = ContextFactory.getBean("serverEntityService");
						ServerEntity serverEntity = serverEntityService.getServerEntityByMapping(loginQueueName);
						if (serverEntity != null) {
							Channel channel = MQConnectionFactory.getInstance().getChannel(serverEntity);
							try {
								channel.exchangeDeclare(serverEntity.getDbname(), "fanout");
							} catch (Throwable ex) {
								channel.exchangeDeclare(serverEntity.getDbname(), "fanout", true);
							}
							String queueName = channel.queueDeclare().getQueue();
							channel.queueBind(queueName, serverEntity.getDbname(), "");

							logger.debug(" [*] Waiting for messages...");

							QueueingConsumer consumer = new QueueingConsumer(channel);
							channel.basicConsume(queueName, true, consumer);

							ContextUtils.put(queueName, queueName);
							LoginMessageConsumer bean = new LoginMessageConsumer();

							while (true) {
								try {
									QueueingConsumer.Delivery delivery = consumer.nextDelivery();
									logger.debug("envelope:" + delivery.getEnvelope());
									String message = new String(delivery.getBody(), "UTF-8");
									logger.debug("Receive Msg:" + message);
									bean.processMessage(message, serverEntity);
								} catch (Exception ex) {
									logger.error(ex);
								}
							}

						} else {
							logger.error(loginQueueName + " queue not config!!!");
						}
					}
				} else {
					logger.error("loginQueueName is null!");
				}
			} catch (Exception ex) {
				logger.error(ex);
			} finally {
				running.set(false);
			}
		}
	}

	public void runJob(JobExecutionContext context) throws JobExecutionException {

	}

}
