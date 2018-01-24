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
public class TableCombinationDomainFactory {

	public static final String TABLENAME = "TABLE_COMBINATION";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("name", "NAME_");
		columnMap.put("title", "TITLE_");
		columnMap.put("type", "TYPE_");
		columnMap.put("databaseIds", "DATABASEIDS_");
		columnMap.put("templateTableName", "TEMPLATETABLENAME_");
		columnMap.put("primaryKey", "PRIMARYKEY_");
		columnMap.put("uniquenessKey", "UNIQUENESSKEY_");
		columnMap.put("syncColumns", "SYNCCOLUMNS_");
		columnMap.put("syncTableNames", "SYNCTABLENAMES_");
		columnMap.put("datasetIds", "DATASETIDS_");
		columnMap.put("targetDatabaseId", "TARGETDATABASEID_");
		columnMap.put("targetTableName", "TARGETTABLENAME_");
		columnMap.put("targetTableStruct", "TARGETTABLESTRUCT_");
		columnMap.put("currentUserFlag", "CURRENTUSERFLAG_");
		columnMap.put("createTableFlag", "CREATETABLEFLAG_");
		columnMap.put("deleteFetch", "DELETEFETCH_");
		columnMap.put("syncStatus", "SYNCSTATUS_");
		columnMap.put("syncTime", "SYNCTIME_");
		columnMap.put("sortNo", "SORTNO_");
		columnMap.put("locked", "LOCKED_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");
		columnMap.put("updateBy", "UPDATEBY_");
		columnMap.put("updateTime", "UPDATETIME_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("databaseIds", "String");
		javaTypeMap.put("templateTableName", "String");
		javaTypeMap.put("primaryKey", "String");
		javaTypeMap.put("uniquenessKey", "String");
		javaTypeMap.put("syncColumns", "String");
		javaTypeMap.put("syncTableNames", "String");
		javaTypeMap.put("datasetIds", "String");
		javaTypeMap.put("targetDatabaseId", "Long");
		javaTypeMap.put("targetTableName", "String");
		javaTypeMap.put("targetTableStruct", "String");
		javaTypeMap.put("currentUserFlag", "String");
		javaTypeMap.put("createTableFlag", "String");
		javaTypeMap.put("deleteFetch", "String");
		javaTypeMap.put("syncStatus", "Integer");
		javaTypeMap.put("syncTime", "Date");
		javaTypeMap.put("sortNo", "Integer");
		javaTypeMap.put("locked", "Integer");
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
		tableDefinition.setName("TableCombination");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

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

		ColumnDefinition databaseIds = new ColumnDefinition();
		databaseIds.setName("databaseIds");
		databaseIds.setColumnName("DATABASEIDS_");
		databaseIds.setJavaType("String");
		databaseIds.setLength(2000);
		tableDefinition.addColumn(databaseIds);

		ColumnDefinition templateTableName = new ColumnDefinition();
		templateTableName.setName("templateTableName");
		templateTableName.setColumnName("TEMPLATETABLENAME_");
		templateTableName.setJavaType("String");
		templateTableName.setLength(50);
		tableDefinition.addColumn(templateTableName);

		ColumnDefinition primaryKey = new ColumnDefinition();
		primaryKey.setName("primaryKey");
		primaryKey.setColumnName("PRIMARYKEY_");
		primaryKey.setJavaType("String");
		primaryKey.setLength(50);
		tableDefinition.addColumn(primaryKey);

		ColumnDefinition uniquenessKey = new ColumnDefinition();
		uniquenessKey.setName("uniquenessKey");
		uniquenessKey.setColumnName("UNIQUENESSKEY_");
		uniquenessKey.setJavaType("String");
		uniquenessKey.setLength(50);
		tableDefinition.addColumn(uniquenessKey);

		ColumnDefinition syncColumns = new ColumnDefinition();
		syncColumns.setName("syncColumns");
		syncColumns.setColumnName("SYNCCOLUMNS_");
		syncColumns.setJavaType("String");
		syncColumns.setLength(2000);
		tableDefinition.addColumn(syncColumns);

		ColumnDefinition syncTableNames = new ColumnDefinition();
		syncTableNames.setName("syncTableNames");
		syncTableNames.setColumnName("SYNCTABLENAMES_");
		syncTableNames.setJavaType("String");
		syncTableNames.setLength(2000);
		tableDefinition.addColumn(syncTableNames);

		ColumnDefinition datasetIds = new ColumnDefinition();
		datasetIds.setName("datasetIds");
		datasetIds.setColumnName("DATASETIDS_");
		datasetIds.setJavaType("String");
		datasetIds.setLength(2000);
		tableDefinition.addColumn(datasetIds);

		ColumnDefinition targetDatabaseId = new ColumnDefinition();
		targetDatabaseId.setName("targetDatabaseId");
		targetDatabaseId.setColumnName("TARGETDATABASEID_");
		targetDatabaseId.setJavaType("Long");
		tableDefinition.addColumn(targetDatabaseId);

		ColumnDefinition targetTableName = new ColumnDefinition();
		targetTableName.setName("targetTableName");
		targetTableName.setColumnName("TARGETTABLENAME_");
		targetTableName.setJavaType("String");
		targetTableName.setLength(50);
		tableDefinition.addColumn(targetTableName);

		ColumnDefinition targetTableStruct = new ColumnDefinition();
		targetTableStruct.setName("targetTableStruct");
		targetTableStruct.setColumnName("TARGETTABLESTRUCT_");
		targetTableStruct.setJavaType("String");
		targetTableStruct.setLength(50);
		tableDefinition.addColumn(targetTableStruct);

		ColumnDefinition currentUserFlag = new ColumnDefinition();
		currentUserFlag.setName("currentUserFlag");
		currentUserFlag.setColumnName("CURRENTUSERFLAG_");
		currentUserFlag.setJavaType("String");
		currentUserFlag.setLength(1);
		tableDefinition.addColumn(currentUserFlag);

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

		ColumnDefinition syncStatus = new ColumnDefinition();
		syncStatus.setName("syncStatus");
		syncStatus.setColumnName("SYNCSTATUS_");
		syncStatus.setJavaType("Integer");
		tableDefinition.addColumn(syncStatus);

		ColumnDefinition syncTime = new ColumnDefinition();
		syncTime.setName("syncTime");
		syncTime.setColumnName("SYNCTIME_");
		syncTime.setJavaType("Date");
		tableDefinition.addColumn(syncTime);

		ColumnDefinition sortNo = new ColumnDefinition();
		sortNo.setName("sortNo");
		sortNo.setColumnName("SORTNO_");
		sortNo.setJavaType("Integer");
		tableDefinition.addColumn(sortNo);

		ColumnDefinition locked = new ColumnDefinition();
		locked.setName("locked");
		locked.setColumnName("LOCKED_");
		locked.setJavaType("Integer");
		tableDefinition.addColumn(locked);

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

	private TableCombinationDomainFactory() {

	}

}
