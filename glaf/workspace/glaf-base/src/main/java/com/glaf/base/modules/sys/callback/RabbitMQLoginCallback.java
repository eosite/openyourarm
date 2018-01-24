package com.glaf.base.modules.sys.callback;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ServerEntity;
import com.glaf.core.mq.rabbitmq.PublishProducer;
import com.glaf.core.security.DESUtils;
import com.glaf.core.security.SecurityUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.SignUtils;
import com.glaf.core.util.http.HttpUtils;
import com.glaf.core.web.callback.LoginCallback;

import com.glaf.base.modules.sys.model.LoginModule;
import com.glaf.base.modules.sys.model.LoginToken;
import com.glaf.base.modules.sys.query.LoginModuleQuery;
import com.glaf.base.modules.sys.service.LoginModuleService;
import com.glaf.base.modules.sys.service.LoginTokenService;

public class RabbitMQLoginCallback implements LoginCallback {
	private static final Log logger = LogFactory.getLog(RabbitMQLoginCallback.class);

	@Override
	public void afterLogin(String actorId, HttpServletRequest request, HttpServletResponse response) {
		LoginModuleService loginModuleService = ContextFactory.getBean("loginModuleService");
		LoginTokenService loginTokenService = ContextFactory.getBean("loginTokenService");
		LoginModuleQuery query = new LoginModuleQuery();
		query.type("client");
		query.locked(0);
		List<LoginModule> list = loginModuleService.list(query);
		if (list != null && !list.isEmpty()) {
			loginTokenService.deleteLoginTokenByUserId(actorId);
			PublishProducer producer = null;
			JSONObject json = new JSONObject();
			String clientIP = RequestUtils.getIPAddress(request);
			json.put("clientIP", clientIP);

			for (LoginModule bean : list) {
				if (bean.getServerEntity() != null
						&& StringUtils.equals(bean.getServerEntity().getType(), "rabbitmq")) {
					try {
						ServerEntity serverEntity = bean.getServerEntity();
						logger.debug("prepare get remote server token...");
						String userId = actorId;
						if (StringUtils.isNotEmpty(bean.getLoginUserId())) {
							userId = bean.getLoginUserId();
						}
						if (StringUtils.equals(serverEntity.getSecretAlgorithm(), "DES")
								&& StringUtils.isNotEmpty(serverEntity.getSecretKey())) {
							/**
							 * 通过3DES加密请求的用户编号
							 */
							byte[] binaryData = DESUtils.ecrypt3DES(userId.getBytes(),
									DESUtils.getKeyBytes(serverEntity.getSecretKey()),
									DESUtils.getKeyIvBytes(serverEntity.getSecretIv()));
							userId = DESUtils.byte2hex(binaryData);
						}
						String link = bean.getUrl() + "/website/token/getToken?appId=" + bean.getAppId() + "&appSecret="
								+ DigestUtils.sha512Hex(bean.getAppSecret()) + "&userId=" + userId;
						logger.debug("link:" + link);
						String text = HttpUtils.doPost(link);
						logger.debug("get response data:" + text);

						byte[] data = DESUtils.hex2byte(userId.getBytes());
						byte[] binaryData2 = DESUtils.decrypt3DES(data,
								DESUtils.getKeyBytes(serverEntity.getSecretKey()),
								DESUtils.getKeyIvBytes(serverEntity.getSecretIv()));
						logger.debug("->userId:" + new String(binaryData2));
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

							json.put("timestamp", timestamp);
							json.put("nonce", nonce);
							json.put("signature", signature);

							producer = new PublishProducer(serverEntity.getDbname());
							producer.setHost(serverEntity.getHost());
							producer.setPort(serverEntity.getPort());
							producer.setUsername(serverEntity.getUser());
							String password = SecurityUtils.decode(serverEntity.getKey(), serverEntity.getPassword());
							producer.setPassword(password);
							String sendText = json.toJSONString();
							if (StringUtils.equals(serverEntity.getSecretAlgorithm(), "DES")
									&& StringUtils.isNotEmpty(serverEntity.getSecretKey())) {
								/**
								 * 通过3DES加密发送的消息
								 */
								byte[] binaryData = DESUtils.ecrypt3DES(sendText.getBytes(),
										DESUtils.getKeyBytes(serverEntity.getSecretKey()),
										DESUtils.getKeyIvBytes(serverEntity.getSecretIv()));
								sendText = DESUtils.byte2hex(binaryData);
							}

							producer.sendBytesMessage("fanout", sendText.getBytes());
							logger.debug("send rabbitmq login message:" + json.toJSONString() + " has sent.");
							LoginToken tk = new LoginToken();
							tk.setUserId(userId);
							tk.setClientIP(clientIP);
							tk.setCreateTime(new Date());
							tk.setLoginModuleId(bean.getId());
							tk.setToken(jsonObject.getString("token"));
							tk.setTimeMillis(Long.parseLong(timestamp));
							tk.setNonce(nonce);
							tk.setSignature(signature);
							if (bean.getTimeLive() > 0) {
								tk.setTimeLive(bean.getTimeLive());
							} else {
								tk.setTimeLive(60);
							}
							loginTokenService.save(tk);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error(bean.getTitle() + " login error", ex);
					}
				}
			}
		}
	}

}
