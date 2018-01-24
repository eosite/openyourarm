package com.glaf.datamgr.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

@Transactional(readOnly = true)
public interface TableTransformService {

	@Transactional
	void bulkInsert(List<TableTransform> list);

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String tableName);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	TableTransform getTableTransform(String tableName);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getTableTransformCountByQueryCriteria(TableTransformQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<TableTransform> getTableTransformsByQueryCriteria(int start, int pageSize, TableTransformQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<TableTransform> list(TableTransformQuery query);
	
	@Transactional
	void resetAllTableTransformStatus();

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(TableTransform tableTransform);
	
	@Transactional
	void updateTableTransformStatus(TableTransform model);

}
