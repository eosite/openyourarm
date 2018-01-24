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
public class TableCombinationColumnDomainFactory {

	public static final String TABLENAME = "TABLE_COMBINATION_COL";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("combinationId", "COMBINATIONID_");
		columnMap.put("sourceColumnName", "SOURCECOLUMNNAME_");
		columnMap.put("datasetId", "DATASETID_");
		columnMap.put("targetTableName", "TARGETTABLENAME_");
		columnMap.put("targetColumnName", "TARGETCOLUMNNAME_");
		columnMap.put("targetColumnLabel", "TARGETCOLUMNLABEL_");
		columnMap.put("initValue", "INITVALUE_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("combinationId", "Long");
		javaTypeMap.put("sourceColumnName", "String");
		javaTypeMap.put("datasetId", "String");
		javaTypeMap.put("targetTableName", "String");
		javaTypeMap.put("targetColumnName", "String");
		javaTypeMap.put("targetColumnLabel", "String");
		javaTypeMap.put("initValue", "String");
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
		tableDefinition.setName("TableCombinationColumn");

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

		ColumnDefinition sourceColumnName = new ColumnDefinition();
		sourceColumnName.setName("sourceColumnName");
		sourceColumnName.setColumnName("SOURCECOLUMNNAME_");
		sourceColumnName.setJavaType("String");
		sourceColumnName.setLength(50);
		tableDefinition.addColumn(sourceColumnName);

		ColumnDefinition datasetId = new ColumnDefinition();
		datasetId.setName("datasetId");
		datasetId.setColumnName("DATASETID_");
		datasetId.setJavaType("String");
		datasetId.setLength(50);
		tableDefinition.addColumn(datasetId);

		ColumnDefinition targetTableName = new ColumnDefinition();
		targetTableName.setName("targetTableName");
		targetTableName.setColumnName("TARGETTABLENAME_");
		targetTableName.setJavaType("String");
		targetTableName.setLength(50);
		tableDefinition.addColumn(targetTableName);

		ColumnDefinition targetColumnName = new ColumnDefinition();
		targetColumnName.setName("targetColumnName");
		targetColumnName.setColumnName("TARGETCOLUMNNAME_");
		targetColumnName.setJavaType("String");
		targetColumnName.setLength(50);
		tableDefinition.addColumn(targetColumnName);

		ColumnDefinition targetColumnLabel = new ColumnDefinition();
		targetColumnLabel.setName("targetColumnLabel");
		targetColumnLabel.setColumnName("TARGETCOLUMNLABEL_");
		targetColumnLabel.setJavaType("String");
		targetColumnLabel.setLength(100);
		tableDefinition.addColumn(targetColumnLabel);

		ColumnDefinition initValue = new ColumnDefinition();
		initValue.setName("initValue");
		initValue.setColumnName("INITVALUE_");
		initValue.setJavaType("String");
		initValue.setLength(500);
		tableDefinition.addColumn(initValue);

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

	private TableCombinationColumnDomainFactory() {

	}

}
