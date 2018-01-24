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

package com.glaf.matrix.combination.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.base.LowerLinkedMap;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.BulkInsertBean;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.UUID32;
import com.glaf.matrix.combination.domain.SqlToTableApp;
import com.glaf.matrix.combination.domain.SqlToTableItem;
import com.glaf.matrix.combination.service.SqlToTableAppService;
import com.glaf.matrix.data.domain.TableColumn;
import com.glaf.matrix.data.query.TableColumnQuery;
import com.glaf.matrix.data.service.ITableService;
import com.glaf.matrix.util.SysParams;

public class SqlToTableBean {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public boolean execute(long syncId, Map<String, Object> params) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		ITableService tableService = ContextFactory.getBean("tableService");
		SqlToTableAppService sqlToTableAppService = ContextFactory
				.getBean("com.glaf.matrix.combination.service.sqlToTableAppService");
		SqlToTableApp sqlToTableApp = sqlToTableAppService.getSqlToTableApp(syncId);
		if (sqlToTableApp == null || !StringUtils.equals(sqlToTableApp.getActive(), "Y")) {
			return false;
		}
		QueryHelper helper = new QueryHelper();

		Map<String, Object> parameter = new HashMap<String, Object>();
		if (params != null && !params.isEmpty()) {
			parameter.putAll(params);
		}

		Map<String, Map<String, Object>> dataListMap = new HashMap<String, Map<String, Object>>();
		List<Map<String, Object>> insertList = new ArrayList<Map<String, Object>>();

