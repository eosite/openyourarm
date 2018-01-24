package com.glaf.base.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.glaf.base.modules.sys.model.SysUser;

public interface LoginHandler {

	SysUser doLogin(HttpServletRequest request, HttpServletResponse response);

}
