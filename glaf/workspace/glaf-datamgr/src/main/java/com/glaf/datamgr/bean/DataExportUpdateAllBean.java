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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;

public class DataExportUpdateAllBean {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void updateExportFlag(long databaseId, TableModel tableModel) {
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

		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		Database database = databaseService.getDatabaseById(databaseId);
		String systemName = Environment.DEFAULT_SYSTEM_NAME;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			if (database != null && StringUtils.isNotEmpty(database.getName())) {
				systemName = database.getName();
			}
			if (DBUtils.tableExists(systemName, tableModel.getTableName())) {
				logger.debug("systemName:" + systemName + "->" + tableModel.getTableName());
				DBUtils.alterTable(systemName, tableModel.getTableName(), columns);
				conn = DBConnectionFactory.getConnection(systemName);
				conn.setAutoCommit(false);

				if (tableModel.getStatements() != null && !tableModel.getStatements().isEmpty()) {
					for (String sql : tableModel.getStatements()) {
						if (DBUtils.isAllowedSql(sql)) {
							psmt = conn.prepareStatement(sql);
							psmt.executeUpdate();
							JdbcUtils.close(psmt);
						}
					}
					conn.commit();
				}

				psmt = conn.prepareStatement(" update " + tableModel.getTableName() + " set _EXPORTFLAG_ = 'N' ");
				psmt.executeUpdate();
				conn.commit();
				JdbcUtils.close(psmt);
				ColumnModel idColumn = tableModel.getIdColumn();

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

				Map<Object, String> updateMap = new HashMap<Object, String>();
				StringBuilder buffer = new StringBuilder();
				String sql = " select " + colBuffer.toString() + " , _MD5_ from " + tableModel.getTableName();
				logger.debug("sql:\n" + sql);
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				while (rs.next()) {
					buffer.delete(0, buffer.length());
					Object id = null;
					switch (idColumn.getJavaType()) {
					case "Integer":
						int intId = rs.getInt(1);
						id = new Long(intId);
						buffer.append(intId).append("_");
						break;
					case "Long":
						long longId = rs.getLong(1);
						buffer.append(longId).append("_");
						break;
					case "String":
						String strId = rs.getString(1);
						buffer.append(strId).append("_");
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
							buffer.append(intValue).append("_");
							break;
						case "Long":
							long longValue = rs.getLong(index);
							buffer.append(longValue).append("_");
							break;
						case "String":
							String strValue = rs.getString(index);
							if (strValue != null) {
								buffer.append(strValue).append("_");
							}
							break;
						case "Date":
							Timestamp t = rs.getTimestamp(index);
							if (t != null) {
								buffer.append(t.getTime()).append("_");
							}
							break;
						default:
							Object object = rs.getObject(index);
							if (object != null) {
								buffer.append(object.toString()).append("_");
							}
							break;
						}
					}

					if (buffer.length() > 1) {
						buffer.delete(buffer.length() - 1, buffer.length());
					}

					String oldMd5 = rs.getString(exportCols.size() + 2);
					String md5 = DigestUtils.md5Hex(buffer.toString());
					if (!StringUtils.equals(md5, oldMd5)) {
						updateMap.put(id, md5);
					}
				}

				JdbcUtils.close(rs);
				JdbcUtils.close(psmt);

				if (updateMap.size() > 0) {
					conn.setAutoCommit(false);
					psmt = conn.prepareStatement(" update " + tableModel.getTableName()
							+ " set _EXPORTFLAG_ = 'Y', _MD5_ = ? where " + idColumn.getColumnName() + " = ? ");
					Set<Entry<Object, String>> entrySet = updateMap.entrySet();
					for (Entry<Object, String> entry : entrySet) {
						Object id = entry.getKey();
						String value = entry.getValue();
						psmt.setString(1, value);
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
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("update error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
			JdbcUtils.close(conn);
		}
	}
}
