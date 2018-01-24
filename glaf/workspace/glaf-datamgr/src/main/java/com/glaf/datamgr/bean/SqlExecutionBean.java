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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.SysDataLog;
import com.glaf.core.domain.util.SysDataLogTableUtils;
import com.glaf.core.factory.SysLogFactory;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.query.DatabaseQuery;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FieldType;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.StringTools;

import com.glaf.datamgr.domain.SqlDefinition;
import com.glaf.datamgr.query.SqlDefinitionQuery;
import com.glaf.datamgr.service.SqlDefinitionService;
import com.glaf.datamgr.util.SqlExecutionThreadLocal;
import com.glaf.datamgr.util.SqlResultDomainFactory;

public class SqlExecutionBean {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void execute(String actorId, long sqlDefId) {
		logger.debug("-----------------------SqlExecutionBean.execute----------------------");
		SqlDefinitionService sqlDefinitionService = ContextFactory.getBean("sqlDefinitionService");

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		JdbcBatchExecution batch = new JdbcBatchExecution();
		SqlDefinition sqlDefinition = sqlDefinitionService.getSqlDefinition(sqlDefId);
		DatabaseQuery query = new DatabaseQuery();
		query.active("1");
		long projectId = 0;
		long start = System.currentTimeMillis();
		int runDay = DateUtils.getNowYearMonthDay();
		String jobNo = SqlExecutionThreadLocal.getJobNo();
		List<Database> databases = config.getDatabaseService().list(query);

