package com.glaf.workflow.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.workflow.core.domain.ProcessDefMapping;
import com.glaf.workflow.core.query.ProcessDefMappingQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.workflow.core.mapper.ProcessDefMappingMapper")
public interface ProcessDefMappingMapper {

	void deleteProcessDefMappings(ProcessDefMappingQuery query);

	void deleteProcessDefMappingById(String id);

	ProcessDefMapping getProcessDefMappingById(String id);

	int getProcessDefMappingCount(ProcessDefMappingQuery query);

	List<ProcessDefMapping> getProcessDefMappings(ProcessDefMappingQuery query);

	void insertProcessDefMapping(ProcessDefMapping model);

	void updateProcessDefMapping(ProcessDefMapping model);
	
	ProcessDefMapping getProcessDefMappingByImpProcessDefAndImpSysId(@Param("impProcessDef")String impProcessDef,@Param("impSysId")String impSysId,@Param("sysId")String sysId);

}
