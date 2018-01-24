package com.glaf.dep.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.dep.base.domain.DepBaseCompProp;
import com.glaf.dep.base.query.DepBaseCompPropQuery;

/**
 * 
 * Mapper接口
 * 
 */

@Component("com.glaf.dep.base.mapper.DepBaseCompPropMapper")
public interface DepBaseCompPropMapper {

	void deleteDepBaseCompPropByRuleId(String id);

	DepBaseCompProp getDepBaseCompPropById(String id);

	int getDepBaseCompPropCount(DepBaseCompPropQuery query);

	List<DepBaseCompProp> getDepBaseCompProps(DepBaseCompPropQuery query);

	void insertDepBaseCompProp(DepBaseCompProp model);

	void updateDepBaseCompProp(DepBaseCompProp model);

	void deleteByPrimaryKey(@Param("componentId")String componentId, @Param("ruleId")String ruleId);

}
