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

import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;

import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.web.callback.CallbackProperties;
import com.glaf.core.web.callback.LoginCallback;
import com.glaf.base.callback.LoginCallbackThread;

import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.online.domain.UserOnline;
import com.glaf.base.online.service.UserOnlineService;
import com.glaf.base.utils.ContextUtil;
import com.glaf.base.utils.ParamUtil;

@Controller("/demologin")
@RequestMapping("/demologin")
public class DemoLoginController {
	protected static final Log logger = LogFactory.getLog(DemoLoginController.class);

	private SysUserService sysUserService;

	private UserOnlineService userOnlineService;

	/**
	 * 演示用户登录
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		logger.debug("---------------------doLogin--------------------");
		String token = ParamUtil.getParameter(request, "token");
		if (StringUtils.isNotEmpty(SystemConfig.getString("login_token"))
				&& StringUtils.equals(SystemConfig.getString("login_token"), token)
				&& SystemConfig.getBoolean("login_as_secret_key", false)) {
			String userId = ParamUtil.getParameter(request, "userId");
			logger.debug("ip:" + RequestUtils.getIPAddress(request));
			logger.debug("params:" + RequestUtils.getParameterMap(request));
			logger.debug("userId:" + userId);
			logger.debug("token:" + token);

			SysUser bean = null;
			// logger.debug("y:" + y);
			// 用户登陆，返回系统用户对象
			AuthorizeService authorizeService = ContextFactory.getBean("authorizeService");
			bean = authorizeService.login(userId);
			if (bean != null && StringUtils.equals(bean.getSecretLoginFlag(), "Y")) {
				logger.debug(userId + " login success!");
			} else {
				bean = null;
			}

			/**
			 * 系统管理员不允许以这种方式登录
			 */
			if (bean != null && bean.isSystemAdministrator()) {
				bean = null;
			}

			if (bean != null) {

				// HttpSession session = request.getSession(true);// 重写Session

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
								// callback.afterLogin(bean.getAccount(), request,
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
				bean.setLastLoginIP(RequestUtils.getIPAddress(request));
				bean.setLockLoginTime(null);
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
						online.setLoginIP(RequestUtils.getIPAddress(request));
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

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@javax.annotation.Resource
	public void setUserOnlineService(UserOnlineService userOnlineService) {
		this.userOnlineService = userOnlineService;
	}
}
