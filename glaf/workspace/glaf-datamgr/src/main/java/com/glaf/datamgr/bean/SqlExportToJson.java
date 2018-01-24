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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.glaf.datamgr.domain.SqlDefinition;
import com.glaf.datamgr.domain.SqlExport;
import com.glaf.datamgr.service.SqlDefinitionService;
import com.glaf.datamgr.service.SqlExportService;

public class SqlExportToJson {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public JSONArray doExport(long sqlExportId, long databaseId, Map<String, Object> params) {
		JSONArray array = new JSONArray();
		SqlExportService sqlExportService = ContextFactory.getBean("sqlExportService");
		SqlExport sqlExport = sqlExportService.getSqlExport(sqlExportId);
		if (sqlExport != null) {
			if (StringUtils.isNotEmpty(sqlExport.getSqlDefIds())) {
				List<Long> sqlDefIds = StringTools.splitToLong(sqlExport.getSqlDefIds());
				if (sqlDefIds != null && !sqlDefIds.isEmpty()) {
					int successCount = 0;
					for (Long sqlDefId : sqlDefIds) {
						boolean success = false;
						int retry = 0;
						while (retry < 3 && !success) {
							try {
								retry++;
								array.addAll(this.doExport(sqlExport, sqlDefId, databaseId, params));
								successCount++;
								success = true;
								break;
							} catch (Exception ex) {
							}
						}
					}
					if (successCount == sqlDefIds.size()) {
						sqlExport.setLastExportStatus(1);
					} else {
						sqlExport.setLastExportStatus(-1);
					}
					sqlExport.setLastExportTime(new Date());
					sqlExportService.updateExportStatus(sqlExport);
				}
			}
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	public JSONArray doExport(SqlExport sqlExport, long sqlDefId, long databaseId, Map<String, Object> params) {
		JSONArray array = new JSONArray();
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		SqlDefinitionService sqlDefinitionService = ContextFactory.getBean("sqlDefinitionService");
		Database database = databaseService.getDatabaseById(databaseId);
		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		if (sqlDef != null) {
			String sql = sqlDef.getSql();

			if (!DBUtils.isAllowedSql(sql)) {
				throw new RuntimeException(" SQL statement illegal ");
			}

			String pk = sqlDef.getRowKey();
			String tableName = sqlDef.getExportTableName();
			SqlExecutor sqlExecutor = DBUtils.replaceSQL(sql, params);

			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			ResultSetMetaData rsmd = null;
			try {
				if (database != null && StringUtils.isNotEmpty(database.getName())) {
					conn = DBConnectionFactory.getConnection(database.getName());
				} else {
					conn = DBConnectionFactory.getConnection();
				}
				psmt = conn.prepareStatement(sqlExecutor.getSql());
				if (sqlExecutor.getParameter() != null) {
					List<Object> values = (List<Object>) sqlExecutor.getParameter();
					JdbcUtils.fillStatement(psmt, values);
				}
				rs = psmt.executeQuery();
				rsmd = rs.getMetaData();
				int count = rsmd.getColumnCount();

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
					columns.add(column);
				}
				while (rs.next()) {
					JSONObject json = new JSONObject();
					for (ColumnDefinition c : columns) {
						Object object = rs.getObject(c.getIndex());
						if (object != null) {
							String javaType = c.getJavaType();
							if ("Integer".equals(javaType)) {
								json.put(c.getColumnLabel(), Integer.parseInt(object.toString()));
							} else if ("Long".equals(javaType)) {
								json.put(c.getColumnLabel(), Long.parseLong(object.toString()));
							} else if ("Double".equals(javaType)) {
								json.put(c.getColumnLabel(), Double.parseDouble(object.toString()));
							} else if ("Date".equals(javaType)) {
								if (object instanceof java.sql.Date) {
									java.sql.Date date = (java.sql.Date) object;
									json.put(c.getColumnLabel(), DateUtils.getDateTime(date));
								} else if (object instanceof java.sql.Timestamp) {
									java.sql.Timestamp timetamp = (java.sql.Timestamp) object;
									json.put(c.getColumnLabel(), DateUtils.getDateTime(timetamp));
								} else if (object instanceof java.util.Date) {
									java.util.Date date = (java.util.Date) object;
									json.put(c.getColumnLabel(), DateUtils.getDateTime(date));
								} else {
									json.put(c.getColumnLabel(), object);
								}
							} else if ("String".equals(javaType)) {
								String str = object.toString();
								json.put(c.getColumnLabel(), str);
							} else {
								String str = object.toString();
								json.put(c.getColumnLabel(), str);
							}
						}
					}
					array.add(json);
				}
			} catch (Exception ex) {
				logger.error("export json error", ex);
				throw new RuntimeException(ex);
			} finally {
				JdbcUtils.close(rs);
				JdbcUtils.close(psmt);
				JdbcUtils.close(conn);
			}
		}
		return array;
	}

	public String export(long sqlExportId, long databaseId) {
		JSONArray array = null;
		SqlExportService sqlExportService = ContextFactory.getBean("sqlExportService");
		SqlExport sqlExport = sqlExportService.getSqlExport(sqlExportId);
		if (sqlExport != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("currDate", new java.util.Date());
			params.put("calendar", java.util.Calendar.getInstance());
			params.put("time", java.util.Calendar.getInstance().getTime());
			params.put("currYearMonthDay", DateUtils.getYearMonthDay(new Date()));
			params.put("lastExportTime", sqlExport.getLastExportTime());
			array = this.doExport(sqlExportId, databaseId, params);
		}
		if (array != null) {
			return array.toJSONString();
		}
		return null;
	}

	public List<File> exportAll(long sqlExportId) {
		List<File> files = new ArrayList<File>();
		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> activeDatabases = config.getDatabases();
		if (activeDatabases != null && !activeDatabases.isEmpty()) {
			for (Database database : activeDatabases) {
				String filename = null;
				if (StringUtils.isNotEmpty(database.getSection())) {
					filename = database.getSection() + ".json";
				} else if (StringUtils.isNotEmpty(database.getMapping())) {
					filename = database.getMapping() + ".json";
				} else {
					filename = database.getDbname() + ".json";
				}
				try {
					String json = this.export(sqlExportId, database.getId());
					if (json != null) {
						byte[] bytes = json.getBytes();
						File file = new File(SystemProperties.getConfigRootPath() + "/temp/" + filename);
						FileUtils.deleteFile(file.getAbsolutePath());
						FileUtils.save(file.getAbsolutePath(), bytes);
						files.add(file);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error("export json error", ex);
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
						filename = database.getSection() + ".json";
					} else if (StringUtils.isNotEmpty(database.getMapping())) {
						filename = database.getMapping() + ".json";
					} else {
						filename = database.getDbname() + ".json";
					}
					try {
						byte[] bytes = this.export(sqlExportId, database.getId()).getBytes();
						File file = new File(SystemProperties.getConfigRootPath() + "/temp/" + filename);
						FileUtils.save(file.getAbsolutePath(), bytes);
						files.add(file);
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error("export json error", ex);
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
					filename = database.getSection() + ".json";
				} else if (StringUtils.isNotEmpty(database.getMapping())) {
					filename = database.getMapping() + ".json";
				} else {
					filename = database.getDbname() + ".json";
				}
				try {
					byte[] bytes = this.export(sqlExportId, database.getId()).getBytes();
					File file = new File(SystemProperties.getConfigRootPath() + "/temp/" + filename);
					FileUtils.save(file.getAbsolutePath(), bytes);
					files.add(file);
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error("export json error", ex);
				}
			}
		}
		return files;
	}

}
