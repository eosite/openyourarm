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

package com.glaf.matrix.data.domain;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.matrix.data.util.SysTableJsonFactory;
import com.glaf.matrix.data.util.TableColumnJsonFactory;

/**
 * 数据表定义
 * 
 * 
 */
@Entity
@Table(name = "SYS_EXT_TABLE")
public class SysTable implements java.io.Serializable, java.lang.Comparable<SysTable> {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TABLEID_", length = 50)
	protected String tableId;

	/**
	 * 表名
	 */
	@Column(name = "TABLENAME_", length = 50)
	protected String tableName;

	@Column(name = "ADDTYPE_")
	protected int addType;

	@Transient
	protected String dbType;

	/**
	 * 聚合主键列集
	 */
	@Column(name = "AGGREGATIONKEYS_", length = 500)
	protected String aggregationKeys;

	@Transient
	protected String className;

	@Column(name = "COLUMNQTY_")
	protected int columnQty;

	@Transient
	protected List<TableColumn> columns = new java.util.ArrayList<TableColumn>();

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
	 * 级联删除
	 */
	@Column(name = "DELETECASCADE_")
	protected int deleteCascade;

	/**
	 * 是否删除抓取数据
	 */
	@Column(name = "DELETEFETCH_", length = 1)
	protected String deleteFetch;

	@Column(name = "DELETEFLAG_")
	protected int deleteFlag;

	/**
	 * 描述
	 */
	@Column(name = "DESCRIPTION_", length = 500)
	protected String description;

	/**
	 * 显示类型 form,grid,tree,treegrid
	 */
	@Column(name = "DISPLAYTYPE_", length = 50)
	protected String displayType;

	/**
	 * 标题
	 */
	@Column(name = "ENGLISHTITLE_")
	protected String englishTitle;

	@Transient
	protected String entityName;

	@Transient
	protected TableColumn idColumn;

	/**
	 * 级联插入
	 */
	@Column(name = "INSERTCASCADE_")
	protected int insertCascade;

	@Transient
	protected boolean updateAllowed = true;

	/**
	 * 是否从表(Y-从表，N-不是从表)
	 */
	@Column(name = "ISSUBTABLE_", length = 2)
	protected String isSubTable;

	/**
	 * 是否需要JBPM工作流支持
	 */
	@Transient
	protected boolean jbpmSupport;

	/**
	 * 是否锁定
	 */
	@Column(name = "LOCKED_")
	protected int locked;

	/**
	 * 模块名称
	 */
	@Transient
	protected String moduleName;

	@Transient
	protected String packageName;

	@Column(name = "PRIMARYKEY_", length = 200)
	protected String primaryKey;

	/**
	 * 修订版本
	 */
	@Column(name = "REVISION_")
	protected int revision;

	@Column(name = "SORTNO_")
	protected int sortNo;

	@Column(name = "STARTROWINDEX_")
	protected int startRowIndex;

	@Column(name = "SYSNUM_", length = 100)
	protected String sysnum;

	@Column(name = "SYSTEMFLAG_", length = 2)
	protected String systemFlag;

	@Transient
	protected String name;

	/**
	 * 附件标识 0-无，1-1个附件，2-多个附件
	 */
	@Column(name = "ATTACHMENTFLAG_", length = 1)
	protected String attachmentFlag;

	/**
	 * 附件允许的扩展名
	 */
	@Column(name = "ATTACHMENTEXTS_", length = 200)
	protected String attachmentExts;

	/**
	 * 附件大小
	 */
	@Column(name = "ATTACHMENTSIZE_")
	protected int attachmentSize;

	/**
	 * 审核标记
	 */
	@Column(name = "AUDITFLAG_", length = 10)
	protected String auditFlag;

	/**
	 * 树型结构标识， Y-树型，N-非树型
	 */
	@Column(name = "TREEFLAG_", length = 1)
	protected String treeFlag;

	/**
	 * 权限标识
	 */
	@Column(name = "PRIVILEGEFLAG_", length = 10)
	protected String privilegeFlag;

	/**
	 * 是否临时表
	 */
	@Column(name = "TEMPORARYFLAG_", length = 1)
	protected String temporaryFlag;

	/**
	 * 标题
	 */
	@Column(name = "TITLE_")
	protected String title;

