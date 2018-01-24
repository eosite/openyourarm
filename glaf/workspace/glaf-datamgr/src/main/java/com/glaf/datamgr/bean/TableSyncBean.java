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
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.factory.TableFactory;
import com.glaf.core.jdbc.BatchUpdateBean;
import com.glaf.core.jdbc.BulkInsertBean;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.StringTools;
import com.glaf.datamgr.domain.TableExecution;
import com.glaf.datamgr.domain.TableSync;
import com.glaf.datamgr.jdbc.DynamicSqlHelper;
import com.glaf.datamgr.jdbc.MyBatisHelper;
import com.glaf.datamgr.service.TableSyncService;
import com.glaf.datamgr.util.ExceptionUtils;

public class TableSyncBean implements java.lang.Runnable {
	protected static TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected static Configuration conf = BaseConfiguration.create();

	protected DynamicSqlHelper sqlHelper = new DynamicSqlHelper();

	protected long sourceDatabaseId;

	protected long targetDatabaseId;

	protected long tableSyncId;

	protected boolean result;

	public TableSyncBean() {

	}

	public TableSyncBean(long sourceDatabaseId, long targetDatabaseId, long tableSyncId) {
		this.sourceDatabaseId = sourceDatabaseId;
		this.targetDatabaseId = targetDatabaseId;
		this.tableSyncId = tableSyncId;
	}

