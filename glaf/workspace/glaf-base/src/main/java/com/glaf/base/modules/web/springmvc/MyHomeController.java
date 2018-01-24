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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.glaf.core.base.TreeModel;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.security.LoginContext;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.Hex;
import com.glaf.core.util.RSA;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.SignUtils;
import com.glaf.core.web.callback.CallbackProperties;
import com.glaf.core.web.callback.LoginCallback;

import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.LoginModule;
import com.glaf.base.modules.sys.model.LoginToken;
import com.glaf.base.modules.sys.query.LoginModuleQuery;
import com.glaf.base.modules.sys.service.LoginModuleService;
import com.glaf.base.modules.sys.service.LoginTokenService;
import com.glaf.base.modules.sys.service.SysApplicationService;

import com.glaf.ui.ThemeConstants;
import com.glaf.ui.model.UserTheme;
import com.glaf.ui.query.UserThemeQuery;
import com.glaf.ui.service.LayoutService;
import com.glaf.ui.service.PanelService;
import com.glaf.ui.service.ThemeService;
import com.glaf.ui.service.UserPortalService;
import com.glaf.ui.service.UserThemeService;

@Controller("/my/home")
@RequestMapping("/my/home")
public class MyHomeController {
	protected final static Log logger = LogFactory.getLog(MyHomeController.class);

	protected LayoutService layoutService;

	protected PanelService panelService;

	protected SysApplicationService sysApplicationService;

	protected UserPortalService userPortalService;

	protected ThemeService themeService;

	protected UserThemeService userThemeService;

	protected LoginTokenService loginTokenService;

	protected LoginModuleService loginModuleService;

