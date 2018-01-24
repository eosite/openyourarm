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
public interface TreeTableSyntheticRuleMapper {

	void bulkInsertTreeTableSyntheticRule(List<TreeTableSyntheticRule> list);

	void bulkInsertTreeTableSyntheticRule_oracle(List<TreeTableSyntheticRule> list);

	void deleteTreeTableSyntheticRuleById(Long id);

	void deleteTreeTableSyntheticRulesBySyntheticId(long syntheticId);

	TreeTableSyntheticRule getTreeTableSyntheticRuleById(Long id);

	List<TreeTableSyntheticRule> getTreeTableSyntheticRulesBySyntheticId(long syntheticId);

	List<TreeTableSyntheticRule> getTreeTableSyntheticRulesByTableName(String tableName);
	
	void insertTreeTableSyntheticRule(TreeTableSyntheticRule model);

	void updateTreeTableSyntheticRule(TreeTableSyntheticRule model);

}
