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

package com.glaf.matrix.data.sync.bean;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.el.Mvel2ExpressionEvaluator;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.jdbc.BulkInsertBean;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.security.Authentication;
import com.glaf.core.service.EntityService;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.ReflectUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.UUID32;
import com.glaf.matrix.data.sync.TableSyncContext;
import com.glaf.matrix.data.sync.converter.FieldConverter;
import com.glaf.matrix.data.sync.handler.CommonDataHandler;
import com.glaf.matrix.data.sync.handler.DataHandler;
import com.glaf.matrix.data.sync.handler.DataPostprocessor;
import com.glaf.matrix.data.sync.handler.DataPreprocessor;
import com.glaf.matrix.data.sync.model.ColumnMapping;
import com.glaf.matrix.data.sync.model.TableSyncDefinition;
import com.glaf.matrix.data.sync.model.TargetTable;

public class MultiTableSyncBean {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void execute(long databaseId, TableSyncDefinition tableSync) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		EntityService entityService = ContextFactory.getBean("entityService");
		String idPrefix = DateUtils.getNowYearMonthDay() + "/system";
		if (Authentication.getAuthenticatedActorId() != null) {
			idPrefix = DateUtils.getNowYearMonthDay() + "/" + Authentication.getAuthenticatedActorId();
		}
		BulkInsertBean insertBean = new BulkInsertBean();
		AtomicInteger currentId = new AtomicInteger();
		Database database = null;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {

			int nowIdx = entityService.nextId(idPrefix).intValue();
			currentId.set(nowIdx);

			TableSyncContext context = new TableSyncContext();
			context.setCurrentId(currentId.incrementAndGet());

			if (databaseId > 0) {
				database = databaseService.getDatabaseById(databaseId);
			}
			if (database != null) {
				conn = DBConnectionFactory.getConnection(database.getName());
			} else {
				conn = DBConnectionFactory.getConnection();
			}

			TableDefinition tableDefinition = new TableDefinition();
			tableDefinition.setTableName(tableSync.getTable());

			ColumnDefinition column1 = new ColumnDefinition();
			column1.setColumnName("EX_SYNC_KEY_");// 来源表的主键值
			column1.setJavaType("String");
			column1.setLength(500);
			tableDefinition.addColumn(column1);

			ColumnDefinition column2 = new ColumnDefinition();
			column2.setColumnName("EX_SYNC_FLAG_");// 设置同步标记
			column2.setJavaType("String");
			column2.setLength(1);
			tableDefinition.addColumn(column2);

			ColumnDefinition column3 = new ColumnDefinition();
			column3.setColumnName("EX_SYNC_TIME_");// 设置同步时间
			column3.setJavaType("Date");
			tableDefinition.addColumn(column3);

			conn.setAutoCommit(false);
			DBUtils.alterTable(conn, tableDefinition);
			conn.commit();

			if (tableSync.getTargetTables() != null && !tableSync.getTargetTables().isEmpty()) {
				for (TargetTable targetTable : tableSync.getTargetTables()) {
					TableDefinition tableDefinition2 = new TableDefinition();
					tableDefinition2.setTableName(targetTable.getTable());

					ColumnDefinition column21 = new ColumnDefinition();
					column21.setColumnName("EX_SYNC_KEY_");// 来源表的主键值
					column21.setJavaType("String");
					column21.setLength(500);
					tableDefinition2.addColumn(column21);

					ColumnDefinition column31 = new ColumnDefinition();
					column31.setColumnName("EX_SYNC_TIME_");// 设置同步时间
					column31.setJavaType("Date");
					tableDefinition2.addColumn(column31);

					DBUtils.alterTable(conn, tableDefinition2);
					conn.commit();

					if (targetTable.getChildren() != null && !targetTable.getChildren().isEmpty()) {
						for (TargetTable child : targetTable.getChildren()) {
							TableDefinition tableDefinition22 = new TableDefinition();
							tableDefinition22.setTableName(child.getTable());

							ColumnDefinition column221 = new ColumnDefinition();
							column221.setColumnName("EX_SYNC_KEY_");// 来源表的主键值
							column221.setJavaType("String");
							column221.setLength(500);
							tableDefinition22.addColumn(column221);

							ColumnDefinition column231 = new ColumnDefinition();
							column231.setColumnName("EX_SYNC_TIME_");// 设置同步时间
							column231.setJavaType("Date");
							tableDefinition22.addColumn(column231);

							DBUtils.alterTable(conn, tableDefinition22);
							conn.commit();
						}
					}
				}
			}

			FieldConverter converter = null;
			if (StringUtils.isNotEmpty(tableSync.getConverter())) {
				converter = (FieldConverter) ReflectUtils.instantiate(tableSync.getConverter());
				converter.convert(conn, tableSync.getTable(), tableSync.getAttributeMap());
			}

			/**
			 * 从来源表中检索未同步的数据，然后按规则同步到各个目标表中，并修改相应的同步标记
			 */
			QueryHelper helper = new QueryHelper();
			TableDefinition tableDefinition2 = new TableDefinition();
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> insertList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> resultList = helper.getResultList(conn, " select * from " + tableSync.getTable(),
					new HashMap<String, Object>(), 500000);
			Map<String, Map<String, Object>> rootMap = new HashMap<String, Map<String, Object>>();
			if (resultList != null && !resultList.isEmpty()) {
				String primaryKey = tableSync.getPrimaryKey();
				String key = null;
				Object value = null;
				String dataHandler = null;
				String srcTableColumn = null;
				String targetTableColumn = null;
				Map<String, Object> xdataMap = null;
				long ts = System.currentTimeMillis();
				java.util.Date time = new java.util.Date(ts);
				primaryKey = primaryKey.toLowerCase();
				List<ColumnMapping> columns = null;
				List<ColumnDefinition> columnDefinitions = null;

				if (tableSync.getTargetTables() != null && !tableSync.getTargetTables().isEmpty()) {
					for (TargetTable targetTable : tableSync.getTargetTables()) {
						if (StringUtils.isNotEmpty(targetTable.getBeforeSql())) {
							DBUtils.executeSchemaResource(conn, targetTable.getBeforeSql());
						}
						List<String> keys = this.getList(conn, targetTable.getTable(), "EX_SYNC_KEY_");
						insertList.clear();
						dataList.clear();
						for (Map<String, Object> dataMap : resultList) {
							key = ParamUtils.getString(dataMap, primaryKey);
							if (key != null && !keys.contains(key)) {
								insertList.add(dataMap);
							}
						}
						if (insertList.size() > 0) {
							columns = targetTable.getColumns();
							if (columns != null && !columns.isEmpty()) {
								ColumnDefinition idColumn = null;
								List<ColumnDefinition> columns2 = DBUtils.getColumnDefinitions(conn,
										targetTable.getTable());
								if (columns2 != null && !columns.isEmpty()) {
									for (ColumnDefinition column : columns2) {
										if (StringUtils.equalsIgnoreCase(column.getColumnName(), primaryKey)) {
											idColumn = column;
											break;
										}
									}
								}
								for (Map<String, Object> dataMap : insertList) {
									Map<String, Object> rowMap = new HashMap<String, Object>();
									rowMap.putAll(dataMap);
									rowMap.put("EX_SYNC_KEY_", ParamUtils.getString(dataMap, primaryKey));
									rowMap.put("EX_SYNC_TIME_", time);
									rootMap.put(ParamUtils.getString(dataMap, primaryKey), dataMap);
									for (ColumnMapping col : columns) {
										value = null;// 清空变量
										srcTableColumn = col.getSrcTableColumn();
										targetTableColumn = col.getTargetTableColumn();
										if (srcTableColumn != null && srcTableColumn.indexOf(".") != -1) {
											srcTableColumn = srcTableColumn.substring(
													srcTableColumn.lastIndexOf(".") + 1, srcTableColumn.length());
										}
										if (targetTableColumn.indexOf(".") != -1) {
											targetTableColumn = targetTableColumn.substring(
													targetTableColumn.lastIndexOf(".") + 1, targetTableColumn.length());
										}
										if (srcTableColumn != null) {
											value = ParamUtils.getObject(dataMap, srcTableColumn);
										}
										if (value == null) {
											if (StringUtils.isNotEmpty(col.getValueExpression())) {
												value = Mvel2ExpressionEvaluator.evaluate(col.getValueExpression(),
														rowMap);
											} else if (StringUtils.isNotEmpty(col.getInitValue())) {
												value = col.getInitValue();
											}
										}
										rowMap.put(targetTableColumn, value);
										rowMap.put(targetTableColumn.toLowerCase(), value);
										rowMap.put(col.getTargetTableColumn(), value);
										rowMap.put(col.getTargetTableColumn().toLowerCase(), value);
									}
									if (idColumn != null
											&& ParamUtils.getObject(rowMap, idColumn.getColumnName()) == null) {
										switch (idColumn.getJavaType()) {
										case "Long":
											rowMap.put(idColumn.getColumnName(), ++ts);
											rowMap.put(idColumn.getColumnName().toLowerCase(),
													rowMap.get(idColumn.getColumnName()));
											break;
										default:
											if (StringUtils.equals(targetTable.getPrimaryKeyExpression(), "#{uuid}")) {
												rowMap.put(idColumn.getColumnName(), UUID32.getUUID());
												rowMap.put(idColumn.getColumnName().toLowerCase(),
														rowMap.get(idColumn.getColumnName()));
											} else {
												rowMap.put(idColumn.getColumnName(), idPrefix + "-"
														+ StringTools.getDigit8Id(currentId.incrementAndGet()));
												rowMap.put(idColumn.getColumnName().toLowerCase(),
														rowMap.get(idColumn.getColumnName()));
											}
											break;
										}
									}
									// logger.debug("row:" + rowMap);
									// logger.debug("####id=" + rowMap.get(idColumn.getColumnName()));
									dataList.add(rowMap);
								}
							}

							if (dataList.size() > 0) {
								logger.debug("准备写数据到:" + targetTable.getTable());
								columnDefinitions = DBUtils.getColumnDefinitions(conn, targetTable.getTable());
								tableDefinition2.setTableName(targetTable.getTable());
								tableDefinition2.setColumns(columnDefinitions);
								logger.debug(targetTable.getTable() + "插入记录条数:" + dataList.size());
								insertBean.bulkInsert(conn, tableDefinition2, dataList);
								targetTable.setDataList(dataList);

								if (targetTable.getChildren() != null && !targetTable.getChildren().isEmpty()) {
									for (TargetTable child : targetTable.getChildren()) {
										logger.debug("准备写数据到:" + child.getTable());
										if (StringUtils.isNotEmpty(child.getBeforeSql())) {
											DBUtils.executeSchemaResource(conn, child.getBeforeSql());
										}
										dataHandler = child.getDataHandler();
										DataHandler handler = null;
										if (StringUtils.isNotEmpty(dataHandler)) {
											handler = (DataHandler) ReflectUtils.instantiate(dataHandler);
										}
										if (handler == null) {
											handler = new CommonDataHandler();
										}
										columnDefinitions = DBUtils.getColumnDefinitions(conn, child.getTable());
										tableDefinition2.setTableName(child.getTable());
										tableDefinition2.setColumns(columnDefinitions);
										Object initData = null;
										if (StringUtils.isNotEmpty(child.getReprocessor())) {
											DataPreprocessor processor = (DataPreprocessor) ReflectUtils
													.instantiate(child.getReprocessor());
											initData = processor.prepare(conn, child);
										}

										ColumnDefinition idColumn = null;
										List<ColumnDefinition> columns3 = DBUtils.getColumnDefinitions(conn,
												child.getTable());
										if (columns != null && !columns.isEmpty()) {
											for (ColumnDefinition column : columns3) {
												if (StringUtils.equalsIgnoreCase(column.getColumnName(), primaryKey)) {
													idColumn = column;
													break;
												}
											}
										}
										int index = 0;
										for (Map<String, Object> masterData : dataList) {
											key = ParamUtils.getString(masterData, "EX_SYNC_KEY_");
											xdataMap = rootMap.get(key);
											logger.debug("xdataMap:" + xdataMap);
											if (xdataMap != null && !xdataMap.isEmpty()) {
												Set<Entry<String, Object>> entrySet = xdataMap.entrySet();
												for (Entry<String, Object> entry : entrySet) {
													String key2 = entry.getKey();
													Object val2 = entry.getValue();
													if (ParamUtils.getObject(masterData, key2) == null) {
														masterData.put(key2, val2);
													}
													if (key2.indexOf(".") != -1) {
														key2 = key2.substring(key2.lastIndexOf(".") + 1, key2.length());
														if (ParamUtils.getObject(masterData, key2) == null) {
															masterData.put(key2, val2);
														}
													}
												}
											}
											masterData.put("EX_SYNC_KEY_",
													ParamUtils.getString(masterData, child.getPrimaryKey()));
											masterData.put("EX_SYNC_TIME_", time);

											context.setIdColumn(idColumn);
											context.setColumns(columns3);
											context.setInitData(initData);
											context.setMasterData(masterData);
											context.setTargetTable(child);

											handler.process(conn, context);
											logger.debug((index++) + "->");
											if (context.getProcessedDataList() != null
													&& !context.getProcessedDataList().isEmpty()) {
												logger.debug(child.getTable() + "插入记录条数:"
														+ context.getProcessedDataList().size());
												insertBean.bulkInsert(conn, tableDefinition2,
														context.getProcessedDataList());
												child.setDataList(context.getProcessedDataList());
											}

											if (child.getAfterDynamicSqls() != null
													&& !child.getAfterDynamicSqls().isEmpty()) {
												SqlExecutor sqlExecutor = null;
												PreparedStatement psmt2 = null;
												List<Object> values = null;
												for (String dynamicSql : child.getAfterDynamicSqls()) {
													try {
														sqlExecutor = DBUtils.replaceSQL(dynamicSql, masterData);
														psmt2 = conn.prepareStatement(sqlExecutor.getSql());
														if (values != null && !values.isEmpty()) {
															JdbcUtils.fillStatement(psmt2, values);
														}
														psmt2.executeUpdate();
														JdbcUtils.close(psmt2);
													} finally {
														JdbcUtils.close(psmt2);
													}
												}
											}
										}

										if (StringUtils.isNotEmpty(child.getAfterSql())) {
											DBUtils.executeSchemaResource(conn, child.getAfterSql());
										}

										if (StringUtils.isNotEmpty(child.getPostprocessor())) {
											DataPostprocessor processor = (DataPostprocessor) ReflectUtils
													.instantiate(child.getPostprocessor());
											processor.after(conn, child);
										}
									}
								}
							}
						}
						
						if (StringUtils.isNotEmpty(targetTable.getAfterSql())) {
							DBUtils.executeSchemaResource(conn, targetTable.getAfterSql());
						}

						if (StringUtils.isNotEmpty(targetTable.getPostprocessor())) {
							DataPostprocessor processor = (DataPostprocessor) ReflectUtils
									.instantiate(targetTable.getPostprocessor());
							processor.after(conn, targetTable);
						}
					}
					conn.commit();
				}
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			logger.error("execute sync error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
			JdbcUtils.close(conn);
			if (currentId.get() > 0) {
				entityService.updateNextId(idPrefix, currentId.get());
			}
		}

	}

	public List<String> getList(Connection conn, String tableName, String columnName) {
		List<String> list = new ArrayList<String>();
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			psmt = conn.prepareStatement(" select " + columnName + " from " + tableName);
			rs = psmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			logger.error("execute query error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
			JdbcUtils.close(rs);
		}
		return list;
	}

}
