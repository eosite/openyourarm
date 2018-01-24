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

package com.glaf.form.website.springmvc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.domain.SystemProperty;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.Constants;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.web.callback.CallbackProperties;
import com.glaf.core.web.callback.LoginCallback;
import com.glaf.lanxin.utils.JSONFindUtils;
import com.glaf.teim.domain.EimServerTmp;
import com.glaf.teim.service.EimServerTmpService;
/**
 * 通过其他系统通过MQ登录到本系统 <br/>
 * 
 * 设置服务器端登录配置:/mx/sys/loginModule?type=server
 */

@Controller("/thirdacesslogin")
@RequestMapping("/thirdacesslogin")
public class ThirdAcessLoginController {
	protected static final Log logger = LogFactory.getLog(ThirdAcessLoginController.class);

	private static Configuration conf = BaseConfiguration.create();

	private AuthorizeService authorizeService;

	private SysUserService sysUserService;

	private UserOnlineService userOnlineService;

	private UserOnlineLogService userOnlineLogService;

	@Autowired
	private EimServerTmpService eimServerTmpService;
	
	@RequestMapping("/getLoginInfo")
	@ResponseBody
	public byte[] getLoginInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject ret = new JSONObject();
		ret.put("token", request.getHeader("token"));
		ret.put("userid", request.getHeader("userid"));
		return ret.toJSONString().getBytes("UTF-8");
	}
	
	/**
	 * 登录
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/thirdLogin")
	public ModelAndView thirdLogin(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		logger.debug("---------------------thirdacesslogin--------------------");
		//获取token 与 userId
		//应用实例ID
		String baseId = ParamUtil.getParameter(request, "baseId");
		//模板ID
		String tmpId = ParamUtil.getParameter(request, "tmpId");
		logger.debug("baseId:" + baseId);
		logger.debug("tmpId:" + tmpId);
		
		//进行校验
		//应用实例ID
		String baseId2 = ParamUtil.getParameter(request, "baseId2");
		//模板ID
		String tmpId2 = ParamUtil.getParameter(request, "tmpId2");
		logger.debug("baseId2:" + baseId);
		logger.debug("tmpId2:" + tmpId);

		SysUser bean = null;
		//获取request中的参数信息
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String paramsStr = request.getParameter("params");
		JSONObject paramsJson = new JSONObject();
		if (params != null) {
			paramsJson.putAll(params);
		}
		if (StringUtils.isNotEmpty(paramsStr)) {
			paramsJson.putAll(JSONObject.parseObject(paramsStr));
		}
		logger.debug("params(请求参数):"+params);
		logger.debug("paramsStr(请求参数):"+paramsStr);
		//从cookie获取TGT
		Cookie[] cookies=request.getCookies();
		if(cookies != null){
			for(Cookie cookie:cookies) {
				paramsJson.put(cookie.getName(), cookie.getValue());
			}
		}
		
		if (StringUtils.isEmpty(tmpId) || StringUtils.isEmpty(baseId)) {
			bean = null;
		}else{
			EimServerTmp tmp=eimServerTmpService.getEimServerTmp(tmpId);
			String variableStr = tmp.getResponse_(),userIdName="",datapos="",tokenName="";
			JSONObject variableJSON = new JSONObject();
			if(StringUtils.isNotEmpty(variableStr)){
				//解析模板中的结果模板，获取datapos和userid
				variableJSON = JSON.parseObject(variableStr);
				JSONObject data = variableJSON.getJSONObject("data");
				if(data != null){
					tokenName = data.getString("token");
					userIdName = data.getString("userid");
					datapos = data.getString("datapos");
				}
				//判断验证是否通过
				boolean checkFlag = false;
				
				JSONObject returnJson = eimServerTmpService.callService(tmpId2, baseId2, paramsJson);
				if(StringUtils.equals(returnJson.getString("statusCode"), "200")){
					//判断验证结果是否正确
					variableStr = returnJson.getString("return");
					if(StringUtils.isNotEmpty(variableStr) && variableStr.indexOf("true") != -1){
						//验证错误
						checkFlag = true;
					}
				}
				//验证成功后，进行登陆
				if(checkFlag){
					//解析返回的结果信息，获取userId
					returnJson = eimServerTmpService.callService(tmpId, baseId, paramsJson);
					logger.debug("returnJson(返回结果):"+returnJson);
					variableStr = returnJson.getString("statusCode");
					if(StringUtils.equals(variableStr, "200") && StringUtils.isNotEmpty(userIdName)){
						variableStr = returnJson.getString("return");
						JSONObject retDataJSON = JSON.parseObject(variableStr);
						String userId = "",token="";
						
						if(StringUtils.isNotEmpty(datapos)){
							String[] dataposAry = datapos.split(",");
							JSONObject variable2 = retDataJSON;
							JSONArray parsedDatas = new JSONArray();
							
							//以","分隔遍历datapos
							for(String str:dataposAry){
								JSONFindUtils.getJSONArrayByPath(parsedDatas, retDataJSON, str);
							}
							for(Object obj : parsedDatas){
								variable2 = (JSONObject) obj;
								userId += variable2.getString(userIdName);
								token += variable2.getString(tokenName);
							}
						}else{
							userId = retDataJSON.getString(userIdName);
							token = retDataJSON.getString(tokenName);
						}
						if(StringUtils.isNotEmpty(userId)){
							bean = sysUserService.findByAccount(userId);
						}
						//校验
						bean = authorizeService.login(userId);
						if (StringUtils.isEmpty(bean.getToken())
								|| StringUtils.isEmpty(token)
								|| !StringUtils.equals(bean.getToken(), token)) {
							bean = null;
						}
					}
				}
			}
		}
		
		// 如果每天登录次数超过设置值，设置用户对象为空，防止恶意操作
		if (bean != null && userOnlineLogService.getLoginCount(bean.getAccount()) > conf.getInt("limit.loginCount", 500)) {
			bean = null;
		}

		if (bean != null) {
			LoginContext loginContext = IdentityFactory.getLoginContext(bean.getActorId());
			/**
			 * 系统管理员不允许直接从MQ快捷登录
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
					// com.glaf.shiro.ShiroSecurity.logout();
					// if(!request.getSession(false).equals(null))
					// request.getSession().invalidate();
				} catch (Exception ex) {
					ex.printStackTrace();
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
	 * 登录
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		logger.debug("---------------------thirdacesslogin--------------------");
		//应用实例ID
		String baseId = ParamUtil.getParameter(request, "baseId");
		//模板ID
		String tmpId = ParamUtil.getParameter(request, "tmpId");
		logger.debug("baseId:" + baseId);
		logger.debug("tmpId:" + tmpId);

		SysUser bean = null;
		//获取request中的参数信息
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String paramsStr = request.getParameter("params");
		JSONObject paramsJson = new JSONObject();
		if (params != null) {
			paramsJson.putAll(params);
		}
		if (StringUtils.isNotEmpty(paramsStr)) {
			paramsJson.putAll(JSONObject.parseObject(paramsStr));
		}
		logger.debug("params(请求参数):"+params);
		logger.debug("paramsStr(请求参数):"+paramsStr);
		//从cookie获取TGT
		Cookie[] cookies=request.getCookies();
		if(cookies != null){
			for(Cookie cookie:cookies) {
				paramsJson.put(cookie.getName(), cookie.getValue());
			}
		}
		
		if (StringUtils.isEmpty(tmpId) || StringUtils.isEmpty(baseId)) {
			bean = null;
		}else{
			EimServerTmp tmp=eimServerTmpService.getEimServerTmp(tmpId);
			String variable = tmp.getResponse_(),userIdName="",datapos="";
			JSONObject variableJSON = new JSONObject();
			if(StringUtils.isNotEmpty(variable)){
				//解析模板中的结果模板，获取datapos和userid
				variableJSON = JSON.parseObject(variable);
				JSONObject data = variableJSON.getJSONObject("data");
				if(data != null){
					userIdName = data.getString("userid");
					datapos = data.getString("datapos");
				}
				//解析返回的结果信息，获取userId
				JSONObject returnJson = eimServerTmpService.callService(tmpId, baseId, paramsJson);
				logger.debug("returnJson(返回结果):"+returnJson);
				variable = returnJson.getString("statusCode");
				if(StringUtils.equals(variable, "200") && StringUtils.isNotEmpty(userIdName)){
					variable = returnJson.getString("return");
					JSONObject retDataJSON = JSON.parseObject(variable);
					String userId = "";
					if(StringUtils.isNotEmpty(datapos)){
						String[] dataposAry = datapos.split(","),
								variableAry;
						JSONObject variable2 = retDataJSON;
						//以","分隔遍历datapos
						for(String str:dataposAry){
							variable2 = retDataJSON;
							variableAry = str.split("\\.");
							for(int i = 0 ; i < variableAry.length; i++){
								variable = variableAry[i];
								if(i == variableAry.length - 1){
									if(variable2.getJSONObject(variable) != null){
										userId += variable2.getJSONObject(variable).getString(userIdName);
									}
								}else{
									variable2 = variable2.getJSONObject(variable);
								}
							}
						}
					}else{
						userId = retDataJSON.getString("userId");
					}
					if(StringUtils.isNotEmpty(userId)){
						bean = sysUserService.findByAccount(userId);
					}
					//校验
					bean = authorizeService.login(userId);
				}
			}
		}
		
		// 如果每天登录次数超过设置值，设置用户对象为空，防止恶意操作
		if (bean != null && userOnlineLogService.getLoginCount(bean.getAccount()) > conf.getInt("limit.loginCount", 500)) {
			bean = null;
		}

		if (bean != null) {
			LoginContext loginContext = IdentityFactory.getLoginContext(bean.getActorId());
			/**
			 * 系统管理员不允许直接从MQ快捷登录
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
					// com.glaf.shiro.ShiroSecurity.logout();
					// if(!request.getSession(false).equals(null))
					// request.getSession().invalidate();
				} catch (Exception ex) {
					ex.printStackTrace();
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
