package com.glaf.datamgr.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

@Transactional(readOnly = true)
public interface DataItemDefinitionService {

	@Transactional
	void bulkInsert(List<DataItemDefinition> list);

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
	DataItemDefinition getDataItemDefinition(Long id);
	
	
	DataItemDefinition getDataItemDefinitionByCode(String code);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDataItemDefinitionCountByQueryCriteria(DataItemDefinitionQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<DataItemDefinition> getDataItemDefinitions(String category);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<DataItemDefinition> getDataItemDefinitionsByNodeId(long nodeId);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DataItemDefinition> getDataItemDefinitionsByQueryCriteria(int start, int pageSize,
			DataItemDefinitionQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<DataItemDefinition> list(DataItemDefinitionQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(DataItemDefinition dataItemDefinition);

}
