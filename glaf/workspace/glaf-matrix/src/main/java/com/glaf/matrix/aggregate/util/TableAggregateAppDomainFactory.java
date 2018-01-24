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

package com.glaf.matrix.aggregate.util;

import java.util.Map;
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
public class TableAggregateAppDomainFactory {

	public static final String TABLENAME = "SYS_TABLE_AGGR_APP";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("nodeId", "NODEID_");
		columnMap.put("deploymentId", "DEPLOYMENTID_");
		columnMap.put("title", "TITLE_");
		columnMap.put("sourceTableName", "SOURCETABLENAME_");
		columnMap.put("sourceIdColumn", "SOURCEIDCOLUMN_");
		columnMap.put("sourceIndexIdColumn", "SOURCEINDEXIDCOLUMN_");
		columnMap.put("sourceParentIdColumn", "SOURCEPARENTIDCOLUMN_");
		columnMap.put("sourceTreeIdColumn", "SOURCETREEIDCOLUMN_");
		columnMap.put("sourceTextColumn", "SOURCETEXTCOLUMN_");
		columnMap.put("sourceTableDateSplitColumn", "SOURCETABLEDATESPLITCOLUMN_");
		columnMap.put("sourceCalculateColumns", "SOURCECALCULATECOLUMNS_");
		columnMap.put("splitDateFormat", "SPLITDATEFORMAT_");
		columnMap.put("dimensionTableName", "DIMENSIONTABLENAME_");
		columnMap.put("dimensionColumn", "DIMENSIONCOLUMN_");
		columnMap.put("srcDatabaseId", "SRCDATABASEID_");
		columnMap.put("syncFlag", "SYNCFLAG_");
		columnMap.put("targetDatabaseIds", "TARGETDATABASEIDS_");
		columnMap.put("targetTableName", "TARGETTABLENAME_");
		columnMap.put("type", "TYPE_");
		columnMap.put("autoSyncFlag", "AUTOSYNCFLAG_");
		columnMap.put("interval", "INTERVAL_");
		columnMap.put("sortNo", "SORTNO_");
		columnMap.put("active", "ACTIVE_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");
		columnMap.put("updateBy", "UPDATEBY_");
		columnMap.put("updateTime", "UPDATETIME_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("nodeId", "Long");
		javaTypeMap.put("deploymentId", "String");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("srcDatabaseId", "Long");
		javaTypeMap.put("sourceTableName", "String");
		javaTypeMap.put("sourceIdColumn", "String");
		javaTypeMap.put("sourceIndexIdColumn", "String");
		javaTypeMap.put("sourceParentIdColumn", "String");
		javaTypeMap.put("sourceTreeIdColumn", "String");
		javaTypeMap.put("sourceTextColumn", "String");
		javaTypeMap.put("sourceTableDateSplitColumn", "String");
		javaTypeMap.put("sourceCalculateColumns", "String");
		javaTypeMap.put("splitDateFormat", "String");
		javaTypeMap.put("dimensionTableName", "String");
		javaTypeMap.put("dimensionColumn", "String");
		javaTypeMap.put("syncFlag", "String");
		javaTypeMap.put("targetDatabaseIds", "String");
		javaTypeMap.put("targetTableName", "String");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("autoSyncFlag", "String");
		javaTypeMap.put("interval", "Integer");
		javaTypeMap.put("sortNo", "Integer");
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
		tableDefinition.setName("TableAggregateApp");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition nodeId = new ColumnDefinition();
		nodeId.setName("nodeId");
		nodeId.setColumnName("NODEID_");
		nodeId.setJavaType("Long");
		tableDefinition.addColumn(nodeId);

		ColumnDefinition deploymentId = new ColumnDefinition();
		deploymentId.setName("deploymentId");
		deploymentId.setColumnName("DEPLOYMENTID_");
		deploymentId.setJavaType("String");
		deploymentId.setLength(50);
		tableDefinition.addColumn(deploymentId);

		ColumnDefinition title = new ColumnDefinition();
		title.setName("title");
		title.setColumnName("TITLE_");
		title.setJavaType("String");
		title.setLength(200);
		tableDefinition.addColumn(title);

		ColumnDefinition srcDatabaseId = new ColumnDefinition();
		srcDatabaseId.setName("srcDatabaseId");
		srcDatabaseId.setColumnName("SRCDATABASEID_");
		srcDatabaseId.setJavaType("Long");
		tableDefinition.addColumn(srcDatabaseId);

		ColumnDefinition sourceTableName = new ColumnDefinition();
		sourceTableName.setName("sourceTableName");
		sourceTableName.setColumnName("SOURCETABLENAME_");
		sourceTableName.setJavaType("String");
		sourceTableName.setLength(50);
		tableDefinition.addColumn(sourceTableName);

		ColumnDefinition sourceIdColumn = new ColumnDefinition();
		sourceIdColumn.setName("sourceIdColumn");
		sourceIdColumn.setColumnName("SOURCEIDCOLUMN_");
		sourceIdColumn.setJavaType("String");
		sourceIdColumn.setLength(50);
		tableDefinition.addColumn(sourceIdColumn);

		ColumnDefinition sourceIndexIdColumn = new ColumnDefinition();
		sourceIndexIdColumn.setName("sourceIndexIdColumn");
		sourceIndexIdColumn.setColumnName("SOURCEINDEXIDCOLUMN_");
		sourceIndexIdColumn.setJavaType("String");
		sourceIndexIdColumn.setLength(50);
		tableDefinition.addColumn(sourceIndexIdColumn);

		ColumnDefinition sourceParentIdColumn = new ColumnDefinition();
		sourceParentIdColumn.setName("sourceParentIdColumn");
		sourceParentIdColumn.setColumnName("SOURCEPARENTIDCOLUMN_");
		sourceParentIdColumn.setJavaType("String");
		sourceParentIdColumn.setLength(50);
		tableDefinition.addColumn(sourceParentIdColumn);

		ColumnDefinition sourceTreeIdColumn = new ColumnDefinition();
		sourceTreeIdColumn.setName("sourceTreeIdColumn");
		sourceTreeIdColumn.setColumnName("SOURCETREEIDCOLUMN_");
		sourceTreeIdColumn.setJavaType("String");
		sourceTreeIdColumn.setLength(50);
		tableDefinition.addColumn(sourceTreeIdColumn);

		ColumnDefinition sourceTextColumn = new ColumnDefinition();
		sourceTextColumn.setName("sourceTextColumn");
		sourceTextColumn.setColumnName("SOURCETEXTCOLUMN_");
		sourceTextColumn.setJavaType("String");
		sourceTextColumn.setLength(50);
		tableDefinition.addColumn(sourceTextColumn);

		ColumnDefinition sourceTableDateSplitColumn = new ColumnDefinition();
		sourceTableDateSplitColumn.setName("sourceTableDateSplitColumn");
		sourceTableDateSplitColumn.setColumnName("SOURCETABLEDATESPLITCOLUMN_");
		sourceTableDateSplitColumn.setJavaType("String");
		sourceTableDateSplitColumn.setLength(50);
		tableDefinition.addColumn(sourceTableDateSplitColumn);

		ColumnDefinition sourceCalculateColumns = new ColumnDefinition();
		sourceCalculateColumns.setName("sourceCalculateColumns");
		sourceCalculateColumns.setColumnName("SOURCECALCULATECOLUMNS_");
		sourceCalculateColumns.setJavaType("String");
		sourceCalculateColumns.setLength(4000);
		tableDefinition.addColumn(sourceCalculateColumns);

		ColumnDefinition splitDateFormat = new ColumnDefinition();
		splitDateFormat.setName("splitDateFormat");
		splitDateFormat.setColumnName("SPLITDATEFORMAT_");
		splitDateFormat.setJavaType("String");
		splitDateFormat.setLength(50);
		tableDefinition.addColumn(splitDateFormat);

		ColumnDefinition dimensionTableName = new ColumnDefinition();
		dimensionTableName.setName("dimensionTableName");
		dimensionTableName.setColumnName("DIMENSIONTABLENAME_");
		dimensionTableName.setJavaType("String");
		dimensionTableName.setLength(50);
		tableDefinition.addColumn(dimensionTableName);

		ColumnDefinition dimensionColumn = new ColumnDefinition();
		dimensionColumn.setName("dimensionColumn");
		dimensionColumn.setColumnName("DIMENSIONCOLUMN_");
		dimensionColumn.setJavaType("String");
		dimensionColumn.setLength(50);
		tableDefinition.addColumn(dimensionColumn);

		ColumnDefinition syncFlag = new ColumnDefinition();
		syncFlag.setName("syncFlag");
		syncFlag.setColumnName("SYNCFLAG_");
		syncFlag.setJavaType("String");
		syncFlag.setLength(50);
		tableDefinition.addColumn(syncFlag);

		ColumnDefinition targetDatabaseIds = new ColumnDefinition();
		targetDatabaseIds.setName("targetDatabaseIds");
		targetDatabaseIds.setColumnName("TARGETDATABASEIDS_");
		targetDatabaseIds.setJavaType("String");
		targetDatabaseIds.setLength(2000);
		tableDefinition.addColumn(targetDatabaseIds);

		ColumnDefinition targetTableName = new ColumnDefinition();
		targetTableName.setName("targetTableName");
		targetTableName.setColumnName("TARGETTABLENAME_");
		targetTableName.setJavaType("String");
		targetTableName.setLength(50);
		tableDefinition.addColumn(targetTableName);

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

		ColumnDefinition sortNo = new ColumnDefinition();
		sortNo.setName("sortNo");
		sortNo.setColumnName("SORTNO_");
		sortNo.setJavaType("Integer");
		tableDefinition.addColumn(sortNo);

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

	private TableAggregateAppDomainFactory() {

	}

}