	@RequestMapping("/colseCourse")
	@ResponseBody
	public byte[] colseCourse(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		UserTheme userTheme = new UserTheme();
		Integer id = RequestUtils.getInteger(request, "userThemeId", null);
		if (id != null) {
			try {
				userTheme.setId(id);
				userTheme.setCourse(1);
				userThemeService.save(userTheme);
				return ResponseUtils.responseJsonResult(true);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("关闭教程错误" + e.getMessage());
			}
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping("/content")
	public ModelAndView content(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		// 加载菜单标识
		String menu = request.getParameter("menu");
		if (!StringUtils.isEmpty(menu) && menu.equals("true")) {
			long appId = RequestUtils.getLong(request, "appId", 3);
			// 特殊菜单
			TreeModel root = sysApplicationService.getTreeModelByAppId(appId);
			List<TreeModel> treeNodes = null;
			if (root != null) {
				treeNodes = sysApplicationService.getTreeModels(root.getId(), loginContext.getActorId());
				if (treeNodes != null) {
					Collections.sort(treeNodes);
				}

			} else {
				treeNodes = sysApplicationService.getTreeModels(3, loginContext.getActorId());
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
				logger.debug("##########################Kendo#############################");
				// logger.debug("treeNodes:" + treeNodes);
				// com.glaf.core.util.FileUtils.save("c:/temp/menu.txt",
				// treeNodes.toString().getBytes());

				Collections.sort(treeNodes);

				TreeHelper treeHelper = new TreeHelper();
				JSONObject treeJson = treeHelper.getTreeJson(root, treeNodes);
				modelMap.put("treeJson", treeJson);
				modelMap.put("json", treeJson.toJSONString());
				// logger.debug(treeJson.toJSONString());
				// logger.debug(treeJson.getJSONArray("children").toJSONString());
				// com.glaf.core.util.FileUtils.save("c:/temp/menu.json",
				// treeJson.toJSONString().getBytes());
				request.setAttribute("scripts", treeJson.toJSONString());
			}
		}
		String style = request.getParameter("style");
		String url = "/modules/portal/default/content";
		if (style != null && style.trim().length() > 0) {
			url = "/modules/portal/" + style + "/content";
		}
		return new ModelAndView(url, modelMap);
	}

	protected void fill(JSONObject jsonObject, StringBuilder buffer) {
		String text = jsonObject.getString("text");
		if (text != null && jsonObject.getString("id") != null) {
			JSONArray children = jsonObject.getJSONArray("children");
			if (children != null && !children.isEmpty()) {
				buffer.append("\n  <li iconCls=\"icon-base\"><span>").append(text).append("</span>");
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
				buffer.append("\n    <li iconCls=\"icon-gears\"><a href=\"#\" ").append(" onclick=\"openTabs('")
						.append(text).append("','").append(jsonObject.getString("id")).append("');\">").append(text)
						.append("</a>");
				buffer.append("\n    </li>");
			}
		}
	}

	@RequestMapping("/footer")
	public ModelAndView footer(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String style = request.getParameter("style");
		String url = "/modules/portal/default/footer";
		if (style != null && style.trim().length() > 0) {
			url = "/modules/portal/" + style + "/footer";
		}
		String actorId = RequestUtils.getActorId(request);

		Properties props = CallbackProperties.getProperties();
		if (props != null && props.keys().hasMoreElements()) {
			Enumeration<?> e = props.keys();
			while (e.hasMoreElements()) {
				String className = (String) e.nextElement();
				try {
					Object obj = ClassUtils.instantiateObject(className);
					if (obj instanceof LoginCallback) {
						LoginCallback callback = (LoginCallback) obj;
						logger.debug("-------------------------callback--------------------");
						logger.debug(callback.getClass().getName());
						callback.afterLogin(actorId, request, response);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error(ex);
				}
			}
		}
		try {
			LoginModuleQuery query = new LoginModuleQuery();
			query.locked(0);
			List<LoginModule> list = loginModuleService.list(query);
			if (list != null && !list.isEmpty()) {
				LoginToken token = null;
				String userId = actorId;
				List<String> loginUrls = new ArrayList<String>();
				for (LoginModule m : list) {
					if (StringUtils.isNotEmpty(m.getLoginUserId())) {
						userId = m.getLoginUserId();
					}
					if (StringUtils.equals(m.getType(), "client_rsa") && m.getPeerPublicKey() != null) {
						/**
						 * 用服务器端的公钥加密登录数据
						 */
						String loginUrl = m.getUrl() + "/website/rsa_login?systemCode=" + m.getSystemCode() + "&userId="
								+ Hex.byte2hex(RSA.encryptByPublicKey(userId.getBytes("UTF-8"), m.getPeerPublicKey()))
								+ "&timestamp="
								+ Hex.byte2hex(RSA.encryptByPublicKey(
										String.valueOf(System.currentTimeMillis()).getBytes("UTF-8"),
										m.getPeerPublicKey()));
						loginUrls.add(loginUrl);
					}
					if (StringUtils.equals(m.getType(), "client") && StringUtils.isNotEmpty(m.getLoginUrl())) {
						loginUrls.add(m.getLoginUrl());
					}
					List<LoginToken> tokens = loginTokenService.getLoginTokenByUserId(userId);
					if (tokens != null && !tokens.isEmpty()) {
						logger.debug("tokens size:" + tokens.size());
						token = null;
						for (LoginToken tk : tokens) {
							if (StringUtils.isNotEmpty(tk.getLoginModuleId())
									&& StringUtils.equals(tk.getLoginModuleId(), m.getId())) {
								token = tk;
								break;
							}
						}
						if (token != null && StringUtils.isNotEmpty(token.getToken())) {
							String tk = token.getToken();
							String timestamp = String.valueOf(token.getTimeMillis());
							String nonce = token.getNonce();
							String signature = SignUtils.getSignature(tk, timestamp, nonce);
							String loginUrl = m.getUrl() + "/website/token_login?timestamp=" + timestamp + "&nonce="
									+ nonce + "&signature=" + signature;
							loginUrls.add(loginUrl);
						}
					}
				}
				request.setAttribute("loginUrls", loginUrls);
			}
		} catch (java.lang.Throwable ex) {
			ex.printStackTrace();
		}
		return new ModelAndView(url, modelMap);
	}

	@RequestMapping
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (loginContext != null && loginContext.getUser() != null) {
			modelMap.put("username", loginContext.getUser().getName());
			if (SystemConfig.getBoolean("pwdUpdateEnabled", false)) {
				modelMap.put("pwdUpdateFlag", loginContext.getUser().getPwdUpdateFlag());
			} else {
				modelMap.put("pwdUpdateFlag", "1");// 设置为1，不再弹出修改密码的窗口。
			}
		} else {
			return new ModelAndView("redirect:/mx/login");
		}
		RequestUtils.setRequestParameterToAttribute(request);
		if (StringUtils.isNotEmpty(request.getParameter("systemName"))) {
			Environment.setCurrentSystemName(request.getParameter("systemName"));
		} else {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		}
		String userId = RequestUtils.getActorId(request);
		logger.debug("-----------------------home--------------------------------");
		logger.debug("currentSystemName:" + Environment.getCurrentSystemName());

		long appId = RequestUtils.getLong(request, "appId", 3);

		//
		UserThemeQuery userThemeQuery = new UserThemeQuery();
		userThemeQuery.setActorId(userId);
		List<UserTheme> userThemes = null;
		try {
			userThemes = userThemeService.list(userThemeQuery);
		} catch (Exception e) {
			// e.printStackTrace();
			// logger.error("找不到对应主题：" + e.getMessage());
		}
		UserTheme userTheme = null;
		if (userThemes != null && userThemes.size() > 0) {
			userTheme = userThemes.get(0);
		} else {
			userTheme = new UserTheme();
			userTheme.setActorId(userId);
			userTheme.setBackground(ThemeConstants.BACKGROUND);
			userTheme.setBackgroundType(ThemeConstants.BACKGROUND_TYPE);
			userTheme.setCourse(0);
			userTheme.setHomeThemeStyle(ThemeConstants.HOME_THEME_STYLE);
			userTheme.setCreateBy(userId);
			userTheme.setCreateDate(new Date());
			userTheme.setLayoutModel(ThemeConstants.LAYOUT_MODEL);
			userTheme.setThemeStyle(ThemeConstants.THEME_STYLE);
			try {
				userThemeService.save(userTheme);
			} catch (Exception e) {
				// e.printStackTrace();
				logger.error("保存主题错误：" + e.getMessage());
			}
		}
		request.setAttribute("userTheme", userTheme);
		RequestUtils.setTheme(request, response, userTheme.getLayoutModel(), userTheme.getThemeStyle(),
				userTheme.getHomeThemeStyle());
		BaseDataManager bm = BaseDataManager.getInstance();
		String themeKey = String.join("|", userTheme.getLayoutModel(), userTheme.getHomeThemeStyle(),
				userTheme.getThemeStyle());
		com.glaf.base.modules.sys.model.BaseDataInfo info = bm.getBaseData(themeKey, "dict_theme");
		request.getSession().setAttribute("theme", info);
		// 从字典获取系统主题定义
		List<BaseDataInfo> themeList = bm.getBaseData("dict_theme");
		modelMap.put("themeList", themeList);
		if (userTheme.getLayoutModel().startsWith("home_kendo")) {
			TreeModel root = sysApplicationService.getTreeModelByAppId(appId);
			List<TreeModel> treeNodes = null;
			if (root != null) {
				treeNodes = sysApplicationService.getTreeModels(root.getId(), loginContext.getActorId());
				if (treeNodes != null) {
					Collections.sort(treeNodes);
				}

			} else {
				treeNodes = sysApplicationService.getTreeModels(3, loginContext.getActorId());
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
				logger.debug("treeNodes:" + treeNodes.size());

				TreeHelper treeHelper = new TreeHelper();
				JSONObject treeJson = treeHelper.getTreeJson(root, treeNodes);
				modelMap.put("treeJson", treeJson);
				modelMap.put("json", treeJson.toJSONString());
				// logger.debug(treeJson.toJSONString());
				request.setAttribute("scripts", treeJson.toJSONString());
			}
		} else if (userTheme.getLayoutModel().startsWith("larry_home")){
			TreeModel root = sysApplicationService.getTreeModelByAppId(appId);
			List<TreeModel> treeNodes = null;
			if (root != null) {
				treeNodes = sysApplicationService.getTreeModels(root.getId(), loginContext.getActorId());
				if (treeNodes != null) {
					Collections.sort(treeNodes);
				}

			} else {
				treeNodes = sysApplicationService.getTreeModels(3, loginContext.getActorId());
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
				logger.debug("treeNodes:" + treeNodes.size());

				TreeHelper treeHelper = new TreeHelper();
				JSONObject treeJson = treeHelper.getTreeJsonByLarry(root, treeNodes);
				modelMap.put("treeJson", treeJson);
				modelMap.put("json", treeJson.toJSONString());
				// logger.debug(treeJson.toJSONString());
				request.setAttribute("scripts", treeJson.toJSONString());
			}
		} else if (userTheme.getLayoutModel().startsWith("kendo") || userTheme.getLayoutModel().startsWith("flat")
				|| userTheme.getLayoutModel().startsWith("responsive")) {
			// 特殊菜单
			TreeModel root = sysApplicationService.getTreeModelByAppId(appId);
			List<TreeModel> treeNodes = null;
			if (root != null) {
				treeNodes = sysApplicationService.getTreeModels(root.getId(), loginContext.getActorId());
				if (treeNodes != null) {
					Collections.sort(treeNodes);
				}

			} else {
				treeNodes = sysApplicationService.getTreeModels(3, loginContext.getActorId());
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
				logger.debug("##########################Kendo#############################");
				// logger.debug("treeNodes:" + treeNodes);
				// com.glaf.core.util.FileUtils.save("c:/temp/menu.txt",
				// treeNodes.toString().getBytes());

				Collections.sort(treeNodes);

				TreeHelper treeHelper = new TreeHelper();
				JSONObject treeJson = treeHelper.getTreeJson(root, treeNodes);
				modelMap.put("treeJson", treeJson);
				modelMap.put("json", treeJson.toJSONString());
				// logger.debug(treeJson.toJSONString());
				// logger.debug(treeJson.getJSONArray("children").toJSONString());
				// com.glaf.core.util.FileUtils.save("c:/temp/menu.json",
				// treeJson.toJSONString().getBytes());
				request.setAttribute("scripts", treeJson.toJSONString());
			}
		} else if (userTheme.getLayoutModel().startsWith("home")) {
			String scripts = "";
			try {
				org.json.JSONArray array = sysApplicationService.getUserMenu(appId, userId);
				scripts = array.toString('\n');
				logger.debug("--------------scripts------------------");
				request.setAttribute("scripts", scripts);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		} else if (userTheme.getLayoutModel().startsWith("main")) {
			TreeModel root = sysApplicationService.getTreeModelByAppId(appId);
			List<TreeModel> treeNodes = null;
			if (root != null) {
				treeNodes = sysApplicationService.getTreeModels(root.getId(), loginContext.getActorId());
				if (treeNodes != null) {
					Collections.sort(treeNodes);
				}

			} else {
				treeNodes = sysApplicationService.getTreeModels(3, loginContext.getActorId());
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
				logger.debug("###########################main tree############################");
				logger.debug("treeNodes:" + treeNodes.size());

				TreeHelper treeHelper = new TreeHelper();
				JSONObject treeJson = treeHelper.getTreeJson(root, treeNodes);
				modelMap.put("treeJson", treeJson);
				modelMap.put("json", treeJson.toJSONString());
				// logger.debug(treeJson.toJSONString());

				StringBuilder buffer = new StringBuilder(500);
				String text = treeJson.getString("text");
				if (text != null) {
					buffer.append("\n <li iconCls=\"icon-root\"><span>").append(text).append("</span>");
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

			String x_view = ViewProperties.getString("my_workspace.main");
			if (StringUtils.isNotEmpty(x_view)) {
				return new ModelAndView(x_view, modelMap);
			}
		}
		logger.debug("-----------------------end home-----------------------------");

		return new ModelAndView("/modules/portal/" + userTheme.getLayoutModel(), modelMap);
	}

	@RequestMapping("/left")
	public ModelAndView left(HttpServletRequest request, ModelMap modelMap) {
		String style = request.getParameter("style");
		String url = "/modules/portal/default/left";
		if (style != null && style.trim().length() > 0) {
			url = "/modules/portal/" + style + "/left";
		}
		return new ModelAndView(url, modelMap);
	}

	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (loginContext == null) {
			return new ModelAndView("redirect:/mx/login");
		}
		// String userId = RequestUtils.getActorId(request);
		logger.debug("-----------------------home--------------------------------");
		logger.debug("currentSystemName:" + Environment.getCurrentSystemName());
		// 加载菜单标识
		String menu = request.getParameter("menu");
		if (!StringUtils.isEmpty(menu) && menu.equals("true")) {
			long appId = RequestUtils.getLong(request, "appId", 3);
			// 特殊菜单
			TreeModel root = sysApplicationService.getTreeModelByAppId(appId);
			List<TreeModel> treeNodes = null;
			if (root != null) {
				treeNodes = sysApplicationService.getTreeModels(root.getId(), loginContext.getActorId());
				if (treeNodes != null) {
					Collections.sort(treeNodes);
				}

			} else {
				treeNodes = sysApplicationService.getTreeModels(3, loginContext.getActorId());
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
				logger.debug("##########################Kendo#############################");
				// logger.debug("treeNodes:" + treeNodes);
				// com.glaf.core.util.FileUtils.save("c:/temp/menu.txt",
				// treeNodes.toString().getBytes());

				Collections.sort(treeNodes);

				TreeHelper treeHelper = new TreeHelper();
				JSONObject treeJson = treeHelper.getTreeJson(root, treeNodes);
				modelMap.put("treeJson", treeJson);
				modelMap.put("json", treeJson.toJSONString());
				// logger.debug(treeJson.toJSONString());
				// logger.debug(treeJson.getJSONArray("children").toJSONString());
				// com.glaf.core.util.FileUtils.save("c:/temp/menu.json",
				// treeJson.toJSONString().getBytes());
				request.setAttribute("scripts", treeJson.toJSONString());
			}
		}
		String style = request.getParameter("style");
		String url = "/modules/portal/default/main";
		if (style != null && style.trim().length() > 0) {
			url = "/modules/portal/" + style + "/main";
		}
		return new ModelAndView(url, modelMap);
	}

	@javax.annotation.Resource
	public void setLayoutService(LayoutService layoutService) {
		this.layoutService = layoutService;
	}

	@javax.annotation.Resource
	public void setLoginModuleService(LoginModuleService loginModuleService) {
		this.loginModuleService = loginModuleService;
	}

	@javax.annotation.Resource
	public void setLoginTokenService(LoginTokenService loginTokenService) {
		this.loginTokenService = loginTokenService;
	}

	@javax.annotation.Resource
	public void setPanelService(PanelService panelService) {
		this.panelService = panelService;
	}

	@javax.annotation.Resource
	public void setSysApplicationService(SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
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
		userTheme.setHomeThemeStyle(RequestUtils.getString(request, "homeThemeStyle", ""));
		if (userTheme.getId() != null) {
			UserTheme oldUserTheme = userThemeService.getUserTheme(userTheme.getId());
			userTheme.setCourse(oldUserTheme.getLayoutModel().equals(userTheme.getLayoutModel()) ? 1 : 0);
		}
		try {
			userThemeService.save(userTheme);
			RequestUtils.setTheme(request, response, userTheme.getLayoutModel(), userTheme.getThemeStyle(),
					userTheme.getHomeThemeStyle());
			BaseDataManager bm = BaseDataManager.getInstance();
			String themeKey = String.join("|", userTheme.getLayoutModel(), userTheme.getThemeStyle(),
					userTheme.getHomeThemeStyle());
			com.glaf.base.modules.sys.model.BaseDataInfo info = bm.getBaseData(themeKey, "dict_theme");
			request.getSession().setAttribute("theme", info);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("设置主题错误" + e.getMessage());
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setThemeService(ThemeService themeService) {
		this.themeService = themeService;
	}

	@javax.annotation.Resource
	public void setUserPortalService(UserPortalService userPortalService) {
		this.userPortalService = userPortalService;
	}

	@javax.annotation.Resource
	public void setUserThemeService(UserThemeService userThemeService) {
		this.userThemeService = userThemeService;
	}

	@RequestMapping("/top")
	public ModelAndView top(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (loginContext != null && loginContext.getUser() != null) {
			modelMap.put("username", loginContext.getUser().getName());
		}
		RequestUtils.setRequestParameterToAttribute(request);
		if (StringUtils.isNotEmpty(request.getParameter("systemName"))) {
			Environment.setCurrentSystemName(request.getParameter("systemName"));
		} else {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		}
		// String userId = RequestUtils.getActorId(request);
		logger.debug("-----------------------home--------------------------------");
		logger.debug("currentSystemName:" + Environment.getCurrentSystemName());
		// 加载菜单标识
		String menu = request.getParameter("menu");
		if (!StringUtils.isEmpty(menu) && menu.equals("true")) {
			long appId = RequestUtils.getLong(request, "appId", 3);
			//
			UserThemeQuery userThemeQuery = new UserThemeQuery();
			String userId = RequestUtils.getActorId(request);
			userThemeQuery.setActorId(userId);
			List<UserTheme> userThemes = null;
			try {
				userThemes = userThemeService.list(userThemeQuery);
			} catch (Exception e) {
				// e.printStackTrace();
				// logger.error("找不到对应主题：" + e.getMessage());
			}
			UserTheme userTheme = new UserTheme();
			if (userThemes != null && userThemes.size() > 0) {
				userTheme = userThemes.get(0);
			}
			request.setAttribute("userTheme", userTheme);
			// 特殊菜单
			TreeModel root = sysApplicationService.getTreeModelByAppId(appId);
			List<TreeModel> treeNodes = null;
			if (root != null) {
				treeNodes = sysApplicationService.getTreeModels(root.getId(), loginContext.getActorId());
				if (treeNodes != null) {
					Collections.sort(treeNodes);
				}

			} else {
				treeNodes = sysApplicationService.getTreeModels(3, loginContext.getActorId());
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
				logger.debug("##########################Kendo#############################");
				// logger.debug("treeNodes:" + treeNodes);
				// com.glaf.core.util.FileUtils.save("c:/temp/menu.txt",
				// treeNodes.toString().getBytes());

				Collections.sort(treeNodes);

				TreeHelper treeHelper = new TreeHelper();
				JSONObject treeJson = treeHelper.getTreeJson(root, treeNodes);
				modelMap.put("treeJson", treeJson);
				modelMap.put("json", treeJson.toJSONString());
				// logger.debug(treeJson.toJSONString());
				// logger.debug(treeJson.getJSONArray("children").toJSONString());
				// com.glaf.core.util.FileUtils.save("c:/temp/menu.json",
				// treeJson.toJSONString().getBytes());
				request.setAttribute("scripts", treeJson.toJSONString());
			}
		}
		String style = request.getParameter("style");
		String url = "/modules/portal/default/top";
		if (style != null && style.trim().length() > 0) {
			url = "/modules/portal/" + style + "/top";
		}
		return new ModelAndView(url, modelMap);
	}

}