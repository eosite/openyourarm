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

package com.glaf.base.modules.file.web.springmvc;

import java.io.IOException;

import java.util.*;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.identity.*;
import com.glaf.core.mongodb.MongodbFileManager;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.base.modules.file.domain.*;
import com.glaf.base.modules.file.query.*;
import com.glaf.base.modules.file.service.*;
import com.glaf.base.modules.file.util.*;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.service.SysTreeService;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/file/attachment")
@RequestMapping("/file/attachment")
public class FileAttachmentController {
	protected static final Log logger = LogFactory.getLog(FileAttachmentController.class);

	private static Configuration conf = BaseConfiguration.create();

	protected FileAttachmentService fileAttachmentService;

	protected SysTreeService sysTreeService;

	public FileAttachmentController() {

	}

	@RequestMapping("/chooseFile")
	public ModelAndView chooseFile(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		LogUtils.debug("params=" + RequestUtils.getParameterMap(request));
		String x_view = ViewProperties.getString("fileAttachment.chooseFile");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/attachment/chooseFile", modelMap);
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
					FileAttachment fileAttachment = fileAttachmentService.getFileAttachment(String.valueOf(x));
					if (fileAttachment != null
							&& (StringUtils.equals(fileAttachment.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						if (fileAttachment.getPath() != null) {
							String rootDir = SystemProperties.getConfigRootPath();
							String filePath = rootDir + fileAttachment.getPath();
							FileUtils.deleteFile(filePath);
						}
						fileAttachmentService.deleteById(fileAttachment.getId());
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FileAttachment fileAttachment = fileAttachmentService.getFileAttachment(String.valueOf(id));
			if (fileAttachment != null && (StringUtils.equals(fileAttachment.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				if (fileAttachment.getPath() != null) {
					String rootDir = SystemProperties.getConfigRootPath();
					String filePath = rootDir + fileAttachment.getPath();
					FileUtils.deleteFile(filePath);
				}
				fileAttachmentService.deleteById(fileAttachment.getId());
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		FileAttachment fileAttachment = fileAttachmentService.getFileAttachment(request.getParameter("id"));
		if (fileAttachment != null) {
			request.setAttribute("fileAttachment", fileAttachment);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("fileAttachment.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/attachment/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FileAttachmentQuery query = new FileAttachmentQuery();
		Tools.populate(query, params);
		query.type(request.getParameter("type"));
		if (RequestUtils.getLong(request, "nodeId") > 0) {
			query.setNodeId(RequestUtils.getLong(request, "nodeId"));
		}
		query.setNameLike(request.getParameter("nameLike"));
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
		int total = fileAttachmentService.getFileAttachmentCountByQueryCriteria(query);
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

			List<FileAttachment> list = fileAttachmentService.getFileAttachmentsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FileAttachment fileAttachment : list) {
					JSONObject rowJSON = fileAttachment.toJsonObject();
					rowJSON.put("id", fileAttachment.getId());
					rowJSON.put("rowId", fileAttachment.getId());
					rowJSON.put("fileAttachmentId", fileAttachment.getId());
					rowJSON.put("startIndex", ++start);
					rowJSON.put("path",
							request.getContextPath() + "/mx/lob/lob/download?fileId=" + fileAttachment.getId());
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		logger.debug(result.toJSONString());
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

		return new ModelAndView("/modules/attachment/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("fileAttachment.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/attachment/query", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FileAttachmentQuery query = new FileAttachmentQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FileAttachmentDomainFactory.processDataRequest(dataRequest);

		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
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

		JSONObject result = new JSONObject();
		int total = fileAttachmentService.getFileAttachmentCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

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
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			List<FileAttachment> list = fileAttachmentService.getFileAttachmentsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FileAttachment fileAttachment : list) {
					JSONObject rowJSON = fileAttachment.toJsonObject();
					rowJSON.put("id", fileAttachment.getId());
					rowJSON.put("fileAttachmentId", fileAttachment.getId());
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

	@javax.annotation.Resource
	public void setFileAttachmentService(FileAttachmentService fileAttachmentService) {
		this.fileAttachmentService = fileAttachmentService;
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

	@RequestMapping("/showUpload")
	public ModelAndView showUpload(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		return new ModelAndView("/modules/attachment/showUpload", modelMap);
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		FileAttachment fileAttachment = fileAttachmentService.getFileAttachment(request.getParameter("id"));
		if (fileAttachment != null) {
			Tools.populate(fileAttachment, params);

			fileAttachment.setName(request.getParameter("name"));
			fileAttachment.setDesc(request.getParameter("desc"));
			fileAttachment.setLocked(RequestUtils.getInt(request, "locked"));
			fileAttachment.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
			fileAttachment.setUpdateBy(user.getActorId());
			fileAttachmentService.save(fileAttachment);
		}

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/upload")
	public ModelAndView upload(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String responseType = request.getParameter("responseType");
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			Map<String, Object> params = RequestUtils.getParameterMap(req);

			String type = req.getParameter("type");
			if (StringUtils.isEmpty(type)) {
				type = "0";
			}

			long nodeId = RequestUtils.getLong(request, "nodeId");

			int maxUploadSize = conf.getInt(type + ".maxUploadSize", 0);
			if (maxUploadSize == 0) {
				maxUploadSize = conf.getInt("upload.maxUploadSize", 50);// 50MB
			}

			maxUploadSize = maxUploadSize * FileUtils.MB_SIZE;

			SysTree tree = sysTreeService.getSysTreeByIdWithAncestor(nodeId);
			if (tree != null) {
				if (tree.getAllowedFizeSize() > 0) {
					maxUploadSize = tree.getAllowedFizeSize() * FileUtils.MB_SIZE;
				} else {
					SysTree partent = tree.getParentTree();
					if (partent != null) {
						if (partent.getAllowedFizeSize() > 0) {
							maxUploadSize = partent.getAllowedFizeSize() * FileUtils.MB_SIZE;
						} else {
							SysTree ancestor = partent.getParentTree();
							if (ancestor != null) {
								if (ancestor.getAllowedFizeSize() > 0) {
									maxUploadSize = ancestor.getAllowedFizeSize() * FileUtils.MB_SIZE;
								}
							}
						}
					}
				}
			}

			/**
			 * 文件大小超过maxDiskSize时将文件写到本地硬盘,默认超过20MB的将写到本地硬盘
			 */
			int maxDiskSize = conf.getInt("upload.maxDiskSize", 0);
			if (maxDiskSize == 0) {
				maxDiskSize = conf.getInt("maxDiskSize", 1024 * 1024 * 20);// 20MB
			}

			logger.debug("maxUploadSize:" + maxUploadSize);
			logger.debug("maxDiskSize:" + maxDiskSize);
			MongodbFileManager fileManager = new MongodbFileManager();
			try {
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

						FileAttachment fileAttachment = new FileAttachment();
						Tools.populate(fileAttachment, params);
						fileAttachment.setNodeId(nodeId);
						fileAttachment.setCreateBy(loginContext.getActorId());
						fileAttachment.setFilename(mFile.getOriginalFilename());
						fileAttachment.setOriginalFilename(mFile.getOriginalFilename());
						fileAttachment.setName(req.getParameter("name"));
						fileAttachment.setDesc(req.getParameter("desc"));
						fileAttachment.setType(type);
						fileAttachment.setSize((int) mFile.getSize());

						if (mFile.getSize() <= maxDiskSize) {
							fileAttachment.setPath(null);
							fileAttachment.setBytes(mFile.getBytes());
						} else {
							fileAttachment.setBytes(null);
						}

						fileAttachmentService.save(fileAttachment);

						if (mFile.getSize() > maxDiskSize) {
							BlobItem dataFile = new BlobItemEntity();
							dataFile.setLastModified(System.currentTimeMillis());
							dataFile.setCreateBy(loginContext.getActorId());
							dataFile.setFileId(fileId);
							dataFile.setFilename(mFile.getOriginalFilename());
							dataFile.setName(mFile.getOriginalFilename());
							dataFile.setContentType(mFile.getContentType());
							dataFile.setType(type);
							dataFile.setStatus(0);
							dataFile.setServiceKey("fileAttachment");
							dataFile.setData(FileUtils.getBytes(mFile.getInputStream()));
							fileManager.save(dataFile);
						}

						fileAttachment.setBytes(null);

						if (StringUtils.equalsIgnoreCase(responseType, "json")) {
							response.setContentType("text/plain;charset=UTF-8");
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
							return null;
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		FileAttachment fileAttachment = fileAttachmentService.getFileAttachment(request.getParameter("id"));
		request.setAttribute("fileAttachment", fileAttachment);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("fileAttachment.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/attachment/view");
	}

}
