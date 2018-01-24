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
import java.io.PrintWriter;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.*;
import com.glaf.core.base.BlobItem;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.mongodb.MongodbFileManager;
import com.glaf.core.security.*;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.*;

import com.glaf.datamgr.bean.UpdatePackageBean;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/update/pkg")
@RequestMapping("/sys/update/pkg")
public class UpdatePackageController {
	protected static final Log logger = LogFactory.getLog(UpdatePackageController.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected static AtomicBoolean running = new AtomicBoolean(false);

	protected IBlobService blobService;

	protected FileHistoryService fileHistoryService;

	public UpdatePackageController() {

	}

	@ResponseBody
	@RequestMapping("/downloadBackup")
	public void downloadBackup(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileId = request.getParameter("fileId");
		try {
			FileHistory fileHistory = fileHistoryService.getBackupFileHistory(fileId);
			if (fileHistory != null && fileHistory.getFileName() != null && fileHistory.getFileContent() != null) {
				ResponseUtils.download(request, response, fileHistory.getFileContent(), fileHistory.getFileName());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
	}

	@ResponseBody
	@RequestMapping("/downloadUpdatePkg")
	public void downloadUpdatePkg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileId = request.getParameter("fileId");
		try {
			FileHistory fileHistory = fileHistoryService.getUpdatePkgFileHistory(fileId);
			if (fileHistory != null && fileHistory.getFileName() != null && fileHistory.getFileContent() != null) {
				ResponseUtils.download(request, response, fileHistory.getFileContent(), fileHistory.getFileName());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
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
		query.type("pkg");
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

		return new ModelAndView("/modules/sys/package/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("package.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/package/query", modelMap);
	}

	@javax.annotation.Resource
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

	@javax.annotation.Resource
	public void setFileHistoryService(FileHistoryService fileHistoryService) {
		this.fileHistoryService = fileHistoryService;
	}

	@RequestMapping("/showUpload")
	public ModelAndView showUpload(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("package.showUpload");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/sys/package/showUpload");
	}

	@RequestMapping("/updatePkg")
	@ResponseBody
	public byte[] updatePkg(HttpServletRequest request, ModelMap modelMap) throws IOException {
		UpdatePackageBean bean = new UpdatePackageBean();
		boolean result = bean.updatePkg();
		return ResponseUtils.responseResult(result);
	}

	@ResponseBody
	@RequestMapping("/upload")
	public ModelAndView upload(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		response.setContentType("text/plain;charset=UTF-8");
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String serviceKey = request.getParameter("serviceKey");
		String responseType = request.getParameter("responseType");
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;

			int maxUploadSize = conf.getInt(serviceKey + ".maxUploadSize", 0);
			if (maxUploadSize == 0) {
				maxUploadSize = conf.getInt("upload.maxUploadSize", 50);// 50MB
			}
			maxUploadSize = maxUploadSize * FileUtils.MB_SIZE;

			/**
			 * 文件大小超过maxDiskSize时将文件写到本地硬盘,默认超过5MB的将写到本地硬盘
			 */
			int maxDiskSize = conf.getInt(serviceKey + ".maxDiskSize", 0);
			if (maxDiskSize == 0) {
				maxDiskSize = conf.getInt("upload.maxDiskSize", 1024 * 1024 * 2);// 2MB
			}

			logger.debug("maxUploadSize:" + maxUploadSize);
			MongodbFileManager fileManager = new MongodbFileManager();
			try {
				PrintWriter out = response.getWriter();
				Map<String, MultipartFile> fileMap = req.getFileMap();
				Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
				for (Entry<String, MultipartFile> entry : entrySet) {
					MultipartFile mFile = entry.getValue();
					logger.debug("fize size:" + mFile.getSize());
					if (mFile.getOriginalFilename() != null && mFile.getSize() > 0 && mFile.getSize() < maxUploadSize) {
						String filename = mFile.getOriginalFilename();
						logger.debug("upload file:" + filename);
						logger.debug("fize size:" + mFile.getSize());
						String fileId = UUID32.getUUID();

						BlobItem dataFile = new BlobItemEntity();
						dataFile.setLastModified(System.currentTimeMillis());
						dataFile.setCreateBy(loginContext.getActorId());
						dataFile.setFileId(fileId);
						dataFile.setFilename(mFile.getOriginalFilename());
						dataFile.setName(mFile.getOriginalFilename());
						dataFile.setContentType(mFile.getContentType());
						dataFile.setType("pkg");
						dataFile.setStatus(0);
						dataFile.setServiceKey(serviceKey);

						if (mFile.getSize() <= maxDiskSize) {
							dataFile.setPath(null);
							dataFile.setData(mFile.getBytes());
						} else {
							dataFile.setData(null);
						}
						blobService.insertBlob(dataFile);

						FileHistory fileHistory = new FileHistory();
						fileHistory.setFileId(fileId);
						fileHistory.setCreateBy(loginContext.getActorId());
						fileHistory.setCreateTime(new Date());
						fileHistory.setFileContent(mFile.getBytes());
						fileHistory.setFileName(mFile.getOriginalFilename());
						fileHistory.setFileSize((int) mFile.getSize());
						fileHistory.setLastModified(System.currentTimeMillis());
						fileHistory.setMd5(DigestUtils.md5Hex(mFile.getBytes()));
						fileHistory.setPath(dataFile.getPath());
						fileHistory.setType("pkg");
						fileHistory.setVersion(1);
						fileHistory.setDeleteFlag(0);
						fileHistory.setPkgStatus("NEW");
						fileHistoryService.saveUpdatePackage(fileHistory);

						if (mFile.getSize() > maxDiskSize) {
							dataFile.setData(FileUtils.getBytes(mFile.getInputStream()));
							fileManager.save(dataFile);
						}

						if (StringUtils.equalsIgnoreCase(responseType, "json")) {
							StringBuilder json = new StringBuilder();
							json.append("{");
							json.append("'");
							json.append("fileId");
							json.append("':'");
							json.append(fileId);
							json.append("'");
							Enumeration<String> pNames = request.getParameterNames();
							String pName;
							while (pNames.hasMoreElements()) {
								json.append(",");
								pName = (String) pNames.nextElement();
								json.append("'");
								json.append(pName);
								json.append("':'");
								json.append(request.getParameter(pName));
								json.append("'");
							}
							json.append("}");
							logger.debug(json.toString());
							response.getWriter().write(json.toString());
						} else {
							out.print(fileId);
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return new ModelAndView("/modules/sys/package/go");
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

		String x_view = ViewProperties.getString("package.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/sys/package/view");
	}

}
