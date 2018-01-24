package com.glaf.mqdata.web.dubbo.server;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.ServerEntity;
import com.glaf.core.security.SecurityUtils;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.IServerEntityService;
import com.glaf.core.util.ResponseUtils;
import com.glaf.mqdata.service.MQManagerService;

@Service(version = "3.0.0", protocol = { "rmi", "dubbo" },timeout=300000)
public class SendDataServiceImpl implements SendDataService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected IDatabaseService databaseService;
	protected IServerEntityService serverEntityService;
	protected MQManagerService mqManagerService;

	public byte[] sendMsgtoSys(String sysId, byte[] msg) {
		// TODO Auto-generated method stub
		// 获取数据库配置以及MQ队列信息
		Database database = databaseService.getDatabaseBySysId(sysId);
		if (database == null) {
			logger.error("系统[" + sysId + "]数据库配置不存在");
			return ResponseUtils.responseJsonResult(false);
		}
		// MQ队列名
		String queueName = database.getQueueName();
		if (StringUtils.isEmpty(queueName)) {
			logger.error("系统[" + sysId + "]队列名未定义");
			return ResponseUtils.responseJsonResult(false);
		}
		Long serverId = database.getServerId();
		if (serverId == null) {
			logger.error("系统[" + sysId + "]对应的MQ服务器未配置");
			return ResponseUtils.responseJsonResult(false);
		}
		// 获取当前MQ服务器配置
		ServerEntity serverEntity = serverEntityService.getServerEntityById(serverId);
		if (serverEntity == null) {
			logger.error("系统[" + sysId + "]对应的MQ服务器配置为空");
			return ResponseUtils.responseJsonResult(false);
		}
		try {
			String password = SecurityUtils.decode(database.getKey(), database.getPassword());
	        password = DigestUtils.sha1Hex(password);
			mqManagerService.sendMessageToMQ(serverEntity, queueName, sysId, password, msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("发送数据到系统[" + sysId + "],消息队列[" + queueName + "]错误:" + e.getMessage());
		}
		return ResponseUtils.responseJsonResult(true);
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
	public void setMqManagerService(MQManagerService mqManagerService) {
		this.mqManagerService = mqManagerService;
	}
}
