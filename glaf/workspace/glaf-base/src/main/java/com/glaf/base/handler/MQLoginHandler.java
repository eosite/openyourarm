package com.glaf.base.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.sys.model.LoginMessage;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.LoginMessageService;
import com.glaf.base.online.service.UserOnlineLogService;
import com.glaf.base.utils.LoginContextUtil;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.RequestUtils;

/**
 * http://10.20.1.152/glaf/mx/login?userId=hxj&clientIP=10.20.1.152&token=
 * ae5ab58d091346c8b51a1dbc6df17882
 *
 */
public class MQLoginHandler implements LoginHandler {
	protected static final Log logger = LogFactory.getLog(MQLoginHandler.class);

	protected static Configuration conf = BaseConfiguration.create();

	@Override
	public SysUser doLogin(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("----------------------MQLoginHandler-----------------------");
		String ipAddr = RequestUtils.getIPAddress(request);
		String actorId = request.getParameter("userId");
		String token = request.getParameter("token");

		/**
		 * 系统管理员账户不允许通过MQ方式登录，必须通过密码登录
		 */
		if (StringUtils.equalsIgnoreCase(actorId, "admin") || StringUtils.equalsIgnoreCase(actorId, "root")) {
			return null;
		}

		String cacheKey = token;
		String text = LoginContextUtil.get(cacheKey);
		if (StringUtils.isEmpty(text)) {
			text = CacheFactory.getString("loginMessage", cacheKey);
		}

		if (StringUtils.isEmpty(text)) {
			try {
				LoginMessageService loginMessageService = ContextFactory.getBean("loginMessageService");
				LoginMessage model = loginMessageService.getLoginMessageByToken(token);
				if (model != null) {
					text = model.getContent();
				}
			} catch (Exception ex) {
			}
		}

		SysUser bean = null;

		if (StringUtils.isNotEmpty(text)) {
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
				if (conf.getBoolean("mq.verifyClientIP", false)) {
					// 如果MQ接收的登录地址和现在客户远程地址不一致，不允许登录
					if (!StringUtils.equals(clientIP, ipAddr)) {
						return null;
					}
				}

				if (StringUtils.equals(userId, actorId) && StringUtils.equals(token, token2)) {
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

						/**
						 * 判断是否允许用户从MQ登录
						 */
						if (!StringUtils.equals(bean.getMqLoginFlag(), "Y")) {
							bean = null;
						}

						UserOnlineLogService userOnlineLogService = ContextFactory.getBean("userOnlineLogService");
						// 如果每天登录次数超过设置值，设置用户对象为空，防止恶意操作
						if (bean != null
								&& userOnlineLogService.getLoginCount(userId) > conf.getInt("limit.loginCount", 1000)) {
							bean = null;
						}
					}
				}
			}
		}

		return bean;
	}

}
