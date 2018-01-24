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
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.glaf.base.handler.CasLoginHandler;
import com.glaf.base.handler.LoginHandler;
import com.glaf.base.handler.MQLoginHandler;
import com.glaf.base.handler.PasswordLoginHandler;
import com.glaf.base.handler.SpecialUrlLoginHandler;
import com.glaf.base.handler.ValidateCodeLoginHandler;
import com.glaf.base.handler.WechatLoginHandler;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.LockedHolder;
import com.glaf.base.modules.sys.model.IdentityToken;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.IdentityTokenService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.online.domain.UserOnline;
import com.glaf.base.online.service.UserOnlineService;
import com.glaf.base.res.MessageUtils;
import com.glaf.base.res.ViewMessage;
import com.glaf.base.res.ViewMessages;
import com.glaf.base.utils.CasClientUtils;
import com.glaf.base.utils.ContextUtil;
import com.glaf.base.utils.HttpClientUtil;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.cache.CacheFactory;

import com.glaf.core.config.Configuration;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.SystemProperty;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.security.RSAUtils;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.Constants;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StaxonUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.UUID32;
import com.glaf.core.web.callback.CallbackProperties;
import com.glaf.core.web.callback.LoginCallback;

@Controller("/login")
@RequestMapping("/login")
public class LoginController {
	private static final Log logger = LogFactory.getLog(LoginController.class);

	private static Configuration conf = BaseConfiguration.create();

	protected SysUserService sysUserService;

	protected UserOnlineService userOnlineService;

	protected IdentityTokenService identityTokenService;

	// private RedisTemplate<String, String> redisTemplate;

	// private RedisUtils redisUtils;

