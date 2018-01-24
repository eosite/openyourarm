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
@Table(name = "SYS_DATA_WHERE")
public class WhereSegment implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	@Column(name = "PARENTID_")
	protected Long parentId;

	/**
	 * 数据集编号
	 */
	@Column(name = "DATASETID_", length = 50)
	protected String datasetId;

	/**
	 * 表名
	 */
	@Column(name = "TABLENAME_", length = 50)
	protected String tableName;

	/**
	 * 列名
	 */
	@Column(name = "COLUMNNAME_", length = 50)
	protected String columnName;

	/**
	 * 表达式
	 */
	@Column(name = "EXPRESSION_", length = 2000)
	protected String expression;

	/**
	 * 是否动态条件
	 */
	@Column(name = "DYNAMIC_", length = 1)
	protected String dynamic;

	/**
	 * 参数名称
	 */
	@Column(name = "PARAMNAME_", length = 100)
	protected String parameName;

	/**
	 * 参数类型
	 */
	@Column(name = "PARAMETYPE_", length = 50)
	protected String parameType;

	/**
	 * 是否集合
	 */
	@Column(name = "COLLECTION_", length = 50)
	protected String collection;

	/**
	 * 操作类型（>,=,>=,<,<=,like,in,exists...）
	 */
	@Column(name = "OPERATOR_", length = 50)
	protected String operator;

	/**
	 * 连接条件（AND或OR）
	 */
	@Column(name = "CONNECTOR_", length = 50)
	protected String connector;

	/**
	 * 顺序
	 */
	@Column(name = "ORDINAL_")
	protected Integer ordinal;

	/**
	 * 是否必须
	 */
	@Column(name = "REQUIRED_", length = 1)
	protected String required;

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

	@javax.persistence.Transient
	protected Object value;

	public WhereSegment() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WhereSegment other = (WhereSegment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getCollection() {
		return this.collection;
	}

	public String getColumnName() {
		return this.columnName;
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

	public String getDynamic() {
		return dynamic;
	}

	public String getExpression() {
		return this.expression;
	}

	public Long getId() {
		return this.id;
	}

	/**
	 * Converts from SQL string literals to an Operator. Valid SQL values are
	 * "=", "<>", "LIKE", ">", ">=", "<" and "<=".
	 *
	 * @return a Operator object representing the specified SQL type
	 */
	public String getOperator() {
		return this.operator;
	}

	public Integer getOrdinal() {
		return this.ordinal;
	}

	public String getParameName() {
		return this.parameName;
	}

	public String getParameType() {
		return this.parameType;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public String getRequired() {
		return this.required;
	}

	public String getTableName() {
		return this.tableName;
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

	public Object getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public WhereSegment jsonToObject(JSONObject jsonObject) {
		return WhereSegmentJsonFactory.jsonToObject(jsonObject);
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
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

	public void setDynamic(String dynamic) {
		this.dynamic = dynamic;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public void setParameName(String parameName) {
		this.parameName = parameName;
	}

	public void setParameType(String parameType) {
		this.parameType = parameType;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public JSONObject toJsonObject() {
		return WhereSegmentJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return WhereSegmentJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
