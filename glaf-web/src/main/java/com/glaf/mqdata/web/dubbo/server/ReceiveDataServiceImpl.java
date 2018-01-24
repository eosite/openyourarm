package com.glaf.mqdata.web.dubbo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.ServerEntity;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.IServerEntityService;
import com.glaf.mqdata.service.MQManagerService;

@Service(version = "4.0.0", protocol = { "rmi", "dubbo" },timeout=300000)
public class ReceiveDataServiceImpl implements ReceiveDataService{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected IDatabaseService databaseService;
	protected IServerEntityService serverEntityService;
	protected MQManagerService mqManagerService;
	public byte[] receiveMsg(String sysId, boolean receivedFlag) {
		// 获取数据库配置以及MQ队列信息
		Database database = databaseService.getDatabaseBySysId(sysId);
		byte[] dataPackage = null;
		if (database == null) {
			logger.error("系统[" + sysId + "]数据库配置不存在");
			dataPackage= null;
		}
		// MQ队列名
		String queueName = database.getQueueName();
		if (StringUtils.isEmpty(queueName)) {
			logger.error("系统[" + sysId + "]队列名未定义");
			dataPackage= null;
		}
		Long serverId = database.getServerId();
		if (serverId == null) {
			logger.error("系统[" + sysId + "]对应的MQ服务器未配置");
			dataPackage= null;
		}
		// 获取当前MQ服务器配置
		ServerEntity serverEntity = serverEntityService.getServerEntityById(serverId);
		if (serverEntity == null) {
			logger.error("系统[" + sysId + "]对应的MQ服务器配置为空");
			dataPackage= null;
		}
		try {
			dataPackage = mqManagerService.receiveDataFromMQ(serverEntity, database, receivedFlag);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("系统[" + sysId + "]从MQ服务器读取信息出错：" + e.getMessage());
		}
		if(dataPackage==null){
			dataPackage=new byte[]{'1'};
		}
		return dataPackage;
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
