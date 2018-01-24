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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.sql.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.query.DatabaseQuery;
import com.glaf.core.service.EntityService;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.StringTools;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.util.FastJsonUtils;

public class AggregationTableExecution {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected DataSetService dataSetService;

	protected IDatabaseService databaseService;

	protected ITableDefinitionService tableDefinitionService;

	public void deleteAll(TableDefinition table) {
		String tablename = table.getTableName();
		if (StringUtils.isNotEmpty(tablename) && StringUtils.startsWithIgnoreCase(tablename, "ETL_")
				&& StringUtils.equals(table.getTemporaryFlag(), "1")) {
			logger.debug(tablename + " clear data...");
			Connection connection = null;
			PreparedStatement psmt = null;
			try {
				connection = DBConnectionFactory.getConnection();
				connection.setAutoCommit(false);
				psmt = connection.prepareStatement("delete from " + tablename);
				psmt.executeUpdate();
				psmt.close();
				connection.commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("run batch error", ex);
				throw new RuntimeException(ex);
			} finally {
				JdbcUtils.close(psmt);
				JdbcUtils.close(connection);
			}
		}
	}

	public void deleteAll(TableDefinition table, String type, int runDay) {
		String tablename = table.getTableName();
		if (StringUtils.isNotEmpty(tablename) && StringUtils.startsWithIgnoreCase(tablename, "ETL_")
				&& StringUtils.equals(table.getDeleteFetch(), "1")) {
			logger.debug("type:" + type + "&runDay:" + runDay);
			logger.debug(tablename + " clear data...");
			Connection connection = null;
			PreparedStatement psmt = null;
			try {
				connection = DBConnectionFactory.getConnection();
				connection.setAutoCommit(false);
				psmt = connection.prepareStatement("delete from " + tablename + " where TYPE_= ? and RUNDAY_ = ? ");
				psmt.setString(1, type);
				psmt.setInt(2, runDay);
				psmt.executeUpdate();
				psmt.close();
				connection.commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("run batch error", ex);
				throw new RuntimeException(ex);
			} finally {
				JdbcUtils.close(psmt);
				JdbcUtils.close(connection);
			}
		}
	}

