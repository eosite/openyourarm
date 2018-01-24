package com.glaf.workflow.web.springmvc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.activiti.model.ProcessContext;
import com.glaf.activiti.service.ActivitiProcessService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.factory.DataServiceFactory;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.dep.base.factory.DataProcessFactory;
import com.glaf.form.core.domain.ActReBusiness;
import com.glaf.form.core.domain.FormTaskmain;
import com.glaf.form.core.domain.FormWorkFlowRule;
import com.glaf.form.core.domain.FormWorkflowPlan;
import com.glaf.form.core.query.ActReBusinessQuery;
import com.glaf.form.core.query.FormTaskmainQuery;
import com.glaf.form.core.query.FormWorkFlowRuleQuery;
import com.glaf.form.core.query.FormWorkflowPlanQuery;
import com.glaf.form.core.service.ActReBusinessService;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.service.FormRuleService;
import com.glaf.form.core.service.FormTaskService;
import com.glaf.form.core.service.FormTaskmainService;
import com.glaf.form.core.service.FormWorkFlowRuleService;
import com.glaf.form.core.service.FormWorkflowPlanService;
import com.glaf.workflow.core.domain.ActReElementDef;
import com.glaf.workflow.core.domain.ActReTaskHis;
import com.glaf.workflow.core.domain.PageUserTask;
import com.glaf.workflow.core.domain.ProcessInsMapping;
import com.glaf.workflow.core.query.PageUserTaskQuery;
import com.glaf.workflow.core.query.ProcessInsMappingQuery;
import com.glaf.workflow.core.service.ActReElementDefService;
import com.glaf.workflow.core.service.ActReTaskHisService;
import com.glaf.workflow.core.service.IPageUserTaskService;
import com.glaf.workflow.core.service.ProcessInsMappingService;
import com.glaf.workflow.core.service.TaskExtService;
import com.glaf.workflow.core.service.WorkFlowDefinedService;
import com.glaf.workflow.core.util.WorkflowConstant;
import com.google.protobuf.ServiceException;

@Controller("/form/workflow/defined")
@RequestMapping("/form/workflow/defined")
public class FormWorkFlowDefinedController {

	protected FormRuleService formRuleService;

	protected FormRulePropertyService formRulePropertyService;

	protected WorkFlowDefinedService workFlowDefinedService;

	protected RepositoryService repositoryService;

	protected ActivitiProcessService activitiProcessService;

	protected ActReElementDefService actReElementDefService;

	protected IPageUserTaskService pageUserTaskService;

	protected TaskService taskService;

	@Autowired
	protected RuntimeService runtimeService;

	protected ProcessEngine processEngine;

	protected ActReBusinessService actReBusinessService;

	protected IdGenerator idGenerator;

	protected FormWorkflowPlanService formWorkflowPlanService;

	@Autowired
	protected FormWorkFlowRuleService formWorkFlowRuleService;

	protected FormTaskmainService formTaskmainService;

	protected FormTaskService formTaskService;

	protected DataSetService dataSetService;

	@Autowired
	protected TaskExtService taskExtService;

	@Autowired
	protected ProcessInsMappingService processInsMappingService;

