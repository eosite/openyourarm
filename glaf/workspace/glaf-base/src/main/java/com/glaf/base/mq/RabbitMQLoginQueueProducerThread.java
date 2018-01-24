package com.glaf.base.mq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ServerEntity;
import com.glaf.core.security.SecurityUtils;
import com.glaf.core.service.IServerEntityService;

public class RabbitMQLoginQueueProducerThread implements java.lang.Runnable {
	private static final Log logger = LogFactory.getLog(RabbitMQLoginQueueProducerThread.class);
	protected String token;
	protected String userId;
	protected String loginIP;
	protected String endPointName;

	public RabbitMQLoginQueueProducerThread(String token, String userId, String loginIP, String endPointName) {
		this.token = token;
		this.userId = userId;
		this.loginIP = loginIP;
		this.endPointName = endPointName;
	}

	public void run() {
		try {
			IServerEntityService serverEntityService = ContextFactory.getBean("serverEntityService");
			logger.debug("endPointName:" + endPointName);
			ServerEntity serverEntity = serverEntityService.getServerEntityByMapping("queue_db");
			if (serverEntity != null) {
				RabbitMQLoginQueueProducer producer = new RabbitMQLoginQueueProducer(this.endPointName);
				producer.setHost(serverEntity.getHost());
				producer.setPort(serverEntity.getPort());
				producer.setUsername(serverEntity.getUser());
				String password = SecurityUtils.decode(serverEntity.getKey(), serverEntity.getPassword());
				producer.setPassword(password);
				JSONObject json = producer.buildMessage(token, userId, loginIP);
				logger.debug("host:" + serverEntity.getHost());
				logger.debug("port:" + serverEntity.getPort());
				logger.debug("user:" + serverEntity.getUser());
				logger.debug("send message:" + json.toJSONString());
				// producer.sendBytesMessage(json.toJSONString().getBytes("UTF-8"));
				producer.sendBytesMessage(Hex.encodeHexString(json.toJSONString().getBytes()).getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

}
