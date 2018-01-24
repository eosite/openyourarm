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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.BulkInsertBean;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.matrix.data.sync.domain.CombineApp;

public class CombineSyncBean {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public int execute(long srcDatabaseId, long targetDatabaseId, CombineApp combineApp, String jobNo) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		Database srcDatabase = databaseService.getDatabaseById(srcDatabaseId);
		Database targetDatabase = databaseService.getDatabaseById(targetDatabaseId);
		String sourceTableName = combineApp.getSourceTableName();
		String targetTableName = combineApp.getTargetTableName();
		List<ColumnDefinition> columns = DBUtils.getColumnDefinitions(srcDatabase.getName(), sourceTableName);
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setTableName(targetTableName);
		String primaryKey = null;

		for (ColumnDefinition column : columns) {
			if (column.isPrimaryKey()) {
				column.setPrimaryKey(false);
				primaryKey = column.getColumnName();
			}
		}

		tableDefinition.setColumns(columns);

		if (!DBUtils.tableExists(targetDatabase.getName(), targetTableName)) {

			ColumnDefinition idColumn = new ColumnDefinition();
			idColumn.setColumnName("EX_PRIMARY_KEY_");// 来源表的主键值
			idColumn.setJavaType("String");
			idColumn.setLength(200);
			tableDefinition.setIdColumn(idColumn);

			ColumnDefinition column1 = new ColumnDefinition();
			column1.setColumnName("EX_MD5_");
			column1.setJavaType("String");
			column1.setLength(200);
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

			ColumnDefinition column4 = new ColumnDefinition();
			column4.setColumnName("EX_VERSION_");// 设置版本
			column4.setJavaType("Integer");
			tableDefinition.addColumn(column4);

			ColumnDefinition column5 = new ColumnDefinition();
			column5.setColumnName("EX_JOBNO_");// 设置处理批次
			column5.setJavaType("String");
			column5.setLength(50);
			tableDefinition.addColumn(column5);

			DBUtils.createTable(targetDatabase.getName(), tableDefinition);
		}

