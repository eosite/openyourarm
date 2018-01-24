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
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.domain.SystemProperty;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.Constants;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.web.callback.CallbackProperties;
import com.glaf.core.web.callback.LoginCallback;

import com.glaf.base.handler.LoginHandler;
import com.glaf.base.handler.MQLoginHandler;
import com.glaf.base.handler.PasswordLoginHandler;
import com.glaf.base.handler.SpecialUrlLoginHandler;
import com.glaf.base.handler.WechatLoginHandler;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.online.domain.UserOnline;
import com.glaf.base.online.service.UserOnlineService;
import com.glaf.base.utils.ContextUtil;
import com.glaf.base.utils.ParamUtil;

@Controller("/authentication")
@RequestMapping("/authentication")
public class AuthenticationController {
	private static final Log logger = LogFactory.getLog(AuthenticationController.class);

	private SysUserService sysUserService;

	private UserOnlineService userOnlineService;

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping
	public byte[] authorized(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.debug("---------------------authorized--------------------");
		RequestUtils.setRequestParameterToAttribute(request);
		JSONObject jsonObject = new JSONObject();

		String systemName = ParamUtil.getParameter(request, Constants.SYSTEM_NAME);
		if (StringUtils.isNotEmpty(systemName)) {
			Environment.setCurrentSystemName(systemName);
		} else {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		}

		logger.debug("currentSystemName:" + Environment.getCurrentSystemName());

		// 获取参数
		String account = ParamUtil.getParameter(request, "x");
		String password = ParamUtil.getParameter(request, "y");

		String userId = ParamUtil.getParameter(request, "userId");
		String token = ParamUtil.getParameter(request, "token");
		String login_json = request.getParameter("login_json");
		String login_params = request.getParameter("login_params");

		SysUser bean = null;
		if (StringUtils.isNotEmpty(account) && StringUtils.isNotEmpty(password)) {
			HttpSession session = request.getSession(false);
			if (session == null) {
				jsonObject.put("msg", "会话已经过期，请重新登录。");
				jsonObject.put("status", 400);
				return jsonObject.toJSONString().getBytes("UTF-8");
			}
			String rand = (String) session.getAttribute("x_y");
			String rand2 = (String) session.getAttribute("x_z");
			if (StringUtils.isEmpty(rand) && StringUtils.isEmpty(rand2)) {
				jsonObject.put("msg", "登录参数不正确。");
				jsonObject.put("status", 300);
				return jsonObject.toJSONString().getBytes("UTF-8");
			}
			LoginHandler handler = new PasswordLoginHandler();
			bean = handler.doLogin(request, response);
		} else if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(token)) {
			LoginHandler handler = new MQLoginHandler();
			bean = handler.doLogin(request, response);
		} else if (StringUtils.isNotEmpty(login_json)) {
			LoginHandler handler = new SpecialUrlLoginHandler();
			bean = handler.doLogin(request, response);
		} else if (StringUtils.isNotEmpty(login_params)) {
			LoginHandler handler = new WechatLoginHandler();
			bean = handler.doLogin(request, response);
		}

		if (bean == null) {
			// 用户对象为空或失效，显示错误信息
			jsonObject.put("msg", "用户名或密码不正确。");
			jsonObject.put("status", 500);
			return jsonObject.toJSONString().getBytes("UTF-8");
		}
		String ipAddr = RequestUtils.getIPAddress(request);
		SystemProperty p = SystemConfig.getProperty("login_limit");
		if (!(StringUtils.equals(account, "root") || StringUtils.equals(account, "admin"))) {
			logger.debug("#################check login limit#####################");

			SystemProperty pt = SystemConfig.getProperty("login_time_check");
			int timeoutSeconds = 300;

			if (pt != null && pt.getValue() != null && StringUtils.isNumeric(pt.getValue())) {
				timeoutSeconds = Integer.parseInt(pt.getValue());
			}
			if (timeoutSeconds < 300) {
				timeoutSeconds = 300;
			}
			if (timeoutSeconds > 3600) {
				timeoutSeconds = 3600;
			}

			/**
			 * 检测是否限制一个用户只能在一个地方登录
			 */
			if (p != null && StringUtils.equals(p.getValue(), "true")) {
				logger.debug("#################3#########################");
				String loginIP = null;
				UserOnline userOnline = userOnlineService.getUserOnline(account);
				logger.debug("userOnline:" + userOnline);
				boolean timeout = false;
				if (userOnline != null) {
					loginIP = userOnline.getLoginIP();
					if (userOnline.getCheckDateMs() != null
							&& System.currentTimeMillis() - userOnline.getCheckDateMs() > timeoutSeconds * 1000) {
						timeout = true;// 超时，说明登录已经过期
					}
					if (userOnline.getLoginDate() != null && System.currentTimeMillis()
							- userOnline.getLoginDate().getTime() > timeoutSeconds * 1000) {
						timeout = true;// 超时，说明登录已经过期
					}
				}
				logger.info("timeout:" + timeout);
				logger.info("login IP:" + loginIP);
				if (!timeout) {// 超时，说明登录已经过期，不用判断是否已经登录了
					if (loginIP != null && !(StringUtils.equals(ipAddr, loginIP))) {// 用户已在其他机器登陆
						logger.debug("用户已经在其他地方登录。");
						jsonObject.put("msg", "用户已经在其他地方登录。");
						jsonObject.put("status", 201);
						return jsonObject.toJSONString().getBytes("UTF-8");
					}
				}
			}
		}

		HttpSession session = request.getSession(true);// 重写Session

		Properties props = CallbackProperties.getProperties();
		if (props != null && props.keys().hasMoreElements()) {
			Enumeration<?> e = props.keys();
			while (e.hasMoreElements()) {
				String className = (String) e.nextElement();
				try {
					Object obj = ClassUtils.instantiateObject(className);
					if (obj instanceof LoginCallback) {
						LoginCallback callback = (LoginCallback) obj;
						callback.afterLogin(bean.getAccount(), request, response);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error(ex);
				}
			}
		}

		// 登录成功，修改最近一次登录时间
		bean.setLastLoginDate(new Date());
		bean.setLastLoginIP(ipAddr);
		bean.setLockLoginTime(new Date());
		bean.setLoginRetry(0);
		sysUserService.updateUser(bean);

		ContextUtil.put(bean.getAccount(), bean);// 传入全局变量

		systemName = Environment.getCurrentSystemName();

		if (StringUtils.isEmpty(systemName)) {
			systemName = "default";
		}

		RequestUtils.setLoginUser(request, response, systemName, bean.getAccount());

		try {
			UserOnline online = new UserOnline();
			online.setActorId(bean.getActorId());
			online.setName(bean.getName());
			online.setCheckDate(new Date());
			online.setLoginDate(new Date());
			online.setLoginIP(ipAddr);
			userOnlineService.login(online);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		LoginContext loginContext = IdentityFactory.getLoginContext(bean.getActorId());
		logger.debug(loginContext.toJsonObject().toJSONString());

		jsonObject.put("msg", "用户成功登录。");
		jsonObject.put("status", 200);

		if (bean.getAccountType() == 2) {// 微信用户
			jsonObject.put("jump", "/mx/wechat/main");
		} else {
			jsonObject.put("jump", "/mx/my/home");
		}
		return jsonObject.toJSONString().getBytes("UTF-8");
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@javax.annotation.Resource
	public void setUserOnlineService(UserOnlineService userOnlineService) {
		this.userOnlineService = userOnlineService;
	}

}