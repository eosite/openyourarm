package com.glaf.workflow.listener;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.workflow.core.domain.ActReElementDef;
import com.glaf.workflow.core.service.ActReElementDefService;
import com.glaf.workflow.core.service.TaskExtService;
import com.glaf.workflow.core.util.WorkflowConstant;

@Component("assignUserListener")
public class AssignUserListener implements TaskListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ActReElementDefService actReElementDefService;

	TaskExtService taskExtService;

	SysUserService sysUserService;
	
	RuntimeService runtimeService;
	
	SysDepartmentService sysDepartmentService;
	
    TaskService taskService;

    @Resource
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	
	@Resource
	public void setTaskExtService(TaskExtService taskExtService) {
		this.taskExtService = taskExtService;
	}
	@Resource
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	@Resource
	public void setActReElementDefService(ActReElementDefService actReElementDefService) {
		this.actReElementDefService = actReElementDefService;
	}


	@Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@Resource
	public void setSysDepartmentService(SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		//String fromKey = null;
		//if (delegateTask.getExecutionId() != null) {
			//fromKey=taskExtService.getActIdByExecoutionId(delegateTask.getExecutionId());
		//}
		// 获取任务对应的规则
		String processDefinitionId = delegateTask.getProcessDefinitionId();
		String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
		// 获取提单人
		String submitter = delegateTask.getVariable(WorkflowConstant.SUBMITTER) != null
				? (String) delegateTask.getVariable(WorkflowConstant.SUBMITTER) : "";
		// 获取流程变量值
		Map<String, Object> variables = delegateTask.getVariables();
		ActReElementDef actReElementDef = actReElementDefService
				.getActReElementDefByProcDefIdTaskKey(processDefinitionId, taskDefinitionKey);
		// 获取任务执行者
		//if (fromKey != null) {
			//if (variables.containsKey(fromKey + "_user")) {
				//variables.put(actReElementDef.getEleID() + "_user", variables.get(fromKey + "_user"));
				//variables.remove(fromKey + "_user");
				//delegateTask.removeVariable(fromKey + "_user");
				//taskExtService.removeVariable(delegateTask.getProcessInstanceId(), fromKey + "_user");
			//}
		//}
		Set<String> assigns = actReElementDefService.getTaskAssign(variables, actReElementDef, submitter);
		if(CollectionUtils.isEmpty(assigns)/*assigns==null*/){
			assigns=new HashSet<String>(); 
			//获取最近一次任务的执行者
			List<String> lastAssignee=taskExtService.getTaskLastAssignee(delegateTask.getProcessInstanceId(), taskDefinitionKey);
			if(lastAssignee!=null){
				assigns.addAll(lastAssignee);
			}
		}
		//将本节点外地其它节点的动态用户参数值清空
		String key=null;
		for(Entry<String, Object> entry: variables.entrySet()){
			key=entry.getKey();
			if((key.endsWith("_user")&&!key.equals(taskDefinitionKey+"_user"))){
				delegateTask.setVariableLocal(key, "");
			}
		}
		// 如果当前只有一个用户，直接指定任务受理人
		if (assigns.size() == 1) {
			for (String assignne : assigns) {
				delegateTask.setAssignee(assignne);
				// 如果当前任务为提单人任务
				if (actReElementDef.getSubmitterTaskFlag() != null && actReElementDef.getSubmitterTaskFlag() == 1) {
					// 设置流程提单人、提单人部门、提单人上级部门、提单人上上级部门
					delegateTask.setVariable(WorkflowConstant.SUBMITTER, assignne);
					SysUser user = sysUserService.findByAccount(assignne);
					if (user.getDepartment() != null) {
						delegateTask.setVariable(WorkflowConstant.SUBDEPT, user.getDepartment().getCode());
						// 获取上级部门
						// SysDepartment pSysDepartment =
						// user.getDepartment().getParent();
						// SysDepartment pSysDepartment =
						// sysDepartmentService.getSysDepartment(user.getDepartment().getId()).getParent();
						SysDepartment pSysDepartment = sysDepartmentService
								.getSysDepartmentWithAncestor(user.getDepartment().getId()).getParent();
						if (pSysDepartment != null) {
							delegateTask.setVariable(WorkflowConstant.SUBPARENTDEPT, pSysDepartment.getCode());
							// 获取上上级部门
							// pSysDepartment =
							// sysDepartmentService.getSysDepartment(pSysDepartment.getId());
							// if(pSysDepartment == null)
							// continue;
							SysDepartment grandSysDepartment = pSysDepartment.getParent();
							if (grandSysDepartment != null)
								delegateTask.setVariable(WorkflowConstant.SUBGRANDDEPT, grandSysDepartment.getCode());
							else
								delegateTask.setVariable(WorkflowConstant.SUBGRANDDEPT, "");
						} else {
							delegateTask.setVariable(WorkflowConstant.SUBPARENTDEPT, "");
							delegateTask.setVariable(WorkflowConstant.SUBGRANDDEPT, "");
						}
					}
				}
			}
		} else if(assigns.size()>1) {
			delegateTask.addCandidateUsers(assigns);
		}
	}

}
