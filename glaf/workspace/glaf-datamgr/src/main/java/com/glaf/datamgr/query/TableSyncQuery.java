package com.glaf.datamgr.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class TableSyncQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String name;
	protected String titleLike;
	protected String type;
	protected Integer syncStatus;
	protected Date syncTimeGreaterThanOrEqual;
	protected Date syncTimeLessThanOrEqual;
	protected Long sourceDatabaseId;
	protected String sourceTableName;
	protected String targetTableName;
	protected String scheduleFlag;
	protected String createTableFlag;
	protected String deleteFetch;
	protected String insertOnly;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;

	public TableSyncQuery() {

	}

	public TableSyncQuery createTableFlag(String createTableFlag) {
		if (createTableFlag == null) {
			throw new RuntimeException("createTableFlag is null");
		}
		this.createTableFlag = createTableFlag;
		return this;
	}

	public TableSyncQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public TableSyncQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public TableSyncQuery deleteFetch(String deleteFetch) {
		if (deleteFetch == null) {
			throw new RuntimeException("deleteFetch is null");
		}
		this.deleteFetch = deleteFetch;
		return this;
	}

	public String getCreateTableFlag() {
		return createTableFlag;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public String getDeleteFetch() {
		return deleteFetch;
	}

	public String getInsertOnly() {
		return insertOnly;
	}

	public String getName() {
		return name;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("syncStatus".equals(sortColumn)) {
				orderBy = "E.SYNCSTATUS_" + a_x;
			}

			if ("syncTime".equals(sortColumn)) {
				orderBy = "E.SYNCTIME_" + a_x;
			}

			if ("sourceDatabaseId".equals(sortColumn)) {
				orderBy = "E.SOURCEDATABASEID_" + a_x;
			}

			if ("sourceTableName".equals(sortColumn)) {
				orderBy = "E.SOURCETABLENAME_" + a_x;
			}

			if ("primaryKey".equals(sortColumn)) {
				orderBy = "E.PRIMARYKEY_" + a_x;
			}

			if ("aggregationKeys".equals(sortColumn)) {
				orderBy = "E.AGGREGATIONKEYS_" + a_x;
			}

			if ("targetTableName".equals(sortColumn)) {
				orderBy = "E.TARGETTABLENAME_" + a_x;
			}

			if ("targetDatabaseIds".equals(sortColumn)) {
				orderBy = "E.TARGETDATABASEIDS_" + a_x;
			}

			if ("scheduleFlag".equals(sortColumn)) {
				orderBy = "E.SCHEDULEFLAG_" + a_x;
			}

			if ("createTableFlag".equals(sortColumn)) {
				orderBy = "E.CREATETABLEFLAG_" + a_x;
			}

			if ("deleteFetch".equals(sortColumn)) {
				orderBy = "E.DELETEFETCH_" + a_x;
			}

			if ("insertOnly".equals(sortColumn)) {
				orderBy = "E.INSERTONLY_" + a_x;
			}

			if ("locked".equals(sortColumn)) {
				orderBy = "E.LOCKED_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("createTime".equals(sortColumn)) {
				orderBy = "E.CREATETIME_" + a_x;
			}

			if ("updateBy".equals(sortColumn)) {
				orderBy = "E.UPDATEBY_" + a_x;
			}

			if ("updateTime".equals(sortColumn)) {
				orderBy = "E.UPDATETIME_" + a_x;
			}

		}
		return orderBy;
	}

	public String getScheduleFlag() {
		return scheduleFlag;
	}

	public Long getSourceDatabaseId() {
		return sourceDatabaseId;
	}

	public String getSourceTableName() {
		return sourceTableName;
	}

	public Integer getSyncStatus() {
		return syncStatus;
	}

	public Date getSyncTimeGreaterThanOrEqual() {
		return syncTimeGreaterThanOrEqual;
	}

	public Date getSyncTimeLessThanOrEqual() {
		return syncTimeLessThanOrEqual;
	}

	public String getTargetTableName() {
		return targetTableName;
	}

	public String getTitleLike() {
		if (titleLike != null && titleLike.trim().length() > 0) {
			if (!titleLike.startsWith("%")) {
				titleLike = "%" + titleLike;
			}
			if (!titleLike.endsWith("%")) {
				titleLike = titleLike + "%";
			}
		}
		return titleLike;
	}

	public String getType() {
		return type;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("name", "NAME_");
		addColumn("title", "TITLE_");
		addColumn("type", "TYPE_");
		addColumn("syncStatus", "SYNCSTATUS_");
		addColumn("syncTime", "SYNCTIME_");
		addColumn("sourceDatabaseId", "SOURCEDATABASEID_");
		addColumn("sourceTableName", "SOURCETABLENAME_");
		addColumn("primaryKey", "PRIMARYKEY_");
		addColumn("aggregationKeys", "AGGREGATIONKEYS_");
		addColumn("targetTableName", "TARGETTABLENAME_");
		addColumn("targetDatabaseIds", "TARGETDATABASEIDS_");
		addColumn("scheduleFlag", "SCHEDULEFLAG_");
		addColumn("createTableFlag", "CREATETABLEFLAG_");
		addColumn("deleteFetch", "DELETEFETCH_");
		addColumn("insertOnly", "INSERTONLY_");
		addColumn("locked", "LOCKED_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateTime", "UPDATETIME_");
	}

	public TableSyncQuery insertOnly(String insertOnly) {
		if (insertOnly == null) {
			throw new RuntimeException("insertOnly is null");
		}
		this.insertOnly = insertOnly;
		return this;
	}

	public TableSyncQuery locked(Integer locked) {
		if (locked == null) {
			throw new RuntimeException("locked is null");
		}
		this.locked = locked;
		return this;
	}

	public TableSyncQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public TableSyncQuery scheduleFlag(String scheduleFlag) {
		if (scheduleFlag == null) {
			throw new RuntimeException("scheduleFlag is null");
		}
		this.scheduleFlag = scheduleFlag;
		return this;
	}

	public void setCreateTableFlag(String createTableFlag) {
		this.createTableFlag = createTableFlag;
	}

	public void setCreateTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setDeleteFetch(String deleteFetch) {
		this.deleteFetch = deleteFetch;
	}

	public void setInsertOnly(String insertOnly) {
		this.insertOnly = insertOnly;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScheduleFlag(String scheduleFlag) {
		this.scheduleFlag = scheduleFlag;
	}

	public void setSourceDatabaseId(Long sourceDatabaseId) {
		this.sourceDatabaseId = sourceDatabaseId;
	}

	public void setSourceTableName(String sourceTableName) {
		this.sourceTableName = sourceTableName;
	}

	public void setSyncStatus(Integer syncStatus) {
		this.syncStatus = syncStatus;
	}

	public void setSyncTimeGreaterThanOrEqual(Date syncTimeGreaterThanOrEqual) {
		this.syncTimeGreaterThanOrEqual = syncTimeGreaterThanOrEqual;
	}

	public void setSyncTimeLessThanOrEqual(Date syncTimeLessThanOrEqual) {
		this.syncTimeLessThanOrEqual = syncTimeLessThanOrEqual;
	}

	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TableSyncQuery sourceDatabaseId(Long sourceDatabaseId) {
		if (sourceDatabaseId == null) {
			throw new RuntimeException("sourceDatabaseId is null");
		}
		this.sourceDatabaseId = sourceDatabaseId;
		return this;
	}

	public TableSyncQuery sourceTableName(String sourceTableName) {
		if (sourceTableName == null) {
			throw new RuntimeException("sourceTableName is null");
		}
		this.sourceTableName = sourceTableName;
		return this;
	}

	public TableSyncQuery syncStatus(Integer syncStatus) {
		if (syncStatus == null) {
			throw new RuntimeException("syncStatus is null");
		}
		this.syncStatus = syncStatus;
		return this;
	}

	public TableSyncQuery syncTimeGreaterThanOrEqual(Date syncTimeGreaterThanOrEqual) {
		if (syncTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("syncTime is null");
		}
		this.syncTimeGreaterThanOrEqual = syncTimeGreaterThanOrEqual;
		return this;
	}

	public TableSyncQuery syncTimeLessThanOrEqual(Date syncTimeLessThanOrEqual) {
		if (syncTimeLessThanOrEqual == null) {
			throw new RuntimeException("syncTime is null");
		}
		this.syncTimeLessThanOrEqual = syncTimeLessThanOrEqual;
		return this;
	}

	public TableSyncQuery targetTableName(String targetTableName) {
		if (targetTableName == null) {
			throw new RuntimeException("targetTableName is null");
		}
		this.targetTableName = targetTableName;
		return this;
	}

	public TableSyncQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public TableSyncQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

}