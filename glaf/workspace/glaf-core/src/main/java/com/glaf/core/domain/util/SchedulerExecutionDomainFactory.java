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

package com.glaf.core.domain.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.base.TableModel;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;

/**
 * 
 * 实体数据工厂类
 *
 */
public class SchedulerExecutionDomainFactory {

	public static final String TABLENAME = "SYS_SCHEDULER_EXECUTION";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	protected static TableModel tableModel;

	static {
		columnMap.put("id", "ID_");
		columnMap.put("schedulerId", "SCHEDULERID_");
		columnMap.put("businessKey", "BUSINESSKEY_");
		columnMap.put("count", "COUNT_");
		columnMap.put("value", "VALUE_");
		columnMap.put("runYear", "RUNYEAR_");
		columnMap.put("runMonth", "RUNMONTH_");
		columnMap.put("runWeek", "RUNWEEK_");
		columnMap.put("runQuarter", "RUNQUARTER_");
		columnMap.put("runDay", "RUNDAY_");
		columnMap.put("runTime", "RUNTIME_");
		columnMap.put("jobNo", "JOBNO_");
		columnMap.put("status", "STATUS_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("schedulerId", "String");
		javaTypeMap.put("businessKey", "String");
		javaTypeMap.put("count", "Integer");
		javaTypeMap.put("value", "Double");
		javaTypeMap.put("runYear", "Integer");
		javaTypeMap.put("runMonth", "Integer");
		javaTypeMap.put("runWeek", "Integer");
		javaTypeMap.put("runQuarter", "Integer");
		javaTypeMap.put("runDay", "Integer");
		javaTypeMap.put("runTime", "Integer");
		javaTypeMap.put("jobNo", "String");
		javaTypeMap.put("status", "Integer");
		javaTypeMap.put("createBy", "String");
		javaTypeMap.put("createTime", "Date");
	}

	public static TableDefinition createTable() {
		TableDefinition tableDefinition = getTableDefinition(TABLENAME);
		if (!DBUtils.tableExists(TABLENAME)) {
			DBUtils.createTable(tableDefinition);
		} else {
			DBUtils.alterTable(tableDefinition);
		}
		return tableDefinition;
	}

	public static TableDefinition createTable(String tableName) {
		TableDefinition tableDefinition = getTableDefinition(tableName);
		if (!DBUtils.tableExists(tableName)) {
			DBUtils.createTable(tableDefinition);
		} else {
			DBUtils.alterTable(tableDefinition);
		}
		return tableDefinition;
	}

	public static Map<String, String> getColumnMap() {
		return columnMap;
	}

	public static Map<String, String> getJavaTypeMap() {
		return javaTypeMap;
	}

	public static TableDefinition getTableDefinition() {
		return getTableDefinition(TABLENAME);
	}

	public static TableDefinition getTableDefinition(String tableName) {
		tableName = tableName.toUpperCase();
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setTableName(tableName);
		tableDefinition.setName("SchedulerExecution");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition schedulerId = new ColumnDefinition();
		schedulerId.setName("schedulerId");
		schedulerId.setColumnName("SCHEDULERID_");
		schedulerId.setJavaType("String");
		schedulerId.setLength(0);
		tableDefinition.addColumn(schedulerId);

		ColumnDefinition businessKey = new ColumnDefinition();
		businessKey.setName("businessKey");
		businessKey.setColumnName("BUSINESSKEY_");
		businessKey.setJavaType("String");
		businessKey.setLength(0);
		tableDefinition.addColumn(businessKey);

		ColumnDefinition count = new ColumnDefinition();
		count.setName("count");
		count.setColumnName("COUNT_");
		count.setJavaType("Integer");
		tableDefinition.addColumn(count);

		ColumnDefinition value = new ColumnDefinition();
		value.setName("value");
		value.setColumnName("VALUE_");
		value.setJavaType("Double");
		tableDefinition.addColumn(value);

		ColumnDefinition runYear = new ColumnDefinition();
		runYear.setName("runYear");
		runYear.setColumnName("RUNYEAR_");
		runYear.setJavaType("Integer");
		tableDefinition.addColumn(runYear);

		ColumnDefinition runMonth = new ColumnDefinition();
		runMonth.setName("runMonth");
		runMonth.setColumnName("RUNMONTH_");
		runMonth.setJavaType("Integer");
		tableDefinition.addColumn(runMonth);

		ColumnDefinition runWeek = new ColumnDefinition();
		runWeek.setName("runWeek");
		runWeek.setColumnName("RUNWEEK_");
		runWeek.setJavaType("Integer");
		tableDefinition.addColumn(runWeek);

		ColumnDefinition runQuarter = new ColumnDefinition();
		runQuarter.setName("runQuarter");
		runQuarter.setColumnName("RUNQUARTER_");
		runQuarter.setJavaType("Integer");
		tableDefinition.addColumn(runQuarter);

		ColumnDefinition runDay = new ColumnDefinition();
		runDay.setName("runDay");
		runDay.setColumnName("RUNDAY_");
		runDay.setJavaType("Integer");
		tableDefinition.addColumn(runDay);

		ColumnDefinition runTime = new ColumnDefinition();
		runTime.setName("runTime");
		runTime.setColumnName("RUNTIME_");
		runTime.setJavaType("Integer");
		tableDefinition.addColumn(runTime);

		ColumnDefinition jobNo = new ColumnDefinition();
		jobNo.setName("jobNo");
		jobNo.setColumnName("JOBNO_");
		jobNo.setJavaType("String");
		jobNo.setLength(50);
		tableDefinition.addColumn(jobNo);

		ColumnDefinition status = new ColumnDefinition();
		status.setName("status");
		status.setColumnName("STATUS_");
		status.setJavaType("Integer");
		tableDefinition.addColumn(status);

		ColumnDefinition createBy = new ColumnDefinition();
		createBy.setName("createBy");
		createBy.setColumnName("CREATEBY_");
		createBy.setJavaType("String");
		createBy.setLength(50);
		tableDefinition.addColumn(createBy);

		ColumnDefinition createTime = new ColumnDefinition();
		createTime.setName("createTime");
		createTime.setColumnName("CREATETIME_");
		createTime.setJavaType("Date");
		tableDefinition.addColumn(createTime);

		return tableDefinition;
	}

	public static void processDataRequest(DataRequest dataRequest) {
		if (dataRequest != null) {
			if (dataRequest.getFilter() != null) {
				if (dataRequest.getFilter().getField() != null) {
					dataRequest.getFilter().setColumn(
							columnMap.get(dataRequest.getFilter().getField()));
					dataRequest.getFilter()
							.setJavaType(
									javaTypeMap.get(dataRequest.getFilter()
											.getField()));
				}

				List<FilterDescriptor> filters = dataRequest.getFilter()
						.getFilters();
				for (FilterDescriptor filter : filters) {
					filter.setParent(dataRequest.getFilter());
					if (filter.getField() != null) {
						filter.setColumn(columnMap.get(filter.getField()));
						filter.setJavaType(javaTypeMap.get(filter.getField()));
					}

					List<FilterDescriptor> subFilters = filter.getFilters();
					for (FilterDescriptor f : subFilters) {
						f.setColumn(columnMap.get(f.getField()));
						f.setJavaType(javaTypeMap.get(f.getField()));
						f.setParent(filter);
					}
				}
			}
		}
	}

	private SchedulerExecutionDomainFactory() {

	}

}
