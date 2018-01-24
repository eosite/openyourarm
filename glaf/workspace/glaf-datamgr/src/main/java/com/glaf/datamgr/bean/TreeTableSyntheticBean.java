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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
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
import com.glaf.core.factory.TableFactory;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.StringTools;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.SelectSegment;
import com.glaf.datamgr.domain.TableExecution;
import com.glaf.datamgr.domain.TableSynthetic;
import com.glaf.datamgr.domain.TreeTableSynthetic;
import com.glaf.datamgr.domain.TreeTableSyntheticRule;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.jdbc.MyBatisHelper;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.service.TreeTableSyntheticService;
import com.glaf.datamgr.util.ExceptionUtils;
import com.glaf.datamgr.util.FastJsonUtils;
import com.glaf.datamgr.util.TreeTableCountDomainFactory;

public class TreeTableSyntheticBean implements java.lang.Runnable {

	protected static Configuration conf = BaseConfiguration.create();

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected long sourceDatabaseId;

	protected long targetDatabaseId;

	protected long treeTableSyntheticId;

	protected boolean result;

	public TreeTableSyntheticBean() {

	}

	public TreeTableSyntheticBean(long sourceDatabaseId, long targetDatabaseId, long treeTableSyntheticId) {
		this.sourceDatabaseId = sourceDatabaseId;
		this.targetDatabaseId = targetDatabaseId;
		this.treeTableSyntheticId = treeTableSyntheticId;
	}

