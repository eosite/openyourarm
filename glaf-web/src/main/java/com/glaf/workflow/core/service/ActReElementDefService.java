package com.glaf.workflow.core.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.glaf.workflow.core.domain.*;
import com.glaf.workflow.core.query.*;

@Transactional(readOnly = true)
public interface ActReElementDefService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> IDs);

	/**
	 * 删除流程元素定义
	 * 
	 * @param modelId
	 * @param resourceId
	 */
	@Transactional
	void deleteActReElementDefByModelIdResourceId(String modelId,
			String resourceId);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<ActReElementDef> list(ActReElementDefQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getActReElementDefCountByQueryCriteria(ActReElementDefQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<ActReElementDef> getActReElementDefsByQueryCriteria(int start,
			int pageSize, ActReElementDefQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	ActReElementDef getActReElementDef(Long id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(ActReElementDef actReElementDef);
	
	ActReElementDef getActReElementDefByModelIdResourceId(
			String modelId, String resourceId,String processDefId);
	/**
	 * 根据流程定义ID和任务ID获取扩展规则
	 * @param processDefId
	 * @param taskKey
	 * @return
	 */
	ActReElementDef getActReElementDefByProcDefIdTaskKey(
			String processDefId,String taskKey);
	/**
	 * 获取流程变量定义
	 * @param modelId
	 * @param resourceId
	 * @param processDefId
	 * @return
	 */
	List<JSONObject> getVariableJSON(String modelId, String resourceId,String processDefId);
	/**
	 * 获取流程定义自定义属性
	 * @param modelId
	 * @param processDefId
	 * @return
	 */
	List<ActReElementDef> getWorkflowActReElementDefsByModelIdProDefId(String modelId,String processDefId);
	
	ActReElementDef getActReElementDefByProcessDefinitionIdResourceId(String processDefId,String resourceId);
	/**
	 * 获取流程定义自定义属性Map key resourceId
	 * @param modelId
	 * @param processDefId
	 * @return
	 */
	Map<String,ActReElementDef> getWorkflowActReElementDefMapByModelIdProDefId(String modelId,String processDefId);
	/**
	 * List to Map
	 * @param actReElementDefs
	 * @return key resourceId
	 */
	Map<String,ActReElementDef> getWorkflowActReElementDefMap(List<ActReElementDef> actReElementDefs);
	/**
	 * 获取工作流定义属性定义JSON
	 * @param actReElementDef
	 * @return
	 */
	JSONObject getWorkflowActReElementDefJsonObj(ActReElementDef actReElementDef);
	/**
	 * 获取工作流用户任务分配人
	 * @param ActReElementDef
	 * @return
	 */
	Set<String> getTaskAssign(Map<String,Object> variables,ActReElementDef ActReElementDef,String submitter);
	/**
	 * 更新扩展规则表流程定义ID
	 * @param modelId 模型ID
	 * @param processDefId
	 * @param deploymentId
	 */
	void updateProcedefIdByModelId(String modelId,String processDefId,String deploymentId);
	/**
	 * 复制模型扩展配置 并将ACT_RE_MODEL DEPLOYMENT_ID_置为空
	 * @param modelId
	 * @return
	 */
	boolean copyWorkFlow(String modelId,String userId);
	/**
	 * 获取流程变量定义
	 * @param processKey 流程定义关键字
	 * @return
	 */
	List<JSONObject> getVariableJSONByProcessKey(String processKey);

	void removeDeletedElementsByNotInIds(String modelId,List<String> resourceIds);
}