		StringBuilder buff = new StringBuilder();
		Map<String, String> md5Map = new HashMap<String, String>();
		Map<String, Integer> versionMap = new HashMap<String, Integer>();
		Connection srcConn = null;
		Connection targetConn = null;
		PreparedStatement srcPsmt = null;
		PreparedStatement targetPsmt = null;
		ResultSet srcRs = null;
		ResultSet targetRs = null;
		try {
			targetConn = DBConnectionFactory.getConnection(targetDatabase.getName());
			buff.append(" select ").append(primaryKey).append(", EX_MD5_, EX_VERSION_").append(" from ")
					.append(targetTableName);
			if (StringUtils.isNotEmpty(combineApp.getSqlCriteria())
					&& DBUtils.isLegalQuerySql(combineApp.getSqlCriteria())) {
				if (!StringUtils.startsWithIgnoreCase(combineApp.getSqlCriteria().trim(), "where")) {
					buff.append(" where 1=1 ");
				}
				buff.append(combineApp.getSqlCriteria());
			}
			buff.append(" order by EX_VERSION_ desc ");

			targetPsmt = targetConn.prepareStatement(buff.toString());
			targetRs = targetPsmt.executeQuery();
			while (targetRs.next()) {
				String key = targetRs.getObject(1).toString();
				String md5 = targetRs.getString(2);
				int version = targetRs.getInt(3);
				if (!md5Map.containsKey(key)) {// 只取最新版本的数据
					md5Map.put(key, md5);
				}
				if (!versionMap.containsKey(key)) {
					versionMap.put(key, version);
				}
			}

			JdbcUtils.close(targetRs);
			JdbcUtils.close(targetPsmt);

			srcConn = DBConnectionFactory.getConnection(srcDatabase.getName());
			buff.delete(0, buff.length());
			List<String> excludeColumns = new ArrayList<String>();
			if (StringUtils.isNotEmpty(combineApp.getExcludeColumns())) {
				StringTokenizer token = new StringTokenizer(combineApp.getExcludeColumns(), ",");
				while (token.hasMoreTokens()) {
					String tmp = token.nextToken();
					excludeColumns.add(tmp.trim().toUpperCase());
				}
			}

			int index = 0;
			buff.append(" select ");

			for (ColumnDefinition column : columns) {
				if (excludeColumns.contains(column.getColumnName().toUpperCase())) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "EX_PRIMARY_KEY_")) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "EX_MD5_")) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "EX_SYNC_FLAG_")) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "EX_SYNC_TIME_")) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "EX_VERSION_")) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "EX_JOBNO_")) {
					continue;
				}
				column.setPosition(++index);
				buff.append(column.getColumnName()).append(", ");
			}

			buff.delete(buff.length() - 2, buff.length());
			buff.append(" from ").append(sourceTableName);

			srcPsmt = srcConn.prepareStatement(buff.toString());
			srcRs = srcPsmt.executeQuery();

			String key = null;
			java.util.Date now = new java.util.Date();
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			while (srcRs.next()) {
				buff.delete(0, buff.length());
				Map<String, Object> dataMap = new HashMap<String, Object>();
				for (ColumnDefinition column : columns) {
					if (excludeColumns.contains(column.getColumnName().toUpperCase())) {
						continue;
					}
					if (StringUtils.equalsIgnoreCase(column.getColumnName(), "EX_PRIMARY_KEY_")) {
						continue;
					}
					if (StringUtils.equalsIgnoreCase(column.getColumnName(), "EX_MD5_")) {
						continue;
					}
					if (StringUtils.equalsIgnoreCase(column.getColumnName(), "EX_SYNC_FLAG_")) {
						continue;
					}
					if (StringUtils.equalsIgnoreCase(column.getColumnName(), "EX_SYNC_TIME_")) {
						continue;
					}
					if (StringUtils.equalsIgnoreCase(column.getColumnName(), "EX_VERSION_")) {
						continue;
					}
					if (StringUtils.equalsIgnoreCase(column.getColumnName(), "EX_JOBNO_")) {
						continue;
					}
					Object object = srcRs.getObject(column.getPosition());
					dataMap.put(column.getColumnName().toLowerCase(), object);
					if (object != null) {
						if (StringUtils.equalsIgnoreCase(primaryKey, column.getColumnName())) {
							key = object.toString();
						}
						if (object instanceof java.util.Date) {
							java.util.Date date = (java.util.Date) object;
							buff.append(DateUtils.getDateTime(date)).append('\001');
						} else {
							buff.append(object.toString()).append('\001');
						}
					} else {
						buff.append("null").append('\001');
					}
				}
				if (key != null) {
					String md5 = DigestUtils.md5Hex(buff.toString());
					if (!StringUtils.equals(md5Map.get(key), md5)) {
						Integer version = versionMap.get(key);
						if (version == null) {
							version = 0;
						}
						version++;
						dataMap.put("ex_md5_", md5);
						dataMap.put("ex_jobno_", jobNo);
						dataMap.put("ex_sync_flag_", "Y");
						dataMap.put("ex_sync_time_", now);
						dataMap.put("ex_version_", version);
						dataMap.put("ex_primary_key_", (key + "_" + version));
						dataList.add(dataMap);
					}
				}
			}

			JdbcUtils.close(srcRs);
			JdbcUtils.close(srcPsmt);
			JdbcUtils.close(srcConn);

			if (dataList.size() > 0) {
				BulkInsertBean insertBean = new BulkInsertBean();
				targetConn.setAutoCommit(false);
				columns = DBUtils.getColumnDefinitions(targetDatabase.getName(), targetTableName);
				tableDefinition.setTableName(targetTableName);
				tableDefinition.setColumns(columns);
				insertBean.bulkInsert(targetConn, tableDefinition, dataList);
				targetConn.commit();
				JdbcUtils.close(targetConn);
			}

			return dataList.size();
		} catch (Exception ex) {
			// ex.printStackTrace();
			logger.error("execute sync error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(srcRs);
			JdbcUtils.close(srcPsmt);
			JdbcUtils.close(srcConn);

			JdbcUtils.close(targetRs);
			JdbcUtils.close(targetPsmt);
			JdbcUtils.close(targetConn);

		}

	}

}