	@Column(name = "TOPID_", length = 50)
	protected String topId;

	/**
	 * 是否树型结构
	 */
	@Transient
	protected boolean treeSupport;

	/**
	 * 表类型
	 */
	@Column(name = "TYPE_", length = 50)
	protected String type;

	/**
	 * 级联更新
	 */
	@Column(name = "UPDATECASCADE_")
	protected int updateCascade;

	@Column(name = "NODEID_")
	protected Long nodeId;

	/**
	 * TableType
	 */
	@Column(name = "TABLETYPE_", length = 50)
	protected String tableType;

	/**
	 * InsertOnly
	 */
	@Column(name = "INSERTONLY_", length = 10)
	protected String insertOnly;

	/**
	 * StopSkipRow
	 */
	@Column(name = "STOPSKIPROW_")
	protected int stopSkipRow;

	/**
	 * StopWord
	 */
	@Column(name = "STOPWORD_", length = 500)
	protected String stopWord;

	/**
	 * Split
	 */
	@Column(name = "SPLIT_", length = 200)
	protected String split;

	@javax.persistence.Transient
	protected TableCorrelation tableCorrelation;

	public SysTable() {

	}

	public void addColumn(TableColumn column) {
		if (columns == null) {
			columns = new java.util.ArrayList<TableColumn>();
		}
		if (!columns.contains(column)) {
			columns.add(column);
		}
	}

	public void addField(TableColumn field) {
		if (columns == null) {
			columns = new java.util.ArrayList<TableColumn>();
		}
		JSONObject jsonObject = field.toJsonObject();
		TableColumn column = TableColumnJsonFactory.jsonToObject(jsonObject);
		columns.add(column);
	}

	public int compareTo(SysTable o) {
		if (o == null) {
			return -1;
		}

		SysTable field = o;

		int l = this.sortNo - field.getSortNo();

		int ret = 0;

		if (l > 0) {
			ret = 1;
		} else if (l < 0) {
			ret = -1;
		}
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysTable other = (SysTable) obj;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}

	public int getAddType() {
		return addType;
	}

	public String getAggregationKeys() {
		return aggregationKeys;
	}

	public String getAttachmentExts() {
		return attachmentExts;
	}

	public String getAttachmentFlag() {
		return attachmentFlag;
	}

