package com.glaf.workflow.core.util;

import java.util.Iterator;
import java.util.Map;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Comment;

public class JumpTaskCmd implements Command<Comment> {

	protected String executionId;
	protected ActivityImpl desActivity;
	protected ActivityImpl currentActivity; 
	protected Map<String, Object> variables;
	
	
	public JumpTaskCmd(String executionId, ActivityImpl currentActivity,ActivityImpl desActivity,Map<String, Object> variables) {
		this.executionId = executionId;
		this.currentActivity=currentActivity;
		this.desActivity = desActivity;
		this.variables=variables;
	}
	
	public Comment execute(CommandContext commandContext) {
		        ExecutionEntity executionEntity = Context.getCommandContext().getExecutionEntityManager().findExecutionById(executionId);  
				executionEntity.setVariables(variables); 
				executionEntity.setEventSource(this.currentActivity); 
				executionEntity.setActivity(this.currentActivity); 
				// 根据executionId 获取Task 
				Iterator<TaskEntity> localIterator = Context.getCommandContext() 
				.getTaskEntityManager() 
				.findTasksByExecutionId(this.executionId).iterator(); 
				while (localIterator.hasNext()) {
					TaskEntity taskEntity = (TaskEntity) localIterator.next(); 
					// 触发任务监听 
					//taskEntity.fireEvent("complete"); 
					// 删除任务的原因 
					Context.getCommandContext().getTaskEntityManager() 
					.deleteTask(taskEntity, "completed", true); 
				} 
				executionEntity.executeActivity(this.desActivity); 
		        return null;
	}

}