	public void execute(String actorId, String tableName) {
		TableDefinition table = getTableDefinitionService().getTableDefinition(tableName);
		int runDay = DateUtils.getNowYearMonthDay();
		this.deleteAll(table);
		this.deleteAll(table, "ETL", runDay);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		String aggrKeys = table.getAggregationKeys();
		StringBuffer aggrColumnBuffer = new StringBuffer(500);
		List<ColumnDefinition> columns = table.getColumns();
		List<ColumnDefinition> aggrColumns = new ArrayList<ColumnDefinition>();
		Map<String, ColumnDefinition> columnMap = new HashMap<String, ColumnDefinition>();
		if (columns != null && !columns.isEmpty()) {
			for (ColumnDefinition column : columns) {
				columnMap.put(column.getName(), column);
				columnMap.put(column.getColumnName(), column);
				columnMap.put(column.getColumnLabel(), column);
				if (StringUtils.containsIgnoreCase(aggrKeys, column.getColumnName())) {
					// 找到聚合列
					aggrColumns.add(column);
					aggrColumnBuffer.append(column.getColumnName()).append("_");
				}
			}
		}

		if (aggrColumnBuffer.length() > 0) {
			aggrColumnBuffer.delete(aggrColumnBuffer.length() - 1, aggrColumnBuffer.length());
		}

		String aggrColumn = aggrColumnBuffer.toString();
		logger.debug("aggrColumn:" + aggrColumn);

		if (StringUtils.isNotEmpty(table.getDatasetIds())) {
			DataSetBuilder builder = new DataSetBuilder();
			StringBuffer buffer = new StringBuffer(500);
			Map<String, Map<String, Object>> dataMap = new HashMap<String, Map<String, Object>>();
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			Map<String, Object> parameter = new HashMap<String, Object>();
			Map<String, Object> contextMap = SystemConfig.getContextMap();
			if (contextMap != null && !contextMap.isEmpty()) {
				Set<Entry<String, Object>> entrySet = contextMap.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					String key = entry.getKey();
					Object value = entry.getValue();
					parameter.put(key, value);
				}
			}
			DatabaseQuery query = new DatabaseQuery();
			query.active("1");
			List<Database> databases = getDatabaseService().list(query);
			List<String> datasetIds = StringTools.split(table.getDatasetIds());
			if (databases != null && !databases.isEmpty() && datasetIds != null && !datasetIds.isEmpty()) {
				for (String datasetId : datasetIds) {
					List<JSONObject> dataList = new ArrayList<JSONObject>();
					DataSet dataSet = getDataSetService().getDataSet(datasetId);
					for (Database database : databases) {
						if (StringUtils.equals(database.getVerify(), "Y")
								&& StringUtils.contains(database.getRunType(), "INST")) {
							JSONObject jsonObject = builder.getJson(database.getName(), dataSet, 0, 50000, parameter);
							if (jsonObject.containsKey("rows")) {
								JSONArray array = jsonObject.getJSONArray("rows");
								if (array != null && array.size() > 0) {
									int len = array.size();
									logger.debug(array.toJSONString());
									logger.debug(dataSet.getTitle() + " rows size:" + len);
									for (int i = 0; i < len; i++) {
										JSONObject json = array.getJSONObject(i);
										json = FastJsonUtils.addLowerCaseKey(json);
										json.put("databaseId", database.getId());
										dataList.add(json);
									}
								}
							}
						}
					}
					if (!dataList.isEmpty()) {
						/**
						 * 处理来源于不同数据库同一数据集的数据
						 */
						for (JSONObject jsonObject : dataList) {
							Map<String, Object> rowMap = new HashMap<String, Object>();
							rowMap.put("runYear", year);
							rowMap.put("RUNYEAR_", year);
							jsonObject = FastJsonUtils.addLowerCaseKey(jsonObject);
							Iterator<Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
							while (iterator.hasNext()) {
								Entry<String, Object> entry = iterator.next();
								String key = (String) entry.getKey();
								Object value = entry.getValue();
								if (value != null) {
									rowMap.put(key, value);
								}
							}
							buffer.delete(0, buffer.length());

							Iterator<ColumnDefinition> it = aggrColumns.iterator();
							while (it.hasNext()) {
								ColumnDefinition col = it.next();
								Object val = ParamUtils.get(rowMap, col.getName());
								if (val != null) {
									buffer.append(val.toString());
								} else {
									buffer.append("");
								}
								if (it.hasNext()) {
									buffer.append("_");
								}
							}
							if (buffer.toString().endsWith("_")) {
								buffer.delete(buffer.length() - 1, buffer.length());
							}
							String aggregationValue = buffer.toString();
							Map<String, Object> newRow = dataMap.get(aggregationValue);
							if (newRow == null) {
								newRow = new HashMap<String, Object>();
								newRow.put("datasetId", dataSet.getId());
								newRow.put("runYear", year);
								newRow.put("RUNYEAR_", year);
								newRow.put(aggrKeys, aggregationValue);
								newRow.put(aggrColumn, aggregationValue);
								newRow.put("aggregationKey", aggregationValue);
								dataMap.put(aggregationValue, newRow);
								resultList.add(newRow);
							}
							jsonObject = FastJsonUtils.addLowerCaseKey(jsonObject);
							Iterator<Entry<String, Object>> iterator2 = jsonObject.entrySet().iterator();
							while (iterator2.hasNext()) {
								Entry<String, Object> entry = iterator2.next();
								String key = (String) entry.getKey();
								Object value = entry.getValue();
								if (value != null && !newRow.containsKey(key)) {
									newRow.put(key, value);
								}
							}
						}
					}
				}
			}
			logger.debug("resultList:" + resultList);
			String jobNo = "ETL_" + DateUtils.getNowYearMonthDayHHmmss();
			this.execute(actorId, table, jobNo, columns, resultList);
		}
	}

	public void execute(String actorId, TableDefinition table, String jobNo, List<ColumnDefinition> columns,
			List<Map<String, Object>> resultList) {
		logger.debug("-------------------start execute-------------------");
		long start = System.currentTimeMillis();
		int day = DateUtils.getNowYearMonthDay();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		Timestamp time = DateUtils.toTimestamp(calendar.getTime());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int quarter = 1;
		if (month <= 3) {
			quarter = 1;
		} else if (month > 3 && month <= 6) {
			quarter = 2;
		}
		if (month > 6 && month <= 9) {
			quarter = 3;
		}
		if (month > 9) {
			quarter = 4;
		}

		int columnCount = 0;
		String tablename = table.getTableName();

		StringBuffer buffer = new StringBuffer();
		buffer.append(" insert into ").append(tablename).append(
				" (ID_, PROJECTID_, DATABASEID_, DATASETID_, RUNYEAR_, RUNMONTH_, RUNWEEK_, RUNQUARTER_, RUNDAY_, JOBNO_, TYPE_, AGGREGATIONKEY_, CREATEBY_, CREATETIME_");
		Map<Integer, ColumnDefinition> columnMap = new HashMap<Integer, ColumnDefinition>();
		if (columns != null && !columns.isEmpty()) {
			for (ColumnDefinition column : columns) {
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "ID_")) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "PROJECTID_")) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "DATABASEID_")) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "DATASETID_")) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "RUNYEAR_")) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "RUNMONTH_")) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "RUNWEEK_")) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "RUNQUARTER_")) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "RUNDAY_")) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "JOBNO_")) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "TYPE_")) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "AGGREGATIONKEY_")) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "CREATEBY_")) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "CREATETIME_")) {
					continue;
				}
				buffer.append(", ").append(column.getColumnName());
				columnMap.put(columnCount, column);
				columnCount++;
			}
		}
		buffer.append(" ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ");
		for (int i = 0; i < columnCount; i++) {
			buffer.append(",? ");
		}
		buffer.append(" ) ");

		logger.debug(buffer.toString());

		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			EntityService entityService = ContextFactory.getBean("entityService");
			Long id = entityService.nextId(tablename, "ID_");
			if (id == null) {
				id = 0L;
			}
			connection = DBConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			psmt = connection.prepareStatement(buffer.toString());
			int len = resultList.size();
			int index = 1;
			for (int i = 0; i < len; i++) {
				index = 1;
				Map<String, Object> dataMap = resultList.get(i);
				psmt.setLong(index++, ++id);
				psmt.setLong(index++, ParamUtils.getLong(dataMap, "projectId"));
				psmt.setLong(index++, ParamUtils.getLong(dataMap, "databaseId"));
				psmt.setString(index++, ParamUtils.getString(dataMap, "datasetId"));
				psmt.setInt(index++, year);
				psmt.setInt(index++, month);
				psmt.setInt(index++, week);
				psmt.setInt(index++, quarter);
				psmt.setInt(index++, day);
				psmt.setString(index++, jobNo);
				psmt.setString(index++, "ETL");
				psmt.setString(index++, ParamUtils.getString(dataMap, "aggregationKey"));
				psmt.setString(index++, actorId);
				psmt.setTimestamp(index++, time);
				for (int j = 0; j < columnCount; j++) {
					ColumnDefinition column = columnMap.get(j);
					if (StringUtils.equalsIgnoreCase(column.getJavaType(), "Integer")) {
						if (ParamUtils.getIntValue(dataMap, column.getName()) != null) {
							psmt.setInt(index++, ParamUtils.getIntValue(dataMap, column.getName()));
						} else {
							psmt.setNull(index++, Types.INTEGER);
						}
					} else if (StringUtils.equalsIgnoreCase(column.getJavaType(), "Long")) {
						if (ParamUtils.getLongValue(dataMap, column.getName()) != null) {
							psmt.setLong(index++, ParamUtils.getLongValue(dataMap, column.getName()));
						} else {
							psmt.setNull(index++, Types.BIGINT);
						}
					} else if (StringUtils.equalsIgnoreCase(column.getJavaType(), "Double")) {
						if (ParamUtils.getDoubleValue(dataMap, column.getName()) != null) {
							psmt.setDouble(index++, ParamUtils.getDoubleValue(dataMap, column.getName()));
						} else {
							psmt.setNull(index++, Types.DOUBLE);
						}
					} else if (StringUtils.equalsIgnoreCase(column.getJavaType(), "Date")) {
						if (ParamUtils.getTimestamp(dataMap, column.getName()) != null) {
							psmt.setTimestamp(index++, ParamUtils.getTimestamp(dataMap, column.getName()));
						} else {
							psmt.setNull(index++, Types.TIMESTAMP);
						}
					} else if (StringUtils.equalsIgnoreCase(column.getJavaType(), "String")) {
						psmt.setString(index++, ParamUtils.getString(dataMap, column.getName()));
					} else {
						if (dataMap.get(column.getName()) != null) {
							psmt.setObject(index++, dataMap.get(column.getName()));
						} else {
							psmt.setNull(index++, Types.NULL);
						}
					}
				}
				psmt.addBatch();
			}
			psmt.executeBatch();
			psmt.close();
			connection.commit();
		} catch (Exception ex) {
			logger.error("execute batch error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
			JdbcUtils.close(connection);
		}
		long time2 = System.currentTimeMillis() - start;
		logger.debug("-------------------end execute-------------------");
		logger.debug("total time(ms):" + time2);
	}

	public IDatabaseService getDatabaseService() {
		if (databaseService == null) {
			databaseService = ContextFactory.getBean("databaseService");
		}
		return databaseService;
	}

	public DataSetService getDataSetService() {
		if (dataSetService == null) {
			dataSetService = ContextFactory.getBean("dataSetService");
		}
		return dataSetService;
	}

	public ITableDefinitionService getTableDefinitionService() {
		if (tableDefinitionService == null) {
			tableDefinitionService = ContextFactory.getBean("tableDefinitionService");
		}
		return tableDefinitionService;
	}

	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	public void setDataSetService(DataSetService dataSetService) {
		this.dataSetService = dataSetService;
	}

	public void setTableDefinitionService(ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

}
