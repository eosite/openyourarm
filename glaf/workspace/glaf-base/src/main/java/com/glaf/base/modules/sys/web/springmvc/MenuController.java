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
import java.util.List;

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
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;

@Controller("/identity/menu")
@RequestMapping("/identity/menu")
public class MenuController {
	private static final Log logger = LogFactory.getLog(MenuController.class);

	protected SysApplicationService sysApplicationService;

	protected SysTreeService sysTreeService;

	@ResponseBody
	@RequestMapping("/menuJson")
	public byte[] menuJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		long parentId = ParamUtil.getLongParameter(request, "parentId", 3);

		SysTree root = sysTreeService.findById(parentId);
		List<SysTree> list = sysApplicationService.getTreeWithApplicationList(parentId);
		List<TreeModel> treeModels = new ArrayList<TreeModel>();

		for (SysTree tree : list) {
			if (tree.getParentId() == root.getId()) {
				// tree.setParentId(0);
			}
			if (tree.getId() != root.getId()) {
				treeModels.add(tree);
			}
			if (tree.getApp() != null) {
				if (tree.getApp().getUrl() != null) {
					tree.setUrl(tree.getApp().getUrl());
					if (StringUtils.startsWith(tree.getApp().getUrl(), "/mx/form/page/viewPage?isPublish=true")) {
						String new_url = StringTools.replace(tree.getApp().getUrl(), "isPublish=true&", "");
						tree.setUrl(new_url);
					}
				} else {
					if (StringUtils.equalsIgnoreCase(tree.getApp().getSystemFlag(), "WPF")) {
						String url = SysConstants.WPF_DEF_URL
								+ RequestUtils.encodeString(String.valueOf(tree.getApp().getId()));
						tree.setUrl(url);
					} else if (StringUtils.equalsIgnoreCase(tree.getApp().getSystemFlag(), "JAVA")
							&& StringUtils.isNotEmpty(tree.getApp().getPageId())) {
						String url = SysConstants.FORM_DEF_URL + tree.getApp().getPageId();
						tree.setUrl(url);
						logger.debug("-------------------JAVA-------------");
					}
				}
			}
		}

		logger.debug("treeModels:" + treeModels.size());
		TreeHelper treeHelper = new TreeHelper();
		JSONArray jsonArray = treeHelper.getTreeJSONArray(treeModels);
		// JacksonTreeHelper treeHlper = new JacksonTreeHelper();
		// ArrayNode jsonArray = treeHlper.getTreeArrayNode(treeModels);
		logger.debug(jsonArray.toJSONString());
		return jsonArray.toJSONString().getBytes("UTF-8");

	}

	/**
	 * 显示菜单页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/choose")
	public ModelAndView roleMenus(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("identity.menu.choose");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return new ModelAndView("/modules/identity/menu/choose", modelMap);
	}

	@javax.annotation.Resource
	public void setSysApplicationService(SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

}