package com.glaf.base.modules.website.springmvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.SystemProperty;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.Constants;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.Hex;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.SignUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.UUID32;
import com.glaf.core.web.callback.CallbackProperties;
import com.glaf.core.web.callback.LoginCallback;

import com.glaf.base.callback.LoginCallbackThread;
import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.LoginMessage;
import com.glaf.base.modules.sys.model.LoginModule;
import com.glaf.base.modules.sys.model.LoginToken;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.query.LoginModuleQuery;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.LoginMessageService;
import com.glaf.base.modules.sys.service.LoginModuleService;
import com.glaf.base.modules.sys.service.LoginTokenService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.online.domain.UserOnline;
import com.glaf.base.online.service.UserOnlineLogService;
import com.glaf.base.online.service.UserOnlineService;
import com.glaf.base.utils.ContextUtil;
import com.glaf.base.utils.LoginContextUtil;

@Controller("/rsa_login")
@RequestMapping("/rsa_login")
public class RSALoginController {
	private static final Log logger = LogFactory.getLog(RSALoginController.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected AuthorizeService authorizeService;

	protected LoginModuleService loginModuleService;

	protected LoginTokenService loginTokenService;

	protected LoginMessageService loginMessageService;

	protected SysUserService sysUserService;

	protected UserOnlineService userOnlineService;

	protected UserOnlineLogService userOnlineLogService;

	@ResponseBody
	@RequestMapping("/getToken")
	public void getToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain; charset=UTF-8");
		String systemCode = request.getParameter("systemCode");
		String userId = request.getParameter("userId");
		if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(systemCode)) {
			PrintWriter writer = null;
			try {
				LoginModuleQuery query = new LoginModuleQuery();
				query.systemCode(systemCode);
				query.type("server_rsa");
				List<LoginModule> list = loginModuleService.list(query);
				if (list != null && !list.isEmpty()) {
					LoginModule loginModule = list.get(0);
					long ts = System.currentTimeMillis();
					JSONObject json = new JSONObject();
					json.put("token", UUID32.getUUID() + DigestUtils.sha512Hex(userId + "_" + ts));
					json.put("nonce", UUID32.getUUID());
					json.put("timestamp", ts);
					LoginToken token = new LoginToken();
					token.setLoginModuleId(loginModule.getId());
					token.setToken(json.getString("token"));
					token.setNonce(json.getString("nonce"));
					token.setTimeLive(loginModule.getTimeLive());
					token.setUserId(userId);
					token.setTimeMillis(ts);
					String signature = SignUtils.getSignature(json.getString("token"), String.valueOf(ts),
							json.getString("nonce"));
					token.setSignature(signature);
					loginTokenService.save(token);

					byte[] data = loginModuleService.encryptText(loginModule.getId(), json.toJSONString().getBytes());
					String text = Hex.byte2hex(data);
					writer = response.getWriter();
					writer.write(text);
					writer.flush();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			} finally {
				if (writer != null) {
					writer.close();
				}
			}
		}
	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		logger.debug("---------------------doLogin--------------------");
		String systemCode = request.getParameter("systemCode");
		String timestamp = request.getParameter("timestamp");
		String userId = request.getParameter("userId");
		SysUser bean = null;
		if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(systemCode) && StringUtils.isNotEmpty(timestamp)) {
			try {
				LoginModuleQuery query = new LoginModuleQuery();
				query.systemCode(systemCode);
				query.type("server_rsa");
				List<LoginModule> list = loginModuleService.list(query);
				if (list != null && !list.isEmpty()) {
					LoginModule loginModule = list.get(0);
					byte[] data = loginModuleService.decryptText(loginModule.getId(), Hex.hex2byte(timestamp));
					timestamp = new String(data, "UTF-8");
					/**
					 * 判断两个时间时候在限制范围内。
					 */
					if (System.currentTimeMillis() - Long.parseLong(timestamp) < DateUtils.MINUTE * 5) {
						data = loginModuleService.decryptText(loginModule.getId(), Hex.hex2byte(userId));
						userId = new String(data, "UTF-8");
						if (StringUtils.isNotEmpty(userId)) {
							bean = authorizeService.login(userId);
						}
					}
				}
			} catch (Exception ex) {
				logger.error("RSA Login Error", ex);
			}
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

	@ResponseBody
	@RequestMapping("/saveToken")
	public void saveToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain; charset=UTF-8");
		String text = request.getParameter("text");
		String systemCode = request.getParameter("systemCode");
		if (StringUtils.isNotEmpty(text) && StringUtils.isNotEmpty(systemCode)) {
			logger.debug("systemCode:" + systemCode);
			logger.debug("text:" + text);
			PrintWriter writer = null;
			try {
				LoginModuleQuery query = new LoginModuleQuery();
				query.systemCode(systemCode);
				query.type("server_rsa");
				List<LoginModule> list = loginModuleService.list(query);
				if (list != null && !list.isEmpty()) {
					LoginModule loginModule = list.get(0);
					byte[] data = Hex.hex2byte(text);
					byte[] bytes = loginModuleService.decryptText(loginModule.getId(), data);
					if (bytes != null) {
						JSONObject json = JSON.parseObject(new String(bytes));
						if (json.containsKey("clientIP") && json.containsKey("signature")) {
							String signature = json.getString("signature");
							LoginToken token = loginTokenService.getLoginTokenBySignature(signature);
							if (token != null) {
								String userId = token.getUserId();
								User user = IdentityFactory.getUser(userId);
								/**
								 * 用户存在并且有效
								 */
								if (user != null && user.getLocked() != 1) {
									LoginMessageService loginMessageService = ContextFactory
											.getBean("loginMessageService");
									// 防止恶意发送登录消息
									int total = loginMessageService.getTodayLoginCountByUserId(userId);
									logger.debug("total:" + total);
									if (total < 2000) {
										String cacheKey = json.getString("clientIP") + "_" + userId;
										String clientIP = json.getString("clientIP");
										String section = json.getString("section");

										LoginMessage model = new LoginMessage();
										model.setClientIP(clientIP);
										model.setContent(text);
										model.setLoginTime(token.getTimeMillis());
										model.setTimeLive(token.getTimeLive());
										model.setToken(token.getToken());
										model.setUserId(userId);
										model.setSection(section);// 标段信息
										model.setUid(json.getString("uid"));
										model.setFlowid(json.getString("flowid"));
										model.setPosition(json.getString("position"));
										model.setCellTreedotIndex(json.getString("cellTreedotIndex"));
										loginMessageService.save(model);
										LoginContextUtil.put(cacheKey, json.toJSONString());
										CacheFactory.put("loginMessage", cacheKey, json.toJSONString());
										logger.debug("save message");
										writer = response.getWriter();
										writer.write("OK");
										writer.flush();
									} else {
										logger.warn("ignore message:" + text);
									}
								}
							}
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			} finally {
				if (writer != null) {
					writer.close();
				}
			}
		}
	}

	@javax.annotation.Resource
	public void setAuthorizeService(AuthorizeService authorizeService) {
		this.authorizeService = authorizeService;
	}

	@javax.annotation.Resource
	public void setLoginMessageService(LoginMessageService loginMessageService) {
		this.loginMessageService = loginMessageService;
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
