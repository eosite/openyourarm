package com.glaf.datamgr.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

@Transactional(readOnly = true)
public interface TreeTableAggregateService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(long id);

	@Transactional
	void deleteRuleById(long id);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	TreeTableAggregate getTreeTableAggregate(long id);
	
	/**
	 * 根据目标表获取一条记录
	 * 
	 * @return
	 */
	TreeTableAggregate getTreeTableAggregateByTargetTableName(String tableName);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getTreeTableAggregateCountByQueryCriteria(TreeTableAggregateQuery query);

	TreeTableAggregateRule getTreeTableAggregateRule(long ruleId);

	List<TreeTableAggregateRule> getTreeTableAggregateRulesByTableName(String tableName);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<TreeTableAggregate> getTreeTableAggregatesByQueryCriteria(int start, int pageSize,
			TreeTableAggregateQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<TreeTableAggregate> list(TreeTableAggregateQuery query);

	@Transactional
	void resetAllTreeTableAggregateStatus();

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(TreeTableAggregate treeTableAggregate);

	/**
	 * 另存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void saveAs(TreeTableAggregate treeTableAggregate);

	@Transactional
	void saveRule(TreeTableAggregateRule rule);

	/**
	 * 保存同步规则
	 * 
	 * @param aggregateId
	 * @param rules
	 */
	@Transactional
	void saveRules(long aggregateId, List<TreeTableAggregateRule> rules);

	@Transactional
	void updateTreeTableAggregateStatus(TreeTableAggregate model);

}
