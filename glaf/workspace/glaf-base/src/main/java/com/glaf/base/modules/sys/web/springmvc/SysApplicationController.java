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

package com.glaf.base.modules.sys.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipInputStream;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.query.SysApplicationQuery;
import com.glaf.base.modules.sys.query.SysTreeQuery;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.util.SysTreeJsonFactory;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.config.ViewProperties;
import com.glaf.base.res.MessageUtils;
import com.glaf.base.res.ViewMessage;
import com.glaf.base.res.ViewMessages;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.core.util.ZipUtils;

@Controller("/sys/application")
@RequestMapping("/sys/application")
public class SysApplicationController {
	private static final Log logger = LogFactory.getLog(SysApplicationController.class);

	private SysApplicationService sysApplicationService;

	private SysTreeService sysTreeService;

	/**
	 * 批量删除信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/batchDelete")
	public ModelAndView batchDelete(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		boolean ret = false;
		long[] ids = ParamUtil.getLongParameterValues(request, "id");
		// ret = sysApplicationService.deleteAll(ids);

		if (ids != null && ids.length > 0) {
			try {
				sysApplicationService.markDeleteFlag(ids, 1);
				ret = true;
			} catch (Exception ex) {
				logger.error("batch delete application error.", ex);
			}
		}
		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("application.delete_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("application.delete_failure"));
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("/show_msg2", modelMap);
	}

	@ResponseBody
	@RequestMapping("/exportJson")
	public void exportJson(HttpServletRequest request, HttpServletResponse response) {
		int parentId = ParamUtil.getIntParameter(request, "parentId", 0);
		if (parentId > 0) {
			try {
				SysTree parent = sysTreeService.findById(parentId);
				if (parent.getId() == 3) {
					List<SysTree> treeList = sysTreeService.getSysTreeList(parentId);
					Map<String, byte[]> zipMap = new HashMap<String, byte[]>();
					for (SysTree tree : treeList) {
						JSONObject jsonObject = toJSONObject(tree);
						zipMap.put(tree.getName() + ".json", jsonObject.toJSONString().getBytes("UTF-8"));
					}
					byte[] zipBytes = ZipUtils.toZipBytes(zipMap);
					ResponseUtils.download(request, response, zipBytes, "tree.zip");
				} else {
					JSONObject jsonObject = toJSONObject(parent);
					ResponseUtils.download(request, response, jsonObject.toJSONString().getBytes("UTF-8"),
							parent.getName() + ".json");
				}
			} catch (Exception ex) {
			}
		}
	}

	@RequestMapping("/exportMenus")
	public void exportMenus(HttpServletRequest request, HttpServletResponse response) {
		String objectIds = request.getParameter("nodeIds");
		List<Long> nodeIds = StringTools.splitToLong(objectIds);
		if (nodeIds != null && !nodeIds.isEmpty()) {
			List<SysApplication> apps = sysApplicationService.getApplicationList();
			if (apps != null && !apps.isEmpty()) {
				StringBuilder buffer = new StringBuilder();

				for (SysApplication app : apps) {
					if (nodeIds.contains(app.getNodeId())) {
						buffer.append(
								"insert into sys_application (ID, CODE, APPDESC, locked, NAME, NODEID, SHOWMENU, SORT, IMAGEPATH, DATABASEID_, LINKFILEID, LINKFILENAME, LINKPARAM, LINKTYPE, REFID1, REFID2, REFID3, REFID4, REFID5, REFNAME1, REFNAME2, REFNAME3, REFNAME4, REFNAME5, UID_, FLOWID_, CELLTREEDOTINDEX_, POSITION_, SYSTEMFLAG_, PAGEID_, URL) values ( ");
						buffer.append(app.getId()).append(", ");
						if (StringUtils.isNotEmpty(app.getCode())) {
							String text = app.getCode();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(app.getDesc())) {
							String text = app.getDesc();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						buffer.append(app.getLocked()).append(", ");

						if (StringUtils.isNotEmpty(app.getName())) {
							String text = app.getName();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						buffer.append(app.getNodeId()).append(", ");

						buffer.append(app.getShowMenu()).append(", ");

						buffer.append(app.getSort()).append(", ");

						if (StringUtils.isNotEmpty(app.getImagePath())) {
							String text = app.getImagePath();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (app.getDatabaseId() != null) {
							buffer.append(app.getDatabaseId()).append(", ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(app.getLinkFileId())) {
							String text = app.getLinkFileId();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(app.getLinkFileName())) {
							String text = app.getLinkFileName();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(app.getLinkParam())) {
							String text = app.getLinkParam();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(app.getLinkType())) {
							String text = app.getLinkType();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (app.getRefId1() != null) {
							buffer.append(app.getRefId1()).append(", ");
						} else {
							buffer.append("null, ");
						}

						if (app.getRefId2() != null) {
							buffer.append(app.getRefId2()).append(", ");
						} else {
							buffer.append("null, ");
						}

						if (app.getRefId3() != null) {
							buffer.append(app.getRefId3()).append(", ");
						} else {
							buffer.append("null, ");
						}

						if (app.getRefId4() != null) {
							buffer.append(app.getRefId4()).append(", ");
						} else {
							buffer.append("null, ");
						}

						if (app.getRefId5() != null) {
							buffer.append(app.getRefId5()).append(", ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(app.getRefName1())) {
							String text = app.getRefName1();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(app.getRefName2())) {
							String text = app.getRefName2();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(app.getRefName3())) {
							String text = app.getRefName3();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(app.getRefName4())) {
							String text = app.getRefName4();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(app.getRefName5())) {
							String text = app.getRefName5();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(app.getUid())) {
							String text = app.getUid();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(app.getFlowid())) {
							String text = app.getFlowid();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(app.getCellTreedotIndex())) {
							String text = app.getCellTreedotIndex();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(app.getPosition())) {
							String text = app.getPosition();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(app.getSystemFlag())) {
							String text = app.getSystemFlag();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(app.getPageId())) {
							String text = app.getPageId();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(app.getUrl())) {
							String text = app.getUrl();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("' ");
						} else {
							buffer.append("null ");
						}
						buffer.append(");");
						buffer.append(FileUtils.newline);
					}
				}

				buffer.append(FileUtils.newline);
				buffer.append(FileUtils.newline);
				buffer.append(FileUtils.newline);

				SysTreeQuery query = new SysTreeQuery();
				query.setDeleteFlag(0);
				query.setOrderBy(" E.ID asc ");
				List<SysTree> trees = sysTreeService.getApplicationSysTrees(query);
				if (trees != null && !trees.isEmpty()) {
					for (SysTree tree : trees) {
						if (nodeIds.contains(tree.getId())) {
							buffer.append(
									"insert into sys_tree (id, parent, name, nodedesc, sort, code, discriminator, treeid, moveable, icon, iconCls, url, ALLOWEDFILEEXTS, ALLOWEDFIZESIZE, PROVIDERCLASS) values (");
							buffer.append(tree.getId()).append(",");
							buffer.append(tree.getParentId()).append(",");

							if (StringUtils.isNotEmpty(tree.getName())) {
								String text = tree.getName();
								text = StringTools.replace(text, "'", "\'");
								buffer.append("'").append(text).append("', ");
							} else {
								buffer.append("null, ");
							}

							if (StringUtils.isNotEmpty(tree.getDesc())) {
								String text = tree.getDesc();
								text = StringTools.replace(text, "'", "\'");
								buffer.append("'").append(text).append("', ");
							} else {
								buffer.append("null, ");
							}

							buffer.append(tree.getSort()).append(", ");

							if (StringUtils.isNotEmpty(tree.getCode())) {
								String text = tree.getCode();
								text = StringTools.replace(text, "'", "\'");
								buffer.append("'").append(text).append("', ");
							} else {
								buffer.append("null, ");
							}

							if (StringUtils.isNotEmpty(tree.getDiscriminator())) {
								String text = tree.getDiscriminator();
								text = StringTools.replace(text, "'", "\'");
								buffer.append("'").append(text).append("', ");
							} else {
								buffer.append("null, ");
							}

							if (StringUtils.isNotEmpty(tree.getTreeId())) {
								String text = tree.getTreeId();
								text = StringTools.replace(text, "'", "\'");
								buffer.append("'").append(text).append("', ");
							} else {
								buffer.append("null, ");
							}

							if (StringUtils.isNotEmpty(tree.getMoveable())) {
								String text = tree.getMoveable();
								text = StringTools.replace(text, "'", "\'");
								buffer.append("'").append(text).append("', ");
							} else {
								buffer.append("null, ");
							}

							if (StringUtils.isNotEmpty(tree.getIcon())) {
								String text = tree.getIcon();
								text = StringTools.replace(text, "'", "\'");
								buffer.append("'").append(text).append("', ");
							} else {
								buffer.append("null, ");
							}

							if (StringUtils.isNotEmpty(tree.getIconCls())) {
								String text = tree.getIconCls();
								text = StringTools.replace(text, "'", "\'");
								buffer.append("'").append(text).append("', ");
							} else {
								buffer.append("null, ");
							}

							if (StringUtils.isNotEmpty(tree.getUrl())) {
								String text = tree.getUrl();
								text = StringTools.replace(text, "'", "\'");
								buffer.append("'").append(text).append("', ");
							} else {
								buffer.append("null, ");
							}

							if (StringUtils.isNotEmpty(tree.getAllowedFileExts())) {
								String text = tree.getAllowedFileExts();
								text = StringTools.replace(text, "'", "\'");
								buffer.append("'").append(text).append("', ");
							} else {
								buffer.append("null, ");
							}

							buffer.append(tree.getAllowedFizeSize()).append(", ");

							if (StringUtils.isNotEmpty(tree.getProviderClass())) {
								String text = tree.getProviderClass();
								text = StringTools.replace(text, "'", "\'");
								buffer.append("'").append(text).append("' ");
							} else {
								buffer.append("null ");
							}

							buffer.append(");");
							buffer.append(FileUtils.newline);

						}
					}
				}
				try {
					ResponseUtils.download(request, response, buffer.toString().getBytes("UTF-8"),
							"sys_application.insert.sql");
				} catch (IOException e) {
				} catch (ServletException e) {
				}
			}
		}
	}

	protected void importData(long parentId, LoginContext loginContext, byte[] bytes) throws IOException {
		JSONObject jsonObject = JSON.parseObject(new String(bytes, "UTF-8"));
		SysTree tree = SysTreeJsonFactory.jsonToObject(jsonObject);
		if (sysTreeService.getSysTreeByCode(tree.getCode()) == null) {
			SysTree parent = null;
			if (parentId > 0) {
				parent = sysTreeService.findById(parentId);
			} else {
				parent = sysTreeService.getSysTreeByCode(SysConstants.TREE_APP);
			}
			if (parent != null) {
				tree.setId(0);
				tree.setParent(parent);
				tree.setParentId(parent.getId());
				tree.setTreeId(null);
				tree.setUpdateBy(null);
				tree.setUpdateDate(null);
				// sysTreeService.create(tree);
				SysApplication app = new SysApplication();
				app.setNode(tree);
				app.setCreateBy(loginContext.getActorId());
				app.setDesc(tree.getDesc());
				app.setImagePath(tree.getIcon());
				app.setName(tree.getName());
				app.setCode(tree.getCode());
				sysApplicationService.create(app);
				logger.debug("create tree......");
			}
			if (jsonObject.containsKey("array")) {
				JSONArray array = jsonObject.getJSONArray("array");
				if (array != null && !array.isEmpty()) {
					List<SysTree> list = SysTreeJsonFactory.arrayToList(array);
					for (SysTree bean : list) {
						if (bean.getApp() != null) {
							bean.setId(0);
							bean.setParent(tree);
							bean.setParentId(tree.getId());
							bean.getApp().setNode(bean);
							sysApplicationService.create(bean.getApp());
						}
					}
				}
			}
		}
	}

	@RequestMapping("/importJson")
	public ModelAndView importJson(HttpServletRequest request, ModelMap modelMap) throws IOException {
		int parentId = ParamUtil.getIntParameter(request, "parentId", 0);
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		ZipInputStream zipInputStream = null;
		boolean status = false;
		// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			try {
				MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
				Map<String, MultipartFile> fileMap = req.getFileMap();
				Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
				int maxSize = 5 * FileUtils.MB_SIZE;
				for (Entry<String, MultipartFile> entry : entrySet) {
					MultipartFile mFile = entry.getValue();
					if (mFile.getOriginalFilename() != null && mFile.getSize() > 0) {
						String filename = mFile.getOriginalFilename();
						logger.debug("upload file:" + filename);
						logger.debug("fize size:" + mFile.getSize());
						if (StringUtils.endsWithIgnoreCase(filename, ".json") && mFile.getSize() < maxSize) {
							this.importData(parentId, loginContext, mFile.getBytes());
						} else if (StringUtils.endsWithIgnoreCase(filename, ".zip") && mFile.getSize() < maxSize) {
							zipInputStream = new ZipInputStream(mFile.getInputStream());
							Map<String, byte[]> zipMap = ZipUtils.getZipBytesMap(zipInputStream);
							if (zipMap != null && !zipMap.isEmpty()) {
								Set<Entry<String, byte[]>> entrySet2 = zipMap.entrySet();
								for (Entry<String, byte[]> entry2 : entrySet2) {
									String key = entry2.getKey();
									if (StringUtils.endsWithIgnoreCase(key, ".json")) {
										byte[] value = entry2.getValue();
										try {
											// 3-根应用的编号
											this.importData(3, loginContext, value);
										} catch (Exception ex) {
											status = false;
											ex.printStackTrace();
											logger.error("error import json data ", ex);
										}
									}
								}
							}
						}
					}
				}
				status = true;
			} catch (Exception ex) {
				status = false;
				ex.printStackTrace();
				logger.error("error import data ", ex);
			}
			request.setAttribute("status", status);
		}
		return this.showList(request, modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysApplicationQuery query = new SysApplicationQuery();
		Tools.populate(query, params);
		query.setDeleteFlag(0);

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
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = sysApplicationService.getSysApplicationCountByQueryCriteria(query);
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

			List<SysApplication> list = sysApplicationService.getSysApplicationsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysApplication sysApplication : list) {
					JSONObject rowJSON = sysApplication.toJsonObject();
					rowJSON.put("id", sysApplication.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			result.put("total", total);
			result.put("totalCount", total);
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
		}
		return result.toString().getBytes("UTF-8");
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
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("application.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/app/list", modelMap);
	}

	/**
	 * 显示修改页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/permission")
	public ModelAndView permission(HttpServletRequest request, ModelMap modelMap) {
		// RequestUtils.setRequestParameterToAttribute(request);
		long id = ParamUtil.getIntParameter(request, "id", 0);
		if (id > 0) {
			SysApplication bean = sysApplicationService.findById(id);
			if (bean != null) {
				List<SysRole> roles = sysApplicationService.getApplicationRoleWithUsers(bean.getId());
				request.setAttribute("bean", bean);
				request.setAttribute("roles", roles);
			}
		}

		String x_view = ViewProperties.getString("application.permission");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/app/permission", modelMap);
	}

	/**
	 * 显示增加页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/prepareAdd")
	public ModelAndView prepareAdd(HttpServletRequest request, ModelMap modelMap) {
		// RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("application.prepareAdd");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/app/app_add", modelMap);
	}

	/**
	 * 显示修改页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/prepareModify")
	public ModelAndView prepareModify(HttpServletRequest request, ModelMap modelMap) {
		// RequestUtils.setRequestParameterToAttribute(request);
		long id = ParamUtil.getLongParameter(request, "id", 0);
		SysApplication bean = sysApplicationService.findById(id);
		request.setAttribute("bean", bean);

		SysTree parent = sysTreeService.getSysTreeByCode(SysConstants.TREE_APP);
		parent.setDeep(0);
		List<SysTree> list = new ArrayList<SysTree>();
		list.add(parent);
		sysTreeService.getSysTree(list, parent.getId(), 1);
		request.setAttribute("parent", list);

		String x_view = ViewProperties.getString("application.prepareModify");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/app/app_modify", modelMap);
	}

	/**
	 * 提交增加信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/saveAdd")
	public ModelAndView saveAdd(HttpServletRequest request, ModelMap modelMap) {
		// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			SysApplication bean = new SysApplication();
			Map<String, Object> dataMap = RequestUtils.getParameterMap(req);
			try {
				Tools.populate(bean, dataMap);
			} catch (Exception ex) {
			}
			bean.setName(ParamUtil.getParameter(req, "name"));
			bean.setCode(ParamUtil.getParameter(req, "code"));
			bean.setDesc(ParamUtil.getParameter(req, "desc"));
			bean.setUrl(ParamUtil.getParameter(req, "url"));
			bean.setImagePath(ParamUtil.getParameter(req, "imagePath"));
			bean.setShowMenu(ParamUtil.getIntParameter(req, "showMenu", 0));
			bean.setShowType(request.getParameter("showType"));
			bean.setLinkParam(ParamUtil.getParameter(req, "linkParam"));
			bean.setPrintParam(ParamUtil.getParameter(req, "printParam"));
			bean.setDatabaseId(ParamUtil.getLongParameter(req, "databaseId", 0));
			bean.setUid(ParamUtil.getParameter(req, "uid"));
			bean.setFlowid(req.getParameter("flowid"));
			bean.setCellTreedotIndex(req.getParameter("cellTreedotIndex"));
			bean.setPosition(req.getParameter("position"));
			bean.setSystemFlag(ParamUtil.getParameter(req, "systemFlag"));
			bean.setPageId(ParamUtil.getParameter(request, "pageId"));
			bean.setRefId1(ParamUtil.getIntParameter(req, "refId1", 0));
			bean.setRefName1(ParamUtil.getParameter(req, "refName1"));
			bean.setRefId2(ParamUtil.getIntParameter(req, "refId2", 0));
			bean.setRefName2(ParamUtil.getParameter(req, "refName2"));
			bean.setRefId3(ParamUtil.getIntParameter(req, "refId3", 0));
			bean.setRefName3(ParamUtil.getParameter(req, "refName3"));
			bean.setRefId4(ParamUtil.getIntParameter(req, "refId4", 0));
			bean.setRefName4(ParamUtil.getParameter(req, "refName4"));
			bean.setRefId5(ParamUtil.getIntParameter(req, "refId5", 0));
			bean.setRefName5(ParamUtil.getParameter(req, "refName5"));
			bean.setCreateBy(RequestUtils.getActorId(request));
			if (StringUtils.isEmpty(bean.getRefName2())) {
				bean.setUid("");
			}
			if (StringUtils.isEmpty(bean.getRefName3())) {
				bean.setPageId("");
			}
			if (StringUtils.isEmpty(bean.getRefName5())) {
				bean.setCellTreedotIndex("");
			}

			SysTree node = new SysTree();
			node.setName(bean.getName());
			node.setDesc(bean.getName());
			node.setCode(bean.getCode());
			node.setCreateBy(RequestUtils.getActorId(request));
			node.setParentId((long) ParamUtil.getIntParameter(req, "parent", 0));
			bean.setNode(node);

			Map<String, MultipartFile> fileMap = req.getFileMap();
			Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
			for (Entry<String, MultipartFile> entry : entrySet) {
				MultipartFile mFile = entry.getValue();
				if (StringUtils.equals(mFile.getName(), "linkFileName")) {
					if (mFile.getOriginalFilename() != null && mFile.getSize() > 0) {
						String filename = mFile.getOriginalFilename();
						bean.setLinkFileName(filename);
						bean.setLinkType("T");
						try {
							bean.setLinkFileContent(mFile.getBytes());
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
				if (StringUtils.equals(mFile.getName(), "printFileName")) {
					if (mFile.getOriginalFilename() != null && mFile.getSize() > 0) {
						String filename = mFile.getOriginalFilename();
						bean.setPrintFileName(filename);
						bean.setPrintType("T");
						try {
							bean.setPrintFileContent(mFile.getBytes());
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			}

			boolean ret = sysApplicationService.create(bean);
			ViewMessages messages = new ViewMessages();
			if (ret) {// 保存成功
				messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("application.add_success"));
			} else {// 保存失败
				messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("application.add_failure"));
			}
			MessageUtils.addMessages(request, messages);
		}
		return new ModelAndView("/show_msg", modelMap);
	}

	/**
	 * 提交修改信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/saveModify")
	public ModelAndView saveModify(HttpServletRequest request, ModelMap modelMap) {
		// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			long id = ParamUtil.getIntParameter(req, "id", 0);
			SysApplication bean = sysApplicationService.findById(id);
			if (bean != null) {
				Map<String, Object> dataMap = RequestUtils.getParameterMap(req);
				try {
					Tools.populate(bean, dataMap);
				} catch (Exception ex) {
				}
				bean.setName(ParamUtil.getParameter(req, "name"));
				bean.setCode(ParamUtil.getParameter(req, "code"));
				bean.setDesc(ParamUtil.getParameter(req, "desc"));
				bean.setUrl(ParamUtil.getParameter(req, "url"));
				bean.setSort(ParamUtil.getIntParameter(req, "sort", 0));
				bean.setImagePath(ParamUtil.getParameter(req, "imagePath"));
				bean.setShowMenu(ParamUtil.getIntParameter(req, "showMenu", 0));
				bean.setShowType(request.getParameter("showType"));
				bean.setLinkParam(ParamUtil.getParameter(req, "linkParam"));
				bean.setPrintParam(ParamUtil.getParameter(req, "printParam"));
				bean.setDatabaseId(ParamUtil.getLongParameter(req, "databaseId", 0));
				bean.setUid(ParamUtil.getParameter(req, "uid"));
				// bean.setUid(req.getParameter("uid"));
				bean.setFlowid(req.getParameter("flowid"));
				bean.setCellTreedotIndex(req.getParameter("cellTreedotIndex"));
				bean.setPosition(req.getParameter("position"));
				bean.setSystemFlag(ParamUtil.getParameter(req, "systemFlag"));
				bean.setPageId(ParamUtil.getParameter(request, "pageId"));
				bean.setUpdateBy(RequestUtils.getActorId(request));
				bean.setLocked(ParamUtil.getIntParameter(req, "locked", 0));
				bean.setRefId1(ParamUtil.getIntParameter(req, "refId1", 0));
				bean.setRefName1(ParamUtil.getParameter(req, "refName1"));
				bean.setRefId2(ParamUtil.getIntParameter(req, "refId2", 0));
				bean.setRefName2(ParamUtil.getParameter(req, "refName2"));
				bean.setRefId3(ParamUtil.getIntParameter(req, "refId3", 0));
				bean.setRefName3(ParamUtil.getParameter(req, "refName3"));
				bean.setRefId4(ParamUtil.getIntParameter(req, "refId4", 0));
				bean.setRefName4(ParamUtil.getParameter(req, "refName4"));
				bean.setRefId5(ParamUtil.getIntParameter(req, "refId5", 0));
				bean.setRefName5(ParamUtil.getParameter(req, "refName5"));

				if (StringUtils.isEmpty(bean.getRefName2())) {
					bean.setUid("");
				}
				if (StringUtils.isEmpty(bean.getRefName3())) {
					bean.setPageId("");
				}
				if (StringUtils.isEmpty(bean.getRefName5())) {
					bean.setCellTreedotIndex("");
				}

				SysTree node = bean.getNode();
				node.setName(bean.getName());
				node.setCode(bean.getCode());
				node.setDesc(bean.getName());
				node.setParentId(ParamUtil.getLongParameter(req, "parent", 0));
				bean.setNode(node);

				Map<String, MultipartFile> fileMap = req.getFileMap();
				Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
				for (Entry<String, MultipartFile> entry : entrySet) {
					MultipartFile mFile = entry.getValue();
					if (StringUtils.equals(mFile.getName(), "linkFileName")) {
						if (mFile.getOriginalFilename() != null && mFile.getSize() > 0) {
							String filename = mFile.getOriginalFilename();
							bean.setLinkFileName(filename);
							bean.setLinkType("T");
							try {
								bean.setLinkFileContent(mFile.getBytes());
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}
					}
					if (StringUtils.equals(mFile.getName(), "printFileName")) {
						if (mFile.getOriginalFilename() != null && mFile.getSize() > 0) {
							String filename = mFile.getOriginalFilename();
							bean.setPrintFileName(filename);
							bean.setPrintType("T");
							try {
								bean.setPrintFileContent(mFile.getBytes());
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			}
			boolean ret = false;
			try {
				ret = sysApplicationService.update(bean);
			} catch (Exception ex) {
				ret = false;
				logger.error(ex);
			}
			ViewMessages messages = new ViewMessages();
			if (ret) {// 保存成功
				messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("application.modify_success"));
			} else {// 保存失败
				messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("application.modify_failure"));
			}
			MessageUtils.addMessages(request, messages);
		}
		return new ModelAndView("/show_msg", modelMap);
	}

	@javax.annotation.Resource
	public void setSysApplicationService(SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

	/**
	 * 显示框架页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showBase")
	public ModelAndView showBase(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_view = ViewProperties.getString("application.showBase");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/app/basedata_frame", modelMap);
	}

	/**
	 * 显示框架页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showFrame")
	public ModelAndView showFrame(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		SysTree bean = sysTreeService.getSysTreeByCode(SysConstants.TREE_APP);
		request.setAttribute("parent", bean.getId() + "");
		String x_view = ViewProperties.getString("application.showFrame");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/app/app_frame", modelMap);
	}

	/**
	 * 显示导入页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showImport")
	public ModelAndView showImport(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("tree.showImport");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		// 显示列表页面
		return new ModelAndView("/modules/sys/app/showImport", modelMap);
	}

	/**
	 * 显示所有列表
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showList")
	public ModelAndView showList(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int parent = ParamUtil.getIntParameter(request, "parent", 0);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size", SysConstants.PAGE_SIZE);

		SysApplicationQuery query = new SysApplicationQuery();
		String rq = ParamUtil.getParameter(request, "_rq_", "");
		logger.debug("_rq_:" + rq);
		String nameLike_encode = ParamUtil.getParameter(request, "nameLike_encode", "");
		String codeLike_encode = ParamUtil.getParameter(request, "codeLike_encode", "");

		if ("1".equals(rq)) {
			logger.debug("-----------------------参数查询-----------------------");
			String nameLike = ParamUtil.getParameter(request, "nameLike", "");
			String codeLike = ParamUtil.getParameter(request, "codeLike", "");
			if (StringUtils.isNotEmpty(nameLike)) {
				query.setNameLike(nameLike);
				request.setAttribute("nameLike_encode", RequestUtils.encodeString(nameLike));
				request.setAttribute("nameLike", nameLike);
			} else {
				request.removeAttribute("nameLike");
				request.removeAttribute("nameLike_encode");
				request.setAttribute("nameLike", "");
			}
			if (StringUtils.isNotEmpty(codeLike)) {
				query.setCodeLike(codeLike);
				request.setAttribute("codeLike_encode", RequestUtils.encodeString(codeLike));
				request.setAttribute("codeLike", codeLike);
			} else {
				request.removeAttribute("codeLike");
				request.removeAttribute("codeLike_encode");
				request.setAttribute("codeLike", "");
			}
		} else {
			logger.debug("-----------------------链接查询-----------------------");
			if (StringUtils.isNotEmpty(nameLike_encode)) {
				String nameLike = RequestUtils.decodeString(nameLike_encode);
				query.setNameLike(nameLike);
				request.setAttribute("nameLike_encode", nameLike_encode);
				request.setAttribute("nameLike", nameLike);
			}
			if (StringUtils.isNotEmpty(codeLike_encode)) {
				String codeLike = RequestUtils.decodeString(codeLike_encode);
				query.setCodeLike(codeLike);
				request.setAttribute("codeLike_encode", codeLike_encode);
				request.setAttribute("codeLike", codeLike);
			}

		}

		query.setDeleteFlag(0);

		PageResult pager = null;

		if (parent > 0) {
			pager = sysApplicationService.getApplicationList(parent, pageNo, pageSize);
		} else {
			pager = sysApplicationService.getApplicationList(pageNo, pageSize, query);
		}
		request.setAttribute("pager", pager);

		String x_view = ViewProperties.getString("application.showList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		// 显示列表页面
		return new ModelAndView("/modules/sys/app/app_list", modelMap);
	}

	/**
	 * 显示二级栏目导航菜单
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showNavMenu")
	public ModelAndView showNavMenu(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int parent = ParamUtil.getIntParameter(request, "parent", 0);
		List<SysTree> list = new ArrayList<SysTree>();
		sysTreeService.getSysTree(list, parent, 0);
		request.setAttribute("list", list);

		String x_view = ViewProperties.getString("application.showNavMenu");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/app/navmenu", modelMap);
	}

	@RequestMapping("/showPermission")
	public ModelAndView showPermission(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("application.showPermission");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/app/permission_frame", modelMap);
	}

	@ResponseBody
	@RequestMapping("/sort")
	public byte[] sort(@RequestParam(value = "parent") int parent, @RequestParam(value = "id") int id,
			@RequestParam(value = "operate") int operate) {
		logger.debug("parent:" + parent + "; id:" + id + "; operate:" + operate);
		try {
			SysApplication bean = sysApplicationService.findById(id);
			sysApplicationService.sort(parent, bean, operate);
			return ResponseUtils.responseResult(true);
		} catch (Exception ex) {
		}
		return ResponseUtils.responseResult(false);
	}

	protected JSONObject toJSONObject(SysTree parent) {
		JSONObject jsonObject = new JSONObject();
		if (parent != null) {
			jsonObject = parent.toJsonObject();
			List<SysTree> treeList = new ArrayList<SysTree>();
			sysTreeService.loadSysTrees(treeList, parent.getId(), 0);
			if (treeList != null && !treeList.isEmpty()) {
				JSONArray array = SysTreeJsonFactory.listToArray(treeList);
				jsonObject.put("array", array);
			}
		}
		return jsonObject;
	}
}