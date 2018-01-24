package com.glaf.dep.base.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.dep.base.domain.*;
import com.glaf.dep.base.query.*;

/**
 * 
 * Mapper接口
 * 
 */

@Component("com.glaf.dep.base.mapper.DepBasePropScopeMapper")
public interface DepBasePropScopeMapper {

	int getDepBasePropScopeCount(DepBasePropScopeQuery query);

	List<DepBasePropScope> getDepBasePropScopes(DepBasePropScopeQuery query);

	void insertDepBasePropScope(DepBasePropScope model);

	void updateDepBasePropScope(DepBasePropScope model);

	List<DepBasePropScope> getDepBasePropScopesByRuleId(String ruleId);

	List<DepBasePropScope> getDepBasePropScopesByUIId(String uiId);

	void deleteByRuleId(String ruleId);

	void deleteByUIId(String uiId);

}