	@Autowired
	private ActReTaskHisService actReTaskHisService;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		return new ModelAndView("/workflow/form_wf_defined", modelMap);
	}

	/**
	 * 获取某个流程定义的所有节点
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showImageAndDiv")
	public ModelAndView showImageAndDiv(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		String processDefinitionId = RequestUtils.getString(request, "processDefinitionId");

		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);

		List<ActivityImpl> list = def.getActivities();
		if (list != null) {
			StringBuilder SB = new StringBuilder(
					"<input class='processed-name' hidden=true value='" + def.getName() + "' ></input>");
			Element e;
			for (ActivityImpl ai : list) {

				Map<String, Object> properties = ai.getProperties();

				e = new Element(Tag.valueOf("div"), "");
				if (properties != null) {
					if (this.typeFilter(properties.get("type"))) {// 只选择任务节点
						continue;
					}
					e.attr("taskName", properties.get("name") + "");
					e.attr("json", JSON.toJSONString(properties));
				}

				e.attr("class", "processed").attr("id", ai.getId());
				e.attr("style", "left:" + this.get(ai.getX()) + "px;top:" + this.get(ai.getY()) + "px;width:"
						+ this.get(ai.getWidth()) + "px;height:" + this.get(ai.getHeight()) + "px;");

				e.attr("onclick", "parent.showSelectProperties(this);");

				SB.append(e.toString());
			}
			params.put("x_script", SB.toString());
		}
		modelMap.putAll(params);
		return new ModelAndView("/activiti/view/view", modelMap);
	}

	private Set<String> filters = new HashSet<String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add("usertask");
			add("startevent");
		}
	};

	private boolean typeFilter(Object type) {
		if (type == null || !(filters.contains(type.toString().toLowerCase()))) {
			return true;
		}
		return false;
	}

	private int get(int i) {
		return i - 2;
	}

	/**
	 * 流程提交
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("processSubmit")
	public byte[] processSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {

		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		JSONObject result = new JSONObject();

		String id = ParamUtil.getString(params, "id"); // 业务表id

		String pageId = ParamUtil.getString(params, "pageId"); // 表单页面id

		if (loginContext == null) {
			throw new RuntimeException("用户为空.....");
		}
		// if (id == null || pageId == null) {
		if (pageId == null) {
			throw new RuntimeException("流程提交参数不对.....");
		}
		String actorId = loginContext.getActorId();
		ActReBusiness actReBusiness = null;
		ActReBusinessQuery query = new ActReBusinessQuery();
		query.setPageId(pageId);
		query.setBusValue(id);
		List<ActReBusiness> list = actReBusinessService.list(query);
		if (list != null && !list.isEmpty()) {
			actReBusiness = list.get(0);
		}

		processList = new ArrayList<String>();
		if (actReBusiness == null) { // 流程未启动
			Map<String, String> ruleMap = null;
			Boolean multiple = RequestUtils.getBoolean(request, "multiple");// 多流程
			if (multiple) {
				String ruleId = RequestUtils.getString(request, "rid");
				ruleMap = this.formRulePropertyService.getRuleMap(ruleId);
				String selectPro = "selectPro";
				String defId;
				JSONObject prObject;
				if (ruleMap.containsKey(selectPro)) { // 列表启动
					selectPro = ruleMap.get(selectPro);
					if (StringUtils.isNotBlank(selectPro)) {
						JSONObject selectProJson = JSON.parseArray(selectPro).getJSONObject(0);
						JSONArray datas = selectProJson.getJSONObject("data").getJSONArray("datas");
						if (datas != null && !datas.isEmpty()) {
							for (int i = 0; i < datas.size(); i++) {
								prObject = datas.getJSONObject(i);
								defId = prObject.getString("defId");
								this.multipleSubmitByDefId(defId, actorId, id, params);
							}
						}
					}
				} else if (ruleMap.containsKey("dataSourceSetByFlow")) { // 动态传参启动
					String dataSourceSetByFlow = ruleMap.get("dataSourceSetByFlow");
					if (StringUtils.isNotBlank(dataSourceSetByFlow)) {
						String flowParams = ParamUtils.getString(params, "flowParams");
						if (StringUtils.isNotBlank(flowParams)) {
							JSONObject parameter = JSON.parseObject(flowParams), column;

							JSONObject dataSource = JSONArray.parseArray(dataSourceSetByFlow).getJSONObject(0);

							String datasetId = dataSource.getJSONArray("selectDatasource").getJSONObject(0)
									.getString("datasetId"), columnName = "";

							JSONArray selectColumns = dataSource.getJSONArray("selectColumns");
							for (int i = 0; i < selectColumns.size(); i++) {
								column = selectColumns.getJSONObject(i);
								if (column.containsKey("ctype")) {
									if (column.getString("ctype").equalsIgnoreCase("MSGID")) {
										columnName = column.getString("columnName");
										break;
									}
								}
							}

							DataSetBuilder dataSetBuilder = new DataSetBuilder();
							JSONArray jsonArray = dataSetBuilder.getJsonArray(datasetId, parameter);
							if (jsonArray != null && jsonArray.size() > 0) {
								for (int i = 0; i < jsonArray.size(); i++) {
									prObject = jsonArray.getJSONObject(i);
									defId = prObject.getString(columnName);
									this.multipleSubmitByDefId(defId, actorId, id, params);
								}
							}
						} else {
							throw new RuntimeException("流程参数不能为空!");
						}
					}
				}
			} else {
				String pageRuleId = ParamUtil.getString(params, "pageRuleId"); // 表单页面规则id
				ruleMap = this.formRulePropertyService.getRuleMap(pageRuleId);
				if (ruleMap != null) {
					String key = "flowDefined";
					if (ruleMap.containsKey(key)) {
						key = ruleMap.get(key);
						if (StringUtils.isNotBlank(key)) {
							JSONObject processDefined = JSON.parseArray(key).getJSONObject(0);
							if (processDefined != null) {
								processDefined.put("pageId", pageId);
								processDefined.put("tableName", formRuleService.getTableNameByPageId(pageId));
								processDefined.put("id", id);
								processDefined.put("actorId", actorId);
								this.start(processDefined, params);
							}
						}
					} else {
						throw new RuntimeException("流程未定义....");
					}
				}
			}
		} else { // 执行下一个流程

			ProcessContext ctx = new ProcessContext();
			ctx.setActorId(loginContext.getActorId());
			ctx.setProcessInstanceId(actReBusiness.getProcessId());
			ctx.setVariables(params);

			this.activitiProcessService.completeTask(ctx);

			// TODO
			// this.insertPageUserTask(actReBusiness);// 执行流程后，流程插入到第三个表。
		}

		result.put("processInstanceId", StringUtils.join(processList, ','));

		return result.toJSONString().getBytes("UTF-8");
	}

	private List<String> processList = null;

	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("getMultipleVariables")
	public byte[] getMultipleVariables(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String ruleId = RequestUtils.getString(request, "rid");
		String pageId = RequestUtils.getString(request, "pageId", null);// 页面ID
		Map<String, String> ruleMap = this.formRulePropertyService.getRuleMap(ruleId);
		String selectPro = "selectPro", defId;
		JSONArray result = new JSONArray();
		if (ruleMap.containsKey(selectPro)) { // 列表启动
			selectPro = ruleMap.get(selectPro);
			if (StringUtils.isNotBlank(selectPro)) {
				JSONObject selectProJson = JSON.parseArray(selectPro).getJSONObject(0);
				JSONArray datas = selectProJson.getJSONObject("data").getJSONArray("datas");
				if (datas != null && !datas.isEmpty()) {
					List<FormWorkflowPlan> plans = null;
					FormWorkflowPlanQuery query = new FormWorkflowPlanQuery();
					for (int i = 0; i < datas.size(); i++) {
						defId = datas.getJSONObject(i).getString("defId");
						query.setDefId(defId);
						query.setPageId(pageId);
						query.setVersion(this.formWorkflowPlanService.getNextVersionByDefId(defId) - 1);
						plans = this.formWorkflowPlanService.list(query);
						if (plans != null && plans.size() > 0) {
							result.add(plans.get(0).getBytes());
						}
					}
				}
			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	private void start(JSONObject processDefined, Map<String, Object> params) throws IOException {
		if (processDefined != null) {
			String pageId = processDefined.getString("pageId");
			String id = processDefined.getString("id");
			String actorId = processDefined.getString("actorId");
			String defId = processDefined.getString("defId");

			String bussinessKey = pageId + "_0_" + id;// 组合业务key
			String processDefinitionKey = processDefined.getString("key");
			ProcessInstance processInstance = this.activitiProcessService.startProcessInstanceByKey(actorId,
					processDefinitionKey, bussinessKey, params);
			// activitiProcessService.startProcessInstanceByKey(actorId,
			// processDefinitionKey, businessKey, variables)
			processList.add(processInstance.getProcessInstanceId());

			ActReBusiness actReBusiness = this.insertBusinessTable(processInstance, actorId,
					processDefined.getString("tableName"), defId); // 保存业务关联表
			// TODO
			// this.insertPageUserTask(actReBusiness);// 执行流程后，流程插入到第三个表。
		} else {
			throw new RuntimeException("流程未定义....");
		}
	}

	/**
	 * 根据用户自定义数据集 配置信息id 启动流程
	 * 
	 * @param defId
	 * @param actorId
	 * @throws IOException
	 */
	private void multipleSubmitByDefId(String defId, String actorId, String id, Map<String, Object> params)
			throws IOException {
		if (StringUtils.isNotBlank(defId)) {
			FormWorkflowPlanQuery query = new FormWorkflowPlanQuery();
			query.setDefId(defId);
			query.setVersion(this.formWorkflowPlanService.getNextVersionByDefId(defId) - 1);
			List<FormWorkflowPlan> plans = this.formWorkflowPlanService.list(query);
			if (plans != null && !plans.isEmpty()) {
				String pageId;
				JSONObject processDefined;
				TableModel tableModel = null;
				for (FormWorkflowPlan plan : plans) {
					pageId = plan.getPageId();
					if (id == null)
						tableModel = this.workFlowDefinedService.createNewInstanceByPageId(pageId, actorId);// this.createNewInstanceByPageId(pageId,
						if(tableModel != null) {
							DataServiceFactory.getInstance().insertTableData(tableModel);
						}
					// actorId);
					if (StringUtils.isNotBlank(plan.getBytes())) {
						processDefined = JSONArray.parseArray(plan.getBytes()).getJSONObject(0);
						processDefined.put("pageId", pageId);
						processDefined.put("id", tableModel == null ? id : tableModel.getPrimaryKey());
						processDefined.put("actorId", actorId);
						processDefined.put("defId", defId);
						processDefined.put("tableName", tableModel == null
								? formRuleService.getTableNameByPageId(pageId) : tableModel.getTableName());
						this.start(processDefined, params);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param processInstance
	 * @param actorId
	 * @param bustbName
	 * @return
	 */
	private ActReBusiness insertBusinessTable(ProcessInstance processInstance, String actorId, String bustbName,
			String defId) {

		String bussinessKey = processInstance.getBusinessKey();

		String[] args = bussinessKey.split("_0_");
		String pageId = args[0], id = args[1];
		String href = SystemConfig.getServiceUrl() + "/mx/form/page/viewPage?id=" + pageId;
		try {
			href = href + "&flow-param=" + URLEncoder.encode(id, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ActReBusiness actReBusiness = new ActReBusiness();
		actReBusiness.setBustbName(bustbName); // 业务表名
		actReBusiness.setBustbId("id");
		actReBusiness.setBusValue(id);
		actReBusiness.setProcessId(processInstance.getProcessInstanceId());
		actReBusiness.setProcessName(processInstance.getProcessDefinitionName());
		actReBusiness.setKey(bussinessKey);
		actReBusiness.setUrl(href);
		actReBusiness.setPageId(pageId);
		actReBusiness.setCreateBy(actorId);
		actReBusiness.setCreateDate(new Date());
		actReBusiness.setDefId(defId);

		actReBusinessService.save(actReBusiness); // 保存到业务关联表

		return actReBusiness;
	}

	protected void insertPageUserTask(ActReBusiness actReBusiness) throws UnsupportedEncodingException {

		String businessKey = actReBusiness.getKey();

		// 获取当前运行任务数据
		String taskNo = "2";
		String sendTask = "1";
		String action = "1";
		String activityCode = "0";
		String activityName = "";
		String sender = "";
		String processPromoter = "";
		String processCode = "";
		String processName = "";
		String nextOperator = "";
		String owner = "";
		Date taskStartTime = null;
		Date taskEndTime = null;
		String title = "";
		PageUserTask pageUserTask = null;

		// 查询该流程的其他任务
		// 流程进入到下一个节点时，历史的流程都将变成已办并设置同步删除
		PageUserTaskQuery query = new PageUserTaskQuery();
		query.setProcessCode(businessKey);
		List<PageUserTask> historyTasks = pageUserTaskService.list(query);
		if (historyTasks != null && historyTasks.size() > 0) {
			pageUserTask = historyTasks.get(0);
			processName = pageUserTask.getProcessName();
			processCode = pageUserTask.getProcessCode();
			owner = pageUserTask.getProcessPromoter();
			processPromoter = pageUserTask.getProcessPromoter();
			taskStartTime = pageUserTask.getTaskStartTime();
			// 以上设置的参数主要用于流程结束没有任务时使用，避免为空值
		}
		// ////////--end--//////

		// 获取上一个任务数据
		String prevOperator = "";
		Date prevTaskEndTime = null;
		if (actReBusiness != null) {
			// prevProcInst.get
			List<HistoricTaskInstance> tis = processEngine.getHistoryService().createHistoricTaskInstanceQuery()
					.processInstanceId(actReBusiness.getProcessId()).finished().orderByTaskId().desc().list();
			if (tis != null && tis.size() > 0) {
				HistoricTaskInstance ti = tis.get(0);
				prevOperator = ti.getAssignee();
				prevTaskEndTime = ti.getEndTime();
			}
		}

		// 获取当前任务
		ProcessInstance procInst = processEngine.getRuntimeService().createProcessInstanceQuery()
				.processInstanceBusinessKey(businessKey).singleResult();

		Task task = null;
		if (procInst == null) {
			activityName = "999999";
			sendTask = "5";
			title = (actReBusiness.getProcessName() == null ? processName : actReBusiness.getProcessName()) + "-完成";
		} else {
			// 获取下一个任务
			// PvmActivity nextActivity =
			// workFlowDefinedService.getNextTaskDefinition(procInst.getId(),
			// procInst.getProcessDefinitionId());
			// if(nextActivity!=null){
			// String id = nextActivity.getId();
			//
			// List<Task> nextTasks =
			// processEngine.getTaskService().createTaskQuery().taskDefinitionKey(id).list();
			// for(Task nextTask : nextTasks){
			// nextOperator += ";"+nextTask.getAssignee();
			// }
			// nextOperator = nextOperator.substring(0);
			// }

			// 获取当前任务
			task = processEngine.getTaskService().createTaskQuery().processInstanceId(procInst.getId()).singleResult();
			taskStartTime = task.getCreateTime();
			nextOperator = task.getAssignee();// 下一个审核人

			HistoricProcessInstance hiProcInst = processEngine.getHistoryService().createHistoricProcessInstanceQuery()
					.processInstanceId(procInst.getId()).singleResult();
			taskEndTime = hiProcInst.getEndTime() == null ? new Date() : hiProcInst.getEndTime();
			processPromoter = hiProcInst.getStartUserId();

			processCode = businessKey;
			processName = procInst.getProcessDefinitionName();
			activityCode = task.getId();
			activityName = task.getName();
			sender = task.getAssignee();
			owner = task.getOwner() == null ? nextOperator : task.getOwner();// 如果owner为空，默认为下一个审核人
			title = processName + "-" + activityName;
		}

		String[] args = businessKey.split("_0_");
		String href = SystemConfig.getServiceUrl() + "/mx/form/page/viewPage?id=" + args[0];
		try {
			href = href + "&flow-param=" + URLEncoder.encode(args[1], "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Base64 base64 = new Base64();
		href = base64.encodeToString(href.getBytes());

		href = SystemConfig.getServiceUrl() + "/website/sczq/applogin?forward=" + href;
		if (pageUserTask == null) {
			pageUserTask = new PageUserTask();
		}
		pageUserTask.setTask(sendTask);
		pageUserTask.setAction(action);
		pageUserTask.setTaskType("121");// 其他；120：审批流程(OA)
		pageUserTask.setTaskNo(taskNo);
		pageUserTask.setActivityCode(activityCode);
		pageUserTask.setActivityName(activityName);
		pageUserTask.setPrevOperator(StringUtils.isEmpty(prevOperator) ? processPromoter : prevOperator);
		pageUserTask.setNextOperator(nextOperator);
		pageUserTask.setSender(sender);
		pageUserTask.setProcessPromoter(processPromoter);
		pageUserTask.setTitle(title);
		pageUserTask.setHref(href);
		pageUserTask.setOwner(owner);
		pageUserTask.setPriority("1");
		pageUserTask.setProcessCode(processCode);
		pageUserTask.setProcessName(processName);
		pageUserTask.setExpired(null);
		pageUserTask.setIsSync(0);
		pageUserTask.setSyncDate(null);
		pageUserTask.setSyncIsSuccess(null);
		pageUserTask.setPrevTaskEndTime(prevTaskEndTime);
		pageUserTask.setTaskStartTime(taskStartTime);
		pageUserTask.setTaskEndTime(taskEndTime);

		pageUserTaskService.save(pageUserTask);

	}

	/**
	 * 获取流程定义所有节点
	 * 
	 * @param request
	 * @param response
	 * @return JSONObject
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("getActivities")
	public byte[] getActivities(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject result = new JSONObject();
		String processDefinitionId = RequestUtils.getString(request, "processDefinitionId");
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);

		List<ActivityImpl> list = def.getActivities();
		if (list != null) {
			JSONArray array = new JSONArray();
			JSONObject json = null;
			for (ActivityImpl ai : list) {

				List<PvmTransition> outTransitions = ai.getOutgoingTransitions();// 获取从某个节点出来的所有线路
				for (PvmTransition tr : outTransitions) {
					PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
					// System.out.println(ac);
					// System.out.println("下一步任务任务：" + ac.getId()+
					// ac.getProperty("name"));
					json = new JSONObject();
					json.put("id", ac.getId());
					json.put("name", ac.getProperty("name") == null ? "" : ac.getProperty("name"));

					array.add(json);
				}
				// System.out.println(ai.getProperties());
				// array.add(ai.getProperties());
			}
			result.put("rows", array);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取流程变量
	 * 
	 * @param request
	 * @param response
	 * @return JSONObject
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("getVariableJSON")
	public byte[] getVariableJSON(HttpServletRequest request, HttpServletResponse response) throws IOException {

		JSONObject result = new JSONObject();

		String processDefId = RequestUtils.getString(request, "processDefinitionId");

		String taskKey = RequestUtils.getString(request, "taskKey");

		ActReElementDef def = this.actReElementDefService.getActReElementDefByProcDefIdTaskKey(processDefId, taskKey);

		result.put("rows", def);

		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 流程提交 new
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("submit")
	public byte[] submit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject result = new JSONObject();
		String defIdKey = "defId", processIdKey = "processId";
		String pageId = RequestUtils.getString(request, "pageId");
		String defId = RequestUtils.getString(request, defIdKey);
		result.put(defIdKey, defId);

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String processId = RequestUtils.getString(request, processIdKey);

		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();
		params.put(WorkflowConstant.ACTORID, actorId);
		params.put(WorkflowConstant.BUSINESSKEY, defId);
		List<String> processes = new ArrayList<String>();

		if (StringUtils.isNotBlank(pageId)) {
			Map<String, String> ruleMap = formRuleService.getRuleByPageId(pageId);
			if (ruleMap != null) {
				String selectPro = "selectPro";
				String idValue = ParamUtils.getString(params, pageId);
				FormTaskmain main = this.formTaskmainService.getFormTaskMainByPageIdAndIdValue(pageId, idValue);
				if (main != null) {
					processId = main.getProcessId();
				}
				JSONObject prObject;
				if (ruleMap.containsKey(selectPro)) { // 列表启动
					selectPro = ruleMap.get(selectPro);
					if (StringUtils.isNotBlank(selectPro)) {
						JSONObject selectProJson = JSON.parseArray(selectPro).getJSONObject(0);
						JSONArray datas = selectProJson.getJSONObject("data").getJSONArray("datas");
						if (datas != null && !datas.isEmpty()) {
							for (int i = 0; i < datas.size(); i++) {
								prObject = datas.getJSONObject(i);
								defId = prObject.getString("defId");
								this.submit(defId, processId, actorId, params, processes);
							}
						}
					}
				} else if (ruleMap.containsKey("dataSourceSetByFlow")) { // 动态传参启动
					String dataSourceSetByFlow = ruleMap.get("dataSourceSetByFlow");
					if (StringUtils.isNotBlank(dataSourceSetByFlow)) {
						String flowParams = ParamUtils.getString(params, "flowParams");
						if (StringUtils.isNotBlank(flowParams)) {

							JSONObject parameter = JSON.parseObject(flowParams), column;

							JSONObject dataSource = JSONArray.parseArray(dataSourceSetByFlow).getJSONObject(0);

							String datasetId = dataSource.getJSONArray("selectDatasource").getJSONObject(0)
									.getString("datasetId"), columnName = "";

							/**
							 * 页面级输入输出参数
							 */
							JSONArray array = JSON.parseArray(ruleMap.get("paraType"));
							if (array != null) {
								JSONObject o = array.getJSONObject(0);
								Map<String, String> m = new HashMap<String, String>();
								if (o != null) {
									o = o.getJSONObject("datas");
									if (o != null) {
										for (String key : o.keySet()) {
											JSONObject para = o.getJSONArray(key).getJSONObject(0);
											if (para != null) {
												m.put(key, para.getString("inparam"));
											}
										}
									}
								}

								if (parameter != null && StringUtils.isNotBlank(datasetId)) {
									JSONArray dataSetParams = this.dataSetService.getDataSetParams(datasetId);
									if (dataSetParams != null) {
										for (int i = 0, size = dataSetParams.size(); i < size; i++) {
											JSONObject p = dataSetParams.getJSONObject(i);
											String paramName = p.getString("param"), paramName0;
											paramName0 = m.get(paramName);
											if (StringUtils.isNotBlank(paramName0)) {
												if (parameter.containsKey(paramName0)) {
													parameter.put(paramName, parameter.get(paramName0));
												}
											}
										}
									}
								}
							}

							JSONArray selectColumns = dataSource.getJSONArray("selectColumns");
							for (int i = 0; i < selectColumns.size(); i++) {
								column = selectColumns.getJSONObject(i);
								if (column.containsKey("ctype")) {
									if (column.getString("ctype").equalsIgnoreCase("MSGID")) {
										columnName = column.getString("columnName");
										break;
									}
								}
							}
							if (MapUtils.isNotEmpty(parameter)) {
								DataSetBuilder dataSetBuilder = new DataSetBuilder();
								JSONArray jsonArray = dataSetBuilder.getJsonArray(datasetId, parameter);
								if (jsonArray != null && jsonArray.size() > 0) {
									for (int i = 0; i < jsonArray.size(); i++) {
										prObject = jsonArray.getJSONObject(i);
										defId = prObject.getString(columnName);
										if (StringUtils.isNotBlank(defId))
											continue;
										this.submit(defId, processId, actorId, params, processes);
									}
								}
							}
						} else {
							throw new RuntimeException("流程参数不能为空!");
						}
					}
				}
			}
		} else {
			Boolean multiple = RequestUtils.getBooleanParameter(request, "multiple", "true");
			if (multiple) {
				char syn = ',';
				Set<String> sets = new HashSet<>();
				if(StringUtils.equalsIgnoreCase(processId, "true")){ //事件批量启动
					sets.addAll(Arrays.asList(StringUtils.split(defId, syn)));
					for (String did : sets) {
						this.submit(did, "", actorId, params, processes);
					}
				} else { //事件批量提交
					sets.addAll(Arrays.asList(StringUtils.split(processId, syn)));
					for (String pid : sets) {
						this.submit(defId, pid, actorId, params, processes);
					}
				}

//				if (StringUtils.contains(defId, syn)) {
//
//					sets.addAll(Arrays.asList(StringUtils.split(defId, syn)));
//
//					for (String did : sets) {
//						this.submit(did, "", actorId, params, processes);
//					}
//
//				} else if (StringUtils.contains(processId, syn)) {
//
//					sets.addAll(Arrays.asList(StringUtils.split(processId, syn)));
//
//					for (String pid : sets) {
//						this.submit(defId, pid, actorId, params, processes);
//					}
//
//				}

			} else
				this.submit(defId, processId, actorId, params, processes);
		}
		result.put(processIdKey, processes);
		result.put("STATUS", MapUtils.getObject(params, "STATUS"));
		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 流程提交方法
	 * 
	 * @param defId
	 * @param processId
	 * @param actorId
	 * @param params
	 * @param processes
	 * @throws IOException
	 */
	public void submit(String defId, String processId, String actorId, Map<String, Object> params,
			List<String> processes) throws IOException {
		List<FormTaskmain> formTaskmains = null;

		ActReTaskHis his = null;

		if (StringUtils.isNotBlank(processId)) { // 判断实例是否存在
			FormTaskmainQuery formTaskMainQuery = new FormTaskmainQuery();
			formTaskMainQuery.setProcessId(processId);
			formTaskmains = this.formTaskmainService.list(formTaskMainQuery);
		}
		List<FormWorkflowPlan> formWorkflowPlans = this.getFormWorkflowPlans(defId);

		if (CollectionUtils.isEmpty(formTaskmains) && StringUtils.isBlank(processId)) {
			params.put(WorkflowConstant.SUBMITTER, actorId);
			ProcessInstance processInstance = workFlowDefinedService.startProcess(formWorkflowPlans, params, actorId);

			processId = processInstance != null ? processInstance.getId() : "";

			this.runTask(processId, null, actorId, params);// 启动并跳转
		} else if (StringUtils.isNotBlank(processId)) {

			String taskId = null;
			ProcessInstance processInstance = null;
			Task task = null;
			try {
				processInstance = this.getProcessInstance(processId);

				/**
				 * 批量动态指定人
				 */
				String multiActorId = MapUtils.getString(params, "multiActorId");
				if (MapUtils.getBooleanValue(params, "multiple", false) && StringUtils//
						.isNotBlank(multiActorId)) {
					params = new JSONObject(params);
					// 获得当前流程的定义模型
					ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
							.getDeployedProcessDefinition(processInstance.getProcessDefinitionId());

					if (processDefinition != null) {
						List<ActivityImpl> activitiList = processDefinition.getActivities();
						for (ActivityImpl activityImpl : activitiList) {
							String id = activityImpl.getId();
							if (StringUtils.startsWith(id, "userTask"))
								params.put(id + "_user", multiActorId);// TODO
						}

					}
				}

				task = taskExtService//
						.getUserTaskByProcessInstanceId(processId, actorId);// this.getTask(processInstance.getId());
				if (task != null) {
					taskId = task.getTaskDefinitionKey();
					params.put("currentTask", taskId);
					params.put(WorkflowConstant.TASKID, task.getId());
					params.put(taskId + "_user", actorId);// TODO

					his = this.getHisTask(task);
				}

			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}

			/**
			 * 流程提交前
			 */
			if (StringUtils.isNotEmpty(taskId)) {
				this.preSubmit(defId, params, taskId);
			}

			if (!this.runTask(processId, taskId, actorId, params)) {
				// ProcessContext ctx = new ProcessContext();
				// ctx.setActorId(actorId);
				// ctx.setProcessInstanceId(processId);
				// ctx.setVariables(params);
				if (task != null) {
					if (params.containsKey("approve") && //
							!ParamUtils.getBoolean(params, "approve")) {// 退回
						params.put(WorkflowConstant.SUBTYPE, WorkflowConstant.BACK);
						params.put("approve", false);
					} else {
						params.put(WorkflowConstant.SUBTYPE, WorkflowConstant.SUBMIT);
						params.put("approve", true);
					}
					// taskService.claim(task.getId(), actorId);
					// taskService.complete(task.getId(), params);

					taskExtService.completeTaskSkipEmptyTask(processId, actorId, params);
				}
			}

			this.workFlowDefinedService.saveFormTask(formWorkflowPlans, formTaskmains.get(0), params, actorId);

			/**
			 * 流程提交后
			 */
			if (StringUtils.isNotEmpty(taskId)) {

				/**
				 * 可以用异步执行
				 */
				this.nextSubmit(defId, params, taskId);
				processInstance = this.getProcessInstance(processId);
				if (processInstance == null) { // 如果流程已经完成(生成当前大流程下一个流程节点的表单数据)
					FormTaskmain main = this.formTaskmainService//
							.getParentFormTaskmainBySubProcessId(processId);
					if(main != null) {
						params.put("wbsId", main.getVariableVal());
						this.workFlowDefinedService.runSubProcess(params, actorId//
								, main.getProcessId(), main.getDefId());
					}
				}
			}

		}
		processes.add(processId);

		this.saveTaskHis(his, params);

		this.updateMapping(processId, false, params);
	}

	/**
	 * 签收
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("assign")
	public byte[] assign(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String processIdKey = "processId";

		String processId = RequestUtils.getString(request, processIdKey);

		LoginContext loginContext = RequestUtils.getLoginContext(request);

		String actorId = loginContext.getActorId();

		Task task = taskExtService.getUserTaskByProcessInstanceId(processId, actorId);

		/**
		 * 签收
		 */
		if (task != null) {
			taskService.claim(task.getId(), actorId);
			return ResponseUtils.responseJsonResult(true);
		}

		return ResponseUtils.responseJsonResult(false);
	}

	private ActReTaskHis getHisTask(Task task) {
		ActReTaskHis his = new ActReTaskHis();
		his.setFromActorId(task.getAssignee());
		his.setFromKey(task.getTaskDefinitionKey());
		his.setFromId(task.getId());
		his.setProcessId(task.getProcessInstanceId());
		return his;
	}

	/**
	 * 保存历史
	 * 
	 * @param his
	 * @param processId
	 * @param params
	 */
	protected void saveTaskHis(ActReTaskHis his, Map<String, Object> params) {
		if (his != null) {
			ActReTaskHis h = null;
			/**
			 * 获取下一个活动的任务(多个)
			 */
			List<Task> userTasks = taskService.createTaskQuery()//
					.processInstanceId(his.getProcessId()).list();
			if (CollectionUtils.isNotEmpty(userTasks)) {
				for (Task task : userTasks) {
					h = new ActReTaskHis();
					try {
						BeanUtils.copyProperties(h, his);
					} catch (Exception e) {
						e.printStackTrace();
					}
					h.setActorId(task.getAssignee());
					h.setCreateDate(task.getCreateTime());
					h.setTaskId(task.getId());
					h.setTaskKey(task.getTaskDefinitionKey());
					h.setTaskName(task.getName());
					h.setSubType(MapUtils.getString(//
							params, WorkflowConstant.SUBTYPE));
					this.actReTaskHisService.save(h);
				}
			}
		}
	}

	/**
	 * 指定跳转节点
	 * 
	 * @param processId
	 * @param actorId
	 * @param params
	 * @return
	 */
	protected boolean runTask(String processId, String taskId, String actorId, Map<String, Object> params) {
		String destTaskKey = MapUtils.getString(//
				params, "targetTask");
		boolean rst = StringUtils.isNotBlank(destTaskKey);
		if (rst) {// 指定跳转节点
			try {
				/**
				 * 判断是否为结束节点，结束流程
				 */
				if (StringUtils.startsWithIgnoreCase(destTaskKey, "end")) {
					taskExtService.deleteProcessInstance(processId, "自由跳转结束");
				} else {
					// taskService.complete(taskId, params);
					// taskExtService.jump(processId, destTaskKey);
					params.put(WorkflowConstant.SUBTYPE, WorkflowConstant.JUMP);
					taskExtService.jumpTask(processId, destTaskKey, actorId, "自由跳转", params);
					// taskExtService.rejectTask(processId, destTaskKey,
					// actorId, "自由跳转");// TODO
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		return rst;
	}

	/**
	 * 获取当前提交的任务节点
	 * 
	 * @return
	 */
	private Task getTask(String processId) {
		return processEngine.getTaskService().createTaskQuery()//
				.processInstanceId(processId).singleResult();
	}

	private ProcessInstance getProcessInstance(String processId) {
		return this.runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
	}

	/**
	 * 流程提交后执行任务
	 * 
	 * @param defId
	 * @param params
	 * @param taskId
	 */
	private void nextSubmit(String defId, Map<String, Object> params, String taskId) {
		if (!params.containsKey(WorkflowConstant.NEXTSUBMIT)) {
			return;
		}
		JSONObject json = this.getTaskRule(defId, taskId);
		if (json != null) {
			Long wdataSetId = json.getLong("wdataSetId");
			if (wdataSetId != null && json.containsKey(WorkflowConstant.TASKWDATASET)) {
				json = json.getJSONArray(WorkflowConstant.TASKWDATASET)//
						.getJSONObject(0).getJSONObject("datas");
				if (json != null) {
					String submitParamsStr = ParamUtils.getString(params, WorkflowConstant.NEXTSUBMIT);
					JSONObject submitParams = JSON.parseObject(submitParamsStr).getJSONObject("params"), tmp;
					for (String key : json.keySet()) {
						if (!submitParams.containsKey(key)) {
							tmp = json.getJSONArray(key).getJSONObject(0);
							if (tmp != null) {
								if (tmp.containsKey("params")) {
									tmp = tmp.getJSONObject("params");
									if (tmp.containsKey("expression")) {
										tmp = tmp.getJSONObject("expression");
										submitParams.put(key, tmp.get("expActVal"));
									}
								}
							}
						}
					}
					logger.debug(submitParams.toJSONString());
					// depBaseWdataSetService.execDynamic(wdataSetId,
					// submitParams);
					DataProcessFactory.getInstance().execDynamic(wdataSetId, submitParams);
				}
			}
		}
	}

	/**
	 * 
	 * 流程提交前执行方法
	 * 
	 * @param defId
	 * @param params
	 * @param taskId
	 */
	private void preSubmit(String defId, Map<String, Object> params, String taskId) {
		if (!params.containsKey(WorkflowConstant.PRESUBMIT)) {
			return;
		}
		JSONObject json = this.getTaskRule(defId, taskId);
		if (json != null) {
			logger.debug(json.toJSONString());
		}
	}

	/**
	 * 流程结束、撤回【更新流程映射表
	 * 
	 * @param processId
	 * @param params
	 */
	protected void updateMapping(String processId, boolean reject, Map<String, Object> params) {
		if (params == null)
			params = new JSONObject();
		params.put("STATUS", 0);
		if (StringUtils.isNotEmpty(processId)) {
			// 取得流程实例
			ProcessInsMapping processInsMapping = new ProcessInsMapping();
			processInsMapping.setDesProcInsId(processId);
			ProcessInstance processInstance = this.getProcessInstance(processId);
			Integer status = 0;// 流程正常前进
			if (processInstance == null) { // 流程结束
				ProcessInsMappingQuery query = new ProcessInsMappingQuery();
				query.setDesProcInsId(processId);
				Integer count = processInsMappingService.getProcessInsMappingCountByQueryCriteria(query);
				if (count > 0) {
					if (reject) { //
						processInsMapping.setProcStatus(99);
						processInsMapping.setProcResult(0);
					} else {
						processInsMapping.setProcStatus(99);
						processInsMapping.setProcResult(1);
					}
					processInsMappingService.updateMapping(processInsMapping);
					status = -1; // 黄工平台
				} else {
					status = 1; // 流程结束
				}

			} else if (reject) {
				processInsMapping.setProcStatus(0);
				processInsMapping.setProcResult(0);
				processInsMappingService.updateMapping(processInsMapping);
				status = -1;// 流程退回
			}
			params.put("STATUS", status);
			/*if (processInstance != null) {
				*//**
				 * 清空所有参与者 <节点_user> 参数
				 *//*
				this.taskExtService.emptyAllProcessInstVaribale(processId);
			}*/

			if (status == 0 && params != null) {
				String approveKey = "approve";
				if (params.containsKey(approveKey) && !ParamUtils.getBoolean(params, approveKey)) {// 退回
					status = -1;// 流程退回
				}
			}
			this.formTaskmainService.updateStatus(status, processId);
		}

	}

	/**
	 * 获取流程绑定方案
	 * 
	 * @param defId
	 * @return
	 */
	private List<FormWorkflowPlan> getFormWorkflowPlans(String defId) {
//		FormWorkflowPlanQuery query = new FormWorkflowPlanQuery();
//		query.setDefId(defId);
//		query.setVersion(this.formWorkflowPlanService.getNextVersionByDefId(defId) - 1);
		List<FormWorkflowPlan> formWorkflowPlans = this.formWorkflowPlanService.getPlans(defId);
		return formWorkflowPlans;
	}

	/**
	 * 获取流程绑定方案 规则
	 * 
	 * @param defId
	 * @return
	 */
	private List<FormWorkFlowRule> getFormWorkflowRules(String defId) {

		FormWorkFlowRuleQuery query = new FormWorkFlowRuleQuery();

		query.setDefId(defId);

		query.setVersion(this.formWorkflowPlanService.getNextVersionByDefId(defId) - 1);// 两个版本号保持一致

		List<FormWorkFlowRule> list = formWorkFlowRuleService.list(query);

		return list;
	}

	private JSONObject getTaskRule(String defId, String taskId) {
		List<FormWorkFlowRule> list = this.getFormWorkflowRules(defId);
		JSONObject json = null;
		if (CollectionUtils.isNotEmpty(list)) {
			for (FormWorkFlowRule fwfr : list) {
				if (StringUtils.equalsIgnoreCase(fwfr.getActTaskId(), taskId)) {
					if (StringUtils.isNotEmpty(fwfr.getBytes())) {
						json = JSON.parseArray(fwfr.getBytes()).getJSONObject(0);

						break;
					}
				}
			}
		}
		return json;
	}

	/**
	 * 流程终止、取消、撤回...
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("flowProcess")
	public byte[] flowProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject result = new JSONObject();

		String processId = RequestUtils.getString(request, "processId");

		Map<String, Object> params = RequestUtils.getParameterMap(request);

		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();
		ActReTaskHis his = null;
		String msg = "";
		if (StringUtils.isEmpty(processId)) {
			logger.error(msg = "flowProcess 流程实例id 不能为空!");
			result.put("errMsg", msg);
		} else if (StringUtils.isEmpty(actorId)) {
			logger.error(msg = "flowProcess 用户 不能为空!");
			result.put("errMsg", msg);
		} else {
			String message = RequestUtils.getString(request, "message");

			Integer type = RequestUtils.getInteger(request, "type");

			switch (type) {
			case 1:// 终止[必须发起人或者admin 才能执行这个操作]

				String submitter = workFlowDefinedService.getSubmitterByProcessId(processId);

				boolean isOk = loginContext.isSystemAdministrator() || StringUtils.equalsIgnoreCase(actorId, submitter);

				if (isOk) {
					taskExtService.deleteProcessInstance(processId, message);
				} else {
					result.put("errMsg", "只能高级管理员或者提单人才能终止流程!");
				}

				break;
			case 2:// 挂起
				taskExtService.suspendProcessInstance(processId);
				break;
			case 3:// 激活
				taskExtService.activateProcessInstance(processId);
				break;
			case 4: // 撤回
				String destTaskKey = RequestUtils.getString(request, "destTaskKey");

				List<Task> userTasks = taskService.createTaskQuery()//
						.processInstanceId(processId).list();
				if (StringUtils.isEmpty(destTaskKey)) {

					his = this.getHisTask(userTasks.get(0));

					/**
					 * 直接向上撤回，清空动态指定节点的参与者 <节点_user> 参数
					 */
					taskExtService.rejectPreTask(processId, actorId, message);

					if (processId != null) {
						//this.taskExtService.emptyAllProcessInstVaribale(processId);
						//获取当前任务
						List<Task> tasks=taskService.createTaskQuery().processInstanceId(processId).list();
						//获取当前所有任务定义
						Vector<String> taskDefkeys=new Vector<String>();
						for(Task task:tasks) {
							taskDefkeys.add(task.getTaskDefinitionKey()+"_user");
						}
						//清空当前待办任务外的流程变量
						this.taskExtService.emptyProcessInstVaribale(processId,taskDefkeys);
					}

				} else {
					/**
					 * 撤回到指定的节点【只能往上】
					 */
					try {

						for (Task task : userTasks) {
							/*
							 * if(task.getTaskDefinitionKey().equals(anObject))
							 */
						}

						if (StringUtils.equalsIgnoreCase(destTaskKey, WorkflowConstant.LASTFLOW)) {
							taskExtService.deleteProcessInstance(processId, message);
							this.updateMapping(processId, true, null);
						} else {
							/**
							 * 自由跳转
							 */
							taskExtService.rejectTask(processId, destTaskKey, actorId, message);
						}

					} catch (ServiceException e) {
						e.printStackTrace();
						logger.error(msg = e.getMessage());
						result.put("errMsg", msg);
					}
				}
				break;
			}
		}

		this.saveTaskHis(his, params);

		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取流程当前节点之前走过的节点
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("getPermissRejectTasks")
	public byte[] getPermissRejectTasks(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject result = new JSONObject();

		String taskId = RequestUtils.getString(request, "taskId");
		String processId = RequestUtils.getString(request, WorkflowConstant.PROCESSID);
		String msg = "";
		if (StringUtils.isEmpty(processId)) {
			logger.error(msg = "getPermissRejectTasks 流程实例id 不能为空!");
			result.put("errMsg", msg);
		}
		if (StringUtils.isEmpty(taskId)) {
			logger.error(msg = "getPermissRejectTasks 任务id taskId 不能为空!");
			result.put("errMsg", msg);
		}
		List<ActivityImpl> list = null;
		if (StringUtils.isEmpty(taskId)) {
			list = taskExtService.getPermissRejectTasks(processId);
		} else {
			list = taskExtService.getPermissRejectTasksByTaskId(processId, taskId);
		}
		JSONArray rows = new JSONArray();
		ProcessInsMappingQuery query = new ProcessInsMappingQuery();
		query.setDesProcInsId(processId);
		List<?> pmts = processInsMappingService.list(query);
		if (CollectionUtils.isNotEmpty(pmts)) {
			JSONObject json = new JSONObject();
			json.put("id", WorkflowConstant.LASTFLOW);
			json.put("name", "主流程");
			json.put("title", "主流程");
			rows.add(json);
		}
		if (CollectionUtils.isNotEmpty(list)) {

			for (ActivityImpl a : list) {
				JSONObject json = new JSONObject(a.getProperties());
				json.put("id", a.getId());
				rows.add(json);
			}
		}

		result.put("rows", rows);
		return result.toJSONString().getBytes("UTF-8");
	}

	@Resource
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@Resource
	public void setActivitiProcessService(ActivitiProcessService activitiProcessService) {
		this.activitiProcessService = activitiProcessService;
	}

	@Resource
	public void setFormRulePropertyService(FormRulePropertyService formRulePropertyService) {
		this.formRulePropertyService = formRulePropertyService;
	}

	@Resource
	public void setWorkFlowDefinedService(WorkFlowDefinedService workFlowDefinedService) {
		this.workFlowDefinedService = workFlowDefinedService;
	}

	@Resource
	public void setActReElementDefService(ActReElementDefService actReElementDefService) {
		this.actReElementDefService = actReElementDefService;
	}

	@Resource
	public void setPageUserTaskService(IPageUserTaskService pageUserTaskService) {
		this.pageUserTaskService = pageUserTaskService;
	}

	@Resource
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@Resource
	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	@Resource
	public void setActReBusinessService(ActReBusinessService actReBusinessService) {
		this.actReBusinessService = actReBusinessService;
	}

	@Resource
	public void setFormWorkflowPlanService(FormWorkflowPlanService formWorkflowPlanService) {
		this.formWorkflowPlanService = formWorkflowPlanService;
	}

	@Resource
	public void setFormRuleService(FormRuleService formRuleService) {
		this.formRuleService = formRuleService;
	}

	@Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setFormTaskmainService(FormTaskmainService formTaskmainService) {
		this.formTaskmainService = formTaskmainService;
	}

	@Resource
	public void setFormTaskService(FormTaskService formTaskService) {
		this.formTaskService = formTaskService;
	}

	@Resource
	public void setDataSetService(DataSetService dataSetService) {
		this.dataSetService = dataSetService;
	}
}
