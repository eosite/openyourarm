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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.StringTools;
import com.glaf.datamgr.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "TABLE_COMBINATION_RULE")
public class TableCombinationRule implements Serializable, JSONable {
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
	 * 目标表名称
	 */
	@Column(name = "TARGETTABLENAME_", length = 50)
	protected String targetTableName;

	/**
	 * 目标表主键列
	 */
	@Column(name = "TARGETTABLEPRIMARYKEY_", length = 50)
	protected String targetTablePrimaryKey;

	/**
	 * 唯一列
	 */
	@Column(name = "UNIQUENESSCOLUMN_", length = 50)
	protected String uniquenessColumn;

	/**
	 * 转换条件
	 */
	@Column(name = "SQLCONDITION_", length = 2000)
	protected String sqlCondition;

	public TableCombinationRule() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableCombinationRule other = (TableCombinationRule) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public long getCombinationId() {
		return this.combinationId;
	}

	public long getId() {
		return this.id;
	}

	public String getSqlCondition() {
		if (StringUtils.isNotEmpty(sqlCondition)) {
			sqlCondition = StringTools.replace(sqlCondition, "‘", "'");
			sqlCondition = StringTools.replace(sqlCondition, "’", "'");
		}
		return this.sqlCondition;
	}

	public String getTargetTableName() {
		return this.targetTableName;
	}

	public String getTargetTablePrimaryKey() {
		if (targetTablePrimaryKey != null) {
			targetTablePrimaryKey = targetTablePrimaryKey.trim();
			targetTablePrimaryKey = targetTablePrimaryKey.toLowerCase();
		}
		return this.targetTablePrimaryKey;
	}

	public String getUniquenessColumn() {
		return uniquenessColumn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public TableCombinationRule jsonToObject(JSONObject jsonObject) {
		return TableCombinationRuleJsonFactory.jsonToObject(jsonObject);
	}

	public void setCombinationId(long combinationId) {
		this.combinationId = combinationId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setSqlCondition(String sqlCondition) {
		this.sqlCondition = sqlCondition;
	}

	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}

	public void setTargetTablePrimaryKey(String targetTablePrimaryKey) {
		this.targetTablePrimaryKey = targetTablePrimaryKey;
	}

	public void setUniquenessColumn(String uniquenessColumn) {
		this.uniquenessColumn = uniquenessColumn;
	}

	public JSONObject toJsonObject() {
		return TableCombinationRuleJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return TableCombinationRuleJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
