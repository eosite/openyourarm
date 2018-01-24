package com.glaf.datamgr.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DataSetExportQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String name;
	protected String nameLike;
	protected String titleLike;
	protected String type;
	protected String typeLike;
	protected String exportDBName;

	protected Integer lastExportStatus;

	protected Date lastExportTimeGreaterThanOrEqual;
	protected Date lastExportTimeLessThanOrEqual;
	protected String serviceKey;

	protected String userId;

	protected List<String> userIds;

	protected String scheduleFlag;

	protected String deleteFetch;

	protected String publicFlag;

	protected String createTableFlag;

	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;

	public DataSetExportQuery() {

	}

	public DataSetExportQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public DataSetExportQuery createTableFlag(String createTableFlag) {
		if (createTableFlag == null) {
			throw new RuntimeException("createTableFlag is null");
		}
		this.createTableFlag = createTableFlag;
		return this;
	}

	public DataSetExportQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public DataSetExportQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public DataSetExportQuery deleteFetch(String deleteFetch) {
		if (deleteFetch == null) {
			throw new RuntimeException("deleteFetch is null");
		}
		this.deleteFetch = deleteFetch;
		return this;
	}

	public DataSetExportQuery exportDBName(String exportDBName) {
		if (exportDBName == null) {
			throw new RuntimeException("exportDBName is null");
		}
		this.exportDBName = exportDBName;
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

	public String getExportDBName() {
		return exportDBName;
	}

	public Integer getLastExportStatus() {
		return lastExportStatus;
	}

	public Date getLastExportTimeGreaterThanOrEqual() {
		return lastExportTimeGreaterThanOrEqual;
	}

	public Date getLastExportTimeLessThanOrEqual() {
		return lastExportTimeLessThanOrEqual;
	}

	public String getName() {
		return name;
	}

	public String getNameLike() {
		if (nameLike != null && nameLike.trim().length() > 0) {
			if (!nameLike.startsWith("%")) {
				nameLike = "%" + nameLike;
			}
			if (!nameLike.endsWith("%")) {
				nameLike = nameLike + "%";
			}
		}
		return nameLike;
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

			if ("exportDBName".equals(sortColumn)) {
				orderBy = "E.EXPORTDBNAME_" + a_x;
			}

			if ("lastExportStatus".equals(sortColumn)) {
				orderBy = "E.LASTEXPORTSTATUS_" + a_x;
			}

			if ("lastExportTime".equals(sortColumn)) {
				orderBy = "E.LASTEXPORTTIME_" + a_x;
			}

			if ("serviceKey".equals(sortColumn)) {
				orderBy = "E.SERVICEKEY_" + a_x;
			}

			if ("userId".equals(sortColumn)) {
				orderBy = "E.USERID_" + a_x;
			}

			if ("datasetIds".equals(sortColumn)) {
				orderBy = "E.DATASETIDS_" + a_x;
			}

			if ("scheduleFlag".equals(sortColumn)) {
				orderBy = "E.SCHEDULEFLAG_" + a_x;
			}

			if ("deleteFetch".equals(sortColumn)) {
				orderBy = "E.DELETEFETCH_" + a_x;
			}

			if ("publicFlag".equals(sortColumn)) {
				orderBy = "E.PUBLICFLAG_" + a_x;
			}

			if ("createTableFlag".equals(sortColumn)) {
				orderBy = "E.CREATETABLEFLAG_" + a_x;
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

	public String getPublicFlag() {
		return publicFlag;
	}

	public String getScheduleFlag() {
		return scheduleFlag;
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

	public String getTypeLike() {
		if (typeLike != null && typeLike.trim().length() > 0) {
			if (!typeLike.startsWith("%")) {
				typeLike = "%" + typeLike;
			}
			if (!typeLike.endsWith("%")) {
				typeLike = typeLike + "%";
			}
		}
		return typeLike;
	}

	public String getUserId() {
		return userId;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("name", "NAME_");
		addColumn("title", "TITLE_");
		addColumn("type", "TYPE_");
		addColumn("exportDBName", "EXPORTDBNAME_");
		addColumn("lastExportStatus", "LASTEXPORTSTATUS_");
		addColumn("lastExportTime", "LASTEXPORTTIME_");
		addColumn("serviceKey", "SERVICEKEY_");
		addColumn("userId", "USERID_");
		addColumn("datasetIds", "DATASETIDS_");
		addColumn("scheduleFlag", "SCHEDULEFLAG_");
		addColumn("deleteFetch", "DELETEFETCH_");
		addColumn("publicFlag", "PUBLICFLAG_");
		addColumn("createTableFlag", "CREATETABLEFLAG_");
		addColumn("locked", "LOCKED_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateTime", "UPDATETIME_");
	}

	public DataSetExportQuery lastExportStatus(Integer lastExportStatus) {
		if (lastExportStatus == null) {
			throw new RuntimeException("lastExportStatus is null");
		}
		this.lastExportStatus = lastExportStatus;
		return this;
	}

	public DataSetExportQuery lastExportTimeGreaterThanOrEqual(Date lastExportTimeGreaterThanOrEqual) {
		if (lastExportTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("lastExportTime is null");
		}
		this.lastExportTimeGreaterThanOrEqual = lastExportTimeGreaterThanOrEqual;
		return this;
	}

	public DataSetExportQuery lastExportTimeLessThanOrEqual(Date lastExportTimeLessThanOrEqual) {
		if (lastExportTimeLessThanOrEqual == null) {
			throw new RuntimeException("lastExportTime is null");
		}
		this.lastExportTimeLessThanOrEqual = lastExportTimeLessThanOrEqual;
		return this;
	}

	public DataSetExportQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public DataSetExportQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public DataSetExportQuery publicFlag(String publicFlag) {
		if (publicFlag == null) {
			throw new RuntimeException("publicFlag is null");
		}
		this.publicFlag = publicFlag;
		return this;
	}

	public DataSetExportQuery scheduleFlag(String scheduleFlag) {
		if (scheduleFlag == null) {
			throw new RuntimeException("scheduleFlag is null");
		}
		this.scheduleFlag = scheduleFlag;
		return this;
	}

	public DataSetExportQuery serviceKey(String serviceKey) {
		if (serviceKey == null) {
			throw new RuntimeException("serviceKey is null");
		}
		this.serviceKey = serviceKey;
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

	public void setExportDBName(String exportDBName) {
		this.exportDBName = exportDBName;
	}

	public void setLastExportStatus(Integer lastExportStatus) {
		this.lastExportStatus = lastExportStatus;
	}

	public void setLastExportTimeGreaterThanOrEqual(Date lastExportTimeGreaterThanOrEqual) {
		this.lastExportTimeGreaterThanOrEqual = lastExportTimeGreaterThanOrEqual;
	}

	public void setLastExportTimeLessThanOrEqual(Date lastExportTimeLessThanOrEqual) {
		this.lastExportTimeLessThanOrEqual = lastExportTimeLessThanOrEqual;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setPublicFlag(String publicFlag) {
		this.publicFlag = publicFlag;
	}

	public void setScheduleFlag(String scheduleFlag) {
		this.scheduleFlag = scheduleFlag;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTypeLike(String typeLike) {
		this.typeLike = typeLike;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public DataSetExportQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public DataSetExportQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public DataSetExportQuery typeLike(String typeLike) {
		if (typeLike == null) {
			throw new RuntimeException("type is null");
		}
		this.typeLike = typeLike;
		return this;
	}

	public DataSetExportQuery userId(String userId) {
		if (userId == null) {
			throw new RuntimeException("userId is null");
		}
		this.userId = userId;
		return this;
	}

	public DataSetExportQuery userIds(List<String> userIds) {
		if (userIds == null) {
			throw new RuntimeException("userIds is empty ");
		}
		this.userIds = userIds;
		return this;
	}

}