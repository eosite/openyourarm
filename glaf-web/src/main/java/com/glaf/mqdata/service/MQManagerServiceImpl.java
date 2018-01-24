package com.glaf.mqdata.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.glaf.core.config.Environment;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.ServerEntity;
import com.glaf.core.query.ServerEntityQuery;
import com.glaf.core.security.SecurityUtils;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.IServerEntityService;
import com.glaf.maildata.domain.EmailSend;
import com.glaf.maildata.domain.EmailSendAtt;
import com.glaf.maildata.service.EmailRecService;
import com.glaf.maildata.service.EmailSendAttService;
import com.glaf.maildata.service.EmailSendService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

@Service("com.glaf.mqdata.service.mqManagerServiceImpl")
@Transactional(readOnly = true)
public class MQManagerServiceImpl implements MQManagerService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected IDatabaseService databaseService;
	protected IServerEntityService serverEntityService;
	protected EmailSendService emailSendService;
	protected EmailSendAttService emailSendAttService;
	protected EmailRecService emailRecService;

	public void createMQConnectionProperties() {
		// 获取标段关联MQ服务器配置
		CachingConnectionFactory cf = new CachingConnectionFactory();
		cf.setAddresses("192.168.10.122");
		cf.setUsername("adminmq");
		cf.setPort(5672);
		cf.setPassword("mtxx87668438");
		// set up the queue, exchange, binding on the broker
		RabbitAdmin admin = new RabbitAdmin(cf);
		Queue queue = new Queue("myQueue");
		admin.declareQueue(queue);
		// TopicExchange exchange = new TopicExchange("myExchange");
		// admin.declareExchange(exchange);
		// admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("foo.*"));
		DirectExchange exchange = new DirectExchange("myExchange");
		admin.declareExchange(exchange);
		admin.declareBinding(BindingBuilder.bind(queue).to(exchange).withQueueName());
		// set up the listener and container
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cf);
		Object listener = new Object() {
			public void handleMessage(String foo) {
				System.out.println(foo);
			}
		};
		MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
		container.setMessageListener(adapter);
		container.setQueueNames("myQueue");
		container.start();
		// send something
		RabbitTemplate template = new RabbitTemplate(cf);
		template.convertAndSend("myExchange", "foo.bar", "Hello, world!");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		container.stop();
		// 检测队列是否创建，没创建则创建，并创建管理用户
	}

	public void sendDataToMQ() {

		// 获取系统MQ服务器
		Map<String, Database> sysDatabaseMap = getSysDatabaseMap();
		// 获取MQ服务器列表
		Map<Long, ServerEntity> mqServerMap = getMQServerMap();
		// 队列名称
		String queueName = null;
		// MQ服务器ID
		Long serverId = null;
		// MQ服务器配置
		ServerEntity serverEntity = null;
		// 获取数据库连接代码
		String dbCode = null;
		// 当前默认库
		String defaultDatabse = Environment.DEFAULT_SYSTEM_NAME;
		Database database = null;
		String sysId = null;
		for (Entry<String, Database> sysDatabase : sysDatabaseMap.entrySet()) {
			database = sysDatabase.getValue();
			sysId = sysDatabase.getKey();
			if (StringUtils.isEmpty(sysId)) {
				logger.info("系统ID标识未指定！");
				continue;
			}
			queueName = database.getQueueName();
			serverId = database.getServerId();
			if (StringUtils.isEmpty(queueName) || serverId == null || serverId == 0L) {
				logger.info("系统" + sysId + " " + database.getName() + "消息队列名称未配置或消息队列服务器未指定！");
				continue;
			}
			serverEntity = mqServerMap.get(serverId);
			if (serverEntity == null) {
				logger.info("系统" + sysId + " " + database.getName() + "指定MQ服务器配置为空！");
				continue;
			}
			dbCode=database.getCode();
			sendDbImpDataToMQ(dbCode, sysDatabaseMap, mqServerMap);
		}
		// 切换当前数据库连接为默认库
		Environment.setCurrentSystemName(defaultDatabse);

	}

	/**
	 * 针对单个系统发送信息到消息队列
	 * 
	 * @param dbCode
	 * @param sysDatabaseMap
	 * @param mqServerMap
	 */
	public void sendDbImpDataToMQ(String dbCode, Map<String, Database> sysDatabaseMap,
			Map<Long, ServerEntity> mqServerMap) {
		// 切换当前数据库连接到dbCode对应的数据库下
		Environment.setCurrentSystemName(dbCode);
		// 获取待发送接口数据列表
		List<EmailSend> emailSendList = emailSendService.getNeedSendEmails();
		if(emailSendList==null||emailSendList.size()==0)
		{
			return;
		}
		// 获取邮件附件
		List<EmailSendAtt> emailSendAttrs = null;
		// 接口数据描述内容
		String xmlDesc = null;
		String recSysId = null;
		byte[] dataPackage = null;
		Long serverId = null;
		ServerEntity serverEntity = null;
		String queueName = null;
		Database database = null;
		String subject=null;
		for (EmailSend emailSend : emailSendList) {
			xmlDesc = emailSend.getText();
			if (StringUtils.isEmpty(xmlDesc)) {
				xmlDesc = emailSend.getHtml();
			}
			if (StringUtils.isEmpty(xmlDesc)) {
				logger.info(emailSend.getId() + "描述文件为空！");
				continue;
			}
			// 获取接收方系统信息
			recSysId = emailSend.getToSysId();
			// 获取发送方MQ服务器配置
			if (!sysDatabaseMap.containsKey(recSysId)) {
				logger.info("不存在系统" + recSysId + "配置");
				continue;
			}
			database = sysDatabaseMap.get(recSysId);
			serverId = database.getServerId();
			if (serverId == null) {
				logger.info("系统" + recSysId + "MQ服务器关联未设置");
				continue;
			}
			queueName = database.getQueueName();
			if (StringUtils.isEmpty(queueName)) {
				logger.info("系统" + recSysId + "消息队列名未设置");
				continue;
			}
			serverEntity = mqServerMap.get(serverId);
			if (serverEntity == null) {
				logger.info("系统" + recSysId + "MQ服务器配置不存在");
				continue;
			}
			// 获取邮件附件信息
			emailSendAttrs = emailSendAttService.getEmailSendAttsByMailId(emailSend.getId());
			// 数据封包
			try {
				subject=emailSend.getSubject();
				dataPackage = emailSendService.generateSendDataPackage(subject,xmlDesc, emailSendAttrs);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				logger.error("发送数据【ID=" + emailSend.getId() + "】到系统" + recSysId + ",封装数据包出错!");
				break;
			}
			// 发送数据到MQ
			try {
				String password = SecurityUtils.decode(database.getKey(), database.getPassword());
	            password = DigestUtils.sha1Hex(password);
				sendMessageToMQ(serverEntity, queueName, database.getSysId(), password, dataPackage);
				// 更新接口数据发送状态为已发送
				emailSendService.updateEmailOperationStatus(emailSend.getId(), 1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("发送数据到系统" + recSysId + "消息队列名出错!");
				break;
			}
		}
	}

	/**
	 * 发送消息到队列
	 * 
	 * @param serverEntity
	 * @param queueName
	 * @param userName
	 *            登录MQ用户名
	 * @param passWord
	 *            登录MQ密码
	 * @param data
	 * @throws Exception
	 */
	public void sendMessageToMQ(ServerEntity serverEntity, String queueName, String userName, String passWord,
			byte[] data) throws Exception {
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost(serverEntity.getHost());
		cf.setUsername(userName);
		cf.setPort(serverEntity.getPort());
		cf.setPassword(passWord);
		Connection connection = null;
		// 下一步我们创建一个channel, 通过这个channel就可以完成API中的大部分工作了。
		Channel channel = null;
		// 为了发送消息, 我们必须声明一个队列，来表示我们的消息最终要发往的目的地。
		try {
			connection = cf.newConnection();
			// 下一步我们创建一个channel, 通过这个channel就可以完成API中的大部分工作了。
			channel = connection.createChannel();
			channel.queueDeclare(queueName, true, false, false, null);
			// 然后我们将一个消息发往这个队列。
			channel.basicPublish("", queueName, null, data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			throw e;

		} finally {
			// 最后，我们关闭channel和连接，释放资源。
			try {
				channel.close();
				connection.close();
			} catch (IOException | TimeoutException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
				throw e;
			}

		}

	}

	

	/**
	 * 获取MQ服务器列表
	 * 
	 * @return
	 */
	public Map<Long, ServerEntity> getMQServerMap() {
		Map<Long, ServerEntity> serverMap = new HashMap<Long, ServerEntity>();
		// 获取服务器列表
		ServerEntityQuery query = new ServerEntityQuery();
		query.setType("rabbitmq");
		List<ServerEntity> servers = serverEntityService.list(query);
		for (ServerEntity serverEntity : servers) {
			serverMap.put(serverEntity.getId(), serverEntity);
		}
		return serverMap;
	}

	/**
	 * 获取系统数据库配置列表
	 * 
	 * @return
	 */
	public Map<String, Database> getSysDatabaseMap() {
		Map<String, Database> sysDatabaseMap = new HashMap<String, Database>();
		// 获取标段库配置
		List<Database> databases = databaseService.getDatabases();
		for (Database database : databases) {
			sysDatabaseMap.put(database.getSysId(), database);
		}
		return sysDatabaseMap;
	}

	public void receiveDataFromMQ() {
		// 获取系统MQ服务器
		Map<String, Database> sysDatabaseMap = getSysDatabaseMap();
		// 获取MQ服务器列表
		Map<Long, ServerEntity> mqServerMap = getMQServerMap();
		// 队列名称
		String queueName = null;
		// MQ服务器ID
		Long serverId = null;
		// MQ服务器配置
		ServerEntity serverEntity = null;
		// 获取数据库连接代码
		String dbCode = null;
		// 当前默认库
		String defaultDatabse = Environment.DEFAULT_SYSTEM_NAME;
		Database database = null;
		String sysId = null;
		for (Entry<String, Database> sysDatabase : sysDatabaseMap.entrySet()) {
			database = sysDatabase.getValue();
			sysId = sysDatabase.getKey();
			if (StringUtils.isEmpty(sysId)) {
				logger.info("系统ID标识未指定！");
				continue;
			}
			queueName = database.getQueueName();
			serverId = database.getServerId();
			if (StringUtils.isEmpty(queueName) || serverId == null || serverId == 0L) {
				logger.info("系统" + sysId + " " + database.getName() + "消息队列名称未配置或消息队列服务器未指定！");
				continue;
			}
			serverEntity = mqServerMap.get(serverId);
			if (serverEntity == null) {
				logger.info("系统" + sysId + " " + database.getName() + "指定MQ服务器配置为空！");
				continue;
			}
			try {
				receiveDataFromMQToDb(serverEntity, database);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("接收系统" + sysId + " " + database.getName() + "接口数据出错" + e.getMessage());
				continue;
			}
		}
		// 切换当前数据库连接为默认库
		Environment.setCurrentSystemName(defaultDatabse);

	}

	/**
	 * 从MQ获取数据到数据库
	 * 
	 * @param queueName
	 * @param serverEntity
	 * @param database
	 * @throws Exception
	 */
	public void receiveDataFromMQToDb(ServerEntity serverEntity, Database database) throws Exception {
		// 切换当前数据库连接到dbCode对应的数据库下
		Environment.setCurrentSystemName(database.getCode());
		String queueName = database.getQueueName();
		String userName = database.getSysId();
		String password = SecurityUtils.decode(database.getKey(), database.getPassword());
        password = DigestUtils.sha1Hex(password);
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost(serverEntity.getHost());
		cf.setUsername(userName);
		cf.setPort(serverEntity.getPort());
		cf.setPassword(password);
		Connection connection = null;
		// 下一步我们创建一个channel, 通过这个channel就可以完成API中的大部分工作了。
		Channel channel = null;
		// 为了发送消息, 我们必须声明一个队列，来表示我们的消息最终要发往的目的地。
		try {
			connection = cf.newConnection();
			// 下一步我们创建一个channel, 通过这个channel就可以完成API中的大部分工作了。
			channel = connection.createChannel();
			channel.queueDeclare(queueName, true, false, false, null);
			QueueingConsumer consumer = new QueueingConsumer(channel);
			// 自动通知标志 true 消息一旦被消费者收到，服务端就知道该消息已经投递，从而从队列中将消息剔除
			boolean autoAck = false;
			channel.basicConsume(queueName, autoAck, consumer);
			byte[] dataPackage = null;
			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery(10000);
				if (delivery == null)
					break;
				dataPackage = delivery.getBody();
				// 解析xml定义，将接口数据以及附件放入数据库
				emailRecService.saveDataToDb(dataPackage);
				// 发出通知已接收删除
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			throw e;

		} finally {
			// 最后，我们关闭channel和连接，释放资源。
			try {
				channel.close();
				connection.close();
			} catch (IOException | TimeoutException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
				throw e;
			}

		}
	}

	/**
	 * 从MQ获取数据到数据库
	 * 
	 * @param queueName
	 * @param serverEntity
	 * @param database
	 * @param receivedFlag 上次是否成功接收
	 * @throws Exception
	 */
	public byte[] receiveDataFromMQ(ServerEntity serverEntity, Database database,boolean receivedFlag) throws Exception {
		// 切换当前数据库连接到dbCode对应的数据库下
		Environment.setCurrentSystemName(database.getCode());
		String queueName = database.getQueueName();
		String userName = database.getSysId();
		String password = SecurityUtils.decode(database.getKey(), database.getPassword());
        password = DigestUtils.sha1Hex(password);
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost(serverEntity.getHost());
		cf.setUsername(userName);
		cf.setPort(serverEntity.getPort());
		cf.setPassword(password);
		Connection connection = null;
		// 下一步我们创建一个channel, 通过这个channel就可以完成API中的大部分工作了。
		Channel channel = null;
		// 为了发送消息, 我们必须声明一个队列，来表示我们的消息最终要发往的目的地。
		byte[] dataPackage = null;
		try {
			connection = cf.newConnection();
			// 下一步我们创建一个channel, 通过这个channel就可以完成API中的大部分工作了。
			channel = connection.createChannel();
			channel.queueDeclare(queueName, true, false, false, null);
			QueueingConsumer consumer = new QueueingConsumer(channel);
			// 自动通知标志 true 消息一旦被消费者收到，服务端就知道该消息已经投递，从而从队列中将消息剔除
			boolean autoAck = false;
			channel.basicConsume(queueName, autoAck, consumer);
			QueueingConsumer.Delivery delivery = consumer.nextDelivery(10000);
			if(receivedFlag){
				//发出通知已接收删除
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				delivery = consumer.nextDelivery(10000);
			}
			if (delivery == null) {
				return null;
			}
			dataPackage = delivery.getBody();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			throw e;

		} finally {
			// 最后，我们关闭channel和连接，释放资源。
			try {
				channel.close();
				connection.close();
			} catch (IOException | TimeoutException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
				throw e;
			}

		}
		return dataPackage;
	}

	public static void main(String args[]) {
		// 获取标段关联MQ服务器配置
		CachingConnectionFactory cf = new CachingConnectionFactory();
		cf.setAddresses("192.168.10.122");
		cf.setUsername("adminmq");
		cf.setPort(5672);
		cf.setPassword("mtxx87668438");
		// set up the queue, exchange, binding on the broker
		RabbitAdmin admin = new RabbitAdmin(cf);
		Queue queue = new Queue("myQueue");
		admin.declareQueue(queue);
		DirectExchange exchange = new DirectExchange("myExchange");
		admin.declareExchange(exchange);
		admin.declareBinding(BindingBuilder.bind(queue).to(exchange).withQueueName());
		// set up the listener and container
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cf);
		Object listener = new Object() {
			public void handleMessage(String foo) {
				System.out.println(foo);
			}
		};
		MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
		container.setMessageListener(adapter);
		container.setQueueNames("myQueue");
		container.start();
		// send something
		RabbitTemplate template = new RabbitTemplate(cf);
		template.convertAndSend("myExchange", "foo.bar", "Hello, world!");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		container.stop();
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource
	public void setServerEntityService(IServerEntityService serverEntityService) {
		this.serverEntityService = serverEntityService;
	}

	@javax.annotation.Resource
	public void setEmailSendService(EmailSendService emailSendService) {
		this.emailSendService = emailSendService;
	}

	@javax.annotation.Resource
	public void setEmailSendAttService(EmailSendAttService emailSendAttService) {
		this.emailSendAttService = emailSendAttService;
	}

	@javax.annotation.Resource
	public void setEmailRecService(EmailRecService emailRecService) {
		this.emailRecService = emailRecService;
	}
}
