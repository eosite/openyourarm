package com.glaf.teim.service;

import java.sql.SQLException;

import com.alibaba.fastjson.JSONObject;
import com.glaf.teim.domain.EimBaseInfo;
import com.glaf.teim.domain.EimServerTmp;

public interface EimServerSyncService {
	/**
	 * 服务数据同步到表 用于内部循环调用
	 * 
	 * @param eimBaseInfo
	 *            接口应用基础信息
	 * @param eimServerTmp
	 *            服务模板
	 * @param targetDatabase
	 *            目标数据源
	 * @param targetName
	 *            目标表
	 * @param columnMapping
	 *            接口返回数据列与目标表列映射关系
	 * @param paramsMap
	 *            参数
	 */
	 void syncNoToken(String baseId, String tmpId, String targetDatabase, String targetName, JSONObject columnMapping,
			JSONObject paramsMap,String[] extColumnStr) throws Exception;
    /**
     *  服务数据同步到表  用于外部循环调用
     * @param eimBaseInfo  接口应用基础信息
     * @param eimServerTmp  服务模板
     * @param targetDatabase  目标数据源
     * @param targetTableName   目标表
     * @param columnMapping 接口返回数据列与目标表列映射关系
     * @param paramsMap 参数
     */
	 void syncNoToken(EimBaseInfo eimBaseInfo, EimServerTmp eimServerTmp, String targetDatabase, String targetTableName,
			JSONObject columnMapping, JSONObject paramsMap, String[] extColumnStr) throws Exception;
	/**
	 * 根据任务ID同步服務數據
	 * @param taskId
	 */
	void sysncDataByTaskId(String taskId) throws Exception;
}
