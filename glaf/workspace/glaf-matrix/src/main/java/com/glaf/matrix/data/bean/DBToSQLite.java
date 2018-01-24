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
package com.glaf.matrix.data.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.FileUtils;
import com.zaxxer.hikari.HikariDataSource;

public class DBToSQLite {

	protected static final Log logger = LogFactory.getLog(DBToSQLite.class);

	protected static AtomicBoolean running = new AtomicBoolean(false);

	public void export(String systemName, List<String> tables, String sqliteDB) {
		List<ColumnDefinition> columns = null;
		TableToSQLite tableSqlDB = new TableToSQLite();
		// List<String> tables = new ArrayList<String>();
		// tables.add("SYS_KEY");
		// tables.clear();
		// tables.add("SYS_KEY");
		// tables.add("SYS_LOB");
		// tables.add("R_GLAF18049_TB29");
		Map<String, String> tableErrMap = new HashMap<String, String>();
		SQLiteHelper sqliteHelper = new SQLiteHelper();
		HikariDataSource targetDataSource = null;
		if (!running.get()) {
			try {
				running.set(true);
				targetDataSource = sqliteHelper.getDataSource(sqliteDB);
				List<String> errorTables = new ArrayList<String>();
				for (String tableName : tables) {
					if (StringUtils.equalsIgnoreCase(tableName, "sys_scheduler_execution")
							|| StringUtils.equalsIgnoreCase(tableName, "form_page_history")
							|| StringUtils.equalsIgnoreCase(tableName, "form_rule_audit")
							|| StringUtils.equalsIgnoreCase(tableName, "form_attachment")
							|| StringUtils.startsWithIgnoreCase(tableName, "temp_")
							|| StringUtils.startsWithIgnoreCase(tableName, "tmp_")
							|| StringUtils.startsWithIgnoreCase(tableName, "log_")
							|| StringUtils.startsWithIgnoreCase(tableName, "treetable_")) {
						continue;
					}
					if (StringUtils.endsWithIgnoreCase(tableName, "_log")) {
						continue;
					}
					if (DBUtils.isTemoraryTable(tableName)) {
						continue;
					}
					try {
						if (DBUtils.getTableCount(systemName, tableName) == 0) {
							continue;
						}
						columns = DBUtils.getColumnDefinitions(systemName, tableName);
						if (columns != null && !columns.isEmpty()) {
							int batchSize = 1000;
							for (ColumnDefinition column : columns) {
								if (StringUtils.equals(column.getJavaType(), "Blob")) {
									batchSize = 10;
									break;
								}
							}
							tableSqlDB.execute(systemName, targetDataSource, tableName, batchSize);
						}
					} catch (Exception ex) {
						errorTables.add(tableName);
						tableErrMap.put(tableName, ex.getMessage());
						logger.error(ex);
					}
				}
				if (errorTables.size() > 0) {
					StringBuilder buff = new StringBuilder();
					for (String t : errorTables) {
						logger.error(t + "-->" + tableErrMap.get(t));
						buff.append(t).append(FileUtils.newline);
					}
					String filename = SystemProperties.getConfigRootPath() + "/db/" + sqliteDB + "_export_error.log";
					FileUtils.save(filename, buff.toString().getBytes());
				} else {
					logger.info("Data Export OK!");
					logger.info("数据导出成功！");
				}
			} finally {
				running.set(false);
				if (targetDataSource != null) {
					targetDataSource.close();
					targetDataSource = null;
				}
			}
		}
	}

	public void exportAll(String systemName, String sqliteDB) {
		List<String> tables = DBUtils.getTables(systemName);
		this.export(systemName, tables, sqliteDB);
	}

}
