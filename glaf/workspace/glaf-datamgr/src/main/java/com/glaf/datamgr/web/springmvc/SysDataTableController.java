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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.SysDataField;
import com.glaf.core.domain.SysDataTable;
import com.glaf.core.identity.Role;
import com.glaf.core.identity.User;
import com.glaf.core.query.SysDataTableQuery;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.ISysDataItemService;
import com.glaf.core.service.ISysDataTableService;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.core.util.ZipUtils;
import com.glaf.core.xml.XmlReader;
import com.glaf.datamgr.bean.TableMetaBean;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/system/datatable")
@RequestMapping("/system/datatable")
public class SysDataTableController {
	protected static final Log logger = LogFactory.getLog(SysDataTableController.class);

	protected ISysDataTableService sysDataTableService;

	protected ISysDataItemService sysDataItemService;

	public SysDataTableController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					SysDataTable sysDataTable = sysDataTableService.getDataTableById(String.valueOf(x));

					if (sysDataTable != null
							&& (StringUtils.equals(sysDataTable.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						sysDataTable.setDeleteFlag(1);
						sysDataTableService.saveDataTable(sysDataTable);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			SysDataTable sysDataTable = sysDataTableService.getDataTableById(String.valueOf(id));

			if (sysDataTable != null && (StringUtils.equals(sysDataTable.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				sysDataTable.setDeleteFlag(1);
				sysDataTableService.saveDataTable(sysDataTable);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		SysDataTable sysDataTable = sysDataTableService.getDataTableById(request.getParameter("id"));
		if (sysDataTable != null) {
			request.setAttribute("sysDataTable", sysDataTable);

			List<String> perms = StringTools.split(sysDataTable.getPerms());
			StringBuffer buffer = new StringBuffer();
			List<Role> roles = IdentityFactory.getRoles();

			if (roles != null && !roles.isEmpty()) {
				for (Role role : roles) {
					if (perms.contains(String.valueOf(role.getId()))) {
						buffer.append(role.getName());
						buffer.append(FileUtils.newline);
					}
				}
			}

			request.setAttribute("x_role_names", buffer.toString());
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sysDataTable.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/datatable/edit", modelMap);
	}

	@RequestMapping(value = "/importMapping", method = RequestMethod.POST)
	public ModelAndView importMapping(HttpServletRequest request, ModelMap modelMap) {
		// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			LoginContext loginContext = RequestUtils.getLoginContext(request);
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = req.getFileMap();
			XmlReader reader = new XmlReader();
			Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
			for (Entry<String, MultipartFile> entry : entrySet) {
				MultipartFile mFile = entry.getValue();
				if (mFile.getOriginalFilename() != null && mFile.getSize() > 0) {
					String filename = mFile.getOriginalFilename();
					if (filename.endsWith(".zip")) {
						ZipInputStream zipInputStream = null;
						try {
							zipInputStream = new ZipInputStream(mFile.getInputStream());
							Map<String, byte[]> zipMap = ZipUtils.getZipBytesMap(zipInputStream);
							if (zipMap != null && !zipMap.isEmpty()) {
								Set<Entry<String, byte[]>> entrySet2 = zipMap.entrySet();
								for (Entry<String, byte[]> entry2 : entrySet2) {
									String name = entry2.getKey();
									if (name.endsWith(".mapping.xml")) {
										byte[] bytes = entry2.getValue();
										SysDataTable dataTable = reader
												.getSysDataTable(new ByteArrayInputStream(bytes));
										if (sysDataTableService.getDataTableByName(dataTable.getTablename()) == null) {
											dataTable.setCreateBy(loginContext.getActorId());
											sysDataTableService.saveDataTable(dataTable);
										}
									}
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							IOUtils.closeStream(zipInputStream);
						}
					} else if (filename.endsWith(".mapping.xml")) {
						try {
							SysDataTable dataTable = reader.getSysDataTable(mFile.getInputStream());
							if (sysDataTableService.getDataTableByName(dataTable.getTablename()) == null) {
								dataTable.setCreateBy(loginContext.getActorId());
								sysDataTableService.saveDataTable(dataTable);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return this.list(request, modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysDataTableQuery query = new SysDataTableQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
		}
		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;

		int pageNo = ParamUtils.getInt(params, "page");
		limit = ParamUtils.getInt(params, "rows");
		start = (pageNo - 1) * limit;
		orderName = ParamUtils.getString(params, "sortName");
		order = ParamUtils.getString(params, "sortOrder");

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = sysDataTableService.getDataTableCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<SysDataTable> list = sysDataTableService.getDataTablesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysDataTable sysDataTable : list) {
					JSONObject rowJSON = sysDataTable.toJsonObject();
					rowJSON.put("id", sysDataTable.getId());
					rowJSON.put("rowId", sysDataTable.getId());
					rowJSON.put("datatableId", sysDataTable.getId());
					rowJSON.put("startIndex", ++start);
					if (userMap.get(sysDataTable.getCreateBy()) != null) {
						rowJSON.put("createByName", userMap.get(sysDataTable.getCreateBy()).getName());
					}
					if (userMap.get(sysDataTable.getUpdateBy()) != null) {
						rowJSON.put("updateByName", userMap.get(sysDataTable.getUpdateBy()).getName());
					}
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI + "?" + request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/sys/datatable/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("sysDataTable.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/datatable/query", modelMap);
	}

 

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SysDataTable sysDataTable = new SysDataTable();
		Tools.populate(sysDataTable, params);

		String perm = request.getParameter("perms");
		List<String> perms = StringTools.split(perm);
		StringBuffer buffer = new StringBuffer();
		List<Role> roles = IdentityFactory.getRoles();
		if (roles != null && !roles.isEmpty()) {
			for (Role role : roles) {
				if (perms.contains(String.valueOf(role.getId()))) {
					buffer.append(role.getId()).append(",");
				}
			}
		}

		sysDataTable.setPerms(buffer.toString());
		sysDataTable.setAddressPerms(request.getParameter("addressPerms"));
		sysDataTable.setAccessType(request.getParameter("accessType"));
		sysDataTable.setServiceKey(request.getParameter("serviceKey"));
		sysDataTable.setTablename(request.getParameter("tablename"));
		sysDataTable.setSortColumnName(request.getParameter("sortColumnName"));
		sysDataTable.setSortOrder(request.getParameter("sortOrder"));
		sysDataTable.setTitle(request.getParameter("title"));
		sysDataTable.setType(RequestUtils.getInt(request, "type"));
		sysDataTable.setMaxUser(RequestUtils.getInt(request, "maxUser"));
		sysDataTable.setMaxSys(RequestUtils.getInt(request, "maxSys"));
		sysDataTable.setReadUrl(request.getParameter("readUrl"));
		sysDataTable.setCreateUrl(request.getParameter("createUrl"));
		sysDataTable.setUpdateUrl(request.getParameter("updateUrl"));
		sysDataTable.setDestroyUrl(request.getParameter("destroyUrl"));
		sysDataTable.setCreateBy(actorId);
		sysDataTable.setUpdateBy(actorId);
		sysDataTable.setContent(request.getParameter("content"));
		sysDataTable.setIsSubTable(request.getParameter("isSubTable"));
		sysDataTable.setLocked(RequestUtils.getInt(request, "locked"));

		sysDataTableService.saveDataTable(sysDataTable);

		return this.list(request, modelMap);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody SysDataTable saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		SysDataTable sysDataTable = new SysDataTable();
		try {
			Tools.populate(sysDataTable, model);

			String perm = request.getParameter("perms");
			List<String> perms = StringTools.split(perm);
			StringBuffer buffer = new StringBuffer();
			List<Role> roles = IdentityFactory.getRoles();
			if (roles != null && !roles.isEmpty()) {
				for (Role role : roles) {
					if (perms.contains(String.valueOf(role.getId()))) {
						buffer.append(role.getId()).append(",");
					}
				}
			}

			sysDataTable.setPerms(buffer.toString());
			sysDataTable.setAddressPerms(request.getParameter("addressPerms"));
			sysDataTable.setAccessType(request.getParameter("accessType"));
			sysDataTable.setServiceKey(ParamUtils.getString(model, "serviceKey"));
			sysDataTable.setTablename(ParamUtils.getString(model, "tablename"));
			sysDataTable.setSortColumnName(request.getParameter("sortColumnName"));
			sysDataTable.setSortOrder(request.getParameter("sortOrder"));
			sysDataTable.setTitle(ParamUtils.getString(model, "title"));
			sysDataTable.setType(ParamUtils.getInt(model, "type"));
			sysDataTable.setMaxUser(ParamUtils.getInt(model, "maxUser"));
			sysDataTable.setMaxSys(ParamUtils.getInt(model, "maxSys"));
			sysDataTable.setReadUrl(request.getParameter("readUrl"));
			sysDataTable.setCreateUrl(request.getParameter("createUrl"));
			sysDataTable.setUpdateUrl(request.getParameter("updateUrl"));
			sysDataTable.setDestroyUrl(request.getParameter("destroyUrl"));
			sysDataTable.setCreateBy(actorId);
			sysDataTable.setUpdateBy(actorId);
			sysDataTable.setContent(ParamUtils.getString(model, "content"));
			sysDataTable.setIsSubTable(ParamUtils.getString(model, "isSubTable"));
			sysDataTable.setLocked(RequestUtils.getInt(request, "locked"));

			this.sysDataTableService.saveDataTable(sysDataTable);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return sysDataTable;
	}

	@ResponseBody
	@RequestMapping("/saveSysDataTable")
	public byte[] saveSysDataTable(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysDataTable sysDataTable = new SysDataTable();
		try {
			Tools.populate(sysDataTable, params);

			String perm = request.getParameter("perms");
			List<String> perms = StringTools.split(perm);
			StringBuffer buffer = new StringBuffer();
			List<Role> roles = IdentityFactory.getRoles();
			if (roles != null && !roles.isEmpty()) {
				for (Role role : roles) {
					if (perms.contains(String.valueOf(role.getId()))) {
						buffer.append(role.getId()).append(",");
					}
				}
			}

			sysDataTable.setPerms(buffer.toString());
			sysDataTable.setAddressPerms(request.getParameter("addressPerms"));
			sysDataTable.setAccessType(request.getParameter("accessType"));
			sysDataTable.setServiceKey(request.getParameter("serviceKey"));
			sysDataTable.setTablename(request.getParameter("tablename"));
			sysDataTable.setSortColumnName(request.getParameter("sortColumnName"));
			sysDataTable.setSortOrder(request.getParameter("sortOrder"));
			sysDataTable.setTitle(request.getParameter("title"));
			sysDataTable.setType(RequestUtils.getInt(request, "type"));
			sysDataTable.setMaxUser(RequestUtils.getInt(request, "maxUser"));
			sysDataTable.setMaxSys(RequestUtils.getInt(request, "maxSys"));
			sysDataTable.setReadUrl(request.getParameter("readUrl"));
			sysDataTable.setCreateUrl(request.getParameter("createUrl"));
			sysDataTable.setUpdateUrl(request.getParameter("updateUrl"));
			sysDataTable.setDestroyUrl(request.getParameter("destroyUrl"));
			sysDataTable.setCreateBy(actorId);
			sysDataTable.setUpdateBy(actorId);
			sysDataTable.setContent(request.getParameter("content"));
			sysDataTable.setIsSubTable(request.getParameter("isSubTable"));
			sysDataTable.setLocked(RequestUtils.getInt(request, "locked"));

			this.sysDataTableService.saveDataTable(sysDataTable);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setSysDataItemService(ISysDataItemService sysDataItemService) {
		this.sysDataItemService = sysDataItemService;
	}

	@javax.annotation.Resource
	public void setSysDataTableService(ISysDataTableService sysDataTableService) {
		this.sysDataTableService = sysDataTableService;
	}

	@RequestMapping("/showImport")
	public ModelAndView showImport(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sysDataTable.showImport");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/datatable/showImport", modelMap);
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SysDataTable sysDataTable = sysDataTableService.getDataTableById(request.getParameter("id"));

		Tools.populate(sysDataTable, params);

		String perm = request.getParameter("perms");
		List<String> perms = StringTools.split(perm);
		StringBuffer buffer = new StringBuffer();
		List<Role> roles = IdentityFactory.getRoles();
		if (roles != null && !roles.isEmpty()) {
			for (Role role : roles) {
				if (perms.contains(String.valueOf(role.getId()))) {
					buffer.append(role.getId()).append(",");
				}
			}
		}

		sysDataTable.setPerms(buffer.toString());
		sysDataTable.setAddressPerms(request.getParameter("addressPerms"));
		sysDataTable.setAccessType(request.getParameter("accessType"));
		sysDataTable.setServiceKey(request.getParameter("serviceKey"));
		sysDataTable.setTablename(request.getParameter("tablename"));
		sysDataTable.setSortColumnName(request.getParameter("sortColumnName"));
		sysDataTable.setSortOrder(request.getParameter("sortOrder"));
		sysDataTable.setTitle(request.getParameter("title"));
		sysDataTable.setType(RequestUtils.getInt(request, "type"));
		sysDataTable.setMaxUser(RequestUtils.getInt(request, "maxUser"));
		sysDataTable.setMaxSys(RequestUtils.getInt(request, "maxSys"));
		sysDataTable.setReadUrl(request.getParameter("readUrl"));
		sysDataTable.setCreateUrl(request.getParameter("createUrl"));
		sysDataTable.setUpdateUrl(request.getParameter("updateUrl"));
		sysDataTable.setDestroyUrl(request.getParameter("destroyUrl"));
		sysDataTable.setUpdateBy(user.getActorId());
		sysDataTable.setContent(request.getParameter("content"));
		sysDataTable.setIsSubTable(request.getParameter("isSubTable"));
		sysDataTable.setLocked(RequestUtils.getInt(request, "locked"));

		sysDataTableService.saveDataTable(sysDataTable);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/updateTableMeta")
	public byte[] updateTableMeta(HttpServletRequest request) {
		SysDataTable sysDataTable = sysDataTableService.getDataTableById(request.getParameter("id"));
		if (sysDataTable != null) {
			User user = RequestUtils.getUser(request);
			String actorId = user.getActorId();
			String systemName = com.glaf.core.config.Environment.DEFAULT_SYSTEM_NAME;
			String tableName = sysDataTable.getTablename();
			String tableNameCn = sysDataTable.getTitle();
			List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();
			List<SysDataField> fields = sysDataTable.getFields();
			if (fields != null && !fields.isEmpty()) {
				for (SysDataField field : fields) {
					ColumnDefinition column = new ColumnDefinition();
					column.setColumnName(field.getColumnName());
					column.setTitle(field.getTitle());
					column.setJavaType(field.getDataType());
					column.setLength(field.getLength());
					if ("Y".equals(field.getRequired())) {
						column.setRequired(true);
					} else {
						column.setRequired(false);
					}
					columns.add(column);
				}
				TableMetaBean tableMetaBean = new TableMetaBean();
				tableMetaBean.saveTableMeta(actorId, systemName, tableName, tableNameCn, columns);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		SysDataTable sysDataTable = sysDataTableService.getDataTableById(request.getParameter("id"));
		request.setAttribute("sysDataTable", sysDataTable);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("sysDataTable.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/sys/datatable/view");
	}

}
