package com.glaf.base.modules.sys.callback;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.Hex;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.SignUtils;
import com.glaf.core.util.http.HttpUtils;
import com.glaf.core.web.callback.LoginCallback;

import com.glaf.base.modules.sys.model.LoginModule;
import com.glaf.base.modules.sys.model.LoginToken;
import com.glaf.base.modules.sys.query.LoginModuleQuery;
import com.glaf.base.modules.sys.service.LoginModuleService;
import com.glaf.base.modules.sys.service.LoginTokenService;

public class RSALoginCallback implements LoginCallback {
	private static final Log logger = LogFactory.getLog(RSALoginCallback.class);

	@Override
	public void afterLogin(String actorId, HttpServletRequest request, HttpServletResponse response) {
		LoginModuleService loginModuleService = ContextFactory.getBean("loginModuleService");
		LoginTokenService loginTokenService = ContextFactory.getBean("loginTokenService");
		LoginModuleQuery query = new LoginModuleQuery();
		query.type("client_rsa");
		query.locked(0);
		List<LoginModule> list = loginModuleService.list(query);
		if (list != null && !list.isEmpty()) {
			loginTokenService.deleteLoginTokenByUserId(actorId);
			JSONObject json = new JSONObject();
			String clientIP = RequestUtils.getIPAddress(request);
			json.put("clientIP", clientIP);
			for (LoginModule bean : list) {
				try {
					String userId = actorId;
					if (StringUtils.isNotEmpty(bean.getLoginUserId())) {
						userId = bean.getLoginUserId();
					}
					logger.debug("prepare get remote server token...");
					String link = bean.getUrl() + "/website/rsa_login/getToken?systemCode=" + bean.getSystemCode()
							+ "&userId=" + userId;
					logger.debug("link:" + link);
					String text = HttpUtils.doPost(link);
					logger.debug("get response data:" + text);
					byte[] data = Hex.hex2byte(text);
					text = new String(loginModuleService.decryptText(bean.getId(), data));
					logger.debug("text:" + text);
					if (StringUtils.isNotEmpty(text)) {
						userId = actorId;
						if (StringUtils.isNotEmpty(bean.getLoginUserId())) {
							userId = bean.getLoginUserId();
						}
						JSONObject jsonObject = JSON.parseObject(text);
						String token = jsonObject.getString("token");
						String timestamp = jsonObject.getString("timestamp");
						String nonce = jsonObject.getString("nonce");
						String signature = SignUtils.getSignature(token, timestamp, nonce);
						json.put("signature", signature);
						String sendText = json.toJSONString();
						data = loginModuleService.encryptText(bean.getId(), sendText.getBytes());
						sendText = Hex.byte2hex(data);
						link = bean.getUrl() + "/website/rsa_login/saveToken?systemCode=" + bean.getSystemCode()
								+ "&text=" + sendText;
						logger.debug("link:" + link);
						text = HttpUtils.doPost(link);
						logger.debug("response:" + text);
						if (StringUtils.equals(text, "OK")) {
							LoginToken tk = new LoginToken();
							tk.setUserId(userId);
							tk.setClientIP(clientIP);
							tk.setCreateTime(new Date());
							tk.setLoginModuleId(bean.getId());
							tk.setSignature(signature);
							tk.setToken(token);
							tk.setTimeMillis(Long.parseLong(timestamp));
							tk.setNonce(nonce);
							loginTokenService.save(tk);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error(bean.getTitle() + " login error", ex);
				}
			}
		}
	}

}
