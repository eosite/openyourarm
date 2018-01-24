package com.glaf.datamgr.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

@Transactional(readOnly = true)
public interface DynamicSqlTreeService {

	@Transactional
	void bulkInsert(List<DynamicSqlTree> list);

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
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	DynamicSqlTree getDynamicSqlTree(Long id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDynamicSqlTreeCountByQueryCriteria(DynamicSqlTreeQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DynamicSqlTree> getDynamicSqlTreesByQueryCriteria(int start, int pageSize, DynamicSqlTreeQuery query);

	List<DynamicSqlTree> getDynamicSqlTreesByTableName(String tableName);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<DynamicSqlTree> list(DynamicSqlTreeQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(DynamicSqlTree dynamicSqlTree);

}