	// private SpringCacheUtils springCacheUtils;
	/**
	 * 登录
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/cassso")
	public ModelAndView casSSOLogin(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String userId = null;
		String ticket = null;
		String casLoginFullAddress = null;
		// 字典数据中获取配置信息
		BaseDataManager bm = BaseDataManager.getInstance();
		// 获取CAS登录地址
		String casLoginAddress = bm.getBaseData("loginAddress", "CASSSO") != null
				? bm.getBaseData("loginAddress", "CASSSO").getValue()
				: null;
		// 获取应用APP
		String appid = bm.getBaseData("appid", "CASSSO") != null ? bm.getBaseData("appid", "CASSSO").getValue() : null;
		// 系统服务地址
		String service = bm.getBaseData("serviceAddress", "CASSSO") != null
				? bm.getBaseData("serviceAddress", "CASSSO").getValue()
				: null;
		// 接收令牌参数
		String token = bm.getBaseData("token", "CASSSO") != null ? bm.getBaseData("token", "CASSSO").getValue() : null;
		// 发送令牌参数
		String sendToken = bm.getBaseData("sendToken", "CASSSO") != null
				? bm.getBaseData("sendToken", "CASSSO").getValue()
				: null;
		// 令牌认证服务地址
		String authAddress = bm.getBaseData("authAddress", "CASSSO") != null
				? bm.getBaseData("authAddress", "CASSSO").getValue()
				: null;
		// 获取令牌信息，如果没有令牌信息则跳转到登录页面
		ticket = request.getParameter(token);
		// 跳转到cas登录页面
		casLoginFullAddress = casLoginAddress + "?service=" + service + "&appid=" + appid;
		if (StringUtils.isEmpty(ticket)) {

			try {
				response.sendRedirect(casLoginFullAddress);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// 验证令牌是否有效
			Map<String, String> params = new HashMap<String, String>();
			params.put(sendToken, ticket);
			params.put("appid", appid);
			String authResult = HttpClientUtil.getResponseContent(authAddress, params);
			logger.info("SSO authResult::::::" + authResult);
			if (StringUtils.isNotEmpty(authResult)) {
				String jsonStr = StaxonUtils.xml2json(authResult);
				JSONObject json = JSONObject.parseObject(jsonStr);
				JSONObject resJson = json.getJSONObject("RES");
				// 验证成功
				if (resJson.getString("CODE") != null && "1".equals(resJson.getString("CODE"))) {
					userId = resJson.getString("ZHH");
					request.getSession().setAttribute("loginType", "CAS");
				}
				// 验证失败
				else {
					if (casLoginFullAddress != null) {
						try {
							response.sendRedirect(casLoginFullAddress);
							return null;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				if (casLoginFullAddress != null) {
					try {
						response.sendRedirect(casLoginFullAddress);
						return null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		SysUser bean = null;
		if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(ticket)) {
			AuthorizeService authorizeService = ContextFactory.getBean("authorizeService");
			bean = authorizeService.login(userId);
			if (bean == null) {
				if (casLoginFullAddress != null) {
					try {
						response.sendRedirect(casLoginFullAddress);
						return null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				String ipAddr = RequestUtils.getIPAddress(request);
				SystemProperty p = SystemConfig.getProperty("login_limit");
				if (!(StringUtils.equals(userId, "root") || StringUtils.equals(userId, "admin"))) {
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
						UserOnline userOnline = userOnlineService.getUserOnline(userId);
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
						if (!timeout) {// 超时，说明登录已经过期，不用判断是否已经登录了
							if (loginIP != null && !(StringUtils.equals(ipAddr, loginIP))) {
								// 用户已在其他机器登陆
								if (casLoginFullAddress != null) {
									try {
										response.sendRedirect(casLoginFullAddress);
										return null;
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				}

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
								logger.debug("-------------------------callback--------------------");
								logger.debug(callback.getClass().getName());
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
				RequestUtils.setRequestParameterToAttribute(request);

				String systemName = ParamUtil.getParameter(request, Constants.SYSTEM_NAME);
				if (StringUtils.isNotEmpty(systemName)) {
					Environment.setCurrentSystemName(systemName);
				} else {
					Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
				}
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
			}
		}

		return new ModelAndView("/modules/login", modelMap);
	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/doLogin")
	public ModelAndView doLogin(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		logger.debug("---------------------doLogin--------------------");
		RequestUtils.setRequestParameterToAttribute(request);

		String systemName = ParamUtil.getParameter(request, Constants.SYSTEM_NAME);
		if (StringUtils.isNotEmpty(systemName)) {
			Environment.setCurrentSystemName(systemName);
		} else {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		}
		String responseDataType = request.getParameter("responseDataType");
		logger.debug("currentSystemName:" + Environment.getCurrentSystemName());
		ViewMessages messages = new ViewMessages();
		// 获取参数
		String account = ParamUtil.getParameter(request, "x");
		account = StringEscapeUtils.escapeHtml(account);
		modelMap.put("account", StringEscapeUtils.escapeHtml(account));
		String password = ParamUtil.getParameter(request, "y");
		String userId = ParamUtil.getParameter(request, "userId");
		String token = ParamUtil.getParameter(request, "token");
		String login_json = request.getParameter("login_json");
		String login_params = request.getParameter("login_params");
		//获取短信验证码
		String validateCode = ParamUtil.getParameter(request, "k");
		SysUser bean = null;
		String rand = null;
		String rand2 = null;
		// 获取sso登录授权标识
		String ssoFlag = SystemConfig.getString("sso_flag");
		//账号和（密码或短信验证码）存在时
		if (StringUtils.isNotEmpty(account) && (StringUtils.isNotEmpty(password) || StringUtils.isNotEmpty(validateCode))) {
			HttpSession session = request.getSession(false);
			if (session == null) {
				if (StringUtils.isNotEmpty(responseDataType) && StringUtils.equals(responseDataType, "json")) {
					OutputStream output = null;
					try {
						request.setCharacterEncoding("UTF-8");
						response.setCharacterEncoding("UTF-8");
						response.setContentType("application/json; charset=UTF-8");
						output = response.getOutputStream();
						byte[] bytes = ResponseUtils.responseJsonResult(10001, "未初始化登录会话。");
						output.write(bytes);
						output.flush();
						return null;
					} catch (Exception e) {
					} finally {
						IOUtils.closeStream(output);
					}
				}
				return new ModelAndView("/modules/login", modelMap);
			}
			rand = (String) session.getAttribute("x_y");
			rand2 = (String) session.getAttribute("x_z");

			if (StringUtils.isEmpty(rand) && StringUtils.isEmpty(rand2)) {
				if (StringUtils.isNotEmpty(responseDataType) && StringUtils.equals(responseDataType, "json")) {
					OutputStream output = null;
					try {
						request.setCharacterEncoding("UTF-8");
						response.setCharacterEncoding("UTF-8");
						response.setContentType("application/json; charset=UTF-8");
						output = response.getOutputStream();
						byte[] bytes = ResponseUtils.responseJsonResult(10002, "登录信息不正确。");
						output.write(bytes);
						output.flush();
						return null;
					} catch (Exception e) {
					} finally {
						IOUtils.closeStream(output);
					}
				}
				messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("authorize.login_failure"));
				MessageUtils.addMessages(request, messages);
				return new ModelAndView("/modules/login", modelMap);
			}
			LoginHandler handler = null;
			if(StringUtils.isNotEmpty(password)){
				handler = new PasswordLoginHandler();
			}else{
				handler = new ValidateCodeLoginHandler();
			}
			
			bean = handler.doLogin(request, response);
			session.removeAttribute("x_y");// 一次性失效，用一次即过期
			session.removeAttribute("x_z");
		} else if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(token)) {
			LoginHandler handler = new MQLoginHandler();
			bean = handler.doLogin(request, response);
		} else if (StringUtils.isNotEmpty(login_json)) {
			LoginHandler handler = new SpecialUrlLoginHandler();
			bean = handler.doLogin(request, response);
		} else if (StringUtils.isNotEmpty(login_params)) {
			LoginHandler handler = new WechatLoginHandler();
			bean = handler.doLogin(request, response);
		} else if (StringUtils.isNotEmpty(ssoFlag) && ssoFlag.equals("true")) {
			// 获取用户账号
			LoginHandler handler = new CasLoginHandler();
			bean = handler.doLogin(request, response);
		}

		if (bean == null) {
			if (StringUtils.isNotEmpty(responseDataType) && StringUtils.equals(responseDataType, "json")) {
				if (!(StringUtils.equals(account, "root") || StringUtils.equals(account, "admin"))) {
					OutputStream output = null;
					try {
						request.setCharacterEncoding("UTF-8");
						response.setCharacterEncoding("UTF-8");
						response.setContentType("application/json; charset=UTF-8");
						output = response.getOutputStream();
						
						byte[] bytes = ResponseUtils.responseJsonResult(10003, "用户名或密码不正确，登录失败。");
						if(StringUtils.isEmpty(password)){
							bytes = ResponseUtils.responseJsonResult(10003, "手机号或验证码不正确，登录失败。");
						}
						if (LockedHolder.getLocked() != null && LockedHolder.getLocked().equals("1")) {
							//获取锁定的用户信息，计算剩余的锁定时间
							bean = sysUserService.findByAccount(ParamUtil.getParameter(request, "x"));
							
							//从字典中获取自动解锁时间(小时)
							BaseDataManager bm = BaseDataManager.getInstance();
							com.glaf.base.modules.sys.model.BaseDataInfo info =null;
							info = bm.getBaseData("autoUnlockTime","userLockRule");
							String autoUnlockTimeStr = info.getValue();
							if(StringUtils.isNotEmpty(autoUnlockTimeStr)){
								long autoUnlockTime = 0l,s = 0l;
								Date nowDate = new Date();
								if(StringUtils.equals(autoUnlockTimeStr, "ND")){
									//隔天解锁
									Calendar calendar = Calendar.getInstance();
							        calendar.setTime(nowDate);
							        calendar.set(Calendar.HOUR_OF_DAY, 0);
							        calendar.set(Calendar.MINUTE, 0);
							        calendar.set(Calendar.SECOND, 0);
							        //增加一天
							        calendar.add(Calendar.DATE, 1);
							      //计算，剩余时间 =明天时间-当前时间
							        s = calendar.getTimeInMillis() - nowDate.getTime();
								}else{
									autoUnlockTime = (long) (Float.valueOf(autoUnlockTimeStr) * 60 * 60 * 1000);
									//计算，剩余时间 =锁定时间 - （当前时间-上次登陆时间） 
									s = autoUnlockTime - (nowDate.getTime() - bean.getLastLoginTime().getTime());
								}
								JSONObject ret = new JSONObject();
								ret.put("statusCode", 10003);
								ret.put("message", "短时间内重复登录，用户账号被锁定");
								ret.put("remainMs", s);
								bytes = ret.toJSONString().getBytes("UTF-8");
							}else{
								bytes = ResponseUtils.responseJsonResult(10003, "短时间内重复登录，用户账号被锁定");
							}
						}
						output.write(bytes);
						output.flush();
						return null;
					} catch (Exception e) {
					} finally {
						IOUtils.closeStream(output);
					}
				}
			}
			// 用户对象为空或失效，显示错误信息
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("authorize.login_failure"));
			MessageUtils.addMessages(request, messages);
			return new ModelAndView("/modules/login", modelMap);
		} else {
			String ipAddr = RequestUtils.getIPAddress(request);
			SystemProperty p = SystemConfig.getProperty("login_limit");

			// 判断用户有效期是否失效
			Date deadlineTime = bean.getDeadlineTime();
			if (deadlineTime != null && (new Date()).getTime() > deadlineTime.getTime()) {
				// 有效期失效
				OutputStream output = null;
				try {
					request.setCharacterEncoding("UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json; charset=UTF-8");
					output = response.getOutputStream();
					byte[] bytes = ResponseUtils.responseJsonResult(10003, "用户有效期已过，请续期。");
					output.write(bytes);
					output.flush();
					return null;
				} catch (Exception e) {
				} finally {
					IOUtils.closeStream(output);
				}
			}

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
							messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("authorize.login_failure2"));
							MessageUtils.addMessages(request, messages);
							logger.debug("用户已经在其他地方登录。");

							if (StringUtils.isNotEmpty(responseDataType)
									&& StringUtils.equals(responseDataType, "json")) {
								OutputStream output = null;
								try {
									request.setCharacterEncoding("UTF-8");
									response.setCharacterEncoding("UTF-8");
									response.setContentType("application/json; charset=UTF-8");
									output = response.getOutputStream();
									byte[] bytes = ResponseUtils.responseJsonResult(201, "用户已经在其他地方登录。");
									output.write(bytes);
									output.flush();
									return null;
								} catch (Exception e) {
								} finally {
									IOUtils.closeStream(output);
								}
							} else {
								return new ModelAndView("/modules/login", modelMap);
							}
						}
					}
				}
			}

			HttpSession session = request.getSession(true);// 重写Session
			String uias_flag = SystemConfig.getString("uias_flag");
			if ("true".equals(uias_flag)) {
				// 登录成功，修改最近一次登录时间
				// 如果作为统一认证中心单点登录集成平台则模拟登陆并记录TGT
				if (StringUtils.isNotEmpty(rand) && StringUtils.isNotEmpty(rand2)) {
					if (rand != null) {
						password = StringTools.replace(password, rand, "");
					}
					if (rand2 != null) {
						password = StringTools.replace(password, rand2, "");
					}
				}
				String ticketServer = SystemConfig.getString("ticket_service_address");
				String TGT = CasClientUtils.getTicketGrantingTicket(ticketServer, account, account + ":" + password);
				// 将TGT写入session
				session.setAttribute("TGT", TGT);
				Cookie cookie = new Cookie("TGT", TGT);
				cookie.setPath("/");
				cookie.setMaxAge(-1);
				response.addCookie(cookie);
			}
			bean.setLastLoginDate(new Date());
			if (!bean.isSystemAdministrator()) {
				bean.setLastLoginIP(ipAddr);
			}
			bean.setLockLoginTime(null);
			bean.setLoginRetry(0);
			sysUserService.updateUser(bean);
			ContextUtil.put(bean.getAccount(), bean);// 传入全局变量

			IdentityToken identityToken = new IdentityToken();
			java.util.Random random = new java.util.Random();
			String ip = RequestUtils.getIPAddress(request);
			identityToken.setId(DateUtils.getNowYearMonthDayHHmmss() + "_" + bean.getActorId() + "_" + ip);
			identityToken.setClientIP(ip);
			identityToken.setNonce(UUID32.getUUID());
			identityToken.setTimeLive(7200);// 7200秒
			identityToken.setToken(StringTools.getRandomString(random.nextInt(100)));
			identityToken.setType("Session");
			identityToken.setUserId(bean.getActorId());
			this.identityTokenService.save(identityToken);
			// 将Token信息写入缓存
			// redisUtils.add(identityToken.getToken(), bean);
			// redisUtils.add(identityToken.getToken(), bean,3);
			// SysUser user=(SysUser)redisUtils.get(identityToken.getToken(),3);
			// springCacheUtils.put("default", identityToken.getToken(), bean);
			// Object
			// userObj=((SimpleValueWrapper)springCacheUtils.get(identityToken.getToken())).get();
			// redisUtils.add(identityToken.getToken(), bean.getActorId());
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
				if (!bean.isSystemAdministrator()) {
					online.setLoginIP(ipAddr);
				}
				userOnlineService.login(online);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

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

			LoginContext loginContext = IdentityFactory.getLoginContext(bean.getActorId());
			logger.debug(loginContext.toJsonObject().toJSONString());

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

			if (StringUtils.isNotEmpty(responseDataType) && StringUtils.equals(responseDataType, "json")) {
				OutputStream output = null;
				try {
					request.setCharacterEncoding("UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json; charset=UTF-8");
					output = response.getOutputStream();
					byte[] bytes = ResponseUtils.responseJsonResult(true, "登录成功！");
					output.write(bytes);
					output.flush();
					return null;
				} catch (Exception e) {
				} finally {
					IOUtils.closeStream(output);
				}
			}

			if (bean.getAccountType() == 2) {// 微信用户
				return new ModelAndView("/modules/wx_main", modelMap);
			} else {
				return new ModelAndView("/modules/main", modelMap);
			}
		}
	}

	@ResponseBody
	@RequestMapping("/getLoginSecurityKey")
	public void getLoginSecurityKey(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		OutputStream output = null;
		try {
			HttpSession session = request.getSession(true);
			java.util.Random random = new java.util.Random();
			String rand = UUID32.getUUID() + this.getRandomString(random.nextInt(10))
					+ this.getRandomString(random.nextInt(199)) + Math.abs(random.nextInt(99999999))
					+ this.getRandomString(random.nextInt(10)) + this.getRandomString(random.nextInt(999))
					+ Math.abs(random.nextInt(99999999)) + UUID32.getUUID();
			String rand2 = UUID32.getUUID() + this.getRandomString(random.nextInt(10))
					+ this.getRandomString(random.nextInt(199)) + Math.abs(random.nextInt(99999999))
					+ this.getRandomString(random.nextInt(10)) + this.getRandomString(random.nextInt(999))
					+ Math.abs(random.nextInt(99999999)) + UUID32.getUUID();
			if (session != null) {
				session.setAttribute("x_y", rand);
				session.setAttribute("x_z", rand2);
				json.put("x_y", rand);
				json.put("x_z", rand2);
			}

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			output = response.getOutputStream();
			output.write(json.toJSONString().getBytes("UTF-8"));
			output.flush();
			logger.debug(json.toJSONString());
			return;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(output);
		}
	}

	public String getRandomString(int length) {
		StringBuilder buffer = new StringBuilder(
				"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~!@#$^*()_-=&%+?/\\.<>|{}[]");
		buffer.append(UUID32.getUUID());
		StringBuilder sb = new StringBuilder(length);
		Random random = new Random();
		int range = buffer.length();
		for (int i = 0; i < length; i++) {
			sb.append(buffer.charAt(random.nextInt(range)));
		}
		return sb.toString();
	}

	@ResponseBody
	@RequestMapping("/getToken")
	public void getToken(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String userId = request.getParameter("userId");
		logger.debug("userId:" + userId);
		if (StringUtils.isNotEmpty(userId)) {
			OutputStream output = null;
			try {
				User user = IdentityFactory.getUser(userId);
				if (user != null && ContextUtil.increaseUser(userId) < 500) {
					IdentityToken identityToken = new IdentityToken();
					java.util.Random random = new java.util.Random();
					String rand1 = StringTools.getRandomString(random.nextInt(50));
					String rand2 = StringTools.getRandomString(random.nextInt(50));
					String ip = RequestUtils.getIPAddress(request);
					identityToken.setId(DateUtils.getNowYearMonthDay() + "_" + userId + "_" + ip);
					identityToken.setClientIP(ip);
					identityToken.setNonce(UUID32.getUUID());
					identityToken.setRand1(rand1);
					identityToken.setRand2(rand2);
					identityToken.setTimeLive(30);// 30秒
					identityToken.setToken(StringTools.getRandomString(random.nextInt(100)));
					identityToken.setType("Login");
					identityToken.setUserId(userId);
					this.identityTokenService.save(identityToken);

					json.put("x_y", rand1);
					json.put("x_z", rand2);
					json.put("token", identityToken.getToken());
					json.put("public_key", RSAUtils.getDefaultRSAPublicKey());

					request.setCharacterEncoding("UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json; charset=UTF-8");
					output = response.getOutputStream();
					output.write(json.toJSONString().getBytes("UTF-8"));
					output.flush();
					// logger.debug("----------------------------getLoginToken--------------");
					// logger.debug(json.toJSONString());
					return;
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			} finally {
				IOUtils.closeStream(output);
			}
		}
	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String ip = RequestUtils.getIPAddress(request);
		/**
		 * 允许从指定的机器上通过用户名密码登录
		 */
		if (StringUtils.contains(conf.get("login.allow.ip", "127.0.0.1"), ip)
				|| StringUtils.contains(SystemConfig.getString("login.allow.ip", "127.0.0.1"), ip)) {
			String actorId = request.getParameter("x");
			String password = request.getParameter("y");
			HttpSession session = request.getSession(true);
			java.util.Random random = new java.util.Random();
			String rand = Math.abs(random.nextInt(999999)) + com.glaf.core.util.UUID32.getUUID()
					+ Math.abs(random.nextInt(999999));
			String rand2 = Math.abs(random.nextInt(999999)) + com.glaf.core.util.UUID32.getUUID()
					+ Math.abs(random.nextInt(999999));
			session = request.getSession(true);
			if (session != null) {
				rand = Base64.encodeBase64String(rand.getBytes()) + com.glaf.core.util.UUID32.getUUID();
				rand2 = Base64.encodeBase64String(rand2.getBytes()) + com.glaf.core.util.UUID32.getUUID();
				session.setAttribute("x_y", rand);
				session.setAttribute("x_z", rand2);
			}
			String url = request.getContextPath() + "/mx/login/doLogin?x=" + actorId + "&y=" + rand + password + rand2;
			try {
				response.sendRedirect(url);
				return null;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		String userId = ParamUtil.getParameter(request, "userId");
		String token = ParamUtil.getParameter(request, "token");

		if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(token)) {
			return this.doLogin(request, response, modelMap);
		}

		String login_html = SystemConfig.getString("login_html");
		if (StringUtils.isNotEmpty(login_html) && (StringUtils.endsWithIgnoreCase(login_html, ".html")
				|| StringUtils.endsWithIgnoreCase(login_html, ".htm"))) {
			try {
				if (StringUtils.startsWith(login_html, request.getContextPath())) {
					response.sendRedirect(login_html);
				} else {
					response.sendRedirect(request.getContextPath() + login_html);
				}
				return null;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		// if (RequestUtils.isMobile(request)) {
		// 手机版登录页面
		// return new ModelAndView("/mobile/login");
		// }

		return new ModelAndView("/modules/login");
	}

	/**
	 * 注销
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String actorId = RequestUtils.getActorId(request);
		// 退出系统，清除session对象
		if (request.getSession(false) != null) {
			request.getSession().removeAttribute(
					Constants.LOGIN_INFO + "_" + StringTools.replace(request.getContextPath(), "/", ""));
			String uias_flag = SystemConfig.getString("uias_flag");
			if ("true".equals(uias_flag)) {
				if (request.getSession().getAttribute("TGT") != null) {
					String tgc = (String) request.getSession().getAttribute("TGT");
					// 调用sso loginout服务
					String ticketServer = SystemConfig.getString("ticket_service_address");
					CasClientUtils.logout(ticketServer, tgc);
					request.getSession().removeAttribute("TGT");
					Cookie cookie = new Cookie("TGT", null);
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
		}

		try {
			SystemProperty p = SystemConfig.getProperty("login_limit");
			if (p != null && StringUtils.equals(p.getValue(), "true")) {
				userOnlineService.logout(actorId);
			}
			String cacheKey = Constants.LOGIN_USER_CACHE + actorId;
			CacheFactory.remove("loginContext", cacheKey);
			cacheKey = Constants.USER_CACHE + actorId;
			CacheFactory.remove("user", cacheKey);
			com.glaf.shiro.ShiroSecurity.logout();

			Cookie cookie = new Cookie(
					Constants.COOKIE_NAME + "_" + StringTools.replace(request.getContextPath(), "/", ""), null);
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);

			if (request.getSession(false) != null) {
				request.getSession().invalidate();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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
	@RequestMapping
	public ModelAndView prepareLogin(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		logger.debug("---------------------prepareLogin--------------------");
		RequestUtils.setRequestParameterToAttribute(request);

		String userId = request.getParameter("userId");
		String token = request.getParameter("token");

		String account = request.getParameter("x");
		account = StringEscapeUtils.escapeHtml(account);
		modelMap.put("account", StringEscapeUtils.escapeHtml(account));
		// 获取sso登录授权标识
		String ssoFlag = SystemConfig.getString("sso_flag");
		if (StringUtils.isNotEmpty(ssoFlag) && ssoFlag.equals("true")) {
			logger.debug("->sso login");
			return this.doLogin(request, response, modelMap);
		}
		if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(token)) {
			logger.debug("->token" + token);
			return this.doLogin(request, response, modelMap);
		}

		String login_html = SystemConfig.getString("login_html");
		if (StringUtils.isNotEmpty(login_html) && (StringUtils.endsWithIgnoreCase(login_html, ".html")
				|| StringUtils.endsWithIgnoreCase(login_html, ".htm"))) {
			try {
				if (StringUtils.startsWith(login_html, request.getContextPath())) {
					response.sendRedirect(login_html);
				} else {
					response.sendRedirect(request.getContextPath() + login_html);
				}
				return null;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

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

		// if (RequestUtils.isMobile(request)) {
		// 手机版登录页面
		// view = "/mobile/login";
		// }

		// 显示登陆页面
		return new ModelAndView(view, modelMap);
	}

	@javax.annotation.Resource(name = "identityTokenService")
	public void setIdentityTokenService(IdentityTokenService identityTokenService) {
		this.identityTokenService = identityTokenService;
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