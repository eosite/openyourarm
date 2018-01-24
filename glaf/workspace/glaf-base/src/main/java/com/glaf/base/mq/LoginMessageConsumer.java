package com.glaf.base.mq;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.glaf.core.cache.CacheFactory;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ServerEntity;
import com.glaf.core.identity.User;
import com.glaf.core.security.DESUtils;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.SecurityUtils;

import com.glaf.base.modules.sys.model.LoginMessage;
import com.glaf.base.modules.sys.model.LoginToken;
import com.glaf.base.modules.sys.service.LoginMessageService;
import com.glaf.base.modules.sys.service.LoginTokenService;
import com.glaf.base.utils.LoginContextUtil;

/**
 * 用于其他系统通过MQ登录本系统的消息消费
 *
 */
public class LoginMessageConsumer {
	private static final Log logger = LogFactory.getLog(LoginMessageConsumer.class);

	public void processMessage(String message, ServerEntity serverEntity) {
		LoginTokenService loginTokenService = ContextFactory.getBean("loginTokenService");
		LoginMessageService loginMessageService = ContextFactory.getBean("loginMessageService");
		try {
			if (serverEntity != null) {
				if (StringUtils.equals(serverEntity.getSecretAlgorithm(), "DES")
						&& StringUtils.isNotEmpty(serverEntity.getSecretKey())) {
					/**
					 * 通过3DES解密接收到的消息
					 */
					byte[] data = DESUtils.hex2byte(message.getBytes());
					byte[] binaryData = DESUtils.decrypt3DES(data,
							SecurityUtils.getKeyBytes(serverEntity.getSecretKey()),
							SecurityUtils.getKeyIvBytes(serverEntity.getSecretIv()));
					message = new String(binaryData);
					logger.debug("3DES解密后的消息:" + message);
				}
			}
			JSONObject json = JSON.parseObject(message);
			if (json.containsKey("clientIP") && json.containsKey("signature")) {
				String signature = json.getString("signature");
				LoginToken token = loginTokenService.getLoginTokenBySignature(signature);
				if (token != null) {
					String userId = token.getUserId();
					User user = IdentityFactory.getUser(userId);
					/**
					 * 用户存在并且有效
					 */
					if (user != null && user.getLocked() != 1) {
						String cacheKey = json.getString("clientIP") + "_" + userId;
						String clientIP = json.getString("clientIP");
						String section = json.getString("section");
						// 防止恶意发送登录消息
						int total = loginMessageService.getTodayLoginCountByUserId(userId);
						logger.debug("total:" + total);
						if (total < 2000) {
							LoginMessage model = new LoginMessage();
							model.setClientIP(clientIP);
							model.setContent(message);
							model.setLoginTime(token.getTimeMillis());
							model.setTimeLive(token.getTimeLive());
							model.setToken(token.getToken());
							model.setServerId(serverEntity.getId());
							model.setUserId(userId);
							model.setSection(section);// 标段信息
							model.setUid(json.getString("uid"));
							model.setFlowid(json.getString("flowid"));
							model.setPosition(json.getString("position"));
							model.setCellTreedotIndex(json.getString("cellTreedotIndex"));
							loginMessageService.save(model);
							LoginContextUtil.put(cacheKey, json.toJSONString());
							CacheFactory.put("loginMessage", cacheKey, json.toJSONString());
							logger.debug("save message");
						} else {
							logger.debug("ignore message:" + message);
						}
					}
				}
			} else {
				String userId = json.getString("userId");
				String token = json.getString("token");
				if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(token)) {
					User user = IdentityFactory.getUser(userId);
					/**
					 * 用户存在并且有效
					 */
					if (user != null && user.getLocked() != 1) {
						String cacheKey = json.getString("clientIP") + "_" + userId;
						String clientIP = json.getString("clientIP");
						String section = json.getString("section");
						// 防止恶意发送登录消息
						int total = loginMessageService.getTodayLoginCountByUserId(userId);
						logger.debug("total:" + total);
						if (total < 2000) {
							LoginMessage model = new LoginMessage();
							model.setClientIP(clientIP);
							model.setContent(message);
							model.setLoginTime(System.currentTimeMillis());
							model.setTimeLive(600);
							model.setToken(token);
							model.setServerId(serverEntity.getId());
							model.setUserId(userId);
							model.setSection(section);// 标段信息
							model.setUid(json.getString("uid"));
							model.setFlowid(json.getString("flowid"));
							model.setPosition(json.getString("position"));
							model.setCellTreedotIndex(json.getString("cellTreedotIndex"));
							loginMessageService.save(model);
							LoginContextUtil.put(cacheKey, json.toJSONString());
							CacheFactory.put("loginMessage", cacheKey, json.toJSONString());
							logger.debug("save message");
						} else {
							logger.debug("ignore message:" + message);
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
	}
}