	public int getAttachmentSize() {
		return attachmentSize;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public String getClassName() {
		if (className != null) {
			return className;
		}
		if (getPackageName() != null && getEntityName() != null) {
			return getPackageName() + "." + getEntityName();
		}
		return this.getEntityName();
	}

	public int getColumnQty() {
		return columnQty;
	}

	public List<TableColumn> getColumns() {
		return columns;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getDbType() {
		return dbType;
	}

	public int getDeleteCascade() {
		return deleteCascade;
	}

	public String getDeleteFetch() {
		return deleteFetch;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public String getDescription() {
		return description;
	}

	public String getDisplayType() {
		return displayType;
	}

	public String getEnglishTitle() {
		return englishTitle;
	}

	public String getEntityName() {
		return entityName;
	}

	public Map<String, TableColumn> getFields() {
		Map<String, TableColumn> fieldMap = new LinkedHashMap<String, TableColumn>();
		if (columns != null && !columns.isEmpty()) {
			for (TableColumn column : columns) {
				fieldMap.put(column.getName(), column);
			}
		}
		return fieldMap;
	}

	public TableColumn getIdColumn() {
		return idColumn;
	}

	public TableColumn getIdField() {
		return idColumn;
	}

	public int getInsertCascade() {
		return insertCascade;
	}

	public String getInsertOnly() {
		return insertOnly;
	}

	public String getIsSubTable() {
		return isSubTable;
	}

	public int getLocked() {
		return locked;
	}

	public String getModuleName() {
		if (moduleName == null) {
			moduleName = "apps";
		}
		return moduleName;
	}

	public String getName() {
		return name;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public String getPrivilegeFlag() {
		return privilegeFlag;
	}

	public int getRevision() {
		return revision;
	}

	public int getSortNo() {
		return sortNo;
	}

	public String getSplit() {
		return split;
	}

	public int getStartRowIndex() {
		return startRowIndex;
	}

	public int getStopSkipRow() {
		return stopSkipRow;
	}

	public String getStopWord() {
		return stopWord;
	}

	public String getSysnum() {
		return sysnum;
	}

	public String getSystemFlag() {
		return systemFlag;
	}

	public TableCorrelation getTableCorrelation() {
		return tableCorrelation;
	}

	public String getTableId() {
		return tableId;
	}

	public String getTableName() {
		return tableName;
	}

	public String getTableType() {
		return tableType;
	}

	public String getTemporaryFlag() {
		return temporaryFlag;
	}

	public String getTitle() {
		return title;
	}

	public String getTopId() {
		return topId;
	}

	public String getTreeFlag() {
		return treeFlag;
	}

	public String getType() {
		return type;
	}

	public int getUpdateCascade() {
		return updateCascade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
		return result;
	}

	public boolean isJbpmSupport() {
		return jbpmSupport;
	}

	public boolean isTreeSupport() {
		return treeSupport;
	}

	public boolean isUpdateAllowed() {
		return updateAllowed;
	}

	public SysTable jsonToObject(JSONObject jsonObject) {
		return SysTableJsonFactory.jsonToObject(jsonObject);
	}

	public void setAddType(int addType) {
		this.addType = addType;
	}

	public void setAggregationKeys(String aggregationKeys) {
		this.aggregationKeys = aggregationKeys;
	}

	public void setAttachmentExts(String attachmentExts) {
		this.attachmentExts = attachmentExts;
	}

	public void setAttachmentFlag(String attachmentFlag) {
		this.attachmentFlag = attachmentFlag;
	}

	public void setAttachmentSize(int attachmentSize) {
		this.attachmentSize = attachmentSize;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setColumnQty(int columnQty) {
		this.columnQty = columnQty;
	}

	public void setColumns(List<TableColumn> columns) {
		this.columns = columns;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public void setDeleteCascade(int deleteCascade) {
		this.deleteCascade = deleteCascade;
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

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public void setEnglishTitle(String englishTitle) {
		this.englishTitle = englishTitle;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public void setIdColumn(TableColumn idColumn) {
		if (idColumn != null) {
			this.idColumn = idColumn;
			this.idColumn.setPrimaryKey(true);
			this.addColumn(idColumn);
		}
	}

	public void setIdField(TableColumn idField) {
		JSONObject jsonObject = idField.toJsonObject();
		this.idColumn = TableColumnJsonFactory.jsonToObject(jsonObject);
		idColumn.setPrimaryKey(true);
	}

	public void setInsertCascade(int insertCascade) {
		this.insertCascade = insertCascade;
	}

	public void setInsertOnly(String insertOnly) {
		this.insertOnly = insertOnly;
	}

	public void setIsSubTable(String isSubTable) {
		this.isSubTable = isSubTable;
	}

	public void setJbpmSupport(boolean jbpmSupport) {
		this.jbpmSupport = jbpmSupport;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void setPrivilegeFlag(String privilegeFlag) {
		this.privilegeFlag = privilegeFlag;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public void setSplit(String split) {
		this.split = split;
	}

	public void setStartRowIndex(int startRowIndex) {
		this.startRowIndex = startRowIndex;
	}

	public void setStopSkipRow(int stopSkipRow) {
		this.stopSkipRow = stopSkipRow;
	}

	public void setStopWord(String stopWord) {
		this.stopWord = stopWord;
	}

	public void setSysnum(String sysnum) {
		this.sysnum = sysnum;
	}

	public void setSystemFlag(String systemFlag) {
		this.systemFlag = systemFlag;
	}

	public void setTableCorrelation(TableCorrelation tableCorrelation) {
		this.tableCorrelation = tableCorrelation;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public void setTemporaryFlag(String temporaryFlag) {
		this.temporaryFlag = temporaryFlag;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTopId(String topId) {
		this.topId = topId;
	}

	public void setTreeFlag(String treeFlag) {
		this.treeFlag = treeFlag;
	}

	public void setTreeSupport(boolean treeSupport) {
		this.treeSupport = treeSupport;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUpdateAllowed(boolean updateAllowed) {
		this.updateAllowed = updateAllowed;
	}

	public void setUpdateCascade(int updateCascade) {
		this.updateCascade = updateCascade;
	}

	public JSONObject toJsonObject() {
		return SysTableJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysTableJsonFactory.toObjectNode(this);
	}

}