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
import java.net.InetAddress;
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
import com.glaf.core.domain.SysDataLog;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.factory.SysLogFactory;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FieldType;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.StringTools;
import com.glaf.datamgr.bean.SqliteDBHelper;
import com.glaf.datamgr.bean.SqliteXmlPackage;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.SqlExport;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.query.SqlExportQuery;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.service.SqlExportService;
import com.zaxxer.hikari.HikariDataSource;

public class DataSetExportToSqlite {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void doExport(long sqlExportId, long databaseId, Map<String, Object> params) {
		SqlExportService sqlExportService = ContextFactory.getBean("sqlExportService");
		SqlExport sqlExport = sqlExportService.getSqlExport(sqlExportId);
		if (sqlExport != null) {
			if (StringUtils.isNotEmpty(sqlExport.getDatasetIds())) {
				String sqliteDB = sqlExport.getExportDBName();
				List<String> datasetIds = StringTools.split(sqlExport.getDatasetIds());
				if (datasetIds != null && !datasetIds.isEmpty()) {
					int successCount = 0;
					for (String datasetId : datasetIds) {
						boolean success = false;
						int retry = 0;
						while (retry < 3 && !success) {
							try {
								retry++;
								this.doExport(datasetId, databaseId, params, sqliteDB);
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
	}

	public void doExport(long sqlExportId, long databaseId, String sqliteDB, Map<String, Object> params) {
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
								this.doExport(datasetId, databaseId, params, sqliteDB);
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
	}

	@SuppressWarnings("unchecked")
	public void doExport(String datasetId, long databaseId, Map<String, Object> params, String sqliteDB) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		DataSetService dataSetService = ContextFactory.getBean("dataSetService");
		Database database = databaseService.getDatabaseById(databaseId);
		DataSet ds = dataSetService.getDataSet(datasetId);
		if (ds != null && database != null) {
			Connection conn = null;
			Connection conn2 = null;
			HikariDataSource ds2 = null;
			PreparedStatement psmt = null;
			PreparedStatement psmt02 = null;
			ResultSet rs = null;
			ResultSetMetaData rsmd = null;

			String pk = ds.getRowKey();
			String tableName = ds.getExportTableName();
			SqliteDBHelper helper = new SqliteDBHelper();
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
				StringBuffer sb2 = new StringBuffer();
				StringBuffer sb3 = new StringBuffer();
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
					sb2.append(column.getColumnLabel()).append(", ");
					sb3.append("?, ");
					columns.add(column);
				}

				sb2.delete(sb2.length() - 2, sb2.length());
				sb2.append(") values( ").append(sb3.substring(0, sb3.length() - 2)).append(")");

				ds2 = helper.getDataSource(sqliteDB);
				conn2 = ds2.getConnection();
				conn2.setAutoCommit(false);
				if (!DBUtils.tableExists(conn2, tableName)) {
					DBUtils.createTable(conn2, tableDefinition);
				} else {
					DBUtils.alterTable(conn2, tableDefinition);
					if (StringUtils.equalsIgnoreCase(ds.getDeleteFetch(), "Y")) {
						psmt02 = conn2.prepareStatement(" delete from " + tableName);
						psmt02.executeUpdate();
						psmt02.close();
					}
				}

				psmt02 = conn2.prepareStatement(sb2.toString());
				int i = 1;
				int index = 0;
				while (rs.next()) {
					i = 1;
					for (ColumnDefinition c : columns) {
						Object object = rs.getObject(c.getIndex());
						if (object != null) {
							String javaType = c.getJavaType();
							if ("Integer".equals(javaType)) {
								psmt02.setInt(i++, Integer.parseInt(object.toString()));
							} else if ("Long".equals(javaType)) {
								psmt02.setLong(i++, Long.parseLong(object.toString()));
							} else if ("Double".equals(javaType)) {
								psmt02.setDouble(i++, Double.parseDouble(object.toString()));
							} else if ("Date".equals(javaType)) {
								if (object instanceof java.sql.Date) {
									java.sql.Date date = (java.sql.Date) object;
									psmt02.setString(i++, DateUtils.getDateTime(date));
								} else if (object instanceof java.sql.Timestamp) {
									java.sql.Timestamp timetamp = (java.sql.Timestamp) object;
									psmt02.setString(i++, DateUtils.getDateTime(timetamp));
								}
							} else if ("String".equals(javaType)) {
								psmt02.setString(i++, object.toString());
							} else if ("Clob".equals(javaType)) {
								if (object instanceof java.io.Reader) {
									java.io.Reader reader = (java.io.Reader) object;
									psmt02.setCharacterStream(i++, reader);
								} else if (object instanceof java.sql.Clob) {
									java.sql.Clob clob = (java.sql.Clob) object;
									java.io.Reader reader = clob.getCharacterStream();
									String content = IOUtils.read(reader);
									psmt02.setString(i++, content);
								} else if (object instanceof String) {
									psmt02.setString(i++, (String) object);
								}
							} else if ("Blob".equals(javaType)) {
								if (object instanceof java.sql.Blob) {
									psmt02.setBlob(i++, (java.sql.Blob) object);
								} else if (object instanceof byte[]) {
									psmt02.setBytes(i++, (byte[]) object);
								}
							} else if ("Boolean".equals(javaType)) {
								if (object instanceof Boolean) {
									Boolean b = (Boolean) object;
									if (b) {
										psmt02.setInt(i++, 1);
									} else {
										psmt02.setInt(i++, 0);
									}
								} else {
									psmt02.setInt(i++, Integer.parseInt(object.toString()));
								}
							} else {
								psmt02.setString(i++, object.toString());
							}
						} else {
							if ("Blob".equals(c.getJavaType())) {
								psmt02.setBytes(i++, null);
							} else {
								psmt02.setObject(i++, null);
							}
						}
					}
					psmt02.addBatch();
					index++;
					if (index > 0 && index % 1000 == 0) {
						psmt02.executeBatch();
						conn2.commit();
						psmt02.clearBatch();
					}
				}
				psmt02.executeBatch();
				conn2.commit();
				psmt02.clearBatch();
			} catch (Exception ex) {
				logger.error("export error", ex);
				throw new RuntimeException(ex);
			} finally {
				JdbcUtils.close(rs);
				JdbcUtils.close(psmt);
				JdbcUtils.close(conn);
				JdbcUtils.close(psmt02);
				JdbcUtils.close(conn2);
				if (ds2 != null) {
					ds2.close();
					ds2 = null;
				}
			}
		}
	}

	public void export(long sqlExportId, long databaseId) {
		SqlExportService sqlExportService = ContextFactory.getBean("sqlExportService");
		SqlExport sqlExport = sqlExportService.getSqlExport(sqlExportId);
		if (sqlExport != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("currDate", new java.util.Date());
			params.put("calendar", java.util.Calendar.getInstance());
			params.put("time", java.util.Calendar.getInstance().getTime());
			params.put("currYearMonthDay", DateUtils.getYearMonthDay(new Date()));
			params.put("lastExportTime", sqlExport.getLastExportTime());
			this.doExport(sqlExport.getId(), databaseId, params);
		}
	}

	public void exportAll() {
		SqlExportService sqlExportService = ContextFactory.getBean("sqlExportService");
		SqlExportQuery query = new SqlExportQuery();
		List<SqlExport> list = sqlExportService.list(query);
		if (list != null && !list.isEmpty()) {
			List<String> tables = new ArrayList<String>();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("currDate", new java.util.Date());
			params.put("calendar", java.util.Calendar.getInstance());
			params.put("time", java.util.Calendar.getInstance().getTime());
			params.put("currYearMonthDay", DateUtils.getYearMonthDay(new Date()));
			DatabaseConnectionConfig config = new DatabaseConnectionConfig();
			SqliteXmlPackage xmlPackage = new SqliteXmlPackage();
			for (SqlExport sqlExport : list) {
				params.put("lastExportTime", sqlExport.getLastExportTime());
				if (sqlExport.getDatabaseId() != null && sqlExport.getDatabaseId() > 0) {
					Database database = config.getDatabase(sqlExport.getDatabaseId());
					String sqliteDB = sqlExport.getExportDBName();
					long start = System.currentTimeMillis();
					try {
						String dbpath = SystemProperties.getConfigRootPath() + "/db/" + sqliteDB;
						FileUtils.deleteFile(dbpath);
						this.doExport(sqlExport.getId(), database.getId(), sqliteDB, params);
						xmlPackage.saveSqliteToDB(sqlExport.getUserId(), sqliteDB, sqlExport.getType(), 90, tables);
						try {
							String ip = InetAddress.getLocalHost().getHostAddress();
							SysDataLog log = new SysDataLog();
							log.setBusinessKey(String.valueOf(sqlExport.getId()));
							log.setActorId("system");
							log.setCreateTime(new Date());
							log.setFlag(1);
							log.setIp(ip);
							log.setServiceKey("data_package");
							log.setModuleId("data_package_" + sqlExport.getId());
							log.setTimeMS((int) (System.currentTimeMillis() - start));
							log.setContent(sqlExport.getTitle() + "打包成功。");
							SysLogFactory.getInstance().addLog(log);
						} catch (Exception e) {
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error("export sqlite error", ex);
						try {
							String ip = InetAddress.getLocalHost().getHostAddress();
							SysDataLog log = new SysDataLog();
							log.setBusinessKey(String.valueOf(sqlExport.getId()));
							log.setActorId("system");
							log.setCreateTime(new Date());
							log.setFlag(-1);
							log.setIp(ip);
							log.setServiceKey("data_package");
							log.setModuleId("data_package_" + sqlExport.getId());
							log.setTimeMS((int) (System.currentTimeMillis() - start));
							log.setContent(sqlExport.getTitle() + "打包失败：" + ex.getMessage());
							SysLogFactory.getInstance().addLog(log);
						} catch (Exception e) {
						}
					}
				}
			}
		}
	}

	public List<File> exportAll(long sqlExportId, Collection<Long> databaseIds) {
		List<File> files = new ArrayList<File>();
		SqlExportService sqlExportService = ContextFactory.getBean("sqlExportService");
		SqlExport sqlExport = sqlExportService.getSqlExport(sqlExportId);
		if (sqlExport != null) {
			DatabaseConnectionConfig config = new DatabaseConnectionConfig();
			List<Database> activeDatabases = config.getDatabases();
			if (databaseIds != null && activeDatabases != null && !activeDatabases.isEmpty()) {
				for (Database database : activeDatabases) {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("currDate", new java.util.Date());
					params.put("calendar", java.util.Calendar.getInstance());
					params.put("time", java.util.Calendar.getInstance().getTime());
					params.put("currYearMonthDay", DateUtils.getYearMonthDay(new Date()));
					params.put("lastExportTime", sqlExport.getLastExportTime());
					if (databaseIds.contains(database.getId())) {
						String sqliteDB = sqlExport.getExportDBName() + "_" + database.getDbname();
						String dbpath = SystemProperties.getConfigRootPath() + "/db/" + sqliteDB;
						try {
							FileUtils.deleteFile(dbpath);
							this.doExport(sqlExportId, database.getId(), sqliteDB, params);
							files.add(new File(dbpath));
						} catch (Exception ex) {
							logger.error("export sqlite error", ex);
						}
					}
				}
			}
		}
		return files;
	}

	public byte[] exportAllInOne(long sqlExportId, Collection<Long> databaseIds) {
		SqlExportService sqlExportService = ContextFactory.getBean("sqlExportService");
		SqlExport sqlExport = sqlExportService.getSqlExport(sqlExportId);
		if (sqlExport != null) {
			String sqliteDB = sqlExport.getExportDBName();
			String dbpath = SystemProperties.getConfigRootPath() + "/db/" + sqliteDB;
			try {
				FileUtils.deleteFile(dbpath);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			DatabaseConnectionConfig config = new DatabaseConnectionConfig();
			List<Database> activeDatabases = config.getDatabases();
			if (databaseIds != null && activeDatabases != null && !activeDatabases.isEmpty()) {
				for (Database database : activeDatabases) {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("currDate", new java.util.Date());
					params.put("calendar", java.util.Calendar.getInstance());
					params.put("time", java.util.Calendar.getInstance().getTime());
					params.put("currYearMonthDay", DateUtils.getYearMonthDay(new Date()));
					params.put("lastExportTime", sqlExport.getLastExportTime());
					if (databaseIds.contains(database.getId())) {
						try {
							this.doExport(sqlExportId, database.getId(), sqliteDB, params);
						} catch (Exception ex) {
							logger.error("export sqlite error", ex);
						}
					}
				}
			}
			return FileUtils.getBytes(dbpath);
		}
		return null;
	}

	public void exportAndPackage(long sqlExportId, String actorId) {
		SqlExportService sqlExportService = ContextFactory.getBean("sqlExportService");
		SqlExport sqlExport = sqlExportService.getSqlExport(sqlExportId);
		if (sqlExport != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("currDate", new java.util.Date());
			params.put("calendar", java.util.Calendar.getInstance());
			params.put("time", java.util.Calendar.getInstance().getTime());
			params.put("currYearMonthDay", DateUtils.getYearMonthDay(new Date()));
			params.put("lastExportTime", sqlExport.getLastExportTime());
			DatabaseConnectionConfig config = new DatabaseConnectionConfig();
			List<String> tables = new ArrayList<String>();
			if (sqlExport.getDatabaseId() != null && sqlExport.getDatabaseId() > 0) {
				Database database = config.getDatabase(sqlExport.getDatabaseId());
				String sqliteDB = sqlExport.getExportDBName();
				SqliteXmlPackage xmlPackage = new SqliteXmlPackage();
				try {
					String dbpath = SystemProperties.getConfigRootPath() + "/db/" + sqliteDB;
					FileUtils.deleteFile(dbpath);
					this.doExport(sqlExport.getId(), database.getId(), sqliteDB, params);
					xmlPackage.saveSqliteToDB(sqlExport.getUserId(), sqliteDB, sqlExport.getType(), 90, tables);
				} catch (Exception ex) {
					logger.error("export sqlite error", ex);
					throw new RuntimeException(ex);
				}
			}
		}
	}
}
