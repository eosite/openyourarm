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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.metamodel.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.config.Configuration;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.StringTools;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.DataSetSynthetic;
import com.glaf.datamgr.domain.SelectSegment;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.jdbc.MyBatisHelper;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.service.DataSetSyntheticService;
import com.glaf.datamgr.util.ExceptionUtils;
import com.glaf.datamgr.util.FastJsonUtils;

public class DataSetSyntheticBean implements java.lang.Runnable {

	protected static Configuration conf = BaseConfiguration.create();

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected long sourceDatabaseId;

	protected long targetDatabaseId;

	protected long dataSetSyntheticId;

	protected boolean result;

	public DataSetSyntheticBean() {

	}

	public DataSetSyntheticBean(long sourceDatabaseId, long targetDatabaseId, long dataSetSyntheticId) {
		this.sourceDatabaseId = sourceDatabaseId;
		this.targetDatabaseId = targetDatabaseId;
		this.dataSetSyntheticId = dataSetSyntheticId;
	}

	public boolean execute(long sourceDatabaseId, long targetDatabaseId, long dataSetSyntheticId) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		DataSetService dataSetService = ContextFactory.getBean("dataSetService");
		DataSetSyntheticService dataSetSyntheticService = ContextFactory.getBean("dataSetSyntheticService");
		try {
			Database sourceDatabase = databaseService.getDatabaseById(sourceDatabaseId);
			Database targetDatabase = databaseService.getDatabaseById(targetDatabaseId);
			DataSetSynthetic dataSetSynthetic = dataSetSyntheticService.getDataSetSynthetic(dataSetSyntheticId);
			if (dataSetSynthetic != null) {
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

				DataSetBuilder builder = new DataSetBuilder();
				DataSet dataSet = dataSetService.getDataSet(dataSetSynthetic.getSourceDataSetId());
				Query query = builder.buildQuery(sourceDatabase.getName(), dataSetSynthetic.getSourceDataSetId(),
						parameter);
				String sql = query.toSql();
				QueryHelper helper = new QueryHelper();
				List<ColumnDefinition> columns = helper.getColumnDefinitions(sourceDatabase.getName(), sql, parameter);
				if (columns != null && !columns.isEmpty()) {

					List<SelectSegment> selectSegments = dataSet.getSelectSegments();
					Map<String, String> columnMap = new HashMap<String, String>();
					for (SelectSegment seg : selectSegments) {
						logger.debug(seg.getColumnLabel() + "\t" + seg.getColumnName());
						if (StringUtils.isNotEmpty(seg.getMapping())) {
							columnMap.put(seg.getColumnLabel().toLowerCase(), seg.getMapping().toLowerCase());
						} else {
							columnMap.put(seg.getColumnLabel().toLowerCase(), seg.getColumnName().toLowerCase());
						}
					}

					for (ColumnDefinition c : columns) {
						logger.debug(c.getColumnLabel() + "\t" + c.getColumnName());
						if (columnMap.get(c.getColumnLabel().toLowerCase()) != null) {
							c.setColumnName(columnMap.get(c.getColumnLabel().toLowerCase()));
						} else {
							if (columnMap.get(c.getColumnName().toLowerCase()) != null) {
								c.setColumnName(columnMap.get(c.getColumnName().toLowerCase()));
							}
						}
						logger.debug("->" + c.getColumnName());
					}

					for (ColumnDefinition col : columns) {
						col.setPrimaryKey(false);
					}

					TableDefinition tableDefinition = new TableDefinition();
					tableDefinition.setTableName(dataSetSynthetic.getTargetTableName());

					ColumnDefinition syntheticId = new ColumnDefinition();
					syntheticId.setName("syntheticId");
					syntheticId.setColumnName("_SYNTHETICID_");
					if (DBUtils.ORACLE.equals(targetDatabase.getType())) {
						syntheticId.setColumnName("EX_SYNTHETICID_");
					}
					syntheticId.setJavaType("Long");
					columns.add(syntheticId);

					ColumnDefinition col2 = new ColumnDefinition();
					col2.setColumnName("_DATABASEID_");
					if (DBUtils.ORACLE.equals(targetDatabase.getType())) {
						col2.setColumnName("EX_DATABASEID_");
					}
					col2.setJavaType("Long");
					columns.add(col2);

					ColumnDefinition col22 = new ColumnDefinition();
					col22.setColumnName("_NAME_");
					if (DBUtils.ORACLE.equals(targetDatabase.getType())) {
						col22.setColumnName("EX_NAME_");
					}
					col22.setJavaType("String");
					col22.setLength(500);
					columns.add(col22);

					ColumnDefinition col23 = new ColumnDefinition();
					col23.setColumnName("_MAPPING_");
					if (DBUtils.ORACLE.equals(targetDatabase.getType())) {
						col23.setColumnName("EX_MAPPING_");
					}
					col23.setJavaType("String");
					col23.setLength(200);
					columns.add(col23);

					ColumnDefinition col24 = new ColumnDefinition();
					col24.setColumnName("_SECTION_");
					if (DBUtils.ORACLE.equals(targetDatabase.getType())) {
						col24.setColumnName("EX_SECTION_");
					}
					col24.setJavaType("String");
					col24.setLength(200);
					columns.add(col24);

					ColumnDefinition col4 = new ColumnDefinition();
					col4.setColumnName("_AGGRKEY_");
					if (DBUtils.ORACLE.equals(targetDatabase.getType())) {
						col4.setColumnName("EX_AGGRKEY_");
					}
					col4.setJavaType("String");
					col4.setLength(500);
					columns.add(col4);

					ColumnDefinition col5 = new ColumnDefinition();
					col5.setColumnName("ENABLED_FLAG");
					if (DBUtils.ORACLE.equals(targetDatabase.getType())) {
						col5.setColumnName("EX_ENABLED_FLAG_");
					}
					col5.setJavaType("String");
					col5.setLength(1);
					columns.add(col5);

					ColumnDefinition col6 = new ColumnDefinition();
					col6.setColumnName("MD5_");
					if (DBUtils.ORACLE.equals(targetDatabase.getType())) {
						col6.setColumnName("EX_MD5_");
					}
					col6.setJavaType("String");
					col6.setLength(100);
					columns.add(col6);

					ColumnDefinition idColumn = new ColumnDefinition();
					idColumn.setColumnName("_UUID_");
					if (DBUtils.ORACLE.equals(targetDatabase.getType())) {
						idColumn.setColumnName("EX_UUID_");
					}
					idColumn.setJavaType("String");
					idColumn.setLength(100);
					idColumn.setPrimaryKey(true);
					tableDefinition.setIdColumn(idColumn);
					tableDefinition.setColumns(columns);

					if (DBUtils.tableExists(targetDatabase.getName(), dataSetSynthetic.getTargetTableName())) {
						DBUtils.alterTable(targetDatabase.getName(), tableDefinition);
					} else {
						DBUtils.createTable(targetDatabase.getName(), tableDefinition);
					}

					return this.execute(sourceDatabase, targetDatabase, dataSetSynthetic);
				}
			}
			return false;
		} catch (Exception ex) {
			logger.error("execute error", ex);
			throw new RuntimeException(ex);
		}
	}

	public boolean execute(Database sourceDatabase, Database targetDatabase, DataSetSynthetic dataSetSynthetic) {
		if (!(StringUtils.startsWithIgnoreCase(dataSetSynthetic.getTargetTableName(), "useradd_")
				|| StringUtils.startsWithIgnoreCase(dataSetSynthetic.getTargetTableName(), "etl_")
				|| StringUtils.startsWithIgnoreCase(dataSetSynthetic.getTargetTableName(), "sync_")
				|| StringUtils.startsWithIgnoreCase(dataSetSynthetic.getTargetTableName(), "tree_table_")
				|| StringUtils.startsWithIgnoreCase(dataSetSynthetic.getTargetTableName(), "tmp"))) {
			return false;
		}
		DataSetService dataSetService = ContextFactory.getBean("dataSetService");
		DataSetBuilder builder = new DataSetBuilder();
		StringBuilder insertBuffer = new StringBuilder();
		StringBuilder updateBuffer = new StringBuilder();

		Map<String, String> sourceMd5Map = new HashMap<String, String>();
		Map<String, Map<String, Object>> sourceDataMap = new HashMap<String, Map<String, Object>>();

		Map<String, String> targetMd5Map = new HashMap<String, String>();

		List<String> aggregationKeys = StringTools.split(dataSetSynthetic.getAggregationKeys().toLowerCase(), ",");
		List<String> syncColumns = StringTools.split(dataSetSynthetic.getSyncColumns().toLowerCase(), ",");
		logger.debug("aggr columns:" + aggregationKeys);
		logger.debug("sync columns:" + syncColumns);

		String idColumnName = null;
		ColumnDefinition idColumn = null;

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

		Connection targetConn = null;
		PreparedStatement targetPsmt = null;
		ResultSet targetRS = null;

		PreparedStatement targetInsertPsmt = null;
		PreparedStatement targetUpdatePsmt = null;
		try {
			DataSet dataSet = dataSetService.getDataSet(dataSetSynthetic.getSourceDataSetId());
			Query query = builder.buildQuery(sourceDatabase.getName(), dataSetSynthetic.getSourceDataSetId(),
					parameter);
			String sql = query.toSql();
			QueryHelper helper = new QueryHelper();
			List<ColumnDefinition> srcColumns = helper.getColumnDefinitions(sourceDatabase.getName(), sql, parameter);
			List<SelectSegment> selectSegments = dataSet.getSelectSegments();
			Map<String, String> columnMap = new HashMap<String, String>();
			for (SelectSegment seg : selectSegments) {
				logger.debug(seg.getColumnLabel() + "\t" + seg.getColumnName());
				if (StringUtils.isNotEmpty(seg.getMapping())) {
					columnMap.put(seg.getColumnLabel().toLowerCase(), seg.getMapping().toLowerCase());
				} else {
					columnMap.put(seg.getColumnLabel().toLowerCase(), seg.getColumnName().toLowerCase());
				}
			}

			for (ColumnDefinition c : srcColumns) {
				logger.debug(c.getColumnLabel() + "\t" + c.getColumnName());
				if (columnMap.get(c.getColumnLabel().toLowerCase()) != null) {
					c.setColumnName(columnMap.get(c.getColumnLabel().toLowerCase()));
				} else {
					if (columnMap.get(c.getColumnName().toLowerCase()) != null) {
						c.setColumnName(columnMap.get(c.getColumnName().toLowerCase()));
					}
				}
				logger.debug("->" + c.getColumnName());
			}

			List<ColumnDefinition> destColumns = DBUtils.getColumnDefinitions(targetDatabase.getName(),
					dataSetSynthetic.getTargetTableName());
			List<String> primaryKeys2 = DBUtils.getUpperCasePrimaryKeys(targetDatabase.getName(),
					dataSetSynthetic.getTargetTableName());

			List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();

			int position = 1;
			for (ColumnDefinition col : destColumns) {
				if (primaryKeys2.contains(col.getColumnName().toUpperCase())) {
					idColumn = col;
					idColumn.setPosition(destColumns.size());
					idColumnName = col.getColumnName().toLowerCase();
				} else {
					col.setPosition(position++);
					columns.add(col);
				}
			}

			targetConn = DBConnectionFactory.getConnection(targetDatabase.getName());
			targetConn.setAutoCommit(false);
			logger.debug("目标数据库已经连接:" + targetDatabase.getDbname());
			logger.debug("idColumnName:" + idColumnName);

			String enabledFlag = null;
			String columnName = null;
			ColumnDefinition cm = null;
			int len = srcColumns.size();
			StringBuilder row = new StringBuilder();
			StringBuilder rowAggr = new StringBuilder();

			SqlExecutor sqlExecutor = DBUtils.replaceSQL(sql, parameter);

			insertBuffer.append(" insert into ").append(dataSetSynthetic.getTargetTableName()).append(" ( ");
			updateBuffer.append(" update ").append(dataSetSynthetic.getTargetTableName()).append(" set ");

			len = columns.size();

			int k = 0;
			for (ColumnDefinition c : columns) {
				columnName = c.getColumnName().toLowerCase();
				insertBuffer.append(columnName).append(" , ");
				updateBuffer.append(columnName).append(" = ? ");
				if (k++ < (len - 1)) {
					updateBuffer.append(" , ");
				}
			}

			insertBuffer.append(idColumnName).append(") values ( ");
			for (k = 1; k <= len; k++) {
				insertBuffer.append(" ?, ");
			}

			updateBuffer.append(" where ").append(idColumnName).append(" = ? ");
			insertBuffer.append(" ? ) ");

			logger.debug("select sql :" + sqlExecutor.getSql());
			logger.debug("insert sql :" + insertBuffer.toString());
			logger.debug("update sql :" + updateBuffer.toString());

			Map<String, String> columnMap2 = new HashMap<String, String>();
			List<SelectSegment> segments = dataSet.getSelectSegments();
			for (SelectSegment segment : segments) {
				if (StringUtils.equals(segment.getOutput(), "true")) {
					columnMap2.put(segment.getColumnName().toLowerCase(), segment.getColumnLabel().toLowerCase());
				}
			}

			Object object = null;
			String aggrKey = null;

			dataSet.setDatabaseId(sourceDatabase.getId());
			JSONArray array = builder.getJsonArray(sourceDatabase.getName(), dataSet, parameter);

			if (array != null && !array.isEmpty()) {
				int size = array.size();
				JSONObject json = null;
				Map<String, Object> dataMap = null;
				for (int i = 0; i < size; i++) {
					json = array.getJSONObject(i);
					json = FastJsonUtils.addLowerCaseKey(json);
					// logger.debug(json.toJSONString());
					row.delete(0, row.length());
					rowAggr.delete(0, rowAggr.length());

					dataMap = new HashMap<String, Object>();

					if (!aggregationKeys.isEmpty()) {
						for (String aggregationKey : aggregationKeys) {
							String alias = columnMap2.get(aggregationKey);
							object = json.get(alias);
							if (object != null) {
								if (object instanceof java.util.Date) {
									java.util.Date date = (java.util.Date) object;
									rowAggr.append(date.getTime());
								} else {
									rowAggr.append(object.toString());
								}
							}
						}

						aggrKey = DigestUtils.md5Hex(rowAggr.toString());

						if (!syncColumns.isEmpty()) {
							for (String syncColumn : syncColumns) {
								String alias = columnMap2.get(syncColumn);
								object = json.get(alias);
								if (object != null) {
									if (object instanceof java.util.Date) {
										java.util.Date date = (java.util.Date) object;
										row.append(date.getTime());
									} else {
										row.append(object.toString());
									}
									dataMap.put(syncColumn, object);
								}
							}
							sourceMd5Map.put(aggrKey, DigestUtils.md5Hex(row.toString()));
						}

						sourceDataMap.put(aggrKey, dataMap);
						// logger.debug(aggrKey + ":" + dataMap);
					}
				}
			}

			logger.debug("sourceMd5Map size: " + sourceMd5Map.size());
			logger.debug("sourceDataMap size: " + sourceDataMap.size());
			logger.debug("sourceDataMap: " + sourceDataMap);

			StringBuilder selectBuffer = new StringBuilder();
			selectBuffer.delete(0, selectBuffer.length());
			selectBuffer.append("select * from ").append(dataSetSynthetic.getTargetTableName());

			if (DBUtils.ORACLE.equals(targetDatabase.getType())) {
				selectBuffer.append(" where EX_DATABASEID_ = ").append(sourceDatabase.getId());
				selectBuffer.append(" and EX_SYNTHETICID_ = ").append(dataSetSynthetic.getId());
			} else {
				selectBuffer.append(" where _DATABASEID_ = ").append(sourceDatabase.getId());
				selectBuffer.append(" and _SYNTHETICID_ = ").append(dataSetSynthetic.getId());
			}

			String id = null;

			targetPsmt = targetConn.prepareStatement(selectBuffer.toString());
			targetRS = targetPsmt.executeQuery();

			MyBatisHelper helper2 = new MyBatisHelper();
			List<Map<String, Object>> resultList2 = helper2.getResults(targetRS);
			for (Map<String, Object> dataMap : resultList2) {

				row.delete(0, row.length());
				rowAggr.delete(0, rowAggr.length());

				if (!aggregationKeys.isEmpty()) {
					for (String aggregationKey : aggregationKeys) {
						object = ParamUtils.getObject(dataMap, aggregationKey);
						if (object != null) {
							if (object instanceof java.util.Date) {
								java.util.Date date = (java.util.Date) object;
								rowAggr.append(date.getTime());
							} else {
								rowAggr.append(object.toString());
							}
						}
					}

					aggrKey = DigestUtils.md5Hex(rowAggr.toString());

					if (!syncColumns.isEmpty()) {
						for (String syncColumn : syncColumns) {
							object = ParamUtils.getObject(dataMap, syncColumn);
							if (object != null) {
								if (object instanceof java.util.Date) {
									java.util.Date date = (java.util.Date) object;
									row.append(date.getTime());
								} else {
									row.append(object.toString());
								}
							}
						}
						targetMd5Map.put(aggrKey, DigestUtils.md5Hex(row.toString()));
					}

					Map<String, Object> newDataMap = sourceDataMap.get(aggrKey);

					if (newDataMap != null) {
						id = ParamUtils.getString(dataMap, idColumnName);
						enabledFlag = ParamUtils.getString(dataMap, "enabled_flag");

						if (enabledFlag == null) {
							enabledFlag = ParamUtils.getString(dataMap, "ex_enabled_flag_");
						}

						// 放入之前的记录的id
						newDataMap.put(idColumnName, id);

						if (enabledFlag != null && enabledFlag.length() == 1) {
							// 目标表enabledFlag值
							newDataMap.put("enabled_flag", enabledFlag);
							newDataMap.put("ex_enabled_flag_", enabledFlag);
						}

						// logger.debug(id + "==" + enabledFlag);
					}
				}
			}

			JdbcUtils.close(targetRS);
			JdbcUtils.close(targetPsmt);

			logger.debug(targetMd5Map + ":" + targetMd5Map);

			/**
			 * 删除掉目标表数据，再重新抓取
			 */
			boolean insertOperation = false;
			if (StringUtils.equalsIgnoreCase(dataSetSynthetic.getDeleteFetch(), "Y")) {
				if (StringUtils.startsWithIgnoreCase(dataSetSynthetic.getTargetTableName(), "useradd_")
						|| StringUtils.startsWithIgnoreCase(dataSetSynthetic.getTargetTableName(), "etl_")
						|| StringUtils.startsWithIgnoreCase(dataSetSynthetic.getTargetTableName(), "sync_")
						|| StringUtils.startsWithIgnoreCase(dataSetSynthetic.getTargetTableName(), "tree_table_")
						|| StringUtils.startsWithIgnoreCase(dataSetSynthetic.getTargetTableName(), "tmp")) {
					String sql2 = " delete from " + dataSetSynthetic.getTargetTableName() + " where _DATABASEID_ = "
							+ sourceDatabase.getId() + " and _SYNTHETICID_ = " + dataSetSynthetic.getId();
					if (DBUtils.ORACLE.equals(targetDatabase.getType())) {
						sql2 = " delete from " + dataSetSynthetic.getTargetTableName() + " where EX_DATABASEID_ = "
								+ sourceDatabase.getId() + " and EX_SYNTHETICID_ = " + dataSetSynthetic.getId();
					}
					DBUtils.executeSchemaResourceIgnoreException(targetConn, sql2);
					// targetConn.commit();
					insertOperation = true;
				}
			}

			targetInsertPsmt = targetConn.prepareStatement(insertBuffer.toString());
			targetUpdatePsmt = targetConn.prepareStatement(updateBuffer.toString());

			Set<Entry<String, Map<String, Object>>> entrySet = sourceDataMap.entrySet();
			for (Entry<String, Map<String, Object>> entry : entrySet) {
				String key = entry.getKey();
				Map<String, Object> dataMap = entry.getValue();
				String md5 = sourceMd5Map.get(key);
				if (!insertOperation) {
					/**
					 * 判断MD5值是否相同，相同就认为是记录一样的，不用做任何操作
					 */
					if (StringUtils.equals(md5, targetMd5Map.get(key))) {
						// logger.debug(key + " is exists !!!");
						continue;
					}
				}

				if (!insertOperation && targetMd5Map.containsKey(key)) {
					/**
					 * 判断是否只能新增，如果是只能新增，就不做修改
					 */
					if (StringUtils.equalsIgnoreCase(dataSetSynthetic.getInsertOnly(), "Y")) {
						// logger.debug(key + " insert only !");
						continue;
					}

					dataMap.put("_syntheticid_", dataSetSynthetic.getId());
					dataMap.put("_databaseid_", sourceDatabase.getId());
					dataMap.put("_name_", sourceDatabase.getTitle());
					dataMap.put("_section_", sourceDatabase.getSection());
					dataMap.put("_mapping_", sourceDatabase.getMapping());

					dataMap.put("ex_syntheticid_", dataSetSynthetic.getId());
					dataMap.put("ex_databaseid_", sourceDatabase.getId());
					dataMap.put("ex_name_", sourceDatabase.getTitle());
					dataMap.put("ex_section_", sourceDatabase.getSection());
					dataMap.put("ex_mapping_", sourceDatabase.getMapping());

					// logger.debug("update row:" + dataMap);

					for (int j = 0; j < columns.size(); j++) {
						cm = columns.get(j);
						columnName = cm.getColumnName().toLowerCase();
						switch (cm.getJavaType()) {
						case "Integer":
							targetUpdatePsmt.setInt(cm.getPosition(), ParamUtils.getInt(dataMap, columnName));
							break;
						case "Long":
							targetUpdatePsmt.setLong(cm.getPosition(), ParamUtils.getLong(dataMap, columnName));
							break;
						case "Double":
							targetUpdatePsmt.setDouble(cm.getPosition(), ParamUtils.getDouble(dataMap, columnName));
							break;
						case "Date":
							if (ParamUtils.getDate(dataMap, columnName) != null) {
								targetUpdatePsmt.setTimestamp(cm.getPosition(),
										DateUtils.toTimestamp(ParamUtils.getDate(dataMap, columnName)));
							} else {
								targetUpdatePsmt.setTimestamp(cm.getPosition(), null);
							}
							break;
						case "String":
							targetUpdatePsmt.setString(cm.getPosition(), ParamUtils.getString(dataMap, columnName));
							break;
						default:
							targetUpdatePsmt.setObject(cm.getPosition(), dataMap.get(columnName));
							break;
						}
					}
					switch (idColumn.getJavaType()) {
					case "Integer":
						targetUpdatePsmt.setInt(idColumn.getPosition(), ParamUtils.getInt(dataMap, idColumnName));
						break;
					case "Long":
						targetUpdatePsmt.setLong(idColumn.getPosition(), ParamUtils.getLong(dataMap, idColumnName));
						break;
					case "Double":
						targetUpdatePsmt.setDouble(idColumn.getPosition(), ParamUtils.getDouble(dataMap, idColumnName));
						break;
					default:
						targetUpdatePsmt.setString(idColumn.getPosition(), ParamUtils.getString(dataMap, idColumnName));
						break;
					}
					// 做更新操作
					targetUpdatePsmt.addBatch();
					// logger.debug("add update batch.");
				} else {
					if (dataMap.get(idColumnName) == null) {
						dataMap.put(idColumnName, sourceDatabase.getId() + "_" + key);
					}
					// logger.debug("insert row:" + dataMap);

					dataMap.put("_aggrkey_", key);
					dataMap.put("_syntheticid_", dataSetSynthetic.getId());
					dataMap.put("_databaseid_", sourceDatabase.getId());
					dataMap.put("_name_", sourceDatabase.getTitle());

					dataMap.put("ex_aggrkey_", key);
					dataMap.put("ex_syntheticid_", dataSetSynthetic.getId());
					dataMap.put("ex_databaseid_", sourceDatabase.getId());
					dataMap.put("ex_name_", sourceDatabase.getTitle());

					if (!dataMap.containsKey("_section_")) {
						dataMap.put("_section_", sourceDatabase.getSection());
					}

					if (!dataMap.containsKey("ex_section_")) {
						dataMap.put("ex_section_", sourceDatabase.getSection());
					}

					if (!dataMap.containsKey("_mapping_")) {
						dataMap.put("_mapping_", sourceDatabase.getMapping());
					}

					if (!dataMap.containsKey("ex_mapping_")) {
						dataMap.put("ex_mapping_", sourceDatabase.getMapping());
					}

					for (int j = 0; j < columns.size(); j++) {
						cm = columns.get(j);
						columnName = cm.getColumnName().toLowerCase();
						switch (cm.getJavaType()) {
						case "Integer":
							targetInsertPsmt.setInt(cm.getPosition(), ParamUtils.getInt(dataMap, columnName));
							break;
						case "Long":
							targetInsertPsmt.setLong(cm.getPosition(), ParamUtils.getLong(dataMap, columnName));
							break;
						case "Double":
							targetInsertPsmt.setDouble(cm.getPosition(), ParamUtils.getDouble(dataMap, columnName));
							break;
						case "Date":
							if (cm.getDateValue() != null) {
								targetInsertPsmt.setTimestamp(cm.getPosition(),
										DateUtils.toTimestamp(ParamUtils.getDate(dataMap, columnName)));
							} else {
								targetInsertPsmt.setObject(cm.getPosition(), null);
							}
							break;
						case "String":
							targetInsertPsmt.setString(cm.getPosition(), ParamUtils.getString(dataMap, columnName));
							break;
						default:
							targetInsertPsmt.setObject(cm.getPosition(), dataMap.get(columnName));
							break;
						}
					}

					switch (idColumn.getJavaType()) {
					case "Integer":
						targetInsertPsmt.setInt(idColumn.getPosition(), ParamUtils.getInt(dataMap, idColumnName));
						break;
					case "Long":
						targetInsertPsmt.setLong(idColumn.getPosition(), ParamUtils.getLong(dataMap, idColumnName));
						break;
					case "Double":
						targetInsertPsmt.setDouble(idColumn.getPosition(), ParamUtils.getDouble(dataMap, idColumnName));
						break;
					default:
						targetInsertPsmt.setString(idColumn.getPosition(), ParamUtils.getString(dataMap, idColumnName));
						break;
					}
					// 做新增操作
					targetInsertPsmt.addBatch();
					// logger.debug("add insert batch.");
				}
			}

			if (targetInsertPsmt != null) {
				targetInsertPsmt.executeBatch();
				// targetConn.commit();
				logger.debug("execute insert batch.");
			}

			if (targetUpdatePsmt != null) {
				targetUpdatePsmt.executeBatch();
				// targetConn.commit();
				logger.debug("execute update batch.");
			}

			targetConn.commit();

			sourceMd5Map.clear();
			sourceMd5Map = null;

			targetMd5Map.clear();
			targetMd5Map = null;

			sourceDataMap.clear();
			sourceDataMap = null;

			return true;

		} catch (Exception ex) {
			ExceptionUtils.addMsg("dataset_synthetic_" + dataSetSynthetic.getId(), ex.getMessage());
			logger.error("execute sql error", ex);
			throw new RuntimeException(ex);
		} finally {
			sourceMd5Map = null;
			targetMd5Map = null;
			sourceDataMap = null;

			JdbcUtils.close(targetRS);
			JdbcUtils.close(targetPsmt);
			JdbcUtils.close(targetInsertPsmt);
			JdbcUtils.close(targetUpdatePsmt);
			JdbcUtils.close(targetConn);
		}
	}

	public boolean getResult() {
		return result;
	}

	public void run() {
		result = this.execute(sourceDatabaseId, targetDatabaseId, dataSetSyntheticId);
	}

}
