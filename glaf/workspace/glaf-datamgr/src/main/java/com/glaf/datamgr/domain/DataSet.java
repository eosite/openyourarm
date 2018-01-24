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
@Table(name = "SYS_DATASET")
public class DataSet implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false, length = 50)
	protected String id;

	/**
	 * 父级ID
	 */
	@Column(name = "TOPID_", length = 50)
	protected String topId;

	/**
	 * 层级ID
	 */
	@Column(name = "TREEID_", length = 200)
	protected String treeId;

	/**
	 * 数据库编号
	 */
	@Column(name = "DATABASEID_")
	protected Long databaseId;

	/**
	 * 可执行数据库编号
	 */
	@Column(name = "EXECUTEDATABASEIDS_", length = 2000)
	protected String executeDatabaseIds;

	@Column(name = "NODEID_")
	protected Long nodeId;

	/**
	 * 类别
	 */
	@Column(name = "CATEGORY_", length = 50)
	protected String category;

	/**
	 * 名称
	 */
	@Column(name = "NAME_", length = 200)
	protected String name;

	/**
	 * 主题
	 */
	@Column(name = "TITLE_", length = 200)
	protected String title;

	/**
	 * 描述
	 */
	@Column(name = "DESCRIPTION_", length = 500)
	protected String description;

	/**
	 * MyBatis的select语句编号
	 */
	@Column(name = "STATEMENTID_", length = 100)
	protected String statementId;

	/**
	 * 
	 * SQL语句
	 */
	@Lob
	@Column(name = "SQL_")
	protected String sql;

	/**
	 * 
	 * 扩展文本(json结构)
	 */
	@Lob
	@Column(name = "EXTTEXT_")
	protected String extText;

	/**
	 * 访问类型
	 */
	@Column(name = "ACCESSTYPE_", length = 20)
	protected String accessType;

	/**
	 * 权限集
	 */
	@Column(name = "PERMS_", length = 500)
	protected String perms;

	/**
	 * 允许地址列表
	 */
	@Column(name = "ADDRESSPERMS_", length = 2000)
	protected String addressPerms;

	/**
	 * 类型
	 */
	@Column(name = "TYPE_", length = 50)
	protected String type;

	/**
	 * 是否激活
	 */
	@Column(name = "ACTIVE_", length = 1)
	protected String active;

	/**
	 * 是否缓存
	 */
	@Column(name = "CACHEFLAG_", length = 1)
	protected String cacheFlag;

	/**
	 * 是否图表定义
	 */
	@Column(name = "CHARTFLAG_", length = 1)
	protected String chartFlag;

	/**
	 * 动态标记
	 */
	@Column(name = "DYNAMICFLAG_", length = 1)
	protected String dynamicFlag;

	/**
	 * 动态数据集
	 */
	@Column(name = "DYNAMICDATASET_", length = 1)
	protected String dynamicDataSet;

	/**
	 * 是否唯一
	 */
	@Column(name = "DISTINCTFLAG_", length = 1)
	protected String distinctFlag;

	/**
	 * 是否共享
	 */
	@Column(name = "SHAREFLAG_", length = 1)
	protected String shareFlag;

	/**
	 * 是否定时调度
	 */
	@Column(name = "SCHEDULEFLAG_", length = 1)
	protected String scheduleFlag;

	/**
	 * 是否公开
	 */
	@Column(name = "PUBLICFLAG_", length = 1)
	protected String publicFlag;

	/**
	 * 保存标记
	 */
	@Column(name = "SAVEFLAG_", length = 10)
	protected String saveFlag;

	/**
	 * 是否可修改
	 */
	@Column(name = "UPDATEFLAG_", length = 1)
	protected String updateFlag;

	/**
	 * 是否验证通过
	 */
	@Column(name = "VERIFY_", length = 1)
	protected String verify;

	/**
	 * 是否初始化
	 */
	@Column(name = "INITFLAG_", length = 1)
	protected String initFlag;

	/**
	 * 是否导出
	 */
	@Column(name = "EXPORTFLAG_", length = 1)
	protected String exportFlag;

	/**
	 * 导出表名
	 */
	@Column(name = "EXPORTTABLENAME_", length = 50)
	protected String exportTableName;

	/**
	 * 导出库名
	 */
	@Column(name = "EXPORTDBNAME_", length = 50)
	protected String exportDBName;

	/**
	 * 最后导出时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LASTEXPORTTIME_")
	protected Date lastExportTime;

	/**
	 * 最后导出标记
	 */
	@Column(name = "LASTEXPORTSTATUS_")
	protected Integer lastExportStatus;

	/**
	 * 来源规则编号
	 */
	@Column(name = "SOURCERULEID_", length = 200)
	protected String sourceRuleId;

	/**
	 * 来源规则类型
	 */
	@Column(name = "SOURCERULETYPE_", length = 50)
	protected String sourceRuleType;

	/**
	 * 来源数据表
	 */
	@Lob
	@Column(name = "SOURCETABLES_")
	protected String sourceTables;

	@Column(name = "ROWKEY_", length = 500)
	protected String rowKey;

	/**
	 * 每次抓取前删除
	 */
	@Column(name = "DELETEFETCH_", length = 1)
	protected String deleteFetch;

	/**
	 * 启用/禁用
	 */
	@Column(name = "LOCKED_")
	protected int locked;

	/**
	 * 删除标记
	 */
	@Column(name = "DELETEFLAG_")
	protected int deleteFlag;

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
	protected boolean checked;

	@javax.persistence.Transient
	protected String lastestJson;

	@javax.persistence.Transient
	protected JSONObject lastJson;

	@javax.persistence.Transient
	private boolean extend = false;

	@javax.persistence.Transient
	private Boolean hasMapping = null;

	/**
	 * 主键 [可能有多个]
	 */
	@Column(name = "PRIMARYKEYS_", length = 100)
	protected String primaryKeys;

	@javax.persistence.Transient
	protected List<SelectSegment> selectSegments = new ArrayList<SelectSegment>();

	@javax.persistence.Transient
	protected List<FromSegment> fromSegments = new ArrayList<FromSegment>();

	@javax.persistence.Transient
	protected List<WhereSegment> whereSegments = new ArrayList<WhereSegment>();

	@javax.persistence.Transient
	protected List<JoinSegment> joinSegments = new ArrayList<JoinSegment>();

	@javax.persistence.Transient
	protected List<OrderBySegment> orderBySegments = new ArrayList<OrderBySegment>();

	@javax.persistence.Transient
	protected List<GroupBySegment> groupBySegments = new ArrayList<GroupBySegment>();

	@javax.persistence.Transient
	protected List<HavingSegment> havingSegments = new ArrayList<HavingSegment>();

	@javax.persistence.Transient
	protected List<SqlParameter> parameters = new ArrayList<SqlParameter>();

	public DataSet() {

	}

	public void addFrom(FromSegment segment) {
		if (fromSegments == null) {
			fromSegments = new ArrayList<FromSegment>();
		}
		fromSegments.add(segment);
	}

	public void addGroupBy(GroupBySegment segment) {
		if (groupBySegments == null) {
			groupBySegments = new ArrayList<GroupBySegment>();
		}
		groupBySegments.add(segment);
	}

	public void addHaving(HavingSegment segment) {
		if (havingSegments == null) {
			havingSegments = new ArrayList<HavingSegment>();
		}
		havingSegments.add(segment);
	}

	public void addJoin(JoinSegment segment) {
		if (joinSegments == null) {
			joinSegments = new ArrayList<JoinSegment>();
		}
		joinSegments.add(segment);
	}

	public void addOrderBy(OrderBySegment segment) {
		if (orderBySegments == null) {
			orderBySegments = new ArrayList<OrderBySegment>();
		}
		orderBySegments.add(segment);
	}

	public void addSelect(SelectSegment segment) {
		if (selectSegments == null) {
			selectSegments = new ArrayList<SelectSegment>();
		}
		selectSegments.add(segment);
	}

	public void addSqlParameter(SqlParameter param) {
		if (parameters == null) {
			parameters = new ArrayList<SqlParameter>();
		}
		parameters.add(param);
	}

	public void addWhere(WhereSegment segment) {
		if (whereSegments == null) {
			whereSegments = new ArrayList<WhereSegment>();
		}
		whereSegments.add(segment);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataSet other = (DataSet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getAccessType() {
		return this.accessType;
	}

	public String getActive() {
		return active;
	}

	public String getAddressPerms() {
		return this.addressPerms;
	}

	public String getCacheFlag() {
		return this.cacheFlag;
	}

	public String getCategory() {
		return category;
	}

	public String getChartFlag() {
		return chartFlag;
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

	public Long getDatabaseId() {
		return databaseId;
	}

	public String getDeleteFetch() {
		return deleteFetch;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public String getDescription() {
		return this.description;
	}

	public String getDistinctFlag() {
		return distinctFlag;
	}

	public String getDynamicFlag() {
		return dynamicFlag;
	}

	public String getExecuteDatabaseIds() {
		return executeDatabaseIds;
	}

	public String getExportDBName() {
		return exportDBName;
	}

	public String getExportFlag() {
		return exportFlag;
	}

	public String getExportTableName() {
		return exportTableName;
	}

	public List<FromSegment> getFromSegments() {
		return fromSegments;
	}

	public List<GroupBySegment> getGroupBySegments() {
		return groupBySegments;
	}

	public List<HavingSegment> getHavingSegments() {
		return havingSegments;
	}

	public String getId() {
		return this.id;
	}

	public String getInitFlag() {
		return initFlag;
	}

	public List<JoinSegment> getJoinSegments() {
		return joinSegments;
	}

	public String getLastestJson() {
		return lastestJson;
	}

	public Integer getLastExportStatus() {
		return lastExportStatus;
	}

	public Date getLastExportTime() {
		return lastExportTime;
	}

	public int getLocked() {
		return this.locked;
	}

	public String getName() {
		return this.name;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public List<OrderBySegment> getOrderBySegments() {
		return orderBySegments;
	}

	public List<SqlParameter> getParameters() {
		return parameters;
	}

	public String getPerms() {
		return this.perms;
	}

	public String getPublicFlag() {
		return publicFlag;
	}

	public String getRowKey() {
		return rowKey;
	}

	public String getSaveFlag() {
		return saveFlag;
	}

	public String getScheduleFlag() {
		return scheduleFlag;
	}

	public List<SelectSegment> getSelectSegments() {
		return selectSegments;
	}

	public String getShareFlag() {
		return shareFlag;
	}

	public String getSourceRuleId() {
		return sourceRuleId;
	}

	public String getSourceRuleType() {
		return sourceRuleType;
	}

	public String getSourceTables() {
		return sourceTables;
	}

	public String getSql() {
		return this.sql;
	}

	public String getStatementId() {
		return statementId;
	}

	public String getTitle() {
		return this.title;
	}

	public String getType() {
		return this.type;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public String getUpdateFlag() {
		return updateFlag;
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

	public String getVerify() {
		return verify;
	}

	public List<WhereSegment> getWhereSegments() {
		return whereSegments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean isChecked() {
		return checked;
	}

	public boolean isExtend() {
		return extend;
	}

	public DataSet jsonToObject(JSONObject jsonObject) {
		return DataSetJsonFactory.jsonToObject(jsonObject);
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setAddressPerms(String addressPerms) {
		this.addressPerms = addressPerms;
	}

	public void setCacheFlag(String cacheFlag) {
		this.cacheFlag = cacheFlag;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setChartFlag(String chartFlag) {
		this.chartFlag = chartFlag;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public void setDeleteFetch(String deleteFetch) {
		this.deleteFetch = deleteFetch;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDistinctFlag(String distinctFlag) {
		this.distinctFlag = distinctFlag;
	}

	public void setDynamicFlag(String dynamicFlag) {
		this.dynamicFlag = dynamicFlag;
	}

	public void setExecuteDatabaseIds(String executeDatabaseIds) {
		this.executeDatabaseIds = executeDatabaseIds;
	}

	public void setExportDBName(String exportDBName) {
		this.exportDBName = exportDBName;
	}

	public void setExportFlag(String exportFlag) {
		this.exportFlag = exportFlag;
	}

	public void setExportTableName(String exportTableName) {
		this.exportTableName = exportTableName;
	}

	public void setExtend(boolean extend) {
		this.extend = extend;
	}

	public void setFromSegments(List<FromSegment> fromSegments) {
		this.fromSegments = fromSegments;
	}

	public void setGroupBySegments(List<GroupBySegment> groupBySegments) {
		this.groupBySegments = groupBySegments;
	}

	public void setHavingSegments(List<HavingSegment> havingSegments) {
		this.havingSegments = havingSegments;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInitFlag(String initFlag) {
		this.initFlag = initFlag;
	}

	public void setJoinSegments(List<JoinSegment> joinSegments) {
		this.joinSegments = joinSegments;
	}

	public void setLastestJson(String lastestJson) {
		this.lastestJson = lastestJson;
	}

	public void setLastExportStatus(Integer lastExportStatus) {
		this.lastExportStatus = lastExportStatus;
	}

	public void setLastExportTime(Date lastExportTime) {
		this.lastExportTime = lastExportTime;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setOrderBySegments(List<OrderBySegment> orderBySegments) {
		this.orderBySegments = orderBySegments;
	}

	public void setParameters(List<SqlParameter> parameters) {
		this.parameters = parameters;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public void setPublicFlag(String publicFlag) {
		this.publicFlag = publicFlag;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public void setScheduleFlag(String scheduleFlag) {
		this.scheduleFlag = scheduleFlag;
	}

	public void setSelectSegments(List<SelectSegment> selectSegments) {
		this.selectSegments = selectSegments;
	}

	public void setShareFlag(String shareFlag) {
		this.shareFlag = shareFlag;
	}

	public void setSourceRuleId(String sourceRuleId) {
		this.sourceRuleId = sourceRuleId;
	}

	public void setSourceRuleType(String sourceRuleType) {
		this.sourceRuleType = sourceRuleType;
	}

	public void setSourceTables(String sourceTables) {
		this.sourceTables = sourceTables;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public void setWhereSegments(List<WhereSegment> whereSegments) {
		this.whereSegments = whereSegments;
	}

	public JSONObject toJsonObject() {
		return DataSetJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DataSetJsonFactory.toObjectNode(this);
	}

	public String getDynamicDataSet() {
		return dynamicDataSet;
	}

	public void setDynamicDataSet(String dynamicDataSet) {
		this.dynamicDataSet = dynamicDataSet;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(String primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	public String getTopId() {
		return topId;
	}

	public void setTopId(String topId) {
		this.topId = topId;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getExtText() {
		return extText;
	}

	public void setExtText(String extText) {
		this.extText = extText;
	}

	public JSONObject getLastJson() {
		return lastJson;
	}

	public void setLastJson(JSONObject lastJson) {
		this.lastJson = lastJson;
	}

	public Boolean isHasMapping() {
		return hasMapping;
	}

	public void setHasMapping(Boolean hasMapping) {
		this.hasMapping = hasMapping;
	}

}