		SysParams.putInternalParams(parameter);
		Database srcDatabase = null;
		Database targetDatabase = null;
		Connection srcConn = null;
		Connection targetConn = null;
		PreparedStatement srcPsmt = null;
		PreparedStatement psmt = null;
		ResultSet srcRs = null;
		ResultSet rs = null;
		try {

			targetDatabase = databaseService.getDatabaseById(sqlToTableApp.getTargetDatabaseId());

			if (targetDatabase != null) {
				targetConn = DBConnectionFactory.getConnection(targetDatabase.getName());
				logger.debug("targetConn:" + targetConn);
			} else {
				targetConn = DBConnectionFactory.getConnection();
			}

			if (targetConn != null) {
				List<ColumnDefinition> columns = null;
				boolean tableExists = false;
				if (DBUtils.tableExists(targetConn, sqlToTableApp.getTargetTableName())) {
					tableExists = true;
					columns = DBUtils.getColumnDefinitions(targetConn, sqlToTableApp.getTargetTableName());
				} else {
					columns = new ArrayList<ColumnDefinition>();
				}
				JdbcUtils.close(targetConn);

				Set<String> columnNames = new HashSet<String>();
				for (ColumnDefinition col : columns) {
					columnNames.add(col.getColumnName().trim().toLowerCase());
				}

				/**
				 * 优先使用外部定义的列信息
				 */
				if (StringUtils.equals(sqlToTableApp.getExternalColumnsFlag(), "Y")) {
					TableColumnQuery query = new TableColumnQuery();
					query.tableId("sqltotable_app_" + syncId);
					List<TableColumn> tblColumns = tableService.getTableColumns(query);
					if (tblColumns != null && !tblColumns.isEmpty()) {
						Map<String, ColumnDefinition> colMap = new HashMap<String, ColumnDefinition>();
						for (ColumnDefinition column : columns) {
							colMap.put(column.getColumnName().trim().toLowerCase(), column);
						}
						for (TableColumn tblCol : tblColumns) {
							ColumnDefinition column = colMap.get(tblCol.getColumnName().trim().toLowerCase());
							if (column != null) {
								column.setJavaType(tblCol.getJavaType());
								column.setLength(tblCol.getLength());
							} else {
								column = new ColumnDefinition();
								column.setColumnName(tblCol.getColumnName());
								column.setJavaType(tblCol.getJavaType());
								column.setLength(tblCol.getLength());
								columns.add(column);
								columnNames.add(column.getColumnName().trim().toLowerCase());
								logger.debug(tblCol.getColumnName() + " length:" + column.getLength());
							}
						}
					}
				}

				TableDefinition tableDefinition = new TableDefinition();
				tableDefinition.setTableName(sqlToTableApp.getTargetTableName());
				tableDefinition.setColumns(columns);

				ColumnDefinition idColumn = new ColumnDefinition();
				idColumn.setColumnName("EX_ID_");// 主键
				idColumn.setJavaType("String");
				idColumn.setLength(200);
				tableDefinition.setIdColumn(idColumn);

				ColumnDefinition syncIdColumn = new ColumnDefinition();
				syncIdColumn.setColumnName("EX_SYNCID_");// syncId
				syncIdColumn.setJavaType("Long");
				tableDefinition.addColumn(syncIdColumn);

				ColumnDefinition databaseIdColumn = new ColumnDefinition();
				databaseIdColumn.setColumnName("EX_DATABASEID_");// databaseId
				databaseIdColumn.setJavaType("Long");
				tableDefinition.addColumn(databaseIdColumn);

				List<ColumnDefinition> cols = null;
				List<SqlToTableItem> items = sqlToTableApp.getItems();
				for (SqlToTableItem item : items) {
					if (item.getLocked() == 1) {
						continue;
					}
					String sql = item.getSql();
					if (!DBUtils.isLegalQuerySql(sql)) {
						throw new RuntimeException(" SQL statement illegal ");
					}
					sql = QueryUtils.replaceSQLVars(sql, parameter);
					List<Long> databaseIds = StringTools.splitToLong(sqlToTableApp.getSrcDatabaseIds());
					for (Long databaseId : databaseIds) {
						srcDatabase = databaseService.getDatabaseById(databaseId);
						if (srcDatabase != null) {
							srcConn = DBConnectionFactory.getConnection(srcDatabase.getName());
							if (StringUtils.isNotEmpty(item.getRecursionSql())) {
								if (!DBUtils.isLegalQuerySql(item.getRecursionSql())) {
									throw new RuntimeException(" SQL statement illegal ");
								}
								List<String> recursionKeys = StringTools.splitLowerCase(item.getRecursionColumns(),
										",");
								if (recursionKeys != null && !recursionKeys.isEmpty()) {
									cols = helper.getColumnList(srcConn, item.getRecursionSql(), parameter);
									if (cols != null && !cols.isEmpty()) {
										for (ColumnDefinition col : cols) {
											if (!columnNames.contains(col.getColumnName().toLowerCase())) {
												if (recursionKeys.contains(col.getColumnName().toLowerCase())) {
													columns.add(col);
													columnNames.add(col.getColumnName().trim().toLowerCase());
												}
											}
										}
									}
								}
							}
							cols = helper.getColumnList(srcConn, sql, parameter);
							if (cols != null && !cols.isEmpty()) {
								for (ColumnDefinition col : cols) {
									if (!columnNames.contains(col.getColumnName().toLowerCase())) {
										columns.add(col);
										columnNames.add(col.getColumnName().trim().toLowerCase());
									}
								}
							}
							JdbcUtils.close(srcConn);
						}
					}
				}

				if (targetDatabase != null) {
					targetConn = DBConnectionFactory.getConnection(targetDatabase.getName());
					logger.debug("targetConn:" + targetConn);
				} else {
					targetConn = DBConnectionFactory.getConnection();
				}
				targetConn.setAutoCommit(false);
				if (!tableExists) {
					DBUtils.createTable(targetConn, tableDefinition);
					for (ColumnDefinition col : cols) {
						TableColumn tbCol = new TableColumn();
						tbCol.setTitle(col.getTitle());
						tbCol.setColumnName(col.getColumnName());
						tbCol.setJavaType(col.getJavaType());
						tbCol.setLength(col.getLength());
						tbCol.setTableId("sqltotable_app_" + syncId);
						tbCol.setId("sqltotable_app_" + syncId + "_" + col.getColumnName());
						tableService.saveTableColumn("sqltotable_app_" + syncId, tbCol);
					}
				} else {
					DBUtils.alterTable(targetConn, tableDefinition);
					for (ColumnDefinition col : cols) {
						TableColumn tbCol = new TableColumn();
						tbCol.setTitle(col.getTitle());
						tbCol.setColumnName(col.getColumnName());
						tbCol.setJavaType(col.getJavaType());
						tbCol.setLength(col.getLength());
						tbCol.setTableId("sqltotable_app_" + syncId);
						tbCol.setId("sqltotable_app_" + syncId + "_" + col.getColumnName());
						tableService.saveTableColumn("sqltotable_app_" + syncId, tbCol);
					}
				}
				targetConn.commit();
				JdbcUtils.close(targetConn);

				logger.debug("columns:" + columns);

				int totalSize = 0;
				Object value = null;
				String idValue = null;
				StringBuilder keyBuffer = new StringBuilder();
				List<Map<String, Object>> sourceResultList = null;
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());

				for (SqlToTableItem item : items) {
					if (item.getLocked() == 1) {
						continue;
					}

					String sql = item.getSql();
					sql = QueryUtils.replaceSQLVars(sql, parameter);
					List<String> recursionKeys = StringTools.splitLowerCase(item.getRecursionColumns(), ",");
					boolean recursionKeyExists = false;
					if (recursionKeys != null && !recursionKeys.isEmpty()) {
						recursionKeyExists = true;
					}

					if (recursionKeyExists && StringUtils.isNotEmpty(item.getRecursionSql())) {
						List<Long> databaseIds = StringTools.splitToLong(sqlToTableApp.getSrcDatabaseIds());
						for (Long databaseId : databaseIds) {
							srcDatabase = databaseService.getDatabaseById(databaseId);
							if (srcDatabase != null) {
								srcConn = DBConnectionFactory.getConnection(srcDatabase.getName());
								List<Map<String, Object>> recursionResultList = helper.getResultList(srcConn,
										item.getRecursionSql(), parameter, 5000);

								if (recursionResultList != null && !recursionResultList.isEmpty()) {
									LowerLinkedMap params2 = new LowerLinkedMap();
									final List<String> keys = StringTools.splitLowerCase(item.getPrimaryKey(), ",");

									int index = 0;
									logger.debug("recursionKeys:" + recursionKeys);
									for (Map<String, Object> paramMap : recursionResultList) {
										params2.clear();// 清空条件
										params2.putAll(parameter);// 放入外部输入条件
										params2.putAll(paramMap);// 放入循环输入条件
										// logger.debug(" paramMap:" + paramMap);
										index++;
										// logger.debug(index + " ->params:" + paramMap);

										/**
										 * 通过新的参数获取每次循环的结果集
										 */
										sourceResultList = helper.getResultList(srcConn, sql, params2,
												SysParams.MAX_SIZE);

										if (sourceResultList != null && !sourceResultList.isEmpty()) {
											for (Map<String, Object> rowMap : sourceResultList) {
												Map<String, Object> dataMap2 = new LowerLinkedMap();
												dataMap2.putAll(rowMap);

												keyBuffer.delete(0, keyBuffer.length());

												if (StringUtils.equals(sqlToTableApp.getUniqueFlag(), "S")) {
													keyBuffer.append(databaseId).append("_");
												}

												for (String key : keys) {
													value = ParamUtils.getObject(dataMap2, key);
													if (value == null) {
														value = ParamUtils.getObject(params2, key);
													}
													if (value != null) {
														if (value instanceof Date) {
															keyBuffer.append(formatter.format((Date) value))
																	.append("_");
														} else {
															keyBuffer.append(value).append("_");
														}
													} else {
														keyBuffer.append("null").append("_");
													}
												}

												keyBuffer.delete(keyBuffer.length() - 1, keyBuffer.length());

												idValue = DigestUtils.md5Hex(keyBuffer.toString());
												// idValue = keyBuffer.toString();
												if (idValue != null) {
													Map<String, Object> dataMap = dataListMap.get(idValue);
													if (dataMap == null) {
														dataMap = new LowerLinkedMap();
														dataMap.put("ex_id_", idValue);
														dataMap.put("ex_syncid_", syncId);
														dataMap.put("ex_databaseid_", databaseId);
														if (recursionKeyExists) {
															for (String recursionKey : recursionKeys) {
																dataMap.put(recursionKey,
																		ParamUtils.getObject(paramMap, recursionKey));
																if (index == 1) {

																}
															}
														}
													}
													dataMap.putAll(dataMap2);
													dataListMap.put(idValue, dataMap);
												}
											}
											totalSize = dataListMap.size();
											if (totalSize > SysParams.MAX_SIZE) {
												logger.warn("totalSize:" + totalSize);
												throw new RuntimeException(
														"data list too large, please split small condition.");
											}
										}
									}
								}
								JdbcUtils.close(srcConn);
							}
						}
					} else {
						List<Long> databaseIds = StringTools.splitToLong(sqlToTableApp.getSrcDatabaseIds());
						for (Long databaseId : databaseIds) {
							srcDatabase = databaseService.getDatabaseById(databaseId);
							if (srcDatabase != null) {
								srcConn = DBConnectionFactory.getConnection(srcDatabase.getName());
								sourceResultList = helper.getResultList(srcConn, sql, parameter, SysParams.MAX_SIZE);
								if (sourceResultList != null && !sourceResultList.isEmpty()) {
									List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
									for (Map<String, Object> rowMap : sourceResultList) {
										LowerLinkedMap dataMap2 = new LowerLinkedMap();
										dataMap2.putAll(rowMap);
										dataList.add(dataMap2);
									}
									sourceResultList.clear();

									List<String> keys = StringTools.splitLowerCase(item.getPrimaryKey(), ",");
									if (keys != null && !keys.isEmpty()) {
										for (Map<String, Object> rowMap : dataList) {
											keyBuffer.delete(0, keyBuffer.length());
											if (StringUtils.equals(sqlToTableApp.getUniqueFlag(), "S")) {
												keyBuffer.append(databaseId).append("_");
											}
											for (String key : keys) {
												value = rowMap.get(key);
												if (value == null) {
													value = ParamUtils.getObject(parameter, key);
												}
												if (value != null) {
													if (value instanceof Date) {
														keyBuffer.append(formatter.format((Date) value)).append("_");
													} else {
														keyBuffer.append(value).append("_");
													}
												} else {
													keyBuffer.append("null").append("_");
												}
											}
											keyBuffer.delete(keyBuffer.length() - 1, keyBuffer.length());

											idValue = DigestUtils.md5Hex(keyBuffer.toString());
											// idValue = keyBuffer.toString();
											if (idValue != null) {
												Map<String, Object> dataMap = dataListMap.get(idValue);
												if (dataMap == null) {
													dataMap = new LowerLinkedMap();
													dataMap.put("ex_id_", idValue);
													dataMap.put("ex_syncid_", syncId);
													dataMap.put("ex_databaseid_", databaseId);
												}
												dataMap.putAll(rowMap);
												dataListMap.put(idValue, dataMap);
											}
										}
									} else {
										for (Map<String, Object> rowMap : dataList) {
											String uuid = UUID32.getUUID();
											rowMap.put("ex_id_", uuid);
											rowMap.put("ex_syncid_", syncId);
											rowMap.put("ex_databaseid_", databaseId);
											dataListMap.put(uuid, rowMap);
										}
									}
								}
							}
						}
					}
					totalSize = dataListMap.size();
					if (totalSize > SysParams.MAX_SIZE) {
						logger.warn("totalSize:" + totalSize);
						throw new RuntimeException("data list too large, please split small condition.");
					}
				}

				insertList.addAll(dataListMap.values());

				if (insertList.size() > 0) {
					if (targetDatabase != null) {
						targetConn = DBConnectionFactory.getConnection(targetDatabase.getName());
						logger.debug("targetConn:" + targetConn);
					} else {
						targetConn = DBConnectionFactory.getConnection();
					}
					BulkInsertBean insertBean = new BulkInsertBean();
					targetConn.setAutoCommit(false);
					DBUtils.executeSchemaResource(targetConn, " delete from " + sqlToTableApp.getTargetTableName()
							+ " where ex_syncid_ = " + sqlToTableApp.getId());
					insertBean.bulkInsert(targetConn, tableDefinition, insertList);
					targetConn.commit();
				}

				insertList.clear();
				dataListMap.clear();
				insertList = null;
				dataListMap = null;

			}

			return true;
		} catch (Exception ex) {
			// ex.printStackTrace();
			logger.error("execute sync error", ex);
			// logger.error("[insertList]:" + insertList);
			throw new RuntimeException(ex);
		} finally {

			insertList = null;
			dataListMap = null;

			JdbcUtils.close(srcRs);
			JdbcUtils.close(srcPsmt);
			JdbcUtils.close(srcConn);

			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
			JdbcUtils.close(targetConn);
		}
	}

}
