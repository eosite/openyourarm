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

import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.datamgr.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "TABLE_COMBINATION_COL")
public class TableCombinationColumn implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected long id;

	/**
	 * 合成编号
	 */
	@Column(name = "COMBINATIONID_")
	protected long combinationId;

	/**
	 * 模板表字段名
	 */
	@Column(name = "SOURCECOLUMNNAME_", length = 50)
	protected String sourceColumnName;

	/**
	 * 数据集编号
	 */
	@Column(name = "DATASETID_", length = 50)
	protected String datasetId;

	/**
	 * 目标表名称
	 */
	@Column(name = "TARGETTABLENAME_", length = 50)
	protected String targetTableName;

	/**
	 * 目标表字段名
	 */
	@Column(name = "TARGETCOLUMNNAME_", length = 50)
	protected String targetColumnName;

	/**
	 * 目标表字段别名
	 */
	@Column(name = "TARGETCOLUMNLABEL_", length = 100)
	protected String targetColumnLabel;

	/**
	 * 初始化值
	 */
	@Column(name = "INITVALUE_", length = 500)
	protected String initValue;

	@javax.persistence.Transient
	protected String title;

	public TableCombinationColumn() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableCombinationColumn other = (TableCombinationColumn) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public long getCombinationId() {
		return this.combinationId;
	}

	public String getDatasetId() {
		return datasetId;
	}

	public long getId() {
		return this.id;
	}

	public String getInitValue() {
		return initValue;
	}

	public String getSourceColumnName() {
		if (sourceColumnName != null) {
			sourceColumnName = sourceColumnName.trim();
			sourceColumnName = sourceColumnName.toLowerCase();
		}
		return this.sourceColumnName;
	}

	public String getTargetColumnLabel() {
		return targetColumnLabel;
	}

	public String getTargetColumnName() {
		if (targetColumnName != null) {
			targetColumnName = targetColumnName.trim();
			targetColumnName = targetColumnName.toLowerCase();
		}
		return this.targetColumnName;
	}

	public String getTargetTableName() {
		return this.targetTableName;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public TableCombinationColumn jsonToObject(JSONObject jsonObject) {
		return TableCombinationColumnJsonFactory.jsonToObject(jsonObject);
	}

	public void setCombinationId(long combinationId) {
		this.combinationId = combinationId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setInitValue(String initValue) {
		this.initValue = initValue;
	}

	public void setSourceColumnName(String sourceColumnName) {
		this.sourceColumnName = sourceColumnName;
	}

	public void setTargetColumnLabel(String targetColumnLabel) {
		this.targetColumnLabel = targetColumnLabel;
	}

	public void setTargetColumnName(String targetColumnName) {
		this.targetColumnName = targetColumnName;
	}

	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JSONObject toJsonObject() {
		return TableCombinationColumnJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return TableCombinationColumnJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
