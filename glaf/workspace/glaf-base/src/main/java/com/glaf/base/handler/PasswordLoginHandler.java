package com.glaf.base.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.online.service.UserOnlineLogService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.StringTools;

public class PasswordLoginHandler implements LoginHandler {
	private static final Log logger = LogFactory.getLog(PasswordLoginHandler.class);

	private static Configuration conf = BaseConfiguration.create();

	@Override
	public SysUser doLogin(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("----------------------PasswordLoginHandler--------------------");
		String account = ParamUtil.getParameter(request, "x");
		String password = ParamUtil.getParameter(request, "y");

		HttpSession session = request.getSession(false);
		String rand = (String) session.getAttribute("x_y");
		String rand2 = (String) session.getAttribute("x_z");

		SysUser bean = null;

		if (StringUtils.isNotEmpty(rand) && StringUtils.isNotEmpty(rand2)) {
			if (rand != null) {
				password = StringTools.replace(password, rand, "");
			}

			if (rand2 != null) {
				password = StringTools.replace(password, rand2, "");
			}

			logger.debug(account + " start login........................");
			logger.debug("currentSystemName:" + Environment.getCurrentSystemName());

			// 用户登陆，返回系统用户对象
			AuthorizeService authorizeService = ContextFactory.getBean("authorizeService");
			bean = authorizeService.authorize(account, password);
			logger.debug("current authorize User--->"+account);
			UserOnlineLogService userOnlineLogService = ContextFactory.getBean("userOnlineLogService");
			// 如果每天登录次数超过设置值，设置用户对象为空，防止恶意操作
			if (userOnlineLogService.getLoginCount(account) > conf.getInt("limit.loginCount", 1000)) {
				bean = null;
			}
			logger.debug("sysuser is null --->"+(bean == null));
		}

		return bean;
	}

}
