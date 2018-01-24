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

package com.glaf.datamgr.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.factory.TableFactory;
import com.glaf.core.jdbc.BulkInsertBean;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.security.Authentication;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.UUID32;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.TableCombination;
import com.glaf.datamgr.domain.TableCombinationColumn;
import com.glaf.datamgr.domain.TableCombinationRule;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.jdbc.MyBatisHelper;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.service.TableCombinationService;
import com.glaf.datamgr.util.ExceptionUtils;
import com.glaf.datamgr.util.FastJsonUtils;

/**
 * 目标表与模板表相同的表组合功能
 *
 */
public class TableCombinationSimilarBean implements java.lang.Runnable {

	protected static Configuration conf = BaseConfiguration.create();

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected long sourceDatabaseId;

	protected long targetDatabaseId;

	protected long tableCombinationId;

	protected boolean result;

	public TableCombinationSimilarBean() {

	}

	public TableCombinationSimilarBean(long sourceDatabaseId, long targetDatabaseId, long tableCombinationId) {
		this.sourceDatabaseId = sourceDatabaseId;
		this.targetDatabaseId = targetDatabaseId;
		this.tableCombinationId = tableCombinationId;
	}

	public boolean execute(long sourceDatabaseId, long targetDatabaseId, long tableCombinationId) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		TableCombinationService tableCombinationService = ContextFactory.getBean("tableCombinationService");
		try {
			Database database = databaseService.getDatabaseById(sourceDatabaseId);
			Database targetDatabase = databaseService.getDatabaseById(targetDatabaseId);
			if (targetDatabase == null) {
				targetDatabase = database;
			}
			TableCombination tableCombination = tableCombinationService.getTableCombination(tableCombinationId);
			if (tableCombination != null) {
				List<ColumnDefinition> sourceColumns = DBUtils.getColumnDefinitions(database.getName(),
						tableCombination.getTemplateTableName());
				Map<String, ColumnDefinition> columnMap = new HashMap<String, ColumnDefinition>();
				for (ColumnDefinition col : sourceColumns) {
					columnMap.put(col.getColumnName().toLowerCase(), col);
				}
				TableDefinition tableDefinition = new TableDefinition();
				tableDefinition.setTableName(tableCombination.getTargetTableName());
				tableDefinition.setColumns(sourceColumns);
				List<ColumnDefinition> columns = tableDefinition.getColumns();

				if (DBUtils.ORACLE.equals(targetDatabase.getType())) {

					ColumnDefinition col2 = new ColumnDefinition();
					col2.setColumnName("ORCL_DATABASEID_");
					col2.setJavaType("Long");
					columns.add(col2);

					ColumnDefinition col22 = new ColumnDefinition();
					col22.setColumnName("ORCL_NAME_");
					col22.setJavaType("String");
					col22.setLength(500);
					columns.add(col22);

					ColumnDefinition col23 = new ColumnDefinition();
					col23.setColumnName("ORCL_MAPPING_");
					col23.setJavaType("String");
					col23.setLength(200);
					columns.add(col23);

					ColumnDefinition col24 = new ColumnDefinition();
					col24.setColumnName("ORCL_SECTION_");
					col24.setJavaType("String");
					col24.setLength(200);
					columns.add(col24);

					ColumnDefinition col5 = new ColumnDefinition();
					col5.setColumnName("ORCL_TABLE_");
					col5.setJavaType("String");
					col5.setLength(80);
					columns.add(col5);

					ColumnDefinition col6 = new ColumnDefinition();
					col6.setColumnName("ORCL_DATASETID_");
					col6.setJavaType("String");
					col6.setLength(50);
					columns.add(col6);

					ColumnDefinition col7 = new ColumnDefinition();
					col7.setColumnName("ORCL_COMPLEXKEY_");
					col7.setJavaType("String");
					col7.setLength(250);
					columns.add(col7);

					ColumnDefinition col8 = new ColumnDefinition();
					col8.setColumnName("ORCL_UNIQUENESSKEY_");
					col8.setJavaType("String");
					col8.setLength(250);
					columns.add(col8);

					ColumnDefinition col9 = new ColumnDefinition();
					col9.setColumnName("ORCL_CURRENT_USER_");
					col9.setJavaType("String");
					col9.setLength(50);
					columns.add(col9);

					ColumnDefinition col10 = new ColumnDefinition();
					col10.setColumnName("ORCL_REF_KEY_");
					col10.setJavaType("String");
					col10.setLength(500);
					columns.add(col10);
				} else {
					ColumnDefinition col2 = new ColumnDefinition();
					col2.setColumnName("_DATABASEID_");
					col2.setJavaType("Long");
					columns.add(col2);

					ColumnDefinition col22 = new ColumnDefinition();
					col22.setColumnName("_NAME_");
					col22.setJavaType("String");
					col22.setLength(500);
					columns.add(col22);

					ColumnDefinition col23 = new ColumnDefinition();
					col23.setColumnName("_MAPPING_");
					col23.setJavaType("String");
					col23.setLength(200);
					columns.add(col23);

					ColumnDefinition col24 = new ColumnDefinition();
					col24.setColumnName("_SECTION_");
					col24.setJavaType("String");
					col24.setLength(200);
					columns.add(col24);

					ColumnDefinition col5 = new ColumnDefinition();
					col5.setColumnName("_TABLE_");
					col5.setJavaType("String");
					col5.setLength(80);
					columns.add(col5);

					ColumnDefinition col6 = new ColumnDefinition();
					col6.setColumnName("_DATASETID_");
					col6.setJavaType("String");
					col6.setLength(50);
					columns.add(col6);

					ColumnDefinition col7 = new ColumnDefinition();
					col7.setColumnName("_COMPLEXKEY_");
					col7.setJavaType("String");
					col7.setLength(250);
					columns.add(col7);

					ColumnDefinition col8 = new ColumnDefinition();
					col8.setColumnName("_UNIQUENESSKEY_");
					col8.setJavaType("String");
					col8.setLength(250);
					columns.add(col8);

					ColumnDefinition col9 = new ColumnDefinition();
					col9.setColumnName("_CURRENT_USER_");
					col9.setJavaType("String");
					col9.setLength(50);
					columns.add(col9);

					ColumnDefinition col10 = new ColumnDefinition();
					col10.setColumnName("_REF_KEY_");
					col10.setJavaType("String");
					col10.setLength(500);
					columns.add(col10);
				}

				if (DBUtils.tableExists(targetDatabase.getName(), tableCombination.getTargetTableName())) {
					DBUtils.alterTable(targetDatabase.getName(), tableDefinition);
				} else {
					DBUtils.createTable(targetDatabase.getName(), tableDefinition);
				}

				TableFactory.clearCache("default", tableCombination.getTemplateTableName());
				TableFactory.clearCache(targetDatabase.getName(), tableCombination.getTemplateTableName());

				TableFactory.clearCache("default", tableCombination.getTargetTableName());
				TableFactory.clearCache(targetDatabase.getName(), tableCombination.getTargetTableName());
				return this.execute(sourceDatabaseId, targetDatabaseId, tableCombination);
			}

			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("execute error", ex);
			throw new RuntimeException(ex);
		}
	}

	public boolean execute(long sourceDatabaseId, long targetDatabaseId, TableCombination tableCombination) {
		if (StringUtils.equalsIgnoreCase(tableCombination.getTargetTableName(), "userinfo")) {
			return false;
		}
		if (StringUtils.equalsIgnoreCase(tableCombination.getTargetTableName(), "userrole")) {
			return false;
		}
		if (StringUtils.startsWithIgnoreCase(tableCombination.getTargetTableName(), "sys_")) {
			return false;
		}
		if (StringUtils.startsWithIgnoreCase(tableCombination.getTargetTableName(), "act_")) {
			return false;
		}
		if (StringUtils.startsWithIgnoreCase(tableCombination.getTargetTableName(), "jbpm_")) {
			return false;
		}

		if (StringUtils.startsWithIgnoreCase(tableCombination.getTargetTableName(), "form_")) {
			if (!StringUtils.equalsIgnoreCase(tableCombination.getTargetTableName(), "FORM_ATTACHMENT")) {
				return false;
			}
		}

		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		Database database = databaseService.getDatabaseById(sourceDatabaseId);
		Database targetDatabase = databaseService.getDatabaseById(targetDatabaseId);
		logger.debug("----------------------------------------------------------------");
		logger.debug("-----------------TableCombinationSimilarBean execute------------");
		logger.debug("----------------------------------------------------------------");

		List<String> syncColumns = StringTools.splitLowerCase(tableCombination.getSyncColumns());
		Collections.sort(syncColumns);// 排序，确保修改字段顺序时不影响结果
		logger.debug("sync columns:" + syncColumns);

		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("_date_", now);
		parameter.put("_now_", now);
		parameter.put("_year_", year);
		parameter.put("_month_", month);
		parameter.put("_week_", week);
		parameter.put("_day_", day);
		parameter.put("_nowYearMonth_", DateUtils.getNowYearMonth());
		parameter.put("_nowYearMonthDay_", DateUtils.getNowYearMonthDay());

		StringBuffer buffer = new StringBuffer();
		MyBatisHelper helper = new MyBatisHelper();
		BulkInsertBean bulkInsertBean = new BulkInsertBean();
		List<String> rowIds = new ArrayList<String>();
		List<String> refKeys = new ArrayList<String>();
		List<String> uniquenessKeys = new ArrayList<String>();
		List<Map<String, Object>> resultList = null;
		List<Map<String, Object>> insertList = new ArrayList<Map<String, Object>>();

		long ts = System.currentTimeMillis();
		int maxVal = Integer.MAX_VALUE;

		Connection sourceConn = null;
		PreparedStatement sourcePsmt = null;
		ResultSet sourceRS = null;

		Connection targetConn = null;
		PreparedStatement targetPsmt = null;
		ResultSet targetRS = null;
		try {
			if (targetDatabase == null) {
				targetDatabase = database;
			}
			sourceConn = DBConnectionFactory.getConnection(database.getName());

			targetConn = DBConnectionFactory.getConnection(targetDatabase.getName());

			List<ColumnDefinition> sourceColumns = DBUtils.getColumnDefinitions(sourceConn,
					tableCombination.getTemplateTableName());
			List<ColumnDefinition> targetColumns = DBUtils.getColumnDefinitions(targetConn,
					tableCombination.getTargetTableName());

			TableDefinition tableDefinition = new TableDefinition();
			tableDefinition.setTableName(tableCombination.getTargetTableName());
			tableDefinition.setColumns(targetColumns);

			ColumnDefinition idColumn = null;

			for (ColumnDefinition column : targetColumns) {
				logger.debug(column.getColumnName() + "\t" + column.getJavaType());
				if (StringUtils.equalsIgnoreCase(column.getJavaType(), "Blob")) {
					logger.debug(column.getColumnName() + " is blob");
				}
				if (column.isPrimaryKey()) {
					idColumn = column;
				}
			}

			List<ColumnDefinition> selectedColumns = new ArrayList<ColumnDefinition>();
			for (String syncColumn : syncColumns) {
				for (ColumnDefinition column : sourceColumns) {
					if (StringUtils.equalsIgnoreCase(syncColumn, column.getColumnName())) {
						selectedColumns.add(column);
					}
				}
			}

			int total = 0;

			/**
			 * 判断是否每次执行前删除
			 */
			if (StringUtils.equals(tableCombination.getDeleteFetch(), "Y")) {
				String sql = " delete from " + tableCombination.getTargetTableName() + " where _DATABASEID_ = "
						+ database.getId();

				if (DBUtils.ORACLE.equals(targetDatabase.getType())) {
					sql = " delete from " + tableCombination.getTargetTableName() + " where ORCL_DATABASEID_ = "
							+ database.getId();
				}
				if (StringUtils.equals(tableCombination.getCurrentUserFlag(), "Y")) {
					if (DBUtils.ORACLE.equals(targetDatabase.getType())) {
						sql = sql + " and ORCL_CURRENT_USER_ = '" + Authentication.getAuthenticatedActorId() + "' ";
					} else {
						sql = sql + " and _CURRENT_USER_ = '" + Authentication.getAuthenticatedActorId() + "' ";
					}
				}

				logger.debug("execute delete sql:" + sql);

				targetConn.setAutoCommit(false);
				DBUtils.executeSchemaResourceIgnoreException(targetConn, sql);
				targetConn.commit();
			}

			StringBuffer sqlBuffer = new StringBuffer();

			if (StringUtils.equals(tableCombination.getCurrentUserFlag(), "Y")) {
				if (DBUtils.ORACLE.equals(targetDatabase.getType())) {
					sqlBuffer.append(" select orcl_complexkey_, orcl_uniquenesskey_, orcl_ref_key_ ");
					if (StringUtils.isNotEmpty(tableCombination.getUniquenessKey())) {
						sqlBuffer.append(", ").append(tableCombination.getUniquenessKey());
					}
					sqlBuffer.append(" from ").append(tableCombination.getTargetTableName())
							.append(" where ORCL_CURRENT_USER_ = '").append(Authentication.getAuthenticatedActorId())
							.append("' ");
					targetPsmt = targetConn.prepareStatement(sqlBuffer.toString());
				} else {
					sqlBuffer.append(" select _complexkey_, _uniquenesskey_, _ref_key_ ");
					if (StringUtils.isNotEmpty(tableCombination.getUniquenessKey())) {
						sqlBuffer.append(", ").append(tableCombination.getUniquenessKey());
					}
					sqlBuffer.append(" from ").append(tableCombination.getTargetTableName())
							.append(" where _CURRENT_USER_ = '").append(Authentication.getAuthenticatedActorId())
							.append("' ");
					targetPsmt = targetConn.prepareStatement(sqlBuffer.toString());
				}
			} else {
				if (DBUtils.ORACLE.equals(targetDatabase.getType())) {
					sqlBuffer.append(" select orcl_complexkey_, orcl_uniquenesskey_, orcl_ref_key_ ");
					if (StringUtils.isNotEmpty(tableCombination.getUniquenessKey())) {
						sqlBuffer.append(", ").append(tableCombination.getUniquenessKey());
					}
					sqlBuffer.append(" from ").append(tableCombination.getTargetTableName());
					targetPsmt = targetConn.prepareStatement(sqlBuffer.toString());
				} else {
					sqlBuffer.append(" select _complexkey_, _uniquenesskey_, _ref_key_ ");
					if (StringUtils.isNotEmpty(tableCombination.getUniquenessKey())) {
						sqlBuffer.append(", ").append(tableCombination.getUniquenessKey());
					}
					sqlBuffer.append(" from ").append(tableCombination.getTargetTableName());
					targetPsmt = targetConn.prepareStatement(sqlBuffer.toString());
				}
			}

			targetRS = targetPsmt.executeQuery();
			while (targetRS.next()) {
				rowIds.add(targetRS.getString(1));
				uniquenessKeys.add(targetRS.getString(2));
				String key = targetRS.getString(3);
				if (key != null) {
					refKeys.add(key.trim());
				}
				if (StringUtils.isNotEmpty(tableCombination.getUniquenessKey())) {
					Object object = targetRS.getObject(4);
					if (object != null) {
						refKeys.add(object.toString());
					}
				}
			}

			JdbcUtils.close(targetRS);
			JdbcUtils.close(targetPsmt);

			// logger.debug("refKeys:" + refKeys);

			Map<String, String> refKeyMap = new HashMap<String, String>();
			List<TableCombinationRule> rules = tableCombination.getRules();
			List<TableCombinationColumn> columns = tableCombination.getColumns();

			for (TableCombinationRule rule : rules) {
				if (StringUtils.isNotEmpty(rule.getTargetTableName())
						&& StringUtils.isNotEmpty(rule.getUniquenessColumn())) {
					refKeyMap.put(rule.getTargetTableName().toLowerCase(), rule.getUniquenessColumn());
				}
			}

			List<String> tableNames = StringTools.split(tableCombination.getSyncTableNames());
			if (tableNames != null && !tableNames.isEmpty()) {
				List<String> processed = new ArrayList<String>();
				String keyColumn = null;
				for (String tableName : tableNames) {
					if (processed.contains(tableName.toLowerCase())) {
						continue;
					}
					keyColumn = refKeyMap.get(tableName.toLowerCase());
					logger.debug(tableName + "\t" + keyColumn);
					buffer.delete(0, buffer.length());
					buffer.append(" select E.*, ");
					for (TableCombinationColumn column : columns) {
						if (StringUtils.equalsIgnoreCase(column.getTargetTableName(), tableName)) {
							if (StringUtils.isNotEmpty(column.getTargetColumnName())
									&& StringUtils.isNotEmpty(column.getSourceColumnName())) {
								buffer.append("E.").append(column.getTargetColumnName()).append(" as ")
										.append(column.getSourceColumnName()).append(", ");
							}
						}
					}
					if (keyColumn != null) {
						buffer.append("E.").append(keyColumn);
					} else {
						buffer.delete(buffer.length() - 2, buffer.length());
					}
					buffer.append(" from ").append(tableName).append(" E where 1=1 ");
					for (TableCombinationRule rule : rules) {
						if (StringUtils.isNotEmpty(rule.getTargetTableName())
								&& StringUtils.isNotEmpty(rule.getUniquenessColumn())) {
							refKeyMap.put(rule.getTargetTableName(), rule.getUniquenessColumn());
						}
						if (StringUtils.equalsIgnoreCase(rule.getTargetTableName(), tableName)) {
							if (StringUtils.isNotEmpty(rule.getSqlCondition())) {
								buffer.append(" and ").append(rule.getSqlCondition());
							}
						}
					}

					logger.debug("execute query:" + buffer.toString());

					sourcePsmt = sourceConn.prepareStatement(buffer.toString());
					sourceRS = sourcePsmt.executeQuery();
					resultList = helper.getResults(sourceRS);
					JdbcUtils.close(sourceRS);
					JdbcUtils.close(sourcePsmt);

					if (resultList != null && !resultList.isEmpty()) {
						insertList.clear();
						for (Map<String, Object> dataMap : resultList) {
							// logger.debug("dataMap:" + dataMap);
							dataMap.put("_table_", tableName);
							dataMap.put("_databaseid_", database.getId());

							dataMap.put("orcl_table_", tableName);
							dataMap.put("orcl_databaseid_", database.getId());

							if (StringUtils.equals(tableCombination.getCurrentUserFlag(), "Y")) {
								dataMap.put("_current_user_", Authentication.getAuthenticatedActorId());
								dataMap.put("orcl_current_user_", Authentication.getAuthenticatedActorId());
							}

							if (dataMap.get("_name_") == null) {
								dataMap.put("_name_", database.getTitle());
								dataMap.put("orcl_name_", database.getTitle());
							}
							if (dataMap.get("_section_") == null) {
								dataMap.put("_section_", database.getSection());
								dataMap.put("orcl_section_", database.getSection());
							}
							if (dataMap.get("_mapping_") == null) {
								dataMap.put("_mapping_", database.getMapping());
								dataMap.put("orcl_mapping_", database.getMapping());
							}

							if (StringUtils.isNotEmpty(tableCombination.getUniquenessKey())) {
								String key = ParamUtils.getString(dataMap, tableCombination.getUniquenessKey());
								if (!uniquenessKeys.contains(key)) {
									uniquenessKeys.add(key);
									dataMap.put("_uniquenesskey_", key);
									dataMap.put("orcl_uniquenesskey_", key);
									if (keyColumn != null && ParamUtils.getObject(dataMap, keyColumn) != null) {
										dataMap.put("_ref_key_",
												ParamUtils.getObject(dataMap, keyColumn).toString().trim());
										dataMap.put("orcl_ref_key_",
												ParamUtils.getObject(dataMap, keyColumn).toString().trim());
									}
									if (StringUtils.isNotEmpty(tableCombination.getPrimaryKey())) {
										String id = ParamUtils.getString(dataMap, tableCombination.getPrimaryKey());
										id = database.getId() + "__/__" + id;
										if (!rowIds.contains(id)) {
											rowIds.add(id);
											dataMap.put("_complexkey_", id);
											dataMap.put("orcl_complexkey_", id);
											if (keyColumn != null && ParamUtils.getObject(dataMap, keyColumn) != null) {
												/**
												 * 已经存在了就不再插入
												 */
												if (!refKeys.contains(
														ParamUtils.getObject(dataMap, keyColumn).toString().trim())) {
													insertList.add(dataMap);
												}
											} else {
												insertList.add(dataMap);
											}
										}
									} else {
										if (keyColumn != null && ParamUtils.getObject(dataMap, keyColumn) != null) {
											/**
											 * 已经存在了就不再插入
											 */
											if (!refKeys
													.contains(ParamUtils.getObject(dataMap, keyColumn).toString())) {
												insertList.add(dataMap);
											}
										} else {
											insertList.add(dataMap);
										}
									}
								}
							} else {
								if (StringUtils.isNotEmpty(tableCombination.getPrimaryKey())) {
									String id = ParamUtils.getString(dataMap, tableCombination.getPrimaryKey());
									id = database.getId() + "__/__" + id;
									if (!rowIds.contains(id)) {
										rowIds.add(id);
										dataMap.put("_complexkey_", id);
										dataMap.put("orcl_complexkey_", id);
										if (keyColumn != null && ParamUtils.getObject(dataMap, keyColumn) != null) {
											/**
											 * 已经存在了就不再插入
											 */
											if (!refKeys.contains(
													ParamUtils.getObject(dataMap, keyColumn).toString().trim())) {
												insertList.add(dataMap);
											}
										} else {
											insertList.add(dataMap);
										}
									}
								} else {
									if (keyColumn != null && ParamUtils.getObject(dataMap, keyColumn) != null) {
										/**
										 * 已经存在了就不再插入
										 */
										if (!refKeys
												.contains(ParamUtils.getObject(dataMap, keyColumn).toString().trim())) {
											insertList.add(dataMap);
										}
									} else {
										insertList.add(dataMap);
									}
								}
							}
						}

						if (idColumn != null) {
							for (Map<String, Object> dataMap2 : insertList) {
								if (ParamUtils.getObject(dataMap2, idColumn.getColumnName()) == null) {
									switch (idColumn.getJavaType()) {
									case "Integer":
										dataMap2.put(idColumn.getColumnName().toLowerCase(), --maxVal);
										break;
									case "Long":
										dataMap2.put(idColumn.getColumnName().toLowerCase(), ++ts);
										break;
									case "String":
										dataMap2.put(idColumn.getColumnName().toLowerCase(), UUID32.getUUID());
										break;
									default:
										break;
									}
								}
							}
						}

						for (Map<String, Object> dataMap2 : insertList) {
							for (TableCombinationColumn column : columns) {
								if (StringUtils.isNotEmpty(column.getInitValue())) {
									if (ParamUtils.getObject(dataMap2, column.getSourceColumnName()) == null) {
										dataMap2.put(column.getSourceColumnName(), column.getInitValue());
									}
								}
							}
						}

						total = total + insertList.size();
						bulkInsertBean.bulkInsert(targetConn, tableDefinition, insertList);
						// targetConn.commit();
						processed.add(tableName.toLowerCase());
						insertList.clear();
					}
				}
				logger.debug("processed tables:" + processed);
			}

			List<String> datasetIds = StringTools.split(tableCombination.getDatasetIds());
			if (datasetIds != null && !datasetIds.isEmpty()) {
				DataSet dataSet = null;
				DataSetBuilder builder = new DataSetBuilder();
				DataSetService dataSetService = ContextFactory.getBean("dataSetService");
				for (String datasetId : datasetIds) {
					dataSet = dataSetService.getDataSet(datasetId);
					if (dataSet == null) {
						continue;
					}
					if (StringUtils.isNotEmpty(dataSet.getExecuteDatabaseIds())) {
						List<Long> executeDatabaseIds = StringTools.splitToLong(dataSet.getExecuteDatabaseIds());
						if (executeDatabaseIds != null && !executeDatabaseIds.isEmpty()) {
							if (!executeDatabaseIds.contains(database.getId())) {
								continue;
							}
						}
					}
					dataSet.setDatabaseId(database.getId());
					JSONArray array = builder.getJsonArray(database.getName(), dataSet, parameter);
					if (array != null && !array.isEmpty()) {
						insertList.clear();
						int size = array.size();
						logger.debug(dataSet.getName() + " total record size:" + size);
						JSONObject json = null;
						Map<String, Object> dataMap = null;
						for (int i = 0; i < size; i++) {
							json = array.getJSONObject(i);
							json = FastJsonUtils.addLowerCaseKey(json);
							dataMap = new HashMap<String, Object>();
							for (TableCombinationColumn column : columns) {
								if (column.getSourceColumnName() != null
										&& StringUtils.equalsIgnoreCase(column.getDatasetId(), datasetId)) {
									dataMap.put(column.getSourceColumnName(), json.get(column.getTargetColumnLabel()));
								}
							}

							if (StringUtils.isNotEmpty(tableCombination.getUniquenessKey())) {
								String key = ParamUtils.getString(dataMap, tableCombination.getUniquenessKey());
								if (!uniquenessKeys.contains(key)) {
									uniquenessKeys.add(key);
									dataMap.put("_uniquenesskey_", key);
									dataMap.put("orcl_uniquenesskey_", key);
									if (StringUtils.isNotEmpty(tableCombination.getPrimaryKey())) {
										String id = ParamUtils.getString(dataMap, tableCombination.getPrimaryKey());
										id = database.getId() + "__/__" + id;
										if (!rowIds.contains(id)) {
											rowIds.add(id);
											dataMap.put("_complexkey_", id);
											dataMap.put("orcl_complexkey_", id);
											insertList.add(dataMap);
										}
									} else {
										insertList.add(dataMap);
									}
								}
							} else {
								if (StringUtils.isNotEmpty(tableCombination.getPrimaryKey())) {
									String id = ParamUtils.getString(dataMap, tableCombination.getPrimaryKey());
									id = database.getId() + "__/__" + id;
									if (!rowIds.contains(id)) {
										rowIds.add(id);
										dataMap.put("_complexkey_", id);
										dataMap.put("orcl_complexkey_", id);
										insertList.add(dataMap);
									}
								} else {
									insertList.add(dataMap);
								}
							}
						}

						if (insertList != null && !insertList.isEmpty()) {
							total = total + insertList.size();
							for (Map<String, Object> dataMap2 : insertList) {
								dataMap2.put("_datasetid_", datasetId);
								dataMap2.put("_databaseid_", database.getId());

								dataMap2.put("orcl_datasetid_", datasetId);
								dataMap2.put("orcl_databaseid_", database.getId());

								if (StringUtils.equals(tableCombination.getCurrentUserFlag(), "Y")) {
									dataMap2.put("_current_user_", Authentication.getAuthenticatedActorId());
									dataMap2.put("orcl_current_user_", Authentication.getAuthenticatedActorId());
								}

								if (dataMap2.get("_name_") == null) {
									dataMap2.put("_name_", database.getTitle());
									dataMap2.put("orcl_name_", database.getTitle());
								}
								if (dataMap2.get("_section_") == null) {
									dataMap2.put("_section_", database.getSection());
									dataMap2.put("orcl_section_", database.getSection());
								}
								if (dataMap2.get("_mapping_") == null) {
									dataMap2.put("_mapping_", database.getMapping());
									dataMap2.put("orcl_mapping_", database.getMapping());
								}
							}

							if (idColumn != null) {
								for (Map<String, Object> dataMap2 : insertList) {
									if (ParamUtils.getObject(dataMap2, idColumn.getColumnName()) == null) {
										switch (idColumn.getJavaType()) {
										case "Integer":
											dataMap2.put(idColumn.getColumnName().toLowerCase(), --maxVal);
											break;
										case "Long":
											dataMap2.put(idColumn.getColumnName().toLowerCase(), ++ts);
											break;
										case "String":
											dataMap2.put(idColumn.getColumnName().toLowerCase(), UUID32.getUUID());
											break;
										default:
											break;
										}
									}
								}
							}

							bulkInsertBean.bulkInsert(targetConn, tableDefinition, insertList);
							// targetConn.commit();
							insertList.clear();
						}
					}
				}
			}

			targetConn.commit();

			logger.debug("process finish.");

			return true;
		} catch (Exception ex) {
			ExceptionUtils.addMsg("table_combination_" + tableCombination.getId(), ex.getMessage());
			logger.error("execute sql error", ex);
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			insertList = null;

			JdbcUtils.close(sourceRS);
			JdbcUtils.close(sourcePsmt);
			JdbcUtils.close(sourceConn);

			JdbcUtils.close(targetRS);
			JdbcUtils.close(targetPsmt);
			JdbcUtils.close(targetConn);
		}
	}

	public boolean getResult() {
		return result;
	}

	public void run() {
		result = this.execute(sourceDatabaseId, targetDatabaseId, tableCombinationId);
	}

}
