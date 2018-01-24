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

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import com.glaf.core.config.Configuration;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.domain.SystemProperty;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.web.callback.CallbackProperties;
import com.glaf.core.web.callback.LoginCallback;

import com.glaf.base.callback.LoginCallbackThread;
import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.online.domain.UserOnline;
import com.glaf.base.online.service.UserOnlineLogService;
import com.glaf.base.online.service.UserOnlineService;
import com.glaf.base.utils.ContextUtil;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RestClient;

@Controller("/applogin")
@RequestMapping("/applogin")
public class AppLoginController {
	protected static final Log logger = LogFactory.getLog(AppLoginController.class);

	private static Configuration conf = BaseConfiguration.create();

	private AuthorizeService authorizeService;

	private SysUserService sysUserService;

	private UserOnlineService userOnlineService;

	private UserOnlineLogService userOnlineLogService;

	/**
	 * 3DES解密
	 * 
	 * @param input
	 *            需要解密的字节流
	 * @param secretKey
	 *            密钥
	 * @param secretIv
	 *            向量
	 * @return
	 */
	public byte[] decrypt3DES(String input, String secretKey, String secretIv) {
		if (input == null || secretKey == null || secretIv == null) {
			return null;
		}
		byte[] res = null;
		try {
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			Key deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS7Padding");
			IvParameterSpec ips = new IvParameterSpec(secretIv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
			byte[] inputbyte = Base64.decodeBase64(input);
			res = cipher.doFinal(inputbyte);
			return res;
		} catch (Exception ex) {
			throw new RuntimeException("3DES CBC解密过程出现错误！", ex);
		}
	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		logger.debug("---------------------doLogin--------------------");
		if (!SystemConfig.getBoolean("login_as_secret_key")) {
			logger.debug("系统未开启密锁登录，请在系统参数设置中开启！");
			return this.prepareLogin(request, response, modelMap);
		}
		String loginId = ParamUtil.getParameter(request, "loginId");
		String password = ParamUtil.getParameter(request, "passwd");
		String token = ParamUtil.getParameter(request, "token");
		logger.debug("ip:" + RequestUtils.getIPAddress(request));
		logger.debug("params:" + RequestUtils.getParameterMap(request));
		logger.debug("loginId:" + loginId);
		logger.debug("password:" + password);
		logger.debug("token:" + token);

		SysUser bean = null;
		String actorId = null;
		if (StringUtils.isNotEmpty(loginId) && StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(token)) {
			if (StringUtils.startsWith(loginId, "demo")) {
				actorId = loginId;
				bean = sysUserService.getSysUserToken(actorId);
				if (bean != null && StringUtils.equals(bean.getAppSecret(), password)
						&& StringUtils.equals(bean.getAppId(), token)) {
					logger.debug("loginId:" + loginId + " verify ok.");
				} else {
					actorId = null;
					bean = null;
				}
			} else {
				/**
				 * 进行字符串替换
				 */
				loginId = StringTools.replace(loginId, "_a", "+");
				loginId = StringTools.replace(loginId, "_b", "=");
				loginId = StringTools.replace(loginId, "_c", "/");

				password = StringTools.replace(password, "_a", "+");
				password = StringTools.replace(password, "_b", "=");
				password = StringTools.replace(password, "_c", "/");

				RestClient client = new RestClient();
				JSONObject json = client.getSecretKeyJSON(token);
				logger.debug("json:" + json.toJSONString());
				String secretIv = json.getString("secretIv");
				String secretKey = json.getString("secretKey");

				/**
				 * 使用对方提供的DES密锁解密用户Id及密码
				 */
				String userId = new String(decrypt3DES(loginId, secretKey, secretIv));
				String loginSecret = new String(decrypt3DES(password, secretKey, secretIv));
				logger.debug("3DES解密后用户名:" + userId);
				logger.debug("3DES解密后密码:" + loginSecret);

				/**
				 * 再使用我们系统自己的RSA密锁解密
				 */
				// userId = RSAUtils.decryptString(userId);
				// loginSecret = RSAUtils.decryptString(loginSecret);

				actorId = userId;
				logger.debug("userId:" + userId);
				logger.debug("loginSecret:" + loginSecret);
				// logger.debug("y:" + y);
				// 用户登陆，返回系统用户对象

				// bean = authorizeService.authorizeLoginSecret(actorId,
				// loginSecret);
				bean = authorizeService.login(userId);// 这里有安全问题，由于对方未提供修改密码功能。
				if (bean != null) {
					logger.debug(userId + " login success!");
				}
			}
		}

		// 如果每天登录次数超过设置值，设置用户对象为空，防止恶意操作
		if (userOnlineLogService.getLoginCount(actorId) > conf.getInt("limit.loginCount", 5000)) {
			bean = null;
		}

		if (bean != null) {
			String ipAddr = RequestUtils.getIPAddress(request);
			SystemProperty p = SystemConfig.getProperty("login_limit");
			if (!(StringUtils.equals(bean.getAccount(), "root") || StringUtils.equals(bean.getAccount(), "admin"))) {
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
					UserOnline userOnline = userOnlineService.getUserOnline(bean.getAccount());
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
							// logger.debug("-------------------------callback--------------------");
							// logger.debug(callback.getClass().getName());
							// callback.afterLogin(bean.getAccount(),
							// request,
							// response);
							if (StringUtils.equals(className, "com.glaf.shiro.ShiroLoginCallback")) {
								callback.afterLogin(bean.getAccount(), request, response);
							} else {
								LoginCallbackThread command = new LoginCallbackThread(callback, bean.getAccount(),
										request, response);
								com.glaf.core.util.ThreadFactory.execute(command);
								Thread.sleep(50);
							}
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

			String systemName = Environment.getCurrentSystemName();

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
				if (!bean.isSystemAdministrator()) {
					online.setLoginIP(ipAddr);
				}
				userOnlineService.login(online);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			LoginContext loginContext = IdentityFactory.getLoginContext(bean.getActorId());
			logger.debug(loginContext.toJsonObject().toJSONString());

			return new ModelAndView("/modules/main", modelMap);
		} else {
			// 处理登录不成功的情况
			if (bean == null) {
				BaseDataManager manager = BaseDataManager.getInstance();
				BaseDataInfo loginInfo = manager.getBaseData("third_loginUrl", "login_param");
				if (loginInfo != null && StringUtils.isNotEmpty(loginInfo.getValue())) {
					try {
						response.sendRedirect(loginInfo.getValue());
						return null;
					} catch (IOException e) {
					}
				}
			}
		}

		return this.prepareLogin(request, response, modelMap);
	}

	/**
	 * 准备登录
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	public ModelAndView prepareLogin(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		logger.debug("---------------------prepareLogin--------------------");
		RequestUtils.setRequestParameterToAttribute(request);

		HttpSession session = request.getSession(true);
		java.util.Random random = new java.util.Random();
		String rand = Math.abs(random.nextInt(9999)) + com.glaf.core.util.UUID32.getUUID()
				+ Math.abs(random.nextInt(9999));
		String rand2 = Math.abs(random.nextInt(9999)) + com.glaf.core.util.UUID32.getUUID()
				+ Math.abs(random.nextInt(9999));
		if (session != null) {
			rand = Base64.encodeBase64String(rand.getBytes()) + com.glaf.core.util.UUID32.getUUID();
			rand2 = Base64.encodeBase64String(rand2.getBytes()) + com.glaf.core.util.UUID32.getUUID();
			session.setAttribute("x_y", rand);
			session.setAttribute("x_z", rand2);
		}

		String view = "/modules/login";

		if (StringUtils.isNotEmpty(SystemConfig.getString("login_view"))) {
			view = SystemConfig.getString("login_view");
		}

		if (RequestUtils.isMobile(request)) {
			// 手机版登录页面
			view = "/mobile/login";
		}

		// 显示登陆页面
		return new ModelAndView(view, modelMap);
	}

	@javax.annotation.Resource
	public void setAuthorizeService(AuthorizeService authorizeService) {
		this.authorizeService = authorizeService;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@javax.annotation.Resource
	public void setUserOnlineLogService(UserOnlineLogService userOnlineLogService) {
		this.userOnlineLogService = userOnlineLogService;
	}

	@javax.annotation.Resource
	public void setUserOnlineService(UserOnlineService userOnlineService) {
		this.userOnlineService = userOnlineService;
	}
}
