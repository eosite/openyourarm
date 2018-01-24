package com.glaf.teim.service;

import java.util.*;

import org.dom4j.DocumentException;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.glaf.teim.domain.*;
import com.glaf.teim.query.*;

@Transactional(readOnly = true)
public interface EimServerTmpService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> tmpIds);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<EimServerTmp> list(EimServerTmpQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getEimServerTmpCountByQueryCriteria(EimServerTmpQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<EimServerTmp> getEimServerTmpsByQueryCriteria(int start, int pageSize, EimServerTmpQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	EimServerTmp getEimServerTmp(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(EimServerTmp eimServerTmp);

	/**
	 * 移動到分類
	 * 
	 * @param tmpId
	 * @param categoryId
	 */
	@Transactional
	void moveToCategory(String tmpIds, String categoryId);

	/**
	 * 调用服务
	 * 
	 * @param tmpId
	 *            服务模板ID
	 * @param paramsJson
	 *            参数
	 * @return
	 */
	JSONObject callService(String tmpId, String baseId, JSONObject paramsJson);


	/**
	 * 不需要访问令牌调用服务
	 * 
	 * @param tmpId
	 *            服务模板ID
	 * @param paramsJson
	 *            参数
	 * @return
	 */
	JSONObject callServiceNoToken(String tmpId, String id, JSONObject paramsMap);
	/**
	 * 获取模板树数据
	 * @return
	 */
	List<EimServerTmpTree> getEimServerTmpTreeData();
    /**
     * 根据wsurl地址获取服务方法和参数
     * @param wsUrl
     * @return
     */
	JSONObject getWebServiceMethodAndParams(String wsUrl) throws DocumentException;

}
