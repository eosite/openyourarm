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

package com.glaf.base.modules.website.springmvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.Environment;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.security.LoginContext;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.RequestUtils;
import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.ui.service.LayoutService;
import com.glaf.ui.service.PanelService;
import com.glaf.ui.service.UserPortalService;

@Controller("/website/portal")
public class WebSitePortalController {
	protected final static Log logger = LogFactory
			.getLog(WebSitePortalController.class);

	protected LayoutService layoutService;

	protected PanelService panelService;

	protected SysApplicationService sysApplicationService;

	protected UserPortalService userPortalService;

	protected void fill(JSONObject jsonObject, StringBuffer buffer) {
		String text = jsonObject.getString("text");
		if (text != null && jsonObject.getString("id") != null) {
			JSONArray children = jsonObject.getJSONArray("children");
			if (children != null && !children.isEmpty()) {
				buffer.append("\n  <li iconCls=\"icon-base\"><span>")
						.append(text).append("</span>");
				buffer.append("\n      <ul>");
				Iterator<Object> iterator = children.iterator();
				while (iterator.hasNext()) {
					Object obj = iterator.next();
					if (obj instanceof JSONObject) {
						JSONObject json = (JSONObject) obj;
						this.fill(json, buffer);
					}
				}
				buffer.append("\n      </ul>");
				buffer.append("\n  </li>");
			} else {
				buffer.append("\n    <li iconCls=\"icon-gears\"><a href=\"#\" ")
						.append(" onclick=\"openTabs('").append(text)
						.append("','").append(jsonObject.getString("id"))
						.append("');\">").append(text).append("</a>");
				buffer.append("\n    </li>");
			}
		}
	}

	@RequestMapping("/website/home")
	public ModelAndView home(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		if (StringUtils.isNotEmpty(request.getParameter("systemName"))) {
			Environment
					.setCurrentSystemName(request.getParameter("systemName"));
		} else {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		}
		String userId = RequestUtils.getActorId(request);
		logger.debug("-----------------------home--------------------------------");
		logger.debug("currentSystemName:" + Environment.getCurrentSystemName());

		long appId = RequestUtils.getLong(request, "appId", 0);

		if (appId == 0) {
			String appCode = RequestUtils.getString(request, "appCode",
					"website");
			SysApplication sysApplication = sysApplicationService
					.findByCode(appCode);
			appId = sysApplication.getId();
		}

		String scripts = "";
		try {
			org.json.JSONArray array = sysApplicationService.getUserMenu(appId,
					userId);
			scripts = array.toString('\n');
			request.setAttribute("scripts", scripts);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}

		return new ModelAndView("/modules/website/portal/home", modelMap);
	}

	@RequestMapping("/website/index")
	public ModelAndView index(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		logger.debug("loginContext:"
				+ loginContext.toJsonObject().toJSONString());
		RequestUtils.setRequestParameterToAttribute(request);

		if (StringUtils.isNotEmpty(request.getParameter("systemName"))) {
			Environment
					.setCurrentSystemName(request.getParameter("systemName"));
		} else {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		}

		long appId = RequestUtils.getLong(request, "appId", 0);

		if (appId == 0) {
			String appCode = RequestUtils.getString(request, "appCode");
			SysApplication sysApplication = sysApplicationService
					.findByCode(appCode);
			appId = sysApplication.getId();
		}

		TreeModel root = sysApplicationService.getTreeModelByAppId(appId);
		List<TreeModel> treeNodes = null;
		if (root != null) {
			treeNodes = sysApplicationService.getTreeModels(root.getId(),
					loginContext.getActorId());
			if (treeNodes != null) {
				Collections.sort(treeNodes);
			}
		} else {
			treeNodes = sysApplicationService.getTreeModels(appId,
					loginContext.getActorId());
			if (treeNodes != null) {
				Collections.sort(treeNodes);
			}
		}

		Collection<String> roles = loginContext.getRoles();
		List<String> list = new ArrayList<String>();
		if (roles != null && !roles.isEmpty()) {
			for (String r : roles) {
				list.add(r);
			}
		}

		modelMap.put("root", root);
		modelMap.put("treeNodes", treeNodes);

		if (root != null && treeNodes != null) {

			logger.debug("#######################################################");
			logger.debug("root:" + root.toJsonObject().toJSONString());
			// logger.debug("treeNodes:" + treeNodes);

			TreeHelper treeHelper = new TreeHelper();
			JSONObject treeJson = treeHelper.getTreeJson(root, treeNodes);
			modelMap.put("treeJson", treeJson);
			modelMap.put("json", treeJson.toJSONString());
			logger.debug("json=" + treeJson.toJSONString());
			JSONArray jsonArray = treeHelper.getTreeJSONArray(treeNodes);
			modelMap.put("jsonArray", jsonArray.toJSONString());
			logger.debug("jsonArray=" + jsonArray.toJSONString());

			StringBuffer buffer = new StringBuffer();
			String text = treeJson.getString("text");
			if (text != null) {
				buffer.append("\n <li iconCls=\"icon-root\"><span>")
						.append(text).append("</span>");
				JSONArray children = treeJson.getJSONArray("children");
				if (children != null && !children.isEmpty()) {
					buffer.append("\n  <ul>");
					Iterator<Object> iterator = children.iterator();
					while (iterator.hasNext()) {
						Object obj = iterator.next();
						if (obj instanceof JSONObject) {
							JSONObject json = (JSONObject) obj;
							this.fill(json, buffer);
						}
					}
					buffer.append("\n  </ul>");
				}
				buffer.append("\n</li>");
			}

			modelMap.put("scripts", buffer.toString());
		}

		logger.debug("#######################################");
		logger.debug(loginContext.getRoles());

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("website_workspace.index");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/website/portal/index", modelMap);
	}

