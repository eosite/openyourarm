package com.glaf.dep.base.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import com.glaf.dep.base.domain.*;
import com.glaf.dep.base.query.*;

/**
 * 
 * Mapper接口
 * 
 */

@Component("com.glaf.dep.base.mapper.DepBasePropRelMapper")
public interface DepBasePropRelMapper {

	DepBasePropRel getDepBasePropRelById(String id);

	int getDepBasePropRelCount(DepBasePropRelQuery query);

	List<DepBasePropRel> getDepBasePropRels(DepBasePropRelQuery query);

	void insertDepBasePropRel(DepBasePropRel model);

	void updateDepBasePropRel(DepBasePropRel model);

	void deleteByRuleId(String id);

	void deleteByPrimaryKey(@Param("ruleId")String ruleId,@Param("relRuleId")String relRuleId);

	DepBasePropRel getDepBasePropRelByPrimaryKey(@Param("ruleId")String ruleId,@Param("relRuleId")String relRuleId);
	
	List<DepBasePropRel> selectDepBasePropRelByRuleId(String ruleId);
}
