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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.StringTools;
import com.glaf.datamgr.util.*;

/**
 * 
 * 实体对象
 *
 */

// select * from TABLE_COMBINATION;

// select * from TABLE_COMBINATION_RULE;

// select * from TABLE_COMBINATION_COL;

@Entity
@Table(name = "TABLE_COMBINATION")
public class TableCombination implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected long id;

	/**
	 * 名称
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * 标题
	 */
	@Column(name = "TITLE_", length = 200)
	protected String title;

	/**
	 * 类型
	 */
	@Column(name = "TYPE_", length = 20)
	protected String type;

	/**
	 * 转换数据库编号
	 */
	@Column(name = "DATABASEIDS_", length = 2000)
	protected String databaseIds;

	/**
	 * 模板表
	 */
	@Column(name = "TEMPLATETABLENAME_", length = 50)
	protected String templateTableName;

	/**
	 * 主键列
	 */
	@Column(name = "PRIMARYKEY_", length = 50)
	protected String primaryKey;

	/**
	 * 所有业务表唯一列
	 */
	@Column(name = "UNIQUENESSKEY_", length = 50)
	protected String uniquenessKey;

	/**
	 * 同步列
	 */
	@Column(name = "SYNCCOLUMNS_", length = 2000)
	protected String syncColumns;

	/**
	 * 组合表
	 */
	@Column(name = "SYNCTABLENAMES_", length = 4000)
	protected String syncTableNames;

	/**
	 * 是否调度
	 */
	@Column(name = "SCHEDULEFLAG_", length = 1)
	protected String scheduleFlag;

	/**
	 * 来源数据集
	 */
	@Column(name = "DATASETIDS_", length = 4000)
	protected String datasetIds;

	/**
	 * 目标数据库编号
	 */
	@Column(name = "TARGETDATABASEID_")
	protected long targetDatabaseId;

	/**
	 * 目标表名
	 */
	@Column(name = "TARGETTABLENAME_", length = 50)
	protected String targetTableName;

	/**
	 * 目标表与模板表相同结构
	 */
	@Column(name = "TARGETTABLESTRUCT_", length = 50)
	protected String targetTableStruct;

	/**
	 * 当前用户关联标识
	 */
	@Column(name = "CURRENTUSERFLAG_", length = 1)
	protected String currentUserFlag;

	/**
	 * 是否需要建表
	 */
	@Column(name = "CREATETABLEFLAG_", length = 1)
	protected String createTableFlag;

	/**
	 * 每次抓取前删除
	 */
	@Column(name = "DELETEFETCH_", length = 1)
	protected String deleteFetch;

	/**
	 * 同步状态
	 */
	@Column(name = "SYNCSTATUS_")
	protected int syncStatus;

	/**
	 * 最后同步时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SYNCTIME_")
	protected Date syncTime;

	/**
	 * 顺序
	 */
	@Column(name = "SORTNO_")
	protected int sortNo;

	/**
	 * 是否锁定
	 */
	@Column(name = "LOCKED_")
	protected int locked;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	/**
	 * 修改人
	 */
	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy;

	/**
	 * 修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime;

	@javax.persistence.Transient
	protected List<TableCombinationColumn> columns = new ArrayList<TableCombinationColumn>();

	@javax.persistence.Transient
	protected List<TableCombinationRule> rules = new ArrayList<TableCombinationRule>();

	public TableCombination() {

	}

	public void addColumn(TableCombinationColumn col) {
		if (columns == null) {
			columns = new ArrayList<TableCombinationColumn>();
		}
		columns.add(col);
	}

	public void addRule(TableCombinationRule rule) {
		if (rules == null) {
			rules = new ArrayList<TableCombinationRule>();
		}
		rules.add(rule);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableCombination other = (TableCombination) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public List<TableCombinationColumn> getColumns() {
		return columns;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getCreateTableFlag() {
		return this.createTableFlag;
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

	public String getCurrentUserFlag() {
		return currentUserFlag;
	}

	public String getDatabaseIds() {
		return this.databaseIds;
	}

	public String getDatasetIds() {
		return datasetIds;
	}

	public String getDeleteFetch() {
		return this.deleteFetch;
	}

	public long getId() {
		return this.id;
	}

	public int getLocked() {
		return this.locked;
	}

	public String getName() {
		return this.name;
	}

	public String getPrimaryKey() {
		if (primaryKey != null) {
			primaryKey = primaryKey.trim();
			primaryKey = primaryKey.toLowerCase();
		}
		return this.primaryKey;
	}

	public List<TableCombinationRule> getRules() {
		return rules;
	}

	public String getScheduleFlag() {
		return scheduleFlag;
	}

	public int getSortNo() {
		return this.sortNo;
	}

	public String getSyncColumns() {
		if (syncColumns != null) {
			syncColumns = syncColumns.trim();
			syncColumns = syncColumns.toLowerCase();
			List<String> columns = StringTools.splitLowerCase(syncColumns);
			if (columns != null && !columns.isEmpty()) {
				if (StringUtils.isNotEmpty(primaryKey)) {
					if (!columns.contains(primaryKey.toLowerCase())) {
						syncColumns = primaryKey + ", " + syncColumns;
					}
				}
				if (StringUtils.isNotEmpty(uniquenessKey)) {
					if (!columns.contains(uniquenessKey.toLowerCase())) {
						syncColumns = syncColumns + ", " + uniquenessKey;
					}
				}
			}
		}
		return syncColumns;
	}

	public int getSyncStatus() {
		return this.syncStatus;
	}

	public String getSyncTableNames() {
		if (syncTableNames != null) {
			syncTableNames = syncTableNames.trim();
			syncTableNames = syncTableNames.toLowerCase();
		}
		return syncTableNames;
	}

	public Date getSyncTime() {
		return this.syncTime;
	}

	public String getSyncTimeString() {
		if (this.syncTime != null) {
			return DateUtils.getDateTime(this.syncTime);
		}
		return "";
	}

	public long getTargetDatabaseId() {
		return targetDatabaseId;
	}

	public String getTargetTableName() {
		return this.targetTableName;
	}

	public String getTargetTableStruct() {
		return targetTableStruct;
	}

	public String getTemplateTableName() {
		return this.templateTableName;
	}

	public String getTitle() {
		return this.title;
	}

	public String getType() {
		return this.type;
	}

	public String getUniquenessKey() {
		return uniquenessKey;
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
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public TableCombination jsonToObject(JSONObject jsonObject) {
		return TableCombinationJsonFactory.jsonToObject(jsonObject);
	}

	public void setColumns(List<TableCombinationColumn> columns) {
		this.columns = columns;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTableFlag(String createTableFlag) {
		this.createTableFlag = createTableFlag;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setCurrentUserFlag(String currentUserFlag) {
		this.currentUserFlag = currentUserFlag;
	}

	public void setDatabaseIds(String databaseIds) {
		this.databaseIds = databaseIds;
	}

	public void setDatasetIds(String datasetIds) {
		this.datasetIds = datasetIds;
	}

	public void setDeleteFetch(String deleteFetch) {
		this.deleteFetch = deleteFetch;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void setRules(List<TableCombinationRule> rules) {
		this.rules = rules;
	}

	public void setScheduleFlag(String scheduleFlag) {
		this.scheduleFlag = scheduleFlag;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public void setSyncColumns(String syncColumns) {
		this.syncColumns = syncColumns;
	}

	public void setSyncStatus(int syncStatus) {
		this.syncStatus = syncStatus;
	}

	public void setSyncTableNames(String syncTableNames) {
		this.syncTableNames = syncTableNames;
	}

	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}

	public void setTargetDatabaseId(long targetDatabaseId) {
		this.targetDatabaseId = targetDatabaseId;
	}

	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}

	public void setTargetTableStruct(String targetTableStruct) {
		this.targetTableStruct = targetTableStruct;
	}

	public void setTemplateTableName(String templateTableName) {
		this.templateTableName = templateTableName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUniquenessKey(String uniquenessKey) {
		this.uniquenessKey = uniquenessKey;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public JSONObject toJsonObject() {
		return TableCombinationJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return TableCombinationJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
