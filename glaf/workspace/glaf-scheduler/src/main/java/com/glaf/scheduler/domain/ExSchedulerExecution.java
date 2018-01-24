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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;
import com.glaf.scheduler.util.SchedulerExecutionJsonFactory;

/**
 * 
 * 实体对象
 *
 */

public class ExSchedulerExecution implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	protected Long id;

	protected String schedulerId;

	protected String businessKey;

	protected Integer count;

	protected Double value;

	protected Integer runYear;

	protected Integer runMonth;

	protected Integer runWeek;

	protected Integer runQuarter;

	protected Integer runDay;

	protected Integer runTime;

	protected String jobNo;

	protected Integer status;

	protected String createBy;

	protected Date createTime;

	public ExSchedulerExecution() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExSchedulerExecution other = (ExSchedulerExecution) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getBusinessKey() {
		return this.businessKey;
	}

	public Integer getCount() {
		return this.count;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getCreateTimeString() {
		if (this.createTime != null) {
			return DateUtils.getDateTime(this.createTime);
		}
		return "";
	}

	public Long getId() {
		return this.id;
	}

	public String getJobNo() {
		return this.jobNo;
	}

	public Integer getRunDay() {
		return this.runDay;
	}

	public Integer getRunMonth() {
		return this.runMonth;
	}

	public Integer getRunQuarter() {
		return this.runQuarter;
	}

	public Integer getRunTime() {
		return this.runTime;
	}

	public Integer getRunWeek() {
		return this.runWeek;
	}

	public Integer getRunYear() {
		return this.runYear;
	}

	public String getSchedulerId() {
		return this.schedulerId;
	}

	public Integer getStatus() {
		return this.status;
	}

	public Double getValue() {
		return this.value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public ExSchedulerExecution jsonToObject(JSONObject jsonObject) {
		return SchedulerExecutionJsonFactory.jsonToObject(jsonObject);
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public void setRunDay(Integer runDay) {
		this.runDay = runDay;
	}

	public void setRunMonth(Integer runMonth) {
		this.runMonth = runMonth;
	}

	public void setRunQuarter(Integer runQuarter) {
		this.runQuarter = runQuarter;
	}

	public void setRunTime(Integer runTime) {
		this.runTime = runTime;
	}

	public void setRunWeek(Integer runWeek) {
		this.runWeek = runWeek;
	}

	public void setRunYear(Integer runYear) {
		this.runYear = runYear;
	}

	public void setSchedulerId(String schedulerId) {
		this.schedulerId = schedulerId;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public JSONObject toJsonObject() {
		return SchedulerExecutionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SchedulerExecutionJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
