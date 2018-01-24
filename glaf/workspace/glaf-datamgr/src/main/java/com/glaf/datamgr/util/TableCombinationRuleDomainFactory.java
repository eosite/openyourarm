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
public class TableCombinationRuleDomainFactory {

	public static final String TABLENAME = "TABLE_COMBINATION_RULE";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("combinationId", "COMBINATIONID_");
		columnMap.put("targetTableName", "TARGETTABLENAME_");
		columnMap.put("targetTablePrimaryKey", "TARGETTABLEPRIMARYKEY_");
		columnMap.put("uniquenessColumn", "UNIQUENESSCOLUMN_");
		columnMap.put("sqlCondition", "SQLCONDITION_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("combinationId", "Long");
		javaTypeMap.put("targetTableName", "String");
		javaTypeMap.put("targetTablePrimaryKey", "String");
		javaTypeMap.put("uniquenessColumn", "String");
		javaTypeMap.put("sqlCondition", "String");
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
		tableDefinition.setName("TableCombinationRule");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition combinationId = new ColumnDefinition();
		combinationId.setName("combinationId");
		combinationId.setColumnName("COMBINATIONID_");
		combinationId.setJavaType("Long");
		tableDefinition.addColumn(combinationId);

		ColumnDefinition targetTableName = new ColumnDefinition();
		targetTableName.setName("targetTableName");
		targetTableName.setColumnName("TARGETTABLENAME_");
		targetTableName.setJavaType("String");
		targetTableName.setLength(50);
		tableDefinition.addColumn(targetTableName);

		ColumnDefinition targetTablePrimaryKey = new ColumnDefinition();
		targetTablePrimaryKey.setName("targetTablePrimaryKey");
		targetTablePrimaryKey.setColumnName("TARGETTABLEPRIMARYKEY_");
		targetTablePrimaryKey.setJavaType("String");
		targetTablePrimaryKey.setLength(50);
		tableDefinition.addColumn(targetTablePrimaryKey);

		ColumnDefinition uniquenessColumn = new ColumnDefinition();
		uniquenessColumn.setName("uniquenessColumn");
		uniquenessColumn.setColumnName("UNIQUENESSCOLUMN_");
		uniquenessColumn.setJavaType("String");
		uniquenessColumn.setLength(50);
		tableDefinition.addColumn(uniquenessColumn);

		ColumnDefinition sqlCondition = new ColumnDefinition();
		sqlCondition.setName("sqlCondition");
		sqlCondition.setColumnName("SQLCONDITION_");
		sqlCondition.setJavaType("String");
		sqlCondition.setLength(2000);
		tableDefinition.addColumn(sqlCondition);

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

	private TableCombinationRuleDomainFactory() {

	}

}
