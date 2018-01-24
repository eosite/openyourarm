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
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.DataFile;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.IBlobService;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;

public class DataExportAllBean {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public JSONArray exportJson(long databaseId, String serviceKey, TableModel tableModel) {
		return this.exportJson(databaseId, serviceKey, tableModel, true);
	}

	public JSONArray exportJson(long databaseId, String serviceKey, TableModel tableModel, boolean insertBlob) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		Database database = databaseService.getDatabaseById(databaseId);

		List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();
		ColumnDefinition md5Col = new ColumnDefinition();
		md5Col.setName("md5");
		md5Col.setColumnName("_MD5_");
		md5Col.setJavaType("String");
		md5Col.setLength(200);
		columns.add(md5Col);

		ColumnDefinition exportFlagCol = new ColumnDefinition();
		exportFlagCol.setName("exportFlag");
		exportFlagCol.setColumnName("_EXPORTFLAG_");
		exportFlagCol.setJavaType("String");
		exportFlagCol.setLength(20);
		columns.add(exportFlagCol);

		ColumnDefinition syncFlagCol = new ColumnDefinition();
		syncFlagCol.setName("syncFlag");
		syncFlagCol.setColumnName("_SYNCFLAG_");
		syncFlagCol.setJavaType("Integer");
		columns.add(syncFlagCol);

		ColumnDefinition syncTimeCol = new ColumnDefinition();
		syncTimeCol.setName("syncTime");
		syncTimeCol.setColumnName("_SYNCTIME_");
		syncTimeCol.setJavaType("Date");
		columns.add(syncTimeCol);

		ColumnDefinition syncOperatorTypeCol = new ColumnDefinition();
		syncOperatorTypeCol.setName("syncOperatorType");
		syncOperatorTypeCol.setColumnName("_SYNCOPERATORTYPE_");
		syncOperatorTypeCol.setJavaType("String");
		syncOperatorTypeCol.setLength(10);
		columns.add(syncOperatorTypeCol);

		JSONArray result = new JSONArray();
		List<Object> exportList = new ArrayList<Object>();
		ColumnModel idColumn = tableModel.getIdColumn();

		String systemName = Environment.DEFAULT_SYSTEM_NAME;
		java.util.Date exportTime = new java.util.Date();
		java.sql.Timestamp time = DateUtils.toTimestamp(exportTime);
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			if (database != null && StringUtils.isNotEmpty(database.getName())) {
				systemName = database.getName();
			}
			logger.debug("systemName:" + systemName);
			DBUtils.alterTable(systemName, tableModel.getTableName(), columns);
			conn = DBConnectionFactory.getConnection(systemName);

			List<ColumnModel> cols = tableModel.getColumns();
			StringBuilder colBuffer = new StringBuilder();
			colBuffer.append(idColumn.getColumnName());
			List<ColumnModel> exportCols = new ArrayList<ColumnModel>();

			for (ColumnModel col : cols) {
				if (StringUtils.equals(col.getExportFlag(), "true")) {
					exportCols.add(col);
					colBuffer.append(", ").append(col.getColumnName());
				}
			}

			String sql = " select  " + colBuffer.toString() + " from " + tableModel.getTableName();
			logger.debug(sql);
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				JSONObject json = new JSONObject();
				switch (idColumn.getJavaType()) {
				case "Integer":
					int intId = rs.getInt(1);
					exportList.add(new Long(intId));
					json.put(idColumn.getName(), intId);
					break;
				case "Long":
					long longId = rs.getLong(1);
					exportList.add(longId);
					json.put(idColumn.getName(), longId);
					break;
				case "String":
					String strId = rs.getString(1);
					exportList.add(strId);
					json.put(idColumn.getName(), strId);
					break;
				default:
					break;
				}
				int index = 1;
				for (ColumnModel col : exportCols) {
					index++;
					switch (col.getJavaType()) {
					case "Integer":
						int intValue = rs.getInt(index);
						json.put(col.getName(), intValue);
						break;
					case "Long":
						long longValue = rs.getLong(index);
						json.put(col.getName(), longValue);
						break;
					case "String":
						String strValue = rs.getString(index);
						json.put(col.getName(), strValue);
						break;
					case "Date":
						Timestamp t = rs.getTimestamp(index);
						json.put(col.getName(), t);
						break;
					default:
						json.put(col.getName(), rs.getObject(index));
						break;
					}
				}
				result.add(json);
			}
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);

		} catch (Exception ex) {
			logger.error("execute query error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
			JdbcUtils.close(conn);
		}

		if (result.size() > 0) {
			String currSystemName = Environment.CURRENT_SYSTEM_NAME;
			boolean success = false;
			int retry = 0;
			if (insertBlob) {
				while (retry < 3 && !success) {
					try {
						retry++;
						Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
						IBlobService blobService = ContextFactory.getBean("blobService");
						DataFile dataFile = new BlobItemEntity();
						dataFile.setCreateBy("system");
						dataFile.setCreateDate(exportTime);
						dataFile.setData(result.toJSONString().getBytes());
						dataFile.setSize(result.toJSONString().length());
						dataFile.setDeleteFlag(0);
						dataFile.setId(serviceKey + "_" + databaseId + "_" + exportTime.getTime());
						dataFile.setFileId(dataFile.getId());
						dataFile.setFilename(
								serviceKey + "_" + databaseId + "_" + DateUtils.getNowYearMonthDayHHmmss() + ".json");
						dataFile.setContentType("application/json");
						dataFile.setLastModified(exportTime.getTime());
						dataFile.setLocked(0);
						dataFile.setName(tableModel.getTitle());
						dataFile.setObjectId("databaseId");
						dataFile.setObjectValue(String.valueOf(databaseId));
						dataFile.setBusinessKey(serviceKey + "_" + databaseId);
						dataFile.setServiceKey(serviceKey);
						dataFile.setStatus(9);
						dataFile.setType("export");
						blobService.insertBlob(dataFile);
						success = true;
						break;
					} catch (Exception ex) {
						logger.error("save blob error", ex);
					} finally {
						Environment.setCurrentSystemName(currSystemName);
					}
				}
			} else {
				success = true;
			}
			if (success && exportList.size() > 0) {
				retry = 0;
				success = false;
				while (retry < 3 && !success) {
					try {
						retry++;
						conn = DBConnectionFactory.getConnection(systemName);
						conn.setAutoCommit(false);
						psmt = conn.prepareStatement(" update " + tableModel.getTableName()
								+ " set _SYNCFLAG_ = 1, _SYNCTIME_ = ? where " + idColumn.getColumnName() + " = ? ");
						for (Object id : exportList) {
							psmt.setTimestamp(1, time);
							switch (idColumn.getJavaType()) {
							case "Integer":
								psmt.setInt(2, (Integer) id);
								break;
							case "Long":
								psmt.setLong(2, (Long) id);
								break;
							case "String":
								psmt.setString(2, (String) id);
								break;
							default:
								break;
							}
							psmt.addBatch();
						}
						psmt.executeBatch();
						conn.commit();
						success = true;
						break;
					} catch (Exception ex) {
						logger.error("update status error", ex);
						throw new RuntimeException(ex);
					} finally {
						JdbcUtils.close(psmt);
						JdbcUtils.close(conn);
					}
				}
			}
		}
		return result;
	}
}
