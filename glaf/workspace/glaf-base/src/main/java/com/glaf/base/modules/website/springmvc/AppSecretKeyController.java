package com.glaf.base.modules.website.springmvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.LoginModule;
import com.glaf.base.modules.sys.service.LoginModuleService;
import com.glaf.core.security.SecurityUtils;
import com.glaf.core.util.StringTools;

@Controller("/secretKey")
@RequestMapping("/secretKey")
public class AppSecretKeyController {

	protected LoginModuleService loginModuleService;

	@ResponseBody
	@RequestMapping
	public byte[] getSecurityKey(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		JSONObject json = new JSONObject();
		String srcsystem = request.getParameter("srcsystem");
		String token = request.getParameter("token");
		if (StringUtils.isNotEmpty(srcsystem) && StringUtils.isNotEmpty(token)) {
			LoginModule loginModule = loginModuleService.getLoginModuleByToken(token);
			if (loginModule != null && StringUtils.equals(srcsystem, loginModule.getSystemCode())) {
				byte[] key = SecurityUtils.getKeyBytes(loginModule.getAppId());
				byte[] keyIV = SecurityUtils.getKeyIvBytes(loginModule.getAppSecret());
				String key_str = Base64.encodeBase64String(key);
				String keyIV_str = Base64.encodeBase64String(keyIV);

				key_str = StringTools.replace(key_str, "+", "_a");
				key_str = StringTools.replace(key_str, "=", "_b");
				key_str = StringTools.replace(key_str, "/", "_c");

				keyIV_str = StringTools.replace(keyIV_str, "+", "_a");
				keyIV_str = StringTools.replace(keyIV_str, "=", "_b");
				keyIV_str = StringTools.replace(keyIV_str, "/", "_c");
				json.put("secretKey", key_str);
				json.put("secretIv", keyIV_str);
				json.put("secretKey_md5Hex", DigestUtils.md5Hex(key));
				json.put("secretIv_md5Hex", DigestUtils.md5Hex(keyIV));
			}
		}
		return json.toJSONString().getBytes("UTF-8");
	}

	@javax.annotation.Resource
	public void setLoginModuleService(LoginModuleService loginModuleService) {
		this.loginModuleService = loginModuleService;
	}
}
