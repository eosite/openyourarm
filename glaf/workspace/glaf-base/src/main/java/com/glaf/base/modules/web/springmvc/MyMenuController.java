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

package com.glaf.base.modules.web.springmvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glaf.core.base.TreeModel;
import com.glaf.core.domain.Database;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.ITreeModelService;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.UUID32;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.mq.RabbitMQWPFLoginQueueProducerThread;

@Controller("/my/menu")
@RequestMapping("/my/menu")
public class MyMenuController {

	protected final static Log logger = LogFactory.getLog(MyMenuController.class);

	protected IDatabaseService databaseService;

	protected SysApplicationService sysApplicationService;

	protected ITreeModelService treeModelService;

	@RequestMapping("/jump")
	public void jump(HttpServletRequest request, HttpServletResponse response) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (loginContext == null) {
			try {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			} catch (IOException ex) {
			}
		}
		String menuId = request.getParameter("menuId");
		if (menuId != null) {
			menuId = RequestUtils.decodeString(menuId);
			logger.debug("->menuId:" + menuId);
		} else {
			menuId = request.getParameter("id");
		}
		if (menuId != null && StringUtils.isNumeric(menuId)) {
			TreeModel treeModel = sysApplicationService.getTreeModelByAppId(Long.parseLong(menuId));
			SysApplication app = sysApplicationService.findById(Long.parseLong(menuId));
			if (treeModel != null && app != null) {
				try {
					String url = treeModel.getUrl();
					if (StringUtils.isNotEmpty(url)) {
						if (!(url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://"))) {
							if (url.startsWith("/")) {
								url = request.getContextPath() + url;
							} else {
								url = request.getContextPath() + "/" + url;
							}
						}
						if (url.indexOf("?") != -1) {
							url = url + "&time=" + System.currentTimeMillis();
						} else {
							url = url + "?time=" + System.currentTimeMillis();
						}
						logger.debug("forward url:" + url);
						response.sendRedirect(url);
						return;
					} else if (StringUtils.equalsIgnoreCase(app.getSystemFlag(), "JAVA")
							&& StringUtils.isNotEmpty(app.getPageId())) {
						url = request.getContextPath() + SysConstants.FORM_DEF_URL + app.getPageId();
						response.sendRedirect(url);
						return;
					} else if (StringUtils.isNotEmpty(app.getUid()) && StringUtils.equals(app.getSystemFlag(), "WPF")) {
						// 云众联接入WPF菜单
						String loginIP = RequestUtils.getIPAddress(request);
						String token = UUID32.getUUID();
						// 发送登录信息到消息队列MQServer
						Runnable command = new RabbitMQWPFLoginQueueProducerThread(token, loginContext.getActorId(),
								loginIP, app.getUid(), app.getDatabaseId(), "login_queue");
						com.glaf.core.util.threads.ThreadFactory.execute(command);

						Database database = databaseService.getDatabaseById(app.getDatabaseId());
						if (database != null && StringUtils.isNotEmpty(database.getRemoteUrl())) {
							if (StringUtils.startsWithIgnoreCase(database.getRemoteUrl(), "gnesl:")) {
								url = database.getRemoteUrl() + "&token=" + token + "&UserParam=" + token;
								if (StringUtils.isNotEmpty(database.getUserNameKey())) {
									url = url + "&username=" + database.getUserNameKey();
								}
								if (StringUtils.isNotEmpty(database.getTicket())) {
									url = url + "&ticket=" + database.getTicket();
								}
								if (StringUtils.isNotEmpty(database.getToken())) {
									url = url + "&tk=" + database.getTicket();
								}
								logger.debug("-----------RemoteUrl---------------------");
								logger.debug("remoteUrl:" + url);
								response.sendRedirect(url);
								return;
							} else if (StringUtils.startsWithIgnoreCase(database.getRemoteUrl(), "gnoem:")) {
								url = database.getRemoteUrl();
								url = StringTools.replace(url, "demo", token);
								logger.debug("-----------RemoteUrl---------------------");
								logger.debug("remoteUrl:" + url);
								request.getSession().setAttribute("remoteUrl", url);
								response.sendRedirect(request.getContextPath() + "/gnoem.jsp?remoteUrl="
										+ RequestUtils.encodeURL(url));
								return;
							}
						}
					} else {
						return;
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		try {
			request.getRequestDispatcher("/WEB-INF/views/404.jsp").forward(request, response);
		} catch (Exception e) {
		}
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource
	public void setSysApplicationService(SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
	}

	@javax.annotation.Resource
	public void setTreeModelService(ITreeModelService treeModelService) {
		this.treeModelService = treeModelService;
	}

}