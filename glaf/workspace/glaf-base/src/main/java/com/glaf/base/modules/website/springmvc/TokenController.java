/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.base.modules.website.springmvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.sys.model.LoginModule;
import com.glaf.base.modules.sys.model.LoginToken;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.LoginModuleService;
import com.glaf.base.modules.sys.service.LoginTokenService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.core.config.Configuration;
import com.glaf.core.domain.ServerEntity;
import com.glaf.core.security.DESUtils;
import com.glaf.core.security.LoginContext;
import com.glaf.core.security.RSAUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.SignUtils;
import com.glaf.core.util.UUID32;

@Controller("/token")
@RequestMapping("/token")
public class TokenController {
	protected static final Log logger = LogFactory.getLog(TokenController.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected SysUserService sysUserService;

	protected LoginModuleService loginModuleService;

	protected LoginTokenService loginTokenService;

	protected boolean checkToken(HttpServletRequest request, HttpServletResponse response) {
		String token_name = request.getParameter("token_name");
		String token = conf.get("login_token");
		if (StringUtils.isNotEmpty(token_name)) {
			token = conf.get("login_token_" + token_name);
		}
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		if (StringUtils.isNotEmpty(token) && StringUtils.isNotEmpty(signature) && StringUtils.isNotEmpty(timestamp)
				&& StringUtils.isNotEmpty(nonce) && SignUtils.checkToken(token, signature, timestamp, nonce)) {
			// 验证通过
			return true;
		}
		return false;
	}

	@RequestMapping("/encrypt")
	public void encrypt(HttpServletRequest request, HttpServletResponse response) {
		if (this.checkToken(request, response)) {
			String secret = request.getParameter("s");
			if (StringUtils.isNotEmpty(secret)) {
				PrintWriter out = null;
				try {
					out = response.getWriter();
					response.setContentType("text/plain");
					out.write(RSAUtils.encryptString(secret));
					out.flush();
				} catch (IOException ex) {
					ex.printStackTrace();
				} finally {
					IOUtils.closeStream(out);
				}
			}
		}
	}

	@ResponseBody
	@RequestMapping("/getToken")
	public void getToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain; charset=UTF-8");
		String appId = request.getParameter("appId");
		String appSecret = request.getParameter("appSecret");
		String userId = request.getParameter("userId");
		if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(appId) && StringUtils.isNotEmpty(appSecret)) {
			PrintWriter writer = null;
			try {
				LoginModule model = loginModuleService.getLoginModuleByAppId(appId);
				if (model != null && model.getLocked() == 0 && model.getServerEntity() != null) {
					if (StringUtils.equals(appSecret, DigestUtils.sha512Hex(model.getAppSecret()))) {
						ServerEntity serverEntity = model.getServerEntity();
						if (StringUtils.equals(serverEntity.getSecretAlgorithm(), "DES")
								&& StringUtils.isNotEmpty(serverEntity.getSecretKey())) {
							/**
							 * 通过3DES解密请求的用户编号
							 */
							byte[] data = DESUtils.hex2byte(userId.getBytes());
							byte[] binaryData = DESUtils.decrypt3DES(data,
									DESUtils.getKeyBytes(serverEntity.getSecretKey()),
									DESUtils.getKeyIvBytes(serverEntity.getSecretIv()));
							userId = new String(binaryData);
						}
						LoginContext loginContext = com.glaf.core.security.IdentityFactory.getLoginContext(userId);
						if (loginContext != null && !loginContext.isSystemAdministrator()) {
							long ts = System.currentTimeMillis();
							JSONObject json = new JSONObject();
							json.put("token", UUID32.getUUID() + DigestUtils.sha512Hex(userId + "_" + ts));
							json.put("nonce", UUID32.getUUID());
							json.put("timestamp", ts);

							LoginToken bean = new LoginToken();
							bean.setUserId(userId);
							bean.setClientIP(RequestUtils.getIPAddress(request));
							bean.setCreateTime(new Date());
							bean.setToken(json.getString("token"));
							bean.setNonce(json.getString("nonce"));
							bean.setLoginModuleId(model.getId());
							bean.setTimeLive(model.getTimeLive());
							bean.setTimeMillis(ts);
							String signature = SignUtils.getSignature(json.getString("token"), String.valueOf(ts),
									json.getString("nonce"));
							bean.setSignature(signature);
							loginTokenService.save(bean);

							logger.debug(json.toJSONString());
							writer = response.getWriter();
							writer.write(json.toJSONString());
							writer.flush();
							return;
						} else {
							logger.warn("不允许远程登录账户" + userId + "！");
						}
					}
				}
				logger.warn("密锁不合法！");
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			} finally {
				if (writer != null) {
					writer.close();
				}
			}
		}
	}

	@RequestMapping("/md5Hex")
	public void md5Hex(HttpServletRequest request, HttpServletResponse response) {
		String secret = request.getParameter("s");
		if (StringUtils.isNotEmpty(secret)) {
			PrintWriter out = null;
			try {
				out = response.getWriter();
				response.setContentType("text/plain");
				out.write(DigestUtils.md5Hex(secret));
				out.flush();
			} catch (IOException ex) {
			} finally {
				IOUtils.closeStream(out);
			}
		}
	}

	@javax.annotation.Resource
	public void setLoginModuleService(LoginModuleService loginModuleService) {
		this.loginModuleService = loginModuleService;
	}

	@javax.annotation.Resource
	public void setLoginTokenService(LoginTokenService loginTokenService) {
		this.loginTokenService = loginTokenService;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	/**
	 * 提交修改信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping
	public void token(HttpServletRequest request, HttpServletResponse response) {
		String appId = request.getParameter("appid");
		String secret = request.getParameter("secret");
		if (StringUtils.isNotEmpty(appId) && StringUtils.isNotEmpty(secret)) {
			SysUser user = sysUserService.getSysUserByAppId(appId);
			if (user != null && user.getAppSecret() != null) {
				if (StringUtils.equals(secret, DigestUtils.md5Hex(user.getAppSecret()))) {
					if (user.getTokenTime() != null) {
						/**
						 * Token有效期为2小时
						 */
						if (System.currentTimeMillis() - user.getTokenTime().getTime() > 7200000) {
							user = sysUserService.resetUserToken(user.getAccount());
						}
					}
					if (user != null && user.getToken() != null) {
						PrintWriter out = null;
						try {
							out = response.getWriter();
							response.setContentType("text/plain");
							out.write(user.getToken());
							out.flush();
						} catch (IOException ex) {
						} finally {
							IOUtils.closeStream(out);
						}
					}
				}
			}
		}
	}

}