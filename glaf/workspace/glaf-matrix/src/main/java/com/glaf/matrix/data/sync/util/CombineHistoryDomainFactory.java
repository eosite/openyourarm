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

package com.glaf.matrix.data.sync.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;

/**
 * 
 * 实体数据工厂类
 *
 */
public class CombineHistoryDomainFactory {

	public static final String TABLENAME = "SYS_COMBINE_HISTORY";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("combineId", "COMBINEID_");
		columnMap.put("databaseId", "DATABASEID_");
		columnMap.put("databaseName", "DATABASENAME_");
		columnMap.put("jobNo", "JOBNO_");
		columnMap.put("status", "STATUS_");
		columnMap.put("total", "TOTAL_");
		columnMap.put("totalTime", "TOTALTIME_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("combineId", "Long");
		javaTypeMap.put("databaseId", "Long");
		javaTypeMap.put("databaseName", "String");
		javaTypeMap.put("jobNo", "String");
		javaTypeMap.put("status", "Integer");
		javaTypeMap.put("total", "Integer");
		javaTypeMap.put("totalTime", "Integer");
		javaTypeMap.put("createBy", "String");
		javaTypeMap.put("createTime", "Date");
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
		tableDefinition.setName("CombineHistory");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition combineId = new ColumnDefinition();
		combineId.setName("combineId");
		combineId.setColumnName("COMBINEID_");
		combineId.setJavaType("Long");
		tableDefinition.addColumn(combineId);

		ColumnDefinition databaseId = new ColumnDefinition();
		databaseId.setName("databaseId");
		databaseId.setColumnName("DATABASEID_");
		databaseId.setJavaType("Long");
		tableDefinition.addColumn(databaseId);

		ColumnDefinition databaseName = new ColumnDefinition();
		databaseName.setName("databaseName");
		databaseName.setColumnName("DATABASENAME_");
		databaseName.setJavaType("String");
		databaseName.setLength(200);
		tableDefinition.addColumn(databaseName);

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

		ColumnDefinition total = new ColumnDefinition();
		total.setName("total");
		total.setColumnName("TOTAL_");
		total.setJavaType("Integer");
		tableDefinition.addColumn(total);

		ColumnDefinition totalTime = new ColumnDefinition();
		totalTime.setName("totalTime");
		totalTime.setColumnName("TOTALTIME_");
		totalTime.setJavaType("Integer");
		tableDefinition.addColumn(totalTime);

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

	private CombineHistoryDomainFactory() {

	}

}
