package com.glaf.datamgr.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

@Transactional(readOnly = true)
public interface SynchronizationRuleService {

	@Transactional
	void bulkInsert(List<SynchronizationRule> list);

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
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	SynchronizationRule getSynchronizationRule(String id);
	
	
	/**
	 * 根据targetTable获取一条记录
	 * 
	 * @return
	 */
	SynchronizationRule getSynchronizationRuleByTargetTable(String targetTable);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getSynchronizationRuleCountByQueryCriteria(SynchronizationRuleQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<SynchronizationRule> getSynchronizationRulesByQueryCriteria(int start, int pageSize,
			SynchronizationRuleQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<SynchronizationRule> list(SynchronizationRuleQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(SynchronizationRule synchronizationRule);

}
