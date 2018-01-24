package com.glaf.form.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.form.core.domain.FormWorkflowPlan;
import com.glaf.form.core.query.FormWorkflowPlanQuery;

/**
 * 
 * Mapper接口
 * 
 */

@Component("com.glaf.form.core.mapper.FormWorkflowPlanMapper")
public interface FormWorkflowPlanMapper {

	void deleteFormWorkflowPlans(FormWorkflowPlanQuery query);

	void deleteFormWorkflowPlanById(String id);

	FormWorkflowPlan getFormWorkflowPlanById(String id);

	int getFormWorkflowPlanCount(FormWorkflowPlanQuery query);

	List<FormWorkflowPlan> getFormWorkflowPlans(FormWorkflowPlanQuery query);
	
	List<FormWorkflowPlan> getFormWorkflowPlansWithTree(Map<String, Object> params);

	void insertFormWorkflowPlan(FormWorkflowPlan model);

	void updateFormWorkflowPlan(FormWorkflowPlan model);

	Integer getMaxVersionByDefId(String defId);

	List<FormWorkflowPlan> getFormWorkflowPlanByProcessDef(
			@Param("tableName") String tableName,
			@Param("startPointColumn") String startPointColumn,
			@Param("defKeyColumn") String defKeyColumn,
			@Param("processDefId") String processDefId,
			@Param("startPointVal") int startPointVal);

}
