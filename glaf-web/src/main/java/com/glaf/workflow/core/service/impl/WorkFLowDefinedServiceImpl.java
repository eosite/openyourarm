package com.glaf.workflow.core.service.impl;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import javax.annotation.Resource;

import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.activiti.service.ActivitiProcessService;
import com.glaf.core.base.TableModel;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.factory.DataServiceFactory;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.Constants;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.ThreadFactory;
import com.glaf.form.core.domain.FormTask;
import com.glaf.form.core.domain.FormTaskmain;
import com.glaf.form.core.domain.FormWorkflowPlan;
import com.glaf.form.core.query.FormTaskQuery;
import com.glaf.form.core.query.FormTaskmainQuery;
import com.glaf.form.core.service.FormRuleService;
import com.glaf.form.core.service.FormTaskService;
import com.glaf.form.core.service.FormTaskmainService;
import com.glaf.form.core.service.FormWorkflowPlanService;
import com.glaf.workflow.core.domain.ActReElementDef;
import com.glaf.workflow.core.service.ActReElementDefService;
import com.glaf.workflow.core.service.TaskExtService;
import com.glaf.workflow.core.service.WorkFlowDefinedService;
import com.glaf.workflow.core.util.WorkflowConstant;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Service("workFLowDefinedService")
@Transactional(readOnly = true)
public class WorkFLowDefinedServiceImpl implements WorkFlowDefinedService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected RuntimeService runtimeService;

	protected ActReElementDefService actReElementDefService;

	protected ActivitiProcessService activitiProcessService;

	protected FormTaskmainService formTaskmainService;

	protected FormTaskService formTaskService;

	protected FormRuleService formRuleService;

	protected TaskExtService taskExtService;

	protected IdGenerator idGenerator;

	@Autowired
	protected FormWorkflowPlanService formWorkflowPlanService;

	@Autowired
	protected TaskService taskService;

	protected JdbcTemplate jdbcTemplate;

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setFormRuleService(FormRuleService formRuleService) {
		this.formRuleService = formRuleService;
	}

	@Resource
	public void setFormTaskService(FormTaskService formTaskService) {
		this.formTaskService = formTaskService;
	}

	@Resource
	public void setActivitiProcessService(ActivitiProcessService activitiProcessService) {
		this.activitiProcessService = activitiProcessService;
	}

	@Resource
	public void setFormTaskmainService(FormTaskmainService formTaskmainService) {
		this.formTaskmainService = formTaskmainService;
	}

	@Resource
	public void setActReElementDefService(ActReElementDefService actReElementDefService) {
		this.actReElementDefService = actReElementDefService;
	}

	@Resource
	public void setTaskExtService(TaskExtService taskExtService) {
		this.taskExtService = taskExtService;
	}

	protected RepositoryService repositoryService;

	@Resource
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@Override
	public Template getElemsetTemplate(String elemType) {
		String resourceName = "";
		Template template = null;
		if (elemType != null) {
			elemType = getJsonElemType(elemType);
			resourceName = "workflow_" + elemType + "_set" + ".json";
			Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
			configuration.setDefaultEncoding("utf-8");
			configuration.setClassForTemplateLoading(this.getClass(), "/");
			try {
				template = configuration.getTemplate(resourceName);
			} catch (TemplateNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedTemplateNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return template;
	}

	/**
	 * 获取流程元素定义模板JSON
	 */
	@Override
	public String getElemsetTemplateJson(String elemType) {
		String elemset = null;
		try {
			Template template = getElemsetTemplate(elemType);
			StringWriter stringWriter = new StringWriter();
			BufferedWriter writer = new BufferedWriter(stringWriter);
			Map<String, Object> root = new HashMap<String, Object>();
			template.process(root, stringWriter);
			elemset = stringWriter.toString();
			writer.flush();
			writer.close();
		} catch (Exception e) {
			logger.error("Error while loading workflow elemset" + e.getMessage());
		}
		return elemset;
	}

	public String getJsonElemType(String elemType) {
		if (elemType.equals("UserTask")) {
			elemType = "userTask";
		} else if (elemType.equals("SequenceFlow")) {
			elemType = "flow";
		}
		return elemType;
	}

	/**
	 * 根据用户输入返回流程元素定义JSON
	 * 
	 * @param elemType
	 *            元素类型（如：用户任务、连接线）
	 * @param fieldMap
	 *            表单元素序列号后值对象 通过request.getParameterMap获取
	 * @return
	 * @throws TemplateException
	 * @throws IOException
	 */
	@Override
	public String getElemsetJsonByTemplate(String elemType, Map<String, String[]> fieldMap)
			throws TemplateException, IOException {
		String elemset = "";
		Template template = getElemsetTemplate(elemType);
		try {
			if (template != null) {
				Map<String, Object> templateVal = new HashMap<String, Object>();
				if (fieldMap != null) {
					for (Entry<String, String[]> entry : fieldMap.entrySet()) {
						templateVal.put(entry.getKey(), entry.getValue());
					}
				}
				StringWriter stringWriter = new StringWriter();
				BufferedWriter writer = new BufferedWriter(stringWriter);
				template.process(templateVal, stringWriter);
				elemset = stringWriter.toString();
				writer.flush();
				writer.close();
			}
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			throw e;
		}

		return elemset;
	}

	@Override
	public JSONObject saveElemSet(String creator, String modelId, String elemType, String resourceId,
			Map<String, String[]> paramMap) {
		String elemDefJson = null;
		JSONObject jsonObj = new JSONObject();
		try {
			if (paramMap != null && !StringUtils.isEmpty(elemType) && !StringUtils.isEmpty(resourceId)) {
				elemDefJson = getElemsetJsonByTemplate(elemType, paramMap);
				actReElementDefService.deleteActReElementDefByModelIdResourceId(modelId, resourceId);
				ActReElementDef actReElementDef = new ActReElementDef();
				actReElementDef.setModelId(modelId);
				if (paramMap.get("id") != null)
					actReElementDef.setEleID(paramMap.get("id")[0]);
				if (paramMap.get("name") != null)
					actReElementDef.setEleName(paramMap.get("name")[0]);
				if (paramMap.get("description") != null) {
					actReElementDef.setEleDesc(paramMap.get("description")[0]);
				}
				int submitterTaskFlag = 0;
				if (paramMap.containsKey("submitterTask") && paramMap.get("submitterTask") != null) {
					submitterTaskFlag = Integer.parseInt(paramMap.get("submitterTask")[0]);
				}
				actReElementDef.setSubmitterTaskFlag(submitterTaskFlag);
				actReElementDef.setEleResourceId(resourceId);
				if (elemType.equals("CallActivity")) {
					if (paramMap.containsKey("calledProcessKey")) {
						actReElementDef.setSubProcessKey(paramMap.get("calledProcessKey")[0]);
					}
				}
				actReElementDef.setEleType(elemType);
				actReElementDef.setCreator(creator);
				actReElementDef.setCreateDatetime(new Date());
				actReElementDef.setBytes(elemDefJson);
				actReElementDefService.save(actReElementDef);
				jsonObj.put("result", 1);
			} else {
				jsonObj.put("result", 0);
			}

		} catch (Exception e) {
			logger.error("保存流程定义属性规则出错：" + e.getMessage());
			jsonObj.put("result", -1);
			e.printStackTrace();
		}
		return jsonObj;
	}

	@Override
	public ProcessDefinition getProcessDefinitionByDeploymentId(String deploymentId) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.deploymentId(deploymentId).singleResult();
		return processDefinition;
	}

	@Override
	public String getProcessDiagramByModelId(String rootPath, Model modelData) {
		byte[] editorSourceExtra = repositoryService.getModelEditorSourceExtra(modelData.getId());
		InputStream inStream = null;
		String fileParentPath = null;
		String filePath = null;
		String relPath = null;
		File saveFileDoc = null;
		if (editorSourceExtra != null) {
			try {

				String fileName = modelData.getId() + "_" + modelData.getKey() + ".png";
				fileParentPath = rootPath + "/workflow/diagram/";
				saveFileDoc = new File(fileParentPath);
				if (!saveFileDoc.exists()) {
					saveFileDoc.mkdirs();
				}
				filePath = fileParentPath + fileName;
				if (!new File(filePath).exists()) {
					inStream = new ByteArrayInputStream(editorSourceExtra);
					FileUtils.save(filePath, inStream);
				}
				relPath = "/workflow/diagram/" + fileName;
			} catch (Exception e) {
				logger.error("getProcessDiagramByModelId:" + e.getMessage());
			} finally {
				if (inStream != null) {
					try {
						inStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					inStream = null;
				}
			}
		}
		return relPath;
	}

	/**
	 * 生成流程图
	 * 
	 * @param rootPath
	 * @param proDefId
	 * @return
	 */
	@Override
	public String getProcessDiagramByDeployId(String rootPath, String deployId) {

		String fileParentPath = null;
		String filePath = null;
		String relPath = null;
		File saveFileDoc = null;
		InputStream inStream = null;
		try {

			List<String> names = repositoryService.getDeploymentResourceNames(deployId);
			String imageName = null;
			for (String name : names) {
				if (name.indexOf(".png") >= 0) {
					imageName = name;
				}
			}
			if (imageName != null) {
				// 通过部署ID和文件名称得到文件的输入流
				inStream = repositoryService.getResourceAsStream(deployId, imageName);
				fileParentPath = rootPath + "/workflow/diagram/";
				saveFileDoc = new File(fileParentPath);
				if (!saveFileDoc.exists()) {
					saveFileDoc.mkdirs();
				}
				String fileName = deployId + ".png";
				filePath = fileParentPath + fileName;
				if (!new File(filePath).exists()) {
					FileUtils.save(filePath, inStream);
				}
				relPath = "/workflow/diagram/" + fileName;
			}
		} catch (Exception e) {
			logger.error("getProcessDiagramByModelId:" + e.getMessage());
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				inStream = null;
			}
		}
		return relPath;
	}

	@Override
	public ProcessInstance getProcessInstanceByDKeyAndPKeyAndBKey(String processDefinitionKey, String pageId,
			String id) {

		String bussinessKey = pageId + "_0_" + id;// key规则自己规定

		return this.getProcessInstanceByDefKeyKeyAndBussinessKey(processDefinitionKey, bussinessKey);
	}

	@Override
	public ProcessInstance getProcessInstanceByDefKeyKeyAndBussinessKey(String processDefinitionKey,
			String bussinessKey) {

		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processDefinitionKey(processDefinitionKey).processInstanceBusinessKey(bussinessKey).singleResult();

		return processInstance;
	}

	@Override
	public Set<String> getUserTaskAssigns(ActivityExecution execution) {
		String processInstId = execution.getProcessInstanceId();
		// String fromKey=null;
		// if(processInstId!=null&&!processInstId.equals(execution.getId())){
		// fromKey=taskExtService.getActIdByProcessInstId(processInstId);
		// }
		// 获取任务对应的规则
		String processDefinitionId = execution.getProcessDefinitionId();
		String taskDefinitionKey = execution.getCurrentActivityId();
		String submitter = execution.getVariable("submitter") != null ? (String) execution.getVariable("submitter")
				: "";
		ActReElementDef actReElementDef = actReElementDefService
				.getActReElementDefByProcDefIdTaskKey(processDefinitionId, taskDefinitionKey);
		// 获取任务执行者
		Map<String, Object> variables = execution.getVariables();
		/*
		 * if (fromKey != null) { if (variables.containsKey(fromKey + "_user")) {
		 * variables.put(actReElementDef.getEleID() + "_user", variables.get(fromKey +
		 * "_user")); variables.remove(fromKey + "_user");
		 * //taskExtService.removeVariable(processInstId, fromKey + "_user");
		 * //execution.removeVariable(fromKey + "_user");
		 * 
		 * // runtimeService.removeVariable(execution.getId(), fromKey + "_user");
		 * 
		 * } }
		 */
		// 获取退回标识
		Boolean approve = true;
		if (variables.containsKey("approve")) {
			approve = (Boolean) variables.get("approve");
		}
		// 获取任务执行者
		Set<String> assigns = actReElementDef != null
				? actReElementDefService.getTaskAssign(variables, actReElementDef, submitter)
				: null;
		if ((assigns == null || assigns.size() == 0)
				&& (!approve || (variables.containsKey(WorkflowConstant.WF_VAR_IS_REJECTED)
						&& (Integer) variables.get(WorkflowConstant.WF_VAR_IS_REJECTED) == 1))) {
			assigns = new HashSet<String>();
			// 获取最近一次任务的执行者
			List<String> lastAssignee = taskExtService.getTaskLastAssignee(execution.getProcessInstanceId(),
					taskDefinitionKey);
			if (lastAssignee != null) {
				assigns.addAll(lastAssignee);
			}
		}
		// 获取当前提交的任务key
		String currentTask = (String) variables.get("currentTask");
		// 将本节点外地其它节点的动态用户参数值清空
		String key = null;
		for (Entry<String, Object> entry : variables.entrySet()) {
			key = entry.getKey();
			if ((key.endsWith("_user") && !key.equals(taskDefinitionKey + "_user")) || key.equals(currentTask)) {
				// execution.setVariableLocal(key, "");
			}
		}
		// execution.setVariable(variableName, value);
		if (assigns.size() == 0 && variables.containsKey(currentTask + "_assignee")) {
			assigns.add((String) variables.get(currentTask + "_assignee"));
		}
		return assigns;
	}

	@Resource
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	@Override
	public PvmActivity getNextTaskDefinition(String procInstId, String processDefinitionId) {
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		List<ActivityImpl> activitiList = def.getActivities(); // rs是指RepositoryService的实例
		// 当前实例的执行到哪个节点
		ExecutionEntity execution = (ExecutionEntity) runtimeService.createProcessInstanceQuery()
				.processInstanceId(procInstId).singleResult();
		if (execution == null) {
			return null;
		}
		String activitiId = execution.getActivityId();
		if (activitiId == null) {
			return null;
		}

		for (ActivityImpl activityImpl : activitiList) {
			String id = activityImpl.getId();
			if (activitiId.equals(id)) {
				// System.out.println("当前任务：" +
				// activityImpl.getProperty("name")); // 输出某个节点的某种属性
				List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();// 获取从某个节点出来的所有线路
				for (PvmTransition tr : outTransitions) {
					PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
					// System.out.println("下一步任务任务：" + ac.getId()+
					// ac.getProperty("name"));

					return ac;
				}
				break;
			}
		}

		return null;
	}

	@Override
	public PvmActivity getPrevTaskDefinition(String procInstId, String processDefinitionId) {
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		List<ActivityImpl> activitiList = def.getActivities(); // rs是指RepositoryService的实例
		// 当前实例的执行到哪个节点
		ExecutionEntity execution = (ExecutionEntity) runtimeService.createProcessInstanceQuery()
				.processInstanceId(procInstId).singleResult();
		if (execution == null) {
			return null;
		}
		String activitiId = execution.getActivityId();

		for (ActivityImpl activityImpl : activitiList) {
			String id = activityImpl.getId();
			if (activitiId.equals(id)) {
				// System.out.println("当前任务：" +
				// activityImpl.getProperty("name")); // 输出某个节点的某种属性
				List<PvmTransition> inTransitions = activityImpl.getIncomingTransitions();// 获取从某个节点出来的所有线路
				for (PvmTransition tr : inTransitions) {
					PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
					// System.out.println("上一步任务任务：" + ac.getId()+
					// ac.getProperty("name"));
					return ac;
				}
				break;
			}
		}

		return null;
	}

	private static final String p_processIdKey = "__p_processIdKey__"//
			, sub = "__SUB__", processIdKey = "__processIdKey__", WBSIDKEY = "wbsId";

	@Override
	@Transactional
	public ProcessInstance startProcess(List<FormWorkflowPlan> formWorkflowPlans, Map<String, Object> params,
			String actorId) {
		FormWorkflowPlan plan;
		if (CollectionUtils.isNotEmpty(formWorkflowPlans) && (plan = formWorkflowPlans.get(0)) != null) {
			String defId = plan.getDefId();
			FormTaskmain formTaskmain = new FormTaskmain();
			formTaskmain.setCreateBy(actorId);
			formTaskmain.setCreateDate(new Date());
			formTaskmain.setDefinedId(plan.getProcessDefId());
			formTaskmain.setPlanId(Long.parseLong(plan.getId()));
			formTaskmain.setVariableVal(MapUtils.getString(params, WBSIDKEY));// wbs
																				// 节点id
			if (StringUtils.isNotBlank(plan.getBytes())) {
				try {
					JSONObject json = JSONArray.parseArray(plan.getBytes()).getJSONObject(0);
					formTaskmain.setName(json.getString("name"));
				} catch (Exception ex) {
					logger.error(ex.getMessage());
				}
			}
			if (StringUtils.isNotEmpty(MapUtils.getString(params, "name"))) {
				formTaskmain.setName(MapUtils.getString(params, "name"));
			}
			formTaskmain.setDefId(defId);

			JSONObject processDefined = JSONArray.parseArray(plan.getBytes()).getJSONObject(0);
			params.put(Constants.PROCESS_STARTER, actorId);

			/*
			 * ProcessInstance processInstance =
			 * this.activitiProcessService.startProcessInstanceByKey(actorId,
			 * processDefined.getString("key"), defId, params);
			 */

			String p_processId = null, processId = null;

			ProcessInstance processInstance = null;

			if (!params.containsKey(sub)) { // 启动流程
				processInstance = taskExtService.startProcessInstanceByKeySkipEmptyTask(//
						actorId, processDefined.getString("key"), defId, params);
				this.runSubProcess(params, actorId, processId = processInstance.getId(), defId);
			} else {
				processId = MapUtils.getString(params, processIdKey);
				p_processId = MapUtils.getString(params, p_processIdKey);
				params.remove(sub);
				params.remove(processIdKey);
				params.remove(p_processIdKey);
			}

			formTaskmain.setP_processId(p_processId);
			formTaskmain.setProcessId(processId);

			this.formTaskmainService.save(formTaskmain);

			this.saveFormTask(formWorkflowPlans, formTaskmain, new JSONObject(params), actorId);

			return processInstance;
		}
		return null;
	}

	/**
	 * 处理子流程业务
	 */
	public void runSubProcess(Map<String, Object> params//
			, String actorId, String processId, String defId) {
		ProcessInstance processInstance_;
		Vector<String> vector = taskExtService.getSubProcessInstBySuperProcessInst(processId);
		if (CollectionUtils.isNotEmpty(vector)) {
			Map<String, Object> params_ = new JSONObject();
			params_.put("defId", defId);
			for (String processId_ : vector) {
				try {
					processInstance_ = this.getProcessInstanceFromRequest(processId_);
					if (processInstance_ != null) {
						String key = processInstance_.getProcessDefinitionKey();
						params_.put("key", key);
						List<FormWorkflowPlan> formWorkflowPlans_ = formWorkflowPlanService
								.getFormWorkflowPlansWithTree(params_);
						params.put(sub, true);
						params.put(processIdKey, processId_);
						params.put(p_processIdKey, processId);
						this.startProcess(formWorkflowPlans_, params, actorId);
					}
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					continue;
				}
			}
		}
	}

	@Transactional
	@Override
	public void saveFormTask(List<FormWorkflowPlan> formWorkflowPlans, FormTaskmain formTaskmain,
			Map<String, Object> params, String actorId) {
		/*
		 * String pageId, variable; Object idValue; FormTask formTask; TableModel tm;
		 * Set<String> pages = new HashSet<String>(); if (formTaskmain != null) {
		 * List<FormTask> tasks = formTaskmain.getFormTasks(); if
		 * (CollectionUtils.isNotEmpty(tasks)) { for (FormTask task : tasks) {
		 * pages.add(task.getPageId()); } } } for (FormWorkflowPlan p :
		 * formWorkflowPlans) { pageId = p.getPageId(); if (pages.contains(pageId)) {
		 * continue; } tm = new TableModel(); idValue = params.get(pageId); if (idValue
		 * != null) { // 页面有执行保存
		 * tm.setTableName(formRuleService.getTableNameByPageId(pageId));
		 * tm.setPrimaryKey(idValue.toString()); } else { // 自动生成一条数据 idValue = ""; tm =
		 * this.createNewInstanceByPageId(pageId, actorId); }
		 * 
		 * formTask = new FormTask(); formTask.setCreateBy(actorId);
		 * formTask.setPageId(pageId); formTask.setCreateDate(new Date());
		 * formTask.setTmId(formTaskmain.getId()); if (tm != null) {
		 * formTask.setIdValue(tm.getPrimaryKey());
		 * formTask.setTableName(tm.getTableName()); } else { return; } variable =
		 * this.getIdVariable(pageId, "id"); formTask.setVariableKey(variable);
		 * this.formTaskService.save(formTask); }
		 */

		// 启动线程执行保存历史等等操作
		ThreadFactory.execute(new SaveFormTaskRunner(formWorkflowPlans, formTaskmain, new JSONObject(params), actorId));
	}

	protected class SaveFormTaskRunner implements Runnable {
		List<FormWorkflowPlan> formWorkflowPlans;
		FormTaskmain formTaskmain;
		Map<String, Object> params;
		String actorId;
		WorkFlowDefinedService workFlowDefinedService = ContextFactory.getBean("workFLowDefinedService");

		public SaveFormTaskRunner(List<FormWorkflowPlan> formWorkflowPlans, FormTaskmain formTaskmain,
				Map<String, Object> params, String actorId) {
			this.formWorkflowPlans = formWorkflowPlans;
			this.formTaskmain = formTaskmain;
			this.params = params;
			this.actorId = actorId;
		}

		@Override
		public void run() {

			TableModel tm;
			FormTask formTask;
			String pageId, variable, idValue;
			Set<String> pages = new HashSet<String>(), runningPages = new HashSet<>();
			if (formTaskmain != null) {
				List<FormTask> tasks = formTaskmain.getFormTasks();
				if (CollectionUtils.isNotEmpty(tasks)) {
					for (FormTask task : tasks) {
						pages.add(task.getPageId());
					}
				}

				/**
				 * 当前大流程下已经启动的所有表单页面
				 */
				if (StringUtils.isNotBlank(formTaskmain.getP_processId())) {
					FormTaskmainQuery query = new FormTaskmainQuery();
					query.setP_processId(formTaskmain.getP_processId());
					List<FormTaskmain> mains = formTaskmainService.list(query);
					if (CollectionUtils.isNotEmpty(mains)) {
						mains.forEach(e -> {
							if (e.getProcessId().equals(formTaskmain.getProcessId())) {
								return;
							}
							e.getFormTasks().forEach(t -> {
								runningPages.add(t.getPageId());
							});
						});
					}
				}

			}

			Map<String, Set<String>> relation = formWorkflowPlanService//
					.getRelationPage(formWorkflowPlans);

			for (FormWorkflowPlan p : formWorkflowPlans) {
				pageId = p.getPageId();

				if (pageId == null) {
					continue;
				}

				/**
				 * 排除当前表单页面在同一个层级流程上已经启动过的表单
				 */
				if (runningPages.contains(pageId)) {
					continue;
				}

				idValue = MapUtils.getString(params, pageId);

				/**
				 * 如果值为空,可能是其他(不同应用)页面已经保存
				 */
				if (StringUtils.isBlank(idValue) && relation.containsKey(pageId)) {// "同一"页面不同终端运行
					Set<String> set = relation.get(pageId);
					if (set != null && set.size() > 1) {
						continue;
					}
				}

				this.createAndInsert(p, idValue);
				if (pages.contains(pageId)) {// 插入表单流程任务表
					continue;
				} else {
					pages.add(pageId);
				}
				tm = null;
				if (StringUtils.isNotBlank(idValue)) { // 页面有执行保存
					tm = new TableModel();
					tm.setTableName(formRuleService.getTableNameByPageId(pageId));
					tm.setPrimaryKey(idValue.toString());

					/**
					 * 判断该实例有没有记录
					 */
					if (formTaskmain != null) {
						FormTaskQuery query = new FormTaskQuery();
						query.setTmId(formTaskmain.getId());
						query.setIdValue(idValue);
						query.setTableName(tm.getTableName());
						List<FormTask> tasks = formTaskService.list(query);
						if (CollectionUtils.isNotEmpty(tasks)) {
							continue;
						}
					}
				} else if (!params.containsKey(WorkflowConstant.AUTOSTART)) { // 自动生成一条数据
					idValue = "";
					tm = workFlowDefinedService.createNewInstanceByPageId(pageId, actorId);
					if (tm != null) {
						tm.addColumn("index_id", "Integer", MapUtils.getString(params//
								, WBSIDKEY, formTaskmain.getVariableVal()));
						DataServiceFactory.getInstance().insertTableData(tm);
					}
				}

				formTask = new FormTask();
				formTask.setCreateBy(actorId);
				formTask.setPageId(pageId);
				formTask.setCreateDate(new Date());
				formTask.setTmId(formTaskmain.getId());
				if (tm != null) {
					formTask.setIdValue(tm.getPrimaryKey());
					formTask.setTableName(tm.getTableName());
				} else {
					continue;
				}
				variable = workFlowDefinedService.getIdVariable(pageId, "id");
				formTask.setVariableKey(variable);

				formTaskService.save(formTask);
			}
		}

		// 新建历史表并插数据到该表
		void createAndInsert(FormWorkflowPlan plan, Object idValue) {
			if (idValue == null || StringUtils.isEmpty(idValue.toString()) || //
					StringUtils.isEmpty(plan.getPageId())) {
				return;
			}

			if (!params.containsKey(WorkflowConstant.TASKID) || //
					!params.containsKey(WorkflowConstant.PROCESSID))
				return;

			String pageId = plan.getPageId();

			String tableName = formRuleService.getTableNameByPageId(pageId);

			if (StringUtils.isEmpty(tableName))
				return;

			List<ColumnDefinition> dfs = DBUtils.getColumnDefinitions(tableName);

			TableDefinition tableDefinition = new TableDefinition();
			tableDefinition.setTableName(tableName + WorkflowConstant.FLOWTABLEHIS);
			tableDefinition.setColumns(dfs);

			String[] fields = new String[] { "idValue", "CREATEDATE_", //
					// "CREATEBY_",
					WorkflowConstant.PROCESSID, WorkflowConstant.TASKID };

			Set<String> fields1 = new HashSet<String>();
			for (String field : fields) {
				fields1.add(field.toUpperCase());
			}

			params.put("idValue", idValue);
			Boolean exists = DBUtils.tableExists(tableDefinition.getTableName());

			params.put("CREATEDATE_", "GETDATE()");

			// params.put("CREATEBY_", actorId);

			if (!exists) { // 不存在需要建表
				for (String field : fields) {
					ColumnDefinition cdf = new ColumnDefinition();
					cdf.setColumnName(field);
					if (StringUtils.startsWithIgnoreCase(field, "createDate")) {
						cdf.setJavaType("Date");
					} else {
						cdf.setJavaType("String");
						cdf.setLength(50);
					}
					dfs.add(cdf);
				}
				DBUtils.createTable(tableDefinition);
			} else { // 检查表结构
				DBUtils.alterTable(tableDefinition);
			}

			Set<String> set = new HashSet<String>();
			for (ColumnDefinition cdf : dfs) {
				if (cdf.getColumnName().equalsIgnoreCase("id"))
					continue;
				if (!fields1.contains(cdf.getColumnName().toUpperCase()))
					set.add(cdf.getColumnName());
			}

			String join = ", ", fieldsStr = StringUtils.join(set, join), idField = "id";

			StringBuffer sb = new StringBuffer("INSERT INTO ");
			sb.append(tableDefinition.getTableName()).append(" (");
			sb.append(fieldsStr).append(join).append(StringUtils.join(fields, join)).append(", ").append(idField);

			sb.append(") SELECT ").append(fieldsStr);

			Object value;
			for (String field : fields) {
				value = params.get(field);
				if (value == null)
					value = "";
				if (!StringUtils.startsWithIgnoreCase(field, "createDate")) {
					value = "'" + value + "'";
				}
				sb.append(join).append(value).append(" AS ").append(field);
			}
			sb.append(join).append("'").append(idGenerator.getNextId(//
					tableDefinition.getTableName(), idField, actorId))//
					.append("' AS ").append(idField);

			sb.append(" FROM ").append(tableName).append(" WHERE ").append(idField).append(" = '");
			sb.append(idValue).append("'");

			String sql = sb.toString();
			logger.debug(sql);

			jdbcTemplate.execute(sql);
		}
	}

	@Override
	public String getIdVariable(String pageId, String idKey) {
		return formRuleService.getIdVariable(pageId, idKey);
	}

	@Override
	public TableModel createNewInstanceByPageId(String pageId, String actorId) {
		TableModel tableModel = null;
		String tableName = formRuleService.getTableNameByPageId(pageId), id;
		if (StringUtils.isNotBlank(tableName)) {
			tableModel = new TableModel();
			tableModel.setTableName(tableName);
			id = idGenerator.getNextId(tableModel.getTableName(), "id", actorId);
			tableModel.addColumn("id", "String", id);
			tableModel.setPrimaryKey(id);
			this.setOtherField(tableModel);
			// DataServiceFactory.getInstance().insertTableData(tableModel);
		}
		return tableModel;
	}

	/**
	 * 公共字段 set set set
	 * 
	 * @param tableModel
	 */
	private void setOtherField(TableModel tableModel) {
		// ctime
		tableModel.addColumn("ctime", "Date", new Date());
	}

	@Override
	public String getSubmitterByProcessId(String processId) {
		String actorId = null;
		List<Task> taskEntityList = taskService.createTaskQuery().processInstanceId(processId).list();
		if (CollectionUtils.isNotEmpty(taskEntityList)) {
			Map<String, Object> map = null;
			String keys[] = new String[] { "submitter", Constants.PROCESS_STARTER };
			h1: for (Task task : taskEntityList) {
				map = taskService.getVariables(task.getId());
				if (MapUtils.isNotEmpty(map)) {
					for (String key : keys) {
						if (map.containsKey(key)) {
							actorId = map.get(key).toString();
							break h1;
						}
					}
				}
			}
		}
		return actorId;
	}

	protected ProcessInstance getProcessInstanceFromRequest(String processInstanceId) {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		if (processInstance == null) {
			throw new ActivitiObjectNotFoundException("未找到流程实例 id '" + processInstanceId + "'.");
		}
		return processInstance;
	}

	@Override
	public ProcessDefinition getProcessDefinitionByKey(String key) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()//
				.processDefinitionKey(key).latestVersion().singleResult();
		return processDefinition;
	}
}