	public boolean execute(long sourceDatabaseId, long targetDatabaseId, long treeTableSyntheticId) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		TreeTableSyntheticService treeTableSyntheticService = ContextFactory.getBean("treeTableSyntheticService");
		try {
			Database sourceDatabase = databaseService.getDatabaseById(sourceDatabaseId);
			Database targetDatabase = databaseService.getDatabaseById(targetDatabaseId);
			TreeTableSynthetic treeTableSynthetic = treeTableSyntheticService
					.getTreeTableSynthetic(treeTableSyntheticId);
			if (treeTableSynthetic != null) {
				Date now = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH) + 1;
				int week = calendar.get(Calendar.WEEK_OF_YEAR);
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				boolean isLastDayOfMonth = false;
				switch (month) {
				case 2:
					if (day == 28 || day == 29) {
						isLastDayOfMonth = true;
					}
					break;
				case 4:
				case 6:
				case 9:
				case 11:
					if (day == 30) {
						isLastDayOfMonth = true;
					}
					break;
				default:
					if (day == 31) {
						isLastDayOfMonth = true;
					}
					break;
				}

				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("_date_", now);
				parameter.put("_now_", now);
				parameter.put("_year_", year);
				parameter.put("_month_", month);
				parameter.put("_week_", week);
				parameter.put("_day_", day);
				parameter.put("_nowYearMonth_", DateUtils.getNowYearMonth());
				parameter.put("_nowYearMonthDay_", DateUtils.getNowYearMonthDay());

				TableDefinition tableDefinition = TreeTableCountDomainFactory
						.getTableDefinition(treeTableSynthetic.getTargetTableName());
				List<ColumnDefinition> columns = tableDefinition.getColumns();
				List<TreeTableSyntheticRule> rules = treeTableSynthetic.getRules();
				if (rules != null && !rules.isEmpty()) {
					for (TreeTableSyntheticRule rule : rules) {
						if (rule.getLocked() == 1) {
							continue;
						}
						ColumnDefinition column = new ColumnDefinition();
						column.setName(rule.getColumnName());
						column.setColumnName(rule.getColumnName());
						column.setJavaType(rule.getType());
						if (StringUtils.isEmpty(column.getJavaType())) {
							column.setJavaType("String");
						}
						if (StringUtils.equals(column.getJavaType(), "String")) {
							if (rule.getColumnSize() > 0) {
								column.setLength(rule.getColumnSize());
							} else {
								column.setLength(500);
							}
						}
						columns.add(column);
					}

					for (ColumnDefinition c : columns) {
						c.setColumnName(c.getColumnName().toLowerCase());
					}

					for (ColumnDefinition col : columns) {
						col.setPrimaryKey(false);
					}

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
					col5.setJavaType("String");
					col5.setLength(1);
					columns.add(col5);

					ColumnDefinition col6 = new ColumnDefinition();
					col6.setColumnName("_JOBNO_");
					if (DBUtils.ORACLE.equals(targetDatabase.getType())) {
						col6.setColumnName("EX_JOBNO_");
					}
					col6.setJavaType("String");
					col6.setLength(80);
					columns.add(col6);

					ColumnDefinition syntagmatic = new ColumnDefinition();
					syntagmatic.setColumnName("_SYNTAGMATIC_");
					if (DBUtils.ORACLE.equals(targetDatabase.getType())) {
						syntagmatic.setColumnName("EX_SYNTAGMATIC_");
					}
					syntagmatic.setJavaType("Long");
					columns.add(syntagmatic);

					ColumnDefinition idColumn = new ColumnDefinition();
					idColumn.setColumnName("_UUID_");
					if (DBUtils.ORACLE.equals(targetDatabase.getType())) {
						idColumn.setColumnName("EX_UUID_");
					}
					idColumn.setJavaType("String");
					idColumn.setLength(200);
					idColumn.setPrimaryKey(true);
					tableDefinition.setIdColumn(idColumn);
					tableDefinition.setColumns(columns);

					treeTableSynthetic.setColumns(columns);

					if (DBUtils.tableExists(targetDatabase.getName(), treeTableSynthetic.getTargetTableName())) {
						DBUtils.alterTable(targetDatabase.getName(), tableDefinition);
					} else {
						DBUtils.createTable(targetDatabase.getName(), tableDefinition);
					}

					String monthTargetTable = treeTableSynthetic.getTargetTableName() + DateUtils.getNowYearMonth();

					if (StringUtils.equalsIgnoreCase(treeTableSynthetic.getGenByMonth(), "D") || (isLastDayOfMonth
							&& StringUtils.equalsIgnoreCase(treeTableSynthetic.getGenByMonth(), "L"))) {
						tableDefinition.setTableName(monthTargetTable);
						if (DBUtils.tableExists(targetDatabase.getName(), monthTargetTable)) {
							DBUtils.alterTable(targetDatabase.getName(), tableDefinition);
						} else {
							DBUtils.createTable(targetDatabase.getName(), tableDefinition);
						}
					}

					TableFactory.clearCache("default", monthTargetTable);
					TableFactory.clearCache(targetDatabase.getName(), monthTargetTable);

					TableFactory.clearCache("default", treeTableSynthetic.getTargetTableName());
					TableFactory.clearCache(targetDatabase.getName(), treeTableSynthetic.getTargetTableName());

					boolean ret = this.execute(sourceDatabase, targetDatabase, treeTableSynthetic);
					if (ret) {
						if (StringUtils.equalsIgnoreCase(treeTableSynthetic.getGenByMonth(), "D") || (isLastDayOfMonth
								&& StringUtils.equalsIgnoreCase(treeTableSynthetic.getGenByMonth(), "L"))) {
							try {
								columns = DBUtils.getColumnDefinitions(targetDatabase.getName(),
										treeTableSynthetic.getTargetTableName());
								TableCopyBean tableCopyBean = new TableCopyBean();
								TableSynthetic tableSynthetic = new TableSynthetic();
								tableSynthetic.setPrimaryKey(idColumn.getColumnName());
								tableSynthetic.setSourceTableName(treeTableSynthetic.getTargetTableName());
								tableSynthetic.setTargetTableName(monthTargetTable);
								StringBuilder buff = new StringBuilder();
								for (ColumnDefinition col : columns) {
									buff.append(col.getColumnName().toLowerCase()).append(",");
								}
								tableSynthetic.setSyncColumns(buff.toString());

								if (DBUtils.tableExists(targetDatabase.getName(), monthTargetTable)) {
									String ddlStatements = " delete from " + monthTargetTable;
									DBUtils.executeSchemaResource(targetDatabase.getName(), ddlStatements);
								}

								tableCopyBean.execute(targetDatabase, targetDatabase, tableSynthetic);

								treeTableSynthetic.setTargetTableName(monthTargetTable);
								TreeTableSyntheticMetaBean metaBean = new TreeTableSyntheticMetaBean();
								metaBean.updateTreeTableSyntheticMeta(treeTableSynthetic,
										treeTableSynthetic.getRules());
							} catch (Exception ex) {
								logger.error("execute month data error", ex);
							}
						}
					}
					return ret;
				}
			}
			return false;
		} catch (Exception ex) {
			logger.error("execute error", ex);
			throw new RuntimeException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public boolean execute(Database sourceDatabase, Database targetDatabase, TreeTableSynthetic treeTableSynthetic) {
		if (!(StringUtils.startsWithIgnoreCase(treeTableSynthetic.getTargetTableName(), "tree_table_count")
				|| StringUtils.startsWithIgnoreCase(treeTableSynthetic.getTargetTableName(), "useradd_")
				|| StringUtils.startsWithIgnoreCase(treeTableSynthetic.getTargetTableName(), "etl_")
				|| StringUtils.startsWithIgnoreCase(treeTableSynthetic.getTargetTableName(), "sync_")
				|| StringUtils.startsWithIgnoreCase(treeTableSynthetic.getTargetTableName(), "tree_table_")
				|| StringUtils.startsWithIgnoreCase(treeTableSynthetic.getTargetTableName(), "tmp"))) {
			return false;
		}

		logger.debug("----------------------------------------------------------------");
		logger.debug("----------------------TreeTableSyntheticBean execute------------");
		logger.debug("----------------------------------------------------------------");

		DataSetService dataSetService = ContextFactory.getBean("dataSetService");

		StringBuilder insertBuffer = new StringBuilder();

		Map<String, Map<String, Object>> sourceDataMap = new ConcurrentHashMap<String, Map<String, Object>>();
		List<TreeTableSyntheticRule> syncColumns = treeTableSynthetic.getRules();
		logger.debug("sync columns:" + syncColumns);

		String idColumnName = null;
		ColumnDefinition idColumn = null;

		String sourceIdColumnName = treeTableSynthetic.getSourceIdColumn().toLowerCase();// 树表主键列

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

		String jobNo = treeTableSynthetic.getTargetTableName() + "_" + DateUtils.getNowYearMonthDayHHmmss();

		Map<String, TreeTableSyntheticRule> columnMappingMap = new HashMap<String, TreeTableSyntheticRule>();

		AtomicInteger increment = new AtomicInteger(1);

		Connection sourceConn = null;
		PreparedStatement sourcePsmt = null;
		ResultSet sourceRS = null;

		Connection targetConn = null;
		PreparedStatement targetInsertPsmt = null;
		try {

			List<ColumnDefinition> srcColumns = treeTableSynthetic.getColumns();

			List<ColumnDefinition> destColumns = DBUtils.getColumnDefinitions(targetDatabase.getName(),
					treeTableSynthetic.getTargetTableName());
			List<String> primaryKeys2 = DBUtils.getUpperCasePrimaryKeys(targetDatabase.getName(),
					treeTableSynthetic.getTargetTableName());

			List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();

			int position = 1;
			for (ColumnDefinition col : destColumns) {
				if (primaryKeys2.contains(col.getColumnName().toUpperCase())) {
					idColumn = col;
					idColumn.setPosition(destColumns.size());
					idColumnName = col.getColumnName().toLowerCase();
					col.setColumnName(idColumnName);
				} else {
					col.setPosition(position++);
					col.setColumnName(col.getColumnName().toLowerCase());
					columns.add(col);
				}
				logger.debug("->column:" + col.getColumnName());
			}

			sourceConn = DBConnectionFactory.getConnection(sourceDatabase.getName());

			int k = 1;
			String key = null;
			String columnName = null;
			String columnAlias = null;
			ColumnDefinition cm = null;
			int len = srcColumns.size();

			StringBuilder sqlBuffer = new StringBuilder();
			sqlBuffer.append(" select ").append(treeTableSynthetic.getSourceIdColumn()).append(" as ")
					.append(treeTableSynthetic.getSourceIdColumn().toLowerCase());
			if (StringUtils.isNotEmpty(treeTableSynthetic.getSourceParentIdColumn())) {
				sqlBuffer.append(", ").append(treeTableSynthetic.getSourceParentIdColumn()).append(" as ")
						.append(treeTableSynthetic.getSourceParentIdColumn().toLowerCase());
			}

			if (!StringUtils.equalsIgnoreCase(treeTableSynthetic.getSourceIdColumn(),
					treeTableSynthetic.getSourceIndexIdColumn())) {
				if (StringUtils.isNotEmpty(treeTableSynthetic.getSourceIndexIdColumn())) {
					sqlBuffer.append(", ").append(treeTableSynthetic.getSourceIndexIdColumn()).append(" as ")
							.append(treeTableSynthetic.getSourceIndexIdColumn().toLowerCase());
				}
			}

			if (StringUtils.isNotEmpty(treeTableSynthetic.getSourceParentIdColumn())) {
				sqlBuffer.append(", ").append(treeTableSynthetic.getSourceParentIdColumn()).append(" as ")
						.append(treeTableSynthetic.getSourceParentIdColumn().toLowerCase());
			}

			if (StringUtils.isNotEmpty(treeTableSynthetic.getSourceTextColumn())) {
				sqlBuffer.append(", ").append(treeTableSynthetic.getSourceTextColumn()).append(" as ")
						.append(treeTableSynthetic.getSourceTextColumn().toLowerCase());
			}

			if (StringUtils.isNotEmpty(treeTableSynthetic.getSourceTreeIdColumn())) {
				sqlBuffer.append(", ").append(treeTableSynthetic.getSourceTreeIdColumn()).append(" as ")
						.append(treeTableSynthetic.getSourceTreeIdColumn().toLowerCase());
			}

			if (StringUtils.isNotEmpty(treeTableSynthetic.getSourceWbsIndexColumn())) {
				sqlBuffer.append(", ").append(treeTableSynthetic.getSourceWbsIndexColumn()).append(" as ")
						.append(treeTableSynthetic.getSourceWbsIndexColumn().toLowerCase());
			}

			sqlBuffer.append(" from ").append(treeTableSynthetic.getSourceTableName());

			if (StringUtils.isNotEmpty(treeTableSynthetic.getSqlCriteria())) {
				sqlBuffer.append(" where 1=1 ");
				if (StringUtils.startsWithIgnoreCase(treeTableSynthetic.getSqlCriteria().trim(), "and")) {
					sqlBuffer.append(treeTableSynthetic.getSqlCriteria());
				} else {
					sqlBuffer.append(" and ").append(treeTableSynthetic.getSqlCriteria());
				}
			}

			sqlBuffer.append(" order by ").append(treeTableSynthetic.getSourceIdColumn());

			if (!DBUtils.isLegalQuerySql(sqlBuffer.toString())) {
				throw new RuntimeException("sql statement illegal");
			}

			SqlExecutor sqlExecutor = DBUtils.replaceSQL(sqlBuffer.toString(), parameter);

			insertBuffer.append(" insert into ").append(treeTableSynthetic.getTargetTableName()).append(" ( ");

			len = columns.size();

			k = 0;
			for (ColumnDefinition c : columns) {
				columnName = c.getColumnName().toLowerCase();
				insertBuffer.append(columnName).append(" , ");
			}

			insertBuffer.append(idColumnName).append(") values ( ");
			for (k = 1; k <= len; k++) {
				insertBuffer.append(" ?, ");
			}

			insertBuffer.append(" ? ) ");

			logger.debug("select sql :" + sqlExecutor.getSql());
			logger.debug("insert sql :" + insertBuffer.toString());

			sourcePsmt = sourceConn.prepareStatement(sqlExecutor.getSql());
			if (sqlExecutor.getParameter() != null) {
				List<Object> values = (List<Object>) sqlExecutor.getParameter();
				JdbcUtils.fillStatement(sourcePsmt, values);
			}
			sourceRS = sourcePsmt.executeQuery();

			MyBatisHelper helper = new MyBatisHelper();
			List<Map<String, Object>> list = helper.getResults(sourceRS);

			JdbcUtils.close(sourceRS);
			JdbcUtils.close(sourcePsmt);
			if (StringUtils.isEmpty(treeTableSynthetic.getSourceTableExecutionIds())) {
				JdbcUtils.close(sourceConn);
			}

			if (list != null && !list.isEmpty()) {
				logger.debug("主表记录数:" + list.size());
				Object value = null;
				Object orgiValue = null;
				StringTokenizer token = null;
				StringBuilder buff = new StringBuilder();
				int intToken = sourceDatabase.getIntToken();
				String pkColumnName = treeTableSynthetic.getSourceIdColumn().toLowerCase();
				logger.debug("pkColumnName:" + pkColumnName);
				for (Map<String, Object> dataMap : list) {
					// logger.debug("" + dataMap);
					key = ParamUtils.getString(dataMap, pkColumnName);
					if (StringUtils.isNotEmpty(key)) {
						value = dataMap.get(treeTableSynthetic.getSourceIdColumn());
						dataMap.put("primarykey_", value);

						value = dataMap.get(treeTableSynthetic.getSourceIndexIdColumn());
						dataMap.put("indexid_", value);

						if (value != null && StringUtils.isNumeric(value.toString())) {
							dataMap.put("_syntagmatic_", Long.parseLong(sourceDatabase.getIntToken()
									+ StringTools.getDigit7Id(Integer.parseInt(value.toString()))));
							dataMap.put("ex_syntagmatic_", dataMap.get("_syntagmatic_"));
						}

						value = dataMap.get(treeTableSynthetic.getSourceParentIdColumn());
						dataMap.put("parentid_", value);

						value = dataMap.get(treeTableSynthetic.getSourceTextColumn());
						dataMap.put("indexname_", value);

						value = dataMap.get(treeTableSynthetic.getSourceTreeIdColumn());
						dataMap.put("treeid_", value);

						value = dataMap.get(treeTableSynthetic.getSourceWbsIndexColumn());
						dataMap.put("wbsindex_", value);

						if (dataMap.get("indexid_") != null) {
							orgiValue = dataMap.get("indexid_");
							if (StringUtils.isNumeric(orgiValue.toString())) {
								dataMap.put("orig_index_id", orgiValue);
								Long newValue = Long.parseLong(intToken + orgiValue.toString());
								dataMap.put("indexid_", newValue);
							}
						}

						if (dataMap.get("parentid_") != null) {
							orgiValue = dataMap.get("parentid_");
							if (StringUtils.isNumeric(orgiValue.toString())) {
								dataMap.put("orig_parent_id", orgiValue);
								Long newValue = Long.parseLong(intToken + orgiValue.toString());
								dataMap.put("parentid_", newValue);
							}
						}

						if (dataMap.get("treeid_") != null) {
							orgiValue = dataMap.get("treeid_");
							String text = orgiValue.toString();
							buff.delete(0, buff.length());
							token = new StringTokenizer(text, "|");
							while (token.hasMoreTokens()) {
								buff.append(intToken).append(token.nextToken()).append("|");
							}
							if (!text.endsWith("|") && buff.length() > 1) {
								buff.delete(buff.length() - 1, buff.length());
							}
							dataMap.put("orig_tree_id", orgiValue);
							dataMap.put("treeid_", buff.toString());
						}
						// logger.debug("->" + dataMap);
						// logger.debug(treeTableSynthetic.getSourceTreeIdColumn()+"->"+dataMap.get(treeTableSynthetic.getSourceTreeIdColumn().toLowerCase()));
						sourceDataMap.put(key, dataMap);
					}
				}

				logger.debug("sourceDataMap size:" + sourceDataMap.size());

				JSONArray array = null;
				String sourceId = null;
				JSONObject json = null;
				Map<String, Object> dataMap = null;
				Map<String, String> realColumnMap = treeTableSynthetic.getColumnMap();
				Map<String, String> columnAliasMap = treeTableSynthetic.getColumnAliasMap();
				for (ColumnDefinition col : columns) {
					if (columnAliasMap.get(col.getColumnName().toLowerCase()) != null) {
						col.setColumnLabel(columnAliasMap.get(col.getColumnName().toLowerCase()));
					}
				}

				DataSetBuilder builder = new DataSetBuilder();
				Map<String, String> dsMap = new HashMap<String, String>();
				Map<String, SelectSegment> selectMap = new HashMap<String, SelectSegment>();

				List<TreeTableSyntheticRule> rules = treeTableSynthetic.getRules();
				for (TreeTableSyntheticRule rule : rules) {
					if (rule.getLocked() == 1) {
						continue;
					}
					logger.debug("-----------------------rule------------------------");
					logger.debug("处理规则:" + rule.getTitle());
					String mappingToSourceIdColumn = rule.getMappingToSourceIdColumn().toLowerCase();
					String datasetId = rule.getDatasetId();
					if (StringUtils.isNotEmpty(datasetId)) {
						if (StringUtils.isNotEmpty(rule.getMappingToTargetAlias())) {
							columnMappingMap.put(rule.getColumnName().toLowerCase(), rule);
						}
						DataSet dataSet = dataSetService.getDataSet(datasetId);
						if (dataSet != null && dsMap.get(datasetId) == null) {
							selectMap.clear();
							Map<String, String> columnMap = new HashMap<String, String>();
							List<SelectSegment> segments = dataSet.getSelectSegments();
							for (SelectSegment segment : segments) {
								if (StringUtils.equals(segment.getOutput(), "true")) {
									for (TreeTableSyntheticRule r : syncColumns) {
										if (r.getLocked() == 1) {
											continue;
										}
										if (StringUtils.equals(r.getDatasetId(), datasetId)) {
											columnMap.put(segment.getColumnName().toLowerCase(),
													segment.getColumnLabel().toLowerCase());
											selectMap.put(segment.getColumnLabel().toLowerCase(), segment);
										}
									}
								}
							}
							logger.debug("处理数据集:" + dataSet.getName());
							logger.debug("idColumn:" + mappingToSourceIdColumn + "->"
									+ columnMap.get(mappingToSourceIdColumn));
							try {
								logger.debug("数据集所在库:" + sourceDatabase.getTitle());
								dataSet.setDatabaseId(sourceDatabase.getId());
								array = builder.getJsonArray(sourceDatabase.getName(), dataSet, parameter);
								logger.debug("columnMap:" + columnMap);
								logger.debug("------------------------data--------------------------");
								// logger.debug(sourceDatabase.getDbname() +
								// "->" + array.toJSONString());
								logger.debug(dataSet.getName() + "array:" + array);
								if (array != null && !array.isEmpty()) {
									int size = array.size();
									logger.debug(dataSet.getName() + "记录条数：" + size);
									for (int i = 0; i < size; i++) {
										json = array.getJSONObject(i);
										json = FastJsonUtils.addLowerCaseKey(json);
										logger.debug("json:" + json.toJSONString());
										if (json.get(columnMap.get(mappingToSourceIdColumn)) != null) {
											sourceId = json.get(columnMap.get(mappingToSourceIdColumn)).toString();
											// logger.debug("sourceId:" +
											// sourceId);
											dataMap = sourceDataMap.get(sourceId);
											if (dataMap != null) {
												this.processDataMap(datasetId, json, dataMap, syncColumns, columnMap,
														realColumnMap);
											}
										}
									}
								}
								dsMap.put(datasetId, datasetId);
							} catch (Exception ex) {
								ex.printStackTrace();
								logger.error("get dataset '" + datasetId + "' error:" + ex);
							}
						}
					}
					logger.debug("规则'" + rule.getTitle() + "'处理完成。");
					logger.debug("#################################################");
				}
			}

			targetConn = DBConnectionFactory.getConnection(targetDatabase.getName());
			targetConn.setAutoCommit(false);
			logger.debug("目标数据库已经连接:" + targetDatabase.getDbname());

			/**
			 * 删除掉目标表数据，再重新抓取
			 */
			if (StringUtils.equalsIgnoreCase(treeTableSynthetic.getDeleteFetch(), "Y")) {
				String sql2 = " delete from " + treeTableSynthetic.getTargetTableName() + " where DATABASEID_ = "
						+ sourceDatabase.getId() + " and SYNTHETICID_ = " + treeTableSynthetic.getId();
				DBUtils.executeSchemaResourceIgnoreException(targetConn, sql2);
				// targetConn.commit();
			}

			if (sourceDataMap.size() > 0) {
				targetInsertPsmt = targetConn.prepareStatement(insertBuffer.toString());

				int index = 0;
				TreeTableSyntheticRule rule = null;
				Map<String, String> keyMap = new HashMap<String, String>();
				Iterator<Map<String, Object>> iterator = sourceDataMap.values().iterator();
				while (iterator.hasNext()) {
					Map<String, Object> dataMap = iterator.next();
					if (ParamUtils.getString(dataMap, treeTableSynthetic.getSourceIdColumn()) != null) {
						dataMap.put("_uuid_", sourceDatabase.getIntToken() + "_"
								+ ParamUtils.getString(dataMap, treeTableSynthetic.getSourceIdColumn()));
						dataMap.put("ex_uuid_", dataMap.get("_uuid_"));
					} else if (ParamUtils.getString(dataMap, treeTableSynthetic.getSourceIndexIdColumn()) != null) {
						dataMap.put("_uuid_", sourceDatabase.getIntToken() + "_"
								+ ParamUtils.getString(dataMap, treeTableSynthetic.getSourceIndexIdColumn()));
						dataMap.put("ex_uuid_", dataMap.get("_uuid_"));
					} else {
						dataMap.put("_uuid_", sourceDatabase.getIntToken() + "_" + increment.incrementAndGet());
						dataMap.put("ex_uuid_", dataMap.get("_uuid_"));
					}
					if (StringUtils.equals(treeTableSynthetic.getGenNewPrimaryKey(), "Y")) {
						String _id = dataMap.get("_uuid_").toString();
						_id = treeTableSynthetic.getId() + "_" + sourceDatabase.getIntToken() + "_" + _id;
						if (keyMap.get(_id) != null) {
							continue;
						}
						dataMap.put("_uuid_", _id);
						dataMap.put("ex_uuid_", _id);
						keyMap.put(_id, _id);
					}
					dataMap.put("uuid_", dataMap.get("_uuid_"));
					dataMap.put("ex_uuid_", dataMap.get("_uuid_"));
					dataMap.put("ex_jobno_", jobNo);
					dataMap.put("_jobno_", jobNo);
					dataMap.put("jobno_", jobNo);
					dataMap.put("syntheticid_", treeTableSynthetic.getId());
					dataMap.put("databaseid_", sourceDatabase.getId());
					dataMap.put("name_", sourceDatabase.getTitle());
					dataMap.put("discriminator_", sourceDatabase.getDiscriminator());
					dataMap.put("section_", sourceDatabase.getSection());
					dataMap.put("mapping_", sourceDatabase.getMapping());
					dataMap.put("ex_aggrkey_", sourceDatabase.getId() + "_" + dataMap.get(sourceIdColumnName));
					dataMap.put("_aggrkey_", sourceDatabase.getId() + "_" + dataMap.get(sourceIdColumnName));
					dataMap.put("aggrkey_", sourceDatabase.getId() + "_" + dataMap.get(sourceIdColumnName));
					if (index % 10 == 0) {
						logger.debug("dataMap:" + dataMap);
					}
					for (int j = 0; j < columns.size(); j++) {
						cm = columns.get(j);
						columnName = cm.getColumnName();
						columnAlias = cm.getColumnLabel();
						if (columnAlias == null) {
							columnAlias = columnName;
						}
						columnName = columnName.toLowerCase();
						columnAlias = columnAlias.toLowerCase();
						rule = columnMappingMap.get(columnName);
						if (rule != null && StringUtils.isNotEmpty(rule.getMappingToTargetAlias())) {
							if (dataMap.get(rule.getMappingToTargetAlias().toLowerCase()) != null) {
								columnAlias = rule.getMappingToTargetAlias().toLowerCase();
							}
						}
						switch (cm.getJavaType()) {
						case "Integer":
							int intValue = 0;
							if (dataMap.containsKey(columnAlias)) {
								intValue = ParamUtils.getInt(dataMap, columnAlias);
							} else {
								intValue = ParamUtils.getInt(dataMap, columnName);
							}
							targetInsertPsmt.setInt(cm.getPosition(), intValue);
							break;
						case "Long":
							long longValue = 0;
							if (dataMap.containsKey(columnAlias)) {
								longValue = ParamUtils.getLong(dataMap, columnAlias);
							} else {
								longValue = ParamUtils.getLong(dataMap, columnName);
							}
							targetInsertPsmt.setLong(cm.getPosition(), longValue);
							break;
						case "Double":
							double doubleValue = 0;
							if (dataMap.containsKey(columnAlias)) {
								doubleValue = ParamUtils.getDouble(dataMap, columnAlias);
							} else {
								doubleValue = ParamUtils.getDouble(dataMap, columnName);
							}
							targetInsertPsmt.setDouble(cm.getPosition(), doubleValue);
							break;
						case "Date":
							Timestamp timeValue = null;
							if (dataMap.containsKey(columnAlias)) {
								timeValue = DateUtils.toTimestamp(ParamUtils.getDate(dataMap, columnAlias));
							} else {
								timeValue = DateUtils.toTimestamp(ParamUtils.getDate(dataMap, columnName));
							}
							if (timeValue != null) {
								targetInsertPsmt.setTimestamp(cm.getPosition(), timeValue);
							} else {
								targetInsertPsmt.setObject(cm.getPosition(), null);
							}
							break;
						case "String":
							String stringValue = null;
							if (dataMap.containsKey(columnAlias)) {
								stringValue = ParamUtils.getString(dataMap, columnAlias);
							} else {
								stringValue = ParamUtils.getString(dataMap, columnName);
							}
							targetInsertPsmt.setString(cm.getPosition(), stringValue);
							break;
						default:
							Object objectValue = null;
							if (dataMap.containsKey(columnAlias)) {
								objectValue = ParamUtils.getObject(dataMap, columnAlias);
							} else {
								objectValue = ParamUtils.getObject(dataMap, columnName);
							}
							targetInsertPsmt.setObject(cm.getPosition(), objectValue);
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
					index++;
					if (index > 0 && index % 1000 == 0) {
						targetInsertPsmt.executeBatch();
						// targetConn.commit();
					}
				}

				if (targetInsertPsmt != null) {
					targetInsertPsmt.executeBatch();
					targetConn.commit();
					logger.debug("execute insert batch.");
				}

				sourceDataMap.clear();
				sourceDataMap = null;
			}

			parameter.put("ex_jobno_", jobNo);
			parameter.put("_jobno_", jobNo);
			parameter.put("jobno_", jobNo);

			if (StringUtils.isNotEmpty(treeTableSynthetic.getSourceTableExecutionIds())) {
				TableExecutionBean tableExecutionBean = new TableExecutionBean();
				List<String> executionIds = StringTools.split(treeTableSynthetic.getSourceTableExecutionIds());
				List<TableExecution> executions = tableExecutionBean.getTableExecutions(executionIds);
				if (executions != null && !executions.isEmpty()) {
					sourceConn.setAutoCommit(false);
					tableExecutionBean.execute(sourceConn, executions, parameter);
					sourceConn.commit();
				}
			}

			if (StringUtils.isNotEmpty(treeTableSynthetic.getTargetTableExecutionIds())) {
				TableExecutionBean tableExecutionBean = new TableExecutionBean();
				List<String> executionIds = StringTools.split(treeTableSynthetic.getTargetTableExecutionIds());
				List<TableExecution> executions = tableExecutionBean.getTableExecutions(executionIds);
				if (executions != null && !executions.isEmpty()) {
					tableExecutionBean.execute(targetConn, executions, parameter);
					targetConn.commit();
				}
			}

			return true;

		} catch (Exception ex) {
			ExceptionUtils.addMsg("treetable_synthetic_" + treeTableSynthetic.getId(), ex.getMessage());
			logger.error("execute sql error", ex);
			// ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			sourceDataMap = null;

			JdbcUtils.close(sourceRS);
			JdbcUtils.close(sourcePsmt);
			JdbcUtils.close(sourceConn);

			JdbcUtils.close(targetInsertPsmt);
			JdbcUtils.close(targetConn);
		}
	}

	/**
	 * 处理其中一条数据
	 * 
	 * @param datasetId
	 *            数据集编号
	 * @param json
	 *            JSON数据
	 * @param dataMap
	 *            目标数据
	 * @param rules
	 *            规则集
	 * @param columnMap
	 *            数据集对应的列名映射
	 * @param realColumnMap
	 *            目标表的列名
	 */
	protected void processDataMap(String datasetId, JSONObject json, Map<String, Object> dataMap,
			List<TreeTableSyntheticRule> rules, Map<String, String> columnMap, Map<String, String> realColumnMap) {
		String mappingColumn = null;
		String mappingColumnAlias = null;
		String columnLabel = null;
		String key = null;
		Object value = null;

		Iterator<Entry<String, Object>> iterator = json.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			key = (String) entry.getKey();
			value = entry.getValue();
			if (value != null && dataMap.get(key) == null) {
				dataMap.put(key, value);
			}
		}

		for (TreeTableSyntheticRule rule : rules) {
			if (rule.getLocked() == 1) {
				continue;
			}
			/**
			 * 匹配同一数据集的数据，不同数据集字段名一样就有冲突，后面的会覆盖前面的，可以通过别名来解决。
			 * 
			 */
			if (StringUtils.equals(rule.getDatasetId(), datasetId)) {
				mappingColumn = rule.getMappingToTargetColumn().toLowerCase();
				mappingColumnAlias = rule.getMappingToTargetAlias();
				if (StringUtils.isNotEmpty(mappingColumnAlias)) {
					mappingColumnAlias = mappingColumnAlias.toLowerCase();
				}
				// 取得数据集的列别名
				columnLabel = columnMap.get(mappingColumn);
				value = json.get(columnLabel);// 通过别名获取JSON数据值
				if (value != null) {
					dataMap.put(columnLabel, value);
					dataMap.put(mappingColumn, value);
					dataMap.put(rule.getColumnName(), value);
					dataMap.put(realColumnMap.get(mappingColumn), value);
					if (StringUtils.isNotEmpty(mappingColumnAlias)) {
						dataMap.put(mappingColumnAlias, value);// 放到映射的列别名中
					}
					// logger.debug(columnLabel+"\t"+value);
					// logger.debug(realColumnMap.get(mappingColumn)+"\t"+value);
				} else {
					value = json.get(columnMap.get(mappingColumn.toLowerCase()));
					if (value != null) {
						dataMap.put(columnLabel, value);
						dataMap.put(mappingColumn, value);
						dataMap.put(rule.getColumnName(), value);
						dataMap.put(realColumnMap.get(mappingColumn), value);
						if (StringUtils.isNotEmpty(mappingColumnAlias)) {
							dataMap.put(mappingColumnAlias, value);
						}
					} else {
						value = json.get(columnMap.get(mappingColumn.toUpperCase()));
						if (value != null) {
							dataMap.put(columnLabel, value);
							dataMap.put(mappingColumn, value);
							dataMap.put(rule.getColumnName(), value);
							dataMap.put(realColumnMap.get(mappingColumn), value);
							if (StringUtils.isNotEmpty(mappingColumnAlias)) {
								dataMap.put(mappingColumnAlias, value);
							}
						} else {
							value = json.get(mappingColumn);
							if (value != null) {
								dataMap.put(columnLabel, value);
								dataMap.put(mappingColumn, value);
								dataMap.put(rule.getColumnName(), value);
								dataMap.put(realColumnMap.get(mappingColumn), value);
								if (StringUtils.isNotEmpty(mappingColumnAlias)) {
									dataMap.put(mappingColumnAlias, value);
								}
							}
						}
					}
				}
			}
		}
		logger.debug("json data:" + json.toJSONString());
		logger.debug("处理后的数据:" + dataMap);
	}

	public boolean getResult() {
		return result;
	}

	public void run() {
		result = this.execute(sourceDatabaseId, targetDatabaseId, treeTableSyntheticId);
	}

}
