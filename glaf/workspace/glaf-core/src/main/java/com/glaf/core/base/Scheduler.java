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

package com.glaf.core.base;

import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface Scheduler {

	String getAttribute();

	int getAutoStartup();
	
	String getBusinessType();

	String getContent();

	String getCreateBy();

	Date getCreateDate();

	Date getEndDate();

	String getExpression();

	Date getFireTime();

	String getId();

	String getIntervalTime();

	String getIntervalType();

	String getIntervalValue();

	String getJobClass();

	Map<String, Parameter> getJobDataMap();

	long getJobRunTime();

	int getLocked();

	String getMethodName();

	Date getNextFireTime();

	Date getPreviousFireTime();

	int getPriority();

	int getRepeatCount();

	int getRepeatInterval();

	int getRunStatus();

	int getRunType();

	String getSpringBeanId();

	String getSpringClass();

	Date getStartDate();

	int getStartDelay();

	int getStartup();

	String getTaskId();

	String getTaskName();

	String getTaskType();

	int getThreadSize();

	String getTitle();

	boolean isSchedulerAutoStartup();

	boolean isSchedulerStartup();

	boolean isValid();

	Scheduler jsonToObject(JSONObject jsonObject);

	void setAttribute(String attribute);
	
	void setBusinessType(String businessType);

	void setAutoStartup(int autoStartup);

	void setContent(String content);

	void setCreateBy(String createBy);

	void setCreateDate(Date createDate);

	void setEndDate(Date endDate);

	void setExpression(String expression);

	void setFireTime(Date fireTime);

	void setId(String id);

	void setIntervalTime(String intervalTime);

	void setIntervalType(String intervalType);

	void setIntervalValue(String intervalValue);

	void setJobClass(String jobClass);

	void setJobDataMap(Map<String, Parameter> jobDataMap);

	void setJobRunTime(long jobRunTime);

	void setLocked(int locked);

	void setMethodName(String methodName);

	void setNextFireTime(Date nextFireTime);

	void setPreviousFireTime(Date previousFireTime);

	void setPriority(int priority);

	void setRepeatCount(int repeatCount);

	void setRepeatInterval(int repeatInterval);

	void setRunStatus(int runStatus);

	void setRunType(int runType);

	void setSpringBeanId(String springBeanId);

	void setSpringClass(String springClass);

	void setStartDate(Date startDate);

	void setStartDelay(int startDelay);

	void setStartup(int startup);

	void setTaskId(String taskId);

	void setTaskName(String taskName);

	void setTaskType(String taskType);

	void setThreadSize(int threadSize);

	void setTitle(String title);

	JSONObject toJsonObject();

}