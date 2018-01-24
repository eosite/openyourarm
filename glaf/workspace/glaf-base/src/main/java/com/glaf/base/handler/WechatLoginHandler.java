package com.glaf.base.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.online.service.UserOnlineLogService;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.security.RSAUtils;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.SignUtils;

public class WechatLoginHandler implements LoginHandler {

	protected static final Log logger = LogFactory.getLog(WechatLoginHandler.class);

	protected static Configuration conf = BaseConfiguration.create();

	public boolean checkToken(HttpServletRequest request, HttpServletResponse response) {
		String token_name = request.getParameter("token_name");
		String token = conf.get("login_token");
		if (StringUtils.isNotEmpty(token_name)) {
			token = conf.get("login_token_" + token_name);
		}
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		if (StringUtils.isNotEmpty(token) && StringUtils.isNotEmpty(signature) && StringUtils.isNotEmpty(timestamp)
				&& StringUtils.isNotEmpty(nonce) && SignUtils.checkToken(token, signature, timestamp, nonce)) {
			// 验证通过
			return true;
		}
		return false;
	}

	public SysUser doLogin(HttpServletRequest request, HttpServletResponse response) {
		String ipAddr = RequestUtils.getIPAddress(request);
		String login_params = request.getParameter("login_params");
		String token_name = request.getParameter("token_name");
		String token = conf.get("login_token");
		if (StringUtils.isNotEmpty(token_name)) {
			token = conf.get("login_token_" + token_name);
		}

		SysUser bean = null;

		if (this.checkToken(request, response) && StringUtils.isNotEmpty(login_params)) {
			String text = RSAUtils.decryptString(login_params);
			text = new String(Base64.decodeBase64(text));
			JSONObject json = JSON.parseObject(text);
			if (json.containsKey("clientIP") && json.containsKey("userId") && json.containsKey("token")
					&& json.containsKey("loginTime") && json.containsKey("timeLive")) {
				String clientIP = json.getString("clientIP");
				String userId = json.getString("userId");
				String token2 = json.getString("token");
				String section = json.getString("section");
				long loginTime = json.getLongValue("loginTime");
				int timeLive = json.getIntValue("timeLive");

				// 是否验证客户端IP地址，默认是不验证
				if (conf.getBoolean("wechat.verifyClientIP", false)) {
					// 如果服务器接收的登录地址和现在客户远程地址不一致，不允许登录
					if (!StringUtils.equals(clientIP, ipAddr)) {
						return null;
					}
				}

				if (StringUtils.equals(token, token2)) {
					if ((loginTime + timeLive * 60 * 1000) > System.currentTimeMillis()) {
						logger.debug(userId + " start login........................");
						if (StringUtils.isNotEmpty(section)) {
							IDatabaseService databaseService = ContextFactory.getBean("databaseService");
							Database database = databaseService.getDatabaseByMapping(section);
							if (database != null) {
								Environment.setCurrentSystemName(database.getName());
								logger.debug("currentSystemName:" + Environment.getCurrentSystemName());
							}
						}
						AuthorizeService authorizeService = ContextFactory.getBean("authorizeService");
						bean = authorizeService.login(userId);
						
						UserOnlineLogService userOnlineLogService = ContextFactory.getBean("userOnlineLogService");
						// 如果每天登录次数超过设置值，设置用户对象为空，防止恶意操作
						if (userOnlineLogService.getLoginCount(userId) > conf.getInt("limit.loginCount", 1000)) {
							bean = null;
						}
					}
				}
			}
		}

		return bean;
	}

}
