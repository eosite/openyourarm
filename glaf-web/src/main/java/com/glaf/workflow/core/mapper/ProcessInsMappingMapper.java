package com.glaf.workflow.core.mapper;

import java.util.List;
import java.util.Vector;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.workflow.core.domain.ProcessInsMapping;
import com.glaf.workflow.core.domain.ProcessMainTableName;
import com.glaf.workflow.core.query.ProcessInsMappingQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.workflow.core.mapper.ProcessInsMappingMapper")
public interface ProcessInsMappingMapper {

	void deleteProcessInsMappings(ProcessInsMappingQuery query);

	void deleteProcessInsMappingById(String id);

	ProcessInsMapping getProcessInsMappingById(String id);

	int getProcessInsMappingCount(ProcessInsMappingQuery query);
	
	String getActIdByExecoutionId(String execoutionId);
	
	String getActIdByProcessInstId(String processInstId);

	List<ProcessInsMapping> getProcessInsMappings(ProcessInsMappingQuery query);

	void insertProcessInsMapping(ProcessInsMapping model);

	void updateProcessInsMapping(ProcessInsMapping model);
	
	List<ProcessMainTableName> getProcessMainTableNames(String processId);
	
	String getProcessMainTablePrimaryKeyVal(@Param("tableName")String tableName,@Param("fillFormId")String fillFormId);

	List<ProcessMainTableName> getReportProcessMainTableNames(String processId);
	
	List<ProcessMainTableName> getWpfProcessMainTableNames(String processId);

	String getReportProcessMainTablePrimaryKeyVal(@Param("tableName")String tableName, @Param("processId")String processId);
	
	void removeVariable(@Param("processInstId")String processInstId, @Param("varName")String varName);
   
	void emptyProcessInstVaribale(@Param("processInstId")String processInstId,@Param("taskDefKeys")Vector<String> taskDefKeys);
	/**
	 * 获取任务最近一次的执行者
	 * @param processInstId
	 * @param taskKey
	 * @return
	 */
	List<String> getTaskLastAssignee(@Param("processInstId")String processInstId,@Param("taskKey")String taskKey);
	/**
	 * 获取未分配用户任务
	 * @param processInstId
	 * @return
	 */
	List<String> getUnassignedAndUnCandidateTasks(String processInstId);
	/**
	 * 获取子流程key集合
	 * @param superProcessDef
	 * @return
	 */
	List<String> getSubProcessDefBySuperProcessDef(String superProcessDef);

	void emptyAllProcessInstVaribale(String processInstId);
}
