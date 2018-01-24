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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.SerializationUtils;
import com.glaf.core.util.StringTools;

import com.glaf.datamgr.domain.TableSynthetic;
import com.glaf.datamgr.util.ExceptionUtils;

public class TableCopyBean {
	protected static Configuration conf = BaseConfiguration.create();

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public TableCopyBean() {

	}

	public boolean execute(Database sourceDatabase, Database targetDatabase, TableSynthetic tableSynthetic) {
		if (!(StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "useradd_")
				|| StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "etl_")
				|| StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "sync_")
				|| StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "tree_table_")
				|| StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "tmp"))) {
			return false;
		}
		StringBuilder insertBuffer = new StringBuilder();
		StringBuilder updateBuffer = new StringBuilder();

		Map<String, String> sourceMd5Map = new HashMap<String, String>();
		Map<String, String> targetMd5Map = new HashMap<String, String>();
		Map<String, String> targetKeyMap = new HashMap<String, String>();
		Map<String, Map<String, Object>> sourceDataMap = new HashMap<String, Map<String, Object>>();
		List<String> syncColumns = StringTools.split(tableSynthetic.getSyncColumns().toLowerCase(), ",");
		logger.debug("sync columns:" + syncColumns);

		String idColumnName = null;
		ColumnDefinition idColumn = null;

		String sourceIdColumnName = tableSynthetic.getPrimaryKey();

		Connection sourceConn = null;
		PreparedStatement sourcePsmt = null;
		ResultSet sourceRS = null;

		Connection targetConn = null;
		PreparedStatement targetPsmt = null;
		ResultSet targetRS = null;

		PreparedStatement targetInsertPsmt = null;
		PreparedStatement targetUpdatePsmt = null;
		try {
			List<ColumnDefinition> srcColumns = DBUtils.getColumnDefinitions(sourceDatabase.getName(),
					tableSynthetic.getSourceTableName());
			List<String> primaryKeys = DBUtils.getUpperCasePrimaryKeys(sourceDatabase.getName(),
					tableSynthetic.getSourceTableName());

			List<ColumnDefinition> destColumns = DBUtils.getColumnDefinitions(targetDatabase.getName(),
					tableSynthetic.getTargetTableName());
			List<String> primaryKeys2 = DBUtils.getUpperCasePrimaryKeys(targetDatabase.getName(),
					tableSynthetic.getTargetTableName());

			if (primaryKeys != null && !primaryKeys.isEmpty()) {
				/**
				 * 以业务上设置的主键字段优先，如果没有设置再取物理表的主键
				 */
				if (StringUtils.isEmpty(sourceIdColumnName)) {
					sourceIdColumnName = primaryKeys.get(0).toLowerCase();
				}
			}

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

			sourceConn = DBConnectionFactory.getConnection(sourceDatabase.getName());
			targetConn = DBConnectionFactory.getConnection(targetDatabase.getName());
			targetConn.setAutoCommit(false);
			logger.debug("目标数据库已经连接：" + targetDatabase.getDbname());

			int k = 1;
			int len = srcColumns.size();
			StringBuilder row = new StringBuilder();
			StringBuilder selectBuffer = new StringBuilder();
			selectBuffer.append(" select ");
			for (k = 1; k <= len; k++) {
				ColumnDefinition cm = srcColumns.get(k - 1);
				String name = cm.getColumnName().toLowerCase();
				selectBuffer.append(name);
				if (k <= len - 1) {
					selectBuffer.append(",");
				}
			}

			insertBuffer.append(" insert into ").append(tableSynthetic.getTargetTableName()).append(" ( ");
			updateBuffer.append(" update ").append(tableSynthetic.getTargetTableName()).append(" set ");

			len = columns.size();

			k = 0;
			String key = null;
			String columnName = null;
			ColumnDefinition cm = null;
			Object value = null;
			Object targetTableIdValue = null;

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

			selectBuffer.append(" from ").append(tableSynthetic.getSourceTableName());

			logger.debug("select sql :" + selectBuffer.toString());
			logger.debug("insert sql :" + insertBuffer.toString());
			logger.debug("update sql :" + updateBuffer.toString());
			sourcePsmt = sourceConn.prepareStatement(selectBuffer.toString());
			sourceRS = sourcePsmt.executeQuery();
			while (sourceRS.next()) {
				k = 0;
				row.delete(0, row.length());
				Map<String, Object> dataMap = new HashMap<String, Object>();
				for (ColumnDefinition c : srcColumns) {
					k++;
					columnName = c.getColumnName().toLowerCase();
					switch (c.getJavaType()) {
					case "Integer":
						int val = sourceRS.getInt(k);
						dataMap.put(columnName, val);
						if (syncColumns.contains(columnName)) {
							row.append(val);
						}
						break;
					case "Long":
						long val2 = sourceRS.getLong(k);
						dataMap.put(columnName, val2);
						if (syncColumns.contains(columnName)) {
							row.append(val2);
						}
						break;
					case "Boolean":
						boolean val3 = sourceRS.getBoolean(k);
						dataMap.put(columnName, val3);
						if (syncColumns.contains(columnName)) {
							row.append(val3);
						}
						break;
					case "Double":
						double val4 = sourceRS.getDouble(k);
						dataMap.put(columnName, val4);
						if (syncColumns.contains(columnName)) {
							row.append(val4);
						}
						break;
					case "Date":
						Timestamp val5 = sourceRS.getTimestamp(k);
						if (val5 != null) {
							dataMap.put(columnName, val5);
							if (syncColumns.contains(columnName)) {
								row.append(val5.getTime());
							}
						}
						break;
					case "String":
						String val6 = sourceRS.getString(k);
						if (val6 != null) {
							dataMap.put(columnName, val6);
							if (syncColumns.contains(columnName)) {
								row.append(val6);
							}
						}
						break;
					default:
						Object val7 = sourceRS.getObject(k);
						if (val7 != null) {
							dataMap.put(columnName, val7);
							byte[] bytes = SerializationUtils.fstserialize(val7);
							if (syncColumns.contains(columnName)) {
								row.append(new String(bytes));
							}
						}
						break;
					}
					if (StringUtils.equalsIgnoreCase(sourceIdColumnName, columnName)) {
						key = ParamUtils.getString(dataMap, columnName);
					}
				}
				sourceDataMap.put(key, dataMap);// 源表数据
				sourceMd5Map.put(key, DigestUtils.md5Hex(row.toString()));// 源表一条记录的MD5
			}

			JdbcUtils.close(sourceRS);
			JdbcUtils.close(sourcePsmt);

			selectBuffer.delete(0, selectBuffer.length());
			selectBuffer.append(" select ");
			Iterator<ColumnDefinition> iterator3 = destColumns.iterator();
			while (iterator3.hasNext()) {
				ColumnDefinition c = iterator3.next();
				selectBuffer.append(c.getColumnName().toLowerCase());
				if (iterator3.hasNext()) {
					selectBuffer.append(", ");
				}
			}
			selectBuffer.append(" from ").append(tableSynthetic.getTargetTableName());
			targetPsmt = targetConn.prepareStatement(selectBuffer.toString());
			targetRS = targetPsmt.executeQuery();

			while (targetRS.next()) {
				row.delete(0, row.length());
				for (k = 1; k <= len; k++) {
					cm = destColumns.get(k - 1);
					columnName = cm.getColumnName().toLowerCase();
					switch (cm.getJavaType()) {
					case "Integer":
						int val = targetRS.getInt(k);
						value = val;
						if (syncColumns.contains(columnName)) {
							row.append(val);
						}
						break;
					case "Long":
						long val2 = targetRS.getLong(k);
						value = val2;
						if (syncColumns.contains(columnName)) {
							row.append(val2);
						}
						break;
					case "Boolean":
						boolean val3 = targetRS.getBoolean(k);
						value = val3;
						if (syncColumns.contains(columnName)) {
							row.append(val3);
						}
						break;
					case "Double":
						double val4 = targetRS.getDouble(k);
						value = val4;
						if (syncColumns.contains(columnName)) {
							row.append(val4);
						}
						break;
					case "Date":
						Timestamp val5 = targetRS.getTimestamp(k);
						value = val5;
						if (val5 != null) {
							if (syncColumns.contains(columnName)) {
								row.append(val5.getTime());
							}
						}
						break;
					case "String":
						String val6 = targetRS.getString(k);
						value = val6;
						if (val6 != null) {
							if (syncColumns.contains(columnName)) {
								row.append(val6);
							}
						}
						break;
					default:
						Object val7 = targetRS.getObject(k);
						value = val7;
						if (val7 != null) {
							byte[] bytes = SerializationUtils.fstserialize(val7);
							if (syncColumns.contains(columnName)) {
								row.append(new String(bytes));
							}
						}
						break;
					}
					if (StringUtils.equalsIgnoreCase(sourceIdColumnName, columnName)) {
						if (value != null) {
							key = value.toString();
						}
					}
					if (primaryKeys2.contains(columnName.toUpperCase())) {
						if (value != null) {
							targetTableIdValue = value;
						}
					}
				}
				targetMd5Map.put(key, DigestUtils.md5Hex(row.toString()));// 目标表一条记录的MD5
				targetKeyMap.put(key, targetTableIdValue != null ? targetTableIdValue.toString() : null);// 目标表原始主键
			}

			JdbcUtils.close(targetRS);
			JdbcUtils.close(targetPsmt);

			/**
			 * 删除掉目标表数据，再重新抓取
			 */
			boolean insertOperation = false;
			if (StringUtils.equalsIgnoreCase(tableSynthetic.getDeleteFetch(), "Y")) {
				if (StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "useradd_")
						|| StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "etl_")
						|| StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "sync_")
						|| StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "tree_table_")
						|| StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "tmp")) {
					String sql = " delete from " + tableSynthetic.getTargetTableName();
					logger.info("execute clear sql:" + sql);
					DBUtils.executeSchemaResourceIgnoreException(targetConn, sql);
					// targetConn.commit();
					insertOperation = true;
				}
			}

			targetInsertPsmt = targetConn.prepareStatement(insertBuffer.toString());
			targetUpdatePsmt = targetConn.prepareStatement(updateBuffer.toString());

			logger.debug(" sourceMd5Map size: " + sourceMd5Map.size());
			// logger.debug(" targetKeyMap: " + targetKeyMap);
			Set<Entry<String, String>> entrySet = sourceMd5Map.entrySet();
			for (Entry<String, String> entry : entrySet) {
				String id = entry.getKey();
				String md5 = entry.getValue();
				if (!insertOperation) {
					/**
					 * 判断MD5值是否相同，相同就认为是记录一样的，不用做任何操作
					 */
					if (StringUtils.equals(md5, targetMd5Map.get(id))) {
						// logger.debug(id + " is exists !!!");
						continue;
					}
				}

				Map<String, Object> dataMap = sourceDataMap.get(id);

				if (dataMap == null) {
					continue;
				}

				if (targetKeyMap.get(id) != null) {
					dataMap.put(idColumnName, targetKeyMap.get(id));
				}

				if (!insertOperation && targetMd5Map.containsKey(id)) {
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
							if (ParamUtils.getDate(dataMap, columnName) != null) {
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
			ex.printStackTrace();
			ExceptionUtils.addMsg("table_copy_" + tableSynthetic.getId(), ex.getMessage());
			logger.error("execute sql error", ex);
			throw new RuntimeException(ex);
		} finally {
			sourceMd5Map = null;
			targetMd5Map = null;
			sourceDataMap = null;

			JdbcUtils.close(sourceRS);
			JdbcUtils.close(sourcePsmt);
			JdbcUtils.close(sourceConn);

			JdbcUtils.close(targetRS);
			JdbcUtils.close(targetPsmt);
			JdbcUtils.close(targetInsertPsmt);
			JdbcUtils.close(targetUpdatePsmt);
			JdbcUtils.close(targetConn);
		}
	}

}
