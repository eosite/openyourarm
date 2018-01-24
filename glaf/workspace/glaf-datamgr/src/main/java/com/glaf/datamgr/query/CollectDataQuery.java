package com.glaf.datamgr.query;

import java.util.Date;
import java.util.List;

import com.glaf.core.query.DataQuery;

public class CollectDataQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Long databaseId;
	protected List<Long> databaseIds;
	protected Long projectId;
	protected String code;
	protected List<String> codes;
	protected Long sqlDefId;
	protected List<Long> sqlDefIds;
	protected Integer runYear;
	protected Integer runMonth;
	protected Integer runWeek;
	protected Integer runQuarter;
	protected Integer runDay;
	protected Integer runDayGreaterThanOrEqual;
	protected Integer runDayLessThanOrEqual;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;

	public Long getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public List<Long> getDatabaseIds() {
		return databaseIds;
	}

	public void setDatabaseIds(List<Long> databaseIds) {
		this.databaseIds = databaseIds;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public Long getSqlDefId() {
		return sqlDefId;
	}

	public void setSqlDefId(Long sqlDefId) {
		this.sqlDefId = sqlDefId;
	}

	public List<Long> getSqlDefIds() {
		return sqlDefIds;
	}

	public void setSqlDefIds(List<Long> sqlDefIds) {
		this.sqlDefIds = sqlDefIds;
	}

	public Integer getRunYear() {
		return runYear;
	}

	public void setRunYear(Integer runYear) {
		this.runYear = runYear;
	}

	public Integer getRunMonth() {
		return runMonth;
	}

	public void setRunMonth(Integer runMonth) {
		this.runMonth = runMonth;
	}

	public Integer getRunWeek() {
		return runWeek;
	}

	public void setRunWeek(Integer runWeek) {
		this.runWeek = runWeek;
	}

	public Integer getRunQuarter() {
		return runQuarter;
	}

	public void setRunQuarter(Integer runQuarter) {
		this.runQuarter = runQuarter;
	}

	public Integer getRunDay() {
		return runDay;
	}

	public void setRunDay(Integer runDay) {
		this.runDay = runDay;
	}

	public Integer getRunDayGreaterThanOrEqual() {
		return runDayGreaterThanOrEqual;
	}

	public void setRunDayGreaterThanOrEqual(Integer runDayGreaterThanOrEqual) {
		this.runDayGreaterThanOrEqual = runDayGreaterThanOrEqual;
	}

	public Integer getRunDayLessThanOrEqual() {
		return runDayLessThanOrEqual;
	}

	public void setRunDayLessThanOrEqual(Integer runDayLessThanOrEqual) {
		this.runDayLessThanOrEqual = runDayLessThanOrEqual;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
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

			if ("code".equals(sortColumn)) {
				orderBy = "E.CODE_" + a_x;
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
		addColumn("name", "NAME_");
		addColumn("code", "CODE_");
		addColumn("count", "COUNT_");
		addColumn("title", "TITLE_");
		addColumn("databaseId", "DATABASEID_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
		addColumn("runDay", "RUNDAY_");
		addColumn("runYear", "RUNYEAR_");
		addColumn("runMonth", "RUNMONTH_");
		addColumn("runWeek", "RUNWEEK_");
		addColumn("runQuarter", "RUNQUARTER_");
		addColumn("sqlDefId", "SQLDEFID_");
	}

}