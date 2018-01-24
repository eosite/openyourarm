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

package com.glaf.datamgr.web.springmvc;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.datamgr.domain.SqlDefinition;
import com.glaf.datamgr.service.SqlDefinitionService;
import com.glaf.datamgr.service.SqlResultService;

/**
 * 
 * SpringMVC控制器
 *
 */
@Controller("/datamgr/sql/execution/kendoui")
@RequestMapping("/datamgr/sql/execution/kendoui")
public class SqlExecutionKendouiController {
	protected static final Log logger = LogFactory.getLog(SqlExecutionKendouiController.class);

	protected IDatabaseService databaseService;

	protected IQueryDefinitionService queryDefinitionService;

	protected SqlDefinitionService sqlDefinitionService;

	protected SqlResultService sqlResultService;

	public SqlExecutionKendouiController() {

	}

	@RequestMapping("/datagrid")
	public ModelAndView datagrid(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();

		List<Database> databases = config.getDatabases(loginContext);

		if (databases != null && !databases.isEmpty()) {
			request.setAttribute("databases", databases);
		}

		int height = RequestUtils.getInt(request, "height", 320);
		request.setAttribute("height", height);

		int pageSize = RequestUtils.getInt(request, "rows", 10);
		request.setAttribute("rows", pageSize);

		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		if (sqlDef != null) {
			List<ColumnDefinition> columns = queryDefinitionService.getColumnDefinitions("sql_ref_" + sqlDefId);
			if (columns != null && !columns.isEmpty()) {
				int totalLenth = 0;
				for (ColumnDefinition col : columns) {
					if (col.getLength() < 60) {
						col.setLength(60);
					}
					if (StringUtils.equalsIgnoreCase(col.getType(), "datetime")) {
						if (col.getLength() < 120) {
							col.setLength(120);
						}
					}
					totalLenth = totalLenth + col.getLength();
				}
				Collections.sort(columns);
				request.setAttribute("columns", columns);
				request.setAttribute("width", totalLenth + 90);
			}
			request.setAttribute("sqlDef", sqlDef);
		}

		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		return new ModelAndView("/datamgr/sql/execution/kendoui/datagrid");
	}

	@RequestMapping("/treelist")
	public ModelAndView treelist(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();

		List<Database> databases = config.getDatabases(loginContext);

		if (databases != null && !databases.isEmpty()) {
			request.setAttribute("databases", databases);
		}

		int height = RequestUtils.getInt(request, "height", 320);
		request.setAttribute("height", height);

		int pageSize = RequestUtils.getInt(request, "rows", 10);
		request.setAttribute("rows", pageSize);

		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		if (sqlDef != null) {
			List<ColumnDefinition> columns = queryDefinitionService.getColumnDefinitions("sql_ref_" + sqlDefId);
			if (columns != null && !columns.isEmpty()) {
				int totalLenth = 0;
				for (ColumnDefinition col : columns) {
					if (col.getLength() < 60) {
						col.setLength(60);
					}
					if (StringUtils.equalsIgnoreCase(col.getType(), "datetime")) {
						if (col.getLength() < 120) {
							col.setLength(120);
						}
					}
					totalLenth = totalLenth + col.getLength();
				}
				Collections.sort(columns);
				request.setAttribute("columns", columns);
				request.setAttribute("width", totalLenth + 90);
			}
			request.setAttribute("sqlDef", sqlDef);
		}

		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		return new ModelAndView("/datamgr/sql/execution/kendoui/treelist");
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource
	public void setQueryDefinitionService(IQueryDefinitionService queryDefinitionService) {
		this.queryDefinitionService = queryDefinitionService;
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
