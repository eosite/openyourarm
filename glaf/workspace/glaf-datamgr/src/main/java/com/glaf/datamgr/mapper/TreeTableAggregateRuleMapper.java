package com.glaf.datamgr.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.datamgr.domain.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface TreeTableAggregateRuleMapper {

	void bulkInsertTreeTableAggregateRule(List<TreeTableAggregateRule> list);

	void bulkInsertTreeTableAggregateRule_oracle(List<TreeTableAggregateRule> list);

	void deleteTreeTableAggregateRuleById(Long id);

	void deleteTreeTableAggregateRulesByAggregateId(long aggregateId);

	TreeTableAggregateRule getTreeTableAggregateRuleById(Long id);

	List<TreeTableAggregateRule> getTreeTableAggregateRulesByAggregateId(long aggregateId);

	List<TreeTableAggregateRule> getTreeTableAggregateRulesByTableName(String tableName);
	
	void insertTreeTableAggregateRule(TreeTableAggregateRule model);

	void updateTreeTableAggregateRule(TreeTableAggregateRule model);

}
