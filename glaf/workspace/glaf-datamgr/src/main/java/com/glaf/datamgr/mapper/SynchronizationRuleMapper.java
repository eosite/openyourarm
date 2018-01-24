package com.glaf.datamgr.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface SynchronizationRuleMapper {

	void bulkInsertSynchronizationRule(List<SynchronizationRule> list);

	void bulkInsertSynchronizationRule_oracle(List<SynchronizationRule> list);

	void deleteSynchronizationRules(SynchronizationRuleQuery query);

	void deleteSynchronizationRuleById(String id);

	SynchronizationRule getSynchronizationRuleById(String id);
	
	SynchronizationRule getSynchronizationRuleByTargetTable(String targetTable);

	int getSynchronizationRuleCount(SynchronizationRuleQuery query);

	List<SynchronizationRule> getSynchronizationRules(SynchronizationRuleQuery query);

	void insertSynchronizationRule(SynchronizationRule model);

	void updateSynchronizationRule(SynchronizationRule model);

}