		if (StringUtils.equalsIgnoreCase(sqlDefinition.getDeleteFetch(), "Y")) {
			try {
				String tablename = "SYS_SQL_RESULT";
				if (StringUtils.isNotEmpty(sqlDefinition.getTargetTableName())
						&& StringUtils.startsWithIgnoreCase(sqlDefinition.getTargetTableName(), "SQL_RESULT")) {
					tablename = sqlDefinition.getTargetTableName();
				}
				if (DBUtils.tableExists(tablename)) {
					batch.deleteAll(sqlDefinition, "QXT", runDay, tablename);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		try {
			String tablename = "LOG_" + runDay;
			if (!DBUtils.tableExists(tablename)) {
				SysDataLogTableUtils.createTable(tablename);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		for (Database database : databases) {
			if (config.checkConfig(database)) {
				List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
				List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();

				Connection connection = null;
				PreparedStatement psmt = null;
				ResultSetMetaData rsmd = null;
				ResultSet rs = null;
				try {
					connection = DBConnectionFactory.getConnection(database.getName());

					if (StringUtils.equals(sqlDefinition.getAggregationFlag(), "C")) {
						if (StringUtils.isNotEmpty(sqlDefinition.getCountSql())
								&& DBUtils.isAllowedSql(sqlDefinition.getCountSql())) {
							psmt = connection.prepareStatement(sqlDefinition.getCountSql());
							rs = psmt.executeQuery();
							if (rs.next()) {
								Map<String, Object> rowMap = new HashMap<String, Object>();
								rowMap.put("count", rs.getInt(1));
								rowMap.put("databaseId", database.getId());
								rowMap.put("operation", "QXT");
								rowMap.put("type", "QXT");
								resultList.add(rowMap);
							}
						}
					} else if (StringUtils.equals(sqlDefinition.getAggregationFlag(), "A")) {
						if (StringUtils.isNotEmpty(sqlDefinition.getCountSql())
								&& DBUtils.isAllowedSql(sqlDefinition.getCountSql())) {
							psmt = connection.prepareStatement(sqlDefinition.getCountSql());
							rs = psmt.executeQuery();
							if (rs.next()) {
								Map<String, Object> rowMap = new HashMap<String, Object>();
								rowMap.put("value", rs.getDouble(1));
								rowMap.put("databaseId", database.getId());
								rowMap.put("operation", "QXT");
								rowMap.put("type", "QXT");
								resultList.add(rowMap);
							}
						}
					} else if (StringUtils.equals(sqlDefinition.getAggregationFlag(), "S")) {
						if (StringUtils.isNotEmpty(sqlDefinition.getCountSql())
								&& DBUtils.isAllowedSql(sqlDefinition.getCountSql())) {
							psmt = connection.prepareStatement(sqlDefinition.getCountSql());
							rs = psmt.executeQuery();
							if (rs.next()) {
								Map<String, Object> rowMap = new HashMap<String, Object>();
								rowMap.put("value", rs.getDouble(1));
								rowMap.put("databaseId", database.getId());
								rowMap.put("operation", "QXT");
								rowMap.put("type", "QXT");
								resultList.add(rowMap);
							}
						}
					} else {
						if (StringUtils.isNotEmpty(sqlDefinition.getSql())
								&& DBUtils.isAllowedSql(sqlDefinition.getSql())) {
							psmt = connection.prepareStatement(sqlDefinition.getSql());
							rs = psmt.executeQuery();
							if (columns.isEmpty()) {
								rsmd = rs.getMetaData();
								int count = rsmd.getColumnCount();
								for (int i = 1; i <= count; i++) {
									int sqlType = rsmd.getColumnType(i);
									ColumnDefinition column = new ColumnDefinition();
									column.setIndex(i);
									column.setColumnLabel(rsmd.getColumnLabel(i));
									column.setColumnName(rsmd.getColumnName(i));
									column.setJavaType(FieldType.getJavaType(sqlType));
									column.setPrecision(rsmd.getPrecision(i));
									column.setScale(rsmd.getScale(i));
									column.setName(StringTools.camelStyle(column.getColumnLabel().toLowerCase()));
									if (column.getScale() == 0 && sqlType == Types.NUMERIC) {
										column.setJavaType("Long");
									}
									if (StringUtils.equals(column.getJavaType(), "String")) {
										if (column.getPrecision() > 0) {
											column.setLength(column.getPrecision());
										} else {
											column.setLength(250);
										}
									}
									if (!columns.contains(column)) {
										columns.add(column);
									}

									logger.debug(column.getColumnName() + " sqlType:" + sqlType + " precision:"
											+ column.getPrecision() + " scale:" + column.getScale());
								}
							}

							int index = 0;

							while (rs.next() && index < 100000) {
								Map<String, Object> rowMap = new HashMap<String, Object>();

								index++;

								Iterator<ColumnDefinition> iterator = columns.iterator();
								while (iterator.hasNext()) {
									ColumnDefinition column = iterator.next();
									String columnLable = column.getColumnLabel();
									String columnName = column.getColumnName();
									if (StringUtils.isEmpty(columnName)) {
										columnName = column.getColumnLabel();
									}
									columnName = columnName.toLowerCase();
									String javaType = column.getJavaType();

									if ("String".equals(javaType)) {
										String value = rs.getString(column.getIndex());
										if (value != null) {
											value = value.trim();
										}
										rowMap.put(columnLable, value);
									} else if ("Integer".equals(javaType)) {
										Integer value = rs.getInt(column.getIndex());
										if (value != null) {
											rowMap.put(columnLable, new Double(value));
										}
									} else if ("Long".equals(javaType)) {
										Long value = rs.getLong(column.getIndex());
										if (value != null) {
											rowMap.put(columnLable, new Double(value));
										}
									} else if ("Double".equals(javaType)) {
										Double d = rs.getDouble(column.getIndex());
										rowMap.put(columnLable, d);
									} else {
										Object value = rs.getObject(column.getIndex());
										if (value != null) {
											if (value instanceof String) {
												value = (String) value.toString().trim();
											}
										}
										rowMap.put(columnLable, value);
									}
								}

								rowMap.put("databaseId", database.getId());
								rowMap.put("operation", "QXT");
								rowMap.put("type", "QXT");

								resultList.add(rowMap);
							}
						}
					}
				} catch (SQLException ex) {
					logger.error("query sql:" + sqlDefinition.getSql());
					logger.error("execute sql error", ex);
				} finally {
					JdbcUtils.close(rs);
					JdbcUtils.close(psmt);
					JdbcUtils.close(connection);
				}
				if (resultList.size() > 0) {
					String tableName = "SYS_SQL_RESULT";
					try {
						if (StringUtils.isNotEmpty(sqlDefinition.getTargetTableName())
								&& StringUtils.startsWithIgnoreCase(sqlDefinition.getTargetTableName(), "SQL_RESULT")) {
							tableName = sqlDefinition.getTargetTableName();
						}
						if (!DBUtils.tableExists(tableName)) {
							SqlResultDomainFactory.createTable(tableName, columns);
						}

						this.saveResults(actorId, projectId, tableName, sqlDefinition, runDay, jobNo, columns,
								resultList);
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error("save results error:" + ex);
					}
				}
			}
			logger.debug("total time(ms):" + (System.currentTimeMillis() - start));
		}
	}

	public void executeAll(String actorId) {
		SqlDefinitionService sqlDefinitionService = ContextFactory.getBean("sqlDefinitionService");
		SqlDefinitionQuery q = new SqlDefinitionQuery();
		q.setLocked(0);
		q.setFetchFlag("Y");
		List<SqlDefinition> list = sqlDefinitionService.list(q);
		if (list != null && list.size() > 0) {
			Collections.sort(list);
			for (SqlDefinition sqlDef : list) {
				if (StringUtils.equals(sqlDef.getOperation(), "select")) {
					this.execute(actorId, sqlDef.getId());
				}
			}
		}
	}

	public void executeAllAsync(String actorId) {
		SqlDefinitionService sqlDefinitionService = ContextFactory.getBean("sqlDefinitionService");
		SqlDefinitionQuery q = new SqlDefinitionQuery();
		q.setLocked(0);
		q.setFetchFlag("Y");
		List<SqlDefinition> list = sqlDefinitionService.list(q);
		if (list != null && list.size() > 0) {
			Collections.sort(list);
			for (SqlDefinition sqlDef : list) {
				if (StringUtils.equals(sqlDef.getOperation(), "select")) {
					SqlExecutionThread command = new SqlExecutionThread(actorId, sqlDef.getId());
					com.glaf.core.util.ThreadFactory.execute(command);
				}
			}
		}
	}

	private void saveResults(String actorId, long projectId, String tablename, SqlDefinition sqlDef, int runDay,
			String jobNo, List<ColumnDefinition> columns, List<Map<String, Object>> resultList) {
		long start = System.currentTimeMillis();
		int total = 0;

		if (StringUtils.isNotEmpty(sqlDef.getTargetTableName())
				&& StringUtils.startsWithIgnoreCase(sqlDef.getTargetTableName(), "SQL_RESULT")) {
			tablename = sqlDef.getTargetTableName();
		}
		JdbcBatchExecution batch = new JdbcBatchExecution();
		List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
		try {
			if (resultList.size() < 20000) {
				batch.execute(actorId, projectId, tablename, sqlDef, jobNo, columns, resultList);
				logger.info(sqlDef.getTitle() + " 已经成功导入数据。");
				resultList.clear();
			} else {
				int len = resultList.size();
				for (int i = 0; i < len; i++) {
					if (i > 0 && i % 20000 == 0) {
						boolean success = false;
						int retry = 0;
						while (retry < 2 && !success) {
							try {
								retry++;
								batch.execute(actorId, projectId, tablename, sqlDef, jobNo, columns, tempList);
								tempList.clear();
								success = true;
								logger.info(sqlDef.getTitle() + " 已经导入" + total + "条数据（总共" + len + "条）。");
							} catch (Exception ex) {
								logger.error(sqlDef.getTitle() + "导入数据数据出错", ex);
							}
						}
					}
					total = total + 1;
					tempList.add(resultList.get(i));
				}
				if (tempList.size() > 0) {
					boolean success = false;
					int retry = 0;
					while (retry < 2 && !success) {
						try {
							retry++;
							batch.execute(actorId, projectId, tablename, sqlDef, jobNo, columns, tempList);
							success = true;
							logger.info(sqlDef.getTitle() + "已经导入" + total + "条数据（总共" + len + "条）。");
							tempList.clear();
						} catch (Exception ex) {
							logger.error(sqlDef.getTitle() + "导入数据数据出错", ex);
						}
					}
				}
				resultList.clear();
				tempList.clear();
			}

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("rowKey", sqlDef.getId());
			jsonObject.put("subject", sqlDef.getTitle());

			SysDataLog log = new SysDataLog();
			log.setActorId(actorId);
			log.setCreateTime(new Date());
			log.setFlag(1);
			log.setModuleId("database");
			log.setTimeMS((int) (System.currentTimeMillis() - start));
			log.setContent(jsonObject.toJSONString());
			SysLogFactory.getInstance().addLog(log);
			logger.debug("batch time(ms):" + (System.currentTimeMillis() - start));
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			resultList.clear();
			tempList.clear();
			resultList = null;
			tempList = null;
		}
	}

}
