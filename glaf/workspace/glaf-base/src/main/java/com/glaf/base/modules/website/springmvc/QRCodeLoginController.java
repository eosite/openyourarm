package com.glaf.base.modules.website.springmvc;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.glaf.base.callback.LoginCallbackThread;
import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.online.domain.UserOnline;
import com.glaf.base.online.service.UserOnlineLogService;
import com.glaf.base.online.service.UserOnlineService;
import com.glaf.base.utils.ContextUtil;
import com.glaf.base.utils.TokenProcessor;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.SystemProperty;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.web.callback.CallbackProperties;
import com.glaf.core.web.callback.LoginCallback;

@RestController("/qrCodeLoginController")
public class QRCodeLoginController {
	protected static final Log logger = LogFactory.getLog(QRCodeLoginController.class);
	private SysUserService sysUserService;

	private UserOnlineService userOnlineService;

	private AuthorizeService authorizeService;

	private static Configuration conf = BaseConfiguration.create();

	/**
	 * 获取二维码路径
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/QR/token")
	public byte[] getQRLoginToken(@Context HttpServletRequest request) {
		byte[] accessToken = null;
		String token = null;
		JSONObject returnJSON = new JSONObject();
		// 获取appId
		String appId = request.getParameter("appId");
		// 获取系统
		if (StringUtils.isEmpty(appId)) {
			returnJSON.put("result", 500);
			return returnJSON.toJSONString().getBytes();
		}
		// 获取客户端安全KEY
		String clientSrec = request.getParameter("clientSrec");
		if (StringUtils.isEmpty(clientSrec)) {
			returnJSON.put("result", 500);
			return returnJSON.toJSONString().getBytes();
		}
		// 验证是否与系统默认一致
		String localAppId = SystemConfig.getString("appId");
		if (localAppId.equals(appId)) {
			// 生成访问Token
			TokenProcessor tokenProcessor = TokenProcessor.getInstance();
			token = tokenProcessor.generateTokenCodeUrl();
			returnJSON.put("url", token);
			returnJSON.put("result", 200);
			accessToken = returnJSON.toJSONString().getBytes();
		}
		return accessToken;
	}

	/**
	 * 跳转到登录确认页面
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/QR/cf/{token}")
	public ModelAndView loginConfirm(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String token, ModelMap modelMap) {
		String loginUrl = SystemConfig.getString("login");
		String confirmUrl = SystemConfig.getString("login_confirm_url");
		TokenProcessor tokenProcessor = TokenProcessor.getInstance();
		// 验证token是否存在，是否过期
		if (tokenProcessor.checkToken(token)) {
			// 跳转到扫码登录页面
			return new ModelAndView(loginUrl, modelMap);
		}
		modelMap.put("token", "token");
		return new ModelAndView(confirmUrl, modelMap);
	}

	/**
	 * 确认/取消登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/QR/rst/{token}/{status}")
	public byte[] confirmLogin(@Context HttpServletRequest request,
			@PathVariable String token, @PathVariable String status) {
		String actorId = RequestUtils.getActorId(request);
		JSONObject returnJSON = new JSONObject();
		TokenProcessor tokenProcessor = TokenProcessor.getInstance();
		if (tokenProcessor.checkToken(token)) {
			returnJSON.put("result", 500);
		}
		if (tokenProcessor.confirmToken(token, actorId, status)) {
			returnJSON.put("result", 500);
		} else {
			returnJSON.put("result", 200);
		}
		return returnJSON.toJSONString().getBytes();
	}

	/**
	 * 检查登陆状态
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/QR/ck/{token}")
	public byte[] checkLogin(@Context HttpServletRequest request, HttpServletResponse response,
			@PathVariable String token) {
		JSONObject returnJSON = new JSONObject();
		TokenProcessor tokenProcessor = TokenProcessor.getInstance();
		returnJSON.put("result", 1);
		// token不存在或者超时
		if (!tokenProcessor.checkToken(token)) {
			returnJSON.put("result", 500);
			return returnJSON.toJSONString().getBytes();
		}
		String tokenJSONStr = CacheFactory.getString("qrlogin", token);
		JSONObject tokenJSON = JSONObject.parseObject(tokenJSONStr);
		String systemName = null;
		// 获取登陆状态
		String status = tokenJSON.getString("status");
		String actorId = tokenJSON.getString("actorId");
		// token移除
		tokenProcessor.removeToken(token);
		if (status.equals("1") && StringUtils.isNotEmpty(actorId)) {
			// 模拟登陆
			SysUser bean = authorizeService.login(actorId);
			logger.debug("current authorize User--->" + actorId);
			UserOnlineLogService userOnlineLogService = ContextFactory.getBean("userOnlineLogService");
			// 如果每天登录次数超过设置值，设置用户对象为空，防止恶意操作
			if (userOnlineLogService.getLoginCount(actorId) > conf.getInt("limit.loginCount", 1000)) {
				bean = null;
				returnJSON.put("result", 503);
				return returnJSON.toJSONString().getBytes();
			}
			if (bean == null) {
				// 用户不存在
				returnJSON.put("result", 501);
				return returnJSON.toJSONString().getBytes();
			} else {
				String ipAddr = RequestUtils.getIPAddress(request);
				SystemProperty p = SystemConfig.getProperty("login_limit");
				if (!(StringUtils.equals(actorId, "root") || StringUtils.equals(actorId, "admin"))) {
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
						UserOnline userOnline = userOnlineService.getUserOnline(actorId);
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
							if (loginIP != null && !(StringUtils.equals(ipAddr, loginIP))) {// 用户已在其他机器登陆
								// 已在其它位置登陆
								returnJSON.put("result", 502);
								return returnJSON.toJSONString().getBytes();
							}
						}
					}
				}

				HttpSession session = request.getSession(true);// 重写Session
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

			}
		} else {
			returnJSON.put("result", 0);
		}

		return returnJSON.toJSONString().getBytes();
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
}
