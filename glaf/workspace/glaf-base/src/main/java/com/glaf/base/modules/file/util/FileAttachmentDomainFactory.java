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

package com.glaf.base.modules.file.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;

import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;

public class FileAttachmentDomainFactory {

	public static final String TABLENAME = "T_ATTACHMENT";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("nodeId", "NODEID_");
		columnMap.put("name", "NAME_");
		columnMap.put("filename", "FILENAME_");
		columnMap.put("originalFilename", "ORIGINALFILENAME_");
		columnMap.put("referId", "REFERID_");
		columnMap.put("type", "TYPE_");
		columnMap.put("desc", "DESC_");
		columnMap.put("path", "PATH_");
		columnMap.put("size", "SIZE_");
		columnMap.put("width", "WIDTH_");
		columnMap.put("height", "HEIGHT_");
		columnMap.put("locked", "LOCKED_");
		columnMap.put("deleteFlag", "DELETEFLAG_");
		columnMap.put("createDate", "CREATEDATE_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("updateDate", "UPDATEDATE_");
		columnMap.put("updateBy", "UPDATEBY_");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("nodeId", "Long");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("filename", "String");
		javaTypeMap.put("originalFilename", "String");
		javaTypeMap.put("referId", "String");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("desc", "String");
		javaTypeMap.put("path", "String");
		javaTypeMap.put("size", "Integer");
		javaTypeMap.put("width", "Integer");
		javaTypeMap.put("height", "Integer");
		javaTypeMap.put("locked", "Integer");
		javaTypeMap.put("deleteFlag", "Integer");
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
		tableDefinition.setName("FileAttachment");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition nodeId = new ColumnDefinition();
		nodeId.setName("nodeId");
		nodeId.setColumnName("NODEID_");
		nodeId.setJavaType("Long");
		tableDefinition.addColumn(nodeId);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME_");
		name.setJavaType("String");
		name.setLength(200);
		tableDefinition.addColumn(name);

		ColumnDefinition filename = new ColumnDefinition();
		filename.setName("filename");
		filename.setColumnName("FILENAME_");
		filename.setJavaType("String");
		filename.setLength(200);
		tableDefinition.addColumn(filename);

		ColumnDefinition originalFilename = new ColumnDefinition();
		originalFilename.setName("originalFilename");
		originalFilename.setColumnName("ORIGINALFILENAME_");
		originalFilename.setJavaType("String");
		originalFilename.setLength(200);
		tableDefinition.addColumn(originalFilename);

		ColumnDefinition referId = new ColumnDefinition();
		referId.setName("referId");
		referId.setColumnName("REFERID_");
		referId.setJavaType("String");
		referId.setLength(200);
		tableDefinition.addColumn(referId);

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
		desc.setLength(100);
		tableDefinition.addColumn(desc);

		ColumnDefinition path = new ColumnDefinition();
		path.setName("path");
		path.setColumnName("PATH_");
		path.setJavaType("String");
		path.setLength(200);
		tableDefinition.addColumn(path);

		ColumnDefinition size = new ColumnDefinition();
		size.setName("size");
		size.setColumnName("SIZE_");
		size.setJavaType("Integer");
		tableDefinition.addColumn(size);

		ColumnDefinition width = new ColumnDefinition();
		width.setName("width");
		width.setColumnName("WIDTH_");
		width.setJavaType("Integer");
		tableDefinition.addColumn(width);

		ColumnDefinition height = new ColumnDefinition();
		height.setName("height");
		height.setColumnName("HEIGHT_");
		height.setJavaType("Integer");
		tableDefinition.addColumn(height);

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

	private FileAttachmentDomainFactory() {

	}

}
