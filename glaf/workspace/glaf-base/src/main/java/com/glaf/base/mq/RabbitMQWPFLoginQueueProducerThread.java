package com.glaf.base.mq;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ServerEntity;
import com.glaf.core.domain.SysDataLog;
import com.glaf.core.factory.SysLogFactory;
import com.glaf.core.security.SecurityUtils;
import com.glaf.core.service.IServerEntityService;
import com.glaf.core.util.DateUtils;

public class RabbitMQWPFLoginQueueProducerThread implements java.lang.Runnable {
	private static final Log logger = LogFactory.getLog(RabbitMQWPFLoginQueueProducerThread.class);
	protected String token;
	protected String userId;
	protected String loginIP;
	protected String uid;
	protected String flowid;
	protected String cellTreedotIndex;
	protected String position;
	protected long databaseId;
	protected String endPointName;

	public RabbitMQWPFLoginQueueProducerThread(String token, String userId, String loginIP, String uid, long databaseId,
			String endPointName) {
		this.token = token;
		this.userId = userId;
		this.loginIP = loginIP;
		this.uid = uid;
		this.databaseId = databaseId;
		this.endPointName = endPointName;
	}

	public RabbitMQWPFLoginQueueProducerThread(String token, String userId, String loginIP, String flowid,
			String cellTreedotIndex, String position, long databaseId, String endPointName) {
		this.token = token;
		this.userId = userId;
		this.loginIP = loginIP;
		this.flowid = flowid;
		this.cellTreedotIndex = cellTreedotIndex;
		this.position = position;
		this.databaseId = databaseId;
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

				SysDataLog sysLog = new SysDataLog();
				sysLog.setIp("localhost");
				sysLog.setActorId(userId);
				sysLog.setCreateTime(new Date());
				sysLog.setModuleId("ISDP_CS");
				sysLog.setServiceKey("ISDP_CS_MQ");

				JSONObject jsonx = new JSONObject();
				jsonx.put("clientIP", loginIP);
				jsonx.put("userId", userId);
				jsonx.put("databaseId", databaseId);
				jsonx.put("loginTime", DateUtils.getDateTime(new Date()));
				long start = System.currentTimeMillis();

				if (StringUtils.isNotEmpty(uid)) {
					JSONObject json = producer.buildMessage(token, userId, loginIP, uid, databaseId);
					logger.debug("host:" + serverEntity.getHost());
					logger.debug("port:" + serverEntity.getPort());
					logger.debug("user:" + serverEntity.getUser());
					// logger.debug("pwd:" + password);
					logger.debug("send message:" + json.toJSONString());
					// producer.sendBytesMessage(json.toJSONString().getBytes("UTF-8"));
					producer.sendBytesMessage(Hex.encodeHexString(json.toJSONString().getBytes()).getBytes());
					jsonx.put("type", "WPF");
					long ts = System.currentTimeMillis() - start;
					sysLog.setFlag(1);
					sysLog.setTimeMS((int) ts);
					sysLog.setBusinessKey("WPF_" + uid);
					sysLog.setContent(jsonx.toJSONString());
					SysLogFactory.getInstance().addLog(sysLog);
				}
				if (StringUtils.isNotEmpty(cellTreedotIndex)) {
					JSONObject json = producer.buildIsdpMessage(token, userId, loginIP, flowid, cellTreedotIndex,
							position, databaseId);
					logger.debug("host:" + serverEntity.getHost());
					logger.debug("port:" + serverEntity.getPort());
					logger.debug("user:" + serverEntity.getUser());
					// logger.debug("pwd:" + password);
					logger.debug("send message:" + json.toJSONString());
					// producer.sendBytesMessage(json.toJSONString().getBytes("UTF-8"));
					producer.sendBytesMessage(Hex.encodeHexString(json.toJSONString().getBytes()).getBytes());
					jsonx.put("type", "Delphi");
					long ts = System.currentTimeMillis() - start;
					sysLog.setFlag(1);
					sysLog.setTimeMS((int) ts);
					sysLog.setBusinessKey("Delphi_" + cellTreedotIndex);
					sysLog.setContent(jsonx.toJSONString());
					SysLogFactory.getInstance().addLog(sysLog);
				}
			} else {
				logger.warn("不能发送消息，别名为queue_db的消息服务未配置，请联系管理员。");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

}
