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

@Component("com.glaf.dep.base.mapper.DepBaseCompScopeMapper")
public interface DepBaseCompScopeMapper {

	DepBaseCompScope getDepBaseCompScopeById(String id);

	int getDepBaseCompScopeCount(DepBaseCompScopeQuery query);

	List<DepBaseCompScope> getDepBaseCompScopes(DepBaseCompScopeQuery query);

	void insertDepBaseCompScope(DepBaseCompScope model);

	void updateDepBaseCompScope(DepBaseCompScope model);

	List<DepBaseCompScope> getDepBaseCompScopesByComponentId(String componentId);

	List<DepBaseCompScope> getDepBaseCompScopesByUIId(String uiId);

	void deleteByComponentId(String componentId);

	void deleteByUIId(String uiid);

}
