package com.glaf.workflow.core.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.form.core.domain.FormWorkflowPlan;
import com.glaf.form.core.mapper.FormWorkflowPlanMapper;
import com.glaf.form.core.service.FormRuleService;
import com.glaf.workflow.core.domain.ProcessDefMapping;
import com.glaf.workflow.core.domain.ProcessInsMapping;
import com.glaf.workflow.core.util.WorkflowConstant;

@Service("com.glaf.workflow.core.service.StartImpProcessService")
@Transactional(readOnly = true)
public class StartImpProcessServiceImpl implements StartImpProcessService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected ProcessInsMappingService processInsMappingService;

	protected FormRuleService formRuleService;

	protected ProcessDefMappingService processDefMappingService;

	protected WorkFlowDefinedService workFlowDefinedService;

	protected FormWorkflowPlanMapper formWorkflowPlanMapper;

	@Resource
	public void setProcessInsMappingService(ProcessInsMappingService processInsMappingService) {
		this.processInsMappingService = processInsMappingService;
	}

	@Resource
	public void setFormRuleService(FormRuleService formRuleService) {
		this.formRuleService = formRuleService;
	}

	@Resource
	public void setWorkFlowDefinedService(WorkFlowDefinedService workFlowDefinedService) {
		this.workFlowDefinedService = workFlowDefinedService;
	}

	@Resource
	public void setProcessDefMappingService(ProcessDefMappingService processDefMappingService) {
		this.processDefMappingService = processDefMappingService;
	}

	@Resource
	public void setFormWorkflowPlanMapper(FormWorkflowPlanMapper formWorkflowPlanMapper) {
		this.formWorkflowPlanMapper = formWorkflowPlanMapper;
	}

	public void startProcesses() {
		// 获取待启动任务列表
		List<ProcessInsMapping> processInsMappings = processInsMappingService.getNeedStartImpProcess();
		for (ProcessInsMapping processInsMapping : processInsMappings) {
			startProcess(processInsMapping);
		}
	}
	@Transactional
	public void startProcess(ProcessInsMapping processInsMapping) {
		// 获取本地流程
		ProcessDefMapping processDefMapping = processDefMappingService.getProcessDefMappingByImpProcessDefAndImpSysId(
				processInsMapping.getSrcProcDefId(), processInsMapping.getDesSysId(), processInsMapping.getSrcSysId());
		if (processDefMapping != null) {
			// 获取流程页面绑定关系
			// 获取流程表单映射实例表名
			BaseDataInfo baseinfo = BaseDataManager.getInstance().getValue("tableName", "wfpage");
			String wfPageTableName = baseinfo.getValue();
			// 获取流程表单映射实例启动点关键字值列
			baseinfo = BaseDataManager.getInstance().getValue("startPointColumn", "wfpage");
			String startPointColumn = baseinfo.getValue();
			// 获取流程表单映射定义关键字值列
			baseinfo = BaseDataManager.getInstance().getValue("defKeyColumn", "wfpage");
			String defKeyColumn = baseinfo.getValue();
			List<FormWorkflowPlan> formWorkflowPlans = formWorkflowPlanMapper.getFormWorkflowPlanByProcessDef(
					wfPageTableName, startPointColumn, defKeyColumn, processDefMapping.getDesProcDefId(),
					processInsMapping.getDesWbsDefId());

			Map<String, String> tablePrimaryKeyVal = null;
			// 获取流程表单对应主数据表主键值{cell_useradd1 ：admin/20160606}
			if (processInsMapping.getExtTransType() != null && processInsMapping.getExtTransType() == 1) {
				tablePrimaryKeyVal = processInsMappingService.getReportTablePrimaryKeyValBySysId(
						processInsMapping.getDesSysId(), processInsMapping.getSrcProcInsId());
			}else if (processInsMapping.getExtTransType() != null && processInsMapping.getExtTransType() == 2) {
				tablePrimaryKeyVal = processInsMappingService.getWpfTablePrimaryKeyValBySysId(
						processInsMapping.getDesSysId(), processInsMapping.getSrcProcInsId());
			}  else {
				tablePrimaryKeyVal = processInsMappingService.getTablePrimaryKeyValBySysId(
						processInsMapping.getDesSysId(), processInsMapping.getSrcProcInsId());
			}

			Map<String, Object> params = new HashMap<String, Object>();
			// 获取页面对应的数据表
			// Map<String, String> pageMainTableName = null;
			if (CollectionUtils.isNotEmpty(formWorkflowPlans)) {
				String tableName;
				// pageMainTableName = new HashMap<String, String>();
				if (MapUtils.isNotEmpty(tablePrimaryKeyVal)) {
					for (FormWorkflowPlan plan : formWorkflowPlans) {
						tableName = formRuleService.getTableNameByPageId(plan.getPageId());
						params.put("wbsId", processInsMapping.getDesWbsInsId());
						params.put("name", processInsMapping.getProcessInsName());
						if (StringUtils.isNotBlank(tableName)) {
							if (tablePrimaryKeyVal.get(tableName) != null) {
								params.put(plan.getPageId(), tablePrimaryKeyVal.get(tableName));
							}
							// pageMainTableName.put(tableName,
							// plan.getPageId());
						}
					}
				}
			} else {
				return;
			}

			// 启动页面流程任务
			params.put(WorkflowConstant.AUTOSTART, "true");
			ProcessInstance psIns = this.workFlowDefinedService.startProcess(formWorkflowPlans, params, "admin");
			if (psIns != null && StringUtils.isNotEmpty(psIns.getId())) {
				// 更新记录表
				processInsMapping.setDesProcDefId(processDefMapping.getDesProcDefId());
				processInsMapping.setDesProcInsId(psIns.getId());
				processInsMapping.setProcStatus(10);
				processInsMapping.setProcStartTime(new Date());
				processInsMappingService.save(processInsMapping);
			}
		} else {
			logger.info("未找到本地流程映射");
		}
	}

}