	public boolean execute(long sourceDatabaseId, long targetDatabaseId, long tableSyncId) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		TableSyncService tableSyncService = ContextFactory.getBean("tableSyncService");
		try {
			Database sourceDatabase = databaseService.getDatabaseById(sourceDatabaseId);
			Database targetDatabase = databaseService.getDatabaseById(targetDatabaseId);
			TableSync tableSync = tableSyncService.getTableSync(tableSyncId);
			if (tableSync != null) {
				ColumnDefinition idColumn = null;
				List<ColumnDefinition> sourceColumns = DBUtils.getColumnDefinitions(sourceDatabase.getName(),
						tableSync.getSourceTableName());
				Map<String, ColumnDefinition> columnMap = new HashMap<String, ColumnDefinition>();
				for (ColumnDefinition col : sourceColumns) {
					columnMap.put(col.getColumnName().toLowerCase(), col);
				}
				TableDefinition tableDefinition = new TableDefinition();
				tableDefinition.setTableName(tableSync.getTargetTableName());

				List<ColumnDefinition> columns = tableDefinition.getColumns();
				List<String> syncColumns = StringTools.split(tableSync.getSyncColumns());

				/**
				 * 同步的列将成为目标表的字段
				 */
				for (String syncColumn : syncColumns) {
					ColumnDefinition col = columnMap.get(syncColumn.toLowerCase());
					if (col != null) {
						/**
						 * 新目标表的主键以配置的值为准，数据类型即配置字段的类型
						 */
						if (StringUtils.equalsIgnoreCase(col.getColumnName(), tableSync.getPrimaryKey())) {
							col.setPrimaryKey(true);
							idColumn = col;
							tableDefinition.setIdColumn(col);
							continue;
						} else {
							col.setPrimaryKey(false);
						}
						columns.add(col);
					}
				}

				if (idColumn == null) {
					if (StringUtils.isNotEmpty(tableSync.getPrimaryKey())) {
						idColumn = columnMap.get(tableSync.getPrimaryKey().toLowerCase());
						tableDefinition.setIdColumn(idColumn);
					}
				}

				if (tableDefinition.getIdColumn() == null) {
					idColumn = new ColumnDefinition();
					idColumn.setColumnName("SHAID_");
					idColumn.setJavaType("String");
					idColumn.setLength(64);
					tableDefinition.setIdColumn(idColumn);
				}

				ColumnDefinition col2 = new ColumnDefinition();
				col2.setColumnName("EX_DATABASEID_");
				col2.setJavaType("Long");
				columns.add(col2);

				ColumnDefinition col6 = new ColumnDefinition();
				col6.setColumnName("_JOBNO_");
				if (DBUtils.ORACLE.equals(targetDatabase.getType())) {
					col6.setColumnName("EX_JOBNO_");
				}
				col6.setJavaType("String");
				col6.setLength(80);
				columns.add(col6);

				tableDefinition.setColumns(columns);

				if (DBUtils.tableExists(targetDatabase.getName(), tableSync.getTargetTableName())) {
					DBUtils.alterTable(targetDatabase.getName(), tableDefinition);
				} else {
					DBUtils.createTable(targetDatabase.getName(), tableDefinition);
				}

				TableFactory.clearCache("default", tableSync.getSourceTableName());
				TableFactory.clearCache(targetDatabase.getName(), tableSync.getSourceTableName());

				TableFactory.clearCache("default", tableSync.getTargetTableName());
				TableFactory.clearCache(targetDatabase.getName(), tableSync.getTargetTableName());

				if (StringUtils.equals(tableSync.getInsertOnly(), "Y")) {
					TableSyncIncrementBean bean = new TableSyncIncrementBean();
					return bean.execute(sourceDatabaseId, targetDatabaseId, tableSync);
				}

				return this.execute(sourceDatabaseId, targetDatabaseId, tableSync);
			}
			return false;
		} catch (Exception ex) {
			logger.error("execute error", ex);
			throw new RuntimeException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public boolean execute(long sourceDatabaseId, long targetDatabaseId, TableSync tableSync) {
		/**
		 * if (!(StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(),
		 * "useradd_") ||
		 * StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(),
		 * "cell_useradd") ||
		 * StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "etl_") ||
		 * StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "sync_") ||
		 * StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(),
		 * "tree_table_") ||
		 * StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "tmp"))) {
		 * return false; }
		 **/

		if (StringUtils.equalsIgnoreCase(tableSync.getTargetTableName(), "userinfo")) {
			return false;
		}
		if (StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "sys_")) {
			return false;
		}
		if (StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "form_")) {
			return false;
		}
		if (StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "act_")) {
			return false;
		}
		if (StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "jbpm_")) {
			return false;
		}

		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		Database sourceDatabase = databaseService.getDatabaseById(sourceDatabaseId);
		Database targetDatabase = databaseService.getDatabaseById(targetDatabaseId);
		if (StringUtils.equalsIgnoreCase(sourceDatabase.getHost(), targetDatabase.getHost())
				&& StringUtils.equalsIgnoreCase(sourceDatabase.getDbname(), targetDatabase.getDbname())
				&& StringUtils.equalsIgnoreCase(tableSync.getSourceTableName(), tableSync.getTargetTableName())) {
			return false;
		}

		logger.debug("----------------------------------------------------------------");
		logger.debug("----------------------TableSyncBean execute---------------------");
		logger.debug("----------------------------------------------------------------");

		Map<String, String> sourceMD5Map = new HashMap<String, String>();
		Map<String, String> targetMD5Map = new HashMap<String, String>();
		List<String> syncColumns = StringTools.splitLowerCase(tableSync.getSyncColumns());
		Collections.sort(syncColumns);// 排序，确保修改字段顺序时不影响结果
		logger.debug("sync columns:" + syncColumns);

		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		String jobNo = tableSync.getTargetTableName() + "_" + DateUtils.getNowYearMonthDayHHmmss();

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("_date_", now);
		parameter.put("_now_", now);
		parameter.put("_year_", year);
		parameter.put("_month_", month);
		parameter.put("_week_", week);
		parameter.put("_day_", day);
		parameter.put("_nowYearMonth_", DateUtils.getNowYearMonth());
		parameter.put("_nowYearMonthDay_", DateUtils.getNowYearMonthDay());

		int batchSize = conf.getInt("table_sync_batchSize", 50000);

		String sql = null;
		String key = null;
		Object value = null;
		String sourceMD5 = null;
		String targetMD5 = null;
		SqlExecutor sqlExecutor = null;
		SqlExecutor sqlExecutor2 = null;
		List<Map<String, Object>> source_list = null;
		List<Map<String, Object>> target_list = null;
		Set<String> keys = new HashSet<String>();
		Set<String> targetKeys = new HashSet<String>();
		StringBuffer buffer = new StringBuffer();
		MyBatisHelper helper = new MyBatisHelper();
		BulkInsertBean bulkInsertBean = new BulkInsertBean();
		BatchUpdateBean batchUpdateBean = new BatchUpdateBean();
		List<Map<String, Object>> insert_list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> update_list = new ArrayList<Map<String, Object>>();

		Connection sourceConn = null;
		PreparedStatement sourcePsmt = null;
		ResultSet sourceRS = null;

		Connection targetConn = null;
		PreparedStatement targetPsmt = null;
		ResultSet targetRS = null;
		try {

			sqlExecutor2 = sqlHelper.buildSql("table_sync", String.valueOf(tableSync.getId()), parameter);

			sourceConn = DBConnectionFactory.getConnection(sourceDatabase.getName());
			targetConn = DBConnectionFactory.getConnection(targetDatabase.getName());

			logger.debug("目标数据库已经连接：" + targetDatabase.getDbname());
			List<ColumnDefinition> sourceColumns = DBUtils.getColumnDefinitions(sourceConn,
					tableSync.getSourceTableName());
			List<ColumnDefinition> targetColumns = DBUtils.getColumnDefinitions(targetConn,
					tableSync.getTargetTableName());

			TableDefinition tableDefinition = new TableDefinition();
			tableDefinition.setTableName(tableSync.getTargetTableName());
			tableDefinition.setColumns(targetColumns);

			ColumnDefinition idColumn = null;

			StringBuilder selectBuffer = new StringBuilder();

			for (ColumnDefinition column : sourceColumns) {
				if (syncColumns.contains(column.getColumnName().toLowerCase())) {
					selectBuffer.append(column.getColumnName()).append(", ");
				}
			}

			for (ColumnDefinition column : targetColumns) {
				logger.debug(column.getColumnName() + "\t" + column.getJavaType());
				if (StringUtils.equalsIgnoreCase(column.getJavaType(), "Blob")) {
					batchSize = 20;
					logger.debug(column.getColumnName() + " is blob");
				}
			}

			selectBuffer.delete(selectBuffer.length() - 2, selectBuffer.length());

			if (idColumn == null) {
				Map<String, ColumnDefinition> columnMap = new HashMap<String, ColumnDefinition>();
				for (ColumnDefinition col : sourceColumns) {
					columnMap.put(col.getColumnName().toLowerCase(), col);
				}
				/**
				 * 物理主键以配置的字段为准，不以来源表的物理主键。
				 */
				if (StringUtils.isNotEmpty(tableSync.getPrimaryKey())) {
					idColumn = columnMap.get(tableSync.getPrimaryKey().toLowerCase());
				}
			}

			if (idColumn == null) {
				// throw new RuntimeException(tableSync.getSourceTableName() + " primary key is
				// null");
			}

			int maxIndexId = 0;
			int minIndexId = 0;
			int maxForEach = 1;
			String idColumnName = null;

			if (idColumn != null) {
				idColumnName = idColumn.getColumnName().toLowerCase();

				if ((StringUtils.equalsIgnoreCase(idColumn.getJavaType(), "Integer")
						|| StringUtils.equalsIgnoreCase(idColumn.getJavaType(), "Long"))) {
					sql = " select max(" + idColumn.getColumnName() + "), min(" + idColumn.getColumnName() + ") from "
							+ tableSync.getSourceTableName();
					sourcePsmt = sourceConn.prepareStatement(sql);
					sourceRS = sourcePsmt.executeQuery();
					if (sourceRS.next()) {
						maxIndexId = sourceRS.getInt(1);
						minIndexId = sourceRS.getInt(2);
					}
					JdbcUtils.close(sourceRS);
					JdbcUtils.close(sourcePsmt);
					if ((maxIndexId - minIndexId) < batchSize * 500) {
						maxForEach = maxIndexId / batchSize + 1;
					}
				}
			}

			sql = " select count(*) from " + tableSync.getSourceTableName();
			if (StringUtils.isNotEmpty(tableSync.getSqlCriteria())) {
				sql = sql + " where 1=1 and " + tableSync.getSqlCriteria();
			}

			if (!DBUtils.isLegalQuerySql(sql)) {
				throw new RuntimeException("sql statement illegal");
			}

			int totalResult = 0;
			boolean skipResult = false;// 是否使用JDBC游标分页
			sourcePsmt = sourceConn.prepareStatement(sql + sqlExecutor2.getSql());
			if (sqlExecutor2.getParameter() != null) {
				List<Object> values = (List<Object>) sqlExecutor2.getParameter();
				JdbcUtils.fillStatement(sourcePsmt, values);
			}
			sourceRS = sourcePsmt.executeQuery();
			if (sourceRS.next()) {
				totalResult = sourceRS.getInt(1);
			}
			JdbcUtils.close(sourceRS);
			JdbcUtils.close(sourcePsmt);

			/**
			 * 总记录数很小就不分页，一次处理全部记录
			 */
			if (totalResult <= batchSize) {
				maxForEach = 1;
			}

			/**
			 * 如果主键不连续，跨度太大，使用JDBC游标分页
			 */
			if (maxForEach > 200) {
				skipResult = true;
			}

			/**
			 * 如果主键是字符串并且总记录数超过批处理大小，使用JDBC游标分页
			 */
			if (totalResult > batchSize && idColumn != null && StringUtils.equals(idColumn.getJavaType(), "String")) {
				skipResult = true;
			}

			if (idColumn == null) {// 来源表没有主键字段,只能做全表扫描
				skipResult = true;
			}

			// skipResult = true;

			boolean deleteFetch = false;
			if (StringUtils.equalsIgnoreCase(tableSync.getDeleteFetch(), "Y")) {
				if (StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "useradd_")
						|| StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "etl_")
						|| StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "sync_")
						|| StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "tree_table_")
						|| StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "tmp")) {
					sql = " delete from " + tableSync.getTargetTableName() + " where EX_DATABASEID_ = "
							+ targetDatabase.getId();

					if (StringUtils.isNotEmpty(tableSync.getSqlCriteria())) {
						sql = sql + " and " + tableSync.getSqlCriteria();
					}
					logger.info("execute delete sql:" + sql);
					DBUtils.executeSchemaResourceIgnoreException(targetConn, sql);
					// targetConn.commit();
					deleteFetch = true;
				}
				/**
				 * 已有项目使用了两个表做特殊处理。
				 */
				if (StringUtils.equals(tableSync.getTargetTableName(), "cell_useradd8595")
						|| StringUtils.equals(tableSync.getTargetTableName(), "cell_useradd8617")) {
					sql = " delete from " + tableSync.getTargetTableName();
					DBUtils.executeSchemaResourceIgnoreException(targetConn, sql);
					// targetConn.commit();
					deleteFetch = true;
				}
			} else {
				// if
				// (StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(),
				// "cell_useradd")) {
				// if (DBUtils.getTableCount(targetConn,
				// tableSync.getTargetTableName()) > 0) {
				// if (TableFactory.renameTable(targetConn,
				// tableSync.getTargetTableName())) {
				// tableDefinition.setTableName(tableSync.getTargetTableName());
				// DBUtils.createTable(targetConn, tableDefinition);
				// }
				// }
				// }
			}

			if (skipResult) {
				int firstResult = 0;

				logger.debug("totalResult size:" + totalResult);

				if (deleteFetch) {// 每次删除重新提取的情况,不用考虑是否重复,直接将来源表数据复制到目标表即可

					if (idColumn != null) {
						sql = " select " + idColumn.getColumnName() + " from " + tableSync.getTargetTableName();
					} else {
						sql = " select shaid_ from " + tableSync.getTargetTableName();
					}

					int index = 0;
					targetPsmt = targetConn.prepareStatement(sql);
					targetRS = targetPsmt.executeQuery();
					while (targetRS.next()) {
						keys.add(targetRS.getString(1));
						index++;
						if (index % 50000 == 0) {
							logger.debug("index->" + index);
						}
					}

					JdbcUtils.close(targetRS);
					JdbcUtils.close(targetPsmt);

					sql = " select " + selectBuffer.toString() + " from " + tableSync.getSourceTableName();
					if (StringUtils.isNotEmpty(tableSync.getSqlCriteria())) {
						sql = sql + " where 1=1 and " + tableSync.getSqlCriteria();
					}

					sqlExecutor = DBUtils.replaceSQL(sql, parameter);
					logger.debug("source query sql:" + sqlExecutor.getSql());
					sourcePsmt = sourceConn.prepareStatement(sqlExecutor.getSql() + sqlExecutor2.getSql());
					if (sqlExecutor.getParameter() != null) {
						List<Object> values = (List<Object>) sqlExecutor.getParameter();
						if (sqlExecutor2.getParameter() != null) {
							List<Object> values2 = (List<Object>) sqlExecutor2.getParameter();
							if (values2 != null) {
								values.addAll(values2);
							}
						}
						JdbcUtils.fillStatement(sourcePsmt, values);
					}
					sourceRS = sourcePsmt.executeQuery();

					List<String> columns = new ArrayList<String>();
					List<TypeHandler<?>> typeHandlers = new ArrayList<TypeHandler<?>>();
					ResultSetMetaData rsmd = sourceRS.getMetaData();
					for (int i = 0, n = rsmd.getColumnCount(); i < n; i++) {
						columns.add(rsmd.getColumnLabel(i + 1));
						try {
							Class<?> type = Resources.classForName(rsmd.getColumnClassName(i + 1));
							TypeHandler<?> typeHandler = typeHandlerRegistry.getTypeHandler(type);
							if (typeHandler == null) {
								typeHandler = typeHandlerRegistry.getTypeHandler(Object.class);
							}
							typeHandlers.add(typeHandler);
						} catch (Exception ex) {
							typeHandlers.add(typeHandlerRegistry.getTypeHandler(Object.class));
						}
					}

					index = 0;
					String name = null;
					TypeHandler<?> handler = null;
					Map<String, Object> dataMap = null;
					insert_list.clear();

					Set<String> columnNames = new HashSet<String>();
					List<ColumnDefinition> columns3 = new ArrayList<ColumnDefinition>();
					List<ColumnDefinition> columns2 = tableDefinition.getColumns();
					if (tableDefinition.getIdColumn() != null) {
						columns3.add(tableDefinition.getIdColumn());
						columnNames.add(tableDefinition.getIdColumn().getColumnName().toLowerCase());
					} else {
						for (ColumnDefinition column : columns2) {
							if (column.isPrimaryKey()) {
								columns3.add(column);
								columnNames.add(column.getColumnName().toLowerCase());
								break;
							}
						}
					}

					for (ColumnDefinition column : columns2) {
						for (String syncColumn : syncColumns) {
							if (StringUtils.equalsIgnoreCase(column.getColumnName(), syncColumn)) {
								if (!columnNames.contains(syncColumn.toLowerCase())) {
									columns3.add(column);
									columnNames.add(syncColumn.toLowerCase());
								}
							}
						}
					}

					if (!columnNames.contains("ex_databaseid_")) {
						ColumnDefinition col2 = new ColumnDefinition();
						col2.setColumnName("EX_DATABASEID_");
						col2.setJavaType("Long");
						columns3.add(col2);
					}

					tableDefinition.setColumns(columns3);

					targetConn.setAutoCommit(false);

					while (sourceRS.next()) {

						dataMap = new HashMap<String, Object>();
						for (int i = 0, n = columns.size(); i < n; i++) {
							name = columns.get(i);
							handler = typeHandlers.get(i);
							value = handler.getResult(sourceRS, name);
							if (value != null) {
								dataMap.put(name.toLowerCase(), value);
							}
						}

						buffer.delete(0, buffer.length());
						for (String syncColumn : syncColumns) {
							value = dataMap.get(syncColumn);
							if (value != null) {
								if (value instanceof Date) {
									Date date = (Date) value;
									buffer.append(date.getTime()).append("_");
								} else {
									buffer.append(value).append("_");
								}
							} else {
								buffer.append("null").append("_");
							}
						}

						if (idColumn != null) {
							index++;
							dataMap.put("ex_databaseid_", targetDatabase.getId());
							insert_list.add(dataMap);
						} else {
							String sha = DigestUtils.sha1Hex(buffer.toString()) + "_"
									+ Math.abs(buffer.toString().hashCode());
							if (!keys.contains(sha)) {
								index++;
								keys.add(sha);
								dataMap.put("shaid_", sha);
								dataMap.put("ex_databaseid_", targetDatabase.getId());
								insert_list.add(dataMap);
							}
						}
						if (index % batchSize == 0) {
							bulkInsertBean.bulkInsert(targetConn, tableDefinition, insert_list);
							targetConn.commit();
							insert_list.clear();
							logger.debug("insert data:" + index);
						}
					}

					if (insert_list.size() > 0) {
						bulkInsertBean.bulkInsert(targetConn, tableDefinition, insert_list);
						targetConn.commit();
						insert_list.clear();
						logger.debug("insert data:" + index);
					}

					JdbcUtils.close(sourceRS);
					JdbcUtils.close(sourcePsmt);

					return true;

				} else {

					List<String> sourceRowKeys = new ArrayList<String>();
					List<String> targetRowKeys = new ArrayList<String>();
					StringBuffer sqlBuffer = new StringBuffer();

					if (idColumn != null) {
						sql = " select " + idColumn.getColumnName() + " from " + tableSync.getTargetTableName();
					} else {
						sql = " select shaid_ from " + tableSync.getTargetTableName();
					}

					targetPsmt = targetConn.prepareStatement(sql);
					targetRS = targetPsmt.executeQuery();
					while (targetRS.next()) {
						keys.add(targetRS.getString(1));
					}

					JdbcUtils.close(targetRS);
					JdbcUtils.close(targetPsmt);

					while (firstResult <= totalResult) {
						sourceMD5Map.clear();
						targetMD5Map.clear();
						sourceRowKeys.clear();

						sql = " select " + selectBuffer.toString() + " from " + tableSync.getSourceTableName();
						if (StringUtils.isNotEmpty(tableSync.getSqlCriteria())) {
							sql = sql + " where 1=1 and " + tableSync.getSqlCriteria();
						}

						sqlExecutor = DBUtils.replaceSQL(sql, parameter);
						logger.debug("source query sql:" + sqlExecutor.getSql());
						sourcePsmt = sourceConn.prepareStatement(sqlExecutor.getSql() + sqlExecutor2.getSql());
						if (sqlExecutor.getParameter() != null) {
							List<Object> values = (List<Object>) sqlExecutor.getParameter();
							if (sqlExecutor2.getParameter() != null) {
								List<Object> values2 = (List<Object>) sqlExecutor2.getParameter();
								if (values2 != null) {
									values.addAll(values2);
								}
							}
							JdbcUtils.fillStatement(sourcePsmt, values);
						}
						sourceRS = sourcePsmt.executeQuery();
						// JdbcUtils.skipRows(sourceRS, firstResult);// 跳过其中的记录
						if (sourceRS.getType() != ResultSet.TYPE_FORWARD_ONLY) {
							if (firstResult != 0) {
								sourceRS.absolute(firstResult);
								logger.debug("已经定位到:" + firstResult);
							}
						} else {
							for (int i = 0; i < firstResult; i++) {
								sourceRS.next();
							}
							logger.debug("已经跳过:" + firstResult);
						}
						logger.debug("batchSize:" + batchSize);
						source_list = helper.getResults(sourceRS, batchSize);
						JdbcUtils.close(sourceRS);
						JdbcUtils.close(sourcePsmt);

						logger.debug("firstResult:" + firstResult);

						firstResult = firstResult + batchSize;

						if (source_list != null && !source_list.isEmpty()) {
							logger.debug("偏移量:" + firstResult);
							logger.debug("源表记录数:" + source_list.size());
							for (Map<String, Object> dataMap : source_list) {
								buffer.delete(0, buffer.length());
								for (String syncColumn : syncColumns) {
									value = dataMap.get(syncColumn);
									if (value != null) {
										if (value instanceof Date) {
											Date date = (Date) value;
											buffer.append(date.getTime()).append("_");
										} else {
											buffer.append(value).append("_");
										}
									} else {
										buffer.append("null").append("_");
									}
								}
								if (idColumnName != null) {
									key = ParamUtils.getString(dataMap, idColumnName);
								}
								if (StringUtils.isNotEmpty(key)) {
									sourceRowKeys.add(key);
									sourceMD5Map.put(key, DigestUtils.sha1Hex(buffer.toString()) + "_"
											+ Math.abs(buffer.toString().hashCode()));
								} else {
									String sha = DigestUtils.sha1Hex(buffer.toString()) + "_"
											+ Math.abs(buffer.toString().hashCode());
									sourceRowKeys.add(sha);
									dataMap.put("_sha_", sha);
								}
							}

							if (idColumnName != null) {// 判断目标表是否存在指定记录
								sqlBuffer.delete(0, sqlBuffer.length());
								sqlBuffer.append(" select ").append(selectBuffer.toString()).append(" from ")
										.append(tableSync.getTargetTableName()).append(" E where E.EX_DATABASEID_ = ")
										.append(targetDatabase.getId());

								if (idColumn != null) {
									if ((StringUtils.equalsIgnoreCase(idColumn.getJavaType(), "Integer")
											|| StringUtils.equalsIgnoreCase(idColumn.getJavaType(), "Long"))) {
										sqlBuffer.append(QueryUtils.getNumSQLCondition(sourceRowKeys, "E",
												idColumn.getColumnName()));
									} else {
										sqlBuffer.append(QueryUtils.getSQLCondition(sourceRowKeys, "E",
												idColumn.getColumnName()));
									}
								} else {
									sqlBuffer.append(QueryUtils.getSQLCondition(sourceRowKeys, "E", "SHAID_"));
								}

								sql = sqlBuffer.toString();
								logger.debug("target query sql:" + sql);

								sqlExecutor = DBUtils.replaceSQL(sql, parameter);

								String sqlx = sqlExecutor.getSql();
								if (sqlExecutor2.getSql() != null) {
									sqlx = sqlx + sqlExecutor2.getSql();
								}
								logger.debug("target query sqlx:" + sqlx);
								targetPsmt = targetConn.prepareStatement(sqlx);
								if (sqlExecutor.getParameter() != null) {
									List<Object> values = (List<Object>) sqlExecutor.getParameter();
									if (sqlExecutor2.getParameter() != null) {
										List<Object> values2 = (List<Object>) sqlExecutor2.getParameter();
										if (values2 != null) {
											values.addAll(values2);
										}
									}
									JdbcUtils.fillStatement(targetPsmt, values);
								}
								targetRS = targetPsmt.executeQuery();
								target_list = helper.getResults(targetRS);
								JdbcUtils.close(targetRS);
								JdbcUtils.close(targetPsmt);

								if (target_list != null && !target_list.isEmpty()) {
									logger.debug("目标表记录数：" + target_list.size());
									for (Map<String, Object> dataMap : target_list) {
										keys.add(ParamUtils.getString(dataMap, "shaid_"));
										buffer.delete(0, buffer.length());
										for (String syncColumn : syncColumns) {
											value = dataMap.get(syncColumn);
											if (value != null) {
												if (value instanceof Date) {
													Date date = (Date) value;
													buffer.append(date.getTime()).append("_");
												} else {
													buffer.append(value).append("_");
												}
											} else {
												buffer.append("null").append("_");
											}
										}
										if (idColumnName != null) {
											key = ParamUtils.getString(dataMap, idColumnName);
											targetKeys.add(key);
										}
										if (StringUtils.isNotEmpty(key)) {
											targetMD5Map.put(key, DigestUtils.sha1Hex(buffer.toString()) + "_"
													+ Math.abs(buffer.toString().hashCode()));
										} else {
											String sha = DigestUtils.sha1Hex(buffer.toString()) + "_"
													+ Math.abs(buffer.toString().hashCode());
											targetRowKeys.add(sha);
											dataMap.put("_sha_", sha);
										}
									}
								}
							}

							logger.debug("源表记录数：" + source_list.size());
							logger.debug("源表MD5记录数：" + sourceMD5Map.size());
							logger.debug("目标表MD5记录数：" + targetMD5Map.size());
							for (Map<String, Object> dataMap : source_list) {
								if (idColumnName != null) {
									key = ParamUtils.getString(dataMap, idColumnName);
								}
								if (StringUtils.isNotEmpty(key)) {
									sourceMD5 = sourceMD5Map.get(key);
									targetMD5 = targetMD5Map.get(key);
									/**
									 * 目标表记录的MD5值存在并且和源表记录的MD5相等，可以直接跳过不用做任何处理
									 */
									if (StringUtils.isNotEmpty(targetMD5) && StringUtils.equals(sourceMD5, targetMD5)) {
										continue;
									}

									if (targetKeys.contains(key) || StringUtils.isNotEmpty(targetMD5)
											&& !StringUtils.equals(sourceMD5, targetMD5)) {// 记录存在MD5不相等
										update_list.add(dataMap);
									} else {
										dataMap.put("ex_databaseid_", targetDatabase.getId());
										insert_list.add(dataMap);// 记录不存在
									}
								} else {
									String sha = (String) dataMap.get("_sha_");
									if (StringUtils.isNotEmpty(sha)) {
										if (targetRowKeys.contains(sha)) {
											// 源表和目标表的记录SHA相同,说明记录一样,跳过
											continue;
										} else {
											if (!keys.contains(sha)) {
												keys.add(sha);
												dataMap.put("shaid_", sha);
												dataMap.put("ex_databaseid_", targetDatabase.getId());
												insert_list.add(dataMap);// 记录不存在
											}
										}
									} else {
										// insert_list.add(dataMap);// 记录不存在
									}
								}
							}
						}
					}
				}
			} else {
				/**
				 * 根据主键分段导出
				 */
				for (int i = 0; i < maxForEach; i++) {

					sourceMD5Map.clear();
					targetMD5Map.clear();

					if (StringUtils.equals(idColumn.getJavaType(), "String")) {
						sql = " select " + selectBuffer.toString() + " from " + tableSync.getSourceTableName();
						if (StringUtils.isNotEmpty(tableSync.getSqlCriteria())) {
							sql = sql + " where 1=1 and " + tableSync.getSqlCriteria();
						}
					} else {
						sql = " select " + selectBuffer.toString() + " from " + tableSync.getSourceTableName()
								+ " where " + idColumn.getColumnName() + " >= " + (minIndexId + i * batchSize) + " and "
								+ idColumn.getColumnName() + " < " + (minIndexId + (i + 1) * batchSize);
						if (StringUtils.isNotEmpty(tableSync.getSqlCriteria())) {
							sql = sql + " and " + tableSync.getSqlCriteria();
						}
					}

					if (maxForEach == 1) {
						sql = " select " + selectBuffer.toString() + " from " + tableSync.getSourceTableName();
						if (StringUtils.isNotEmpty(tableSync.getSqlCriteria())) {
							sql = sql + " where 1=1 and " + tableSync.getSqlCriteria();
						}
					}

					logger.debug("query sql:" + sql);

					sqlExecutor = DBUtils.replaceSQL(sql, parameter);
					sourcePsmt = sourceConn.prepareStatement(sqlExecutor.getSql() + sqlExecutor2.getSql());
					if (sqlExecutor.getParameter() != null) {
						List<Object> values = (List<Object>) sqlExecutor.getParameter();
						if (sqlExecutor2.getParameter() != null) {
							List<Object> values2 = (List<Object>) sqlExecutor2.getParameter();
							if (values2 != null) {
								values.addAll(values2);
							}
						}
						JdbcUtils.fillStatement(sourcePsmt, values);
					}
					sourceRS = sourcePsmt.executeQuery();

					source_list = helper.getResults(sourceRS);
					JdbcUtils.close(sourceRS);
					JdbcUtils.close(sourcePsmt);

					if (source_list != null && !source_list.isEmpty()) {
						logger.debug("源表记录数：" + source_list.size());
						for (Map<String, Object> dataMap : source_list) {
							buffer.delete(0, buffer.length());
							if (idColumnName != null) {
								key = ParamUtils.getString(dataMap, idColumnName);
							}
							if (StringUtils.isNotEmpty(key)) {
								for (String syncColumn : syncColumns) {
									value = dataMap.get(syncColumn);
									if (value != null) {
										if (value instanceof Date) {
											Date date = (Date) value;
											buffer.append(date.getTime()).append("_");
										} else {
											buffer.append(value).append("_");
										}
									} else {
										buffer.append("null").append("_");
									}
								}
								sourceMD5Map.put(key, DigestUtils.sha1Hex(buffer.toString()) + "_"
										+ Math.abs(buffer.toString().hashCode()));
							}
						}

						if (StringUtils.equals(idColumn.getJavaType(), "String")) {
							sql = " select " + selectBuffer.toString() + " from " + tableSync.getTargetTableName();
						} else {
							sql = " select " + selectBuffer.toString() + " from " + tableSync.getTargetTableName()
									+ " where " + idColumn.getColumnName() + " >= " + (minIndexId + i * batchSize)
									+ " and " + idColumn.getColumnName() + " < " + (minIndexId + (i + 1) * batchSize);
						}

						if (maxForEach == 1) {
							sql = " select " + selectBuffer.toString() + " from " + tableSync.getTargetTableName()
									+ " where EX_DATABASEID_ = " + targetDatabase.getId();
						}

						logger.debug("target query sql:" + sql);

						sqlExecutor = DBUtils.replaceSQL(sql, parameter);

						targetPsmt = targetConn.prepareStatement(sqlExecutor.getSql() + sqlExecutor2.getSql());
						if (sqlExecutor.getParameter() != null) {
							List<Object> values = (List<Object>) sqlExecutor.getParameter();
							if (sqlExecutor2.getParameter() != null) {
								List<Object> values2 = (List<Object>) sqlExecutor2.getParameter();
								if (values2 != null) {
									values.addAll(values2);
								}
							}
							JdbcUtils.fillStatement(targetPsmt, values);
						}
						targetRS = targetPsmt.executeQuery();
						target_list = helper.getResults(targetRS);
						JdbcUtils.close(targetRS);
						JdbcUtils.close(targetPsmt);

						if (target_list != null && !target_list.isEmpty()) {
							logger.debug("目标表记录数：" + target_list.size());
							for (Map<String, Object> dataMap : target_list) {
								buffer.delete(0, buffer.length());
								if (idColumnName != null) {
									key = ParamUtils.getString(dataMap, idColumnName);
								}
								if (StringUtils.isNotEmpty(key)) {
									for (String syncColumn : syncColumns) {
										value = dataMap.get(syncColumn);
										if (value != null) {
											if (value instanceof Date) {
												Date date = (Date) value;
												buffer.append(date.getTime()).append("_");
											} else {
												buffer.append(value).append("_");
											}
										} else {
											buffer.append("null").append("_");
										}
									}
									targetMD5Map.put(key, DigestUtils.sha1Hex(buffer.toString()) + "_"
											+ Math.abs(buffer.toString().hashCode()));
								}
							}
						}

						logger.debug("源表记录数：" + source_list.size());
						logger.debug("源表MD5记录数：" + sourceMD5Map.size());
						logger.debug("目标表MD5记录数：" + targetMD5Map.size());

						for (Map<String, Object> dataMap : source_list) {
							if (idColumnName != null) {
								key = ParamUtils.getString(dataMap, idColumnName);
							}
							if (StringUtils.isNotEmpty(key)) {
								sourceMD5 = sourceMD5Map.get(key);
								targetMD5 = targetMD5Map.get(key);
								/**
								 * 目标表记录的MD5值存在并且和源表记录的MD5相等，可以直接跳过不用做任何处理
								 */
								if (StringUtils.isNotEmpty(targetMD5) && StringUtils.equals(sourceMD5, targetMD5)) {
									continue;
								}

								if (StringUtils.isNotEmpty(targetMD5) && !StringUtils.equals(sourceMD5, targetMD5)) {// 记录存在MD5不相等
									dataMap.put("_jobno_", jobNo);
									dataMap.put("ex_jobno_", jobNo);
									dataMap.put("ex_databaseid_", targetDatabase.getId());
									update_list.add(dataMap);
								} else {// 记录不存在
									dataMap.put("_jobno_", jobNo);
									dataMap.put("ex_jobno_", jobNo);
									dataMap.put("ex_databaseid_", targetDatabase.getId());
									insert_list.add(dataMap);
								}
							}
						}
					}
				}
			}

			logger.debug("insert list size:" + insert_list.size());
			logger.debug("update list size:" + update_list.size());

			targetConn.setAutoCommit(false);

			Set<String> columnNames = new HashSet<String>();
			List<ColumnDefinition> columns3 = new ArrayList<ColumnDefinition>();
			List<ColumnDefinition> columns = tableDefinition.getColumns();
			if (tableDefinition.getIdColumn() != null) {
				columns3.add(tableDefinition.getIdColumn());
				columnNames.add(tableDefinition.getIdColumn().getColumnName().toLowerCase());
			} else {
				for (ColumnDefinition column : columns) {
					if (column.isPrimaryKey()) {
						columns3.add(column);
						columnNames.add(column.getColumnName().toLowerCase());
						break;
					}
				}
			}

			for (ColumnDefinition column : columns) {
				for (String syncColumn : syncColumns) {
					if (StringUtils.equalsIgnoreCase(column.getColumnName(), syncColumn)) {
						if (!columnNames.contains(syncColumn.toLowerCase())) {
							columns3.add(column);
							columnNames.add(syncColumn.toLowerCase());
						}
					}
				}
			}

			if (!columnNames.contains("ex_databaseid_")) {
				ColumnDefinition col2 = new ColumnDefinition();
				col2.setColumnName("EX_DATABASEID_");
				col2.setJavaType("Long");
				columns3.add(col2);
			}

			tableDefinition.setColumns(columns3);

			if (!insert_list.isEmpty()) {
				logger.debug("insert_list size:" + insert_list.size());
				bulkInsertBean.bulkInsert(targetConn, tableDefinition, insert_list);
				insert_list.clear();
			}

			if (!update_list.isEmpty()) {
				batchUpdateBean.executeBatch(targetConn, tableDefinition, update_list);
				update_list.clear();
			}

			sourceMD5Map.clear();
			targetMD5Map.clear();

			if (StringUtils.isNotEmpty(tableSync.getSourceTableExecutionIds())) {
				TableExecutionBean tableExecutionBean = new TableExecutionBean();
				List<String> executionIds = StringTools.split(tableSync.getSourceTableExecutionIds());
				List<TableExecution> executions = tableExecutionBean.getTableExecutions(executionIds);
				if (executions != null && !executions.isEmpty()) {
					sourceConn.setAutoCommit(false);
					tableExecutionBean.execute(sourceConn, executions, parameter);
					sourceConn.commit();
				}
			}

			if (StringUtils.isNotEmpty(tableSync.getTargetTableExecutionIds())) {
				parameter.put("_jobno_", jobNo);
				parameter.put("ex_jobno_", jobNo);
				TableExecutionBean tableExecutionBean = new TableExecutionBean();
				List<String> executionIds = StringTools.split(tableSync.getTargetTableExecutionIds());
				List<TableExecution> executions = tableExecutionBean.getTableExecutions(executionIds);
				if (executions != null && !executions.isEmpty()) {
					tableExecutionBean.execute(targetConn, executions, parameter);
				}
			}

			targetConn.commit();

			JdbcUtils.close(sourceRS);
			JdbcUtils.close(sourcePsmt);
			JdbcUtils.close(sourceConn);

			JdbcUtils.close(targetRS);
			JdbcUtils.close(targetPsmt);
			JdbcUtils.close(targetConn);

			insert_list.clear();
			update_list.clear();
			sourceMD5Map.clear();
			targetMD5Map.clear();

			return true;
		} catch (Exception ex) {
			ExceptionUtils.addMsg("table_sync_" + tableSync.getId(), ex.getMessage());
			logger.error("execute sql error", ex);
			// ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			insert_list.clear();
			update_list.clear();
			sourceMD5Map.clear();
			targetMD5Map.clear();
			insert_list = null;
			update_list = null;
			source_list = null;
			target_list = null;
			sourceMD5Map = null;
			targetMD5Map = null;

			JdbcUtils.close(sourceRS);
			JdbcUtils.close(sourcePsmt);
			JdbcUtils.close(sourceConn);

			JdbcUtils.close(targetRS);
			JdbcUtils.close(targetPsmt);
			JdbcUtils.close(targetConn);
		}
	}

	public boolean getResult() {
		return result;
	}

	public void run() {
		result = this.execute(sourceDatabaseId, targetDatabaseId, tableSyncId);
	}
}
