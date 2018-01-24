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

@Component("com.glaf.dep.base.mapper.DepBaseComponentMapper")
public interface DepBaseComponentMapper {

	void deleteDepBaseComponents(DepBaseComponentQuery query);

	void deleteDepBaseComponentById(String id);

	DepBaseComponent getDepBaseComponentById(String id);

	int getDepBaseComponentCount(DepBaseComponentQuery query);

	List<DepBaseComponent> getDepBaseComponents(DepBaseComponentQuery query);

	void insertDepBaseComponent(DepBaseComponent model);

	void updateDepBaseComponent(DepBaseComponent model);
	
	List<DepBaseComponent> getDepBaseComponentsByRuleId(String ruleId);
}
