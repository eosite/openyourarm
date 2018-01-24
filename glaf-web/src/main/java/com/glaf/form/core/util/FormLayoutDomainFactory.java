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
public class FormLayoutDomainFactory {

	public final static String TABLENAME = "FORM_LAYOUT";

	public final static ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public final static ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("deploymentId", "DEPLOYMENTID_");
		columnMap.put("name", "NAME_");
		columnMap.put("type", "TYPE_");
		columnMap.put("json", "JSON_");
		columnMap.put("imageFileId", "IMAGEFILEID_");
		columnMap.put("imageFileName", "IMAGEFILENAME_");
		columnMap.put("imagePath", "IMAGEPATH_");
		columnMap.put("smallImagePath", "SMALLIMAGEPATH_");
		columnMap.put("mediumImagePath", "MEDIUMIMAGEPATH_");
		columnMap.put("createDate", "CREATEDATE_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("status", "STATUS_");
		columnMap.put("height", "HEIGHT_");
		columnMap.put("width", "WIDTH_");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("deploymentId", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("json", "String");
		javaTypeMap.put("imageFileId", "String");
		javaTypeMap.put("imageFileName", "String");
		javaTypeMap.put("imagePath", "String");
		javaTypeMap.put("smallImagePath", "String");
		javaTypeMap.put("mediumImagePath", "String");
		javaTypeMap.put("createDate", "Date");
		javaTypeMap.put("createBy", "String");
		javaTypeMap.put("status", "Integer");
		javaTypeMap.put("height", "Integer");
		javaTypeMap.put("width", "Integer");
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
		tableDefinition.setName("FormLayout");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition deploymentId = new ColumnDefinition();
		deploymentId.setName("deploymentId");
		deploymentId.setColumnName("DEPLOYMENTID_");
		deploymentId.setJavaType("String");
		deploymentId.setLength(100);
		tableDefinition.addColumn(deploymentId);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME_");
		name.setJavaType("String");
		name.setLength(100);
		tableDefinition.addColumn(name);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE_");
		type.setJavaType("String");
		type.setLength(50);
		tableDefinition.addColumn(type);

		ColumnDefinition json = new ColumnDefinition();
		json.setName("json");
		json.setColumnName("JSON_");
		json.setJavaType("String");
		json.setLength(2000);
		tableDefinition.addColumn(json);

		ColumnDefinition imageFileId = new ColumnDefinition();
		imageFileId.setName("imageFileId");
		imageFileId.setColumnName("IMAGEFILEID_");
		imageFileId.setJavaType("String");
		imageFileId.setLength(200);
		tableDefinition.addColumn(imageFileId);

		ColumnDefinition imageFileName = new ColumnDefinition();
		imageFileName.setName("imageFileName");
		imageFileName.setColumnName("IMAGEFILENAME_");
		imageFileName.setJavaType("String");
		imageFileName.setLength(200);
		tableDefinition.addColumn(imageFileName);

		ColumnDefinition imagePath = new ColumnDefinition();
		imagePath.setName("imagePath");
		imagePath.setColumnName("IMAGEPATH_");
		imagePath.setJavaType("String");
		imagePath.setLength(200);
		tableDefinition.addColumn(imagePath);

		ColumnDefinition smallImagePath = new ColumnDefinition();
		smallImagePath.setName("smallImagePath");
		smallImagePath.setColumnName("SMALLIMAGEPATH_");
		smallImagePath.setJavaType("String");
		smallImagePath.setLength(200);
		tableDefinition.addColumn(smallImagePath);

		ColumnDefinition mediumImagePath = new ColumnDefinition();
		mediumImagePath.setName("mediumImagePath");
		mediumImagePath.setColumnName("MEDIUMIMAGEPATH_");
		mediumImagePath.setJavaType("String");
		mediumImagePath.setLength(200);
		tableDefinition.addColumn(mediumImagePath);

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

		ColumnDefinition status = new ColumnDefinition();
		status.setName("status");
		status.setColumnName("STATUS_");
		status.setJavaType("Integer");
		tableDefinition.addColumn(status);

		ColumnDefinition height = new ColumnDefinition();
		height.setName("height");
		height.setColumnName("HEIGHT_");
		height.setJavaType("Integer");
		tableDefinition.addColumn(height);

		ColumnDefinition width = new ColumnDefinition();
		width.setName("width");
		width.setColumnName("WIDTH_");
		width.setJavaType("Integer");
		tableDefinition.addColumn(width);

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
		if (dataRequest.getFilter() != null) {
			if (dataRequest.getFilter().getField() != null) {
				dataRequest.getFilter().setColumn(
						columnMap.get(dataRequest.getFilter().getField()));
				dataRequest.getFilter().setJavaType(
						javaTypeMap.get(dataRequest.getFilter().getField()));
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

	private FormLayoutDomainFactory() {

	}

}
