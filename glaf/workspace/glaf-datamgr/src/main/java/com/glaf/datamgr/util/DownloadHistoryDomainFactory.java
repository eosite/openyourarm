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
public class DownloadHistoryDomainFactory {

	public static final String TABLENAME = "DOWNLOAD_HISTORY";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	protected static TableModel tableModel;

	static {
		columnMap.put("id", "ID_");
		columnMap.put("fileId", "FILEID_");
		columnMap.put("filename", "FILENAME_");
		columnMap.put("ip", "IP_");
		columnMap.put("userId", "USERID_");
		columnMap.put("downloadTime", "DOWNLOADTIME_");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("fileId", "String");
		javaTypeMap.put("filename", "String");
		javaTypeMap.put("ip", "String");
		javaTypeMap.put("userId", "String");
		javaTypeMap.put("downloadTime", "Date");
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
		tableDefinition.setName("DownloadHistory");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition fileId = new ColumnDefinition();
		fileId.setName("fileId");
		fileId.setColumnName("FILEID_");
		fileId.setJavaType("String");
		fileId.setLength(50);
		tableDefinition.addColumn(fileId);

		ColumnDefinition filename = new ColumnDefinition();
		filename.setName("filename");
		filename.setColumnName("FILENAME_");
		filename.setJavaType("String");
		filename.setLength(250);
		tableDefinition.addColumn(filename);

		ColumnDefinition ip = new ColumnDefinition();
		ip.setName("ip");
		ip.setColumnName("IP_");
		ip.setJavaType("String");
		ip.setLength(100);
		tableDefinition.addColumn(ip);

		ColumnDefinition userId = new ColumnDefinition();
		userId.setName("userId");
		userId.setColumnName("USERID_");
		userId.setJavaType("String");
		userId.setLength(50);
		tableDefinition.addColumn(userId);

		ColumnDefinition downloadTime = new ColumnDefinition();
		downloadTime.setName("downloadTime");
		downloadTime.setColumnName("DOWNLOADTIME_");
		downloadTime.setJavaType("Date");
		tableDefinition.addColumn(downloadTime);

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

	private DownloadHistoryDomainFactory() {

	}

}
