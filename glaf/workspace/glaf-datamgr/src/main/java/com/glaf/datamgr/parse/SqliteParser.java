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

package com.glaf.datamgr.parse;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.util.JdbcUtils;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.db.TableDataManager;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.UUID32;
import com.glaf.core.xml.XmlMappingReader;
import com.glaf.core.xml.XmlReader;
import com.glaf.datamgr.bean.SqliteDBHelper;
import com.zaxxer.hikari.HikariDataSource;

public class SqliteParser implements Parser {

	public static void main(String[] args) throws Exception {
		String mappingFile = "./report/mapping/SKU.mapping.xml";
		String dataFile = "./report/data/SKU.db";
		XmlReader reader = new XmlReader();
		TableDefinition tableDefinition = reader.read(new java.io.FileInputStream(mappingFile));
		if (tableDefinition != null) {
			if (DBUtils.tableExists(tableDefinition.getTableName())) {
				com.glaf.core.util.DBUtils.alterTable(tableDefinition);
			} else {
				com.glaf.core.util.DBUtils.createTable(tableDefinition);
			}
		}
		XmlMappingReader xmlReader = new XmlMappingReader();
		TableModel tableModel = xmlReader.read(new java.io.FileInputStream(mappingFile));
		SqliteParser parser = new SqliteParser();
		List<TableModel> rows = parser.parse(tableModel, new java.io.FileInputStream(dataFile));
		for (TableModel row : rows) {
			System.out.println(row.toString());
		}
		ITableDefinitionService tableDefinitionService = ContextFactory.getBean("tableDefinitionService");
		tableDefinitionService.save(tableDefinition);

		// ITableDataService tableDataService =
		// ContextFactory.getBean("tableDataService");
		// tableDataService.saveAll(tableModel.getTableName(), null, rows);
		TableDataManager manager = new TableDataManager();
		manager.saveAll("default", tableDefinition, null, rows);
	}

	public List<TableModel> parse(TableModel tableModel, java.io.InputStream data) {
		List<TableModel> rows = new java.util.ArrayList<TableModel>();
		Map<String, Object> params = new HashMap<String, Object>();
		SqliteDBHelper helper = new SqliteDBHelper();
		QueryHelper queryHelper = new QueryHelper();
		String dbfile = UUID32.getUUID();
		String dbpath = SystemProperties.getConfigRootPath() + "/temp/" + dbfile;
		HikariDataSource ds = null;
		Connection conn = null;
		try {
			FileUtils.save(dbpath, data);
			ds = helper.getTempDataSource(dbfile);
			conn = ds.getConnection();
			if (DBUtils.tableExists(conn, tableModel.getTableName())) {
				String sql = " select * from " + tableModel.getTableName();
				List<Map<String, Object>> resultList = queryHelper.getResultList(conn, sql, params);
				JdbcUtils.close(conn);
				if (resultList != null && !resultList.isEmpty()) {
					for (Map<String, Object> map : resultList) {
						Map<String, Object> dataMap = QueryUtils.lowerKeyMap(map);
						TableModel row = new TableModel();
						row.setTableName(tableModel.getTableName());
						if (tableModel.getIdColumn() != null && tableModel.getIdColumn().getJavaType() != null) {
							ColumnModel idColumn = tableModel.getIdColumn();
							if (idColumn.getName() != null && dataMap.get(idColumn.getName().toLowerCase()) != null) {
								ColumnModel col = new ColumnModel();
								col.setColumnName(idColumn.getColumnName());
								col.setName(idColumn.getName());
								col.setJavaType(idColumn.getJavaType());
								col.setValue(dataMap.get(idColumn.getName().toLowerCase()));
								switch (idColumn.getJavaType()) {
								case "Integer":
									col.setValue(ParamUtils.getInt(dataMap, idColumn.getName().toLowerCase()));
									break;
								case "Long":
									col.setValue(ParamUtils.getLong(dataMap, idColumn.getName().toLowerCase()));
									break;
								default:
									col.setValue(ParamUtils.getString(dataMap, idColumn.getName().toLowerCase()));
									break;
								}
								row.setIdColumn(col);
							} else if (idColumn.getColumnName() != null
									&& dataMap.get(idColumn.getColumnName().toLowerCase()) != null) {
								ColumnModel col = new ColumnModel();
								col.setColumnName(idColumn.getColumnName());
								col.setName(idColumn.getName());
								col.setJavaType(idColumn.getJavaType());
								col.setValue(dataMap.get(idColumn.getColumnName().toLowerCase()));
								switch (idColumn.getJavaType()) {
								case "Integer":
									col.setValue(ParamUtils.getInt(dataMap, idColumn.getColumnName().toLowerCase()));
									break;
								case "Long":
									col.setValue(ParamUtils.getLong(dataMap, idColumn.getColumnName().toLowerCase()));
									break;
								default:
									col.setValue(ParamUtils.getString(dataMap, idColumn.getColumnName().toLowerCase()));
									break;
								}
								row.setIdColumn(col);
							}
						}

						List<ColumnModel> columns = tableModel.getColumns();
						if (columns != null && !columns.isEmpty()) {
							for (ColumnModel column : columns) {
								if (column.getName() != null && dataMap.get(column.getName().toLowerCase()) != null) {
									ColumnModel col = new ColumnModel();
									col.setColumnName(column.getColumnName());
									col.setName(column.getName());
									col.setJavaType(column.getJavaType());
									col.setValue(dataMap.get(column.getName().toLowerCase()));
									switch (column.getJavaType()) {
									case "Integer":
										col.setValue(ParamUtils.getInt(dataMap, column.getName().toLowerCase()));
										break;
									case "Long":
										col.setValue(ParamUtils.getLong(dataMap, column.getName().toLowerCase()));
										break;
									case "Double":
										col.setValue(ParamUtils.getDouble(dataMap, column.getName().toLowerCase()));
										break;
									case "Date":
										col.setValue(ParamUtils.getDate(dataMap, column.getName().toLowerCase()));
										break;
									case "String":
										col.setValue(ParamUtils.getString(dataMap, column.getName().toLowerCase()));
									default:
										break;
									}
									row.addColumn(col);
								} else if (column.getColumnName() != null
										&& dataMap.get(column.getColumnName().toLowerCase()) != null) {
									ColumnModel col = new ColumnModel();
									col.setColumnName(column.getColumnName());
									col.setName(column.getName());
									col.setJavaType(column.getJavaType());
									col.setValue(dataMap.get(column.getColumnName().toLowerCase()));
									switch (column.getJavaType()) {
									case "Integer":
										col.setValue(ParamUtils.getInt(dataMap, column.getColumnName().toLowerCase()));
										break;
									case "Long":
										col.setValue(ParamUtils.getLong(dataMap, column.getColumnName().toLowerCase()));
										break;
									case "Double":
										col.setValue(
												ParamUtils.getDouble(dataMap, column.getColumnName().toLowerCase()));
										break;
									case "Date":
										col.setValue(ParamUtils.getDate(dataMap, column.getColumnName().toLowerCase()));
										break;
									case "String":
										col.setValue(
												ParamUtils.getString(dataMap, column.getColumnName().toLowerCase()));
									default:
										break;
									}
									row.addColumn(col);
								}
							}
						}
						rows.add(row);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
			if (ds != null) {
				try {
					ds.close();
					ds = null;
				} catch (Exception ex) {
				}
			}
			FileUtils.deleteFile(dbpath);
		}
		return rows;
	}

}