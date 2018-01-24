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

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.entity.SqlExecutor;

import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.datamgr.domain.SqlDefinition;
import com.glaf.datamgr.domain.SqlParameter;
import com.glaf.datamgr.service.SqlDefinitionService;

public class SqlExportJsonDataBean {
	protected static final Log logger = LogFactory.getLog(SqlExportJsonDataBean.class);

	public StringBuffer doExport(long sqlDefId, long databaseId, int start, int limit, Map<String, Object> params) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		SqlDefinitionService sqlDefinitionService = ContextFactory.getBean("sqlDefinitionService");
		StringBuffer buffer = new StringBuffer();
		Database database = databaseService.getDatabaseById(databaseId);
		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		if (sqlDef != null && database != null) {
			return this.doExport(sqlDef, database, start, limit, params);
		}
		return buffer;
	}

	public StringBuffer doExport(long sqlDefId, long databaseId, Map<String, Object> params) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		SqlDefinitionService sqlDefinitionService = ContextFactory.getBean("sqlDefinitionService");
		StringBuffer buffer = new StringBuffer();
		Database database = databaseService.getDatabaseById(databaseId);
		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		if (sqlDef != null && database != null) {
			List<SqlParameter> parameters = sqlDef.getParameters();
			if (parameters != null && !parameters.isEmpty()) {
				for (SqlParameter p : parameters) {
					if (p.getName() != null && p.getType() != null && p.getMapping() != null) {
						if (StringUtils.equalsIgnoreCase(p.getType(), "String")) {
							String value = ParamUtils.getString(params, p.getName());
							if (StringUtils.endsWithIgnoreCase(p.getMapping(), "like")) {
								params.put(p.getMapping(), value + "%");
							} else if (StringUtils.endsWithIgnoreCase(p.getMapping(), "startswith")) {
								params.put(p.getMapping(), value + "%");
							} else if (StringUtils.endsWithIgnoreCase(p.getMapping(), "endswith")) {
								params.put(p.getMapping(), "%" + value);
							} else if (StringUtils.endsWithIgnoreCase(p.getMapping(), "contains")) {
								params.put(p.getMapping(), "%" + value + "%");
							} else {
								params.put(p.getMapping(), value);
							}
						} else if (StringUtils.equalsIgnoreCase(p.getType(), "Integer")) {
							Integer value = ParamUtils.getInt(params, p.getName());
							params.put(p.getMapping(), value);
						} else if (StringUtils.equalsIgnoreCase(p.getType(), "Long")) {
							Long value = ParamUtils.getLong(params, p.getName());
							params.put(p.getMapping(), value);
						} else if (StringUtils.equalsIgnoreCase(p.getType(), "Double")) {
							Double value = ParamUtils.getDouble(params, p.getName());
							params.put(p.getMapping(), value);
						} else if (StringUtils.equalsIgnoreCase(p.getType(), "Date")) {
							Date value = ParamUtils.getDate(params, p.getName());
							params.put(p.getMapping(), value);
						}
					}
				}
			}

			logger.debug("params:" + params);

			DatabaseConnectionConfig config = new DatabaseConnectionConfig();
			if (config.checkConfig(database)) {
				int total = 0;
				String systemName = Environment.getCurrentSystemName();
				QueryHelper helper = new QueryHelper();
				String sql = sqlDef.getSql();
				if(!DBUtils.isAllowedSql(sql)){
					throw new RuntimeException(" SQL statement illegal ");
				}
				try {
					if (StringUtils.isNotEmpty(sqlDef.getCountSql())) {
						logger.debug("getTotal...");
						logger.debug("count sql:" + sqlDef.getCountSql());
						SqlExecutor sqlExecutor = DBUtils.replaceSQL(sqlDef.getCountSql(), params);
						total = helper.getTotal(database.getName(), sqlExecutor);
						logger.debug("->total:" + total);
					} else {
						logger.debug("getTotalRecords...");
						logger.debug("count sql:" + sql);
						total = helper.getTotalRecords(database.getName(), sql, params);
						logger.debug("->total:" + total);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					Environment.setCurrentSystemName(systemName);
				}
				if (total > 0) {
					int start = 0;
					int limit = 20000;
					ForkJoinPool pool = new ForkJoinPool();
					for (int i = 0; i < total / limit + 1; i++) {
						logger.debug("start task " + i);
						SqlExportJsonDataTask task = new SqlExportJsonDataTask(database, sqlDef, start, limit, params);
						try {
							Future<StringBuffer> future = pool.submit(task);
							buffer.append(future.get());
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						start = start + limit;
					}
					pool.shutdown();
				}
			}
		}
		return buffer;
	}

	public StringBuffer doExport(SqlDefinition sqlDef, Database database, int start, int limit,
			Map<String, Object> params) {
		JSONArray jsonArray = new JSONArray();
		if (sqlDef != null && database != null) {
			List<SqlParameter> parameters = sqlDef.getParameters();
			if (parameters != null && !parameters.isEmpty()) {
				for (SqlParameter p : parameters) {
					if (p.getName() != null && p.getType() != null && p.getMapping() != null) {
						if (StringUtils.equalsIgnoreCase(p.getType(), "String")) {
							String value = ParamUtils.getString(params, p.getName());
							if (StringUtils.endsWithIgnoreCase(p.getMapping(), "like")) {
								params.put(p.getMapping(), value + "%");
							} else if (StringUtils.endsWithIgnoreCase(p.getMapping(), "startswith")) {
								params.put(p.getMapping(), value + "%");
							} else if (StringUtils.endsWithIgnoreCase(p.getMapping(), "endswith")) {
								params.put(p.getMapping(), "%" + value);
							} else if (StringUtils.endsWithIgnoreCase(p.getMapping(), "contains")) {
								params.put(p.getMapping(), "%" + value + "%");
							} else {
								params.put(p.getMapping(), value);
							}
						} else if (StringUtils.equalsIgnoreCase(p.getType(), "Integer")) {
							Integer value = ParamUtils.getInt(params, p.getName());
							params.put(p.getMapping(), value);
						} else if (StringUtils.equalsIgnoreCase(p.getType(), "Long")) {
							Long value = ParamUtils.getLong(params, p.getName());
							params.put(p.getMapping(), value);
						} else if (StringUtils.equalsIgnoreCase(p.getType(), "Double")) {
							Double value = ParamUtils.getDouble(params, p.getName());
							params.put(p.getMapping(), value);
						} else if (StringUtils.equalsIgnoreCase(p.getType(), "Date")) {
							Date value = ParamUtils.getDate(params, p.getName());
							params.put(p.getMapping(), value);
						}
					}
				}
			}

			logger.debug("params:" + params);

			DatabaseConnectionConfig config = new DatabaseConnectionConfig();
			if (config.checkConfig(database)) {
				QueryHelper helper = new QueryHelper();
				String sql = sqlDef.getSql();
				if(!DBUtils.isAllowedSql(sql)){
					throw new RuntimeException(" SQL statement illegal ");
				}
				try {
					int total = 0;
					if (StringUtils.isNotEmpty(sqlDef.getCountSql())) {
						logger.debug("getTotal...");
						logger.debug("count sql:" + sqlDef.getCountSql());
						SqlExecutor sqlExecutor = DBUtils.replaceSQL(sqlDef.getCountSql(), params);
						total = helper.getTotal(database.getName(), sqlExecutor);
						logger.debug("->total:" + total);
					} else {
						logger.debug("getTotalRecords...");
						logger.debug("count sql:" + sql);
						total = helper.getTotalRecords(database.getName(), sql, params);
						logger.debug("->total:" + total);
					}

					List<Map<String, Object>> rows = helper.getResultList(database.getName(), sql, params, start,
							limit);
					logger.debug("start:" + start);
					logger.debug("total:" + total);
					if (rows != null && !rows.isEmpty()) {
						Iterator<Map<String, Object>> iterator = rows.iterator();
						while (iterator.hasNext()) {
							Map<String, Object> rowMap = (Map<String, Object>) iterator.next();
							if (rowMap.isEmpty()) {
								continue;
							}
							JSONObject json = new JSONObject();
							Set<Entry<String, Object>> entrySet = rowMap.entrySet();
							for (Entry<String, Object> entry : entrySet) {
								String key = entry.getKey();
								if(StringUtils.equalsIgnoreCase(key, "__row_number__")){
									continue;
								}
								Object value = entry.getValue();
								if (value != null) {
									if (value instanceof java.util.Date) {
										java.util.Date date = (java.util.Date) value;
										String datetime = DateUtils.getDateTime(date);
										json.put(key, datetime);
									} else {
										String val = value.toString();
										json.put(key, val);
									}
								}
							}
							jsonArray.add(json);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(jsonArray.toJSONString());
		buffer.delete(0, 1);
		buffer.delete(buffer.length() - 1, buffer.length());
		return buffer;
	}

}
