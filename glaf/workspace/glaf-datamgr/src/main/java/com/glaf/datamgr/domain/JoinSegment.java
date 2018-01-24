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
@Table(name = "SYS_DATA_JOIN")
public class JoinSegment implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	/**
	 * 数据集编号
	 */
	@Column(name = "DATASETID_", length = 50)
	protected String datasetId;

	/**
	 * 源表
	 */
	@Column(name = "SOURCETABLE_", length = 200)
	protected String sourceTable;

	/**
	 * 源字段
	 */
	@Column(name = "SOURCECOLUMN_", length = 200)
	protected String sourceColumn;

	/**
	 * 目标表
	 */
	@Column(name = "TARGETTABLE_", length = 200)
	protected String targetTable;

	/**
	 * 目标字段
	 */
	@Column(name = "TARGETCOLUMN_", length = 200)
	protected String targetColumn;

	/**
	 * 连接符 （LEFT OUTER JOIN，INNER JOIN，RIGHT OUTER JOIN）
	 */
	@Column(name = "CONNECTOR_", length = 50)
	protected String connector;

	/**
	 * 顺序
	 */
	@Column(name = "ORDINAL_")
	protected Integer ordinal;

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

	public JoinSegment() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JoinSegment other = (JoinSegment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getConnector() {
		return this.connector;
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

	public String getDatasetId() {
		return this.datasetId;
	}

	public Long getId() {
		return this.id;
	}

	public Integer getOrdinal() {
		return this.ordinal;
	}

	public String getSourceColumn() {
		return sourceColumn;
	}

	public String getSourceTable() {
		return sourceTable;
	}

	public String getTargetColumn() {
		return targetColumn;
	}

	public String getTargetTable() {
		return targetTable;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public JoinSegment jsonToObject(JSONObject jsonObject) {
		return JoinSegmentJsonFactory.jsonToObject(jsonObject);
	}

	public void setConnector(String connector) {
		this.connector = connector;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public void setSourceColumn(String sourceColumn) {
		this.sourceColumn = sourceColumn;
	}

	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}

	public void setTargetColumn(String targetColumn) {
		this.targetColumn = targetColumn;
	}

	public void setTargetTable(String targetTable) {
		this.targetTable = targetTable;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public JSONObject toJsonObject() {
		return JoinSegmentJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return JoinSegmentJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
