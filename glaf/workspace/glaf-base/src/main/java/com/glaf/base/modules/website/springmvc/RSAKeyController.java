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
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.glaf.base.callback.LoginCallbackThread;
import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.LoginModule;
import com.glaf.base.modules.sys.model.LoginToken;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.LoginModuleService;
import com.glaf.base.modules.sys.service.LoginTokenService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.online.domain.UserOnline;
import com.glaf.base.online.service.UserOnlineLogService;
import com.glaf.base.online.service.UserOnlineService;
import com.glaf.base.utils.ContextUtil;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.domain.SystemProperty;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.Constants;
import com.glaf.core.util.Hex;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.SignUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.UUID32;
import com.glaf.core.web.callback.CallbackProperties;
import com.glaf.core.web.callback.LoginCallback;

/**
 * 客户端调用方式：<br/>
 * 1）获取令牌： <br/>
 * /website/rsaKey/getToken?syscode=project18038&userid=RSA(用户名) <br/>
 * json返回： {"token":"十六进制字符串的RSA(token)"} <br/>
 * 2）登录： <br/>
 * /website/rsaKey/doLogin?syscode=project18038&token=十六进制字符串的RSA(令牌) <br/>
 * 
 * syscode是在服务器端配置的系统编码，通过syscode获取是哪个系统的配置信息。<br/>
 *
 */
@Controller("/rsaKey")
@RequestMapping("/rsaKey")
public class RSAKeyController {

	protected static final Log logger = LogFactory.getLog(RSAKeyController.class);

	private static Configuration conf = BaseConfiguration.create();

	protected AuthorizeService authorizeService;

	protected SysUserService sysUserService;

	protected LoginModuleService loginModuleService;

	protected LoginTokenService loginTokenService;

	protected UserOnlineService userOnlineService;

	protected UserOnlineLogService userOnlineLogService;

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/doLogin")
	public ModelAndView doLogin(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		logger.debug("---------------------RSA Login--------------------");
		logger.debug("params:" + RequestUtils.getParameterMap(request));
		String syscode = request.getParameter("syscode");
		String token = ParamUtil.getParameter(request, "token");
		String clientIP = RequestUtils.getIPAddress(request);

		logger.debug("ip:" + clientIP);
		logger.debug("token:" + token);
		logger.debug("syscode:" + syscode);

		SysUser bean = null;
		String userId = null;
		LoginToken loginToken = null;

		if (StringUtils.isNotEmpty(syscode) && StringUtils.isNotEmpty(token)) {
			try {
				LoginModule loginModule = loginModuleService.getLoginModuleBySysCode(syscode);
				if (loginModule != null) {
					byte[] data = Hex.hex2byte(token);
					byte[] bytes = loginModuleService.decryptText(loginModule.getId(), data);
					loginToken = loginTokenService.getLoginTokenByToken(new String(bytes));
					if (loginToken != null) {
						userId = loginToken.getUserId();
					}
					if (StringUtils.isNotEmpty(userId)) {
						bean = authorizeService.login(userId);
					}
				}
			} catch (Exception ex) {
				logger.error("RSA Login Error", ex);
			}

			/**
			 * 判断是否允许用户从密锁登录
			 */
			if (bean != null && !StringUtils.equals(bean.getSecretLoginFlag(), "Y")) {
				bean = null;
			}

			// 如果每天登录次数超过设置值，设置用户对象为空，防止恶意操作
			if (bean != null && userOnlineLogService.getLoginCount(userId) > conf.getInt("limit.loginCount", 500)) {
				bean = null;
			}

			if (bean != null) {
				LoginContext loginContext = IdentityFactory.getLoginContext(bean.getActorId());
				/**
				 * 系统管理员不允许直接从RSA快捷登录
				 */
				if (loginContext.isSystemAdministrator()) {
					// 退出系统，清除session对象
					if (request.getSession(false) != null) {
						request.getSession().removeAttribute(Constants.LOGIN_INFO + "_" + StringTools.replace(request.getContextPath(), "/", ""));
					}
					try {
						String cacheKey = Constants.LOGIN_USER_CACHE + loginContext.getActorId();
						CacheFactory.remove("loginContext", cacheKey);
						cacheKey = Constants.USER_CACHE + loginContext.getActorId();
						CacheFactory.remove("user", cacheKey);
					} catch (Exception ex) {
					}
					return new ModelAndView("/modules/login");
				} else {
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
								// ex.printStackTrace();
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
						online.setLoginIP(ipAddr);
						
						userOnlineService.login(online);
					} catch (Exception ex) {

					}
				}

				/**
				 * 令牌登录一次有效，登录成功后即可删除。
				 */
				try {
					loginTokenService.deleteById(loginToken.getId());
				} catch (Exception ex) {
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

				return new ModelAndView("/modules/main", modelMap);
			}
		}

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

		return new ModelAndView("/modules/login", modelMap);
	}

	@ResponseBody
	@RequestMapping("/getToken")
	public byte[] getToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		JSONObject json = new JSONObject();
		String syscode = request.getParameter("syscode");
		String userid = request.getParameter("userid");
		if (StringUtils.isNotEmpty(syscode) && StringUtils.isNotEmpty(userid)) {
			try {
				LoginModule loginModule = loginModuleService.getLoginModuleBySysCode(syscode);
				if (loginModule != null) {
					byte[] data = Hex.decodeHex(userid);
					byte[] bytes = loginModuleService.decryptText(loginModule.getId(), data);
					String actorId = new String(bytes);
					long ts = System.currentTimeMillis();
					LoginToken bean = new LoginToken();
					bean.setSysCode(syscode);
					bean.setUserId(actorId);
					bean.setClientIP(RequestUtils.getIPAddress(request));
					bean.setCreateTime(new Date());
					bean.setToken(UUID32.getUUID() + UUID32.getUUID());
					bean.setNonce(UUID32.getUUID());
					bean.setLoginModuleId(loginModule.getId());
					bean.setTimeLive(loginModule.getTimeLive());
					bean.setTimeMillis(ts);
					String signature = SignUtils.getSignature(bean.getToken(), String.valueOf(ts), bean.getNonce());
					bean.setSignature(signature);
					loginTokenService.save(bean);
					json.put("token", Hex
							.byte2hex(loginModuleService.encryptText(loginModule.getId(), bean.getToken().getBytes())));
					json.put("nonce", bean.getNonce());
					json.put("timestamp", String.valueOf(ts));
				}
			} catch (Exception ex) {
			} finally {
			}
		}
		return json.toJSONString().getBytes("UTF-8");
	}

	@javax.annotation.Resource
	public void setAuthorizeService(AuthorizeService authorizeService) {
		this.authorizeService = authorizeService;
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
