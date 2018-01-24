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

package com.glaf.datamgr.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYS_SQL_CONDITION")
public class SqlCondition implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	@Column(name = "SQLDEFID_")
	protected Long sqlDefId;

	@Column(name = "NAME_", length = 50)
	protected String name;

	@Column(name = "TYPE_", length = 50)
	protected String type;

	@Column(name = "REQUIRED_", length = 1)
	protected String required;

	@Column(name = "COLLECTION_", length = 1)
	protected String collection;

	@Column(name = "OPERATOR_", length = 10)
	protected String operator;

	@Lob
	@Column(name = "SEGMENT_", length = 2000)
	protected String segment;

	@Column(name = "LOCKED_")
	protected Integer locked;

	@Column(name = "DELETEFLAG_")
	protected Integer deleteFlag;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime;

	public SqlCondition() {

	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSqlDefId() {
		return this.sqlDefId;
	}

	public String getName() {
		return this.name;
	}

	public String getType() {
		return this.type;
	}

	public String getRequired() {
		return this.required;
	}

	public String getCollection() {
		return this.collection;
	}

	public String getOperator() {
		return this.operator;
	}

	public String getSegment() {
		return this.segment;
	}

	public Integer getLocked() {
		return this.locked;
	}

	public Integer getDeleteFlag() {
		return this.deleteFlag;
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

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public String getUpdateTimeString() {
		if (this.updateTime != null) {
			return DateUtils.getDateTime(this.updateTime);
		}
		return "";
	}

	public void setSqlDefId(Long sqlDefId) {
		this.sqlDefId = sqlDefId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SqlCondition other = (SqlCondition) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public SqlCondition jsonToObject(JSONObject jsonObject) {
		return SqlConditionJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return SqlConditionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SqlConditionJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
