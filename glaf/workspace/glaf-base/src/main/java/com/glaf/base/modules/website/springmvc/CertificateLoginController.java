package com.glaf.base.modules.website.springmvc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.glaf.base.callback.LoginCallbackThread;
import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.online.domain.UserOnline;
import com.glaf.base.online.service.UserOnlineLogService;
import com.glaf.base.online.service.UserOnlineService;
import com.glaf.base.utils.ContextUtil;

import com.glaf.core.config.Configuration;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.domain.SystemProperty;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.web.callback.CallbackProperties;
import com.glaf.core.web.callback.LoginCallback;

@Controller("/certificatelogin")
@RequestMapping("/certificatelogin")
public class CertificateLoginController {
	protected static final Log logger = LogFactory.getLog(CertificateLoginController.class);

	protected static Configuration conf = BaseConfiguration.create();

	private SysUserService sysUserService;

	private UserOnlineService userOnlineService;

	private UserOnlineLogService userOnlineLogService;

	protected AuthorizeService authorizeService;

	private void addAttribute(Element attributesElement, String name, String namespace) {
		Element attr = attributesElement.addElement("attr");
		attr.addAttribute("name", name);
		attr.addAttribute("namespace", namespace);
	}

	public byte[] createReqMessageXml(String remoteAddr, String attrType, String appId, String signed_data,
			String original_data) throws Exception {
		byte[] messagexml = null;
		Document reqDocument = DocumentHelper.createDocument();
		Element root = reqDocument.addElement("message");
		Element requestHeadElement = root.addElement("head");
		Element requestBodyElement = root.addElement("body");

		requestHeadElement.addElement("version").setText("1.0");
		requestHeadElement.addElement("serviceType").setText("AuthenService");

		Element clientInfoElement = requestBodyElement.addElement("clientInfo");

		Element clientIPElement = clientInfoElement.addElement("clientIP");

		clientIPElement.setText(remoteAddr);

		requestBodyElement.addElement("appId").setText(appId);

		Element authenElement = requestBodyElement.addElement("authen");

		Element authCredentialElement = authenElement.addElement("authCredential");

		authCredentialElement.addAttribute("authMode", "cert");

		authCredentialElement.addElement("detach").setText(signed_data);
		authCredentialElement.addElement("original").setText(original_data);

		requestBodyElement.addElement("accessControl").setText("true");

		Element attributesElement = requestBodyElement.addElement("attributes");
		if ((attrType == null) || (attrType.equals(""))) {
			throw new ServletException("属性列表控制项为空，请确认配置！！！");
		}
		attributesElement.addAttribute("attributeType", attrType);

		if (attrType.equals("portion")) {
			String attributes = getProperties("attributes");
			if ((attributes != null) && (!"".equals(attributes))) {
				String[] attrs = attributes.split(";");
				int num = attrs.length;
				for (int i = 0; i < num; i++) {
					String att = attrs[i];
					if ((att.contains("X509")) || (att.contains("_saml"))) {
						addAttribute(attributesElement, att, "http://www.jit.com.cn/cinas/ias/ns/saml/saml11/X.509");
					} else {
						addAttribute(attributesElement, att, "http://www.jit.com.cn/ums/ns/user");
						addAttribute(attributesElement, att, "http://www.jit.com.cn/pmi/pms/ns/role");
						addAttribute(attributesElement, att, "http://www.jit.com.cn/pmi/pms/ns/privilege");
					}

				}
			}
		}

		StringBuffer reqMessageData = new StringBuffer();
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			XMLWriter writer = new XMLWriter(outStream);
			writer.write(reqDocument);
			messagexml = outStream.toByteArray();

			reqMessageData.append("请求内容开始！\n");
			reqMessageData.append(outStream.toString() + "\n");
			reqMessageData.append("请求内容结束！\n");
			logger.info(reqMessageData.toString() + "\n");
		} catch (Exception ex) {
			logger.error("组装请求时出现异常");
			throw ex;
		}
		return messagexml;
	}

	private String generateRandomNum() {
		String num = "1234567890abcdefghijklmnopqrstopqrstuvwxyz";
		int size = 6;
		char[] charArray = num.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(charArray[((int) (java.lang.Math.random() * 10000.0D) % charArray.length)]);
		}
		return sb.toString();
	}

	private String getProperties(String key) {
		return BaseDataManager.getInstance().getValue(key, "certificate").getValue();
	}

	@RequestMapping("/randNum")
	@ResponseBody
	public byte[] getRandomString(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String randNum = generateRandomNum();
		if ((randNum == null) || (randNum.trim().equals(""))) {
			logger.info("证书认证数据不完整！");
			response.setStatus(500);
		}

		request.getSession().setAttribute("original_data", randNum);

		// request.setAttribute("original", randNum);
		JSONObject json = new JSONObject();
		json.put("randNum", randNum);
		logger.debug("---------------------randNum----------------------------------");
		logger.debug(json.toJSONString());
		logger.debug("session original_data->" + request.getSession().getAttribute("original_data"));
		return json.toJSONString().getBytes("UTF-8");
	}

	public String getRespMessageXml(String authURL, byte[] messagexml) throws Exception {
		int statusCode = 500;
		HttpClient httpClient = null;
		PostMethod postMethod = null;
		httpClient = new HttpClient();
		// httpClient.setConnectionTimeout(10000);
		// httpClient.setTimeout(20000);
		postMethod = new PostMethod(authURL);
		postMethod.setRequestHeader("Connection", "close");
		postMethod.setRequestHeader("Content-Type", "text/xml;charset=UTF-8");
		postMethod.setRequestBody(new ByteArrayInputStream(messagexml));
		StringBuffer respMessageData = new StringBuffer();
		String respMessageXml = null;
		try {
			statusCode = httpClient.executeMethod(postMethod);
			if ((statusCode == 200) || (statusCode == 500)) {
				try {
					byte[] inputstr = postMethod.getResponseBody();

					ByteArrayInputStream ByteinputStream = new ByteArrayInputStream(inputstr);
					ByteArrayOutputStream outStream = new ByteArrayOutputStream();
					int ch = 0;
					try {
						while ((ch = ByteinputStream.read()) != -1) {
							int upperCh = (char) ch;
							outStream.write(upperCh);
						}
						if (statusCode == 200) {
							respMessageData.append("响应内容开始！\n");
							respMessageData.append(new String(outStream.toByteArray(), "UTF-8") + "\n");
							respMessageData.append("响应内容开始！\n");
							respMessageXml = new String(outStream.toByteArray(), "UTF-8");
						} else {
							respMessageData.append("响应500内容开始！\n");
							respMessageData.append(new String(outStream.toByteArray()) + "\n");
							respMessageData.append("响应500内容结束！\n");
						}
						logger.info(respMessageData.toString() + "\n");
					} catch (Exception e) {

					}

				} catch (IOException e) {
					logger.info("读取认证响应报文出现异常！");
					logger.error(e.getMessage());
					throw e;
				} finally {
					if (httpClient != null) {
						postMethod.releaseConnection();
						httpClient.getHttpConnectionManager().closeIdleConnections(0L);
					}
				}
			}
		} catch (Exception e) {
			logger.info("与网关连接出现异常\n");
			logger.error(e.getMessage());
			throw e;
		} finally {
			postMethod.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0L);
			httpClient = null;
		}

		return respMessageXml;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		boolean isSuccess = false;
		String errCode = null;
		String errDesc = null;
		String appId = getProperties("appId");
		String authURL = getProperties("authURL");
		String attrType = getProperties("attrType");
		if ((!StringUtils.isNotEmpty(appId)) || (!StringUtils.isNotEmpty(authURL))) {
			isSuccess = false;
			errDesc = "应用标识或网关认证地址不可为空";
			logger.error("应用标识或网关认证地址不可为空\n");
			return this.prepareLogin(request, response, modelMap);
		}
		String original_data = null;
		String signed_data = null;
		String original_jsp = null;
		// String username = null;
		// String password = null;
		logger.info("应用标识及网关的认证地址读取成功！\n应用标识：" + appId + "\n认证地址：" + authURL + "\n");

		logger.debug("original_data:" + request.getSession().getAttribute("original_data"));
		logger.debug("original_jsp:" + request.getSession().getAttribute("original_jsp"));
		if ((StringUtils.isNotEmpty((String) request.getSession().getAttribute("original_data")))
				&& (StringUtils.isNotEmpty(request.getParameter("signed_data")))) {
			if (StringUtils.isNotEmpty(request.getParameter("original_jsp"))) {
				original_data = (String) request.getSession().getAttribute("original_data");

				original_jsp = request.getParameter("original_jsp");

				if (!original_data.equalsIgnoreCase(original_jsp)) {
					isSuccess = false;
					errDesc = "客户端提供的认证原文与服务端的不一致";
					logger.warn("客户端提供的认证原文与服务端的不一致！\n");
					return this.prepareLogin(request, response, modelMap);
				}
				signed_data = request.getParameter("signed_data");
				original_data = Base64.encodeBase64String(original_jsp.getBytes());
				logger.info("读取认证原文和认证请求包成功！\n认证原文：" + original_jsp + "\n认证请求包：" + signed_data + "\n");
				try {
					// 组装发送包
					byte[] messagexml = createReqMessageXml(request.getRemoteAddr(), attrType, appId, signed_data,
							original_data);
					// 获取返回结果
					String respMessageXml = getRespMessageXml(authURL, messagexml);
					Document respDocument = null;
					Element headElement = null;
					Element bodyElement = null;
					respDocument = DocumentHelper.parseText(respMessageXml);
					headElement = respDocument.getRootElement().element("head");
					bodyElement = respDocument.getRootElement().element("body");

					if (headElement != null) {
						boolean state = Boolean.valueOf(headElement.elementTextTrim("messageState")).booleanValue();
						if (state) {
							isSuccess = false;
							errCode = headElement.elementTextTrim("messageCode");
							errDesc = headElement.elementTextTrim("messageDesc");
							logger.info("认证业务处理失败！\t" + errDesc + "\n");
						}
					}

					logger.info("解析报文头成功！\n");

					Element authResult = bodyElement.element("authResultSet").element("authResult");
					isSuccess = Boolean.valueOf(authResult.attributeValue("success")).booleanValue();
					if (!isSuccess) {
						errCode = authResult.elementTextTrim("authMessageCode");
						errDesc = authResult.elementTextTrim("authMessageDesc");
						logger.info("身份认证失败，失败原因：" + errDesc);

					}

					logger.info("身份认证成功！\n");
					String ss = bodyElement.elementTextTrim("accessControlResult");

					logger.info(ss);

					Element attrsElement = bodyElement.element("attributes");
					if (attrsElement != null) {
						List<?> namespacesElements = attrsElement.elements("attr");
						if ((namespacesElements != null) && (namespacesElements.size() > 0)) {
							logger.info("属性个数：" + namespacesElements.size());
							for (int i = 0; i < namespacesElements.size(); i++) {
								String arrs = ((Element) namespacesElements.get(i)).attributeValue("namespace");
								logger.info(arrs);
							}
							Map certAttributeNodeMap = new HashMap();
							Map childAttributeNodeMap = new HashMap();
							Map umsAttributeNodeMap = new HashMap();
							Map pmsAttributeNodeMap = new HashMap();
							String[] keyes = new String[2];
							String subjectDn = null;
							if (attrsElement != null) {
								List attributeNodeList = attrsElement.elements("attr");
								for (int i = 0; i < attributeNodeList.size(); i++) {
									keyes = new String[2];
									Element userAttrNode = (Element) attributeNodeList.get(i);
									String msgParentName = userAttrNode.attributeValue("parentName");
									String name = userAttrNode.attributeValue("name");
									String value = userAttrNode.getTextTrim();
									keyes[0] = name;
									childAttributeNodeMap.clear();
									String arrs = ((Element) namespacesElements.get(i)).attributeValue("namespace");

									if (arrs.trim().equals("http://www.jit.com.cn/cinas/ias/ns/saml/saml11/X.509")) {
										if ((msgParentName != null) && (!msgParentName.equals(""))) {
											keyes[1] = msgParentName;
											if ((value != null) && (value.length() > 0))
												childAttributeNodeMap.put(keyes, value);
										} else if ((value != null) && (value.length() > 0)) {
											certAttributeNodeMap.put(keyes[0], value);
										}
										certAttributeNodeMap.putAll(childAttributeNodeMap);
									} else if (arrs.trim().equals("http://www.jit.com.cn/ums/ns/user")) {
										if ((msgParentName != null) && (!msgParentName.equals(""))) {
											keyes[1] = msgParentName;
											if ((value != null) && (value.length() > 0))
												childAttributeNodeMap.put(keyes, value);
										} else if ((value != null) && (value.length() > 0)) {
											umsAttributeNodeMap.put(keyes[0], value);
										}
										umsAttributeNodeMap.putAll(childAttributeNodeMap);
									} else if (arrs.trim().contains("http://www.jit.com.cn/pmi/pms")) {
										if ((msgParentName != null) && (!msgParentName.equals(""))) {
											keyes[1] = msgParentName;
											if ((value != null) && (value.length() > 0))
												childAttributeNodeMap.put(keyes, value);
										} else if ((value != null) && (value.length() > 0)) {
											pmsAttributeNodeMap.put(keyes[0], value);
										}
										pmsAttributeNodeMap.putAll(childAttributeNodeMap);
									} else {
										if ((msgParentName != null) && (!msgParentName.equals(""))) {
											keyes[1] = msgParentName;
											if ((value != null) && (value.length() > 0))
												childAttributeNodeMap.put(keyes, value);
										} else if ((value != null) && (value.length() > 0)) {
											certAttributeNodeMap.put(keyes[0], value);
										}
										certAttributeNodeMap.putAll(childAttributeNodeMap);
									}
								}
								// request.setAttribute("certAttributeNodeMap",
								// certAttributeNodeMap);
								// request.setAttribute("umsAttributeNodeMap",
								// umsAttributeNodeMap);
								// request.setAttribute("pmsAttributeNodeMap",
								// pmsAttributeNodeMap);
								// 获取证书信息
								String key = "X509Certificate.SubjectDN";
								subjectDn = certAttributeNodeMap.containsKey(key)
										? (String) certAttributeNodeMap.get(key)
										: null;
								logger.debug("subjectDn:" + subjectDn);
								if (StringUtils.isEmpty(subjectDn)) {
									return this.prepareLogin(request, response, modelMap);
								} else {
									int endPos = 0;
									endPos = subjectDn.indexOf(",", subjectDn.indexOf("T="));
									String userId = subjectDn.substring(subjectDn.indexOf("T=") + 2, endPos);
									userId = StringTools.replaceIgnoreCase(userId, "T=", "");
									userId = StringTools.replaceIgnoreCase(userId, "N=", "");
									SysUser bean = null;
									if (StringUtils.isNotEmpty(userId)) {
										bean = authorizeService.login(userId);
									}
									// 如果每天登录次数超过设置值，设置用户对象为空，防止恶意操作
									if (bean != null && userOnlineLogService.getLoginCount(userId) > conf
											.getInt("limit.loginCount", 500)) {
										bean = null;
									}

									if (bean != null) {
										// LoginContext loginContext =
										// IdentityFactory.getLoginContext(bean.getActorId());
										String ipAddr = RequestUtils.getIPAddress(request);
										SystemProperty p = SystemConfig.getProperty("login_limit");
										if (!(StringUtils.equals(bean.getAccount(), "root")
												|| StringUtils.equals(bean.getAccount(), "admin"))) {
											logger.debug("#################check login limit#####################");

											SystemProperty pt = SystemConfig.getProperty("login_time_check");
											int timeoutSeconds = 300;

											if (pt != null && pt.getValue() != null
													&& StringUtils.isNumeric(pt.getValue())) {
												timeoutSeconds = Integer.parseInt(pt.getValue());
											}
											if (timeoutSeconds < 300) {
												timeoutSeconds = 300;
											}
											if (timeoutSeconds > 3600) {
												timeoutSeconds = 3600;
											}

											/**
											 * 检测是否限制一个用户只能在一个地方登录
											 */
											if (p != null && StringUtils.equals(p.getValue(), "true")) {
												logger.debug("#################3#########################");
												String loginIP = null;
												UserOnline userOnline = userOnlineService
														.getUserOnline(bean.getAccount());
												logger.debug("userOnline:" + userOnline);
												boolean timeout = false;
												if (userOnline != null) {
													loginIP = userOnline.getLoginIP();
													if (userOnline.getCheckDateMs() != null
															&& System.currentTimeMillis()
																	- userOnline.getCheckDateMs() > timeoutSeconds
																			* 1000) {
														timeout = true;// 超时，说明登录已经过期
													}
													if (userOnline.getLoginDate() != null && System.currentTimeMillis()
															- userOnline.getLoginDate().getTime() > timeoutSeconds
																	* 1000) {
														timeout = true;// 超时，说明登录已经过期
													}
												}
												logger.info("timeout:" + timeout);
												logger.info("login IP:" + loginIP);
											}
										}

										HttpSession session = request.getSession(true);// 重写Session
										Properties props = CallbackProperties.getProperties();
										if (props != null && props.keys().hasMoreElements()) {
											Enumeration<?> e = props.keys();
											while (e.hasMoreElements()) {
												String className = (String) e.nextElement();
												try {
													Object obj = ClassUtils.instantiateObject(className);
													if (obj instanceof LoginCallback) {
														LoginCallback callback = (LoginCallback) obj;
														if (StringUtils.equals(className,
																"com.glaf.shiro.ShiroLoginCallback")) {
															callback.afterLogin(bean.getAccount(), request, response);
														} else {
															LoginCallbackThread command = new LoginCallbackThread(
																	callback, bean.getAccount(), request, response);
															com.glaf.core.util.ThreadFactory.execute(command);
															Thread.sleep(50);
														}
													}
												} catch (Exception ex) {
													ex.printStackTrace();
													logger.error(ex);
												}
											}
										}
										// 登录成功，修改最近一次登录时间
										bean.setLastLoginDate(new Date());
										bean.setLastLoginIP(ipAddr);
										bean.setLockLoginTime(new Date());
										bean.setLoginRetry(0);
										sysUserService.updateUser(bean);

										ContextUtil.put(bean.getAccount(), bean);// 传入全局变量

										String systemName = Environment.getCurrentSystemName();

										if (StringUtils.isEmpty(systemName)) {
											systemName = "default";
										}

										RequestUtils.setLoginUser(request, response, systemName, bean.getAccount());

										try {
											UserOnline online = new UserOnline();
											online.setActorId(bean.getActorId());
											online.setName(bean.getName());
											online.setCheckDate(new Date());
											online.setLoginDate(new Date());
											if (!bean.isSystemAdministrator()) {
												online.setLoginIP(ipAddr);
											}
											userOnlineService.login(online);
										} catch (Exception ex) {
											ex.printStackTrace();
										}
										return new ModelAndView("/modules/main", modelMap);
									}
								}
							}
						}
					}
				} catch (Exception e) {
					isSuccess = false;
					e.printStackTrace();
					logger.error(e.getMessage());
					errDesc = e.getMessage();
				}
			} else {
				isSuccess = false;
				errDesc = "证书认证数据不完整";
				logger.info("证书认证数据不完整！\n");
			}
		}
		if (!isSuccess) {
			if (StringUtils.isNotEmpty(errCode)) {
				request.setAttribute("errCode", errCode);
			}
			if (StringUtils.isNotEmpty(errDesc)) {

				request.setAttribute("errDesc", errDesc);
			}
			logger.error("处理数据结束，业务处理失败，失败原因：" + errDesc + "\n");

		}
		// request.setAttribute("isSuccess", new Boolean(isSuccess).toString());

		return this.prepareLogin(request, response, modelMap);
	}

	/**
	 * 准备登录
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	public ModelAndView prepareLogin(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		logger.debug("---------------------prepareLogin--------------------");
		RequestUtils.setRequestParameterToAttribute(request);

		HttpSession session = request.getSession(true);
		java.util.Random random = new java.util.Random();
		String rand = Math.abs(random.nextInt(9999)) + com.glaf.core.util.UUID32.getUUID()
				+ Math.abs(random.nextInt(9999));
		String rand2 = Math.abs(random.nextInt(9999)) + com.glaf.core.util.UUID32.getUUID()
				+ Math.abs(random.nextInt(9999));
		if (session != null) {
			rand = Base64.encodeBase64String(rand.getBytes()) + com.glaf.core.util.UUID32.getUUID();
			rand2 = Base64.encodeBase64String(rand2.getBytes()) + com.glaf.core.util.UUID32.getUUID();
			session.setAttribute("x_y", rand);
			session.setAttribute("x_z", rand2);
		}
		String login_html = SystemConfig.getString("login_html");
		if (StringUtils.isNotEmpty(login_html) && (StringUtils.endsWithIgnoreCase(login_html, ".html")
				|| StringUtils.endsWithIgnoreCase(login_html, ".htm"))) {
			try {
				if (StringUtils.startsWith(login_html, request.getContextPath())) {
					response.sendRedirect(login_html);
				} else {
					response.sendRedirect(request.getContextPath() + login_html);
				}
				return null;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		String view = "/modules/login";

		if (StringUtils.isNotEmpty(SystemConfig.getString("login_view"))) {
			view = SystemConfig.getString("login_view");
		}

		if (RequestUtils.isMobile(request)) {
			// 手机版登录页面
			view = "/mobile/login";
		}

		// 显示登陆页面
		return new ModelAndView(view, modelMap);
	}

	@javax.annotation.Resource
	public void setAuthorizeService(AuthorizeService authorizeService) {
		this.authorizeService = authorizeService;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@javax.annotation.Resource
	public void setUserOnlineLogService(UserOnlineLogService userOnlineLogService) {
		this.userOnlineLogService = userOnlineLogService;
	}

	@javax.annotation.Resource
	public void setUserOnlineService(UserOnlineService userOnlineService) {
		this.userOnlineService = userOnlineService;
	}
}
