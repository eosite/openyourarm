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

import java.io.*;
import java.util.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.scheduler.util.SchedulerLogJsonFactory;

/**
 * 
 * 实体对象
 *
 */

public class ExSchedulerLog implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	protected String id;

	protected String taskId;

	protected Date startDate;

	protected Date endDate;

	protected long jobRunTime;

	protected int status;

	protected String taskName;

	protected String title;

	protected String content;

	protected String exitCode;

	protected String exitMessage;

	protected String createBy;

	protected Date createDate;

	public ExSchedulerLog() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExSchedulerLog other = (ExSchedulerLog) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getContent() {
		return this.content;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getCreateDateString() {
		if (this.createDate != null) {
			return DateUtils.getDateTime(this.createDate);
		}
		return "";
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public String getEndDateString() {
		if (this.endDate != null) {
			return DateUtils.getDateTime(this.endDate);
		}
		return "";
	}

	public String getExitCode() {
		return this.exitCode;
	}

	public String getExitMessage() {
		return this.exitMessage;
	}

	public String getId() {
		return this.id;
	}

	public long getJobRunTime() {
		return this.jobRunTime;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public String getStartDateString() {
		if (this.startDate != null) {
			return DateUtils.getDateTime(this.startDate);
		}
		return "";
	}

	public int getStatus() {
		return this.status;
	}

	public String getTaskId() {
		return taskId;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public String getTitle() {
		return this.title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public ExSchedulerLog jsonToObject(JSONObject jsonObject) {
		return SchedulerLogJsonFactory.jsonToObject(jsonObject);
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

	public void setExitCode(String exitCode) {
		this.exitCode = exitCode;
	}

	public void setExitMessage(String exitMessage) {
		this.exitMessage = exitMessage;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setJobRunTime(long jobRunTime) {
		this.jobRunTime = jobRunTime;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JSONObject toJsonObject() {
		return SchedulerLogJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SchedulerLogJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
