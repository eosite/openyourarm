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

package com.glaf.matrix.data.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.StringTools;
import com.glaf.matrix.data.domain.SysTable;
import com.glaf.matrix.data.domain.TableColumn;
import com.glaf.matrix.data.service.ITableService;
import com.glaf.matrix.data.util.TableDomainFactory;

public class TableExcelImportBean {
	protected static final Log logger = LogFactory.getLog(TableExcelImportBean.class);

	protected ITableService tableService;

	public TableExcelImportBean() {

	}

	public void imp(String tableId, long databaseId, InputStream inputStream) throws IOException {
		if (StringUtils.isNotEmpty(tableId)) {
			SysTable sysTable = getTableService().getSysTableById(tableId);
			if (sysTable != null) {
				List<TableColumn> columns = sysTable.getColumns();
				if (columns != null && !columns.isEmpty()) {
					IDatabaseService databaseService = ContextFactory.getBean("databaseService");
					Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
					Database database = databaseService.getDatabaseById(databaseId);
					Environment.setCurrentSystemName(database.getName());
					String systemName = database.getName();
					String tableName = sysTable.getTableName();
					TableDefinition tableDefinition = new TableDefinition();
					tableDefinition.setTableName(tableName);
					logger.debug("->tableName:" + tableName);
					if (DBUtils.tableExists(systemName, tableName)) {
						tableDefinition.setColumns(DBUtils.getColumnDefinitions(systemName, tableName));
					} else {
						tableDefinition = TableDomainFactory.getTableDefinition(tableName);
						tableDefinition.setTableName(tableName);
					}

					Map<Integer, TableColumn> columnMap1 = new HashMap<Integer, TableColumn>();

					Set<String> cols = new HashSet<String>();
					for (ColumnDefinition column : tableDefinition.getColumns()) {
						cols.add(column.getColumnName().toLowerCase());
					}

					for (TableColumn column : columns) {
						columnMap1.put(column.getColIndex(), column);
						if (!cols.contains(column.getColumnName().toLowerCase())) {
							ColumnDefinition col = new ColumnDefinition();
							col.setColumnName(column.getColumnName());
							col.setJavaType(column.getType());
							col.setLength(250);
							tableDefinition.getColumns().add(col);
						}
					}

					XSSFWorkbook wb = null;
					try {
						wb = new XSSFWorkbook(inputStream);
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					} finally {
						if (wb != null) {
							try {
								wb.close();
							} catch (IOException e) {
							}
						}
					}

					TableColumn column = null;
					XSSFSheet sheet = wb.getSheetAt(0);
					List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
					int rows = sheet.getLastRowNum();
					logger.debug("rows num:" + rows);
					for (int rowIndex = sysTable.getStartRowIndex() - 1; rowIndex <= rows; rowIndex++) {
						XSSFRow row = sheet.getRow(rowIndex);
						if (row == null) {
							continue;
						}
						Map<String, Object> dataMap = new HashMap<String, Object>();
						int cells = row.getLastCellNum();
						for (int colIndex = 0; colIndex < cells; colIndex++) {
							XSSFCell cell = row.getCell(colIndex);
							if (cell != null) {
								column = columnMap1.get(colIndex);
								if (column != null) {
									if (StringUtils.equals(column.getType(), "Integer")) {
										try {
											String cellValue = cell.getStringCellValue();
											if (StringUtils.isNotEmpty(cellValue)) {
												if (cellValue.indexOf(".") != -1) {
													cellValue = cellValue.substring(0, cellValue.indexOf("."));
												}
												cellValue = StringTools.replace(cellValue, ",", "");
												dataMap.put(column.getColumnName(), Integer.parseInt(cellValue));
												dataMap.put(column.getColumnName().toLowerCase(),
														Long.parseLong(cellValue));
											}
										} catch (Exception ex) {
											double cellValue = cell.getNumericCellValue();
											dataMap.put(column.getColumnName(), cellValue);
											dataMap.put(column.getColumnName().toLowerCase(), cellValue);
										}
									} else if (StringUtils.equals(column.getType(), "Long")) {
										try {
											String cellValue = cell.getStringCellValue();
											if (StringUtils.isNotEmpty(cellValue)) {
												if (cellValue.indexOf(".") != -1) {
													cellValue = cellValue.substring(0, cellValue.indexOf("."));
												}
												cellValue = StringTools.replace(cellValue, ",", "");
												dataMap.put(column.getColumnName(), Long.parseLong(cellValue));
												dataMap.put(column.getColumnName().toLowerCase(),
														Long.parseLong(cellValue));
											}
										} catch (Exception ex) {
											double cellValue = cell.getNumericCellValue();
											dataMap.put(column.getColumnName(), cellValue);
											dataMap.put(column.getColumnName().toLowerCase(), cellValue);
										}
									} else if (StringUtils.equals(column.getType(), "Double")) {
										try {
											String cellValue = cell.getStringCellValue();
											if (StringUtils.isNotEmpty(cellValue)) {
												cellValue = StringTools.replace(cellValue, ",", "");
												dataMap.put(column.getColumnName(), Double.parseDouble(cellValue));
												dataMap.put(column.getColumnName().toLowerCase(),
														Double.parseDouble(cellValue));
											}
										} catch (Exception ex) {
											double cellValue = cell.getNumericCellValue();
											dataMap.put(column.getColumnName(), cellValue);
											dataMap.put(column.getColumnName().toLowerCase(), cellValue);
										}
									} else if (StringUtils.equals(column.getType(), "Date")) {
										Date cellValue = cell.getDateCellValue();
										dataMap.put(column.getColumnName(), cellValue);
										dataMap.put(column.getColumnName().toLowerCase(), cellValue);
									} else {
										String cellValue = cell.getStringCellValue();
										dataMap.put(column.getColumnName(), cellValue);
										dataMap.put(column.getColumnName().toLowerCase(), cellValue);
									}
								}
							}
						}
						dataList.add(dataMap);
					}

					if (dataList.size() > 0) {
						java.sql.Connection conn = null;
						try {
							conn = DBConnectionFactory.getConnection();
							conn.setAutoCommit(false);
							BatchInsertBean insertBean = new BatchInsertBean();
							insertBean.bulkInsert(conn, sysTable, dataList);
							conn.commit();
							JdbcUtils.close(conn);
						} catch (Exception ex) {
							ex.printStackTrace();
							logger.error(ex);
							throw new RuntimeException(ex);
						} finally {
							JdbcUtils.close(conn);
						}
					}
				}
			}
		}
	}

	public ITableService getTableService() {
		if (tableService == null) {
			tableService = ContextFactory.getBean("tableService");
		}
		return tableService;
	}

}
