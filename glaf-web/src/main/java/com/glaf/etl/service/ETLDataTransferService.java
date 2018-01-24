package com.glaf.etl.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.etl.domain.ETLDataTransfer;
import com.glaf.etl.query.ETLDataTransferQuery;

@Transactional(readOnly = true)
public interface ETLDataTransferService {

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
	void deleteByIds(List<String> ids);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<ETLDataTransfer> list(ETLDataTransferQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getETLDataTransferCountByQueryCriteria(ETLDataTransferQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<ETLDataTransfer> getETLDataTransfersByQueryCriteria(int start, int pageSize, ETLDataTransferQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	ETLDataTransfer getETLDataTransfer(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(ETLDataTransfer eTLDataTransfer);
	/**
	 * 更新转换定义列与目标列映射
	 * @param transId
	 * @param columnMapping
	 * @param user
	 */
	@Transactional
	void updateColumnMapping(String targetId,String transId,String columnMapping,String user) throws UnsupportedEncodingException;

	/**
	 * 获取源定义
	 * 
	 * @param srcId
	 * @return
	 */
	JSONArray getSrcDefColumns(String srcId) throws UnsupportedEncodingException;

	/**
	 * 获取与目标列映射定义
	 * 
	 * @param srcId
	 * @return
	 */
	JSONObject getColumnMapping(String columnMapping);

}
