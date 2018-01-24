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

package com.glaf.core.web.springmvc;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Semaphore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.domain.ServerEntity;
import com.glaf.core.security.LoginContext;
import com.glaf.core.security.SecurityUtils;
import com.glaf.core.service.IServerEntityService;
import com.glaf.core.util.FtpUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;

@Controller("/ftp/upload")
@RequestMapping("/ftp/upload")
public class MxFtpUploadController {
	protected final static Log logger = LogFactory.getLog(MxFtpUploadController.class);

	private static final int MAX_AVAILABLE = 10;

	private final Semaphore semaphore = new Semaphore(MAX_AVAILABLE, true);

	protected IServerEntityService serverEntityService;

	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		FTPClient ftpClient = null;
		Long serverId = RequestUtils.getLong(request, "serverId");
		String path = RequestUtils.getString(request, "path");
		try {
			ServerEntity server = serverEntityService.getServerEntityById(serverId);
			if (server != null) {
				if (StringUtils.isNotEmpty(server.getPerms())) {
					boolean hasPermission = false;
					List<String> perms = StringTools.split(server.getPerms());
					if (perms != null && !perms.isEmpty()) {
						for (String perm : perms) {
							if (loginContext.getPermissions().contains(perm)) {
								hasPermission = true;
								break;
							}
						}
					}
					if (!hasPermission) {
						return;
					}
				}
				String key = server.getKey();
				String pwd = server.getPassword();
				pwd = SecurityUtils.decode(key, pwd);
				ftpClient = FtpUtils.connectServer(server.getHost(), server.getPort(), server.getUser(), pwd);
				String remoteFile = server.getPath() + "/" + path;
				byte[] bytes = FtpUtils.getBytes(ftpClient, remoteFile);
				String filename = StringTools.replaceIgnoreCase(path, "/", "_");
				ResponseUtils.download(request, response, bytes, filename);
			}
		} catch (Exception ex) {
		} finally {
			FtpUtils.closeConnect(ftpClient);
		}
	}

	@RequestMapping("/remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		String message = "";
		FTPClient ftpClient = null;
		Long serverId = RequestUtils.getLong(request, "serverId");
		String path = RequestUtils.getString(request, "path");
		try {
			ServerEntity server = serverEntityService.getServerEntityById(serverId);
			if (server != null) {
				if (StringUtils.isNotEmpty(server.getPerms())) {
					boolean hasPermission = false;
					List<String> perms = StringTools.split(server.getPerms());
					if (perms != null && !perms.isEmpty()) {
						for (String perm : perms) {
							if (loginContext.getPermissions().contains(perm)) {
								hasPermission = true;
								break;
							}
						}
					}
					if (!hasPermission) {
						return;
					}
				}
				String key = server.getKey();
				String pwd = server.getPassword();
				pwd = SecurityUtils.decode(key, pwd);
				ftpClient = FtpUtils.connectServer(server.getHost(), server.getPort(), server.getUser(), pwd);
				String remoteFile = server.getPath() + "/" + path;
				FtpUtils.deleteFile(ftpClient, remoteFile);
				message = "删除成功！";
			}
		} catch (Exception ex) {
			message = "操作失败！";
		} finally {
			FtpUtils.closeConnect(ftpClient);
		}

		String responseDataType = request.getParameter("responseDataType");
		if (StringUtils.equals(responseDataType, "json")) {
			Map<String, Object> jsonMap = new java.util.HashMap<String, Object>();
			jsonMap.put("message", message);
			JSONObject object = new JSONObject(jsonMap);
			response.getWriter().write(object.toString());
			return;
		} else if (StringUtils.equals(responseDataType, "xml")) {
			StringBuffer buffer = new StringBuffer(500);
			buffer.append("<response>");
			buffer.append("\n    <message>").append(message).append("</message>");
			buffer.append("\n</response>");
			response.getWriter().write(buffer.toString());
			return;
		}
	}

	@javax.annotation.Resource
	public void setServerEntityService(IServerEntityService serverEntityService) {
		this.serverEntityService = serverEntityService;
	}

	@RequestMapping("/uploadNow")
	public void upload(HttpServletRequest request, HttpServletResponse response) {
		// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			Map<String, Object> paramMap = RequestUtils.getParameterMap(req);
			logger.debug("paramMap:" + paramMap);
			FTPClient ftpClient = null;
			Long serverId = RequestUtils.getLong(request, "serverId");
			String path = RequestUtils.getString(request, "path");
			LoginContext loginContext = RequestUtils.getLoginContext(request);
			try {
				semaphore.acquire();
				ServerEntity server = serverEntityService.getServerEntityById(serverId);
				if (server != null) {
					if (StringUtils.isNotEmpty(server.getPerms())) {
						boolean hasPermission = false;
						List<String> perms = StringTools.split(server.getPerms());
						if (perms != null && !perms.isEmpty()) {
							for (String perm : perms) {
								if (loginContext.getPermissions().contains(perm)) {
									hasPermission = true;
									break;
								}
							}
						}
						if (!hasPermission) {
							return;
						}
					}
					Map<String, MultipartFile> fileMap = req.getFileMap();
					Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
					for (Entry<String, MultipartFile> entry : entrySet) {
						MultipartFile mFile = entry.getValue();
						if (mFile.getOriginalFilename() != null && mFile.getSize() > 0) {
							String filename = mFile.getOriginalFilename();
							logger.debug("upload file:" + filename);
							String remoteFile = server.getPath() + "/" + path + "/" + filename;
							FtpUtils.upload(ftpClient, remoteFile, mFile.getInputStream());
						}
					}
				}
			} catch (Exception ex) {
				logger.debug(ex);
			} finally {
				semaphore.release();
				FtpUtils.closeConnect(ftpClient);
			}
		}
	}

}