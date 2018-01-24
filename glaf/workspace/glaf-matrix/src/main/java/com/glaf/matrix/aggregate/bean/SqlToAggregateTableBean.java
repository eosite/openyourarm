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

package com.glaf.matrix.aggregate.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

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
import com.glaf.core.jdbc.QueryConnectionFactory;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.StringTools;

import com.glaf.matrix.aggregate.domain.TableAggregateApp;
import com.glaf.matrix.aggregate.service.TableAggregateAppService;
import com.glaf.matrix.util.SysParams;

public class SqlToAggregateTableBean {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void execute(long srcDatabaseId, long targetDatabaseId, long syncId, Map<String, Object> params) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		TableAggregateAppService tableAggregateAppService = ContextFactory
				.getBean("com.glaf.matrix.aggregate.service.tableAggregateAppService");
		TableAggregateApp tableAggregateApp = tableAggregateAppService.getTableAggregateApp(syncId);
		if (tableAggregateApp == null || !StringUtils.equals(tableAggregateApp.getActive(), "Y")) {
			return;
		}
		QueryHelper helper = new QueryHelper();

		Map<String, Object> parameter = new HashMap<String, Object>();
		if (params != null && !params.isEmpty()) {
			parameter.putAll(params);
		}

		Map<String, Map<String, Object>> dataListMap = new LinkedHashMap<String, Map<String, Object>>();
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
		long ts = 0;
		try {
			srcDatabase = databaseService.getDatabaseById(srcDatabaseId);
			targetDatabase = databaseService.getDatabaseById(targetDatabaseId);

			if (srcDatabase != null) {
				srcConn = DBConnectionFactory.getConnection(srcDatabase.getName());
				logger.debug("srcConn:" + srcConn);
			} else {
				srcConn = DBConnectionFactory.getConnection();
			}

			if (targetDatabase != null) {
				targetConn = DBConnectionFactory.getConnection(targetDatabase.getName());
				logger.debug("targetConn:" + targetConn);
			} else {
				targetConn = DBConnectionFactory.getConnection();
			}

			if (srcConn != null && targetConn != null) {

				ts = System.currentTimeMillis();
				QueryConnectionFactory.getInstance().register(ts, srcConn);

				List<ColumnDefinition> srcColumns = null;
				List<ColumnDefinition> columns = null;
				boolean tableExists = false;

				srcColumns = DBUtils.getColumnDefinitions(srcConn, tableAggregateApp.getSourceTableName());

				TableDefinition tableDefinition = new TableDefinition();
				tableDefinition.setTableName(tableAggregateApp.getTargetTableName());

				if (DBUtils.tableExists(targetConn, tableAggregateApp.getTargetTableName())) {
					tableExists = true;
					columns = DBUtils.getColumnDefinitions(targetConn, tableAggregateApp.getTargetTableName());
					tableDefinition.setColumns(columns);
				} else {
					columns = new ArrayList<ColumnDefinition>();
					for (ColumnDefinition col : srcColumns) {
						if (StringUtils.startsWithIgnoreCase(col.getColumnName(), "EX_SYNC_")) {
							continue;
						}
						if (col.isPrimaryKey()) {
							col.setPrimaryKey(false);
						}
						col.setTableName(tableAggregateApp.getTargetTableName());
						columns.add(col);
					}
					tableDefinition.setColumns(columns);

					ColumnDefinition idColumn = new ColumnDefinition();
					idColumn.setColumnName("EX_ID_");// 主键
					idColumn.setJavaType("String");
					idColumn.setLength(100);
					tableDefinition.setIdColumn(idColumn);

					ColumnDefinition syncIdColumn = new ColumnDefinition();
					syncIdColumn.setColumnName("EX_SYNCID_");// syncId
					syncIdColumn.setJavaType("Long");
					tableDefinition.addColumn(syncIdColumn);

					ColumnDefinition sortColumn = new ColumnDefinition();
					sortColumn.setColumnName("EX_SORT_");// sort
					sortColumn.setJavaType("Integer");
					tableDefinition.addColumn(sortColumn);

					ColumnDefinition levelColumn = new ColumnDefinition();
					levelColumn.setColumnName("EX_LEVEL_");// level
					levelColumn.setJavaType("Integer");
					tableDefinition.addColumn(levelColumn);

					ColumnDefinition splitColumn = new ColumnDefinition();
					splitColumn.setColumnName("EX_SPLIT_");// split
					splitColumn.setJavaType("String");
					splitColumn.setLength(50);
					tableDefinition.addColumn(splitColumn);
				}

				Set<String> columnNames = new HashSet<String>();
				for (ColumnDefinition col : columns) {
					columnNames.add(col.getColumnName().trim().toLowerCase());
				}

				List<String> sourceCalculateColumns = StringTools
						.splitLowerCase(tableAggregateApp.getSourceCalculateColumns());
				logger.debug("sourceCalculateColumns:" + sourceCalculateColumns);

				List<ColumnDefinition> alculateColumns = new ArrayList<ColumnDefinition>();
				for (ColumnDefinition col : srcColumns) {
					if (sourceCalculateColumns.contains(col.getColumnName().trim().toLowerCase())) {
						alculateColumns.add(col);

						if (!columnNames.contains(col.getColumnName().toLowerCase())) {
							col.setColumnName(col.getColumnName().toLowerCase());
							columnNames.add(col.getColumnName().toLowerCase());
							tableDefinition.addColumn(col);
						}

						if (!columnNames.contains(col.getColumnName().toLowerCase() + "_TOTAL")) {
							columnNames.add(col.getColumnName().toLowerCase() + "_total");
							ColumnDefinition col2 = new ColumnDefinition();
							col2.setColumnName(col.getColumnName().toLowerCase() + "_TOTAL");
							col2.setJavaType(col.getJavaType());
							col2.setLength(col.getLength());
							tableDefinition.addColumn(col2);
						}
					}
				}

				logger.debug("columns:" + columns);

				targetConn.setAutoCommit(false);
				if (!tableExists) {
					DBUtils.createTable(targetConn, tableDefinition);
				} else {
					DBUtils.alterTable(targetConn, tableDefinition);
				}
				targetConn.commit();

				StringBuilder sqlBuffer = new StringBuilder();
				sqlBuffer.append(" select distinct ").append(tableAggregateApp.getDimensionColumn()).append(" from ")
						.append(tableAggregateApp.getDimensionTableName()).append(" ").append(" order by ")
						.append(tableAggregateApp.getDimensionColumn()).append(" desc "); // where treeid like '1|54|%'

				List<String> dimensionList = new ArrayList<String>();
				srcPsmt = srcConn.prepareStatement(sqlBuffer.toString());
				srcRs = srcPsmt.executeQuery();
				while (srcRs.next()) {
					dimensionList.add(srcRs.getString(1));
				}
				JdbcUtils.close(srcRs);
				JdbcUtils.close(srcPsmt);

				String sql = " select * from " + tableAggregateApp.getSourceTableName();
				if (StringUtils.isNotEmpty(tableAggregateApp.getSourceTableDateSplitColumn())) {
					sql = sql + " order by " + tableAggregateApp.getSourceTreeIdColumn() + " desc, "
							+ tableAggregateApp.getSourceTableDateSplitColumn() + " asc ";
					logger.debug(sql);
					List<Map<String, Object>> sourceResultList = helper.getResultList(srcConn, sql, parameter, 2000000);
					JdbcUtils.close(srcConn);

					if (sourceResultList != null && !sourceResultList.isEmpty()) {
						List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
						for (Map<String, Object> rowMap : sourceResultList) {
							LowerLinkedMap dataMap2 = new LowerLinkedMap();
							dataMap2.putAll(rowMap);
							dataList.add(dataMap2);
						}
						sourceResultList.clear();

						String text = null;
						String name = null;
						String treeId = null;
						String level1 = null;
						String level2 = null;
						int sort = 0;
						int intValue = 0;
						int intSumValue = 0;
						int tmpValue = 0;
						long longValue = 0;
						long longSumValue = 0;
						double doubleValue = 0;
						double doubleSumValue = 0;
						boolean canExecute = false;
						Date dimensionDate = null;
						Set<String> dimensions = new HashSet<String>();
						Map<String, Object> treeDataMap1 = new HashMap<String, Object>();// 省级
						Map<String, Object> treeSumDataMap1 = new HashMap<String, Object>();// 省级sum
						Map<String, Object> treeDataMap2 = new HashMap<String, Object>();// 市级
						Map<String, Object> treeSumDataMap2 = new HashMap<String, Object>();// 市级sum
						Map<String, Object> treeLeafDataMap = new HashMap<String, Object>();// 区县级
						String splitDateFormat = tableAggregateApp.getSplitDateFormat();
						String treeIdColumn = tableAggregateApp.getSourceTreeIdColumn().trim().toLowerCase();
						if (StringUtils.isEmpty(splitDateFormat)) {
							splitDateFormat = "yyyyMMdd";
						}
						SimpleDateFormat formatter = new SimpleDateFormat(splitDateFormat, Locale.getDefault());

						StringTokenizer token = null;
						StringBuilder keyBuffer = new StringBuilder();
						for (String split : dimensionList) {
							treeLeafDataMap.clear();
							token = new StringTokenizer(split, "|");
							final int level = token.countTokens();
							level1 = split.substring(0, split.indexOf("|") + 1);
							level2 = split.substring(0, split.indexOf("|", 2) + 1);
							logger.debug("level1: " + level1);
							logger.debug("level2: " + level2);
							logger.debug(split + " level = " + level);
							for (Map<String, Object> rowMap : dataList) {
								intValue = 0;
								intSumValue = 0;
								longValue = 0;
								longSumValue = 0;
								doubleValue = 0;
								doubleSumValue = 0;
								dimensionDate = ParamUtils.getDate(rowMap,
										tableAggregateApp.getSourceTableDateSplitColumn());
								if (dimensionDate != null) {
									treeId = (String) rowMap.get(treeIdColumn);
									text = formatter.format(dimensionDate);
									keyBuffer.delete(0, keyBuffer.length());
									keyBuffer.append(split).append(text);
									dimensions.add(text);
									canExecute = false;

									if (StringUtils.equals(treeId, split)) {
										canExecute = true;
									}
									if (canExecute) {
										Map<String, Object> dataMap = dataListMap.get(keyBuffer.toString());
										if (dataMap == null) {
											dataMap = new HashMap<String, Object>();
											dataMap.putAll(rowMap);
											dataMap.put("ex_id_", keyBuffer.toString());
											dataMap.put("ex_syncid_", syncId);
											dataMap.put("ex_sort_", sort++);
											dataMap.put("ex_level_", level);
											dataMap.put("ex_split_", text);
											dataListMap.put(keyBuffer.toString(), dataMap);
										}

										for (ColumnDefinition col : alculateColumns) {
											name = col.getColumnName().trim().toLowerCase();
											switch (col.getJavaType()) {
											case "Integer":
												intValue = ParamUtils.getInt(rowMap, name);
												if (intValue >= 0) {
													intSumValue = ParamUtils.getInt(treeLeafDataMap,
															name + "_total_" + treeId);
													intSumValue = intSumValue + intValue;
													dataMap.put(name + "_total", intSumValue);
													treeLeafDataMap.put(name + "_total_" + treeId, intSumValue);
												}
												break;
											case "Long":

												break;
											case "Double":

												break;
											default:
												break;
											}
										}
									}
								}
							}
							if (level == 2) {
								treeSumDataMap2.clear();
								treeDataMap2.clear();
								treeLeafDataMap.clear();
								logger.debug(split + " finish and clear.");
							}
							logger.debug("dataListMap size:" + dataListMap.size());
						}
					}
				}

				logger.debug("dataListMap size:" + dataListMap.size());
				if (dataListMap.size() > 0) {
					insertList.addAll(dataListMap.values());
					BulkInsertBean insertBean = new BulkInsertBean();
					targetConn.setAutoCommit(false);
					DBUtils.executeSchemaResource(targetConn, " delete from " + tableAggregateApp.getTargetTableName()
							+ " where ex_syncid_ = " + tableAggregateApp.getId());
					insertBean.bulkInsert(targetConn, tableDefinition, insertList);
					targetConn.commit();
				}

				insertList.clear();
				dataListMap.clear();
				insertList = null;
				dataListMap = null;

			}

		} catch (Exception ex) {
			// ex.printStackTrace();
			logger.error("execute sync error", ex);
			// logger.error("[insertList]:" + insertList);
			throw new RuntimeException(ex);
		} finally {

			if (srcConn != null) {
				QueryConnectionFactory.getInstance().unregister(ts, srcConn);
			}

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
