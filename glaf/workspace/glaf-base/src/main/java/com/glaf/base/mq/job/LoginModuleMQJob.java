package com.glaf.base.mq.job;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

import com.glaf.core.config.Configuration;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ServerEntity;
import com.glaf.core.job.BaseJob;
import com.glaf.core.mq.rabbitmq.MQConnectionFactory;
import com.glaf.core.util.ContextUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.sys.model.LoginModule;
import com.glaf.base.modules.sys.query.LoginModuleQuery;
import com.glaf.base.modules.sys.service.LoginModuleService;
import com.glaf.base.mq.LoginMessageConsumer;

/**
 * 通过其他系统通过MQ登录到本系统的消息检测Job 设置服务器端登录配置:/mx/sys/loginModule?type=server
 */
public class LoginModuleMQJob extends BaseJob {

	protected static Configuration conf = BaseConfiguration.create();

	protected static AtomicBoolean running = new AtomicBoolean(false);

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < DateUtils.MINUTE * 1) {
			return;
		}
		logger.debug("--------------------LoginModuleMQJob-----------------------------");
		if (!running.get()) {
			try {
				running.set(true);
				LoginModuleService loginModuleService = ContextFactory.getBean("loginModuleService");
				LoginModuleQuery query = new LoginModuleQuery();
				query.type("server");
				query.locked(0);
				List<LoginModule> list = loginModuleService.list(query);
				if (list != null && !list.isEmpty()) {
					for (LoginModule bean : list) {
						if (bean.getServerEntity() != null
								&& StringUtils.equals(bean.getServerEntity().getType(), "rabbitmq")) {
							ServerEntity serverEntity = bean.getServerEntity();
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
								logger.debug(bean.getTitle());
								logger.debug(" [*] Waiting for login messages...");

								QueueingConsumer consumer = new QueueingConsumer(channel);
								channel.basicConsume(queueName, true, consumer);

								ContextUtils.put(queueName, queueName);
								LoginMessageConsumer messageConsumer = new LoginMessageConsumer();

								while (true) {
									try {
										QueueingConsumer.Delivery delivery = consumer.nextDelivery();
										logger.debug("envelope:" + delivery.getEnvelope());
										String message = new String(delivery.getBody(), "UTF-8");
										logger.debug("Receive Msg:" + message);
										messageConsumer.processMessage(message, serverEntity);
									} catch (Exception ex) {
										logger.error(ex);
									}
								}
							}
						}
					}
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
