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
import com.glaf.core.base.TableModel;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;

/**
 * 
 * 实体数据工厂类
 *
 */
public class DataSetDomainFactory {

	public static final String TABLENAME = "SYS_DATASET";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	protected static TableModel tableModel;

	static {
		columnMap.put("id", "ID_");
		columnMap.put("name", "NAME_");
		columnMap.put("category", "CATEGORY_");
		columnMap.put("title", "TITLE_");
		columnMap.put("description", "DESCRIPTION_");
		columnMap.put("databaseId", "DATABASEID_");
		columnMap.put("executeDatabaseIds", "EXECUTEDATABASEIDS_");
		columnMap.put("statementId", "STATEMENTID_");
		columnMap.put("sql", "SQL_");
		columnMap.put("sourceRuleId", "SOURCERULEID_");
		columnMap.put("sourceRuleType", "SOURCERULETYPE_");
		columnMap.put("sourceTables", "SOURCETABLES_");
		columnMap.put("accessType", "ACCESSTYPE_");
		columnMap.put("perms", "PERMS_");
		columnMap.put("addressPerms", "ADDRESSPERMS_");
		columnMap.put("type", "TYPE_");
		columnMap.put("active", "ACTIVE_");
		columnMap.put("scheduleFlag", "SCHEDULEFLAG_");
		columnMap.put("cacheFlag", "CACHEFLAG_");
		columnMap.put("chartFlag", "CHARTFLAG_");
		columnMap.put("dynamicFlag", "DYNAMICFLAG_");
		columnMap.put("distinctFlag", "DISTINCTFLAG_");
		columnMap.put("shareFlag", "SHAREFLAG_");
		columnMap.put("publicFlag", "PUBLICFLAG_");
		columnMap.put("saveFlag", "SAVEFLAG_");
		columnMap.put("updateFlag", "UPDATEFLAG_");
		columnMap.put("verify", "VERIFY_");
		columnMap.put("initFlag", "INITFLAG_");
		columnMap.put("deleteFetch", "DELETEFETCH_");
		columnMap.put("exportFlag", "EXPORTFLAG_");
		columnMap.put("exportTableName", "EXPORTTABLENAME_");
		columnMap.put("exportDBName", "EXPORTDBNAME_");
		columnMap.put("lastExportStatus", "LASTEXPORTSTATUS_");
		columnMap.put("lastExportTime", "LASTEXPORTTIME_");
		columnMap.put("rowKey", "ROWKEY_");
		columnMap.put("locked", "LOCKED_");
		columnMap.put("deleteFlag", "DELETEFLAG_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");
		columnMap.put("updateBy", "UPDATEBY_");
		columnMap.put("updateTime", "UPDATETIME_");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("category", "String");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("description", "String");
		javaTypeMap.put("databaseId", "Long");
		javaTypeMap.put("executeDatabaseIds", "String");
		javaTypeMap.put("statementId", "String");
		javaTypeMap.put("sql", "String");
		javaTypeMap.put("sourceRuleId", "String");
		javaTypeMap.put("sourceRuleType", "String");
		javaTypeMap.put("sourceTables", "String");
		javaTypeMap.put("accessType", "String");
		javaTypeMap.put("perms", "String");
		javaTypeMap.put("addressPerms", "String");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("active", "String");
		javaTypeMap.put("scheduleFlag", "String");
		javaTypeMap.put("cacheFlag", "String");
		javaTypeMap.put("chartFlag", "String");
		javaTypeMap.put("dynamicFlag", "String");
		javaTypeMap.put("distinctFlag", "String");
		javaTypeMap.put("shareFlag", "String");
		javaTypeMap.put("publicFlag", "String");
		javaTypeMap.put("saveFlag", "String");
		javaTypeMap.put("updateFlag", "String");
		javaTypeMap.put("verify", "String");
		javaTypeMap.put("initFlag", "String");
		javaTypeMap.put("deleteFetch", "String");
		javaTypeMap.put("exportFlag", "String");
		javaTypeMap.put("exportTableName", "String");
		javaTypeMap.put("exportDBName", "String");
		javaTypeMap.put("lastExportStatus", "Integer");
		javaTypeMap.put("lastExportTime", "Date");
		javaTypeMap.put("rowKey", "String");
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
		tableDefinition.setName("DataSet");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition category = new ColumnDefinition();
		category.setName("category");
		category.setColumnName("CATEGORY_");
		category.setJavaType("String");
		category.setLength(50);
		tableDefinition.addColumn(category);

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

		ColumnDefinition description = new ColumnDefinition();
		description.setName("description");
		description.setColumnName("DESCRIPTION_");
		description.setJavaType("String");
		description.setLength(500);
		tableDefinition.addColumn(description);

		ColumnDefinition databaseId = new ColumnDefinition();
		databaseId.setName("databaseId");
		databaseId.setColumnName("DATABASEID_");
		databaseId.setJavaType("Long");
		tableDefinition.addColumn(databaseId);

		ColumnDefinition executeDatabaseIds = new ColumnDefinition();
		executeDatabaseIds.setName("executeDatabaseIds");
		executeDatabaseIds.setColumnName("EXECUTEDATABASEIDS_");
		executeDatabaseIds.setJavaType("String");
		executeDatabaseIds.setLength(2000);
		tableDefinition.addColumn(executeDatabaseIds);

		ColumnDefinition statementId = new ColumnDefinition();
		statementId.setName("statementId");
		statementId.setColumnName("STATEMENTID_");
		statementId.setJavaType("String");
		statementId.setLength(100);
		tableDefinition.addColumn(statementId);

		ColumnDefinition sql = new ColumnDefinition();
		sql.setName("sql");
		sql.setColumnName("SQL_");
		sql.setJavaType("Clob");
		sql.setLength(4000);
		tableDefinition.addColumn(sql);

		ColumnDefinition sourceRuleId = new ColumnDefinition();
		sourceRuleId.setName("sourceRuleId");
		sourceRuleId.setColumnName("SOURCERULEID_");
		sourceRuleId.setJavaType("String");
		sourceRuleId.setLength(200);
		tableDefinition.addColumn(sourceRuleId);

		ColumnDefinition sourceRuleType = new ColumnDefinition();
		sourceRuleType.setName("sourceRuleType");
		sourceRuleType.setColumnName("SOURCERULETYPE_");
		sourceRuleType.setJavaType("String");
		sourceRuleType.setLength(50);
		tableDefinition.addColumn(sourceRuleType);

		ColumnDefinition sourceTables = new ColumnDefinition();
		sourceTables.setName("sourceTables");
		sourceTables.setColumnName("SOURCETABLES_");
		sourceTables.setJavaType("Clob");
		sourceTables.setLength(4000);
		tableDefinition.addColumn(sourceTables);

		ColumnDefinition accessType = new ColumnDefinition();
		accessType.setName("accessType");
		accessType.setColumnName("ACCESSTYPE_");
		accessType.setJavaType("String");
		accessType.setLength(20);
		tableDefinition.addColumn(accessType);

		ColumnDefinition perms = new ColumnDefinition();
		perms.setName("perms");
		perms.setColumnName("PERMS_");
		perms.setJavaType("String");
		perms.setLength(500);
		tableDefinition.addColumn(perms);

		ColumnDefinition addressPerms = new ColumnDefinition();
		addressPerms.setName("addressPerms");
		addressPerms.setColumnName("ADDRESSPERMS_");
		addressPerms.setJavaType("String");
		addressPerms.setLength(2000);
		tableDefinition.addColumn(addressPerms);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE_");
		type.setJavaType("String");
		type.setLength(50);
		tableDefinition.addColumn(type);

		ColumnDefinition active = new ColumnDefinition();
		active.setName("active");
		active.setColumnName("ACTIVE_");
		active.setJavaType("String");
		active.setLength(1);
		tableDefinition.addColumn(active);

		ColumnDefinition scheduleFlag = new ColumnDefinition();
		scheduleFlag.setName("scheduleFlag");
		scheduleFlag.setColumnName("SCHEDULEFLAG_");
		scheduleFlag.setJavaType("String");
		scheduleFlag.setLength(1);
		tableDefinition.addColumn(scheduleFlag);

		ColumnDefinition cacheFlag = new ColumnDefinition();
		cacheFlag.setName("cacheFlag");
		cacheFlag.setColumnName("CACHEFLAG_");
		cacheFlag.setJavaType("String");
		cacheFlag.setLength(1);
		tableDefinition.addColumn(cacheFlag);

		ColumnDefinition chartFlag = new ColumnDefinition();
		chartFlag.setName("chartFlag");
		chartFlag.setColumnName("CHARTFLAG_");
		chartFlag.setJavaType("String");
		chartFlag.setLength(1);
		tableDefinition.addColumn(chartFlag);

		ColumnDefinition dynamicFlag = new ColumnDefinition();
		dynamicFlag.setName("dynamicFlag");
		dynamicFlag.setColumnName("DYNAMICFLAG_");
		dynamicFlag.setJavaType("String");
		dynamicFlag.setLength(1);
		tableDefinition.addColumn(dynamicFlag);

		ColumnDefinition distinctFlag = new ColumnDefinition();
		distinctFlag.setName("distinctFlag");
		distinctFlag.setColumnName("DISTINCTFLAG_");
		distinctFlag.setJavaType("String");
		distinctFlag.setLength(1);
		tableDefinition.addColumn(distinctFlag);

		ColumnDefinition shareFlag = new ColumnDefinition();
		shareFlag.setName("shareFlag");
		shareFlag.setColumnName("SHAREFLAG_");
		shareFlag.setJavaType("String");
		shareFlag.setLength(1);
		tableDefinition.addColumn(shareFlag);

		ColumnDefinition publicFlag = new ColumnDefinition();
		publicFlag.setName("publicFlag");
		publicFlag.setColumnName("PUBLICFLAG_");
		publicFlag.setJavaType("String");
		publicFlag.setLength(1);
		tableDefinition.addColumn(publicFlag);

		ColumnDefinition saveFlag = new ColumnDefinition();
		saveFlag.setName("saveFlag");
		saveFlag.setColumnName("SAVEFLAG_");
		saveFlag.setJavaType("String");
		saveFlag.setLength(2);
		tableDefinition.addColumn(saveFlag);

		ColumnDefinition updateFlag = new ColumnDefinition();
		updateFlag.setName("updateFlag");
		updateFlag.setColumnName("UPDATEFLAG_");
		updateFlag.setJavaType("String");
		updateFlag.setLength(1);
		tableDefinition.addColumn(updateFlag);

		ColumnDefinition verify = new ColumnDefinition();
		verify.setName("verify");
		verify.setColumnName("VERIFY_");
		verify.setJavaType("String");
		verify.setLength(1);
		tableDefinition.addColumn(verify);

		ColumnDefinition initFlag = new ColumnDefinition();
		initFlag.setName("initFlag");
		initFlag.setColumnName("INITFLAG_");
		initFlag.setJavaType("String");
		initFlag.setLength(1);
		tableDefinition.addColumn(initFlag);

		ColumnDefinition rowKey = new ColumnDefinition();
		rowKey.setName("rowKey");
		rowKey.setColumnName("ROWKEY_");
		rowKey.setJavaType("String");
		rowKey.setLength(50);
		tableDefinition.addColumn(rowKey);

		ColumnDefinition deleteFetch = new ColumnDefinition();
		deleteFetch.setName("deleteFetch");
		deleteFetch.setColumnName("DELETEFETCH_");
		deleteFetch.setJavaType("String");
		deleteFetch.setLength(1);
		tableDefinition.addColumn(deleteFetch);

		ColumnDefinition exportFlag = new ColumnDefinition();
		exportFlag.setName("exportFlag");
		exportFlag.setColumnName("EXPORTFLAG_");
		exportFlag.setJavaType("String");
		exportFlag.setLength(1);
		tableDefinition.addColumn(exportFlag);

		ColumnDefinition exportTableName = new ColumnDefinition();
		exportTableName.setName("exportTableName");
		exportTableName.setColumnName("EXPORTTABLENAME_");
		exportTableName.setJavaType("String");
		exportTableName.setLength(50);
		tableDefinition.addColumn(exportTableName);

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

	private DataSetDomainFactory() {

	}

}
