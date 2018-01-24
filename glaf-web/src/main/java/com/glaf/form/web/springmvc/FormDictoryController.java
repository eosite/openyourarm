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

package com.glaf.form.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.res.MessageUtils;
import com.glaf.base.res.ViewMessage;
import com.glaf.base.res.ViewMessages;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.DictoryDefinition;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.DictoryDefinitionService;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.core.util.ZipUtils;
import com.glaf.form.core.domain.FormDictTree;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.query.FormDictTreeQuery;
import com.glaf.form.core.query.FormDictoryQuery;
import com.glaf.form.core.service.IFormDictTreeService;
import com.glaf.form.core.service.IFormDictoryService;
import com.glaf.form.core.util.FormDictTreeJsonFactory;
import com.glaf.form.core.util.FormDictoryFactory;
import com.glaf.form.core.util.FormDictoryJsonFactory;

@Controller("/form/formDictory")
@RequestMapping("/form/formDictory")
public class FormDictoryController {
	private static final Log logger = LogFactory.getLog(FormDictoryController.class);

	protected DictoryDefinitionService dictoryDefinitionService;

	private IFormDictoryService formDictoryService;

	private IFormDictTreeService formDictTreeService;

	private final String TREE_DICTORY = "defined_baseData";

	/**
	 * 提交删除
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/batchDelete")
	public ModelAndView batchDelete(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		boolean ret = true;
		long[] id = ParamUtil.getLongParameterValues(request, "id");
		ret = formDictoryService.deleteAll(id);
		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			FormDictoryFactory.getInstance().reload();
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("dictory.delete_success"));
		} else { // 删除失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("dictory.delete_failure"));
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg2", modelMap);
	}

	@RequestMapping(params = "method=getFormDictoryByTreeCode")
	@ResponseBody
	public byte[] getFormDictoryByTreeCode(HttpServletRequest request) throws Exception {
		String treeCode = request.getParameter("dictTreeCode");
		List<FormDictory> list = FormDictoryFactory.getInstance().getFormDictoryListByTreeCode(treeCode);
		JSONArray array = FormDictoryJsonFactory.listToArray(list);

		return array.toJSONString().getBytes("utf-8");
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormDictoryQuery query = new FormDictoryQuery();
		Tools.populate(query, params);

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
		int total = formDictoryService.getFormDictoryCountByQueryCriteria(query);
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

			List<FormDictory> list = formDictoryService.getFormDictorysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormDictory dictory : list) {
					JSONObject rowJSON = dictory.toJsonObject();
					rowJSON.put("id", dictory.getId());
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

		String x_view = ViewProperties.getString("base_dictory.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/form/dictory/list", modelMap);
	}

	/**
	 * 显示框架页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/loadDictory")
	public ModelAndView loadDictory(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("base_dictory.loadDictory");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/dictory/dictory_load", modelMap);
	}

	/**
	 * 显示增加字典页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/prepareAdd")
	public ModelAndView prepareAdd(HttpServletRequest request, ModelMap modelMap) {
		// 显示列表页面
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Long nodeId = ParamUtils.getLong(params, "parent");
		if (nodeId > 0) {
			List<DictoryDefinition> list = dictoryDefinitionService.getDictoryDefinitions(nodeId, "sys_dictory");
			if (list != null && !list.isEmpty()) {
				Collections.sort(list);
				request.setAttribute("list", list);
			}
		}

		return new ModelAndView("/form/dictory/dictory_add", modelMap);
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
		RequestUtils.setRequestParameterToAttribute(request);
		long id = ParamUtil.getIntParameter(request, "id", 0);
		FormDictory bean = formDictoryService.find(id);
		request.setAttribute("bean", bean);

		long nodeId = ParamUtil.getLongParameter(request, "parent", 0);
		if (nodeId > 0) {
			List<DictoryDefinition> list = dictoryDefinitionService.getDictoryDefinitions(nodeId, "sys_dictory");
			if (list != null && !list.isEmpty()) {
				if (bean != null) {
					Map<String, Object> dataMap = Tools.getDataMap(bean);
					for (DictoryDefinition d : list) {
						Object value = dataMap.get(d.getName());
						d.setValue(value);
					}
				}
				Collections.sort(list);
				request.setAttribute("list", list);
			}
		}

		FormDictTree parent = formDictTreeService.getFormDictTreeByCode(TREE_DICTORY);
		List<FormDictTree> list = new ArrayList<FormDictTree>();
		parent.setDeep(0);
		list.add(parent);
		formDictTreeService.getFormDictTree(list, (int) parent.getId(), 1);
		request.setAttribute("parent", list);

		return new ModelAndView("/form/dictory/dictory_modify", modelMap);
	}

	/**
	 * 提交增加字典信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/saveAdd")
	public ModelAndView saveAdd(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("params:" + params);
		FormDictory bean = new FormDictory();
		try {
			Tools.populate(bean, params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		bean.setCreateBy(RequestUtils.getActorId(request));

		ViewMessages messages = new ViewMessages();
		if (formDictoryService.create(bean)) {// 保存成功
			FormDictoryFactory.getInstance().reload();
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("dictory.add_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("dictory.add_failure"));
		}
		MessageUtils.addMessages(request, messages);
		request.setAttribute("url", "dictory/showList");

		return new ModelAndView("show_msg", modelMap);
	}

	/**
	 * 显示重载数据
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/saveLoadDictory")
	public ModelAndView saveLoadDictory(HttpServletRequest request, ModelMap modelMap) {
		FormDictoryFactory.getInstance().reload();
		ViewMessages messages = new ViewMessages();
		messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("dictory.reload_success"));
		MessageUtils.addMessages(request, messages);

		String x_view = ViewProperties.getString("base_dictory.saveLoadDictory");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		// 显示列表页面
		return new ModelAndView("/form/dictory/dictory_load", modelMap);
	}

	/**
	 * 提交修改字典信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/saveModify")
	public ModelAndView saveModify(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		long id = ParamUtil.getIntParameter(request, "id", 0);
		FormDictory bean = formDictoryService.find(id);
		logger.debug("params:" + params);
		try {
			Tools.populate(bean, params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		bean.setUpdateBy(RequestUtils.getActorId(request));
		ViewMessages messages = new ViewMessages();
		if (formDictoryService.update(bean)) {// 保存成功
			FormDictoryFactory.getInstance().reload();
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("dictory.modify_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("dictory.modify_failure"));
		}
		MessageUtils.addMessages(request, messages);

		return new ModelAndView("show_msg", modelMap);
	}

	@javax.annotation.Resource
	public void setDictoryDefinitionService(DictoryDefinitionService dictoryDefinitionService) {
		this.dictoryDefinitionService = dictoryDefinitionService;

	}

	/**
	 * 显示字典数据
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showDictData")
	public ModelAndView showDictData(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		ModelAndView view = null;
		String code = ParamUtil.getParameter(request, "code");
		Iterator<?> iter = null;
		long parent = ParamUtil.getLongParameter(request, "parent", -1);
		if (!(parent == -1)) {

		} else {
			iter = FormDictoryFactory.getInstance().getFormDictoryIteratorByTreeCode(code);
			view = new ModelAndView("/form/dictory/dictory_select", modelMap);
		}
		request.setAttribute("list", iter);

		// 显示列表页面
		return view;
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
		FormDictTree bean = formDictTreeService.getFormDictTreeByCode(TREE_DICTORY);
		request.setAttribute("parent", bean.getId() + "");

		return new ModelAndView("/form/dictory/dictory_frame", modelMap);
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
		int parent = ParamUtil.getIntParameter(request, "parent", -1);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size", 10);
		PageResult pager = formDictoryService.getFormDictoryList(parent, pageNo, pageSize);
		request.setAttribute("pager", pager);
		// 显示列表页面
		return new ModelAndView("/form/dictory/dictory_list", modelMap);
	}

	@ResponseBody
	@RequestMapping("/sort")
	public byte[] sort(@RequestParam(value = "parent") int parent, @RequestParam(value = "id") int id,
			@RequestParam(value = "operate") int operate) {
		logger.debug("parent:" + parent + "; id:" + id + "; operate:" + operate);
		try {
			FormDictory bean = formDictoryService.find(id);
			formDictoryService.sort(parent, bean, operate);
			return ResponseUtils.responseResult(true);
		} catch (Exception ex) {
		}
		return ResponseUtils.responseResult(false);
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

		String x_view = ViewProperties.getString("dictory.showImport");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		// 显示列表页面
		return new ModelAndView("/form/dictory/showImport", modelMap);
	}

	@ResponseBody
	@RequestMapping("/exportJson")
	public void exportJson(HttpServletRequest request, HttpServletResponse response) {
		int parentId = ParamUtil.getIntParameter(request, "parentId", 0);
		if (parentId > 0) {
			FormDictTree tree = formDictTreeService.findById(parentId);
			if (tree != null) {
				if (StringUtils.equals(tree.getCode(), TREE_DICTORY)) {
					FormDictTreeQuery query = new FormDictTreeQuery();
					query.setLocked(0);
					List<FormDictTree> trees = formDictTreeService.getDictoryFormDictTrees(query);
					if (trees != null && trees.size() > 0) {
						Map<String, byte[]> zipMap = new HashMap<String, byte[]>();
						try {
							for (FormDictTree t : trees) {
								JSONObject result = t.toJsonObject();
								result.remove("id");
								result.remove("_id_");
								result.remove("_oid_");
								result.remove("treeId");
								result.remove("createBy");
								result.remove("createDate");
								result.remove("updateBy");
								result.remove("updateDate");
								List<FormDictory> list = formDictoryService.getFormDictoryList(t.getId());
								JSONArray array = FormDictoryJsonFactory.listToArray(list);
								result.put("dicts", array);
								zipMap.put(t.getName() + ".json", result.toJSONString().getBytes("UTF-8"));
							}
							byte[] zipBytes = ZipUtils.toZipBytes(zipMap);
							ResponseUtils.download(request, response, zipBytes, "dicts.zip");
						} catch (IOException e) {
						} catch (ServletException e) {
						}
					}
				} else {
					JSONObject result = tree.toJsonObject();
					result.remove("id");
					result.remove("_id_");
					result.remove("_oid_");
					result.remove("treeId");
					result.remove("createBy");
					result.remove("createDate");
					result.remove("updateBy");
					result.remove("updateDate");
					try {
						List<FormDictory> list = formDictoryService.getFormDictoryList(parentId);
						JSONArray array = FormDictoryJsonFactory.listToArray(list);
						result.put("dicts", array);
						ResponseUtils.download(request, response, result.toJSONString().getBytes("UTF-8"),
								parentId + ".json");
					} catch (IOException e) {
					} catch (ServletException e) {
					}
				}
			}
		}
	}

	protected void importData(LoginContext loginContext, byte[] bytes) throws IOException {
		JSONObject json = JSON.parseObject(new String(bytes, "UTF-8"));
		FormDictTree tree = FormDictTreeJsonFactory.jsonToObject(json);
		if (formDictTreeService.getFormDictTreeByCode(tree.getCode()) == null) {
			FormDictTree parent = formDictTreeService.getFormDictTreeByCode(TREE_DICTORY);
			if (parent != null) {
				tree.setId(0);
				tree.setParent(parent);
				tree.setParentId(parent.getId());
				tree.setTreeId(null);
				tree.setUpdateBy(null);
				tree.setUpdateDate(null);
				formDictTreeService.create(tree);
				logger.debug("create tree......");
			}

			tree = formDictTreeService.getFormDictTreeByCode(tree.getCode());
			logger.debug("find tree......");

			if (tree != null && json.containsKey("dicts")) {
				logger.debug("tree:" + tree.toJsonObject().toJSONString());
				JSONArray array = json.getJSONArray("dicts");
				if (array != null && array.size() > 0) {
					logger.debug("dicts size:" + array.size());
					List<FormDictory> rows = formDictoryService.getFormDictoryList(tree.getId());
					List<FormDictory> list = FormDictoryJsonFactory.arrayToList(array);
					if (list != null && list.size() > 0) {
						for (FormDictory dict : list) {
							boolean create = true;
							if (rows != null && rows.size() > 0) {
								for (FormDictory d : rows) {
									if (StringUtils.equalsIgnoreCase(d.getCode(), dict.getCode())) {
										create = false;
										break;
									}
								}
							}
							if (create) {
								dict.setId(0);
								dict.setNodeId(tree.getId());
								dict.setCreateBy(loginContext.getActorId());
								formDictoryService.create(dict);
							}
						}
					}
				}
			}
		}
	}

	@RequestMapping("/importJson")
	public ModelAndView importJson(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		ZipInputStream zipInputStream = null;
		boolean status = false;
		try {
			// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			// 检查form中是否有enctype="multipart/form-data"
			if (multipartResolver.isMultipart(request)) {
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
							this.importData(loginContext, mFile.getBytes());
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
											this.importData(loginContext, value);
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
			}
		} catch (Exception ex) {
			status = false;
			ex.printStackTrace();
			logger.error("error import data ", ex);
		} finally {
			IOUtils.closeQuietly(zipInputStream);
		}
		request.setAttribute("status", status);
		return this.list(request, modelMap);
	}

	@Resource
	public void setFormDictoryService(IFormDictoryService formDictoryService) {
		this.formDictoryService = formDictoryService;
	}

	@Resource
	public void setFormDictTreeService(IFormDictTreeService formDictTreeService) {
		this.formDictTreeService = formDictTreeService;
	}
}