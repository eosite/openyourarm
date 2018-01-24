package com.glaf.base.ws;

import javax.jws.WebService;

import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.identity.User;

@WebService(endpointInterface = "com.glaf.base.ws.IUserWebService", serviceName = "wsUserService")
public class UserWebServiceImpl implements IUserWebService {

	public String getUserJson(String userId) {
		SysUserService sysUserService = ContextFactory.getBean("sysUserService");
		User user = sysUserService.findByAccount(userId);
		if (user != null) {
			JSONObject json = user.toJsonObject();
			json.remove("token");
			return json.toJSONString();
		}
		return "";
	}

	public static void main(String[] args) {
		System.setProperty("config.path", ".");
		SysUserService sysUserService = ContextFactory.getBean("sysUserService");
		User user = sysUserService.findByAccount("admin");
		if (user != null) {
			System.out.println(user.toJsonObject().toJSONString());
		}
	}

}
