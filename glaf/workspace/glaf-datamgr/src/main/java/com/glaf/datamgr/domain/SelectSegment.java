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
@Table(name = "SYS_DATA_SELECT")
public class SelectSegment implements Serializable, JSONable {
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
	 * 表名
	 */
	@Column(name = "TABLENAME_", length = 80)
	protected String tableName;

	/**
	 * 字段名
	 */
	@Column(name = "COLUMNNAME_", length = 2000)
	protected String columnName;

	/**
	 * 字段别名
	 */
	@Column(name = "COLUMNLABEL_", length = 250)
	protected String columnLabel;

	/**
	 * 映射别名
	 */
	@Column(name = "COLUMNMAPPING_", length = 250)
	protected String columnMapping;

	/**
	 * 表达式，这里仅限SQL表达式
	 */
	@Lob
	@Column(name = "EXPRESSION_", length = 4000)
	protected String expression;

	/**
	 * 字段中文标题
	 */
	@Column(name = "TITLE_", length = 200)
	protected String title;

	/**
	 * 聚合 (sum,avg,min,max,count)
	 */
	@Column(name = "AGGREGATE_", length = 50)
	protected String aggregate;

	/**
	 * 是否查询 (Y,N)
	 */
	@Column(name = "OUTPUT_", length = 50)
	protected String output;

	/**
	 * 映射名，主要用于映射到图表的X坐标，Y坐标及数值
	 */
	@Column(name = "MAPPING_", length = 250)
	protected String mapping;

	/**
	 * 图表的X坐标，Y坐标
	 */
	@Column(name = "CHARTCOORD_", length = 50)
	protected String chartCoord;

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

	public SelectSegment() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SelectSegment other = (SelectSegment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getAggregate() {
		return aggregate;
	}

	public String getChartCoord() {
		return chartCoord;
	}

	public String getColumnLabel() {
		return this.columnLabel;
	}

	public String getColumnMapping() {
		return columnMapping;
	}

	public String getColumnName() {
		return this.columnName;
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

	public String getExpression() {
		return this.expression;
	}

	public Long getId() {
		return this.id;
	}

	public String getMapping() {
		return mapping;
	}

	public Integer getOrdinal() {
		return this.ordinal;
	}

	public String getOutput() {
		return output;
	}

	public String getTableName() {
		return this.tableName;
	}

	public String getTitle() {
		return title;
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

	public SelectSegment jsonToObject(JSONObject jsonObject) {
		return SelectSegmentJsonFactory.jsonToObject(jsonObject);
	}

	public void setAggregate(String aggregate) {
		this.aggregate = aggregate;
	}

	public void setChartCoord(String chartCoord) {
		this.chartCoord = chartCoord;
	}

	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}

	public void setColumnMapping(String columnMapping) {
		this.columnMapping = columnMapping;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
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

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public JSONObject toJsonObject() {
		return SelectSegmentJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SelectSegmentJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
