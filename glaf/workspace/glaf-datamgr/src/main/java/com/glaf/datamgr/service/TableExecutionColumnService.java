package com.glaf.datamgr.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

@Transactional(readOnly = true)
public interface TableExecutionColumnService {

	@Transactional
	void bulkInsert(List<TableExecutionColumn> list);

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
	List<TableExecutionColumn> list(TableExecutionColumnQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getTableExecutionColumnCountByQueryCriteria(TableExecutionColumnQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<TableExecutionColumn> getTableExecutionColumnsByQueryCriteria(int start, int pageSize,
			TableExecutionColumnQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	TableExecutionColumn getTableExecutionColumn(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(TableExecutionColumn tableExecutionColumn);

}
