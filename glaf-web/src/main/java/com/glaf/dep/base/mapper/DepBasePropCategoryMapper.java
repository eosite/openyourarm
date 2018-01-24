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

@Component("com.glaf.dep.base.mapper.DepBasePropCategoryMapper")
public interface DepBasePropCategoryMapper {

	void deleteDepBasePropCategorys(DepBasePropCategoryQuery query);

	void deleteDepBasePropCategoryById(String id);

	DepBasePropCategory getDepBasePropCategoryById(String id);

	int getDepBasePropCategoryCount(DepBasePropCategoryQuery query);

	List<DepBasePropCategory> getDepBasePropCategorys(
			DepBasePropCategoryQuery query);

	void insertDepBasePropCategory(DepBasePropCategory model);

	void updateDepBasePropCategory(DepBasePropCategory model);

	void deleteByRuleId(String ruleId);

	DepBasePropCategory getDepBasePropCategory(DepBasePropCategoryQuery query);

}
