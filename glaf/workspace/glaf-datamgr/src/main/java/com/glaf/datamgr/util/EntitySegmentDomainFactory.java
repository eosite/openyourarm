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
public class EntitySegmentDomainFactory {

	public static final String TABLENAME = "SYS_ENTITY_SEGMENT";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	protected static TableModel tableModel;

	static {
		columnMap.put("id", "ID_");
		columnMap.put("title", "TITLE_");
		columnMap.put("description", "DESCRIPTION_");
		columnMap.put("operation", "OPERATION_");
		columnMap.put("namespace", "NAMESPACE_");
		columnMap.put("parameterType", "PARAMETERTYPE_");
		columnMap.put("resultType", "RESULTTYPE_");
		columnMap.put("locked", "LOCKED_");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("description", "String");
		javaTypeMap.put("operation", "String");
		javaTypeMap.put("namespace", "String");
		javaTypeMap.put("parameterType", "String");
		javaTypeMap.put("resultType", "String");
		javaTypeMap.put("locked", "Integer");
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
		tableDefinition.setName("EntitySegment");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(200);
		tableDefinition.setIdColumn(idColumn);

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

		ColumnDefinition operation = new ColumnDefinition();
		operation.setName("operation");
		operation.setColumnName("OPERATION_");
		operation.setJavaType("String");
		operation.setLength(50);
		tableDefinition.addColumn(operation);

		ColumnDefinition namespace = new ColumnDefinition();
		namespace.setName("namespace");
		namespace.setColumnName("NAMESPACE_");
		namespace.setJavaType("String");
		namespace.setLength(200);
		tableDefinition.addColumn(namespace);

		ColumnDefinition parameterType = new ColumnDefinition();
		parameterType.setName("parameterType");
		parameterType.setColumnName("PARAMETERTYPE_");
		parameterType.setJavaType("String");
		parameterType.setLength(200);
		tableDefinition.addColumn(parameterType);

		ColumnDefinition resultType = new ColumnDefinition();
		resultType.setName("resultType");
		resultType.setColumnName("RESULTTYPE_");
		resultType.setJavaType("String");
		resultType.setLength(200);
		tableDefinition.addColumn(resultType);

		ColumnDefinition locked = new ColumnDefinition();
		locked.setName("locked");
		locked.setColumnName("LOCKED_");
		locked.setJavaType("Integer");
		tableDefinition.addColumn(locked);

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

	private EntitySegmentDomainFactory() {

	}

}
