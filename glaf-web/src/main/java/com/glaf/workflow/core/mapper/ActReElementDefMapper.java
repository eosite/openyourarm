package com.glaf.workflow.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.workflow.core.domain.ActReElementDef;
import com.glaf.workflow.core.query.ActReElementDefQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.workflow.core.mapper.ActReElementDefMapper")
public interface ActReElementDefMapper {

	void deleteActReElementDefs(ActReElementDefQuery query);

	void deleteActReElementDefById(Long id);
	
	void deleteActReElementDefByModelIdResourceId(@Param("modelId") String modelId,@Param("resourceId") String resourceId);

	ActReElementDef getActReElementDefById(Long id);

	int getActReElementDefCount(ActReElementDefQuery query);

	List<ActReElementDef> getActReElementDefs(ActReElementDefQuery query);

	void insertActReElementDef(ActReElementDef model);

	void updateActReElementDef(ActReElementDef model);
	
	void updateProcedefIdByModelId(@Param("modelId")String modelId, @Param("processDefId")String processDefId);
	
	void updateDeploymentIdByModelId(@Param("modelId")String modelId, @Param("deploymentId")String deploymentId);
	
	List<ActReElementDef> getWorkflowActReElementDefsByModelIdProDefId(
			@Param("modelId")String modelId, @Param("processDefId")String processDefId);
	
	ActReElementDef getActReElementDefByProcessDefinitionIdResourceId(
			@Param("processDefId")String processDefId, @Param("resourceId")String resourceId) ;

	void removeDeletedElementsByNotInIds(@Param("modelId")String modelId,@Param("resourceIds")List<String> resourceIds);
}
