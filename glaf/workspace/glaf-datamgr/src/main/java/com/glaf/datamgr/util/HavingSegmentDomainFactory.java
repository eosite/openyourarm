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
public class HavingSegmentDomainFactory {

	public static final String TABLENAME = "SYS_DATA_HAVING";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	protected static TableModel tableModel;

	static {
		columnMap.put("id", "ID_");
		columnMap.put("datasetId", "DATASETID_");
		columnMap.put("tableName", "TABLENAME_");
		columnMap.put("columnName", "COLUMNNAME_");
		columnMap.put("expression", "EXPRESSION_");
		columnMap.put("dynamic", "DYNAMIC_");
		columnMap.put("parameName", "PARAMNAME_");
		columnMap.put("parameType", "PARAMETYPE_");
		columnMap.put("collection", "COLLECTION_");
		columnMap.put("operator", "OPERATOR_");
		columnMap.put("connector", "CONNECTOR_");
		columnMap.put("ordinal", "ORDINAL_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");
		columnMap.put("updateBy", "UPDATEBY_");
		columnMap.put("updateTime", "UPDATETIME_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("datasetId", "String");
		javaTypeMap.put("tableName", "String");
		javaTypeMap.put("columnName", "String");
		javaTypeMap.put("expression", "String");
		javaTypeMap.put("dynamic", "String");
		javaTypeMap.put("parameName", "String");
		javaTypeMap.put("parameType", "String");
		javaTypeMap.put("collection", "String");
		javaTypeMap.put("operator", "String");
		javaTypeMap.put("connector", "String");
		javaTypeMap.put("ordinal", "Integer");
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
		tableDefinition.setName("HavingSegment");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition datasetId = new ColumnDefinition();
		datasetId.setName("datasetId");
		datasetId.setColumnName("DATASETID_");
		datasetId.setJavaType("String");
		datasetId.setLength(50);
		tableDefinition.addColumn(datasetId);

		ColumnDefinition entityTableName = new ColumnDefinition();
		entityTableName.setName("tableName");
		entityTableName.setColumnName("TABLENAME_");
		entityTableName.setJavaType("String");
		entityTableName.setLength(50);
		tableDefinition.addColumn(entityTableName);

		ColumnDefinition columnName = new ColumnDefinition();
		columnName.setName("columnName");
		columnName.setColumnName("COLUMNNAME_");
		columnName.setJavaType("String");
		columnName.setLength(50);
		tableDefinition.addColumn(columnName);

		ColumnDefinition expression = new ColumnDefinition();
		expression.setName("expression");
		expression.setColumnName("EXPRESSION_");
		expression.setJavaType("String");
		expression.setLength(500);
		tableDefinition.addColumn(expression);

		ColumnDefinition dynamic = new ColumnDefinition();
		dynamic.setName("dynamic");
		dynamic.setColumnName("DYNAMIC_");
		dynamic.setJavaType("String");
		dynamic.setLength(1);
		tableDefinition.addColumn(dynamic);

		ColumnDefinition parameName = new ColumnDefinition();
		parameName.setName("parameName");
		parameName.setColumnName("PARAMNAME_");
		parameName.setJavaType("String");
		parameName.setLength(100);
		tableDefinition.addColumn(parameName);

		ColumnDefinition parameType = new ColumnDefinition();
		parameType.setName("parameType");
		parameType.setColumnName("PARAMETYPE_");
		parameType.setJavaType("String");
		parameType.setLength(50);
		tableDefinition.addColumn(parameType);

		ColumnDefinition collection = new ColumnDefinition();
		collection.setName("collection");
		collection.setColumnName("COLLECTION_");
		collection.setJavaType("String");
		collection.setLength(50);
		tableDefinition.addColumn(collection);

		ColumnDefinition operator = new ColumnDefinition();
		operator.setName("operator");
		operator.setColumnName("OPERATOR_");
		operator.setJavaType("String");
		operator.setLength(50);
		tableDefinition.addColumn(operator);

		ColumnDefinition connector = new ColumnDefinition();
		connector.setName("connector");
		connector.setColumnName("CONNECTOR_");
		connector.setJavaType("String");
		connector.setLength(50);
		tableDefinition.addColumn(connector);

		ColumnDefinition ordinal = new ColumnDefinition();
		ordinal.setName("ordinal");
		ordinal.setColumnName("ORDINAL_");
		ordinal.setJavaType("Integer");
		tableDefinition.addColumn(ordinal);

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

	private HavingSegmentDomainFactory() {

	}

}
