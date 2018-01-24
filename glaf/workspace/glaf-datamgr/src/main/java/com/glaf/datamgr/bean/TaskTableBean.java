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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.el.ExpressionTools;
import com.glaf.core.el.Mvel2ExpressionEvaluator;
import com.glaf.core.jdbc.BulkInsertBean;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.datamgr.domain.TaskTable;
import com.glaf.datamgr.service.TaskTableService;
import com.glaf.datamgr.util.ExceptionUtils;

public class TaskTableBean implements java.lang.Runnable {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected long databaseId;

	protected long taskTableId;

	protected boolean result;

	protected Map<String, Object> parameter;

	public TaskTableBean() {

	}

	public TaskTableBean(long databaseId, long taskTableId, Map<String, Object> parameter) {
		this.databaseId = databaseId;
		this.taskTableId = taskTableId;
		this.parameter = parameter;
	}

	public boolean execute(long databaseId, long taskTableId, Map<String, Object> params) {
		TaskTableService taskTableService = ContextFactory.getBean("com.glaf.datamgr.service.taskTableService");
		TaskTable taskTable = taskTableService.getTaskTable(taskTableId);
		return this.execute(databaseId, taskTable, params);
	}

	public boolean execute(long databaseId, TaskTable taskTable, Map<String, Object> params) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");

		List<ColumnDefinition> columns = null;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			Database database = databaseService.getDatabaseById(databaseId);

			if (database != null) {
				conn = DBConnectionFactory.getConnection(database.getName());
			} else {
				conn = DBConnectionFactory.getConnection();
			}
			logger.debug(taskTable.toJsonObject().toJSONString());
			columns = DBUtils.getColumnDefinitions(conn, taskTable.getTableName());

			TableDefinition tableDefinition = new TableDefinition();
			tableDefinition.setTableName(taskTable.getTableName());
			tableDefinition.setColumns(columns);

			String sql = " select " + taskTable.getIdColumn() + " from " + taskTable.getTableName();
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			List<String> keys = new ArrayList<String>();
			while (rs.next()) {
				keys.add(rs.getString(1));
			}
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);

			Map<String, Object> context = new HashMap<String, Object>();
			context.putAll(params);

			List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(taskTable.getStartTime());

			context.put("year", calendar.get(Calendar.YEAR));
			context.put("month", calendar.get(Calendar.MONTH) + 1);

			int frequency = taskTable.getFrequency();
			if (params.get("frequency") != null) {
				frequency = ParamUtils.getInt(params, "frequency");
				context.put("frequency", frequency);
			}

			int executeDay = taskTable.getExecuteDay();
			if (params.get("executeDay") != null) {
				executeDay = ParamUtils.getInt(params, "executeDay");
				context.put("executeDay", executeDay);
			}

			if (executeDay >= frequency) {
				executeDay = frequency;
				context.put("executeDay", executeDay);
			}

			if (params.get("startTime") != null) {
				calendar.setTime(ParamUtils.getDate(params, "startTime"));
				taskTable.setStartTime(ParamUtils.getDate(params, "startTime"));
				context.put("startTime", taskTable.getStartTime());
			}

			if (params.get("endTime") != null) {
				taskTable.setEndTime(ParamUtils.getDate(params, "endTime"));
				context.put("endTime", taskTable.getEndTime());
			}

			Map<String, Object> dataMap = null;
			Date startDate = null;
			Date endDate = null;
			boolean canLoop = true;
			int loopCount = 0;
			while (canLoop) {
				startDate = calendar.getTime();

				calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + frequency);
				endDate = calendar.getTime();

				loopCount++;

				if (datalist.size() > 5000) {
					canLoop = false;
					break;
				}
				if (taskTable.getEndTime() != null && endDate.getTime() > taskTable.getEndTime().getTime()) {
					canLoop = false;
					break;
				}
			}

			if (loopCount == 0) {
				loopCount = 1;
			}

			canLoop = true;
			context.put("loopCount", loopCount);
			calendar.setTime(taskTable.getStartTime());

			while (canLoop) {
				startDate = calendar.getTime();

				calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + frequency);
				endDate = calendar.getTime();

				String key = taskTable.getType() + "_" + DateUtils.getYearMonthDay(startDate);
				if (keys.contains(key)) {
					//如果时间已经超过时，结束。
					if (taskTable.getEndTime() != null && endDate.getTime() > taskTable.getEndTime().getTime()) {
						canLoop = false;
						break;
					}
					continue;
				}

				if (!keys.contains(key)) {
					dataMap = new HashMap<String, Object>();
					dataMap.putAll(context);

					Set<Entry<String, Object>> entrySet = context.entrySet();
					for (Entry<String, Object> entry : entrySet) {
						String key2 = entry.getKey();
						Object value2 = entry.getValue();
						dataMap.put(key2.toLowerCase(), value2);
					}
					dataMap.put("dateString", DateUtils.getDate(startDate));
					dataMap.put("startDateString", DateUtils.getDate(startDate));
					dataMap.put("endDateString", DateUtils.getDate(endDate));

					String nameExpr = taskTable.getNameExpression();
					String name = ExpressionTools.evaluate(nameExpr, dataMap);

					// logger.debug(nameExpr + ":" + dataMap);
					// logger.debug(nameExpr + ":" + name);

					dataMap.put(taskTable.getIdColumn().toLowerCase(), key);
					dataMap.put(taskTable.getNameColumn().toLowerCase(), name);
					dataMap.put(taskTable.getStartDateColumn().toLowerCase(), startDate);
					dataMap.put(taskTable.getEndDateColumn().toLowerCase(),
							DateUtils.getDateAfter(startDate, executeDay));
					dataMap.put(taskTable.getTypeColumn().toLowerCase(), taskTable.getType());

					if (StringUtils.isNotEmpty(taskTable.getValueExpression())) {
						Object value = Mvel2ExpressionEvaluator.evaluate(taskTable.getValueExpression(), dataMap);
						dataMap.put(taskTable.getValueColumn().toLowerCase(), value);
					}

					datalist.add(dataMap);
					keys.add(key);
				}

				if (datalist.size() > 5000) {
					canLoop = false;
					break;
				}
				if (taskTable.getEndTime() != null && endDate.getTime() > taskTable.getEndTime().getTime()) {
					canLoop = false;
					break;
				}
			}

			if (datalist.size() > 0) {
				BulkInsertBean bulkInsertBean = new BulkInsertBean();
				conn.setAutoCommit(false);
				bulkInsertBean.bulkInsert(conn, tableDefinition, datalist);
				conn.commit();
			}

			return true;
		} catch (Exception ex) {
			ExceptionUtils.addMsg("task_table_" + taskTableId, ex.getMessage());
			logger.error("execute error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
			JdbcUtils.close(conn);
		}
	}

	@Override
	public void run() {
		this.execute(databaseId, taskTableId, parameter);
	}

}
