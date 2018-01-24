package com.glaf.dep.base.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.dep.base.domain.*;
import com.glaf.dep.base.query.*;

@Transactional(readOnly = true)
public interface DepBaseWdataSetService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> ids);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<DepBaseWdataSet> list(DepBaseWdataSetQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDepBaseWdataSetCountByQueryCriteria(DepBaseWdataSetQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DepBaseWdataSet> getDepBaseWdataSetsByQueryCriteria(int start, int pageSize, DepBaseWdataSetQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	DepBaseWdataSet getDepBaseWdataSet(Long id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(DepBaseWdataSet depBaseWdataSet);

	/*
	 * String getInsertSql(Long id, Map<String, Object> params);
	 *//**
		 * 生成动态参数 插入语句
		 * 
		 * @param id
		 * @param params
		 * @return
		 */

	/*
	 * String getInsertDynamicSql(Long id, Map<String, Object> params);
	 * 
	 * String getUpdateSql(Long id, Map<String, Object> params);
	 *//**
		 * 生成动态参数 更新语句
		 * 
		 * @param id
		 * @param params
		 * @return
		 */
	/*
	 * String getUpdateDynamicSql(Long id, Map<String, Object> params);
	 */

	/**
	 * 获取已选更新列定义
	 * 
	 * @param id
	 * @return
	 */
	JSONArray getColumnsBySelected(Long id);

	/**
	 * 获取所有列定义
	 * 
	 * @param id
	 * @return
	 */
	JSONArray getColumns(Long id);

	/**
	 * 获取更新集增删改参数、列
	 * 
	 * 更新，删除参数 whereParams 列 ：columns 已选更新列定义 :selectedColumns
	 * 
	 * @param id
	 * @return
	 */
	JSONObject getWdataSetCud(Long id);

	/**
	 * 刷新更新集(增加系统默认字段<updatedate>)
	 */
	void refreshWDataSets();

}
