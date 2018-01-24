package com.glaf.mqdata.service;

import com.glaf.core.domain.Database;
import com.glaf.core.domain.ServerEntity;

public interface MQManagerService {
    /**
     * 为每个标段创建一个MQ登录用户，创建消息队列，用户授权消息队列读写权限
     * 队列类型 点对点  手动删除消息
     */
	void createMQConnectionProperties();
	/**
	 * 发送接口数据到消息队列
	 */
	void sendDataToMQ();
	/**
	 * 从消息队列获取接口数据到本地
	 */
	void receiveDataFromMQ();
	
	void receiveDataFromMQToDb(ServerEntity serverEntity, Database database) throws Exception;
	/**
	 * 发送数据到消息队列
	 * @param serverEntity
	 * @param queueName
	 * @param userName
	 * @param passWord
	 * @param data
	 * @throws Exception
	 */
	void sendMessageToMQ(ServerEntity serverEntity, String queueName,String userName,String passWord, byte[] data) throws Exception;
	/**
	 * 从消息队列接口数据
	 * @param serverEntity
	 * @param database
	 * @return
	 * @throws Exception
	 */
	byte[] receiveDataFromMQ(ServerEntity serverEntity, Database database,boolean receivedFlag) throws Exception ;
}
