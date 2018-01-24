package com.glaf.datamgr.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class TaskTableQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String title;
	protected String titleLike;
	protected String type;
	protected String tableName;
	protected String tableNameLike;
	protected Date startTimeGreaterThanOrEqual;
	protected Date startTimeLessThanOrEqual;
	protected Date endTimeGreaterThanOrEqual;
	protected Date endTimeLessThanOrEqual;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;
	protected Date updateTimeGreaterThanOrEqual;
	protected Date updateTimeLessThanOrEqual;

	public TaskTableQuery() {

	}

	public String getTitle() {
		return title;
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

	public String getTableName() {
		return tableName;
	}

	public String getTableNameLike() {
		if (tableNameLike != null && tableNameLike.trim().length() > 0) {
			if (!tableNameLike.startsWith("%")) {
				tableNameLike = "%" + tableNameLike;
			}
			if (!tableNameLike.endsWith("%")) {
				tableNameLike = tableNameLike + "%";
			}
		}
		return tableNameLike;
	}

	public Date getStartTimeGreaterThanOrEqual() {
		return startTimeGreaterThanOrEqual;
	}

	public Date getStartTimeLessThanOrEqual() {
		return startTimeLessThanOrEqual;
	}

	public Date getEndTimeGreaterThanOrEqual() {
		return endTimeGreaterThanOrEqual;
	}

	public Date getEndTimeLessThanOrEqual() {
		return endTimeLessThanOrEqual;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public Date getUpdateTimeGreaterThanOrEqual() {
		return updateTimeGreaterThanOrEqual;
	}

	public Date getUpdateTimeLessThanOrEqual() {
		return updateTimeLessThanOrEqual;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTableNameLike(String tableNameLike) {
		this.tableNameLike = tableNameLike;
	}

	public void setStartTimeGreaterThanOrEqual(Date startTimeGreaterThanOrEqual) {
		this.startTimeGreaterThanOrEqual = startTimeGreaterThanOrEqual;
	}

	public void setStartTimeLessThanOrEqual(Date startTimeLessThanOrEqual) {
		this.startTimeLessThanOrEqual = startTimeLessThanOrEqual;
	}

	public void setEndTimeGreaterThanOrEqual(Date endTimeGreaterThanOrEqual) {
		this.endTimeGreaterThanOrEqual = endTimeGreaterThanOrEqual;
	}

	public void setEndTimeLessThanOrEqual(Date endTimeLessThanOrEqual) {
		this.endTimeLessThanOrEqual = endTimeLessThanOrEqual;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setCreateTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setUpdateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual) {
		this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
	}

	public void setUpdateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual) {
		this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
	}

	public TaskTableQuery title(String title) {
		if (title == null) {
			throw new RuntimeException("title is null");
		}
		this.title = title;
		return this;
	}

	public TaskTableQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public TaskTableQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public TaskTableQuery tableName(String tableName) {
		if (tableName == null) {
			throw new RuntimeException("tableName is null");
		}
		this.tableName = tableName;
		return this;
	}

	public TaskTableQuery tableNameLike(String tableNameLike) {
		if (tableNameLike == null) {
			throw new RuntimeException("tableName is null");
		}
		this.tableNameLike = tableNameLike;
		return this;
	}

	public TaskTableQuery startTimeGreaterThanOrEqual(Date startTimeGreaterThanOrEqual) {
		if (startTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("startTime is null");
		}
		this.startTimeGreaterThanOrEqual = startTimeGreaterThanOrEqual;
		return this;
	}

	public TaskTableQuery startTimeLessThanOrEqual(Date startTimeLessThanOrEqual) {
		if (startTimeLessThanOrEqual == null) {
			throw new RuntimeException("startTime is null");
		}
		this.startTimeLessThanOrEqual = startTimeLessThanOrEqual;
		return this;
	}

	public TaskTableQuery endTimeGreaterThanOrEqual(Date endTimeGreaterThanOrEqual) {
		if (endTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("endTime is null");
		}
		this.endTimeGreaterThanOrEqual = endTimeGreaterThanOrEqual;
		return this;
	}

	public TaskTableQuery endTimeLessThanOrEqual(Date endTimeLessThanOrEqual) {
		if (endTimeLessThanOrEqual == null) {
			throw new RuntimeException("endTime is null");
		}
		this.endTimeLessThanOrEqual = endTimeLessThanOrEqual;
		return this;
	}

	public TaskTableQuery deleteFlag(Integer deleteFlag) {
		if (deleteFlag == null) {
			throw new RuntimeException("deleteFlag is null");
		}
		this.deleteFlag = deleteFlag;
		return this;
	}

	public TaskTableQuery locked(Integer locked) {
		if (locked == null) {
			throw new RuntimeException("locked is null");
		}
		this.locked = locked;
		return this;
	}

	public TaskTableQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public TaskTableQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public TaskTableQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual) {
		if (updateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("updateTime is null");
		}
		this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
		return this;
	}

	public TaskTableQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual) {
		if (updateTimeLessThanOrEqual == null) {
			throw new RuntimeException("updateTime is null");
		}
		this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("tableName".equals(sortColumn)) {
				orderBy = "E.TABLENAME_" + a_x;
			}

			if ("idColumn".equals(sortColumn)) {
				orderBy = "E.IDCOLUMN_" + a_x;
			}

			if ("nameColumn".equals(sortColumn)) {
				orderBy = "E.NAMECOLUMN_" + a_x;
			}

			if ("nameExpression".equals(sortColumn)) {
				orderBy = "E.NAMEEXPRESSION_" + a_x;
			}

			if ("typeColumn".equals(sortColumn)) {
				orderBy = "E.TYPECOLUMN_" + a_x;
			}

			if ("startDateColumn".equals(sortColumn)) {
				orderBy = "E.STARTDATECOLUMN_" + a_x;
			}

			if ("endDateColumn".equals(sortColumn)) {
				orderBy = "E.ENDDATECOLUMN_" + a_x;
			}

			if ("startTime".equals(sortColumn)) {
				orderBy = "E.STARTTIME_" + a_x;
			}

			if ("endTime".equals(sortColumn)) {
				orderBy = "E.ENDTIME_" + a_x;
			}

			if ("frequency".equals(sortColumn)) {
				orderBy = "E.FREQUENCY_" + a_x;
			}

			if ("sortNo".equals(sortColumn)) {
				orderBy = "E.SORTNO_" + a_x;
			}

			if ("deleteFlag".equals(sortColumn)) {
				orderBy = "E.DELETEFLAG_" + a_x;
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

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("title", "TITLE_");
		addColumn("type", "TYPE_");
		addColumn("tableName", "TABLENAME_");
		addColumn("idColumn", "IDCOLUMN_");
		addColumn("nameColumn", "NAMECOLUMN_");
		addColumn("nameExpression", "NAMEEXPRESSION_");
		addColumn("typeColumn", "TYPECOLUMN_");
		addColumn("startDateColumn", "STARTDATECOLUMN_");
		addColumn("endDateColumn", "ENDDATECOLUMN_");
		addColumn("startTime", "STARTTIME_");
		addColumn("endTime", "ENDTIME_");
		addColumn("frequency", "FREQUENCY_");
		addColumn("sortNo", "SORTNO_");
		addColumn("deleteFlag", "DELETEFLAG_");
		addColumn("locked", "LOCKED_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateTime", "UPDATETIME_");
	}

}