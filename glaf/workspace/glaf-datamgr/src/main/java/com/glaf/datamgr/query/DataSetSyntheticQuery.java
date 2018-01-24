package com.glaf.datamgr.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DataSetSyntheticQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String name;
	protected String titleLike;
	protected String type;
	protected Integer syncStatus;
	protected Date syncTimeGreaterThanOrEqual;
	protected Date syncTimeLessThanOrEqual;
	protected Long sourceDatabaseIds;
	protected String sourceDataSetId;
	protected String targetTableName;
	protected String scheduleFlag;
	protected String createTableFlag;
	protected String deleteFetch;
	protected String insertOnly;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;

	public DataSetSyntheticQuery() {

	}

	public DataSetSyntheticQuery createTableFlag(String createTableFlag) {
		if (createTableFlag == null) {
			throw new RuntimeException("createTableFlag is null");
		}
		this.createTableFlag = createTableFlag;
		return this;
	}

	public DataSetSyntheticQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public DataSetSyntheticQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public DataSetSyntheticQuery deleteFetch(String deleteFetch) {
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

			if ("sourceDatabaseIds".equals(sortColumn)) {
				orderBy = "E.SOURCEDATABASEIDS_" + a_x;
			}

			if ("sourceDataSetId".equals(sortColumn)) {
				orderBy = "E.SOURCEDATASETID_" + a_x;
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

			if ("targetDatabaseId".equals(sortColumn)) {
				orderBy = "E.TARGETDATABASEID_" + a_x;
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

	public Long getSourceDatabaseIds() {
		return sourceDatabaseIds;
	}

	public String getSourceDataSetId() {
		return sourceDataSetId;
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
		addColumn("sourceDatabaseIds", "SOURCEDATABASEIDS_");
		addColumn("sourceDataSetId", "SOURCEDATASETID_");
		addColumn("primaryKey", "PRIMARYKEY_");
		addColumn("aggregationKeys", "AGGREGATIONKEYS_");
		addColumn("targetTableName", "TARGETTABLENAME_");
		addColumn("targetDatabaseId", "TARGETDATABASEID_");
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

	public DataSetSyntheticQuery insertOnly(String insertOnly) {
		if (insertOnly == null) {
			throw new RuntimeException("insertOnly is null");
		}
		this.insertOnly = insertOnly;
		return this;
	}

	public DataSetSyntheticQuery locked(Integer locked) {
		if (locked == null) {
			throw new RuntimeException("locked is null");
		}
		this.locked = locked;
		return this;
	}

	public DataSetSyntheticQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public DataSetSyntheticQuery scheduleFlag(String scheduleFlag) {
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

	public void setSourceDatabaseIds(Long sourceDatabaseIds) {
		this.sourceDatabaseIds = sourceDatabaseIds;
	}

	public void setSourceDataSetId(String sourceDataSetId) {
		this.sourceDataSetId = sourceDataSetId;
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

	public DataSetSyntheticQuery sourceDatabaseIds(Long sourceDatabaseIds) {
		if (sourceDatabaseIds == null) {
			throw new RuntimeException("sourceDatabaseIds is null");
		}
		this.sourceDatabaseIds = sourceDatabaseIds;
		return this;
	}

	public DataSetSyntheticQuery sourceDataSetId(String sourceDataSetId) {
		if (sourceDataSetId == null) {
			throw new RuntimeException("sourceDataSetId is null");
		}
		this.sourceDataSetId = sourceDataSetId;
		return this;
	}

	public DataSetSyntheticQuery syncStatus(Integer syncStatus) {
		if (syncStatus == null) {
			throw new RuntimeException("syncStatus is null");
		}
		this.syncStatus = syncStatus;
		return this;
	}

	public DataSetSyntheticQuery syncTimeGreaterThanOrEqual(Date syncTimeGreaterThanOrEqual) {
		if (syncTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("syncTime is null");
		}
		this.syncTimeGreaterThanOrEqual = syncTimeGreaterThanOrEqual;
		return this;
	}

	public DataSetSyntheticQuery syncTimeLessThanOrEqual(Date syncTimeLessThanOrEqual) {
		if (syncTimeLessThanOrEqual == null) {
			throw new RuntimeException("syncTime is null");
		}
		this.syncTimeLessThanOrEqual = syncTimeLessThanOrEqual;
		return this;
	}

	public DataSetSyntheticQuery targetTableName(String targetTableName) {
		if (targetTableName == null) {
			throw new RuntimeException("targetTableName is null");
		}
		this.targetTableName = targetTableName;
		return this;
	}

	public DataSetSyntheticQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public DataSetSyntheticQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

}