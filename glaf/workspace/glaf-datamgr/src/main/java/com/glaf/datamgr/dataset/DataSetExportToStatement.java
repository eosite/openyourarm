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
package com.glaf.datamgr.dataset;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.metamodel.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FieldType;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.StringTools;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.SqlExport;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.service.SqlExportService;

public class DataSetExportToStatement {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public String doExport(long sqlExportId, long databaseId, Map<String, Object> params) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(FileUtils.newline);
		SqlExportService sqlExportService = ContextFactory.getBean("sqlExportService");
		SqlExport sqlExport = sqlExportService.getSqlExport(sqlExportId);
		if (sqlExport != null) {
			if (StringUtils.isNotEmpty(sqlExport.getDatasetIds())) {
				List<String> datasetIds = StringTools.split(sqlExport.getDatasetIds());
				if (datasetIds != null && !datasetIds.isEmpty()) {
					int successCount = 0;
					for (String datasetId : datasetIds) {
						boolean success = false;
						int retry = 0;
						while (retry < 3 && !success) {
							try {
								retry++;
								buffer.append(this.doExport(sqlExport, datasetId, databaseId, params));
								buffer.append(FileUtils.newline);
								buffer.append(FileUtils.newline);
								buffer.append(FileUtils.newline);
								buffer.append(FileUtils.newline);
								successCount++;
								success = true;
								break;
							} catch (Exception ex) {
							}
						}
					}
					if (successCount == datasetIds.size()) {
						sqlExport.setLastExportStatus(1);
					} else {
						sqlExport.setLastExportStatus(-1);
					}
					sqlExport.setLastExportTime(new Date());
					sqlExportService.updateExportStatus(sqlExport);
				}
			}
		}
		return buffer.toString();
	}

	@SuppressWarnings("unchecked")
	public String doExport(SqlExport sqlExport, String datasetId, long databaseId, Map<String, Object> params) {
		StringBuilder buffer = new StringBuilder();
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		DataSetService dataSetService = ContextFactory.getBean("dataSetService");
		Database database = databaseService.getDatabaseById(databaseId);
		DataSet ds = dataSetService.getDataSet(datasetId);
		if (ds != null && database != null) {
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			ResultSetMetaData rsmd = null;
			String pk = ds.getRowKey();
			String tableName = ds.getExportTableName();
			DataSetBuilder builder = new DataSetBuilder();
			try {

				Query query = builder.buildQuery(database.getName(), ds, params);
				String sql = query.toSql();

				if (!DBUtils.isAllowedSql(sql)) {
					throw new RuntimeException(" SQL statement illegal ");
				}

				SqlExecutor sqlExecutor = DBUtils.replaceSQL(sql, params);

				conn = DBConnectionFactory.getConnection();

				psmt = conn.prepareStatement(sqlExecutor.getSql());
				if (sqlExecutor.getParameter() != null) {
					List<Object> values = (List<Object>) sqlExecutor.getParameter();
					JdbcUtils.fillStatement(psmt, values);
				}
				rs = psmt.executeQuery();
				rsmd = rs.getMetaData();
				int count = rsmd.getColumnCount();
				StringBuilder sb2 = new StringBuilder();
				sb2.append(" insert into ").append(tableName).append("(");
				TableDefinition tableDefinition = new TableDefinition();
				tableDefinition.setTableName(tableName);
				List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();
				for (int index = 1; index <= count; index++) {
					int sqlType = rsmd.getColumnType(index);
					ColumnDefinition column = new ColumnDefinition();
					column.setIndex(index);
					column.setColumnName(rsmd.getColumnName(index));
					column.setColumnLabel(rsmd.getColumnLabel(index));
					column.setJavaType(FieldType.getJavaType(sqlType));
					column.setPrecision(rsmd.getPrecision(index));
					column.setScale(rsmd.getScale(index));
					if (column.getScale() == 0 && sqlType == Types.NUMERIC) {
						column.setJavaType("Long");
					}
					column.setName(StringTools.camelStyle(column.getColumnLabel().toLowerCase()));
					if (StringUtils.equalsIgnoreCase(pk, column.getColumnLabel())) {
						tableDefinition.setIdColumn(column);
					} else {
						tableDefinition.addColumn(column);
					}
					sb2.append(column.getColumnLabel().toUpperCase()).append(", ");
					columns.add(column);
				}

				sb2.delete(sb2.length() - 2, sb2.length());
				sb2.append(") values( ");
				StringBuilder sb3 = new StringBuilder();

				String dbType = DBConnectionFactory.getDatabaseType(conn);
				if (!StringUtils.equalsIgnoreCase(sqlExport.getCreateTableFlag(), "Y")) {
					buffer.append("/**");
					buffer.append(FileUtils.newline);
				}
				buffer.append(DBUtils.getCreateTableScript(dbType, tableDefinition));
				if (!StringUtils.equalsIgnoreCase(sqlExport.getCreateTableFlag(), "Y")) {
					buffer.append(FileUtils.newline);
					buffer.append("**/");
				}
				buffer.append(FileUtils.newline);
				buffer.append(FileUtils.newline);

				while (rs.next()) {
					sb3.delete(0, sb3.length());
					for (ColumnDefinition c : columns) {
						Object object = rs.getObject(c.getIndex());
						if (object != null) {
							String javaType = c.getJavaType();
							if ("Integer".equals(javaType)) {
								sb3.append(Integer.parseInt(object.toString())).append(", ");
							} else if ("Long".equals(javaType)) {
								sb3.append(Long.parseLong(object.toString())).append(", ");
							} else if ("Double".equals(javaType)) {
								sb3.append(Double.parseDouble(object.toString())).append(", ");
							} else if ("Date".equals(javaType)) {
								if (object instanceof java.sql.Date) {
									java.sql.Date date = (java.sql.Date) object;
									sb3.append("'").append(DateUtils.getDateTime(date)).append("'").append(", ");
								} else if (object instanceof java.sql.Timestamp) {
									java.sql.Timestamp timetamp = (java.sql.Timestamp) object;
									sb3.append("'").append(DateUtils.getDateTime(timetamp)).append("'").append(", ");
								} else if (object instanceof java.util.Date) {
									java.util.Date date = (java.util.Date) object;
									sb3.append("'").append(DateUtils.getDateTime(date)).append("'").append(", ");
								} else {
									sb3.append("'").append(object.toString()).append("'").append(", ");
								}
							} else if ("String".equals(javaType)) {
								String str = object.toString();
								str = StringTools.replace(str, "'", "\'");
								sb3.append("'").append(str).append("'").append(", ");
							} else if ("Clob".equals(javaType)) {
								sb3.append("null, ");
							} else if ("Blob".equals(javaType)) {
								sb3.append("null, ");
							} else {
								String str = object.toString();
								str = StringTools.replace(str, "'", "\'");
								sb3.append("'").append(str).append("'").append(", ");
							}
						} else {
							sb3.append("null, ");
						}
					}
					if (sb3.toString().endsWith(", ")) {
						sb3.delete(sb3.length() - 2, sb3.length());
					}
					buffer.append(sb2.toString()).append(sb3.toString()).append(");");
					buffer.append(FileUtils.newline);
				}
			} catch (Exception ex) {
				logger.error("export error", ex);
				throw new RuntimeException(ex);
			} finally {
				JdbcUtils.close(rs);
				JdbcUtils.close(psmt);
				JdbcUtils.close(conn);
			}
		}
		return buffer.toString();
	}

	public String export(long sqlExportId, long databaseId) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(FileUtils.newline);
		SqlExportService sqlExportService = ContextFactory.getBean("sqlExportService");
		SqlExport sqlExport = sqlExportService.getSqlExport(sqlExportId);
		if (sqlExport != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("currDate", new java.util.Date());
			params.put("calendar", java.util.Calendar.getInstance());
			params.put("time", java.util.Calendar.getInstance().getTime());
			params.put("currYearMonthDay", DateUtils.getYearMonthDay(new Date()));
			params.put("lastExportTime", sqlExport.getLastExportTime());
			buffer.append(this.doExport(sqlExportId, databaseId, params));
			buffer.append(FileUtils.newline);
			buffer.append(FileUtils.newline);
			buffer.append(FileUtils.newline);
			buffer.append(FileUtils.newline);
		}

		return buffer.toString();
	}

	public List<File> exportAll(long sqlExportId) {
		List<File> files = new ArrayList<File>();
		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> activeDatabases = config.getDatabases();
		if (activeDatabases != null && !activeDatabases.isEmpty()) {
			for (Database database : activeDatabases) {
				String filename = null;
				if (StringUtils.isNotEmpty(database.getSection())) {
					filename = database.getSection() + ".sql";
				} else if (StringUtils.isNotEmpty(database.getMapping())) {
					filename = database.getMapping() + ".sql";
				} else {
					filename = database.getDbname() + ".sql";
				}
				try {
					byte[] bytes = this.export(sqlExportId, database.getId()).getBytes();
					File file = new File(SystemProperties.getConfigRootPath() + "/temp/" + filename);
					FileUtils.deleteFile(file.getAbsolutePath());
					FileUtils.save(file.getAbsolutePath(), bytes);
					files.add(file);
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error("export error", ex);
				}
			}
		}
		return files;
	}

	public List<File> exportAll(long sqlExportId, Collection<Long> databaseIds) {
		List<File> files = new ArrayList<File>();
		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> activeDatabases = config.getDatabases();
		if (databaseIds != null && activeDatabases != null && !activeDatabases.isEmpty()) {
			for (Database database : activeDatabases) {
				if (databaseIds.contains(database.getId())) {
					String filename = null;
					if (StringUtils.isNotEmpty(database.getSection())) {
						filename = database.getSection() + ".sql";
					} else if (StringUtils.isNotEmpty(database.getMapping())) {
						filename = database.getMapping() + ".sql";
					} else {
						filename = database.getDbname() + ".sql";
					}
					try {
						byte[] bytes = this.export(sqlExportId, database.getId()).getBytes();
						File file = new File(SystemProperties.getConfigRootPath() + "/temp/" + filename);
						FileUtils.save(file.getAbsolutePath(), bytes);
						files.add(file);
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error("export error", ex);
					}
				}
			}
		}
		return files;
	}

	public List<File> exportAllFile(long sqlExportId) {
		List<File> files = new ArrayList<File>();
		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> activeDatabases = config.getDatabases();
		if (activeDatabases != null && !activeDatabases.isEmpty()) {
			for (Database database : activeDatabases) {
				String filename = null;
				if (StringUtils.isNotEmpty(database.getSection())) {
					filename = database.getSection() + ".sql";
				} else if (StringUtils.isNotEmpty(database.getMapping())) {
					filename = database.getMapping() + ".sql";
				} else {
					filename = database.getDbname() + ".sql";
				}
				try {
					byte[] bytes = this.export(sqlExportId, database.getId()).getBytes();
					File file = new File(SystemProperties.getConfigRootPath() + "/temp/" + filename);
					FileUtils.save(file.getAbsolutePath(), bytes);
					files.add(file);
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error("export error", ex);
				}
			}
		}
		return files;
	}

}
