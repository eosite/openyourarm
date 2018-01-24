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

package com.glaf.base.modules.sys.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.identity.Agent;
import com.glaf.core.identity.util.AgentJsonFactory;

/**
 * 代理人
 *
 */
@Entity
@Table(name = "sys_agent")
public class SysAgent implements Agent {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 委托人 ，consigner
	 */
	@Column(name = "ASSIGNFROM_", length = 50)
	protected String assignFrom;

	/**
	 * 委托人名称
	 */
	@Column(name = "ASSIGNFROMNAME_", length = 50)
	protected String assignFromName;

	/**
	 * 受托人，assignee
	 */
	@Column(name = "ASSIGNTO_", length = 50)
	protected String assignTo;

	/**
	 * 受托人名称
	 */
	@Column(name = "ASSIGNTONAME_", length = 50)
	protected String assignToName;

	/**
	 * 流程名称
	 */
	@Column(name = "PROCESSNAME_", length = 50)
	protected String processName;

	/**
	 * 任务名称
	 */
	@Column(name = "TASKNAME_", length = 50)
	protected String taskName;

	@Column(name = "SERVICEKEY_", length = 50)
	protected String serviceKey;

	/**
	 * 对象编号
	 */
	@Column(name = "OBJECTID_", length = 50)
	protected String objectId;

	/**
	 * 对象值
	 */
	@Column(name = "OBJECTVALUE_", length = 50)
	protected String objectValue;

	/**
	 * 开始生效日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTDATE_")
	protected Date startDate;

	/**
	 * 结束日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDDATE_")
	protected Date endDate;

	/**
	 * 代理类型 0-全局代理 1-代理指定流程的全部任务 2-代理指定流程的指定任务
	 */
	@Column(name = "AGENTTYPE_")
	protected int agentType;

	/**
	 * 锁定标记
	 */
	@Column(name = "LOCKED_")
	protected int locked;

	@javax.persistence.Transient
	protected boolean valid = false;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	public SysAgent() {

	}

	public int getAgentEntityType() {
		return agentType;
	}

	public int getAgentType() {
		return agentType;
	}

	public String getAssignFrom() {
		return assignFrom;
	}

	public String getAssignFromName() {
		return assignFromName;
	}

	public String getAssignTo() {
		return assignTo;
	}

	public String getAssignToName() {
		return assignToName;
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

	public String getId() {
		return id;
	}

	public int getLocked() {
		return locked;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public String getProcessName() {
		return processName;
	}

	public String getServiceKey() {
		return serviceKey;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getTaskName() {
		return taskName;
	}

	public boolean isValid() {
		boolean valid = false;
		Date now = new Date();
		if (locked == 0 && startDate != null && endDate != null) {
			if (now.getTime() >= startDate.getTime() && now.getTime() <= endDate.getTime()) {
				valid = true;
			}
		}
		return valid;
	}

	public void setAgentEntityType(int agentType) {
		this.agentType = agentType;
	}

	public void setAgentType(int agentType) {
		this.agentType = agentType;
	}

	public void setAssignFrom(String assignFrom) {
		this.assignFrom = assignFrom;
	}

	public void setAssignFromName(String assignFromName) {
		this.assignFromName = assignFromName;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	public void setAssignToName(String assignToName) {
		this.assignToName = assignToName;
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

	public void setId(String id) {
		this.id = id;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public JSONObject toJsonObject() {
		return AgentJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return AgentJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}