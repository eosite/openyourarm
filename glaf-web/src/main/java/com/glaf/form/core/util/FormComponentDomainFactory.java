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
public class FormComponentDomainFactory {

	public static final String TABLENAME = "FORM_COMPONENT";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("parentId", "PARENTID_");
		columnMap.put("deploymentId", "DEPLOYMENTID_");
		columnMap.put("name", "NAME_");
		columnMap.put("type", "TYPE_");
		columnMap.put("desc", "DESC_");
		columnMap.put("dataRole", "DATAROLE_");
		columnMap.put("kendoComponent", "KENDOCOMPONENT_");
		columnMap.put("jsEngine", "JSENGINE_");
		columnMap.put("jsPath", "JSPATH_");
		columnMap.put("imageFileName", "IMAGEFILENAME_");
		columnMap.put("imagePath", "IMAGEPATH_");
		columnMap.put("smallImageFileName", "SMALLIMAGEFILENAME_");
		columnMap.put("smallImagePath", "SMALLIMAGEPATH_");
		columnMap.put("mediumImageFileName", "MEDIUMIMAGEFILENAME_");
		columnMap.put("mediumImagePath", "MEDIUMIMAGEPATH_");
		columnMap.put("category", "CATEGORY_");
		columnMap.put("locked", "LOCKED_");
		columnMap.put("deleteFlag", "DELETEFLAG_");
		columnMap.put("version", "VERSION_");
		columnMap.put("createDate", "CREATEDATE_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("updateDate", "UPDATEDATE_");
		columnMap.put("updateBy", "UPDATEBY_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("parentId", "Long");
		javaTypeMap.put("deploymentId", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("desc", "String");
		javaTypeMap.put("dataRole", "String");
		javaTypeMap.put("kendoComponent", "String");
		javaTypeMap.put("jsEngine", "String");
		javaTypeMap.put("jsPath", "String");
		javaTypeMap.put("imageFileName", "String");
		javaTypeMap.put("imagePath", "String");
		javaTypeMap.put("smallFileName", "String");
		javaTypeMap.put("smallImagePath", "String");
		javaTypeMap.put("mediumFileName", "String");
		javaTypeMap.put("mediumImagePath", "String");
		javaTypeMap.put("category", "String");
		javaTypeMap.put("locked", "Integer");
		javaTypeMap.put("deleteFlag", "Integer");
		javaTypeMap.put("version", "Integer");
		javaTypeMap.put("createDate", "Date");
		javaTypeMap.put("createBy", "String");
		javaTypeMap.put("updateDate", "Date");
		javaTypeMap.put("updateBy", "String");
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
		tableDefinition.setName("FormComponent");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition parentId = new ColumnDefinition();
		parentId.setName("parentId");
		parentId.setColumnName("PARENTID_");
		parentId.setJavaType("Long");
		tableDefinition.addColumn(parentId);

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

		ColumnDefinition desc = new ColumnDefinition();
		desc.setName("desc");
		desc.setColumnName("DESC_");
		desc.setJavaType("String");
		desc.setLength(2000);
		tableDefinition.addColumn(desc);

		ColumnDefinition dataRole = new ColumnDefinition();
		dataRole.setName("dataRole");
		dataRole.setColumnName("DATAROLE_");
		dataRole.setJavaType("String");
		dataRole.setLength(50);
		tableDefinition.addColumn(dataRole);

		ColumnDefinition kendoComponent = new ColumnDefinition();
		kendoComponent.setName("kendoComponent");
		kendoComponent.setColumnName("KENDOCOMPONENT_");
		kendoComponent.setJavaType("String");
		kendoComponent.setLength(200);
		tableDefinition.addColumn(kendoComponent);

		ColumnDefinition jsEngine = new ColumnDefinition();
		jsEngine.setName("jsEngine");
		jsEngine.setColumnName("JSENGINE_");
		jsEngine.setJavaType("String");
		jsEngine.setLength(50);
		tableDefinition.addColumn(jsEngine);

		ColumnDefinition jsPath = new ColumnDefinition();
		jsPath.setName("jsPath");
		jsPath.setColumnName("JSPATH_");
		jsPath.setJavaType("String");
		jsPath.setLength(200);
		tableDefinition.addColumn(jsPath);

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

		ColumnDefinition smallImageFileName = new ColumnDefinition();
		smallImageFileName.setName("smallImageFileName");
		smallImageFileName.setColumnName("SMALLIMAGEFILENAME_");
		smallImageFileName.setJavaType("String");
		smallImageFileName.setLength(200);
		tableDefinition.addColumn(smallImageFileName);

		ColumnDefinition smallImagePath = new ColumnDefinition();
		smallImagePath.setName("smallImagePath");
		smallImagePath.setColumnName("SMALLIMAGEPATH_");
		smallImagePath.setJavaType("String");
		smallImagePath.setLength(200);
		tableDefinition.addColumn(smallImagePath);

		ColumnDefinition mediumImageFileName = new ColumnDefinition();
		mediumImageFileName.setName("mediumImageFileName");
		mediumImageFileName.setColumnName("MEDIUMIMAGEFILENAME_");
		mediumImageFileName.setJavaType("String");
		mediumImageFileName.setLength(200);
		tableDefinition.addColumn(mediumImageFileName);

		ColumnDefinition mediumImagePath = new ColumnDefinition();
		mediumImagePath.setName("mediumImagePath");
		mediumImagePath.setColumnName("MEDIUMIMAGEPATH_");
		mediumImagePath.setJavaType("String");
		mediumImagePath.setLength(200);
		tableDefinition.addColumn(mediumImagePath);

		ColumnDefinition category = new ColumnDefinition();
		category.setName("category");
		category.setColumnName("CATEGORY_");
		category.setJavaType("String");
		category.setLength(200);
		tableDefinition.addColumn(category);

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

		ColumnDefinition version = new ColumnDefinition();
		version.setName("version");
		version.setColumnName("VERSION_");
		version.setJavaType("Integer");
		tableDefinition.addColumn(version);

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

		ColumnDefinition updateDate = new ColumnDefinition();
		updateDate.setName("updateDate");
		updateDate.setColumnName("UPDATEDATE_");
		updateDate.setJavaType("Date");
		tableDefinition.addColumn(updateDate);

		ColumnDefinition updateBy = new ColumnDefinition();
		updateBy.setName("updateBy");
		updateBy.setColumnName("UPDATEBY_");
		updateBy.setJavaType("String");
		updateBy.setLength(50);
		tableDefinition.addColumn(updateBy);

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

	private FormComponentDomainFactory() {

	}

}