	@RequestMapping("/website/main")
	public ModelAndView main(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		logger.debug("loginContext:"
				+ loginContext.toJsonObject().toJSONString());
		RequestUtils.setRequestParameterToAttribute(request);

		if (StringUtils.isNotEmpty(request.getParameter("systemName"))) {
			Environment
					.setCurrentSystemName(request.getParameter("systemName"));
		} else {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		}

		long appId = RequestUtils.getLong(request, "appId", 0);

		if (appId == 0) {
			String appCode = RequestUtils.getString(request, "appCode",
					"website");
			SysApplication sysApplication = sysApplicationService
					.findByCode(appCode);
			appId = sysApplication.getId();
		}

		List<TreeModel> topLevelTreeModels = sysApplicationService
				.getTopLevelTreeModels(appId, loginContext.getActorId());

		TreeModel root = sysApplicationService.getTreeModelByAppId(appId);
		List<TreeModel> treeNodes = null;
		if (root != null) {
			treeNodes = sysApplicationService.getTreeModels(root.getId(),
					loginContext.getActorId());
			if (treeNodes != null) {
				Collections.sort(treeNodes);
			}
		} else {
			treeNodes = sysApplicationService.getTreeModels(appId,
					loginContext.getActorId());
			if (treeNodes != null) {
				Collections.sort(treeNodes);
			}
		}

		Collection<String> roles = loginContext.getRoles();
		List<String> list = new ArrayList<String>();
		if (roles != null && !roles.isEmpty()) {
			for (String r : roles) {
				list.add(r);
			}
		}

		modelMap.put("root", root);
		modelMap.put("treeNodes", treeNodes);
		modelMap.put("topLevelTreeModels", topLevelTreeModels);

		if (root != null && treeNodes != null) {

			logger.debug("#######################################################");
			// logger.debug("treeNodes:" + treeNodes.size());

			TreeHelper treeHelper = new TreeHelper();
			JSONObject treeJson = treeHelper.getTreeJson(root, treeNodes);
			modelMap.put("treeJson", treeJson);
			modelMap.put("json", treeJson.toJSONString());
			logger.debug(treeJson.toJSONString());

			JSONArray jsonArray = treeHelper.getTreeJSONArray(treeNodes);
			modelMap.put("jsonArray", jsonArray.toJSONString());
			logger.debug(jsonArray.toJSONString());

			StringBuffer buffer = new StringBuffer();
			String text = treeJson.getString("text");
			if (text != null) {
				buffer.append("\n <li iconCls=\"icon-root\"><span>")
						.append(text).append("</span>");
				JSONArray children = treeJson.getJSONArray("children");
				if (children != null && !children.isEmpty()) {
					buffer.append("\n  <ul>");
					Iterator<Object> iterator = children.iterator();
					while (iterator.hasNext()) {
						Object obj = iterator.next();
						if (obj instanceof JSONObject) {
							JSONObject json = (JSONObject) obj;
							this.fill(json, buffer);
						}
					}
					buffer.append("\n  </ul>");
				}
				buffer.append("\n</li>");
			}

			modelMap.put("scripts", buffer.toString());
		}

		logger.debug("#######################################");
		logger.debug(loginContext.getRoles());

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("website_workspace.main");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/website/portal/main", modelMap);
	}

	@javax.annotation.Resource
	public void setLayoutService(LayoutService layoutService) {
		this.layoutService = layoutService;
	}

	@javax.annotation.Resource
	public void setPanelService(PanelService panelService) {
		this.panelService = panelService;
	}

	@javax.annotation.Resource
	public void setSysApplicationService(
			SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
	}

	@javax.annotation.Resource
	public void setUserPortalService(UserPortalService userPortalService) {
		this.userPortalService = userPortalService;
	}

}