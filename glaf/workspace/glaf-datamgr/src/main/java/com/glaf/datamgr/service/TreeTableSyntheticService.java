package com.glaf.datamgr.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

@Transactional(readOnly = true)
public interface TreeTableSyntheticService {

	@Transactional
	void bulkInsert(List<TreeTableSynthetic> list);

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
	TreeTableSynthetic getTreeTableSynthetic(long id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getTreeTableSyntheticCountByQueryCriteria(TreeTableSyntheticQuery query);

	TreeTableSyntheticRule getTreeTableSyntheticRule(long ruleId);

	List<TreeTableSyntheticRule> getTreeTableSyntheticRulesByTableName(String tableName);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<TreeTableSynthetic> getTreeTableSyntheticsByQueryCriteria(int start, int pageSize,
			TreeTableSyntheticQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<TreeTableSynthetic> list(TreeTableSyntheticQuery query);
	
	@Transactional
	void resetAllTreeTableSyntheticStatus();

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(TreeTableSynthetic treeTableSynthetic);

	/**
	 * 另存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void saveAs(TreeTableSynthetic treeTableSynthetic);

	@Transactional
	void saveRule(TreeTableSyntheticRule rule);

	/**
	 * 保存同步规则
	 * 
	 * @param syntheticId
	 * @param rules
	 */
	@Transactional
	void saveRules(long syntheticId, List<TreeTableSyntheticRule> rules);
	
	@Transactional
	void updateTreeTableSyntheticStatus(TreeTableSynthetic model);

}
