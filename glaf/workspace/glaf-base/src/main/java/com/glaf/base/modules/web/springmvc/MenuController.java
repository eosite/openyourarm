package com.glaf.base.modules.web.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glaf.base.business.AuthorizeBean;
import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.RequestUtils;

@Controller("menu")
@RequestMapping("/menu")
public class MenuController {

	protected static final Log logger = LogFactory.getLog(MenuController.class);

	private SysApplicationService sysApplicationService;

	@RequestMapping("/jump")
	public void jump(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("---------------------------jump----------------------");
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (loginContext == null) {
			try {
				response.sendRedirect(request.getContextPath()
						+ ViewProperties.getString("loginUrl"));
				return;
			} catch (Exception ex) {
			}
		}
		String menuId = request.getParameter("menuId");
		if (menuId != null) {
			menuId = RequestUtils.decodeString(menuId);
		}
		logger.debug("menuId:" + menuId);
		if (menuId != null && StringUtils.isNumeric(menuId)) {
			SysApplication app = sysApplicationService.findById(Long
					.parseLong(menuId));
			if (app != null) {
				boolean accessable = false;
				if (loginContext.isSystemAdministrator()) {
					accessable = true;
				} else {
					AuthorizeBean bean = new AuthorizeBean();
					SysUser sysUser = bean.getUser(loginContext.getActorId());
					if (sysUser != null) {
						accessable = sysUser.hasApplicationAccess(app.getId());
					}
				}
				logger.debug("accessable:" + accessable);
				if (accessable) {
					try {
						String url = app.getUrl();
						if (url != null) {
							if (!(url.toLowerCase().startsWith("http://") || url
									.toLowerCase().startsWith("https://"))) {
								if (url.startsWith("/")) {
									url = request.getContextPath() + url;
								} else {
									url = request.getContextPath() + "/" + url;
								}
							}
							if (url.indexOf("?") != -1) {
								url = url + "&time="
										+ System.currentTimeMillis();
							} else {
								url = url + "?time="
										+ System.currentTimeMillis();
							}
							response.sendRedirect(url);
						} else {
							return;
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		try {
			request.getRequestDispatcher("/WEB-INF/views/404.jsp").forward(
					request, response);
		} catch (Exception e) {
		}
	}

	@javax.annotation.Resource
	public void setSysApplicationService(
			SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
	}

}
