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

package com.glaf.isdp.web.rest;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.TablePage;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.Environment;
import com.glaf.core.domain.Database;
import com.glaf.core.query.TablePageQuery;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.isdp.bean.TableDataBean;
import com.glaf.isdp.service.DotUseService;
import com.glaf.isdp.service.TreepInfoService;

@Controller("/rs/isdp/table/data")
@Path("/rs/isdp/table/data")
public class CellTableDataResource {

	protected static final Log logger = LogFactory.getLog(CellTableDataResource.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected DotUseService dotUseService;

	protected IDatabaseService databaseService;

	protected ITablePageService tablePageService;

	protected TreepInfoService treepInfoService;

	@GET
	@POST
	@Path("/fillform")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] fillform(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@Context UriInfo uriInfo) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json; charset=UTF-8");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		JSONObject responseJSON = new JSONObject();
		TableDataBean bean = new TableDataBean();
		try {
			responseJSON = bean.getFillForm(params);
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@GET
	@POST
	@Path("/json")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] json(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@RequestBody DataRequest dataRequest) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json; charset=UTF-8");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);

		String tableName = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName)) {
			tableName = RequestUtils.decodeString(tableName);
		} else {
			tableName = request.getParameter("tableName");
		}

		if (StringUtils.startsWithIgnoreCase(tableName, "LOG_")) {
			throw new RuntimeException(tableName + " access deny");
		}

		JSONObject responseJSON = new JSONObject();

		if (!DBUtils.isAllowedTable(tableName)) {
			try {
				return responseJSON.toString().getBytes("UTF-8");
			} catch (IOException e) {
				return responseJSON.toString().getBytes();
			}
		}

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "kendoui";
		}
		int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;

		int pageNo = dataRequest.getPage();
		limit = dataRequest.getPageSize();

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		tableName = tableName.toUpperCase();

		Collection<String> rejects = new java.util.ArrayList<String>();
		rejects.add("FILEATT");
		rejects.add("USERINFO");
		rejects.add("USERROLE");
		rejects.add("ROLEUSE");
		rejects.add("ROLEUSE2");
		rejects.add("ATTACHMENT");
		rejects.add("CMS_PUBLICINFO");
		rejects.add("SYS_LOB");
		rejects.add("SYS_MAIL_FILE");
		rejects.add("SYS_DBID");
		rejects.add("SYS_PROPERTY");
		rejects.add("SYS_DATABASE");
		rejects.add("SYS_SERVER");
		rejects.add("SYS_KEY");

		if (conf.get("table.rejects") != null) {
			String str = conf.get("table.rejects");
			List<String> list = StringTools.split(str);
			for (String t : list) {
				rejects.add(t.toUpperCase());
			}
		}

		TablePage tablePage = null;
		TablePageQuery tablePageQuery = new TablePageQuery();
		tablePageQuery.tableName(tableName);
		tablePageQuery.setFirstResult(start);
		tablePageQuery.setMaxResults(limit);

		String orderName = null;
		String order = null;

		if (dataRequest.getSort() != null && !dataRequest.getSort().isEmpty()) {
			SortDescriptor sort = dataRequest.getSort().get(0);
			orderName = sort.getField();
			order = sort.getDir();
			logger.debug("orderName:" + orderName);
			logger.debug("order:" + order);
		}

		if (StringUtils.isNotEmpty(orderName)) {

		}

		if (!rejects.contains(tableName)) {
			long databaseId = RequestUtils.getLong(request, "databaseId");
			String currSystemName = Environment.getCurrentSystemName();
			try {
				Database database = databaseService.getDatabaseById(databaseId);
				if (database != null) {
					Environment.setCurrentSystemName(database.getName());
				}
				long startTs = System.currentTimeMillis();

				if (StringUtils.startsWithIgnoreCase(tableName, "cell_useradd")) {
					int index_id = RequestUtils.getInt(request, "index_id");
					tablePageQuery.setWhere(" where index_id = " + index_id);
				}
				tablePage = tablePageService.getTablePage(tablePageQuery, start, limit);
				long time = System.currentTimeMillis() - startTs;
				logger.debug("查询完成, 记录总数:" + tablePage.getTotal() + " 用时(毫秒):" + time);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			} finally {
				Environment.setCurrentSystemName(currSystemName);
			}
		}

		responseJSON.put("total", 0);
		JSONArray rowsJSON = new JSONArray();
		if (tablePage != null && tablePage.getRows() != null) {
			responseJSON.put("total", tablePage.getTotal());
			for (Map<String, Object> dataMap : tablePage.getRows()) {
				JSONObject rowJSON = new JSONObject();
				Iterator<String> iterator = dataMap.keySet().iterator();
				while (iterator.hasNext()) {
					String name = (String) iterator.next();
					if (StringUtils.equalsIgnoreCase("password", name) || StringUtils.equalsIgnoreCase("pwd", name)) {
						continue;
					}
					Object value = dataMap.get(name);
					if (value != null) {
						if (value instanceof Date) {
							Date date = (Date) value;
							rowJSON.put(name, DateUtils.getDate(date));
							rowJSON.put(name.toLowerCase(), DateUtils.getDate(date));
						} else if (value instanceof byte[]) {
							rowJSON.put(name, "二进制流");
						} else if (value instanceof java.io.InputStream) {
							rowJSON.put(name, "二进制流");
						} else if (value instanceof java.sql.Blob) {
							rowJSON.put(name, "二进制流");
						} else if (value instanceof java.sql.Clob) {
							rowJSON.put(name, "长文本");
						} else {
							rowJSON.put(name, value);
							rowJSON.put(name.toLowerCase(), value);
						}
					}
				}
				rowJSON.put("startIndex", ++start);
				rowsJSON.add(rowJSON);
				dataMap.clear();
			}
		}

		if ("yui".equals(gridType)) {
			responseJSON.put("records", rowsJSON);
		} else {
			responseJSON.put("rows", rowsJSON);
		}

		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource
	public void setDotUseService(DotUseService dotUseService) {
		this.dotUseService = dotUseService;
	}

	@javax.annotation.Resource
	public void setTablePageService(ITablePageService tablePageService) {
		this.tablePageService = tablePageService;
	}

	@javax.annotation.Resource
	public void setTreepInfoService(TreepInfoService treepInfoService) {
		this.treepInfoService = treepInfoService;
	}

	@GET
	@POST
	@Path("/wbsTables")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] wbsTables(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@Context UriInfo uriInfo) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json; charset=UTF-8");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		JSONObject responseJSON = new JSONObject();
		TableDataBean bean = new TableDataBean();
		try {
			responseJSON = bean.getWbsTables(params);
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

}
