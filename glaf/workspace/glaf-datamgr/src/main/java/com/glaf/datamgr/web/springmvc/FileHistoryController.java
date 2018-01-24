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

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.core.util.ZipUtils;

import com.glaf.datamgr.domain.FileHistory;
import com.glaf.datamgr.job.FileBackupJob;
import com.glaf.datamgr.query.FileHistoryQuery;
import com.glaf.datamgr.service.FileHistoryService;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/datamgr/fileHistory")
@RequestMapping("/datamgr/fileHistory")
public class FileHistoryController {
	protected static final Log logger = LogFactory.getLog(FileHistoryController.class);

	protected static AtomicBoolean running = new AtomicBoolean(false);

	protected FileHistoryService fileHistoryService;

	public FileHistoryController() {

	}

	@ResponseBody
	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileId = request.getParameter("fileId");
		try {
			FileHistory fileHistory = fileHistoryService.getFileHistory(fileId);
			if (fileHistory != null && fileHistory.getFileName() != null && fileHistory.getFileContent() != null) {
				if (StringUtils.contains(fileHistory.getFileName(), "jdbc")
						|| StringUtils.contains(fileHistory.getFileName(), "key")
						|| StringUtils.contains(fileHistory.getFileName(), "db")
						|| StringUtils.contains(fileHistory.getFileName(), "database")
						|| StringUtils.contains(fileHistory.getFileName(), "mail.properties")
						|| StringUtils.contains(fileHistory.getFileName(), "redis.properties")
						|| StringUtils.contains(fileHistory.getFileName(), "zookeeper.properties")
						|| StringUtils.contains(fileHistory.getFileName(), "mongodb.properties")
						|| StringUtils.contains(fileHistory.getFileName(), "resource.properties")
						|| StringUtils.contains(fileHistory.getFileName(), "config.properties")) {
					return;
				}
				ResponseUtils.download(request, response, fileHistory.getFileContent(), fileHistory.getFileName());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
	}

	@ResponseBody
	@RequestMapping("/downloadZIP")
	public void downloadZIP(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FileHistoryQuery query = new FileHistoryQuery();
		Tools.populate(query, params);

		String fileNameLike = request.getParameter("fileNameLike");
		String startDay = request.getParameter("startDay");
		String endDay = request.getParameter("endDay");
		if (StringUtils.isNotEmpty(startDay)) {
			Date dateStart = DateUtils.toDate(startDay);
			query.setLastModifiedGreaterThanOrEqual(dateStart.getTime());
		}

		if (StringUtils.isNotEmpty(endDay)) {
			Date dateEnd = DateUtils.toDate(endDay + " 23:59:59");
			query.setLastModifiedLessThanOrEqual(dateEnd.getTime());
		}

		if (StringUtils.isNotEmpty(fileNameLike)) {
			query.setFileNameLike("%" + fileNameLike + "%");
		}
		if (!running.get()) {
			long total = 0;
			Map<String, byte[]> zipMap = new HashMap<String, byte[]>();
			byte[] bytes = null;
			try {
				running.set(true);
				List<FileHistory> list = fileHistoryService.listLastest(query);
				if (list != null && !list.isEmpty()) {
					for (FileHistory fileHistory : list) {
						if (StringUtils.contains(fileHistory.getFileName(), "jdbc.properties")) {
							continue;
						}
						if (StringUtils.contains(fileHistory.getFileName(), "mail.properties")) {
							continue;
						}
						if (StringUtils.contains(fileHistory.getFileName(), "mongodb.properties")) {
							continue;
						}
						if (StringUtils.contains(fileHistory.getFileName(), "config.properties")) {
							continue;
						}
						if (StringUtils.contains(fileHistory.getFileName(), "resource.properties")) {
							continue;
						}
						if (StringUtils.contains(fileHistory.getFileName(), "redis.properties")) {
							continue;
						}
						if (StringUtils.contains(fileHistory.getFileName(), "zookeeper.properties")) {
							continue;
						}
						if (StringUtils.contains(fileHistory.getFileName(), "resource.properties")) {
							continue;
						}
						if (StringUtils.equals(fileHistory.getFileName(), "key")) {
							continue;
						}
						if (fileHistory.getFileSize() > FileUtils.MB_SIZE * 20) {// 20MB
							continue;
						}
						if (total < FileUtils.MB_SIZE * 200) {// 200MB
							FileHistory file = fileHistoryService.getFileHistory(fileHistory.getFileId());
							if (file != null && file.getFileContent() != null) {
								total += file.getFileSize();
								if (file.getPath().startsWith("/")) {
									zipMap.put(file.getPath().substring(1, file.getPath().length()),
											file.getFileContent());
								} else {
									zipMap.put(file.getPath(), file.getFileContent());
								}
							}
						}
					}
					if (!zipMap.isEmpty()) {
						bytes = ZipUtils.toZipBytes(zipMap);
						ResponseUtils.download(request, response, bytes, DateUtils.getNowYearMonthDayHHmmss() + ".zip");
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			} finally {
				running.set(false);
				zipMap.clear();
				zipMap = null;
				bytes = null;
			}
		}
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FileHistoryQuery query = new FileHistoryQuery();
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
		int total = fileHistoryService.getFileHistoryCountByQueryCriteria(query);
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

			List<FileHistory> list = fileHistoryService.getFileHistoriesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FileHistory fileHistory : list) {
					JSONObject rowJSON = fileHistory.toJsonObject();
					rowJSON.put("id", fileHistory.getFileId());
					rowJSON.put("rowId", fileHistory.getFileId());
					rowJSON.put("fileHistoryId", fileHistory.getFileId());
					rowJSON.put("startIndex", ++start);
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

		return new ModelAndView("/datamgr/fileHistory/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("fileHistory.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/fileHistory/query", modelMap);
	}



	@ResponseBody
	@RequestMapping("/reload")
	public byte[] reload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			FileBackupJob job = new FileBackupJob();
			job.doProcess();
			return ResponseUtils.responseResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseResult(false);
	}

	@javax.annotation.Resource
	public void setFileHistoryService(FileHistoryService fileHistoryService) {
		this.fileHistoryService = fileHistoryService;
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		FileHistory fileHistory = fileHistoryService.getFileHistory(request.getParameter("fileId"));
		request.setAttribute("fileHistory", fileHistory);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("fileHistory.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/datamgr/fileHistory/view");
	}

}
