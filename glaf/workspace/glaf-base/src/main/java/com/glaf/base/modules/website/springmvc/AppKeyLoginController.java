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

import com.glaf.base.callback.LoginCallbackThread;
import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.online.domain.UserOnline;
import com.glaf.base.online.service.UserOnlineLogService;
import com.glaf.base.online.service.UserOnlineService;
import com.glaf.base.utils.ContextUtil;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.SystemProperty;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.web.callback.CallbackProperties;
import com.glaf.core.web.callback.LoginCallback;

//https://127.0.0.1:8443/glaf/website/apploginv2?appId=f34ad12168c741b5a4f75d98ba57d558&appSecret=69eceba91937432398bf43437d08900b
@Controller("/apploginv2")
@RequestMapping("/apploginv2")
public class AppKeyLoginController {
	protected static final Log logger = LogFactory.getLog(AppKeyLoginController.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected SysUserService sysUserService;

	protected UserOnlineService userOnlineService;

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
		String actorId = RequestUtils.getActorId(request);
		if (StringUtils.isNotEmpty(actorId)) {
			String redirectUrl = request.getParameter("redirectUrl");
			if (StringUtils.isNotEmpty(redirectUrl)) {
				try {
					response.sendRedirect(redirectUrl);
					return null;
				} catch (IOException e) {
				}
			}

			String redirectUrl_enc = request.getParameter("redirectUrl_enc");
			if (StringUtils.isNotEmpty(redirectUrl_enc)) {
				try {
					response.sendRedirect(RequestUtils.decodeURL(redirectUrl_enc));
					return null;
				} catch (IOException e) {
				}
			}
		}
		logger.debug("params:" + RequestUtils.getParameterMap(request));
		if (SystemConfig.getBoolean("login_as_secret_key", false)) {
			String appId = ParamUtil.getParameter(request, "appId");
			String appSecret = ParamUtil.getParameter(request, "appSecret");
			logger.debug("ip:" + RequestUtils.getIPAddress(request));
			logger.debug("appId:" + appId);
			logger.debug("appSecret:" + appSecret);

			SysUser bean = null;
			String userId = null;
			if (StringUtils.isNotEmpty(appId) && StringUtils.isNotEmpty(appSecret)) {

				bean = sysUserService.getSysUserByAppId(appId);
				if (bean != null && !StringUtils.equals(bean.getAppSecret(), appSecret)) {
					bean = null;
				}

				if (bean != null && !StringUtils.equals(bean.getSecretLoginFlag(), "Y")) {
					bean = null;
				}

				/**
				 * 系统管理员不允许以这种方式登录
				 */
				if (bean != null && bean.isSystemAdministrator()) {
					bean = null;
				}

				if (bean != null) {
					userId = bean.getActorId();
				}

				UserOnlineLogService userOnlineLogService = ContextFactory.getBean("userOnlineLogService");
				// 如果每天登录次数超过设置值，设置用户对象为空，防止恶意操作
				if (userOnlineLogService.getLoginCount(userId) > conf.getInt("limit.loginCount", 5000)) {
					bean = null;
				}

				if (bean != null) {
					String ipAddr = RequestUtils.getIPAddress(request);
					SystemProperty p = SystemConfig.getProperty("login_limit");
					if (!(StringUtils.equals(bean.getAccount(), "root")
							|| StringUtils.equals(bean.getAccount(), "admin"))) {
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
								if (userOnline.getCheckDateMs() != null && System.currentTimeMillis()
										- userOnline.getCheckDateMs() > timeoutSeconds * 1000) {
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
										LoginCallbackThread command = new LoginCallbackThread(callback,
												bean.getAccount(), request, response);
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

					String redirectUrl = request.getParameter("redirectUrl");
					if (StringUtils.isNotEmpty(redirectUrl)) {
						try {
							response.sendRedirect(redirectUrl);
							return null;
						} catch (IOException e) {
						}
					}

					String redirectUrl_enc = request.getParameter("redirectUrl_enc");
					if (StringUtils.isNotEmpty(redirectUrl_enc)) {
						try {
							response.sendRedirect(RequestUtils.decodeURL(redirectUrl_enc));
							return null;
						} catch (IOException e) {
						}
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
