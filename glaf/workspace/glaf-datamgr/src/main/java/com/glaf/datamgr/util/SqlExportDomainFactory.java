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

package com.glaf.datamgr.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;

/**
 * 
 * 实体数据工厂类
 *
 */
public class SqlExportDomainFactory {

	public static final String TABLENAME = "SYS_SQL_EXPORT";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("databaseId", "DATABASEID_");
		columnMap.put("name", "NAME_");
		columnMap.put("title", "TITLE_");
		columnMap.put("type", "TYPE_");
		columnMap.put("serviceKey", "SERVICEKEY_");
		columnMap.put("userId", "USERID_");
		columnMap.put("operation", "OPERATION_");
		columnMap.put("exportFlag", "EXPORTFLAG_");
		columnMap.put("exportDBName", "EXPORTDBNAME_");
		columnMap.put("lastExportStatus", "LASTEXPORTSTATUS_");
		columnMap.put("lastExportTime", "LASTEXPORTTIME_");
		columnMap.put("datasetIds", "DATASETIDS_");
		columnMap.put("sqlDefIds", "SQLDEFIDS_");
		columnMap.put("scheduleFlag", "SCHEDULEFLAG_");
		columnMap.put("createTableFlag", "CREATETABLEFLAG_");
		columnMap.put("deleteFetch", "DELETEFETCH_");
		columnMap.put("publicFlag", "PUBLICFLAG_");
		columnMap.put("locked", "LOCKED_");
		columnMap.put("deleteFlag", "DELETEFLAG_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");
		columnMap.put("updateBy", "UPDATEBY_");
		columnMap.put("updateTime", "UPDATETIME_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("databaseId", "Long");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("serviceKey", "String");
		javaTypeMap.put("userId", "String");
		javaTypeMap.put("operation", "String");
		javaTypeMap.put("exportFlag", "String");
		javaTypeMap.put("exportDBName", "String");
		javaTypeMap.put("lastExportStatus", "Integer");
		javaTypeMap.put("lastExportTime", "Date");
		javaTypeMap.put("datasetIds", "String");
		javaTypeMap.put("sqlDefIds", "String");
		javaTypeMap.put("scheduleFlag", "String");
		javaTypeMap.put("createTableFlag", "String");
		javaTypeMap.put("deleteFetch", "String");
		javaTypeMap.put("publicFlag", "String");
		javaTypeMap.put("locked", "Integer");
		javaTypeMap.put("deleteFlag", "Integer");
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
		tableDefinition.setName("SqlExport");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition databaseId = new ColumnDefinition();
		databaseId.setName("databaseId");
		databaseId.setColumnName("DATABASEID_");
		databaseId.setJavaType("Long");
		tableDefinition.addColumn(databaseId);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME_");
		name.setJavaType("String");
		name.setLength(50);
		tableDefinition.addColumn(name);

		ColumnDefinition title = new ColumnDefinition();
		title.setName("title");
		title.setColumnName("TITLE_");
		title.setJavaType("String");
		title.setLength(200);
		tableDefinition.addColumn(title);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE_");
		type.setJavaType("String");
		type.setLength(20);
		tableDefinition.addColumn(type);

		ColumnDefinition serviceKey = new ColumnDefinition();
		serviceKey.setName("serviceKey");
		serviceKey.setColumnName("SERVICEKEY_");
		serviceKey.setJavaType("String");
		serviceKey.setLength(50);
		tableDefinition.addColumn(serviceKey);

		ColumnDefinition userId = new ColumnDefinition();
		userId.setName("userId");
		userId.setColumnName("USERID_");
		userId.setJavaType("String");
		userId.setLength(50);
		tableDefinition.addColumn(userId);

		ColumnDefinition operation = new ColumnDefinition();
		operation.setName("operation");
		operation.setColumnName("OPERATION_");
		operation.setJavaType("String");
		operation.setLength(50);
		tableDefinition.addColumn(operation);

		ColumnDefinition exportFlag = new ColumnDefinition();
		exportFlag.setName("exportFlag");
		exportFlag.setColumnName("EXPORTFLAG_");
		exportFlag.setJavaType("String");
		exportFlag.setLength(1);
		tableDefinition.addColumn(exportFlag);

		ColumnDefinition exportDBName = new ColumnDefinition();
		exportDBName.setName("exportDBName");
		exportDBName.setColumnName("EXPORTDBNAME_");
		exportDBName.setJavaType("String");
		exportDBName.setLength(50);
		tableDefinition.addColumn(exportDBName);

		ColumnDefinition lastExportStatus = new ColumnDefinition();
		lastExportStatus.setName("lastExportStatus");
		lastExportStatus.setColumnName("LASTEXPORTSTATUS_");
		lastExportStatus.setJavaType("Integer");
		tableDefinition.addColumn(lastExportStatus);

		ColumnDefinition lastExportTime = new ColumnDefinition();
		lastExportTime.setName("lastExportTime");
		lastExportTime.setColumnName("LASTEXPORTTIME_");
		lastExportTime.setJavaType("Date");
		tableDefinition.addColumn(lastExportTime);

		ColumnDefinition datasetIds = new ColumnDefinition();
		datasetIds.setName("datasetIds");
		datasetIds.setColumnName("DATASETIDS_");
		datasetIds.setJavaType("String");
		datasetIds.setLength(2000);
		tableDefinition.addColumn(datasetIds);

		ColumnDefinition sqlDefIds = new ColumnDefinition();
		sqlDefIds.setName("sqlDefIds");
		sqlDefIds.setColumnName("SQLDEFIDS_");
		sqlDefIds.setJavaType("String");
		sqlDefIds.setLength(500);
		tableDefinition.addColumn(sqlDefIds);

		ColumnDefinition createTableFlag = new ColumnDefinition();
		createTableFlag.setName("createTableFlag");
		createTableFlag.setColumnName("CREATETABLEFLAG_");
		createTableFlag.setJavaType("String");
		createTableFlag.setLength(1);
		tableDefinition.addColumn(createTableFlag);

		ColumnDefinition deleteFetch = new ColumnDefinition();
		deleteFetch.setName("deleteFetch");
		deleteFetch.setColumnName("DELETEFETCH_");
		deleteFetch.setJavaType("String");
		deleteFetch.setLength(1);
		tableDefinition.addColumn(deleteFetch);

		ColumnDefinition publicFlag = new ColumnDefinition();
		publicFlag.setName("publicFlag");
		publicFlag.setColumnName("PUBLICFLAG_");
		publicFlag.setJavaType("String");
		publicFlag.setLength(1);
		tableDefinition.addColumn(publicFlag);

		ColumnDefinition scheduleFlag = new ColumnDefinition();
		scheduleFlag.setName("scheduleFlag");
		scheduleFlag.setColumnName("SCHEDULEFLAG_");
		scheduleFlag.setJavaType("String");
		scheduleFlag.setLength(1);
		tableDefinition.addColumn(scheduleFlag);

		ColumnDefinition locked = new ColumnDefinition();
		locked.setName("locked");
		locked.setColumnName("LOCKED_");
		locked.setJavaType("Integer");
		tableDefinition.addColumn(locked);

		ColumnDefinition deleteFlag = new ColumnDefinition();
		deleteFlag.setName("deleteFlag");
		deleteFlag.setColumnName("DELETEFLAG_");
		deleteFlag.setJavaType("Integer");
		tableDefinition.addColumn(deleteFlag);

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

	public static void processDataRequest(DataRequest dataRequest) {
		if (dataRequest != null) {
			if (dataRequest.getFilter() != null) {
				if (dataRequest.getFilter().getField() != null) {
					dataRequest.getFilter().setColumn(columnMap.get(dataRequest.getFilter().getField()));
					dataRequest.getFilter().setJavaType(javaTypeMap.get(dataRequest.getFilter().getField()));
				}

				List<FilterDescriptor> filters = dataRequest.getFilter().getFilters();
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

	private SqlExportDomainFactory() {

	}

}
