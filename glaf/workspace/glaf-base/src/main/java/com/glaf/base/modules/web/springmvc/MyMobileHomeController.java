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

package com.glaf.base.modules.web.springmvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.base.TreeModel;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;

import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.service.SysApplicationService;

import com.glaf.ui.model.UserTheme;
import com.glaf.ui.service.LayoutService;
import com.glaf.ui.service.PanelService;
import com.glaf.ui.service.ThemeService;
import com.glaf.ui.service.UserPortalService;
import com.glaf.ui.service.UserThemeService;

@Controller("/mobile/home")
@RequestMapping("/mobile/home")
public class MyMobileHomeController {
	protected final static Log logger = LogFactory.getLog(MyMobileHomeController.class);

	protected LayoutService layoutService;

	protected PanelService panelService;

	protected SysApplicationService sysApplicationService;

	protected UserPortalService userPortalService;

	protected ThemeService themeService;

	protected UserThemeService userThemeService;

	@RequestMapping
	public ModelAndView home(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		logger.debug("-----------------------mobile home------------------------");

		SysApplication app = sysApplicationService.findByCode("mobile_app");
		if (app != null) {
			TreeModel root = sysApplicationService.getTreeModelByAppId(app.getId());
			List<TreeModel> treeNodes = null;
			if (root != null) {
				treeNodes = sysApplicationService.getTreeModels(root.getId(), loginContext.getActorId());
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
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("mobile.home");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		logger.debug("-----------------------end mobile home-----------------------------");

		return new ModelAndView("/mobile/home", modelMap);
	}

	@RequestMapping("/setTheme")
	@ResponseBody
	public byte[] setTheme(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		UserTheme userTheme = new UserTheme();
		userTheme.setId(RequestUtils.getInteger(request, "userThemeId", null));
		userTheme.setBackground(RequestUtils.getString(request, "background", ""));
		userTheme.setBackgroundType(RequestUtils.getString(request, "backgroundType", ""));
		userTheme.setCreateBy(RequestUtils.getActorId(request));
		userTheme.setCreateDate(new Date());
		userTheme.setLayoutModel(RequestUtils.getString(request, "layoutModel", ""));
		userTheme.setThemeStyle(RequestUtils.getString(request, "themeStyle", ""));
		if (userTheme.getId() != null) {
			UserTheme oldUserTheme = userThemeService.getUserTheme(userTheme.getId());
			userTheme.setCourse(oldUserTheme.getLayoutModel().equals(userTheme.getLayoutModel()) ? 1 : 0);
		}
		try {
			userThemeService.save(userTheme);
			RequestUtils.setTheme(request, response, userTheme.getThemeStyle());
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("设置主题错误" + e.getMessage());
		}
		return ResponseUtils.responseJsonResult(false);
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
	public void setSysApplicationService(SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
	}

	@javax.annotation.Resource
	public void setUserPortalService(UserPortalService userPortalService) {
		this.userPortalService = userPortalService;
	}

	@javax.annotation.Resource
	public void setThemeService(ThemeService themeService) {
		this.themeService = themeService;
	}

	@javax.annotation.Resource
	public void setUserThemeService(UserThemeService userThemeService) {
		this.userThemeService = userThemeService;
	}

}