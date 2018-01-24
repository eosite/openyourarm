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
public class CombineAppDomainFactory {

	public static final String TABLENAME = "SYS_COMBINE_APP";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("title", "TITLE_");
		columnMap.put("sourceTableName", "SOURCETABLENAME_");
		columnMap.put("targetTableName", "TARGETTABLENAME_");
		columnMap.put("srcDatabaseId", "SRCDATABASEID_");
		columnMap.put("targetDatabaseIds", "TARGETDATABASEIDS_");
		columnMap.put("excludeColumns", "EXCLUDECOLUMNS_");
		columnMap.put("sqlCriteria", "SQLCRITERIA_");
		columnMap.put("type", "TYPE_");
		columnMap.put("autoSyncFlag", "AUTOSYNCFLAG_");
		columnMap.put("interval", "INTERVAL_");
		columnMap.put("active", "ACTIVE_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");
		columnMap.put("updateBy", "UPDATEBY_");
		columnMap.put("updateTime", "UPDATETIME_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("sourceTableName", "String");
		javaTypeMap.put("targetTableName", "String");
		javaTypeMap.put("srcDatabaseId", "Long");
		javaTypeMap.put("targetDatabaseIds", "String");
		javaTypeMap.put("excludeColumns", "String");
		javaTypeMap.put("sqlCriteria", "String");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("autoSyncFlag", "String");
		javaTypeMap.put("interval", "Integer");
		javaTypeMap.put("active", "String");
		javaTypeMap.put("createBy", "String");
		javaTypeMap.put("createTime", "Date");
		javaTypeMap.put("updateBy", "String");
		javaTypeMap.put("updateTime", "Date");
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
		tableDefinition.setName("CombineApp");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition title = new ColumnDefinition();
		title.setName("title");
		title.setColumnName("TITLE_");
		title.setJavaType("String");
		title.setLength(200);
		tableDefinition.addColumn(title);

		ColumnDefinition sourceTableName = new ColumnDefinition();
		sourceTableName.setName("sourceTableName");
		sourceTableName.setColumnName("SOURCETABLENAME_");
		sourceTableName.setJavaType("String");
		sourceTableName.setLength(50);
		tableDefinition.addColumn(sourceTableName);

		ColumnDefinition targetTableName = new ColumnDefinition();
		targetTableName.setName("targetTableName");
		targetTableName.setColumnName("TARGETTABLENAME_");
		targetTableName.setJavaType("String");
		targetTableName.setLength(50);
		tableDefinition.addColumn(targetTableName);

		ColumnDefinition srcDatabaseId = new ColumnDefinition();
		srcDatabaseId.setName("srcDatabaseId");
		srcDatabaseId.setColumnName("SRCDATABASEID_");
		srcDatabaseId.setJavaType("Long");
		tableDefinition.addColumn(srcDatabaseId);

		ColumnDefinition targetDatabaseIds = new ColumnDefinition();
		targetDatabaseIds.setName("targetDatabaseIds");
		targetDatabaseIds.setColumnName("TARGETDATABASEIDS_");
		targetDatabaseIds.setJavaType("String");
		targetDatabaseIds.setLength(2000);
		tableDefinition.addColumn(targetDatabaseIds);

		ColumnDefinition excludeColumns = new ColumnDefinition();
		excludeColumns.setName("excludeColumns");
		excludeColumns.setColumnName("EXCLUDECOLUMNS_");
		excludeColumns.setJavaType("String");
		excludeColumns.setLength(4000);
		tableDefinition.addColumn(excludeColumns);

		ColumnDefinition sqlCriteria = new ColumnDefinition();
		sqlCriteria.setName("sqlCriteria");
		sqlCriteria.setColumnName("SQLCRITERIA_");
		sqlCriteria.setJavaType("Clob");
		sqlCriteria.setLength(4000);
		tableDefinition.addColumn(sqlCriteria);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE_");
		type.setJavaType("String");
		type.setLength(50);
		tableDefinition.addColumn(type);

		ColumnDefinition autoSyncFlag = new ColumnDefinition();
		autoSyncFlag.setName("autoSyncFlag");
		autoSyncFlag.setColumnName("AUTOSYNCFLAG_");
		autoSyncFlag.setJavaType("String");
		autoSyncFlag.setLength(50);
		tableDefinition.addColumn(autoSyncFlag);

		ColumnDefinition interval = new ColumnDefinition();
		interval.setName("interval");
		interval.setColumnName("INTERVAL_");
		interval.setJavaType("Integer");
		tableDefinition.addColumn(interval);

		ColumnDefinition active = new ColumnDefinition();
		active.setName("active");
		active.setColumnName("ACTIVE_");
		active.setJavaType("String");
		active.setLength(1);
		tableDefinition.addColumn(active);

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

		ColumnDefinition updateBy = new ColumnDefinition();
		updateBy.setName("updateBy");
		updateBy.setColumnName("UPDATEBY_");
		updateBy.setJavaType("String");
		updateBy.setLength(50);
		tableDefinition.addColumn(updateBy);

		ColumnDefinition updateTime = new ColumnDefinition();
		updateTime.setName("updateTime");
		updateTime.setColumnName("UPDATETIME_");
		updateTime.setJavaType("Date");
		tableDefinition.addColumn(updateTime);

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

	private CombineAppDomainFactory() {

	}

}
