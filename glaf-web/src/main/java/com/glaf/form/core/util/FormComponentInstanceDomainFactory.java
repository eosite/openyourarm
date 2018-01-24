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
package com.glaf.form.core.util;

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
public class FormComponentInstanceDomainFactory {

	public static final String TABLENAME = "FORM_COMPONENT_INSTANCE";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("formDesignId", "FORMDESIGNID_");
		columnMap.put("deploymentId", "DEPLOYMENTID_");
		columnMap.put("componentId", "COMPONENTID_");
		columnMap.put("name", "NAME_");
		columnMap.put("title", "TITLE_");
		columnMap.put("type", "TYPE_");
		columnMap.put("dataType", "DATATYPE_");
		columnMap.put("kendoComponent", "KENDOCOMPONENT_");
		columnMap.put("value", "VALUE_");
		columnMap.put("createDate", "CREATEDATE_");
		columnMap.put("createBy", "CREATEBY_");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("formDesignId", "String");
		javaTypeMap.put("deploymentId", "String");
		javaTypeMap.put("componentId", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("dataType", "String");
		javaTypeMap.put("kendoComponent", "String");
		javaTypeMap.put("value", "String");
		javaTypeMap.put("createDate", "Date");
		javaTypeMap.put("createBy", "String");
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
		tableDefinition.setName("FormComponentInstance");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition formDesignId = new ColumnDefinition();
		formDesignId.setName("formDesignId");
		formDesignId.setColumnName("FORMDESIGNID_");
		formDesignId.setJavaType("String");
		formDesignId.setLength(100);
		tableDefinition.addColumn(formDesignId);

		ColumnDefinition deploymentId = new ColumnDefinition();
		deploymentId.setName("deploymentId");
		deploymentId.setColumnName("DEPLOYMENTID_");
		deploymentId.setJavaType("String");
		deploymentId.setLength(100);
		tableDefinition.addColumn(deploymentId);

		ColumnDefinition componentId = new ColumnDefinition();
		componentId.setName("componentId");
		componentId.setColumnName("COMPONENTID_");
		componentId.setJavaType("String");
		componentId.setLength(100);
		tableDefinition.addColumn(componentId);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME_");
		name.setJavaType("String");
		name.setLength(100);
		tableDefinition.addColumn(name);

		ColumnDefinition title = new ColumnDefinition();
		title.setName("title");
		title.setColumnName("TITLE_");
		title.setJavaType("String");
		title.setLength(100);
		tableDefinition.addColumn(title);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE_");
		type.setJavaType("String");
		type.setLength(50);
		tableDefinition.addColumn(type);

		ColumnDefinition dataType = new ColumnDefinition();
		dataType.setName("dataType");
		dataType.setColumnName("DATATYPE_");
		dataType.setJavaType("String");
		dataType.setLength(50);
		tableDefinition.addColumn(dataType);

		ColumnDefinition kendoComponent = new ColumnDefinition();
		kendoComponent.setName("kendoComponent");
		kendoComponent.setColumnName("KENDOCOMPONENT_");
		kendoComponent.setJavaType("String");
		kendoComponent.setLength(200);
		tableDefinition.addColumn(kendoComponent);

		ColumnDefinition value = new ColumnDefinition();
		value.setName("value");
		value.setColumnName("VALUE_");
		value.setJavaType("String");
		value.setLength(2000);
		tableDefinition.addColumn(value);

		ColumnDefinition createDate = new ColumnDefinition();
		createDate.setName("createDate");
		createDate.setColumnName("CREATEDATE_");
		createDate.setJavaType("Date");
		tableDefinition.addColumn(createDate);

		ColumnDefinition createBy = new ColumnDefinition();
		createBy.setName("createBy");
		createBy.setColumnName("CREATEBY_");
		createBy.setJavaType("String");
		createBy.setLength(50);
		tableDefinition.addColumn(createBy);

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
					dataRequest.getFilter().setColumn(
							columnMap.get(dataRequest.getFilter().getField()));
					dataRequest.getFilter()
							.setJavaType(
									javaTypeMap.get(dataRequest.getFilter()
											.getField()));
				}

				List<FilterDescriptor> filters = dataRequest.getFilter()
						.getFilters();
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

	private FormComponentInstanceDomainFactory() {

	}

}
