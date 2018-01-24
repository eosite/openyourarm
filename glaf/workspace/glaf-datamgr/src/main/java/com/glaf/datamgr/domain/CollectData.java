package com.glaf.datamgr.domain;

import java.util.Date;

public class CollectData implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected long id;

	protected long databaseId;

	protected String title;

	protected String code;

	protected String name;

	protected String projectName;

	protected int count;

	protected long sqlDefId;

	protected int runYear;

	protected int runMonth;

	protected int runWeek;

	protected int runQuarter;

	protected int runDay;

	protected String createBy;

	protected Date createTime;

	public CollectData() {

	}

	public String getCode() {
		return code;
	}

	public int getCount() {
		return count;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public long getDatabaseId() {
		return databaseId;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getProjectName() {
		return projectName;
	}

	public int getRunDay() {
		return runDay;
	}

	public int getRunMonth() {
		return runMonth;
	}

	public int getRunQuarter() {
		return runQuarter;
	}

	public int getRunWeek() {
		return runWeek;
	}

	public int getRunYear() {
		return runYear;
	}

	public long getSqlDefId() {
		return sqlDefId;
	}

	public String getTitle() {
		return title;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDatabaseId(long databaseId) {
		this.databaseId = databaseId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setRunDay(int runDay) {
		this.runDay = runDay;
	}

	public void setRunMonth(int runMonth) {
		this.runMonth = runMonth;
	}

	public void setRunQuarter(int runQuarter) {
		this.runQuarter = runQuarter;
	}

	public void setRunWeek(int runWeek) {
		this.runWeek = runWeek;
	}

	public void setRunYear(int runYear) {
		this.runYear = runYear;
	}

	public void setSqlDefId(long sqlDefId) {
		this.sqlDefId = sqlDefId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
