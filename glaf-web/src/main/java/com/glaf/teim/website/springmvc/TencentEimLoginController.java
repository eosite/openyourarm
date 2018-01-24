package com.glaf.teim.website.springmvc;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.annotation.Resource;
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
import com.glaf.base.callback.LoginCallbackThread;
import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.website.springmvc.AppKeyLoginController;
import com.glaf.base.online.domain.UserOnline;
import com.glaf.base.online.service.UserOnlineLogService;
import com.glaf.base.online.service.UserOnlineService;
import com.glaf.base.utils.ContextUtil;
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
import com.glaf.teim.service.EimServerTmpService;

@Controller("/eimlogin")
@RequestMapping("/eimlogin")
public class TencentEimLoginController {
	protected static final Log logger = LogFactory.getLog(TencentEimLoginController.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected SysUserService sysUserService;

	protected UserOnlineService userOnlineService;

	protected EimServerTmpService eimServerTmpService;
	
	protected AuthorizeService authorizeService;

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
		// 从request Header中获取ck
		String ck = request.getHeader("key");
		if (StringUtils.isEmpty(ck)) {
			return this.prepareLogin(request, response, modelMap);
		}
		// 获取用户ID
		logger.debug("ip:" + RequestUtils.getIPAddress(request));
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("ck", ck);
		//从字典中获取登录实例和SSO模板服务ID
		BaseDataManager manager=BaseDataManager.getInstance();
		BaseDataInfo baseData=manager.getBaseData("teiminst", "teimsso");
		String teiminst=baseData.getValue();
		baseData=manager.getBaseData("teimssotmp", "teimsso");
		String teimssotmp=baseData.getValue();
		JSONObject returnJson = eimServerTmpService.callService(teimssotmp, teiminst, paramsJson);
		if(returnJson==null){
			return this.prepareLogin(request, response, modelMap);
		}
		returnJson=returnJson.getJSONObject("return");
		if(returnJson==null){
			return this.prepareLogin(request, response, modelMap);
		}
		int success=returnJson.getIntValue("success");
		if(success==0){
			return this.prepareLogin(request, response, modelMap);
		}
	    if(returnJson.getJSONObject("result")==null){
	    	return this.prepareLogin(request, response, modelMap);
	    }
		//获取用户ID
		String userId=returnJson.getJSONObject("result").getString("staffName");
		SysUser bean = null;
		if (StringUtils.isNotEmpty(userId)) {
			bean = authorizeService.login(userId);
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
				online.setLoginIP(ipAddr);
				//online.setSessionId(session.getId());
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
				manager = BaseDataManager.getInstance();
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
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@javax.annotation.Resource
	public void setUserOnlineService(UserOnlineService userOnlineService) {
		this.userOnlineService = userOnlineService;
	}
	@javax.annotation.Resource
	public void setAuthorizeService(AuthorizeService authorizeService) {
		this.authorizeService = authorizeService;
	}

	@Resource(name = "com.glaf.teim.service.eimServerTmpService")
	public void setEimServerTmpService(EimServerTmpService eimServerTmpService) {
		this.eimServerTmpService = eimServerTmpService;
	}
}
