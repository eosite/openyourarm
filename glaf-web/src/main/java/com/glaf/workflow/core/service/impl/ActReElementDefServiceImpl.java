package com.glaf.workflow.core.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.PostService;
import com.glaf.base.modules.sys.service.SysUserRoleService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;
import com.glaf.workflow.core.domain.ActReElementDef;
import com.glaf.workflow.core.mapper.ActReElementDefMapper;
import com.glaf.workflow.core.query.ActReElementDefQuery;
import com.glaf.workflow.core.service.ActReElementDefService;
import com.glaf.workflow.core.util.WorkflowConstant;

@Service("actReElementDefServiceImpl")
@Transactional(readOnly = true)
public class ActReElementDefServiceImpl implements ActReElementDefService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ActReElementDefMapper actReElementDefMapper;

	protected SysUserService sysUserService;

	protected SysUserRoleService sysUserRoleService;

	private RepositoryService repositoryService;
	
	private PostService postService;

	@Resource
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@Resource
	public void setSysUserRoleService(SysUserRoleService sysUserRoleService) {
		this.sysUserRoleService = sysUserRoleService;
	}
	@Resource
	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	public ActReElementDefServiceImpl() {

	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			actReElementDefMapper.deleteActReElementDefById(id);
		}
	}

	@Override
	@Transactional
	public void deleteByIds(List<Long> IDs) {
		if (IDs != null && !IDs.isEmpty()) {
			for (Long id : IDs) {
				actReElementDefMapper.deleteActReElementDefById(id);
			}
		}
	}

	@Override
	@Transactional
	public void deleteActReElementDefByModelIdResourceId(String modelId, String resourceId) {
		actReElementDefMapper.deleteActReElementDefByModelIdResourceId(modelId, resourceId);
	}

	public int count(ActReElementDefQuery query) {
		return actReElementDefMapper.getActReElementDefCount(query);
	}

	@Override
	public List<ActReElementDef> list(ActReElementDefQuery query) {
		List<ActReElementDef> list = actReElementDefMapper.getActReElementDefs(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	@Override
	public int getActReElementDefCountByQueryCriteria(ActReElementDefQuery query) {
		return actReElementDefMapper.getActReElementDefCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	@Override
	public List<ActReElementDef> getActReElementDefsByQueryCriteria(int start, int pageSize,
			ActReElementDefQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ActReElementDef> rows = sqlSessionTemplate.selectList("getActReElementDefs", query, rowBounds);
		return rows;
	}

	@Override
	public ActReElementDef getActReElementDef(Long id) {
		if (id == null) {
			return null;
		}
		ActReElementDef actReElementDef = actReElementDefMapper.getActReElementDefById(id);
		return actReElementDef;
	}

	@Override
	@Transactional
	public void save(ActReElementDef actReElementDef) {
		if (actReElementDef.getID() == null) {
			actReElementDef.setID(idGenerator.nextId("ACT_RE_ELEMENTDEF"));
			// actReElementDef.setCreateDate(new Date());
			// actReElementDef.setDeleteFlag(0);
			actReElementDefMapper.insertActReElementDef(actReElementDef);
		} else {
			actReElementDefMapper.updateActReElementDef(actReElementDef);
		}
	}

	@Override
	public ActReElementDef getActReElementDefByModelIdResourceId(String modelId, String resourceId,
			String processDefId) {
		ActReElementDef actReElementDef = null;
		if (!StringUtils.isEmpty(modelId) && !StringUtils.isEmpty(resourceId)) {
			ActReElementDefQuery query = new ActReElementDefQuery();
			query.setModelId(modelId);
			query.setEleResourceId(resourceId);
			query.setProceDefId(processDefId);
			List<ActReElementDef> actReElementDefs = actReElementDefMapper.getActReElementDefs(query);
			if (actReElementDefs != null && actReElementDefs.size() == 1) {
				actReElementDef = actReElementDefs.get(0);
			}
		}
		return actReElementDef;
	}

	/**
	 * 根据流程定义ID和任务ID获取流程扩展定义
	 */
	@Override
	public ActReElementDef getActReElementDefByProcDefIdTaskKey(String processDefId, String taskKey) {
		ActReElementDef actReElementDef = null;
		if (!StringUtils.isEmpty(processDefId) && !StringUtils.isEmpty(taskKey)) {
			ActReElementDefQuery query = new ActReElementDefQuery();
			query.setProceDefId(processDefId);
			query.setEleID(taskKey);
			List<ActReElementDef> actReElementDefs = actReElementDefMapper.getActReElementDefs(query);
			if (actReElementDefs != null && actReElementDefs.size() == 1) {
				actReElementDef = actReElementDefs.get(0);
			}
		}
		return actReElementDef;
	}

	/**
	 * 获取任务分配人
	 * 
	 * @param ActReElementDef
	 * @return
	 */
	@Override
	public Set<String> getTaskAssign(Map<String, Object> variables, ActReElementDef ActReElementDef, String submitter) {
		Set<String> assigns = new LinkedHashSet<String>();
		String bytes = ActReElementDef.getBytes();
		JSONObject jsonAllObj = null;
		JSONArray jsonArr = null;

		if (!StringUtils.isEmpty(bytes)) {
			// 获取变量内容
			jsonAllObj = JSON.parseObject(bytes);
			if (jsonAllObj != null)
				jsonArr = jsonAllObj.getJSONArray("propertyPackages");
			// 获取任务执行方式
			JSONObject taskrunpropJSON = jsonArr.getJSONObject(1);
			// 获取任务人员安排
			JSONObject assignpropJSON = jsonArr.getJSONObject(2);
			JSONArray assignJSONArr = assignpropJSON.getJSONArray("properties");
			JSONObject assignJSON = null;
			JSONObject assignVal = null;
			String assignType = null;
			String operation = null;
			String assignval = null;
			for (int i = 0; i < assignJSONArr.size(); i++) {
				assignJSON = assignJSONArr.getJSONObject(i);
				assignVal = assignJSON.getJSONObject("value");
				// 获取分派类型、运算操作符、分派值 assignType\operation\assignval
				assignType = assignVal.getString("assignType");
				operation = assignVal.getString("operation");
				assignval = assignVal.getString("assignval");
				Set<String> users = getUsersByAssignTypeAndAssignVal(ActReElementDef, assignType, assignval, submitter,
						variables);
				if (operation.equals("include")) {
					assigns.addAll(users);
				} else if (operation.equals("exclude")) {
					assigns.removeAll(users);
				}
			}
		}
		// 根据流程变量值获取动态分配人
		Set<String> dycUsers = new LinkedHashSet<String>();
		if (variables.containsKey(ActReElementDef.getEleID() + "_user")) {

			if (variables.get(ActReElementDef.getEleID() + "_user") != null) {
				dycUsers = getUsersByAssignTypeAndAssignVal(ActReElementDef, "user",
						(String) variables.get(ActReElementDef.getEleID() + "_user"), null, variables);
			}
		}
		if (variables.containsKey(ActReElementDef.getEleID() + "_role")) {
			if (variables.get(ActReElementDef.getEleID() + "_role") != null) {
				dycUsers = getUsersByAssignTypeAndAssignVal(ActReElementDef, "role",
						(String) variables.get(ActReElementDef.getEleID() + "_role"), null, variables);
			}
		}
		if (variables.containsKey(ActReElementDef.getEleID() + "_submitter")) {
			if (variables.get(ActReElementDef.getEleID() + "_submitter") != null) {
				dycUsers = getUsersByAssignTypeAndAssignVal(ActReElementDef, "submitter", null, submitter, variables);
			}
		}
		if (variables.containsKey(ActReElementDef.getEleID() + "_departRole")) {
			if (variables.get(ActReElementDef.getEleID() + "_departRole") != null) {
				dycUsers = getUsersByAssignTypeAndAssignVal(ActReElementDef, "departRole",
						(String) variables.get(ActReElementDef.getEleID() + "_departRole"), null, variables);
			}
		}
		assigns.addAll(dycUsers);
		return assigns;
	}

	/**
	 * 根据分派类型（用户、角色、部门角色）、设置值获取用户
	 * 
	 * @param assignType
	 * @param assignval
	 * @return
	 */
	public Set<String> getUsersByAssignTypeAndAssignVal(ActReElementDef taskDef, String assignType, String assignval,
			String submitter, Map<String, Object> variables) {
		Set<String> users = new LinkedHashSet<String>();
		if (assignType.equals("user") && !StringUtils.isEmpty(assignval)) {
			users.addAll(Arrays.asList(assignval.split(",")));
		} else if (assignType.equals("role") && !StringUtils.isEmpty(assignval)) {
			List<Long> roleIds = new ArrayList<Long>();
			for (String role : assignval.split(",")) {
				roleIds.add(Long.parseLong(role));
			}
			List<SysUser> roleUsers = sysUserService.getSysUsersByRoleIds(roleIds);
			for (SysUser sysUser : roleUsers) {
				users.add(sysUser.getAccount());
			}
		} else if (assignType.equals(WorkflowConstant.SUBMITTER) && !StringUtils.isEmpty(submitter)) {
			users.add(submitter);
		}else if (assignType.equals("post") && !StringUtils.isEmpty(assignval)) {
			List<String> postCodes = new ArrayList<String>();
			postCodes=Arrays.asList(assignval.split(","));
			//获取岗位用户
			List<String> postUsers=postService.getPostUsersByPostCodeList(postCodes);
			users.addAll(postUsers);
		} else if (assignType.equals("departRole")) {
			//获取提单人部门、上级部门、上上级部门
			if (variables.containsKey(WorkflowConstant.SUBDEPT) && variables.get(WorkflowConstant.SUBDEPT) != null) {
				assignval = StringUtils.replace(assignval, "{subDept}",
						(String) variables.get(WorkflowConstant.SUBDEPT));// assignval.replaceAll("${subDept}",
																			// (String)variables.get(WorkflowConstant.SUBDEPT));
			}
			if (variables.containsKey(WorkflowConstant.SUBPARENTDEPT)
					&& variables.get(WorkflowConstant.SUBPARENTDEPT) != null) {
				assignval = StringUtils.replace(assignval, "{subParentDept}",
						(String) variables.get(WorkflowConstant.SUBPARENTDEPT));
			}
			if (variables.containsKey(WorkflowConstant.SUBGRANDDEPT)
					&& variables.get(WorkflowConstant.SUBGRANDDEPT) != null) {
				assignval = StringUtils.replace(assignval, "{subGrandDept}",
						(String) variables.get(WorkflowConstant.SUBGRANDDEPT));
			}
			if (variables.containsKey(taskDef.getEleID() + "_dynDept")
					&& variables.get(taskDef.getEleID() + "_dynDept") != null) {
				assignval = StringUtils.replace(assignval, "{dynDept}",
						(String) variables.get(taskDef.getEleID() + "_dynDept"));
			}
			String departStr = null;
			String roleId = null;
			String[] roleIds = null;
			List<String> roleList = null;
			List<String> departRoleUsers = null;
			for (String departRole : assignval.split("\\|")) {
				departStr = null;
				roleId = null;
				roleIds = null;
				roleList = null;
				departRoleUsers = null;
				if (departRole.split(":") != null && departRole.split(":").length == 2) {
					departStr = departRole.split(":")[0];
					roleId = departRole.split(":")[1];
					roleId = StringUtils.replace(roleId, "[", "");
					roleId = StringUtils.replace(roleId, "]", "");
					roleIds = roleId.split(",");
					roleList = new ArrayList<String>();
					Collections.addAll(roleList, roleIds);
					departRoleUsers = sysUserRoleService.getDepartRolesUsers(departStr, roleList);
					if (departRoleUsers != null)
						users.addAll(departRoleUsers);
				}
			}
		}
		return users;
	}

	/**
	 * 根据流程定义关键字获取流程变量定义
	 * 
	 * @param processKey
	 * @return
	 */
	@Override
	public List<JSONObject> getVariableJSONByProcessKey(String processKey) {

		List<JSONObject> jsonObjList = null;
		// 根据流程定义关键字获取最新的流程定义
		ProcessDefinition processdef = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey)
				.latestVersion().singleResult();
		if (processdef != null) {
			// 根据流程定义ID获取流程主扩展属性
			ActReElementDefQuery query = new ActReElementDefQuery();
			query.setEleID(processKey);
			query.setProceDefId(processdef.getId());
			query.setEleType("workflow");
			List<ActReElementDef> actReElementDefList = list(query);
			if (actReElementDefList != null && actReElementDefList.size() == 1) {
				ActReElementDef actReElementDef = actReElementDefList.get(0);
				String bytes = actReElementDef.getBytes();
				JSONObject jsonAllObj = null;
				JSONArray jsonArr = null;
				JSONObject varObj = null;
				JSONArray variableArr = null;
				if (!StringUtils.isEmpty(bytes)) {
					// 获取变量内容
					jsonAllObj = JSON.parseObject(bytes);
					if (jsonAllObj != null)
						jsonArr = jsonAllObj.getJSONArray("propertyPackages");
					if (jsonAllObj != null)
						varObj = jsonArr.getJSONObject(1);
					if (varObj != null)
						variableArr = varObj.getJSONArray("properties");
				}
				if (variableArr == null || variableArr.size() == 0) {
					return null;
				} else {
					jsonObjList = new ArrayList<JSONObject>();
				}
				JSONObject jsonObject = null, vjsonObject = null, nJsonObject = null;
				String varCode = null, varName = null, varDataType = null;
				for (int i = 0; i < variableArr.size(); i++) {
					jsonObject = variableArr.getJSONObject(i);
					nJsonObject = new JSONObject();
					// 获取value值
					vjsonObject = jsonObject.getJSONObject("value");
					varCode = vjsonObject.getString("varcode");
					varName = vjsonObject.getString("varname");
					varDataType = vjsonObject.getString("varDataType");
					nJsonObject.put("id", i + 1);
					nJsonObject.put("code", varCode);
					nJsonObject.put("pid", 0);
					nJsonObject.put("name", varName);
					nJsonObject.put("value", varName);
					nJsonObject.put("dType", varDataType);
					jsonObjList.add(nJsonObject);
				}
			}
		}
		return jsonObjList;
	}

	/**
	 * 获取流程变量定义
	 * 
	 * @param modelId
	 * @param resourceId
	 * @param processDefId
	 * @return
	 */
	@Override
	public List<JSONObject> getVariableJSON(String modelId, String resourceId, String processDefId) {

		ActReElementDef ActReElementDef = getActReElementDefByModelIdResourceId(modelId, resourceId, processDefId);
		String bytes = ActReElementDef.getBytes();
		JSONObject jsonAllObj = null;
		JSONArray jsonArr = null;
		JSONObject varObj = null;
		JSONArray variableArr = null;
		if (!StringUtils.isEmpty(bytes)) {
			// 获取变量内容
			jsonAllObj = JSON.parseObject(bytes);
			if (jsonAllObj != null)
				jsonArr = jsonAllObj.getJSONArray("propertyPackages");
			if (jsonAllObj != null)
				varObj = jsonArr.getJSONObject(1);
			if (varObj != null)
				variableArr = varObj.getJSONArray("properties");
		}
		// 构造ztree JSON
		List<JSONObject> jsonObjList = null;
		if (variableArr == null || variableArr.size() == 0) {
			return null;
		} else {
			jsonObjList = new ArrayList<JSONObject>();
		}
		JSONObject jsonObject = null, vjsonObject = null, nJsonObject = null;
		String varCode = null, varName = null, varDataType = null;
		for (int i = 0; i < variableArr.size(); i++) {
			jsonObject = variableArr.getJSONObject(i);
			nJsonObject = new JSONObject();
			// 获取value值
			vjsonObject = jsonObject.getJSONObject("value");
			varCode = vjsonObject.getString("varcode");
			varName = vjsonObject.getString("varname");
			varDataType = vjsonObject.getString("varDataType");
			nJsonObject.put("id", i + 1);
			nJsonObject.put("code", varCode);
			nJsonObject.put("pid", 0);
			nJsonObject.put("name", varName);
			nJsonObject.put("value", varName);
			nJsonObject.put("dType", varDataType);
			jsonObjList.add(nJsonObject);
		}
		return jsonObjList;
	}

	@Transactional
	public void runBatch() {
		logger.debug("-------------------start run-------------------");
		String sql = "  ";// 要运行的SQL语句
		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
			psmt = connection.prepareStatement(sql);
			for (int i = 0; i < 2; i++) {
				psmt.addBatch();
			}
			psmt.executeBatch();
			psmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("run batch error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
		}
		logger.debug("-------------------end run-------------------");
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.workflow.core.mapper.ActReElementDefMapper")
	public void setActReElementDefMapper(ActReElementDefMapper actReElementDefMapper) {
		this.actReElementDefMapper = actReElementDefMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@Override
	public List<ActReElementDef> getWorkflowActReElementDefsByModelIdProDefId(String modelId, String processDefId) {
		if (StringUtils.isEmpty(modelId)) {
			return null;
		}
		return actReElementDefMapper.getWorkflowActReElementDefsByModelIdProDefId(modelId, processDefId);
	}

	@Override
	public Map<String, ActReElementDef> getWorkflowActReElementDefMap(List<ActReElementDef> actReElementDefs) {
		Map<String, ActReElementDef> actReElementDefMap = new HashMap<String, ActReElementDef>();
		for (ActReElementDef actReElementDef : actReElementDefs) {
			actReElementDefMap.put(actReElementDef.getEleResourceId(), actReElementDef);
		}
		return actReElementDefMap;
	}

	@Override
	public Map<String, ActReElementDef> getWorkflowActReElementDefMapByModelIdProDefId(String modelId,
			String processDefId) {
		// TODO Auto-generated method stub
		List<ActReElementDef> actReElementDefs = getWorkflowActReElementDefsByModelIdProDefId(modelId, processDefId);
		Map<String, ActReElementDef> actReElementDefMap = actReElementDefs != null
				? getWorkflowActReElementDefMap(actReElementDefs) : null;
		return actReElementDefMap;
	}

	@Override
	public JSONObject getWorkflowActReElementDefJsonObj(ActReElementDef actReElementDef) {
		if(actReElementDef==null){
			return null;
		}
		JSONObject jsonObj = JSON.parseObject(actReElementDef.getBytes());
		return jsonObj;
	}

	@Override
	@Transactional
	public void updateProcedefIdByModelId(String modelId, String processDefId, String deploymentId) {
		actReElementDefMapper.updateProcedefIdByModelId(modelId, processDefId);
		// 更新模型表ACT_RE_MODEL的部署标识DEPLOYMENT_ID_
		actReElementDefMapper.updateDeploymentIdByModelId(modelId, deploymentId);
	}

	@Override
	@Transactional
	public boolean copyWorkFlow(String modelId, String userId) {
		try {
			// 获取当前model
			Model model = repositoryService.createModelQuery().modelId(modelId).singleResult();
			// 验证当前model是否已发布
			if (model != null && !StringUtils.isEmpty(model.getDeploymentId())) {
				actReElementDefMapper.updateDeploymentIdByModelId(modelId, null);
				// 根据DeploymentId获取PROCEDEF_ID_
				ProcessDefinition procedef = repositoryService.createProcessDefinitionQuery()
						.deploymentId(model.getDeploymentId()).singleResult();
				// 获取最近一次流程定义的扩展配置
				List<ActReElementDef> actReElementDefs = getWorkflowActReElementDefsByModelIdProDefId(modelId,
						procedef.getId());
				// 复制扩展配置并将流程定义标识置为空
				for (ActReElementDef actReElementDef : actReElementDefs) {
					actReElementDef.setID(null);
					actReElementDef.setCreateDatetime(new Date());
					actReElementDef.setCreator(userId);
					actReElementDef.setProceDefId(null);
					save(actReElementDef);
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("copyWorkFlow::::modelId=" + modelId + " " + e.getMessage());
		}
		return false;
	}

	@Override
	public ActReElementDef getActReElementDefByProcessDefinitionIdResourceId(String processDefId, String resourceId) {
		ActReElementDef actReElementDef = actReElementDefMapper
				.getActReElementDefByProcessDefinitionIdResourceId(processDefId, resourceId);
		return actReElementDef;
	}

	@Override
	public void removeDeletedElementsByNotInIds(String modelId,List<String> resourceIds) {
		// TODO Auto-generated method stub
		actReElementDefMapper.removeDeletedElementsByNotInIds(modelId,resourceIds);
	}
}
