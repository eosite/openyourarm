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

package com.glaf.datamgr.website.springmvc;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.Environment;
import com.glaf.core.domain.Database;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.datamgr.domain.SqlDefinition;
import com.glaf.datamgr.domain.SqlParameter;
import com.glaf.datamgr.service.SqlDefinitionService;
import com.glaf.datamgr.service.SqlResultService;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/datamgr/data/service")
@RequestMapping("/datamgr/data/service")
public class PublicExecutionController {
	protected static final Log logger = LogFactory.getLog(PublicExecutionController.class);

	protected IDatabaseService databaseService;

	protected SqlDefinitionService sqlDefinitionService;

	protected SqlResultService sqlResultService;

	public PublicExecutionController() {

	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		String gridType = request.getParameter("gridType");
		JSONObject result = new JSONObject();
		int start = 0;
		int limit = 10;

		int pageNo = ParamUtils.getInt(paramMap, "page");
		limit = ParamUtils.getInt(paramMap, "rows");
		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		long databaseId = RequestUtils.getLong(request, "databaseId");
		Database database = databaseService.getDatabaseById(databaseId);
		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.putAll(paramMap);

		if (sqlDef != null && database != null && StringUtils.equals(sqlDef.getPublicFlag(), "Y")) {
			params.put("rowKey", request.getParameter("rowKey"));
			if (sqlDef.getParentId() != null) {
				String jsonParam = request.getParameter("jsonParam");
				if (StringUtils.isNotEmpty(jsonParam)) {
					jsonParam = new String(Base64.decodeBase64(jsonParam), "GBK");
					logger.debug("jsonParam:" + jsonParam);
					JSONObject json = JSON.parseObject(jsonParam);
					Iterator<Entry<String, Object>> iterator = json.entrySet().iterator();
					while (iterator.hasNext()) {
						Entry<String, Object> entry = iterator.next();
						String key = (String) entry.getKey();
						Object value = entry.getValue();
						if (value != null) {
							params.put(key, value);
						}
					}
				}
			}

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
				String systemName = database.getName();
				String sql = sqlDef.getSql();
				try {
					com.glaf.core.config.Environment.setCurrentSystemName(systemName);

					sql = QueryUtils.replaceDollarSQLParas(sql, params);

					int total = helper.getTotalRecords(systemName, sql, params);

					List<Map<String, Object>> rows = null;
					if (StringUtils.contains(sql.toLowerCase(), " count(*) ")) {
						logger.debug("query count...");
						rows = helper.getResultList(Environment.getCurrentSystemName(), sql, params);
					} else {
						if (total > 0 && start < total) {
							logger.debug("->begin:" + start);
							rows = helper.getResultList(Environment.getCurrentSystemName(), sql, params, start, limit);
						}
					}

					logger.debug("start:" + start);
					logger.debug("total:" + total);

					if (rows != null && !rows.isEmpty()) {
						logger.debug("rows:" + rows.size());
						int index = 1;
						JSONArray array = new JSONArray();
						Iterator<Map<String, Object>> iterator = rows.iterator();
						while (iterator.hasNext()) {
							Map<String, Object> rowMap = (Map<String, Object>) iterator.next();
							rowMap.put("startIndex", start + index++);
							rowMap.remove("password");
							rowMap.remove("PASSWORD");
							rowMap.remove("password_");
							rowMap.remove("PASSWORD_");
							rowMap.remove("key");
							rowMap.remove("KEY_");
							rowMap.remove("sPassword");
							array.add(JsonUtils.toJSONObject(rowMap));
						}
						result.put("rows", array);
					}

					result.put("start", start);
					result.put("startIndex", start);
					result.put("limit", limit);
					result.put("pageSize", limit);

					if (StringUtils.equalsIgnoreCase(gridType, "jqgrid")) {
						result.put("records", total);
						result.put("total", total / limit + 1);
					} else {
						result.put("total", total);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					com.glaf.core.config.Environment.removeCurrentSystemName();
				}
			}
		}
		// logger.debug(result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource
	public void setSqlDefinitionService(SqlDefinitionService sqlDefinitionService) {
		this.sqlDefinitionService = sqlDefinitionService;
	}

	@javax.annotation.Resource
	public void setSqlResultService(SqlResultService sqlResultService) {
		this.sqlResultService = sqlResultService;
	}

}
