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

package com.glaf.datamgr.query;

import java.util.*;

import com.glaf.core.query.DataQuery;

public class SqlResultQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Long id;
	protected String suffix;
	protected Long projectId;
	protected Long sqlDefId;
	protected List<Long> sqlDefIds;
	protected Long databaseId;
	protected List<Long> databaseIds;
	protected String jobNo;
	protected String operation;
	protected String type;
	protected Integer runYear;
	protected Integer runMonth;
	protected Integer runWeek;
	protected Integer runQuarter;
	protected Integer runDay;
	protected Integer runDayGreaterThanOrEqual;
	protected Integer runDayLessThanOrEqual;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;

	public SqlResultQuery() {

	}

	public SqlResultQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public SqlResultQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public SqlResultQuery databaseId(Long databaseId) {
		if (databaseId == null) {
			throw new RuntimeException("databaseId is null");
		}
		this.databaseId = databaseId;
		return this;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public Long getDatabaseId() {
		return databaseId;
	}

	public List<Long> getDatabaseIds() {
		return databaseIds;
	}

	public Integer getDateGreaterThanOrEqual() {
		return runDayGreaterThanOrEqual;
	}

	public Integer getDateLessThanOrEqual() {
		return runDayLessThanOrEqual;
	}

	public Long getId() {
		return id;
	}

	public String getJobNo() {
		return jobNo;
	}

	public String getOperation() {
		return operation;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("sqlDefId".equals(sortColumn)) {
				orderBy = "E.SQLDEFID_" + a_x;
			}

			if ("count".equals(sortColumn)) {
				orderBy = "E.COUNT_" + a_x;
			}

			if ("runDay".equals(sortColumn)) {
				orderBy = "E.RUNDAY_" + a_x;
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

	public Long getProjectId() {
		return projectId;
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

	public Integer getRunMonth() {
		return runMonth;
	}

	public Integer getRunQuarter() {
		return runQuarter;
	}

	public Integer getRunWeek() {
		return runWeek;
	}

	public Integer getRunYear() {
		return runYear;
	}

	public Long getSqlDefId() {
		return sqlDefId;
	}

	public List<Long> getSqlDefIds() {
		return sqlDefIds;
	}

	public String getSuffix() {
		if (suffix == null) {
			suffix = "";
		}
		return suffix;
	}

	public String getType() {
		return type;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("sqlDefId", "SQLDEFID_");
		addColumn("count", "COUNT_");
		addColumn("runDay", "RUNDAY_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
	}

	public SqlResultQuery jobNo(String jobNo) {
		if (jobNo == null) {
			throw new RuntimeException("jobNo is null");
		}
		this.jobNo = jobNo;
		return this;
	}

	public SqlResultQuery operation(String operation) {
		if (operation == null) {
			throw new RuntimeException("operation is null");
		}
		this.operation = operation;
		return this;
	}

	public SqlResultQuery runDayGreaterThanOrEqual(Integer runDayGreaterThanOrEqual) {
		if (runDayGreaterThanOrEqual == null) {
			throw new RuntimeException("runDay is null");
		}
		this.runDayGreaterThanOrEqual = runDayGreaterThanOrEqual;
		return this;
	}

	public SqlResultQuery runDayLessThanOrEqual(Integer runDayLessThanOrEqual) {
		if (runDayLessThanOrEqual == null) {
			throw new RuntimeException("runDay is null");
		}
		this.runDayLessThanOrEqual = runDayLessThanOrEqual;
		return this;
	}

	public void setCreateTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public void setDatabaseIds(List<Long> databaseIds) {
		this.databaseIds = databaseIds;
	}

	public void setDateGreaterThanOrEqual(Integer runDayGreaterThanOrEqual) {
		this.runDayGreaterThanOrEqual = runDayGreaterThanOrEqual;
	}

	public void setDateLessThanOrEqual(Integer runDayLessThanOrEqual) {
		this.runDayLessThanOrEqual = runDayLessThanOrEqual;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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

	public void setRunMonth(Integer runMonth) {
		this.runMonth = runMonth;
	}

	public void setRunQuarter(Integer runQuarter) {
		this.runQuarter = runQuarter;
	}

	public void setRunWeek(Integer runWeek) {
		this.runWeek = runWeek;
	}

	public void setRunYear(Integer runYear) {
		this.runYear = runYear;
	}

	public void setSqlDefId(Long sqlDefId) {
		this.sqlDefId = sqlDefId;
	}

	public void setSqlDefIds(List<Long> sqlDefIds) {
		this.sqlDefIds = sqlDefIds;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public void setType(String type) {
		this.type = type;
	}

	public SqlResultQuery sqlDefId(Long sqlDefId) {
		if (sqlDefId == null) {
			throw new RuntimeException("sqlDefId is null");
		}
		this.sqlDefId = sqlDefId;
		return this;
	}

	public SqlResultQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

}