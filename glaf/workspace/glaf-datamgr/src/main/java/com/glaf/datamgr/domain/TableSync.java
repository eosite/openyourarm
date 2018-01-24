package com.glaf.datamgr.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.lang.StringUtils;
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

@Entity
@Table(name = "SYS_TABLE_SYNC")
public class TableSync implements Serializable, JSONable {
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
	 * SQL条件
	 */
	@Column(name = "SQLCRITERIA_", length = 4000)
	protected String sqlCriteria;

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
	 * 来源数据库编号
	 */
	@Column(name = "SOURCEDATABASEID_")
	protected long sourceDatabaseId;

	/**
	 * 来源表名
	 */
	@Column(name = "SOURCETABLENAME_", length = 50)
	protected String sourceTableName;

	/**
	 * 来源表执行
	 */
	@Column(name = "SOURCETABLEEXECUTIONIDS_", length = 800)
	protected String sourceTableExecutionIds;

	/**
	 * 主键列
	 */
	@Column(name = "PRIMARYKEY_", length = 50)
	protected String primaryKey;

	/**
	 * 同步列
	 */
	@Lob
	@Column(name = "SYNCCOLUMNS_", length = 4000)
	protected String syncColumns;

	/**
	 * 目标表名
	 */
	@Column(name = "TARGETTABLENAME_", length = 50)
	protected String targetTableName;

	/**
	 * 目标数据库编号
	 */
	@Column(name = "TARGETDATABASEIDS_", length = 2000)
	protected String targetDatabaseIds;

	/**
	 * 目标表执行
	 */
	@Column(name = "TARGETTABLEEXECUTIONIDS_", length = 800)
	protected String targetTableExecutionIds;

	/**
	 * 是否调度
	 */
	@Column(name = "SCHEDULEFLAG_", length = 1)
	protected String scheduleFlag;

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
	 * 新增但不更新数据
	 */
	@Column(name = "INSERTONLY_", length = 1)
	protected String insertOnly;

	@Column(name = "JOBNO_", length = 50)
	protected String jobNo;

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
	protected ColumnModel idColumn;

	@javax.persistence.Transient
	protected List<ColumnModel> columns = new ArrayList<ColumnModel>();

	public TableSync() {

	}

	public void addColumn(ColumnModel column) {
		if (columns == null) {
			columns = new ArrayList<ColumnModel>();
		}
		columns.add(column);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableSync other = (TableSync) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public List<ColumnModel> getColumns() {
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

	public String getDeleteFetch() {
		return this.deleteFetch;
	}

	public long getId() {
		return this.id;
	}

	public ColumnModel getIdColumn() {
		return idColumn;
	}

	public String getInsertOnly() {
		return this.insertOnly;
	}

	public String getJobNo() {
		return jobNo;
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
		return primaryKey;
	}

	public String getScheduleFlag() {
		return this.scheduleFlag;
	}

	public int getSortNo() {
		return sortNo;
	}

	public long getSourceDatabaseId() {
		return this.sourceDatabaseId;
	}

	public String getSourceTableExecutionIds() {
		return sourceTableExecutionIds;
	}

	public String getSourceTableName() {
		return this.sourceTableName;
	}

	public String getSqlCriteria() {
		if (StringUtils.isNotEmpty(sqlCriteria)) {
			sqlCriteria = StringTools.replace(sqlCriteria, "‘", "'");
			sqlCriteria = StringTools.replace(sqlCriteria, "’", "'");
		}
		return sqlCriteria;
	}

	public String getSyncColumns() {
		if (syncColumns != null) {
			syncColumns = syncColumns.trim();
			syncColumns = syncColumns.toLowerCase();
			List<String> columns = StringTools.split(syncColumns);
			if (columns != null && !columns.isEmpty()) {
				if (StringUtils.isNotEmpty(primaryKey)) {
					if (!columns.contains(primaryKey.toLowerCase())) {
						syncColumns = primaryKey + ", " + syncColumns;
					}
				}
			}
		}
		return syncColumns;
	}

	public int getSyncStatus() {
		return this.syncStatus;
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

	public String getTargetDatabaseIds() {
		return this.targetDatabaseIds;
	}

	public String getTargetTableExecutionIds() {
		return targetTableExecutionIds;
	}

	public String getTargetTableName() {
		return this.targetTableName;
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

	public TableSync jsonToObject(JSONObject jsonObject) {
		return TableSyncJsonFactory.jsonToObject(jsonObject);
	}

	public void setColumns(List<ColumnModel> columns) {
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

	public void setDeleteFetch(String deleteFetch) {
		this.deleteFetch = deleteFetch;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIdColumn(ColumnModel idColumn) {
		this.idColumn = idColumn;
	}

	public void setInsertOnly(String insertOnly) {
		this.insertOnly = insertOnly;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
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

	public void setScheduleFlag(String scheduleFlag) {
		this.scheduleFlag = scheduleFlag;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public void setSourceDatabaseId(long sourceDatabaseId) {
		this.sourceDatabaseId = sourceDatabaseId;
	}

	public void setSourceTableExecutionIds(String sourceTableExecutionIds) {
		this.sourceTableExecutionIds = sourceTableExecutionIds;
	}

	public void setSourceTableName(String sourceTableName) {
		this.sourceTableName = sourceTableName;
	}

	public void setSqlCriteria(String sqlCriteria) {
		this.sqlCriteria = sqlCriteria;
	}

	public void setSyncColumns(String syncColumns) {
		this.syncColumns = syncColumns;
	}

	public void setSyncStatus(int syncStatus) {
		this.syncStatus = syncStatus;
	}

	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}

	public void setTargetDatabaseIds(String targetDatabaseIds) {
		this.targetDatabaseIds = targetDatabaseIds;
	}

	public void setTargetTableExecutionIds(String targetTableExecutionIds) {
		this.targetTableExecutionIds = targetTableExecutionIds;
	}

	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
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

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public JSONObject toJsonObject() {
		return TableSyncJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return TableSyncJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
