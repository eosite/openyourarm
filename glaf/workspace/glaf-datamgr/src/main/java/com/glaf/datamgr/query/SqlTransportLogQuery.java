package com.glaf.datamgr.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SqlTransportLogQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
	protected Long projectId;
	protected Long databaseId;
	protected List<Long> databaseIds;
	protected Long sqlDefId;
	protected List<Long> sqlDefIds;
	protected String type;
	protected List<String> types;
	protected Integer runYear;
	protected Integer runMonth;
	protected Integer runDay;
	protected Integer runDayGreaterThanOrEqual;
	protected Integer runDayLessThanOrEqual;
	protected String jobNo;
	protected String jobNoLike;
	protected List<String> createBys;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;

	public SqlTransportLogQuery() {

	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public Long getDatabaseId() {
		return databaseId;
	}

	public List<Long> getDatabaseIds() {
		return databaseIds;
	}

	public Long getSqlDefId() {
		return sqlDefId;
	}

	public List<Long> getSqlDefIds() {
		return sqlDefIds;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getStatusGreaterThanOrEqual() {
		return statusGreaterThanOrEqual;
	}

	public Integer getStatusLessThanOrEqual() {
		return statusLessThanOrEqual;
	}

	public String getType() {
		return type;
	}

	public List<String> getTypes() {
		return types;
	}

	public Integer getRunYear() {
		return runYear;
	}

	public Integer getRunMonth() {
		return runMonth;
	}

	public Integer getRunDay() {
		return runDay;
	}

	public Integer getRunDayGreaterThanOrEqual() {
		return runDayGreaterThanOrEqual;
	}

	public Integer getRunDayLessThanOrEqual() {
		return runDayLessThanOrEqual;
	}

	public String getJobNo() {
		return jobNo;
	}

	public String getJobNoLike() {
		if (jobNoLike != null && jobNoLike.trim().length() > 0) {
			if (!jobNoLike.startsWith("%")) {
				jobNoLike = "%" + jobNoLike;
			}
			if (!jobNoLike.endsWith("%")) {
				jobNoLike = jobNoLike + "%";
			}
		}
		return jobNoLike;
	}

	public List<String> getCreateBys() {
		return createBys;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public void setDatabaseIds(List<Long> databaseIds) {
		this.databaseIds = databaseIds;
	}

	public void setSqlDefId(Long sqlDefId) {
		this.sqlDefId = sqlDefId;
	}

	public void setSqlDefIds(List<Long> sqlDefIds) {
		this.sqlDefIds = sqlDefIds;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public void setRunYear(Integer runYear) {
		this.runYear = runYear;
	}

	public void setRunMonth(Integer runMonth) {
		this.runMonth = runMonth;
	}

	public void setRunDay(Integer runDay) {
		this.runDay = runDay;
	}

	public void setRunDayGreaterThanOrEqual(Integer runDayGreaterThanOrEqual) {
		this.runDayGreaterThanOrEqual = runDayGreaterThanOrEqual;
	}

	public void setRunDayLessThanOrEqual(Integer runDayLessThanOrEqual) {
		this.runDayLessThanOrEqual = runDayLessThanOrEqual;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public void setJobNoLike(String jobNoLike) {
		this.jobNoLike = jobNoLike;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public void setCreateTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public SqlTransportLogQuery databaseId(Long databaseId) {
		if (databaseId == null) {
			throw new RuntimeException("databaseId is null");
		}
		this.databaseId = databaseId;
		return this;
	}

	public SqlTransportLogQuery databaseIds(List<Long> databaseIds) {
		if (databaseIds == null) {
			throw new RuntimeException("databaseIds is empty ");
		}
		this.databaseIds = databaseIds;
		return this;
	}

	public SqlTransportLogQuery sqlDefId(Long sqlDefId) {
		if (sqlDefId == null) {
			throw new RuntimeException("sqlDefId is null");
		}
		this.sqlDefId = sqlDefId;
		return this;
	}

	public SqlTransportLogQuery sqlDefIds(List<Long> sqlDefIds) {
		if (sqlDefIds == null) {
			throw new RuntimeException("sqlDefIds is empty ");
		}
		this.sqlDefIds = sqlDefIds;
		return this;
	}

	public SqlTransportLogQuery status(Integer status) {
		if (status == null) {
			throw new RuntimeException("status is null");
		}
		this.status = status;
		return this;
	}

	public SqlTransportLogQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public SqlTransportLogQuery types(List<String> types) {
		if (types == null) {
			throw new RuntimeException("types is empty ");
		}
		this.types = types;
		return this;
	}

	public SqlTransportLogQuery runYear(Integer runYear) {
		if (runYear == null) {
			throw new RuntimeException("runYear is null");
		}
		this.runYear = runYear;
		return this;
	}

	public SqlTransportLogQuery runMonth(Integer runMonth) {
		if (runMonth == null) {
			throw new RuntimeException("runMonth is null");
		}
		this.runMonth = runMonth;
		return this;
	}

	public SqlTransportLogQuery runDay(Integer runDay) {
		if (runDay == null) {
			throw new RuntimeException("runDay is null");
		}
		this.runDay = runDay;
		return this;
	}

	public SqlTransportLogQuery runDayGreaterThanOrEqual(Integer runDayGreaterThanOrEqual) {
		if (runDayGreaterThanOrEqual == null) {
			throw new RuntimeException("runDay is null");
		}
		this.runDayGreaterThanOrEqual = runDayGreaterThanOrEqual;
		return this;
	}

	public SqlTransportLogQuery runDayLessThanOrEqual(Integer runDayLessThanOrEqual) {
		if (runDayLessThanOrEqual == null) {
			throw new RuntimeException("runDay is null");
		}
		this.runDayLessThanOrEqual = runDayLessThanOrEqual;
		return this;
	}

	public SqlTransportLogQuery jobNo(String jobNo) {
		if (jobNo == null) {
			throw new RuntimeException("jobNo is null");
		}
		this.jobNo = jobNo;
		return this;
	}

	public SqlTransportLogQuery jobNoLike(String jobNoLike) {
		if (jobNoLike == null) {
			throw new RuntimeException("jobNo is null");
		}
		this.jobNoLike = jobNoLike;
		return this;
	}

	public SqlTransportLogQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public SqlTransportLogQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public SqlTransportLogQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public SqlTransportLogQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("databaseId".equals(sortColumn)) {
				orderBy = "E.DATABASEID_" + a_x;
			}

			if ("sqlDefId".equals(sortColumn)) {
				orderBy = "E.SQLDEFID_" + a_x;
			}

			if ("status".equals(sortColumn)) {
				orderBy = "E.STATUS_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("runYear".equals(sortColumn)) {
				orderBy = "E.RUNYEAR_" + a_x;
			}

			if ("runMonth".equals(sortColumn)) {
				orderBy = "E.RUNMONTH_" + a_x;
			}

			if ("runWeek".equals(sortColumn)) {
				orderBy = "E.RUNWEEK_" + a_x;
			}

			if ("runQuarter".equals(sortColumn)) {
				orderBy = "E.RUNQUARTER_" + a_x;
			}

			if ("runDay".equals(sortColumn)) {
				orderBy = "E.RUNDAY_" + a_x;
			}

			if ("jobNo".equals(sortColumn)) {
				orderBy = "E.JOBNO_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("createTime".equals(sortColumn)) {
				orderBy = "E.CREATETIME_" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("databaseId", "DATABASEID_");
		addColumn("sqlDefId", "SQLDEFID_");
		addColumn("status", "STATUS_");
		addColumn("type", "TYPE_");
		addColumn("runYear", "RUNYEAR_");
		addColumn("runMonth", "RUNMONTH_");
		addColumn("runWeek", "RUNWEEK_");
		addColumn("runQuarter", "RUNQUARTER_");
		addColumn("runDay", "RUNDAY_");
		addColumn("jobNo", "JOBNO_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
	}

}