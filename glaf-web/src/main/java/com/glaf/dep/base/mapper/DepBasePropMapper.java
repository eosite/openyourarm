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

@Component("com.glaf.dep.base.mapper.DepBasePropMapper")
public interface DepBasePropMapper {

	void deleteDepBaseProps(DepBasePropQuery query);

	void deleteDepBasePropById(String id);

	DepBaseProp getDepBasePropById(String id);

	int getDepBasePropCount(DepBasePropQuery query);

	List<DepBaseProp> getDepBaseProps(DepBasePropQuery query);

	void insertDepBaseProp(DepBaseProp model);

	void updateDepBaseProp(DepBaseProp model);

	String getMaxRuleCodeByCategoryCode(String categoryCode);

	int getNextOrderNo();

	List<DepBaseProp> getDepBasePropsByCategoryCode(String categoryCode);
}
