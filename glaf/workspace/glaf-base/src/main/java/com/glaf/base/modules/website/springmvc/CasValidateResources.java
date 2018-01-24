package com.glaf.base.modules.website.springmvc;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.base.modules.uis.domain.UisAppRegister;
import com.glaf.base.modules.uis.service.UisAppRegisterService;
import com.glaf.base.utils.CasClientUtils;
import com.glaf.core.config.SystemConfig;

@Controller("/validate")
@RequestMapping("/validate")
public class CasValidateResources {
	protected static final Log logger = LogFactory.getLog(CasValidateResources.class);

	private UisAppRegisterService appRegisterService;

	@ResponseBody
	@RequestMapping(produces = "text/xml;charset=utf-8")
	public byte[] validate(@Context HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		String appId = request.getParameter("appId");
		if (StringUtils.isEmpty(appId)) {
			logger.error("注册appId未指定");
			return null;
		}

		String ticket = request.getParameter("ticket");
		if (StringUtils.isEmpty(ticket)) {
			logger.error("未获取到客户端服务登录的有效票据");
			return null;
		}
		// 获取ticket验证地址
		String validate_address = SystemConfig.getString("sso_validate_address");
		if (StringUtils.isEmpty(validate_address)) {
			logger.error("客户端登录票据验证服务地址未定义,请到系统参数页面进行配置");
			return null;
		}
		// 获取SSO当前注册应用的服务id
		String service = request.getParameter("service");
		if (StringUtils.isEmpty(service)) {
			// 根据appId获取服务id
			UisAppRegister uisAppRegister = appRegisterService.getUisAppRegister(appId);
			if (uisAppRegister != null) {
				service = uisAppRegister.getSsoServiceId();
			}
		}
		if (StringUtils.isEmpty(service)) {
			String path = request.getContextPath();
			service = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
					+ "/";
		}
		// 获取st验证结果信息
		String userinfo = CasClientUtils.getTicketValidateInfo(validate_address, ticket, service, appId);
		return userinfo.getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping(value = "ticket", produces = "text/html;charset=utf-8")
	public byte[] getTicket(@Context HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		String tgt = request.getParameter("TGT");
		if (StringUtils.isEmpty(tgt)) {
			// 从cookie获取
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("TGT")) {
					tgt = cookie.getValue();
					break;
				}
			}
		}
		if (StringUtils.isEmpty(tgt)) {
			logger.error("登录TGT未发现");
			return null;
		}
		String appId = request.getParameter("appId");
		if (StringUtils.isEmpty(appId)) {
			logger.error("注册appId未指定");
			return null;
		}
		// 获取SSO当前注册应用的服务id
		String service = request.getParameter("service");
		if (StringUtils.isEmpty(service)) {
			// 根据appId获取服务id
			UisAppRegister uisAppRegister = appRegisterService.getUisAppRegister(appId);
			if (uisAppRegister != null) {
				service = uisAppRegister.getSsoServiceId();
			}
		}
		if (StringUtils.isEmpty(service)) {
			String path = request.getContextPath();
			service = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
					+ "/";
		}
		// 获取ticket服务地址
		String ticketServer = SystemConfig.getString("ticket_service_address");
		if (StringUtils.isEmpty(ticketServer)) {
			logger.error("SSO认证服务器未定义,请到系统参数页面进行配置");
			return null;
		}
		String ticket = CasClientUtils.getServiceTicket(ticketServer, tgt, service, appId);
		return ticket.getBytes("UTF-8");
	}

	@Resource(name = "com.glaf.base.modules.uis.service.uisAppRegisterService")
	public void setAppRegisterService(UisAppRegisterService appRegisterService) {
		this.appRegisterService = appRegisterService;
	}
}
