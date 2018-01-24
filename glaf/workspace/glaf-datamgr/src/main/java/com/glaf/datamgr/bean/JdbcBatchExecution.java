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

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.EntityService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.datamgr.domain.SqlDefinition;

public class JdbcBatchExecution {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void deleteAll(SqlDefinition sqlDef, String tablename) {
		if (StringUtils.isNotEmpty(sqlDef.getTargetTableName())
				&& StringUtils.startsWithIgnoreCase(sqlDef.getTargetTableName(), "SQL_RESULT")) {
			tablename = sqlDef.getTargetTableName();
		}

		logger.debug(tablename + " clear data...");
		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			connection = DBConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			psmt = connection.prepareStatement("delete from " + tablename + " where SQLDEFID_= ? ");
			psmt.setLong(1, sqlDef.getId());
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

	public void deleteAll(SqlDefinition sqlDef, String type, int runDay, String tablename) {
		if (StringUtils.isNotEmpty(sqlDef.getTargetTableName())
				&& StringUtils.startsWithIgnoreCase(sqlDef.getTargetTableName(), "SQL_RESULT")) {
			tablename = sqlDef.getTargetTableName();
		}
		logger.debug("type:" + type + "&runDay:" + runDay);
		logger.debug(tablename + " clear data...");
		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			connection = DBConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			psmt = connection
					.prepareStatement("delete from " + tablename + " where SQLDEFID_= ? and TYPE_= ? and RUNDAY_ = ? ");
			psmt.setLong(1, sqlDef.getId());
			psmt.setString(2, type);
			psmt.setInt(3, runDay);
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

	public void execute(String actorId, long projectId, String tablename, SqlDefinition sqlDef, String jobNo,
			List<ColumnDefinition> columns, List<Map<String, Object>> resultList) {
		logger.debug("-------------------start run-------------------");
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

		if (StringUtils.isNotEmpty(sqlDef.getTargetTableName())
				&& StringUtils.startsWithIgnoreCase(sqlDef.getTargetTableName(), "SQL_RESULT")) {
			tablename = sqlDef.getTargetTableName();
		}

		StringBuffer buffer = new StringBuffer();
		buffer.append(" insert into ").append(tablename).append(
				" (ID_, PROJECTID_, DATABASEID_, SQLDEFID_, COUNT_, VALUE_, RUNYEAR_, RUNMONTH_, RUNWEEK_, RUNQUARTER_, RUNDAY_, JOBNO_, TYPE_, OPERATION_, CREATEBY_, CREATETIME_");
		Map<Integer, ColumnDefinition> columnMap = new HashMap<Integer, ColumnDefinition>();
		if (StringUtils.startsWithIgnoreCase(tablename, "SQL_RESULT")) {
			if (columns != null && !columns.isEmpty()) {
				for (ColumnDefinition column : columns) {
					buffer.append(", ").append(column.getColumnLabel());
					columnMap.put(columnCount, column);
					columnCount++;
				}
			}
		}
		buffer.append(" ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ");
		if (StringUtils.startsWithIgnoreCase(tablename, "SQL_RESULT")) {
			for (int i = 0; i < columnCount; i++) {
				buffer.append(",? ");
			}
		}
		buffer.append(" ) ");

		logger.debug(buffer.toString());

		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			EntityService entityService = ContextFactory.getBean("entityService");
			Long id = entityService.nextId(tablename, "ID_");
			if (id == null) {
				id = 1L;
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
				psmt.setLong(index++, projectId);
				psmt.setLong(index++, ParamUtils.getLong(dataMap, "databaseId"));
				psmt.setLong(index++, sqlDef.getId());
				psmt.setInt(index++, ParamUtils.getInt(dataMap, "count"));
				psmt.setDouble(index++, ParamUtils.getDouble(dataMap, "value"));
				psmt.setInt(index++, year);
				psmt.setInt(index++, month);
				psmt.setInt(index++, week);
				psmt.setInt(index++, quarter);
				psmt.setInt(index++, day);
				psmt.setString(index++, jobNo);
				psmt.setString(index++, ParamUtils.getString(dataMap, "type"));
				psmt.setString(index++, ParamUtils.getString(dataMap, "operation"));
				psmt.setString(index++, actorId);
				psmt.setTimestamp(index++, time);

				if (StringUtils.startsWithIgnoreCase(tablename, "SQL_RESULT")) {
					for (int j = 0; j < columnCount; j++) {
						ColumnDefinition column = columnMap.get(j);
						if (StringUtils.equalsIgnoreCase(column.getJavaType(), "Integer")) {
							if (ParamUtils.getIntValue(dataMap, column.getColumnLabel()) != null) {
								psmt.setInt(index++, ParamUtils.getIntValue(dataMap, column.getColumnLabel()));
							} else {
								psmt.setNull(index++, Types.INTEGER);
							}
						} else if (StringUtils.equalsIgnoreCase(column.getJavaType(), "Long")) {
							if (ParamUtils.getLongValue(dataMap, column.getColumnLabel()) != null) {
								psmt.setLong(index++, ParamUtils.getLongValue(dataMap, column.getColumnLabel()));
							} else {
								psmt.setNull(index++, Types.BIGINT);
							}
						} else if (StringUtils.equalsIgnoreCase(column.getJavaType(), "Double")) {
							if (ParamUtils.getDoubleValue(dataMap, column.getColumnLabel()) != null) {
								psmt.setDouble(index++, ParamUtils.getDoubleValue(dataMap, column.getColumnLabel()));
							} else {
								psmt.setNull(index++, Types.DOUBLE);
							}
						} else if (StringUtils.equalsIgnoreCase(column.getJavaType(), "Date")) {
							if (ParamUtils.getTimestamp(dataMap, column.getColumnLabel()) != null) {
								psmt.setTimestamp(index++, ParamUtils.getTimestamp(dataMap, column.getColumnLabel()));
							} else {
								psmt.setNull(index++, Types.TIMESTAMP);
							}
						} else if (StringUtils.equalsIgnoreCase(column.getJavaType(), "String")) {
							psmt.setString(index++, ParamUtils.getString(dataMap, column.getColumnLabel()));
						} else {
							if (dataMap.get(column.getColumnLabel()) != null) {
								psmt.setObject(index++, dataMap.get(column.getColumnLabel()));
							} else {
								psmt.setNull(index++, Types.NULL);
							}
						}
					}
				}
				psmt.addBatch();
			}
			psmt.executeBatch();
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
		long time2 = System.currentTimeMillis() - start;
		logger.debug("-------------------end run-------------------");
		logger.debug("total time(ms):" + time2);
	}

}
