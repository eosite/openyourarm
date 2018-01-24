/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.scheduler.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.base.Parameter;
import com.glaf.core.base.Scheduler;
import com.glaf.scheduler.util.SchedulerJsonFactory;

public class ExSchedulerEntity implements Serializable, Scheduler, JSONable {
	private static final long serialVersionUID = 1L;

	protected String id;

	protected String attribute;

	protected int autoStartup;

	protected String businessType;

	protected String content;

	protected String createBy;

	protected Date createDate;

	protected Date startDate;

	protected int startDelay;

	protected Date endDate;

	protected String expression;

	protected String intervalTime;

	protected String intervalType;

	protected String intervalValue;

	protected String jobClass;

	protected String springClass;

	protected String springBeanId;

	protected String methodName;

	protected Map<String, Parameter> jobDataMap = new java.util.HashMap<String, Parameter>();

	protected int locked;

	protected List<ExSchedulerParam> params = new java.util.ArrayList<ExSchedulerParam>();

	protected int priority;

	protected int repeatCount;

	protected int repeatInterval;

	protected int startup;

	protected String taskName;

	protected String taskType;

	protected int threadSize;

	protected String title;

	protected int runType = 99;

	protected int runStatus;

	protected long jobRunTime;

	protected Date nextFireTime;

	protected Date fireTime;

	protected Date previousFireTime;

	public ExSchedulerEntity() {

	}

	public void addParam(ExSchedulerParam param) {
		if (params == null) {
			params = new java.util.ArrayList<ExSchedulerParam>();
		}
		params.add(param);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExSchedulerEntity other = (ExSchedulerEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getAttribute() {
		return attribute;
	}

	public int getAutoStartup() {
		return autoStartup;
	}

	public String getBusinessType() {
		return businessType;
	}

	public String getContent() {
		return content;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getExpression() {
		return expression;
	}

	public Date getFireTime() {
		return fireTime;
	}

	public String getId() {
		return id;
	}

	public String getIntervalTime() {
		return intervalTime;
	}

	public String getIntervalType() {
		return intervalType;
	}

	public String getIntervalValue() {
		return intervalValue;
	}

	public String getJobClass() {
		return jobClass;
	}

	public Map<String, Parameter> getJobDataMap() {
		return jobDataMap;
	}

	public long getJobRunTime() {
		return jobRunTime;
	}

	public int getLocked() {
		return locked;
	}

	public String getMethodName() {
		return methodName;
	}

	public Date getNextFireTime() {
		return nextFireTime;
	}

	public List<ExSchedulerParam> getParams() {
		return params;
	}

	public Date getPreviousFireTime() {
		return previousFireTime;
	}

	public int getPriority() {
		return priority;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public int getRepeatInterval() {
		return repeatInterval;
	}

	public int getRunStatus() {
		return runStatus;
	}

	public int getRunType() {
		return runType;
	}

	public String getSpringBeanId() {
		return springBeanId;
	}

	public String getSpringClass() {
		return springClass;
	}

	public Date getStartDate() {
		return startDate;
	}

	public int getStartDelay() {
		return startDelay;
	}

	public int getStartup() {
		return startup;
	}

	public String getTaskId() {
		return id;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTaskType() {
		return taskType;
	}

	public int getThreadSize() {
		return threadSize;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean isSchedulerAutoStartup() {
		if (autoStartup == 1) {
			return true;
		}
		return false;
	}

	public boolean isSchedulerStartup() {
		if (startup == 1) {
			return true;
		}
		return false;
	}

	public boolean isValid() {
		boolean valid = false;
		if (locked != 0) {
			return valid;
		}
		Date now = new Date();
		if (startDate != null) {
			if (endDate != null) {
				if (now.getTime() >= startDate.getTime() && now.getTime() <= endDate.getTime()) {
					valid = true;
				}
			} else {
				if (now.getTime() >= startDate.getTime()) {
					valid = true;
				}
			}
		}
		return valid;
	}

	public Scheduler jsonToObject(JSONObject jsonObject) {
		return SchedulerJsonFactory.jsonToObject(jsonObject);
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setAutoStartup(boolean autoStartup) {
		if (autoStartup) {
			this.autoStartup = 1;
		} else {
			this.autoStartup = 0;
		}
	}

	public void setAutoStartup(int autoStartup) {
		this.autoStartup = autoStartup;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setFireTime(Date fireTime) {
		this.fireTime = fireTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIntervalTime(String intervalTime) {
		this.intervalTime = intervalTime;
	}

	public void setIntervalType(String intervalType) {
		this.intervalType = intervalType;
	}

	public void setIntervalValue(String intervalValue) {
		this.intervalValue = intervalValue;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public void setJobDataMap(Map<String, Parameter> jobDataMap) {
		this.jobDataMap = jobDataMap;
	}

	public void setJobRunTime(long jobRunTime) {
		this.jobRunTime = jobRunTime;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	public void setParams(List<ExSchedulerParam> params) {
		this.params = params;
	}

	public void setPreviousFireTime(Date previousFireTime) {
		this.previousFireTime = previousFireTime;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public void setRepeatInterval(int repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public void setRunStatus(int runStatus) {
		this.runStatus = runStatus;
	}

	public void setRunType(int runType) {
		this.runType = runType;
	}

	public void setSpringBeanId(String springBeanId) {
		this.springBeanId = springBeanId;
	}

	public void setSpringClass(String springClass) {
		this.springClass = springClass;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStartDelay(int startDelay) {
		this.startDelay = startDelay;
	}

	public void setStartup(boolean startup) {
		if (startup) {
			this.startup = 1;
		} else {
			this.startup = 0;
		}
	}

	public void setStartup(int startup) {
		this.startup = startup;
	}

	public void setTaskId(String taskId) {
		this.id = taskId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public void setThreadSize(int threadSize) {
		this.threadSize = threadSize;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JSONObject toJsonObject() {
		return SchedulerJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SchedulerJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}